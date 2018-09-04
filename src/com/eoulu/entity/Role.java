/**
 * 
 */
package com.eoulu.entity;

/**
 * @author zhangkai
 *
 *@date 2017/3/21
 *
 *角色信息表实体类  role
 */
public class Role {

	private int ID;
	private String Name;
	private String Authority;
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getAuthority() {
		return Authority;
	}
	public void setAuthority(String authority) {
		Authority = authority;
	}
	
	
}
