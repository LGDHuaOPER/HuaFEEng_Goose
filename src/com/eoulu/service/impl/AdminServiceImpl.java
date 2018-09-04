/**
 * 
 */
package com.eoulu.service.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import com.eoulu.commonality.Page;
import com.eoulu.dao.AuthorityDao;
import com.eoulu.dao.RoleDao;
import com.eoulu.dao.UserAuthorityDao;
import com.eoulu.dao.UserDao;
import com.eoulu.dao.UserRoleDao;
import com.eoulu.entity.Authority;
import com.eoulu.entity.Role;
import com.eoulu.entity.User;
import com.eoulu.entity.UserAuthority;
import com.eoulu.entity.UserRole;
import com.eoulu.service.AdminService;
import com.eoulu.util.DBUtil;
import com.eoulu.util.MD5Util;





/**
 * @author zhangkai
 *
 */
public class AdminServiceImpl implements AdminService {

	@Override
	public List<Map<String, Object>> getAllUser() {

		return new UserDao().getAllUser();
	}

	@Override
	public boolean modifyUser(User user) {
		// TODO Auto-generated method stub
		return new UserDao().modify(user);
	}

	@Override
	public boolean deleteUser(int id) {
		// TODO Auto-generated method stub
		return new UserDao().delete(id);
	}
	
