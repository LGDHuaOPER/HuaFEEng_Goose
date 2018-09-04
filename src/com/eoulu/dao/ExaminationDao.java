package com.eoulu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Examination;
import com.eoulu.util.DBUtil;

public class ExaminationDao {
	
private static Map<String, String> map;
	
	static{
		map = new HashMap<>();
		map.put("科目", "t_examination_details.Subject");
		map.put("编号", "t_examination_details.Number");
	}
	
	public List<Map<String, Object>> getDataByPage(Page page,String classify){
		String sql = "select t_examination_details.ID,t_examination_details.SerialNumber,t_examination_details.Subject,"
				+ "t_examination_details.Number,t_examination_details.Title,t_examination_details.Classify,"
				+ "t_examination_details.Time from t_examination_details"
				+ " where t_examination_details.Classify = ? order by t_examination_details.Number  limit ?,?";
		return new DBUtil().QueryToList(sql, new Object[]{classify,(page.getCurrentPage()-1)*page.getRows(),page.getRows()});
					
	}
	
	public List<Map<String, Object>> getAccount(String classify){
		String sql = "select COUNT(t_examination_details.ID) Count from t_examination_details"
				+ " where t_examination_details.Classify = ?";
		return new DBUtil().QueryToList(sql, new Object[]{classify});
					
	}
	public List<Map<String, Object>> getScore(int subjectID){
		String sql = "select ID,SubjectID,Examiner,ExaminerID,State,Score from t_examination_score where SubjectID = ?";
		return new DBUtil().QueryToList(sql, new Object[]{subjectID});
	}
	
	public List<Map<String, Object>> getExaminer(){
		String sql = "select ID,Examiner from t_examiner";
		return new DBUtil().QueryToList(sql, null);
	}
	public boolean insertExamination(Examination examination){
		String sql = "insert into t_examination_details(SerialNumber,Subject,Number,Time,Classify) values(?,?,?,?,?)";
		Object[] param = new Object[5];
		param[0] = examination.getSerialNumber();
		param[1] = examination.getSubject();
		param[2] = examination.getNumber();
		param[3] = examination.getTime();
		param[4] = examination.getClassify();
		int result = new DBUtil().executeUpdate(sql, param);
		return result>0?true:false;
	}
	public boolean updateExamination(Examination examination){
		String sql = "update t_examination_details set SerialNumber=?,Subject=?,Number=?,Time=? where ID=?";
		Object[] param = new Object[5];
		param[0] = examination.getSerialNumber();
		param[1] = examination.getSubject();
		param[2] = examination.getNumber();
		param[3] = examination.getTime();
		param[4] = examination.getID();
		int result = new DBUtil().executeUpdate(sql, param);
		return result>0?true:false;
	}
	//添加考核备选人
	public boolean addExaminer(String examiner){
		String sql = "insert into t_examiner(Examiner) values(?)";
		int result = new DBUtil().executeUpdate(sql, new Object[]{examiner});
		return result>0?true:false;
	}
	
	/*
	public boolean deleteExaminer(String[] ID){
		String sql = "delete from t_examiner where ID=?";
		DBUtil dbUtil = new DBUtil();
		int result = 0;
		for(int i = 0;i < ID.length;i ++){
			try {
				result = dbUtil.executeUpdateNotClose(sql, new Object[]{Integer.parseInt(ID[i])});
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		dbUtil.closed();
		
		
		
		return result>0?true:false;
	}
	*/
	
