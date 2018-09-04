package com.eoulu.log;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.entity.UserAccess;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.UserAccessServiceImpl;

public class AccessStatistics {

	public void operateAccess(HttpServletRequest request,String jsp){
		UserAccessService acc = new UserAccessServiceImpl();
		int userID = Integer.parseInt(request.getSession().getAttribute("userID").toString());
		int count = acc.getCounts(userID, jsp);
		UserAccess access = new UserAccess();
		access.setJspName(jsp);
		access.setUserID(userID);
		access.setAccessCount(count++);
		acc.operate(access);
	}
	
}
