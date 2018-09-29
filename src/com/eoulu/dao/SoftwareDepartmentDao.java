package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.SoftwareDepartment;
import com.eoulu.util.DBUtil;

public class SoftwareDepartmentDao {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getSoftwareDepartment(Page page, String type,String order) {

		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();

		String sql = "select ID,FileName,FilePath,Type,OperatingTime from t_document_upload where Type=? order by FileName "+order+" limit ?,?";
		Object[] parameter = new Object[] { type, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 总数量
	 * 
	 * @return
	 */
	public int getAllCounts(String type) {
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_document_upload where Type=?";

		Object[] parameter = new Object[] { "AllCounts", type };
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);

		if (ls.size() > 1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());

		return counts;
	}
	public List<Map<String, Object>> querySoftwareDepartment(Page page, String type,String content,String order) {

		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();

		String sql = "select ID,FileName,FilePath,Type,OperatingTime from t_document_upload where Type=? and FileName like ? order by FileName "+order+" limit ?,?";
		Object[] parameter = new Object[] { type,content, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	public int queryAllCounts(String type,String content) {
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_document_upload where Type=? and FileName like ? ";

		Object[] parameter = new Object[] { "AllCounts", type ,content};
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
	public boolean insert(SoftwareDepartment report) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[4];
		String sql = "insert into t_document_upload (FileName,FilePath,Type,OperatingTime) values (?,?,?,?)";
		parameter[0] = report.getFileName();
		parameter[1] = report.getFilePath();
		parameter[2] = "Software";
		parameter[3] = df.format(new Date());

		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	public List<Map<String, Object>> getSoftwareDepartmentByID(int id) {

		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();

		String sql = "select ID,FileName,FilePath,Type,OperatingTime from t_document_upload where ID=?";
		Object[] parameter = new Object[] { id };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	public boolean delete(String fileName) {

		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[] { fileName };
		String sql = "delete from t_document_upload where FileName=? and Type='Software'";

		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	public String getRealName(String account){
		DBUtil db = new DBUtil();
		String sql = "SELECT RealName FROM t_user WHERE UserName=?";
		Object[] param = new Object[]{account};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		String realName = "";
		if(ls.size()>1){
			realName = ls.get(1).get("RealName").toString();
		}
		return realName;
	}
	
}
