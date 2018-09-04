/**
 * 
 */
package com.eoulu.entity;

/**
 * @author zhangkai
 *
 *@date 2017/3/21
 *
 *用户角色表实体类  user_role
 */
public class UserRole {

	private int UserID;
	private String Role;
	
	
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getRole() {
		return Role;
	}
	
	/**
	 * 角色里面是权限的集合，以逗号作为分隔符
	 * */
	public void setRole(String role) {
		Role = role;
	}
	
	
}