	public boolean deleteBatch(int[] idList){
		boolean flag = true;
		DBUtil util = new DBUtil();
		Connection conn = util.getConnection();
		PreparedStatement statement = null;
		String sql = "delete from t_user where ID = ? ";
		try {
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			for(int i = 0;i < idList.length;i ++){
				statement.setInt(1,idList[i]);
				statement.addBatch();
			}
			statement.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				flag = false;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * �����µ��û���Ϣ
	 * */
	@Override
	public boolean insertUser(User user) {
		user.setUserPwd(new MD5Util().md5ToString("EOULU2017"));
		boolean flag = false;

		DBUtil db = new DBUtil();

		try{
			db.getConnection().setAutoCommit(false);

			new UserDao().insert(user,db);

			UserAuthority userAuthority = new UserAuthority();
			userAuthority.setUserID(new UserDao().queryUser(user,db));

			new UserAuthorityDao().insert(userAuthority, db);

			db.getConnection().commit();
			//			System.out.println("�û������ɹ�");
			flag = true;
			db.getConnection().setAutoCommit(true);
		}catch(Exception e){
			e.printStackTrace();
			try {
				db.getConnection().rollback();
				//				System.out.println("�û�����ʧ��");
				db.getConnection().setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}



		return flag;
	}

	//	@Test
	//	public void test(){
	//
	//		User user = new User();
	//		user.setUserName("����");
	//		System.out.println(insertUser(user));
	//
	//	}
	/**
	 * ����Ȩ����Ϣ
	 * 
	 * @return true �����ɹ�  false ����ʧ��
	 * */
	@Override
	public boolean insertAuthority(Authority authority) {

		AuthorityDao authorityDao = new AuthorityDao();
		boolean flag = false,allowInsert=false;
		List<Map<String,Object>> ls = authorityDao.getAuthorityID(authority);

		if(ls.size()>1)
			allowInsert = true;

		if(allowInsert){
			String sql = "insert into t_authority (Name) values (?)";
			Object[] parameter = new Object[]{authority.getName()};
			flag = authorityDao.executeUpdate(sql, parameter);
		}



		return flag;
	}


	/**
	 * ��ȡ���е�Ȩ����Ϣ
	 * */
	@Override
	public List<Map<String,Object>> getAllAuthority(){

		return new AuthorityDao().getAllAuthority();
	}

	/**
	 * ��ȡ���еĽ�ɫ��Ϣ
	 * */
	@Override
	public List<Map<String,Object>> getAllRole(){

		List<Map<String,Object>> ls = new RoleDao().getAllRole();
		ls.get(0).put("col4", "AuthorityName");
		int length = ls.size();
		
		for(int i=1; i<length; i++){
			//�����ݿ��ѯ����Ӧ��ɫID��Ӧ��Ȩ������
			int id = Integer.parseInt(ls.get(i).get("ID").toString());
			String[] authority = getAuthoritiesIDByRoleID(id);
			StringBuffer mid = new StringBuffer();
			for(int m = 0;m < authority.length -1;m ++){
				mid.append(getAuthorityNameByID(Integer.parseInt(authority[m])));
				mid.append(",");
			}
			mid.append(getAuthorityNameByID(Integer.parseInt(authority[authority.length - 1])));
		
			ls.get(i).put("AuthorityName", mid.toString());
		}
		
		
		
		
		return ls;
	}
	
	public String getAuthorityNameByID(int id){
		return new AuthorityDao().getAuthorityNameByID(id);
	}



	/**
	 * ������ɫ��Ϣ
	 * */
	@Override
	public boolean insertRole(Role role){
		boolean flag = false;

		RoleDao rd = new RoleDao();
		Object[] parameter = new Object[]{role.getName(),role.getAuthority()};
		String sql = "insert into t_role (Name,Authority) values (?,?)";


		flag = rd.executeUpdate(sql, parameter);

		return flag;
	}



	/**
	 * ��ȡָ����ɫID�µ�Ȩ������ID
	 * */
	@Override
	public String[] getAuthoritiesIDByRoleID(int id) {
		String[] authority = null;

		if(new RoleDao().roleIsExist(id)){

			String sql = "select * from t_role where ID=?";
			DBUtil db = new DBUtil();
			Object[] parameter = new Object[]{id};

			List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
			String auth = ls.get(1).get("Authority").toString().trim();

			authority = auth.split(",");

		}else{
			authority = new String[0];
		}

		return authority;
	}



	/**
	 * ����Ȩ������ID��ѯ��Ȩ������
	 * */
	@Override
	public List<Map<String,Object>> getAuthoritiesByID(String[] id) {

		DBUtil db = new DBUtil();
		Object[] parameter = id;
		int length = parameter.length;
		String sql = "select * from t_authority where ";

		//sql���ƴ��
		for(int i=0; i<length; i++){
			sql += "ID=?";
			if(i<length-1)
				sql += " or ";
		}
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);


		return ls;
	}

	/**
	 * �޸Ľ�ɫ��Ϣ
	 * */
	@Override
	public boolean modifyRole(Role role) {
		String sql = "update t_role set Name=?,Authority=? where ID=?";
		RoleDao roleDao = new RoleDao();
		Object[] parameter = new Object[3];

		parameter[0] = role.getName();
		parameter[1] = role.getAuthority();
		parameter[2] = role.getID();

		boolean flag = roleDao.executeUpdate(sql, parameter);

		return flag;
	}

	/**
	 * ���ս�ɫID��ѯ����Ӧ��ɫ��Ȩ�����飨���ģ�
	 * */
	@Override
	public List<Map<String, Object>> getAuthoritiesByRoleID(int id) {

		String[] authority = getAuthoritiesIDByRoleID(id);
		List<Map<String, Object>> ls = new ArrayList<Map<String,Object>>();

		if(authority.length>0){
			ls = getAuthoritiesByID(authority);
		}

		return ls;
	}

	/**
	 * ��ȡ�û��Ľ�ɫ����
	 * */
	@Override
	public String[] getRolesIDByUserID(int id) {
		String[] authority = null;

		if(new RoleDao().roleIsExist(id)){

			String sql = "select * from t_user_role where UserID=?";
			DBUtil db = new DBUtil();
			Object[] parameter = new Object[]{id};

			List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
			String auth = ls.get(1).get("Role").toString().trim();

			authority = auth.split(",");

		}else{
			authority = new String[0];
		}

		return authority;
	}


	/**
	 * �޸��û��Ľ�ɫ��Ϣ
	 * */
	@Override
	public boolean modifyUserRole(UserRole userRole) {
		String sql = "update t_user_role set Role=? where UserID=?";

		DBUtil db = new DBUtil();

		Object[] parameter = new Object[2];

		parameter[0] = userRole.getRole();
		parameter[1] = userRole.getUserID();

		boolean flag = db.executeUpdate(sql, parameter)>0?true:false;

		return flag;
	}


	/**
	 * ��ȡ�����û��Ľ�ɫ��Ϣ
	 * */
	@Override
	public List<Map<String, Object>> getAllUserRole() {
		List<Map<String,Object>> ls = new UserRoleDao().getAllUserRole();
		ls.get(0).put("col3", "RoleName");
		int length = ls.size();
		
		for(int i=1; i<length; i++){
			
			String[] roleID = ls.get(i).get("Role").toString().split(",");
			List<Map<String,Object>> roleLS = getRoleName(roleID);
			
		    //����ѯ�����Ľ�ɫ������ƴ��
			int roleLength = roleLS.size();
			StringBuffer roleName = new StringBuffer();;
			for(int j=1; j<roleLength; j++){
			   roleName.append(roleLS.get(j).get("Name").toString());
			   if(j<roleLength-1)
				   roleName.append(",");
			}
			ls.get(i).put("RoleName",roleName.toString());
			
		}
		
		
		return ls;
	}
	
	/**
	 * ���ݽ�ɫID�����ѯ����ɫ������
	 * */
	public List<Map<String,Object>> getRoleName(String[] roleID){
		StringBuffer sql =  new StringBuffer("select * from t_role where");
		Object[] parameter = roleID;
		DBUtil db = new DBUtil();
		
		int length = roleID.length;
		
		for(int i=0; i<length; i++){
			sql.append(" ID=? ");
			if(i<length-1)
				sql.append(" or ");
		}
		
		return db.QueryToList(sql.toString(), parameter);
		
	}
	
	
	@Test
	public void test(){
		System.out.println(getAllRole());
	}

	@Override
	public List<Map<String, Object>> query(String queryType, String content,Page page) {
		UserDao dao = new UserDao();
		return dao.query(queryType, content,page);
	}

	@Override
	public List<Map<String, Object>> getUserByPage(Page page) {
		UserDao dao = new UserDao();
		return dao.getUserByPage(page);
	}

	@Override
	public int getUserCount() {
		UserDao dao = new UserDao();
		return dao.getUserCounts();
	}

	@Override
	public int getQueryCount(String queryType, String content) {
		UserDao dao = new UserDao();
		return dao.getQueryCounts(queryType, content);
	}

	@Override
	public String grantAuthority(HttpServletRequest request) {
		DBUtil db = new DBUtil();
		String authority = request.getParameter("Authority") == null?"":request.getParameter("Authority");
		String userName = request.getParameter("UserName") == null?"": request.getParameter("UserName");
		String role = new UserDao().getUserRole(userName).get(1).get("Role").toString();
		if(role.equals("管理员")
				||role.equals("超级管理员")){
			authority+=",7";
		}
	
		UserAuthority userAuthority = new UserAuthority();
		User user = new User();
		user.setUserName(userName);
		userAuthority.setUserID(new UserDao().queryUser(user,db));
		userAuthority.setAuthority(authority);
	
		
		return new UserAuthorityDao().update(userAuthority)==true?"授权成功！":"授权失败！";
	}

	@Override
	public List<Map<String, Object>> getUserAuthority(String userName) {
		DBUtil db = new DBUtil();
		UserAuthorityDao dao = new UserAuthorityDao();
		User user = new User();
		user.setUserName(userName);
		return dao.getUserAuthority(new UserDao().queryUser(user, db));
	}
}
