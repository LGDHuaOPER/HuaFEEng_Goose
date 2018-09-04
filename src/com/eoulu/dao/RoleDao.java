package com.eoulu.dao;

import java.util.List;
import java.util.Map;


import com.eoulu.entity.Role;
import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 * 
 * @date   2017/3/27
 * 
 * */
public class RoleDao {

	
	/**
	 * ͨ��roleID��ȡrole
	 * 根据id查询角色
	 * @param role  
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * */
	public List<Map<String, Object>> getRoleByID(Role role){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select * from t_role where ID=?";
		Object[] parameter = new Object[]{role.getID()};
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	

	/**
	 * ��ȡ���еĽ�ɫ��Ϣ
	 * 查询所有的角色
	 * */
	public List<Map<String, Object>> getAllRole(){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select * from t_role";
		Object[] parameter = new Object[0];
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	
	/**
	 * ִ����ɾ�Ĳ���
	 * 修改
	 * */
	public boolean executeUpdate(String sql,Object[] parameter){
		boolean flag = false;
		DBUtil db = new DBUtil();
		
		
		int result = db.executeUpdate(sql, parameter);
		
		if(result>0)
			flag = true;
			
		return flag;
	}
	
	
	
	/**
	 * �ж�ָ����ɫ�Ƿ���ڣ�ͨ��ID
	 * 根据id判断用户是否存在
	 * */
	public boolean roleIsExist(int id){
		boolean flag = false;
		
		DBUtil db = new DBUtil();
		String sql = "select * from t_role where ID=?";
		Object[] parameter = new Object[]{id};
		
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		
		
		if(ls.size()>1)
		    flag = true;
		
		return flag;
	}
	
}
