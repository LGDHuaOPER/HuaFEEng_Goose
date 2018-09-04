package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.util.DBUtil;

public class FormfactoryfilesDao {

	public boolean insert(String Path, int ID, int Type) {
		DBUtil db2 = new DBUtil();
		String sql2 = "delete from t_formfactoryfiles where FactroyID=? and Type=? ";
		Object[] param2 = new Object[] {ID,Type};
		db2.executeUpdate(sql2, param2);
		DBUtil db = new DBUtil();
		String sql = "insert into t_formfactoryfiles (FileName,Type,FactroyID) values(?,?,?)";
		Object[] param = new Object[] {Path,Type,ID};
		return db.executeUpdate(sql, param)>0?true:false;
	}

	public String getFilePath(int ID, int Type) {
		DBUtil db = new DBUtil();
		String sql = "select FileName from t_formfactoryfiles where FactroyID =? and Type=?";
		Object[] param = new Object[] {ID,Type};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls.size()>1?ls.get(1).get("FileName").toString():"no";
	}

}
