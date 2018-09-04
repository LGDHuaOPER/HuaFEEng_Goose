package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.entity.VersionManagement;
import com.eoulu.util.DBUtil;

public class VersionManagementDao {
	
	public List<Map<String, Object>> getData(String ProjectName){
		DBUtil dbUtil = new DBUtil();
		String sql = "select ID,ProjectName,Version,Registrant,BoardingTime,UpdatedContent "
				+ "from t_version_management where ProjectName = ?";
		
		return dbUtil.QueryToList(sql, new Object[]{ProjectName});
	}
	
	public boolean addVersion(VersionManagement version){
		DBUtil dbUtil = new DBUtil();
		String sql = "insert into t_version_management(ProjectName,Version,Registrant,BoardingTime,"
				+ "UpdatedContent) values(?,?,?,?,?)";
		Object[] param = new Object[5];
		param[0] = version.getProjectName();
		param[1] = version.getVersion();
		param[2] = version.getRegistrant();
		param[3] = version.getBoardingTime();
		param[4] = version.getUpdatedContent();
		
		int result = dbUtil.executeUpdate(sql, param);
		return result>0?true:false;
		
	}
	
	

}
