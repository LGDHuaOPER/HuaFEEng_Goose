package com.eoulu.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.TrainingRecords;
import com.eoulu.util.DBUtil;
import com.mysql.jdbc.Connection;

public class TrainingRecordsDao {
	
	public List<Map<String, Object>> getDataByPage(Page page,String column,String content){
		DBUtil dbUtil = new DBUtil();
		String sql = "select t_training_records.ID,StaffID,TrainingPeriods,RefreshTime,t_staff.StaffName,t_staff.Department,"
				+ "t_staff.Job from t_training_records left join t_staff on t_training_records.StaffID=t_staff.ID ";
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};;
		if(!column.equals("")){
			sql += "where "+column+" like ?";
			parameter = new Object[]{"%"+content+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}
		sql += " order by OperatingTime desc limit ?,?";
		
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return list;
	}
	
	public int getCounts(String column,String content){
		DBUtil dbUtil = new DBUtil();
		String sql = "select COUNT(ID) Counts from t_training_records ";
		Object[] parameter = null;
		if(!column.equals("")){
			sql += "where "+column+" like ?";
			parameter = new Object[]{"%"+content+"%"};
		}	
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return Integer.parseInt(list.get(1).get("Counts").toString());
	}
	
	public boolean insert( TrainingRecords records){
		String sql = "insert into t_training_records(StaffID,TrainingPeriods,RefreshTime) values(?,?,?)";
		int result = new DBUtil().executeUpdate(sql, new Object[]{records.getStaffID(),records.getTrainingPeriods(),records.getRefreshTime()});
		return result>0?true:false;
	}
	
	public boolean update(TrainingRecords records){
		String sql = "update t_training_records set StaffID=?,TrainingPeriods=?,RefreshTime=? where ID=?";
		int result = new DBUtil().executeUpdate(sql, new Object[]{records.getStaffID(),records.getTrainingPeriods(),
				records.getRefreshTime(),records.getID()});
		return result>0?true:false;
	}
	
	public boolean saveTrainingDetails(List<Map<String, Object>> list){
		 
		String sql = "insert into t_training_details (RecordID,Number,SerialNumber,Attend,TrainingTime,Pass,Feedback) values(?,?,?,?,?,?)";
		String sql1 = "delete from t_training_details where RecordID = ?";
		
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			dbUtil.executeUpdateNotClose(sql1, new Object[]{list.get(0).get("RecordID")});
			for(int i = 0;i < list.size();i ++){
				dbUtil.executeUpdateNotClose(sql, new Object[]{list.get(i).get("RecordID"),list.get(i).get("Number"),
				list.get(i).get("SerialNumber"),list.get(i).get("Attend"),
				list.get(i).get("TrainingTime"),list.get(i).get("Pass"),list.get(i).get("Feedback")});
			}
			conn.commit();
			return true;
			
		} catch (SQLException e) {
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
	

	public List<Map<String, Object>> getTrainingDetails(int recordID){
		String sql = "select ID,Number,Attend,TrainingTime,Pass,Feedback from t_training_details where RecordID=?";
		return new DBUtil().QueryToList(sql, new Object[]{recordID});
	}
	
	

}
