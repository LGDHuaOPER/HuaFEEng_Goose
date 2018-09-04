/**
 * 
 */
package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 * 需求表中的品牌
 */
public class RequirementBrandDao {

	public List<Map<String,Object>> getAllRequirementBrand(){
		
		List<Map<String,Object>> ls;
		String sql = "select * from t_requirement_brand";
		Object[] parameter = new Object[0];
		DBUtil db = new DBUtil();
		
		
		ls = db.QueryToList(sql, parameter);
		
		return ls;
	}
	
	
	
	/**
	 * 通过品牌来查询出ID
	 * 
	 * @return 0没有这个值   返回值大于1有这个参数
	 * */
	public int brandIsExit(String brand){
		int result =  0;
		
		DBUtil db = new DBUtil();
		String sql = "select ID from t_requirement_brand where Brand=?";
		Object[] parameter = new Object[]{brand};
		
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("ID").toString());
		
		
		return result;
	}
	
	
	
	/**
	 * 插入品牌信息
	 * */
	public int insert(String brand){
		int result =  0;
		
		DBUtil db = new DBUtil();
		String sql = "insert into t_requirement_brand (Brand) values (?)";
		Object[] parameter = new Object[]{brand};
		
		result = db.executeUpdate(sql, parameter);
		
		
		
		return result;
	}
	
}
