/**
 * 
 */
package com.eoulu.entity;

/**
 * @author zhangkai
 *
 *@date 2017/3/21
 *
 *�û���ɫ��ʵ����  user_role
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
	 * ��ɫ������Ȩ�޵ļ��ϣ��Զ�����Ϊ�ָ���
	 * */
	public void setRole(String role) {
		Role = role;
	}
	
	
}
