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
 *需求表中的类别，t_requirement_classify 表
 */
public class RequirementClassifyDao {

	public List<Map<String,Object>> getAllRequirementClassify(){

		List<Map<String,Object>> ls;
		String sql = "select * from t_requirement_classify";
		Object[] parameter = new Object[0];
		DBUtil db = new DBUtil();


		ls = db.QueryToList(sql, parameter);

		return ls;
	}

	
	/**
	 * 通过类别来查询出ID
	 * 
	 * @return 0没有这个值   返回值大于1有这个参数
	 * */
	public int classifyIsExit(String classify){
		int result =  0;
		
		DBUtil db = new DBUtil();
		String sql = "select ID from t_requirement_classify where Classify=?";
		Object[] parameter = new Object[]{classify};
		
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("ID").toString());
		
		
		return result;
	}
	
	
	
	/**
	 * 插入类别信息
	 * */
	public int insert(String classify){
		int result =  0;
		
		DBUtil db = new DBUtil();
		String sql = "insert into t_requirement_classify (Classify) values (?)";
		Object[] parameter = new Object[]{classify};
		
		result = db.executeUpdate(sql, parameter);
		
		
		
		return result;
	}

//	@Test
//	public void test(){
//	System.out.println(classifyIsExit("大家好"));	
//	}
	
}
