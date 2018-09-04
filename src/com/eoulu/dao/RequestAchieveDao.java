package com.eoulu.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.InstallationManual;
import com.eoulu.entity.RequestAchieve;
import com.eoulu.util.DBUtil;

public class RequestAchieveDao {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getRequestAchieve(Page page, String area) {

		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();

		String sql = "select ID,FileName,FilePath,Area from t_document_upload where Type='RequestAchieve' and Area=? order by FileName desc limit ?,?";
		Object[] parameter = new Object[] { area, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 总数量
	 * 
	 * @return
	 */
	public int getAllCounts(String area) {
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_document_upload where Type='RequestAchieve' and Area=? ";

		Object[] parameter = new Object[] { "AllCounts", area };
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);

		if (ls.size() > 1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());

		return counts;
	}
	
	public List<Map<String, Object>> queryRequestAchieve(Page page, String area,String content) {

		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();

		String sql = "select ID,FileName,FilePath,Area from t_document_upload where Type='RequestAchieve' and Area=? and FileName like ? order by FileName desc limit ?,?";
		Object[] parameter = new Object[] { area,content, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	public int queryAllCounts(String area,String content) {
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_document_upload where Type='RequestAchieve' and Area=? and FileName like ?";

		Object[] parameter = new Object[] { "AllCounts", area ,content};
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
	public boolean insert(RequestAchieve report) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[5];
		String sql = "insert into t_document_upload (FileName,FilePath,Area,OperatingTime,Type) values (?,?,?,?,?)";
		parameter[0] = report.getFileName();
		parameter[1] = report.getFilePath();
		parameter[2] = report.getArea();
		parameter[3] = df.format(new Date());
		parameter[4] = "RequestAchieve";

		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	public List<Map<String, Object>> getRequestAchieveByID(int id) {

		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();

		String sql = "select ID,FileName,FilePath,Area from t_document_upload where ID=?";
		Object[] parameter = new Object[] { id };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	public boolean delete(String fileName) {

		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[] { fileName };
		String sql = "delete from t_document_upload where FileName=? and Type='RequestAchieve'";

		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}
}
