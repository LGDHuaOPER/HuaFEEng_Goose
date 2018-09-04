package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.entity.PageVisit;
import com.eoulu.util.DBUtil;

public class PageVisitDao {
	public boolean insert(PageVisit visit){
		String sql = "insert into t_page_views(PageName,Department,VisitingTime) values(?,?,?)";
		DBUtil util = new DBUtil();
		Object[] parameter = new Object[3];
		parameter[0] = visit.getPageName();
		parameter[1] = visit.getDepartment();
		parameter[2] = visit.getVisitingTime();
		int result = util.executeUpdate(sql, parameter);
		return result > 0?true:false;
	}
	
	public boolean update(PageVisit visit){
		String sql = "update t_page_views set VisitingTime = ? where PageName = ? and Department = ?";
		DBUtil dbUtil = new DBUtil();
		int result = dbUtil.executeUpdate(sql, new Object[]{visit.getVisitingTime(),visit.getPageName(),visit.getDepartment()});
		return result > 0?true:false;
	}
	
	public List<Map<String, Object>> getData(){
		String sql = "select ID,PageName,Department,VisitingTime from t_page_views order by VisitingTime desc";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, null);
	}
	
	public boolean isExist(PageVisit visit){
		String sql = "select ID from t_page_views where PageName = ? and Department = ?";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, new Object[]{visit.getPageName(),visit.getDepartment()});
		if(list.size()>1){
			return true;
		}else{
			return false;
		}
	}
	
	
	

}
