package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.entity.CommodityInfo;
import com.eoulu.util.DBUtil;

public class ItemTestDao {

	public List<Map<String, Object>> getAllDatas() {
		String sql = "select * from t_item_test";
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, null);
		return ls;

	}

	public boolean update(CommodityInfo info) {
		String sql = "update t_commodity_info SET Item=?,ItemDescription=? WHERE Model=?";
		DBUtil db = new DBUtil();
		Object[] param = new Object[3];
		param[0] = info.getItem();
		param[1] = info.getItemDescription();
		param[2] = info.getModel();
		int count = db.executeUpdate(sql, param);
		boolean flag = false;
		if (count > 0) {
			flag = true;
		}
		return flag;
	}

}
