package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.entity.MailConfig;
import com.eoulu.util.DBUtil;

public class MailConfigDao {
	
	public List<Map<String,Object>> getConfig(String page){
		DBUtil dbUtil = new DBUtil();
		String sql = "select ToList,CopyList from t_mail_config where Page = ?";
		
		return dbUtil.QueryToList(sql, new Object[]{page});
	}
	
	public boolean modifyConfig(MailConfig config){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_mail_config set ToList = ?,CopyList = ? where Page = ?";
		Object[] param = new Object[3];
		param[0] = config.getToList();
		param[1] = config.getCopyList();
		param[2] = config.getPage();
		
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
	}

}
