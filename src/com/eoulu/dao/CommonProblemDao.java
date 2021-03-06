package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.DocumentUpload;
import com.eoulu.util.DBUtil;

public class CommonProblemDao {

	public List<Map<String,Object>> getAllData(Page page,String column,String order){
		DBUtil db = new DBUtil();
		String sql = "SELECT ID,FileName,Content,Author,OperatingTime,DocuType from t_document_upload WHERE Type='CommonProblem' ORDER BY "+column+" "+order+" LIMIT ?,?";
		Object[] param = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;
	}
	public int getAllCounts(){
		DBUtil db = new DBUtil();
		String sql = "SELECT count(ID) from t_document_upload WHERE Type='CommonProblem'";
		Object[] param = null;
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		int count = 0;
		if(ls.size()>1){
			count = Integer.parseInt(ls.get(1).get("count(ID)").toString());
		}
		return count;
	}
	public List<Map<String,Object>> queryAllData(Page page,String content,String column,String order){
		System.out.println("content---"+content);
		DBUtil db = new DBUtil();
		String sql = "SELECT ID,FileName,Content,Author,OperatingTime,DocuType from t_document_upload WHERE Type='CommonProblem' and FileName like ? ORDER BY "+column+" "+order+" LIMIT ?,?";
		Object[] param = new Object[]{content,(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;
	}
	public int queryAllCounts(String content){
		DBUtil db = new DBUtil();
		String sql = "SELECT count(ID) from t_document_upload WHERE Type='CommonProblem' and FileName like ? ";
		Object[] param = new Object[]{content};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		int count = 0;
		if(ls.size()>1){
			count = Integer.parseInt(ls.get(1).get("count(ID)").toString());
		}
		return count;
	}
	
	
	
	public boolean insert(DocumentUpload doc){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		String sql = "insert into t_document_upload (FileName,FilePath,Type,Content,Author,OperatingTime,DocuType) values (?,?,?,?,?,?,?)";
		Object[] param = new Object[7];
		param[0] = doc.getFileName();
		param[1] = doc.getFilePath();
		param[2] = "CommonProblem";
		param[3] = doc.getContent();
		param[4] = doc.getAuthor();
		param[5] = df.format(new Date());
		param[6] = doc.getDocuType();
		int count = db.executeUpdate(sql, param);
		boolean flag = false;
		if(count>=1){
			flag = true;
		}
		return flag;
	}
	
	public boolean delete(String fileName) {

		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[] { fileName };
		String sql = "delete from t_document_upload where FileName=? and Type='CommonProblem'";

		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}
	public String getCommonProblemByID(int id) {
		DBUtil db = new DBUtil();
		String sql = "select FilePath from t_document_upload where ID=?";
		Object[] parameter = new Object[] { id };
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		String path = "";
		if(ls.size()>1){
			path = ls.get(1).get("FilePath").toString();
		}
		return path;
	}
	public boolean update(DocumentUpload doc) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		String sql = "update t_document_upload set FileName=?,FilePath=?,Type=?,Content=?,Author=?,"
						+ "OperatingTime=?,DocuType=? where ID = ?";
		Object[] param = new Object[8];
		
		param[0] = doc.getFileName();
		param[1] = doc.getFilePath();
		param[2] = "CommonProblem";
		param[3] = doc.getContent();
		param[4] = doc.getAuthor();
		param[5] = df.format(new Date());
		param[6] = doc.getDocuType();
		param[7] = doc.getID();
		int count = db.executeUpdate(sql, param);
		boolean flag = false;
		if(count>=1){
			flag = true;
		}
		return flag;
	}
	
}
