package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.dao.UserAccessDao;
import com.eoulu.entity.UserAccess;
import com.eoulu.service.UserAccessService;

public class UserAccessServiceImpl implements UserAccessService{

	@Override
	public List<Map<String, Object>> getAllData(HttpServletRequest request) {
		
		return new UserAccessDao().getAllData(Integer.parseInt(request.getSession().getAttribute("userID").toString()));
	}

	@Override
	public boolean operate(UserAccess user) {
		
		return user.getAccessCount()>0?new UserAccessDao().update(user):new UserAccessDao().insert(user);
	}

	@Override
	public int getCounts(int userID, String jspName) {
		return new UserAccessDao().getCounts(userID, jspName);
	}

}
