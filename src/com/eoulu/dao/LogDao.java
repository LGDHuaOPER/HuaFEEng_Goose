/**
 * 
 */
package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.entity.Equipment;
import com.eoulu.entity.Log;
import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 */
public class LogDao {

	/**
	 * ͨ��logID��ȡlog
	 * 
	 * @param log  
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * */
	public List<Map<String, Object>> getLogByID(Log log){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select * from t_log where ID=?";
		Object[] parameter = new Object[]{log.getID()};
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
}
