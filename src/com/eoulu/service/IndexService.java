/**
 * 
 */package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.entity.User;

/** @author  作者 : zhangkai
* @date 创建时间：2017年5月31日 上午11:17:35 
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
	 * 查询用户信息，按照用户ID
	 * 
	 * id   用户的id
	 * */
	public List<Map<String,Object>> getUserInfo(int id);
	
	/**
	 * 修改密码
	 * */
	public int modifyPassword(User user,String newPwd);
	
	
}
