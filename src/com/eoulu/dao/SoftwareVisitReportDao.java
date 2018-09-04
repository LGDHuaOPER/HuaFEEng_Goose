package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.VisitReport;
import com.eoulu.util.DBUtil;

public class SoftwareVisitReportDao {

	public List<Map<String, Object>> getAllData(Page page, String content, String year) {
		DBUtil db = new DBUtil();

		String sql = "SELECT ID,FileName,FilePath,`Year` FROM t_document_upload where Type='VisitReport' ";
		Object[] param = null;
		if(year.equals("ALL") &&  content == null){
			sql += " limit ?,?";
			param = new Object[] { (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		}
		if (year.equals("ALL") && content != null) {
			sql = "SELECT ID,FileName,FilePath,`Year` FROM t_document_upload where Type='VisitReport' and ";
			sql += "FileName like ?  limit ?,?";
			param = new Object[] {  content, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		}
		
		if (!year.equals("ALL") && content != null) {
			sql = "SELECT ID,FileName,FilePath,`Year` FROM t_document_upload where Type='VisitReport' and  Year=? and ";
			sql += "FileName like ?  limit ?,?";
			param = new Object[] { year, content, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		}

		if (!year.equals("ALL") && content == null) {
			sql = "SELECT ID,FileName,FilePath,`Year` FROM t_document_upload where Type='VisitReport' and  Year=? ";
			sql += "limit ?,?";
			param = new Object[] { year, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		}
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public int getCounts(String content, String year) {

		DBUtil db = new DBUtil();
		String sql = "SELECT count(ID) FROM t_document_upload where Type='VisitReport' ";
		Object[] param = null;
		
		if (year.equals("ALL") && content != null) {
			sql += " and FileName like ? ";
			param = new Object[] {  content};
		}
		
		if (!year.equals("ALL") && content != null) {
			sql += "and  Year=? and FileName like ?  ";
			param = new Object[] { year, content};
		}
		if (!year.equals("ALL") && content == null) {
			sql += "and  Year=? ";
			param = new Object[] { year };
		}
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		int count = 0;
		if (ls.size() > 1) {
			count = Integer.parseInt(ls.get(1).get("count(ID)").toString());
		}

		return count;
	}

	public String getFileName(int id) {
		DBUtil db = new DBUtil();
		String sql = "SELECT FilePath FROM t_document_upload where ID=?";
		Object[] param = new Object[] { id };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		String path = "";
		if (ls.size() > 1) {
			path = ls.get(1).get("FilePath").toString();
		}
		return path;
	}

	public boolean insert(VisitReport report) {
		boolean flag = false;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		String sql = "insert into t_document_upload (FileName,FilePath,Year,OperatingTime,Type) values (?,?,?,?,?)";
		Object[] param = new Object[5];
		param[0] = report.getFileName();
		param[1] = report.getFileName();
		param[2] = report.getYear();
		param[3] = df.format(new Date());
		param[4] = "VisitReport";

		int count = db.executeUpdate(sql, param);
		if (count > 0) {
			flag = true;
		}
		return flag;
	}

	public boolean delete(String fileName) {

		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[] { fileName };
		String sql = "delete from t_software_visit_report where FileName=?";
		int i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}
}
