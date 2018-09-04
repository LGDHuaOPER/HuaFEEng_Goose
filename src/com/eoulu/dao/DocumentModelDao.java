package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.entity.DocumentModel;
import com.eoulu.util.DBUtil;

public class DocumentModelDao {

	public boolean add(DocumentModel model){
		boolean flag ;
		DBUtil db = new DBUtil();
		String sql =  "insert into t_document_model (filePath,fileName,type) values (?,?,?)";
		Object[] parameter = new Object[3];
		parameter[0] = model.getFilePath();
		parameter[1] = model.getFileName();
		parameter[2] = model.getType();
		
		int result = db.executeUpdate(sql, parameter);

		flag = result>0?true:false;

		return flag;
		
	}
	
	public List<Map<String, Object>> getFileByName(String name){
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();

		String sql = "select filePath from t_document_model where fileName=?";
		Object[] parameter = new Object[1];
		parameter[0] = name;
		ls = db.QueryToList(sql, parameter);
		
		return ls;
	}
	
	public List<Map<String, Object>> getAllName(){
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		
		String sql = "select  fileName from t_document_model";
		Object[] parameter = null;
		ls = db.QueryToList(sql, parameter);
		
		return ls;
	}
}
