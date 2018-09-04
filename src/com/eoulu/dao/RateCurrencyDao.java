package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.util.DBUtil;

public class RateCurrencyDao {

	public List<Map<String,Object>> getAllData(){
		DBUtil util = new DBUtil();
		String sql  = "select * from t_rate_currency";
		Object[] parameter = null;
		List<Map<String,Object>> ls = util.QueryToList(sql, parameter);
		return ls;
	}
	
}
