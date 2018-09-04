/**
 * 
 */
package com.eoulu.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.eoulu.entity.UserRole;
import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 *t_user_role��Ĳ�����
 */
public class UserRoleDao {
	
	/**
	 * 用户角色的添加（User的id，与Role的id）
	 * @param userRole
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	public int insert(UserRole userRole,DBUtil db) throws SQLException{
		String sql = "insert into t_user_role (UserID,Role) values (?,?)";

		Object[] parameter = new Object[2];
		parameter[0] = userRole.getUserID();
		parameter[1] = userRole.getRole();
		
		
		return db.executeUpdateNotClose(sql, parameter);
		
	}
	
	
	/**
	 * 查询出所有的用户角色
	 * @return
	 */
	public List<Map<String,Object>> getAllUserRole(){
		String sql = "select * from t_user left join t_user_role on t_user.ID=t_user_role.UserID where UserID>0";
		DBUtil db = new DBUtil();
		
		
		return db.QueryToList(sql, new Object[0]);
	}
	
}
