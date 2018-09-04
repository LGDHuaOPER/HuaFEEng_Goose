 package com.eoulu.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eoulu.commonality.Page;
import com.eoulu.controller.MachineDetailsOperateServlet;
import com.eoulu.entity.OrderStatus;
import com.eoulu.entity.User;
import com.eoulu.util.DBUtil;



public class UserDao {




	/**
	 * ͨ��userID��ȡuser
	 * 根据id查询用户
	 * @param user  
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * */
	public List<Map<String, Object>> getUserByID(User user){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select * from t_user where ID=?";
		Object[] parameter = new Object[]{user.getID()};


		ls = db.QueryToList(sql, parameter);
		return ls;
	}


	/**
	 * ��ȡ�û���Ϣ�������û�������ȡ����
	 * 根据用户名查询该用户的所有信息
	 * @param user    ֻ�����û������� 
	 * 
	 * @return user   �����û�����������
	 * 
	 * */
	public User getUser(User user){

		User localUser = new User();
		DBUtil db = new DBUtil();
		String sql = "select * from t_user where UserName=?";
		Object[] parameter = new Object[]{user.getUserName()};
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);

		if(ls==null || ls.size()<=1){
			return null;
		}
		localUser.setID(Integer.parseInt(ls.get(1).get("ID").toString()));
		localUser.setUserPwd(ls.get(1).get("UserPwd").toString());
		return localUser;
	}


	/**
	 * �����û����ж��û��Ƿ����
	 * 根据用户名判断用户是否存在
	 * 
	 * @return true����   false������
	 * */
	public boolean isExist(String username){
		boolean flag = false;

		DBUtil db = new DBUtil();
		String sql="select ID counts from t_user where UserName=?";
		Object[] parameter = new Object[1];
		parameter[0] = username;
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);

		if(ls.size()>1)
			flag = true;

		return flag;

	}



	/**
	 * 查询出所有用户
	 * ��ȡ���е��û���Ϣ
	 * */
	public List<Map<String,Object>> getAllUser(){

		String sql = "select ID,UserName,Sex,Email,Phone,Role,CreateDate,LastLogin from t_user";
		DBUtil db = new DBUtil();

		return db.QueryToList(sql, new Object[]{});

	}
	public List<Map<String, Object>> getUserByPage(Page page){
		String sql = "select ID,UserName,Sex,Email,Phone,Role,CreateDate,LastLogin from t_user ORDER BY ID DESC LIMIT ?,?";
		DBUtil db = new DBUtil();
		Object[] param = new Object[]{(page.getCurrentPage() - 1) * page.getRows(), page.getRows()};
		List<Map<String, Object>> list = db.QueryToList(sql, param);
		return list;	
	}
	public List<Map<String, Object>> query(String queryType,String content,Page page){
		DBUtil dbUtil = new DBUtil();
		String sql = "";
		switch(queryType){
		case "UserName":
			sql = "select * from t_user where UserName like ? ORDER BY ID DESC LIMIT ?,?";
			break;
		case "Phone":
			sql = "select * from t_user where Phone like ? ORDER BY ID DESC LIMIT ?,?";
			break;
		case "Email":
			sql = "select * from t_user where Email like ? ORDER BY ID DESC LIMIT ?,?";
			break;
		}
			
		Object[] param = {"%"+content+"%",(page.getCurrentPage() - 1) * page.getRows(), page.getRows()};
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, param);
		return list;
	}
	
	public int getUserCounts(){
		DBUtil dbUtil = new DBUtil();
		String sql = "select count(ID) count FROM t_user";
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, null);
		int count = 0;
		if(list.size() > 1){
			count = Integer.parseInt(list.get(1).get("count").toString());
		}
		return count;
	}
	
	public int getQueryCounts(String queryType,String content){
		DBUtil dbUtil = new DBUtil();
		String sql = "";
		switch(queryType){
		case "UserName":
			sql = "select count(ID) count from t_user where UserName like ?";
			break;
		case "Phone":
			sql = "select count(ID) count from t_user where Phone like ?";
			break;
		case "Email":
			sql = "select count(ID) count from t_user where Email like ?";
			break;
		}
		Object[] param = {"%"+content+"%"};
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, param);
		int count = 0;
		if(list.size() > 1){
			count = Integer.parseInt(list.get(1).get("count").toString());
		}
		return count;
	}


	/**
	 * 修改用户信息
	 * */
	public boolean modify(User user){

		boolean flag = false;
		String sql = "update t_user set t_user.UserName=?,t_user.Sex=?,t_user.Email=?,t_user.Phone=?,t_user.Role = ? where t_user.ID=?";
		Object[] parameter = new Object[6];

		parameter[0] = user.getUserName();
		parameter[1] = user.getSex();
		parameter[2] = user.getEmail();
		parameter[3] = user.getPhone();
		parameter[4] = user.getRole();
		parameter[5] = user.getID();

		DBUtil db = new DBUtil();

		int result = db.executeUpdate(sql, parameter);

		if(result>0)	
			flag = true;

		return flag;

	}




	/**
	 * 根据id删除用户
	 * ɾ���û���Ϣ
	 * */
	public boolean delete(int id){

		boolean flag = false;
		String sql = "delete from t_user where t_user.ID=?";
		Object[] parameter = new Object[]{id};

		DBUtil db = new DBUtil();

		int result = db.executeUpdate(sql, parameter);

		if(result>0)	
			flag = true;

		return flag;

	}


	/**
	 * 添加用户
	 * �����û���Ϣ
	 * @throws SQLException 
	 * */
	public boolean insert(User user,DBUtil db) throws SQLException{

		boolean flag = false;
		String sql = "insert into t_user (UserName,UserPwd,Sex,Email,Phone,Role,CreateDate) values (?,?,?,?,?,?,?)";
		Object[] parameter = new Object[7];

		parameter[0] = user.getUserName();
		parameter[1] = user.getUserPwd();
		parameter[2] = user.getSex();
		parameter[3] = user.getEmail();
		parameter[4] = user.getPhone();
		parameter[5] = user.getRole();
		parameter[6] = user.getCreateDate();


		int result = db.executeUpdateNotClose(sql, parameter);

		if(result>0)	
			flag = true;

		return flag;

	}
	
	
	/**
	 * 根据用户名，查询出用户的id
	 * �������в�ѯ�û�
	 * */
	public int queryUser(User user,DBUtil db){
		
		String sql = "select * from t_user where UserName=?";
		Object[] parameter = new Object[]{user.getUserName()};
		
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter, false);
		
		int result = 0;
		
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("ID").toString());
		
		return result;
	}

	
	
	
	/**
	 * ִ�в�ѯ����
	 * 查询
	 * */
	public List<Map<String,Object>> executeQuery(Object[] parameter,String sql){
		DBUtil db = new DBUtil();
		return db.QueryToList(sql, parameter);
	}
	
	
	/**
	 * ִ�г��˲�ѯ֮��ķ���
	 * 更新
	 * */
	public int executeUpdate(String sql,Object[] parameter){
		
		DBUtil db = new DBUtil();
		int result = db.executeUpdate(sql, parameter);
		return result;
		
	}
	
	public String getRealName(String userName,String email){
		String sql = "select * from t_user where UserName=? and Email=?";
		Object[] parameter = new Object[]{userName,email};
		DBUtil db = new DBUtil();
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		String name = "";
		if(ls.size()>1){
			name = ls.get(1).get("RealName").toString();
		}
		return name;
	}
	//	@Test
	//	public void test(){
	//		System.out.println(getAllUser());
	//	}
	public boolean setLastLogin(String date,String userName){
		DBUtil db = new DBUtil();
		String sql = "update t_user set LastLogin=? where UserName = ? ";
		Object[] param = new Object[]{date,userName};
		int result = db.executeUpdate(sql, param);
		return result > 0?true:false;
	}
	
	public List<Map<String, Object>>getUserRole(String userName){
		DBUtil db = new DBUtil();
		String sql = "select Role from t_user where UserName = ?";
		Object[] param = new Object[]{userName};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;
		
	}
	
	public String getMailPassword(String email){
		DBUtil db = new DBUtil();
		String sql = "select MailPassword from t_user where Email = ?";
		Object[] param = new Object[]{email};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls.get(1).get("MailPassword").toString();
	}

}
