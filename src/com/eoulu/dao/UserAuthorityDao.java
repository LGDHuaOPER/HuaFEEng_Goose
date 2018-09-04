package com.eoulu.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.eoulu.entity.UserAuthority;
import com.eoulu.util.DBUtil;

public class UserAuthorityDao {
	
	public int insert(UserAuthority authority,DBUtil db) throws SQLException{
		String sql = "insert into t_user_authority (UserID,Authority) values (?,?)";
		Object[] param = new Object[]{authority.getUserID(),authority.getAuthority()};
		return db.executeUpdateNotClose(sql, param);
		
	}
	
	public boolean update(UserAuthority authority){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_user_authority set Authority = ? where UserID = ?";
		Object[] param = new Object[]{authority.getAuthority(),authority.getUserID()};
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;		
	}
	
	public List<Map<String, Object>> getUserAuthority(int userID){
		DBUtil dbUtil = new DBUtil();
		String sql = "select * from t_user_authority where UserID = ?";
		Object[] param = new Object[]{userID};
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, param);
		return list;
	}

}
