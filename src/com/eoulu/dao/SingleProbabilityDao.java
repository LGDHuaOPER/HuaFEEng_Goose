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
 */
public class SingleProbabilityDao {

	/**
	 * ��ѯ�����еĳɵ�����
	 * */
	public List<Map<String,Object>> getAllProbability(){
		String sql = "select * from t_single_probability";
		Object[] parameter = new Object[0];
		DBUtil db = new DBUtil();
		
		
		
		return db.QueryToList(sql, parameter);
	}
	
	
	/**
	 * ��ѯ�ɵ����Ƿ����
	 * */
	public int isExist(String probability){
		
		int result = 0;
		
		String sql = "select * from t_single_probability where Probability=?";
		DBUtil db = new DBUtil();
		
		List<Map<String,Object>> ls = db.QueryToList(sql, new Object[]{probability});
		
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("ID").toString());
		
		return result;
		
	}
}
