package com.eoulu.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.dao.UserDao;
import com.eoulu.entity.User;
import com.eoulu.service.LoginService;
import com.eoulu.util.DBUtil;

public class LoginServiceImpl implements LoginService {

	@Override
	public int toLoginByCode(User user,HttpServletRequest request,String code) {

		UserDao ud = new UserDao();
		User localUser = ud.getUser(user);
		int flag = 0;
		Object session = request.getSession().getAttribute("code");

		if(session!=null){
			String localCode = session.toString();
			if(localCode.equals(code)){
				if(localUser==null){
					flag = 0;
				}
				else if(user.getUserPwd().equals(localUser.getUserPwd())){
					flag = 1;
				}else if(!user.getUserPwd().equals(localUser.getUserPwd())){
					flag = 2;
				}
			}else{
				flag = 3;
			}
		}




		return flag;
	}

	/**
	 * �ж��û��Ƿ��¼�ɹ����û������ڷ���0���û�������ȷ�����û�ID������ȷ����-1
	 * */
	@Override
	public int toLogin(User user) {

		UserDao ud = new UserDao();
		User localUser = ud.getUser(user);
		int flag = 0;

		
		if(null==localUser){
			flag = 0;
		}else if(user.getUserPwd().equals(localUser.getUserPwd())){
			flag = localUser.getID();
		}else if(!user.getUserPwd().equals(localUser.getUserPwd())){
			flag = -1;
		}

		return flag;
	}

	@Override
	public boolean isExist(String username) {
		return new UserDao().isExist(username);
	}

	/**
	 * ��ȡ�û���Ȩ������
	 * */
	@Override
	public String[] getRole(String id) {

		String sql = "select Role from t_user_role where UserID=?";
		Object[] parameter = new Object[]{id};
		DBUtil db = new DBUtil();
		
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		String[] role = ls.get(1).get("Role").toString().split(",");
		
		return role;
	}
	
	
	
	
	

	/**
	 * ���ݽ�ɫ����ȡȨ����id������Ȩ�޴�ȫid
	 * */
	@Override
	public String[] getAuthority(String id) {

		DBUtil db = new DBUtil();
		String sql = "select * from t_user_authority where UserID = ? ";
		Object[] parameter = new Object[]{Integer.parseInt(id)};
		
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
			
		String authority = ls.get(1).get("Authority").toString();
		
		return authority.toString().split(",");
	}

	/**
	 * ����Ȩ��id������Ȩ�޵�����������
	 * */
	@Override
	public String[] getAuthorityName(String[] authority) {
		StringBuffer authoritybuffer = new StringBuffer();
		String sql = "select * from t_authority where ";
		DBUtil db = new DBUtil();
		int length = authority.length;
		for(int i=0; i<length; i++){
			sql+="ID=? ";
			if(i<length-1){
				sql+="or ";
			}
		}
		Object[] parameter = authority;
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		length = ls.size();
		for(int i=1; i<length; i++){
			
			authoritybuffer.append(ls.get(i).get("ReqUrl")+",");
			
		}
		
		
		return authoritybuffer.toString().split(",");
	}

	
	/**
	 * �����û�ID��ȡ���е�Ȩ������
	 * */
	public String[] getAuthorityByID(String id){
		String[] str = getAuthority(id);
		return getAuthorityName(str);
	}

	
	/**
	 * �����û�Ȩ�޾�����ת��ҳ��
	 * @throws IOException 
	 * @throws ServletException 
	 * */
	@Override
	public void loginSuccess(String[] authority, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		String url = null;
		boolean flag = true;
		
		
		request.getRequestDispatcher("Index").forward(request, response);
		
	}

	@Override
	public boolean setLastLogin(String userName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastLogin = sdf.format(new Date());
		
		return new UserDao().setLastLogin(lastLogin, userName);
	}

	@Override
	public String getUserRole(String userName) {
		
		List<Map<String, Object>> list = new UserDao().getUserRole(userName);
		String role = list.get(1).get("Role").toString();
		return role;
	}
	
	
	
	
	
}