	//保存考核通知相关人员信息
	public boolean saveAssessmentNotice(int subjectID,String[] examiners,String[] examinerID,String department){
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		
	
		String sql1 = "insert into t_examination_score(SubjectID,Examiner,State,ExaminerID) values(?,?,?,?)";
		String sql2 = "update t_examination_details set Status = ?,PublishDate = ?,Department = ? where ID = ?";
		try{
			conn.setAutoCommit(false);
			for(int i = 0;i < examiners.length;i ++){
				dbUtil.executeUpdateNotClose(sql1, new Object[]{subjectID,examiners[i],"no",Integer.parseInt(examinerID[i])});
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dbUtil.executeUpdateNotClose(sql2, new Object[]{"已发布",sdf.format(new Date()),department,subjectID});
			conn.commit();
			return true;
		}catch(SQLException e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
			
		}finally {
			dbUtil.closed();
		}
	
		
	}
	//仅保存人员，不发邮件
	public boolean saveAssessment(int subjectID,String[] examiners,String[] examinerID,String department){
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		
	
		String sql1 = "insert into t_examination_score(SubjectID,Examiner,State,ExaminerID) values(?,?,?,?)";
		String sql2 = "update t_examination_details set Department = ? where ID = ?";
		try{
			conn.setAutoCommit(false);
			for(int i = 0;i < examiners.length;i ++){
				dbUtil.executeUpdateNotClose(sql1, new Object[]{subjectID,examiners[i],"no",Integer.parseInt(examinerID[i])});
			}
		
			dbUtil.executeUpdateNotClose(sql2, new Object[]{department,subjectID});
			conn.commit();
			return true;
		}catch(SQLException e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
			
		}finally {
			dbUtil.closed();
		}
	
		
	}
	
	public boolean isAssessmentExist(int subjectID,String examinerID){
		String sql = "select ID from t_examination_score where subjectID = ? and ExaminerID = ?";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, new Object[]{subjectID,Integer.parseInt(examinerID)});
		if(list.size()>1){
			return true;
		}else{
			return false;
		}
		
	}
	
