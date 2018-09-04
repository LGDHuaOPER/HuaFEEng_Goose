/**
 * 
 */
package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eoulu.entity.Area;
import com.eoulu.entity.Order;
import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 */
public class AreaDao {

	
	/**
	 * 通过areaID获取area
	 * 
	 * @param area  
	 * 
	 * @return List 第一行为列名   以后为数据行
	 * */
	public List<Map<String, Object>> getAreaByID(Area area){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select * from t_area where ID=?";
		Object[] parameter = new Object[]{area.getID()};
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	
	/**
	 * 通过areaName获取areaID
	 * 
	 * @param area  
	 * 
	 * @return int 返回Name对应的ID
	 * */
	public int getAreaIDByName(String area){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select ID from t_area where AreaName=?";
		Object[] parameter = new Object[]{area};
		
		
		ls = db.QueryToList(sql, parameter);
		int ID = 0;
		if(ls.size()>1)
			ID = Integer.parseInt(ls.get(1).get("ID").toString());
		return ID;
	}
	
	
	/**
	 * 获取所有的地区
	 * */
	public List<Map<String, Object>> getArea(){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select t_area.ID,t_area.AreaName from t_area";
		Object[] parameter = new Object[0];
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	
}
