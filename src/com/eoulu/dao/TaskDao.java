package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Task;
import com.eoulu.util.DBUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;


public class TaskDao {
	public boolean insert(Task task){
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil dbUtil = new DBUtil();
		String sql = "insert into t_tasks(PublishedDate,LimitDate,CompleteDate,Title,"
				+ "Description,Classify,Publisher,ResponsibleMan,Review,Progress,IsCompleted,RefNO,OperatingTime,IsPublished) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] parameter = new Object[14];
		parameter[0] = task.getPublishedDate();
		parameter[1] = task.getLimitDate();
		parameter[2] = task.getCompleteDate();
		parameter[3] = task.getTitle();
		parameter[4] = task.getDescription();
		parameter[5] = task.getClassify();
		parameter[6] = task.getPublisher();
		parameter[7] = task.getResponsibleMan();
		parameter[8] = task.getReview();
		parameter[9] = task.getProgress();
		parameter[10] = task.getIsCompleted();
		parameter[11] = task.getRefNo();
		parameter[12] = sdFormat.format(new Date());
		parameter[13] ="待发布";
		int result = dbUtil.executeUpdate(sql, parameter);
		return result > 0?true:false;
				
		
	}
	
	public boolean update(Task task){
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_tasks set PublishedDate=?,LimitDate=?,CompleteDate=?,Title=?,"
				+ "Description=?,Classify=?,Publisher=?,ResponsibleMan=?,Review=?,Progress=?,IsCompleted=?,RefNO=?,OperatingTime=? "
				+ "where ID = ?";
		Object[] parameter = new Object[14];
		parameter[0] = task.getPublishedDate();
		parameter[1] = task.getLimitDate();
		parameter[2] = task.getCompleteDate();
		parameter[3] = task.getTitle();
		parameter[4] = task.getDescription();
		parameter[5] = task.getClassify();
		parameter[6] = task.getPublisher();
		parameter[7] = task.getResponsibleMan();
		parameter[8] = task.getReview();
		parameter[9] = task.getProgress();
		parameter[10] = task.getIsCompleted();
		parameter[11] = task.getRefNo();
		parameter[12] = sdFormat.format(new Date());
		parameter[13] = task.getID();
		int result = dbUtil.executeUpdate(sql, parameter);
		return result > 0?true:false;
				
		
	}
	public boolean  updateStatus(int ID){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_tasks set IsPublished='已发布' where ID=?";
		int result = dbUtil.executeUpdate(sql, new Object[]{ID});
		return result > 0?true:false;	
	}
	
	public List<Map<String, Object>> getDataByPage(Page page,String type,String column1,String content1,String column2,String content2){
		DBUtil dbUtil = new DBUtil();
		String sql = "select ID,PublishedDate,LimitDate,CompleteDate,Title,"
				+ "Description,Classify,Publisher,ResponsibleMan,Review,Progress,"
				+ "IsCompleted,RefNO,IsPublished from t_tasks ";
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};;
		if(type.equals("singleSelect")){
			sql += "where "+column1+" like ?";
			parameter = new Object[]{"%"+content1+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}else if(type.equals("mixSelect")){
			sql += "where "+column1+" like ? and "+column2+" like ?";
			parameter = new Object[]{"%"+content1+"%","%"+content2+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}
		sql += " order by OperatingTime desc limit ?,?";
		
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return list;
	}
	
	public int getCounts(String type,String column1,String content1,String column2,String content2){
		DBUtil dbUtil = new DBUtil();
		String sql = "select COUNT(ID) Count from t_tasks ";
		Object[] parameter = null;
		if(type.equals("singleSelect")){
			sql += "where "+column1+" like ?";
			parameter = new Object[]{"%"+content1+"%"};
		}else if(type.equals("mixSelect")){
			sql += "where "+column1+" like ? and "+column2+" like ?";
			parameter = new Object[]{"%"+content1+"%","%"+content2+"%"};
		}
	
		
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return Integer.parseInt(list.get(1).get("Count").toString());
	}
	
	public List<Map<String, Object>> getStatistics(String startTime,String endTime){
		DBUtil dbUtil = new DBUtil();
		String sql = "select count(ID) Count,ResponsibleMan,MONTH(CompleteDate) Month from t_tasks where "
				+ "CompleteDate between ? and ? group by "
				+ "ResponsibleMan,MONTH(CompleteDate) ORDER BY Count DESC";
		Object[] parameter = new Object[]{startTime,endTime};
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return list;
	}
	
	public List<Map<String, Object>> getStatisticsByReview(String startTime,String endTime){
		DBUtil dbUtil = new DBUtil();
		String sql = "select avg(Review) Score,ResponsibleMan,MONTH(CompleteDate) Month from t_tasks where "
				+ "CompleteDate between ? and ? group by "
				+ "ResponsibleMan,MONTH(CompleteDate) ORDER BY Score DESC";
		Object[] parameter = new Object[]{startTime,endTime};
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return list;
	}
		

}
