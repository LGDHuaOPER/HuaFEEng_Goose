/**
 * 
 */
package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.entity.Customer;
import com.eoulu.entity.DutyFree;
import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 */
public class DutyFreeDao {

	
	/**
	 * ͨ��dutyFreeID��ȡdutyFree
	 * 
	 * @param dutyFree  
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * */
	public List<Map<String, Object>> getDutyFreeByID(DutyFree dutyFree){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select * from t_duty_free where ID=?";
		Object[] parameter = new Object[]{dutyFree.getID()};
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	
	
	/**
	 * ͨ��Status��ȡID
	 * 
	 * @param Status  
	 * 
	 * @return int  0Ϊû������   ����0ΪID
	 * */
	public int getID(String Status){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select ID from t_duty_free where Status=?";
		Object[] parameter = new Object[]{Status};
		
		
		ls = db.QueryToList(sql, parameter);
		int id=0;
		if(ls.size()>1)
			id=Integer.parseInt(ls.get(1).get("ID").toString());
		return id;
	}
	
	/**
	 * ��ȡ���е���˰����֤�������
	 * */
	public List<Map<String, Object>> getAllDutyFree(){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select t_duty_free.ID,t_duty_free.Status from t_duty_free";
		Object[] parameter = new Object[0];
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
}
