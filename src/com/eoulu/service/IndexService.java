/**
 * 
 */package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.entity.User;

/** @author  ���� : zhangkai
* @date ����ʱ�䣺2017��5��31�� ����11:17:35 
* @version 1.0  
* @since  
* @return  
*/
/**
* @author zhangkai
*
*/
public interface IndexService {

	/*
	 * ��ѯ�û���Ϣ�������û�ID
	 * 
	 * id   �û���id
	 * */
	public List<Map<String,Object>> getUserInfo(int id);
	
	/**
	 * �޸�����
	 * */
	public int modifyPassword(User user,String newPwd);
	
	
}
