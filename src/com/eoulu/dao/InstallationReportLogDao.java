package com.eoulu.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.InstallationReportLog;
import com.eoulu.util.DBUtil;

public class InstallationReportLogDao {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getInstallationReportLog(Page page, String area, String year) {

		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();

		System.out.println(area + "--" + year);
		String sql = "select ID,FileName,FilePath,Area,Year from t_document_upload where Type='InstallationReportLog' and Area=? and Year=? order by FileName desc limit ?,?";
		Object[] parameter = new Object[] { area, year, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 总数量
	 * 
	 * @return
	 */
	public int getAllCounts(String area, String year) {
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_document_upload where Type='InstallationReportLog' and Area=? and Year=?  order by FileName desc";

		Object[] parameter = new Object[] { "AllCounts", area, year };
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);

		if (ls.size() > 1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());

		return counts;
	}

	public List<Map<String, Object>> queryInstallationReportLog(Page page, String area, String year,String content) {

		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();

		System.out.println(area + "--" + year);
		String sql = "select ID,FileName,FilePath,Area,Year from t_document_upload where Type='InstallationReportLog' and Area=? and Year=? and FileName like ? order by FileName desc limit ?,?";
		Object[] parameter = new Object[] { area, year,content, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	public int queryAllCounts(String area, String year,String content) {
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_document_upload where Type='InstallationReportLog' and Area=? and Year=? and FileName like ? ";

		Object[] parameter = new Object[] { "AllCounts", area, year,content };
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);

		if (ls.size() > 1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());

		return counts;
	}

	
	/**
	 * 添加
	 * 
	 * @param invoice
	 * @param db
	 * @return
	 */
	public boolean insert(InstallationReportLog log) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[6];
		String sql = "insert into t_document_upload (FileName,FilePath,Area,Year,OperatingTime,Type) values (?,?,?,?,?,?)";
		parameter[0] = log.getFileName();
		parameter[1] = log.getFilePath();
		parameter[2] = log.getArea();
		parameter[3] = log.getYear();
		parameter[4] = df.format(new Date());
		parameter[5] = "InstallationReportLog";
		int i = 0;
		i = db.executeUpdate(sql, parameter);
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		}
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	public List<Map<String, Object>> getInstallationReportLogByID(int id) {

		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();

		String sql = "select ID,FileName,FilePath,Area,Year from t_document_upload where ID=?";
		Object[] parameter = new Object[] { id };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	public boolean delete(String fileName) {

		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[] { fileName };
		String sql = "delete from t_document_upload where FileName=? and Type='InstallationReportLog'";

		int i = 0;
		i = db.executeUpdate(sql, parameter);
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		}
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}
}
