package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.InstallationManual;
import com.eoulu.util.DBUtil;

public class InstallationManualDao {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getInstallationManual(Page page, String area,String type) {

		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();

		String sql = "select ID,FileName,FilePath,Area from t_document_upload where  Type=? and Area=? order by FileName desc limit ?,?";
		Object[] parameter = new Object[] { type,area, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 总数量
	 * 
	 * @return
	 */
	public int getAllCounts(String area,String type) {
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_document_upload where  Type=? and Area=? order by FileName desc";

		Object[] parameter = new Object[] { "AllCounts", type,area };
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);

		if (ls.size() > 1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());

		return counts;
	}

	/**
	 * 搜索
	 * @param page
	 * @param area
	 * @param content
	 * @return
	 */
	public List<Map<String, Object>> queryInstallationManual(Page page, String area,String content,String type) {

		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();

		String sql = "select ID,FileName,FilePath,Area from t_document_upload where  Type=? and Area=? and FileName like ? order by FileName desc limit ?,?";
		Object[] parameter = new Object[] { type,area,content, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	public int queryAllCounts(String area,String content,String type) {
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_document_upload where  Type=? and Area=? and FileName like ? order by FileName desc";

		Object[] parameter = new Object[] { "AllCounts", type, area ,content};
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
	public boolean insert(InstallationManual manual) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[5];
		String sql = "insert into t_document_upload (FileName,FilePath,Area,OperatingTime,Type) values (?,?,?,?,?)";
		parameter[0] = manual.getFileName();
		parameter[1] = manual.getFilePath();
		parameter[2] = manual.getArea();
		parameter[3] = df.format(new Date());
		parameter[4] = manual.getType();

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

	public List<Map<String, Object>> getInstallationManualByID(int id) {

		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();

		String sql = "select ID,FileName,FilePath,Area from t_document_upload where ID=?";
		Object[] parameter = new Object[] { id };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	public boolean delete(String fileName,String type) {

		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[] { fileName,type };
	
		String sql = "delete from t_document_upload where FileName=? and  Type=?";

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
	
	
	public List<Map<String, Object>> getSearch(Page page, String content) {

		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();

		String sql = "select ID,FileName from t_document_upload where  FileName like ? order by ID desc limit ?,?";
		Object[] parameter = new Object[] { "%"+content+"%", (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	public int getSearchCounts(Page page, String content) {

		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();

		String sql = "select count(ID) from t_document_upload where  FileName like ? ";
		Object[] parameter = new Object[] { "%"+content+"%"};

		ls = db.QueryToList(sql, parameter);
		int count = 0;
		if(ls.size()>1){
			count = Integer.parseInt(ls.get(1).get("count(ID)").toString());
		}
		return count;
	}

	
	
}
