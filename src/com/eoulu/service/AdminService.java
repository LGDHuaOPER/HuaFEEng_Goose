/**
 * 
 */
package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.xmlbeans.impl.jam.mutable.MPackage;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Authority;
import com.eoulu.entity.Role;
import com.eoulu.entity.User;
import com.eoulu.entity.UserRole;

/**
 * @author zhangkai
 *
 * ����Ա����Ľӿ�
 */
public interface AdminService {

	/**
	 * ��ѯ�û���Ϣ
	 * */
	public List<Map<String,Object>> getAllUser();
	
	
	public List<Map<String,Object>> query(String queryType,String content,Page page);
	
	
	public List<Map<String,Object>> getUserByPage(Page page);
	
	
	public int getUserCount();
	
	public int getQueryCount(String queryType,String content);
	
	/**
	 * �޸��û���Ϣ
	 * 
	 * @return 1�޸ĳɹ�   0�޸�ʧ��
	 * */
	public boolean modifyUser(User user);
	
	
	
	
	/**
	 * ɾ���û���Ϣ
	 * 
	 * @return 1�޸ĳɹ�   0�޸�ʧ��
	 * */
	public boolean deleteUser(int id);
	
	
	/**
	 * �����û���Ϣ
	 * 
	 * @return 1�޸ĳɹ�   0�޸�ʧ��
	 * */
	public boolean deleteBatch(int[] idList);
	
	
	public boolean insertUser(User user);
	
	
	/**
	 * ����Ȩ��
	 * */
	public boolean insertAuthority(Authority authority);
	
	
	/**
	 * ��ȡ���е�Ȩ����Ϣ
	 * */
	public List<Map<String,Object>> getAllAuthority();
	
	
	/**
	 * ��ȡ���еĽ�ɫ��Ϣ
	 * */
	public List<Map<String,Object>> getAllRole();
	
	
	/**
	 * ������ɫ��Ϣ
	 * */
	public boolean insertRole(Role role);
	
	
	/**
	 * ���ս�ɫ��ѯȨ������ID
	 * */
	public String[] getAuthoritiesIDByRoleID(int id);
	
	
	
	/**
	 * ����Ȩ������ID��ѯ��Ȩ������
	 * */
	public List<Map<String,Object>> getAuthoritiesByID(String[] id);
	
	
	/**
	 * ���ս�ɫID��ѯ����Ӧ��ɫ��Ȩ�����飨���ģ�
	 * */
	public List<Map<String,Object>> getAuthoritiesByRoleID(int id);
	
	/**
	 * ��ɫȨ����Ϣ���޸�
	 * */
	public boolean modifyRole(Role role);
	
	/**
	 * �����û�ID����ѯ�����û��Ľ�ɫ����
	 * */
	public String[] getRolesIDByUserID(int id);
	
	/**
	 * �޸��û��Ľ�ɫ��Ϣ
	 * */
	public boolean modifyUserRole(UserRole userRole);
	
	
	/**
	 * ��ѯ�������û��Ľ�ɫ��Ϣ
	 * */
	public List<Map<String,Object>> getAllUserRole();
	
	
	public String grantAuthority(HttpServletRequest request);
	
	public List<Map<String,Object>> getUserAuthority(String userName);
	
	public String getAuthorityNameByID(int id);
	
	
}
