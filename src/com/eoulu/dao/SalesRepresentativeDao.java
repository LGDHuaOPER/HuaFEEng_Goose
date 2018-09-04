/**
 * 
 */
package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.entity.Area;
import com.eoulu.entity.Role;
import com.eoulu.entity.SalesRepresentative;
import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 */
public class SalesRepresentativeDao {

	
	/**
	 * 通过salesRepresentativeID获取salesRepresentative
	 * 
	 * @param salesRepresentative  
	 * 
	 * @return List 第一行为列名   以后为数据行
	 * */
	public List<Map<String, Object>> getSalesRepresentativeByID(SalesRepresentative salesRepresentative){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select * from t_sales_representative where ID=?";
		Object[] parameter = new Object[]{salesRepresentative.getID()};
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	
	
	/**
	 * 通过SalesName获取salesID
	 * 
	 * @param salesRepresentative  
	 * 
	 * @return int 返回Name对应的ID
	 * */
	public int getSalesID(String salesRepresentative){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select ID from t_sales_representative where Name=?";
		Object[] parameter = new Object[]{salesRepresentative};
		
		
		ls = db.QueryToList(sql, parameter);
		int ID = 0;
		if(ls.size()>1)
			ID = Integer.parseInt(ls.get(1).get("ID").toString());
		return ID;
	}
	
	
	/**
	 * 获取所有的销售代表
	 * */
	public List<Map<String, Object>> getAllSales(){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select t_sales_representative.ID,t_sales_representative.Name,t_sales_representative.EmployeeNumber,t_sales_representative.Contact,t_sales_representative.Email from t_sales_representative";
		Object[] parameter = new Object[0];
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
}
