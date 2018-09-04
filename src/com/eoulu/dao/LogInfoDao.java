package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.LogInfo;
import com.eoulu.util.DBUtil;

public class LogInfoDao {

	public List<Map<String,Object>> getAllData(Page page,String column){
		DBUtil db = new DBUtil();
		String sql = "select * from t_log_info ";
		if(column == null){
			sql += "ORDER BY Date DESC,Time desc";
		}else{
			sql += "ORDER BY "+column;
		}
		sql += " limit ?,?";
		Object[] param = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;
	}
	
	public List<Map<String, Object>> getDataByID(String[] idList){
		String sql1 = "?";
		Object[] param = new Object[idList.length];
		param[0] = Integer.parseInt(idList[0]);
		for(int i = 1;i < idList.length;i ++){
			sql1 += ",?";
			param[i] = Integer.parseInt(idList[i]);
		}
		DBUtil db = new DBUtil();
		String sql = "select * from t_log_info where ID IN("+sql1+")";
		List<Map<String,Object>>  list = db.QueryToList(sql, param);
		return list;
	}
	public int getAllCounts(){
		DBUtil db = new DBUtil();
		String sql = "select COUNT(ID) from t_log_info";
		Object[] param = new Object[]{};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		int count = 0;
		if(ls.size()>1){
			count = Integer.parseInt(ls.get(1).get("COUNT(ID)").toString());
		}
		return count;
	}
	
	public boolean insert(LogInfo log){
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into t_log_info (Account,JspInfo,Description,Date,Time,IpInfo,Location,OperatingTime) VALUES (?,?,?,?,?,?,?,?)";
		Object[] param = new Object[8];
		param[0] = log.getAccount();
		param[1] = log.getJspInfo();
		param[2] = log.getDescription();
		param[3] = log.getDate();
		param[4] = log.getTime();
		param[5] = log.getIpInfo();
		param[6] = log.getLocation();
		param[7] = df.format(new Date());
		boolean flag = false;
//		try {
//			int count = db.executeUpdateNotClose(sql, param);
			int count = db.executeUpdate(sql, param);
			if(count >= 1){
				flag = true;
			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		return flag;
	}
	
	public boolean delete(String outDate){
		DBUtil db = new DBUtil();
		String sql = "delete from t_log_info where Date<?";
		Object[] param = new Object[]{outDate};
		boolean flag = false;
//		try {
//			int count = db.executeUpdateNotClose(sql, param);
			int count = db.executeUpdate(sql, param);
			if(count >=1){
				flag = true;
			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		return flag;
	}
	public List<Map<String, Object>> getAllData(){
		DBUtil db = new DBUtil();
		String sql = "select * from t_log_info";
		Object[] param = null;
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;
	}
	public List<Map<String,Object>> getOutDate(String date){
		DBUtil db = new DBUtil();
		String sql = "select DISTINCT Date from t_log_info WHERE Date<?";
		Object[] param = new Object[]{date};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;
	}
}
