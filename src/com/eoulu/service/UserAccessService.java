package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.entity.UserAccess;

public interface UserAccessService {
	public List<Map<String,Object>> getAllData(HttpServletRequest request);
	
	public int getCounts(int userID,String jspName);
	
	public boolean operate(UserAccess user);
}
