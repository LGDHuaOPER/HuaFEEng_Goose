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
 *t_requirement_source表的实体类
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
	 * 通过来源来查询出ID
	 * 
	 * @return 0没有这个值   返回值大于1有这个参数
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
	 * 插入来源信息
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
