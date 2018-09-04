package com.eoulu.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import com.eoulu.commonality.Page;
import com.eoulu.dao.StaffApplicationDao;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.dao.UserDao;
import com.eoulu.entity.StaffApplication;
import com.eoulu.service.StaffApplicationService;
import com.eoulu.syn.ExportApplication;
import com.eoulu.util.EXCELUtil;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;


public class StaffApplicationServiceImpl implements StaffApplicationService {

	@Override
	public List<Map<String, Object>> getAllData(Page page, String user,String authority,String startTime,String endTime) {
		return new StaffApplicationDao().getAllData(page, user, authority,startTime,endTime);
	}

	@Override
	public int getCounts(String user, String authority,String startTime,String endTime) {
		return new StaffApplicationDao().getCounts(user,authority,startTime,endTime);
	}

	@Override
	public String operate(HttpServletRequest request) {
		Properties pro = new Properties();
		try {
			pro.load(SendMailUtil.class.getResourceAsStream("./staff.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String CopyList = "";
		String email = request.getSession().getAttribute("email").toString().trim();
		int copyNum = Integer.parseInt(pro.get("copyto").toString());
		boolean exist = false;
		for(int i=0;i<copyNum;i++){
			if(email.equals(pro.getProperty("CopyTo"+(i+1)))){
				exist = true;
				break;
			}		
		}
		String[] copyTo = null;
		if(exist){
			copyTo = new String[Integer.parseInt(pro.get("copyto").toString())];
			for(int i=0;i<copyTo.length;i++){
				copyTo[i] = pro.getProperty("CopyTo"+(i+1));
				CopyList += copyTo[i]+";";
			}
		}else{
			copyTo = new String[Integer.parseInt(pro.get("copyto").toString())+1];
			for(int i=0;i<copyTo.length-1;i++){
				copyTo[i] = pro.getProperty("CopyTo"+(i+1));
				CopyList += copyTo[i]+";";
			}
			CopyList += email+";";
			copyTo[copyTo.length-1] = email;	
		}
		

		
		String type = request.getParameter("Type") == null ? "" : request.getParameter("Type");
		String RealName = request.getParameter("RealName") == null ? "" : request.getParameter("RealName").trim();
		String Department = request.getParameter("Department") == null ? "" : request.getParameter("Department");
		String StartTime = request.getParameter("StartTime") == null ? "0000-00-00 00:00:00"
				: request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime") == null ? "0000-00-00 00:00:00"
				: request.getParameter("EndTime");
		String Classify = request.getParameter("Classify") == null ? "" : request.getParameter("Classify");
		String Review = "等待审批";
		String FailedReason = "";
		String ToList = request.getParameter("ToList") == null ? "" : request.getParameter("ToList");
		String recipient = ToList.split("&lt")[1].split("&gt")[0];
		String MailContent = request.getParameter("MailContent") == null ? "" : request.getParameter("MailContent").trim();
		String MailContentText = request.getParameter("MailContentText") == null ? "" : request.getParameter("MailContentText").trim();
		String Reason = request.getParameter("Reason") == null ? "" : request.getParameter("Reason").trim();
		String Eliminate = "未销假";  
		int id = request.getParameter("ID") == null ? 0 : Integer.parseInt(request.getParameter("ID"));
		
		
		String password = request.getParameter("Password").trim();
		StaffApplication staff = new StaffApplication();
		staff.setID(id);
		staff.setRealName(RealName);
		staff.setDepartment(Department);
		staff.setStartTime(StartTime);
		staff.setEndTime(EndTime);
		staff.setClassify(Classify);
		staff.setReview(Review);
		staff.setFailedReason(FailedReason);
		staff.setCopyList(CopyList);
		staff.setMailContent(MailContent);
		staff.setMailContentText(MailContentText);
		staff.setReason(Reason);
		staff.setToList(ToList);
		staff.setEliminate(Eliminate);
		staff.setPassword(password);
		StaffApplicationDao dao = new StaffApplicationDao();
		String result = "";
		
		StaffInfoDao st = new StaffInfoDao();
		List<Map<String,Object>> ls = st.getTelAndName(email);
		String tel = "NA";
		if(ls.size()>1){
			tel = ls.get(1).get("LinkTel")=="--"?"NA":ls.get(1).get("LinkTel").toString();
		}
		
		
		if (password == null || password.equals("")) {
			result = "邮箱密码不能为空！";
		}else{
			
			ExportApplication word = new ExportApplication();
			String url = request.getServletContext().getRealPath("/")+word.exportWord(request);
			String[] filelist = new String[]{url};
			String[] to = recipient.split(";");
			String subject = "Eoulu：《请假申请单》-" +RealName;
			JavaMailToolsUtil util = new JavaMailToolsUtil(email, email, password);

			MailContent = new MethodUtil().getStaffEmailSign(MailContent, RealName, tel, email);
			System.out.println(Arrays.toString(copyTo));
			boolean flag = util.doSendHtmlEmail(subject, MailContent, filelist, to, copyTo);
			if (flag) {
				if(type.equals("add")){
					result = "申请成功！";
					if(!dao.insert(staff)){
						result = "邮件发送成功但保存申请失败！";
					}
				}else{
					result = "修改申请成功！";
					if(!dao.update(staff)){
						result = "邮件发送成功但保存申请失败！";
					}
				}
					
			} else {
				result = "邮件发送失败,请检查密码";
			}
			
			
		}
		return result;

	}

	@Override
	public List<Map<String, Object>> getDataByID(int id) {
		return new StaffApplicationDao().getDataByID(id);
	}

	@Override
	public String updateReview(HttpServletRequest request) {
		StaffApplicationDao dao = new StaffApplicationDao();
		String Review = request.getParameter("Review") == null ? "" : request.getParameter("Review");
		String FailedReason = request.getParameter("FailedReason") == null ? "" : request.getParameter("FailedReason");
		int id = request.getParameter("ID") == null ? 0 : Integer.parseInt(request.getParameter("ID"));
		String email = request.getSession().getAttribute("email").toString();
		String password = request.getParameter("Password").trim();
		List<Map<String, Object>> ls = dao.getDataByID(id);
		String RealName = "";
		String toList = null;
		String[] copyto = null;
		String[] to = null;
		String tel = "NA";
		String mail = email;
		String reviewer = "";
		if(ls.size()>1){
			RealName = ls.get(1).get("RealName").toString();
			toList = ls.get(1).get("ToList").toString();
			if(toList.split("&lt").length > 1){
				to = toList.split("&lt")[1].split("&gt")[0].split(";");
			}else{
				to= toList.split(";");
			}
			copyto = ls.get(1).get("CopyList").toString().split(";");
			String temp = copyto[copyto.length-1];
			copyto[copyto.length-1] = to[0];
			to[0] = temp;
			
			StaffInfoDao st = new StaffInfoDao();
			UserDao userDao = new UserDao();
			List<Map<String,Object>> ls2 = st.getTelAndName(email);
			tel = ls2.get(1).get("LinkTel")==null?"NA":ls2.get(1).get("LinkTel").toString();
			reviewer = ls2.get(1).get("StaffName")=="--"?"NA":ls2.get(1).get("StaffName").toString();
		}
		String str = RealName.replace(" ", "").substring(RealName.replace(" ", "").length()-2);

		System.out.println(str);

		
	
		String subject = "Eoulu：《请假申请单》-" +RealName;
		StringBuffer sb = new StringBuffer();
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>" + str + "，你好！</span><br><br>");
		if(Review.equals("审批通过")){
			sb.append("<span style='font-family:微软雅黑;font-size:14px;'>你的请假申请已审核通过。</span><br><br>");
		}else{
			sb.append("<span style='font-family:微软雅黑;font-size:14px;'>你的请假申请已审核未通过。</span><br>");
			sb.append("<span style='font-family:微软雅黑;font-size:14px;'>原因是："+FailedReason+"。</span><br><br>");
		}
		
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>祝好！<br></span>");
	
	
		String result = "";
		if(password==null || password.equals("")){
			result = "邮箱密码不能为空！";
		}else{
			JavaMailToolsUtil util = new JavaMailToolsUtil(email, email, password);
			String MailContent = new MethodUtil().getStaffEmailSign(sb.toString(), reviewer, tel, mail);
			boolean flag = util.doSendHtmlEmail(subject, MailContent,null, to, copyto);
	
			if (flag) {
				result = "邮件发送成功！";
				if(!dao.updateReview(Review, id, FailedReason)){
					result = "邮件发送成功但数据保存失败！";
				}
					
			} else {
				result = "邮件发送失败,请检查密码";
			}
			
		}
		return result;
	}

	@Override
	public String updateEliminate(HttpServletRequest request) {
		StaffApplicationDao dao = new StaffApplicationDao();
		int id = request.getParameter("ID") == null ? 0 : Integer.parseInt(request.getParameter("ID"));
		String email = request.getSession().getAttribute("email").toString();
		List<Map<String, Object>> ls = dao.getDataByID(id);
		String password = ls.get(1).get("Password").toString();
		String nick = ls.get(1).get("MailContentText").toString().split("！")[0].split("，")[0];
		System.out.println(nick);
		String recipient = "";
		
		
		String RealName = "";
		String[] to = null;
		String[] copyto = null;
		if(ls.size()>1){
			RealName = ls.get(1).get("RealName").toString();
			recipient = ls.get(1).get("ToList").toString().split("&lt")[1].split("&gt")[0];
			copyto = ls.get(1).get("CopyList").toString().split(";");
			to = recipient.split(";");
		}
		
		StaffInfoDao st = new StaffInfoDao();
		List<Map<String,Object>> ls1 = st.getTelAndName(email);
		String tel = "NA";
		String username = "NA";
		if(ls1.size()>1){
			tel = ls1.get(1).get("LinkTel")=="--"?"NA":ls1.get(1).get("LinkTel").toString();
			username = ls1.get(1).get("StaffName")=="--"?"NA":ls1.get(1).get("StaffName").toString();
		}
		if(!username.equals(RealName)){
			return "需申请者本人销假！";
		}
		//String directorName = to[0].split("<")[0];
		String subject = "Eoulu：销假-《请假申请单》-" +RealName;
		StringBuffer sb = new StringBuffer();
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>" + nick +"，您好！</span><br><br>");
		Date date = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		String EliminateTime = sdFormat.format(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		
		
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>"+ RealName +"于"+year+"年"+month+"月"+day+"日完成销假，请知悉！</span><br><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>祝好！<br></span>");
		String content = new MethodUtil().getStaffEmailSign(sb.toString(), RealName, tel, email);
		String result = "";
		if(password==null || password.equals("")){
			result = "邮箱密码不能为空！";
		}else{
				JavaMailToolsUtil util = new JavaMailToolsUtil(email, email, password);
				boolean flag = util.doSendHtmlEmail(subject, content, null, to, copyto);
				if (flag) {
					result = "发送成功";
					dao.updateEliminate(id, EliminateTime);
				} else {
					result = "邮件发送失败！";
				}
			}
		
		
		
		return result;
	}
	
	public static void main(String[] args){
		
		System.out.println(System.getProperty("java.library.path"));
		
	}

	@Override
	public boolean exportExcel(String user, String authority, String startTime, String endTime,String path) {
		StaffApplicationDao dao = new StaffApplicationDao();
		List<Map<String,Object>> data = dao.getExcelData(user, authority, startTime, endTime);
		EXCELUtil util = new EXCELUtil();
		String[] colName = new String[]{"序号","部门","姓名","请假类别","请假事由","请假开始时间","请假结束时间"};
		String[] dataName = new String[]{"rowno","Department","RealName","Classify","Reason","StartTime","EndTime"};
		
		return util.buildExcel(colName, dataName, data, path);
	}
	
	@Test
	public void test(){
		exportExcel("赵文珍", "all", "", "", "D:\\请假申请.xlsx");
	}
	
	

}