	public List<Map<String,Object>> getExaminer(int SubjectID){
		String sql = "select ExaminerID from t_examination_score where SubjectID = ?";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{SubjectID});
	}
	
	public boolean deleteScore(int ExaminerID,int SubjectID){
		String sql = "delete from t_examination_score where ExaminerID = ? and SubjectID=?";
		DBUtil dbUtil = new DBUtil();
		int result = dbUtil.executeUpdate(sql, new Object[]{ExaminerID,SubjectID});
		return result > 0?true:false;
	}
	
	public List<Map<String, Object>> getAssessmentNotice(Page page,String column1,String content1){
		DBUtil dbUtil = new DBUtil();
		String sql = "select t_examination_details.ID,t_examination_details.SerialNumber,t_examination_details.Subject,"
				+ "t_examination_details.Number,t_examination_details.Classify,"
				+ "t_examination_details.Time,t_examination_details.Department,t_examination_details.Status,t_examination_details.PublishDate "
				+ "from t_examination_details";
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		if(!column1.equals("")){
			sql += "where "+map.get(column1)+" like ?";
			parameter = new Object[]{"%"+content1+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}
		
		sql += " order by t_examination_details.PublishDate DESC limit ?,?";
		return dbUtil.QueryToList(sql, parameter);
		
		
	}
	
	public List<Map<String, Object>> getAssessmentNoticeCount(String column1,String content1){
		String sql = "select COUNT(t_examination_details.ID) Count from t_examination_details";
		Object[] parameter = null;
		if(!column1.equals("")){
			sql += "where "+map.get(column1)+" like ?";
			parameter = new Object[]{"%"+content1+"%"};
		}
		return new DBUtil().QueryToList(sql, parameter);
					
	}
	
	public boolean saveScore(List<Map<String, String>> scoreList){
		String sql = "update t_examination_score set Score=?,State = 'yes' where ID=?";
		DBUtil dbUtil = new DBUtil();
		PreparedStatement stmt = null;
		Connection connection = dbUtil.getConnection();
		int[] result = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement(sql);
			for(int i = 0;i < scoreList.size();i ++){
				stmt.setString(1, scoreList.get(i).get("Score"));
				stmt.setInt(2, Integer.parseInt(scoreList.get(i).get("ScoreID")));
				stmt.addBatch();
				
			}
			result = stmt.executeBatch();
			connection.commit();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			dbUtil.closed();
		}
		for(int i = 0;i < result.length;i ++){
			if(result[i] < 1){
				return false;
			}
		}
		return true;
	}
	
	/*
	public boolean saveConfirm(List<Map<String, String>> confirmList){
		String sql = "update t_examination_score set State=? where ID=?";
		DBUtil dbUtil = new DBUtil();
		PreparedStatement stmt = null;
		Connection connection = dbUtil.getConnection();
		int[] result = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement(sql);
			for(int i = 0;i < confirmList.size();i ++){
				stmt.setString(1, confirmList.get(i).get("State"));
				stmt.setInt(2, Integer.parseInt(confirmList.get(i).get("ScoreID")));
				stmt.addBatch();
				
			}
			result = stmt.executeBatch();
			connection.commit();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			dbUtil.closed();
		}
		for(int i = 0;i < result.length;i ++){
			if(result[i] < 1){
				return false;
			}
		}
		return true;
	}
	*/
	
	
	public List<Map<String, Object>> getStatisticsByStaff(String sql,Object[]param){
		
		return new DBUtil().QueryToList(sql, param);
	}
	
	public List<Map<String, Object>> getStatisticsByTime(String sql,Object[]param){
		
		return new DBUtil().QueryToList(sql, param);
		
	}
	
	public List<Map<String, Object>> getDepartmentStatistics(String Number){
		String sql = "SELECT Finished, (COUNT(t_staff.Department)-Finished) NotFinished ,"
				+ "t_staff.Department FROM  t_staff INNER JOIN (SELECT COUNT(t_examination_score.ID) Finished,"
				+ "t_staff.Department FROM t_examination_score LEFT JOIN t_examination_details "
				+ "ON t_examination_score.SubjectID = t_examination_details.ID "
				+ "LEFT JOIN t_staff ON t_examination_score.ExaminerID = t_staff.ID "
				+ "where t_examination_details.Number = ? AND  t_examination_score.State != 'no' "
				+ "GROUP BY t_staff.Department) temp ON t_staff.Department = temp.Department "
				+ "GROUP BY t_staff.Department";

		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{Number});
	}
	
	
	public List<Map<String, Object>> getSubject(){
		String sql = "select DISTINCT Subject from t_examination_details";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql,null);
	}
	
	
	public List<Map<String, Object>> getNumber(String Subject){
		String sql = "select Number,Title from t_examination_details where Subject=?";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{Subject});
	}
	

	
	public List<Map<String, Object>> queryDetails(Page page,String department,String number){
		String sql = "select t_examination_details.ID SubjectID,t_examination_score.ID ScoreID,t_examination_details.SerialNumber,t_examination_details.Subject,"
				+ "t_examination_details.Number,t_examination_details.Title,t_examination_details.Classify,"
				+ "t_examination_details.Time,t_examination_score.Examiner,t_examination_score.Score,"
				+ "t_examination_score.State from t_examination_score LEFT JOIN t_staff "
				+ "ON t_examination_score.ExaminerID = t_staff.ID LEFT JOIN t_examination_details "
				+ "ON t_examination_score.SubjectID = t_examination_details.ID "
				+ "where t_examination_details.Number = ? and t_staff.Department = ? order by t_examination_score.Score  limit ?,?";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{number,department,(page.getCurrentPage()-1)*page.getRows(),page.getRows()});
	}
	
	public List<Map<String, Object>> queryCounts(String department,String number){
		String sql = "select count(t_examination_score.ID) Count from t_examination_score LEFT JOIN t_staff "
				+ "ON t_examination_score.ExaminerID = t_staff.ID LEFT JOIN t_examination_details "
				+ "ON t_examination_score.SubjectID = t_examination_details.ID "
				+ "where t_examination_details.Number = ? and t_staff.Department = ?";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{number,department});
		
	}
	
	/*
	public List<Map<String, Object>> queryExamination(String department,String Number){
	
	}
	*/
	
	

	

}
