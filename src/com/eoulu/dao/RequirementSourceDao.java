/**
 * 
 */
package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 *t_requirement_source���ʵ����
 */
public class RequirementSourceDao {

	public List<Map<String,Object>> getAllRequirementSource(){

		List<Map<String,Object>> ls;
		String sql = "select * from t_requirement_source";
		Object[] parameter = new Object[0];
		DBUtil db = new DBUtil();


		ls = db.QueryToList(sql, parameter);

		return ls;
	}
	
	
	
	
	/**
	 * ͨ����Դ����ѯ��ID
	 * 
	 * @return 0û�����ֵ   ����ֵ����1���������
	 * */
	public int sourceIsExit(String source){
		int result =  0;
		
		DBUtil db = new DBUtil();
		String sql = "select ID from t_requirement_source where Source=?";
		Object[] parameter = new Object[]{source};
		
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("ID").toString());
		
		
		return result;
	}
	
	
	
	/**
	 * ������Դ��Ϣ
	 * */
	public int insert(String source){
		int result =  0;
		
		DBUtil db = new DBUtil();
		String sql = "insert into t_requirement_source (Source) values (?)";
		Object[] parameter = new Object[]{source};
		
		result = db.executeUpdate(sql, parameter);
		
		
		
		return result;
	}
	
}
