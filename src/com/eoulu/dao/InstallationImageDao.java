package com.eoulu.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.InstallationImage;
import com.eoulu.entity.InstallationReportLog;
import com.eoulu.util.DBUtil;

public class InstallationImageDao {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getInstallationImage(Page page) {

		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();

		String sql = "select ID,FileName,FilePath from t_document_upload where Type='InstallationImage'  order by FileName desc limit ?,?";
		Object[] parameter = new Object[] { (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 总数量
	 * 
	 * @return
	 */
	public int getAllCounts() {
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_document_upload where Type='InstallationImage' ";

		Object[] parameter = new Object[] { "AllCounts" };
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);

		if (ls.size() > 1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());

		return counts;
	}

	/**
	 * 搜索
	 * @param page
	 * @param content
	 * @return
	 */
	public List<Map<String, Object>> queryInstallationImage(Page page,String content) {

		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();

		String sql = "select ID,FileName,FilePath from t_document_upload where Type='InstallationImage' and FileName like ? order by FileName desc limit ?,?";
		Object[] parameter = new Object[] { content,(page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	public int queryAllCounts(String content) {
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_document_upload where Type='InstallationImage' and FileName like ? ";

		Object[] parameter = new Object[] { "AllCounts" ,content};
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
	public boolean insert(InstallationImage image) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[4];
		String sql = "insert into t_document_upload (FileName,FilePath,OperatingTime,Type) values (?,?,?,?)";
		parameter[0] = image.getFileName();
		parameter[1] = image.getFilePath();
		parameter[2] = df.format(new Date());
		parameter[3] = "InstallationImage";

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

	public List<Map<String, Object>> getInstallationImageByID(int id) {

		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();

		String sql = "select ID,FileName,FilePath from t_document_upload where ID=?";
		Object[] parameter = new Object[] { id };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	public boolean delete(String fileName) {

		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[] { fileName };
		String sql = "delete from t_document_upload where FileName=? and"
				+ " Type='InstallationImage'";

		int i = 0;
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		}
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

}
