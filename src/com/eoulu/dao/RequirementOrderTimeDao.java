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
 * 预计成单时间  ,t_requirement_order_time
 */
public class RequirementOrderTimeDao {

	public List<Map<String,Object>> getAllRequirementOrderTime(){

		List<Map<String,Object>> ls;
		String sql = "select * from t_requirement_order_time";
		Object[] parameter = new Object[0];
		DBUtil db = new DBUtil();


		ls = db.QueryToList(sql, parameter);

		return ls;
	}
	
	
}
