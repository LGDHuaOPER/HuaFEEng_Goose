package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.dao.UserDao;
import com.eoulu.entity.User;
import com.eoulu.service.IndexService;
import com.eoulu.service.LoginService;
import com.eoulu.util.MD5Util;

/** @author  ���� : zhangkai
* @date ����ʱ�䣺2017��5��31�� ����11:23:25 
* @version 1.0  
* @since  
* @return  
*/
public class IndexServiceImpl implements IndexService {

	/* (non-Javadoc)
	 * @see com.eoulu.service.IndexService#getUserInfo(int)
	 */
	@Override
	public List<Map<String, Object>> getUserInfo(int id) {
		
		UserDao userDao = new UserDao();
		String sql = "select * from t_user where ID=?";
		List<Map<String,Object>> ls = userDao.executeQuery(new Object[]{id}, sql);
		
		return ls;
	}

	/**
	 * �޸�����
	 * 
	 * 
	 * ����ֵ-1   ԭʼ�������
	 * 
	 * ����ֵ���ڵ���1   �����޸ĳɹ�
	 * */
	@Override
	public int modifyPassword(User user,String newPwd) {
		int result = 0;
		LoginService ls = new LoginServiceImpl();
		int sign = ls.toLogin(user);
		
		if(sign==-1){
			result = -1;
		}else if(sign>=1){
			UserDao userDao = new UserDao();
			String sql = "update t_user set UserPwd=? where ID=?";
			Object[] parameter = new Object[]{new MD5Util().md5ToString(newPwd),user.getID()};
			
			result = userDao.executeUpdate(sql, parameter);	
		}
		
		
		
		return result;
	}

}
