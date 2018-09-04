package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.dao.UserDao;
import com.eoulu.entity.User;
import com.eoulu.service.IndexService;
import com.eoulu.service.LoginService;
import com.eoulu.util.MD5Util;

/** @author  作者 : zhangkai
* @date 创建时间：2017年5月31日 上午11:23:25 
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
	 * 修改密码
	 * 
	 * 
	 * 返回值-1   原始密码错误
	 * 
	 * 返回值大于等于1   密码修改成功
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
