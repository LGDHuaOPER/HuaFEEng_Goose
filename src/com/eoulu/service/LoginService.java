package com.eoulu.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.User;

public interface LoginService {

	/**
	 * 
	 * �ж��û���Ϣ�Ƿ���ȷ
	 * */
	public int toLoginByCode(User user, HttpServletRequest request, String code);
	
	/**
	 * 
	 * �ж��û���Ϣ�Ƿ���ȷ
	 * */
	public int toLogin(User user);
	
	/**
	 * �ж��û��Ƿ����
	 * */
	public boolean isExist(String username);
	
	
	
	
	
	/**
	 * ��ȡ�û��Ľ�ɫ����
	 * */
	public String[] getRole(String id);
	
	
	/**
	 * ��ȡ�û���Ȩ������
	 * */
	public String[] getAuthority(String id);
	
	
	/**
	 * ����Ȩ��ID�����ȡ���е�Ȩ������
	 * */
	public String[] getAuthorityName(String[] authority);
	
	/**
	 * �����û�ID��ȡ���е�Ȩ������
	 * */
	public String[] getAuthorityByID(String id);
	
	/**
	 * ����Ȩ����תҳ��
	 * */
	public void loginSuccess(String[] authority,HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException;
	
	public boolean setLastLogin(String userName);
	
	public String getUserRole(String userName);
	
}
