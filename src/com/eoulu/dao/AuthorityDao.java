/**
 * 
 */
package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import org.omg.CORBA.INTERNAL;

import com.eoulu.entity.Authority;
import com.eoulu.entity.Order;
import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 */
public class AuthorityDao {
 
	
	/**
	 * ͨ��authorityID��ȡauthority
	 *根据id查询出所有的权限
	 * @param authority  
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * */
	public List<Map<String, Object>> getAuthorityID(Authority authority){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select * from t_authority where ID=?";
		Object[] parameter = new Object[]{authority.getID()};
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	public String getAuthorityNameByID(int id){
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql="select * from t_authority where ID=?";
		Object[] parameter = new Object[]{id};
		
		ls = db.QueryToList(sql, parameter); 
		return ls.get(1).get("Name").toString();
		
	}
	
	
	
	/**
	 * ִ����ɾ�Ĳ���
	 * 进行修改，返回修改是否成功的信息
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
	 * ��ȡ���е�Ȩ��
	 * 查询出所有权限
	 * */
	public List<Map<String,Object>> getAllAuthority(){
		
		String sql = "select * from t_authority";
		DBUtil db = new DBUtil();
		
		return db.QueryToList(sql, new Object[0]);
	}
}
