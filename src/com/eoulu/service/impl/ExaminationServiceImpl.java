package com.eoulu.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.eoulu.commonality.Page;
import com.eoulu.dao.ExaminationDao;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.entity.Examination;
import com.eoulu.service.ExaminationService;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class ExaminationServiceImpl implements ExaminationService{

	@Override
	public List<Map<String, Object>> getDataByPage(Page page,String classify) {
		ExaminationDao dao = new ExaminationDao();
		List<Map<String, Object>> list = dao.getDataByPage(page, classify);
		for(int i = 1;i < list.size();i ++){
			List<Map<String,Object>> score = dao.getScore(Integer.parseInt(list.get(i).get("ID").toString()));
			JSONArray scoreJson = JSONArray.fromObject(score);
			list.get(i).put("ScoreInfo", scoreJson.toString());
			
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getExaminer() {
		
		return new ExaminationDao().getExaminer();
	}

	@Override
	public boolean addExaminer(String examiner) {
		
		return new ExaminationDao().addExaminer(examiner);
	}

	
	
	/*
	public boolean deleteExaminer(String[] ID) {
	
		return new ExaminationDao().deleteExaminer(ID);
	}
	
	*/

	@Override
	public boolean insertExamination(Examination examination) {

		return new ExaminationDao().insertExamination(examination);
	}

	@Override
	public boolean updateExamination(Examination examination) {
	
		return new ExaminationDao().updateExamination(examination);
	}

	@Override
	public int getAccount(String classify) {
		
		return Integer.parseInt(new ExaminationDao().getAccount(classify).get(1).get("Count").toString());
	}

	@Override
	public String sendAssessmentNotice(Examination examination, String[] examiners,String[] examinerID,String recipient) {

		StaffInfoDao dao = new StaffInfoDao();
		StringBuilder examinerStr = new StringBuilder();
		for(int i = 0;i < examiners.length;i ++){
			examinerStr.append(examiners[i]);
			examinerStr.append("、");
		}
		
		examinerStr.deleteCharAt(examinerStr.lastIndexOf("、"));

		String[] recipients = recipient.split(";");
		String[] to = new String[recipients.length];
		for(int i = 0;i < recipients.length;i ++){
			List<Map<String, Object>> mail = dao.getMail(recipients[i]);
			if(mail.size()>1){
				String email = mail.get(1).get("StaffMail").toString();
				to[i] = email;
			}else{
				to[i] = "";
			}
		}
		
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>Hi, All,</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>此次考核安排如下：</span><br>");
		
		sBuilder.append("<table border='1' style='font-family:微软雅黑;border-color:#999;border-collapse:collapse;font-size: 14px;color:black;box-sizing:border-box;text-align:center;'><tr style='background:#ccc;"
				+ "height:30px;font-weight:bold;'><td>考核科目</td><td>考核编号</td>"
				+ "<td>时间</td><td>考核人员</td></tr><tr style='background:rgba(228,232,235,0.8);height:30px;'>"
				+"<td>"+examination.getSubject()+"</td><td>"+examination.getNumber()
				+"</td><td>"+examination.getTime()+"</td><td>"+examinerStr.toString()+"</td>"
				+"</tr></table><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>以上请大家知悉，如有任何临时情况，无法参加考核，请提前联系范敏敏，谢谢！</span><br>");
	
		String mailContent = new MethodUtil().getEmailSign(sBuilder.toString(), "NA");
		String subject = "Eoulu:员工考核通知";
		String result = sendEmailConfig(subject, to, mailContent);
		ExaminationDao dao2 = new ExaminationDao();
		int subjectID = examination.getID();
		List<String> newExaminerID = new ArrayList<>();
		List<String> newExaminers = new ArrayList<>();
		List<Map<String, Object>> old = dao2.getExaminer(subjectID);
		for(int i = 1;i < old.size();i ++){
			boolean isDelete = true;
			String ID = old.get(i).get("ExaminerID").toString();
			for(int j = 0;j < examinerID.length;j++){
				if(ID.equals(examinerID[j])){
					isDelete = false;
					break;
				}
			}

			if(isDelete){
				dao2.deleteScore(Integer.parseInt(ID),subjectID);
			}
				
		}
		for(int i = 0;i < examinerID.length;i ++){
			if(!dao2.isAssessmentExist(subjectID, examinerID[i])){
				newExaminerID.add(examinerID[i]);
				newExaminers.add(examiners[i]);	
			}
		}
		examinerID = newExaminerID.toArray(new String[0]);
		examiners = newExaminers.toArray(new String[0]);
		
	
		
		if(!dao2.saveAssessmentNotice(subjectID,examiners,examinerID,examination.getDepartment())){
			result +="，考核人员保存失败";
		}
		return result;
	}

	public String sendEmailConfig(String subject,String[] to,String content){
	

		String[] copyto = null;
		Properties pro = new Properties();
		Properties pro2 = new Properties();
	
		List<String> ls = new ArrayList<>();
		String user;
		String uname;
		String pwd;
	
		try {
			pro.load(SendMailUtil.class.getResourceAsStream("email.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		user = pro.getProperty("SEND_USER");
		uname = pro.getProperty("SEND_UNAME");
		pwd = pro.getProperty("SEND_PWD");
		
		try {
			pro2.load(SendMailUtil.class.getResourceAsStream("assessmentNotice.properties"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		int copytoCount = Integer.parseInt(pro2.getProperty("copyto"));
		copyto = new String[copytoCount];
		for(int i=0 ; i<copytoCount ; i++){
			int temp = i+1;
			String key = "CopyTo"+temp;
			ls.add(pro2.getProperty(key));
			
		}
		for(int i=0 ; i<ls.size();i++){
			copyto[i] = ls.get(i);
		}
		return new JavaMailToolsUtil(user, uname, pwd).sendHtmlEmail(subject, content, null, to, copyto);
	
	}

	@Override
	public List<Map<String, Object>> getAssessmentNotice(Page page, String column1, String content1) {
		ExaminationDao dao = new ExaminationDao();
		List<Map<String, Object>> list = dao.getAssessmentNotice(page, column1, content1);
		
		for(int i = 1;i < list.size();i ++){
			StringBuilder sBuilder = new StringBuilder();
			List<Map<String,Object>> score = dao.getScore(Integer.parseInt(list.get(i).get("ID").toString()));
			String[] ids = new String[score.size()-1];
			for(int j = 1; j < score.size();j ++){
				sBuilder.append(score.get(j).get("Examiner").toString());
				sBuilder.append("、");
				ids[j-1] = score.get(j).get("ExaminerID").toString();
			}
			int index = sBuilder.lastIndexOf("、");
			if(index != -1){
				sBuilder.deleteCharAt(index);
			}
			list.get(i).put("ExaminersID", ids);
			list.get(i).put("Examiners", sBuilder.toString());
		}
		
		return list;
	}

	@Override
	public int getAssessmentNoticeCount(String column1, String content1) {
		
		return Integer.parseInt(new ExaminationDao().getAssessmentNoticeCount(column1,content1).get(1).get("Count").toString());
	}

	@Override
	public String saveScore(String scoreJson) {
		JSONArray array = null;
		if(!scoreJson.equals("")){
			array = JSONArray.fromObject(scoreJson);
		}
		JSONObject object = null;
		Map<String,String> updateMap = null;
		List<Map<String, String>> list = new ArrayList<>();
		for(int i =0;i < array.size();i ++){
			object = array.getJSONObject(i);
			updateMap = new HashMap<>();
			updateMap.put("Score", object.getString("Score"));
			updateMap.put("ScoreID", object.getString("ScoreID"));
			list.add(updateMap);
		}
		boolean result = new ExaminationDao().saveScore(list);
		return result == true?"保存成功":"保存失败";
	}
	
	/*
	public String saveConfirm(String confirmJson) {
		JSONArray array = null;
		if(!confirmJson.equals("")){
			array = JSONArray.fromObject(confirmJson);
		}
		JSONObject object = null;
		Map<String,String> updateMap = null;
		List<Map<String, String>> list = new ArrayList<>();
		for(int i =0;i < array.size();i ++){
			object = array.getJSONObject(i);
			updateMap = new HashMap<>();
			updateMap.put("State", object.getString("State"));
			updateMap.put("ScoreID", object.getString("ScoreID"));
			list.add(updateMap);
		}
		
		boolean result = new ExaminationDao().saveConfirm(list);
		return result == true?"保存成功":"保存失败";
		
	}
	*/

	@Override
	public List<Map<String, Object>> getStatisticsByStaff(String classify) {
		String sql = null;
		Object[] param = null;
		if(classify.equals("All")){
			sql = "select t_examination_score.Examiner,COUNT(t_examination_details.Title) Times,"
					+ "AVG(t_examination_score.Score) AvgScore from t_examination_score left join t_examination_details"
					+" on t_examination_score.SubjectID = t_examination_details.ID GROUP BY t_examination_score.Examiner"
					+ " ORDER BY Times DESC";
		}else{
			sql = "select t_examination_score.Examiner,COUNT(t_examination_details.Title) Times,"
					+ "AVG(t_examination_score.Score) AvgScore from t_examination_score left join t_examination_details"
					+" on t_examination_score.SubjectID = t_examination_details.ID WHERE t_examination_details.Classify = ?"
					+ " GROUP BY t_examination_score.Examiner ORDER BY Times DESC";
			param = new Object[]{classify};
		}
		return new ExaminationDao().getStatisticsByStaff(sql,param);
	}

	@Override
	public List<Map<String, Object>> getStatisticsByTime(String basis,String startTime,String endTime) {
		String sql = null;
		Object[]param = null;
		switch (basis) {
		case "week":
	
			sql = "select a.Weeks,a.Times,b.AvgScore from (select DATE_FORMAT(t_examination_details.Time,'%Y-%m-%u') Weeks,"
					+ "COUNT(t_examination_details.Title) Times FROM t_examination_details WHERE "
					+ "t_examination_details.Time != '0000-00-00' and (t_examination_details.Time between ? and ?) GROUP BY Weeks)  a LEFT JOIN "
					+ "(select DATE_FORMAT(t_examination_details.Time,'%Y-%m-%u') Weeks,avg(t_examination_score.Score) AvgScore "
					+ "from t_examination_details LEFT JOIN t_examination_score on t_examination_score.SubjectID "
					+ "= t_examination_details.ID WHERE t_examination_details.Time != '0000-00-00' and (t_examination_details.Time between ? and ?) "
					+ "GROUP BY Weeks)  b "
					+ "on a.Weeks = b.Weeks ORDER BY a.Weeks";
			param = new Object[]{startTime,endTime,startTime,endTime};
			
			break;

		case "month":

			sql = "select a.Months,a.Times,b.AvgScore from (select DATE_FORMAT(t_examination_details.Time,'%Y-%m') Months,"
					+ "COUNT(t_examination_details.Title) Times FROM t_examination_details WHERE "
					+ "t_examination_details.Time != '0000-00-00' and (t_examination_details.Time between ? and ?) GROUP BY Months)  a LEFT JOIN "
					+ "(select DATE_FORMAT(t_examination_details.Time,'%Y-%m') Months,avg(t_examination_score.Score) AvgScore "
					+ "from t_examination_details LEFT JOIN t_examination_score on t_examination_score.SubjectID "
					+ "= t_examination_details.ID WHERE t_examination_details.Time != '0000-00-00' and (t_examination_details.Time between ? and ?) "
					+ "GROUP BY Months)  b "
					+ "on a.Months = b.Months ORDER BY a.Months";
			
			param = new Object[]{startTime,endTime,startTime,endTime};
			break;
		case "year":
			sql = "select a.Years,a.Times,b.AvgScore from (select Year(t_examination_details.Time) Years,"
					+ "COUNT(t_examination_details.Title) Times FROM t_examination_details WHERE "
					+ "t_examination_details.Time != '0000-00-00' GROUP BY Years)  a LEFT JOIN "
					+ "(select Year(t_examination_details.Time) Years,avg(t_examination_score.Score) AvgScore "
					+ "from t_examination_details LEFT JOIN t_examination_score on t_examination_score.SubjectID "
					+ "= t_examination_details.ID WHERE t_examination_details.Time != '0000-00-00' GROUP BY Years )  b "
					+ "on a.Years = b.Years ORDER BY a.Years";
			break;
		}
	
		return new ExaminationDao().getStatisticsByTime(sql,param);
	}

	@Override
	public List<Map<String, Object>> getDepartmentStatistics(String Number) {
		
		return new ExaminationDao().getDepartmentStatistics(Number);
	}

	@Override
	public List<Map<String, Object>> getNumber(String Subject) {

		return new ExaminationDao().getNumber(Subject);
	}

	@Override
	public List<Map<String, Object>> queryDetails(Page page, String department, String number) {
		return new ExaminationDao().queryDetails(page, department, number);
	}

	@Override
	public int queryCounts(String department, String number) {
		ExaminationDao dao = new ExaminationDao();
		return Integer.parseInt(dao.queryCounts(department,number).get(1).get("Count").toString());
	}

	@Override
	public List<Map<String, Object>> getSubject() {
		return new ExaminationDao().getSubject();
	}

	@Override
	public String saveAssessmentNotice(Examination examination, String[] examiners, String[] examinerID) {
		StringBuilder examinerStr = new StringBuilder();
		for(int i = 0;i < examiners.length;i ++){
			examinerStr.append(examiners[i]);
			examinerStr.append("、");
		}
		examinerStr.deleteCharAt(examinerStr.lastIndexOf("、"));
		
	
		ExaminationDao dao = new ExaminationDao();
		int subjectID = examination.getID();
		List<String> newExaminerID = new ArrayList<>();
		List<String> newExaminers = new ArrayList<>();
		for(int i = 0;i < examinerID.length;i ++){
			if(!dao.isAssessmentExist(subjectID, examinerID[i])){
				newExaminerID.add(examinerID[i]);
				newExaminers.add(examiners[i]);	
			}
		}
		
		List<Map<String, Object>> old = dao.getExaminer(subjectID);
		for(int i = 1;i < old.size();i ++){
			boolean isDelete = true;
			String ID = old.get(i).get("ExaminerID").toString();
			for(int j = 0;j < examinerID.length;j++){
				if(ID.equals(examinerID[j])){
					isDelete = false;
					break;
				}
			}
			if(isDelete){
				dao.deleteScore(Integer.parseInt(ID),subjectID);
			}
				
		}
		
		examinerID = newExaminerID.toArray(new String[0]);
		examiners = newExaminers.toArray(new String[0]);
		if(!dao.saveAssessment(subjectID,examiners,examinerID,examination.getDepartment())){
			return "保存失败";
		}else{
			return "保存成功";
		}
			
	}
}

