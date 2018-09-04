package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.WorkReport;
import com.eoulu.util.DBUtil;

public class WorkReportDao {
	
	public boolean insert(WorkReport report){
		
		DBUtil dbUtil = new DBUtil();
		String sql = "insert into t_work_report(Department,Name,Morning,Afternoon,Comments,OperationTime,ToList,CopyToList) values(?,?,?,?,?,?,?,?)";
		Object[] param = new Object[8];
		param[0] = report.getDepartment();
		param[1] = report.getName();
		param[2] = report.getMorning();
		param[3] = report.getAfternoon();
		param[4] = "未发送";
		param[5] = report.getOperationTime();
		param[6] = "liuyanan@eoulu.com;";
		param[7] = "luwenbo@eoulu.com;luoxiaoxu@eoulu.com;zhaolili@eoulu.com;";

		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
	}
	
	public boolean update(WorkReport report){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_work_report set Morning=?,Afternoon=?,"
				+ "MorningPlan=?,AfternoonPlan=?,Introspection=?,OperationTime=? where ID=?";
		Object[] param = new Object[7];
		param[0] = report.getMorning();
		param[1] = report.getAfternoon();
		param[2] = report.getMorningPlan();
		param[3] = report.getAfternoonPlan();
		param[4] = report.getIntrospection();
		param[5] = report.getOperationTime();
		param[6] = report.getID();
	
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
		
	}
	public List<Map<String, Object>> getDataByPage(Page page){
		DBUtil dbUtil = new DBUtil();
		String sql = "select ID,Department,Name,Morning,Afternoon,Comments,"
				+ "MorningPlan,AfternoonPlan,Introspection,ToList,CopyToList from t_work_report order by  OperationTime desc limit ?,?";
		
		List<Map<String, Object>> list = dbUtil.QueryToList(sql,new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()});
		return list;
	}
	
	public int getCounts(){
		DBUtil dbUtil = new DBUtil();
		String sql = "select COUNT(ID) Count from t_work_report ";
				
		Object[] parameter = null;
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return Integer.parseInt(list.get(1).get("Count").toString());
	}
	
	public boolean updateComments(int ID){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_work_report set Comments='已发送' where ID = ?";
		int result = dbUtil.executeUpdate(sql, new Object[]{ID});
		return result>0?true:false;
	
	}
	
	public List<Map<String, Object>> getLogByID(int ID){
		DBUtil dbUtil = new DBUtil();
		String sql = "select Morning,Afternoon,Comments,MorningPlan,AfternoonPlan,"
				+ "Introspection from t_work_report where ID=?";
		
		return dbUtil.QueryToList(sql, new Object[]{ID});
	}
	
	public boolean updateLogState(String toStr,String copytoStr,int ID){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_work_report set Morning = MorningPlan,Afternoon=AfternoonPlan,"
				+ "MorningPlan='',AfternoonPlan='',Comments='未发送',ToList=?,CopyToList=? where ID = ?";
		int result = dbUtil.executeUpdate(sql, new Object[]{toStr,copytoStr,ID});
		
		return result>0?true:false;
	}
	


}
