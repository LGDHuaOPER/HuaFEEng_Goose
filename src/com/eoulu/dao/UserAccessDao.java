package com.eoulu.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.eoulu.entity.UserAccess;
import com.eoulu.util.DBUtil;

public class UserAccessDao {

	public List<Map<String,Object>> getAllData(int userID){
		String sql =  "SELECT  AccessCount,JspName,ID FROM t_user_access WHERE UserID=? Order BY t_user_access.AccessCount DESC LIMIT 0,5";
		Object[] param = new Object[]{userID};
		return new DBUtil().QueryToList(sql, param);
	}
	
	public int getCounts(int userID,String jspName){
		String sql =  "SELECT  AccessCount FROM t_user_access WHERE UserID=? and JspName=? ";
		Object[] param = new Object[]{userID,jspName};
		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, param);
		return ls.size()>1?Integer.parseInt(ls.get(1).get("AccessCount").toString()):0;
	}
	
	public boolean insert(UserAccess user){
		String sql = "insert into t_user_access (JspName,AccessCount,UserID) values (?,?,?)";
		Object[] param = new Object[]{user.getJspName(),user.getAccessCount()+1,user.getUserID()};
		return new DBUtil().executeUpdate(sql, param)>0?true:false;
	}
	public boolean update(UserAccess user){
		String sql = "update t_user_access set AccessCount=?  where UserID=? and JspName=?";
		Object[] param = new Object[]{user.getAccessCount()+1,user.getUserID(),user.getJspName()};
		return new DBUtil().executeUpdate(sql, param)>0?true:false;
	}
}
