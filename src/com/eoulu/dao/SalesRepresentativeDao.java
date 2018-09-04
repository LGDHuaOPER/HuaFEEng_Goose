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
	 * ͨ��salesRepresentativeID��ȡsalesRepresentative
	 * 
	 * @param salesRepresentative  
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
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
	 * ͨ��SalesName��ȡsalesID
	 * 
	 * @param salesRepresentative  
	 * 
	 * @return int ����Name��Ӧ��ID
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
	 * ��ȡ���е����۴���
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
