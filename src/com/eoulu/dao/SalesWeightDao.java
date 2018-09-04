package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.util.DBUtil;

public class SalesWeightDao {

	public List<Map<String, Object>> getSalesWeight() {
		DBUtil db = new DBUtil();
		String sql = "select ID,UnitPrice,Quantity,Value,Client,Shipping from t_salesweight where 1=?";
		Object[] param = new Object[] {1};
		return db.QueryToList(sql,param);
	}

	public Boolean update(int iD, double unitPrice, double quantity, double value, double client, double shipping) {
		DBUtil db = new DBUtil();
		String sql = "update t_salesweight set UnitPrice=?,Quantity=?,Value=?,Client=?,Shipping=? where ID=?";
		Object[] param = new Object[] {unitPrice,quantity,value,client,shipping,iD};
		return db.executeUpdate(sql, param)>0;
	}

}
