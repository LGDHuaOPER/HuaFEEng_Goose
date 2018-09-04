package com.eoulu.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.LogInfoDao;
import com.eoulu.entity.LogInfo;
import com.eoulu.service.LogInfoService;

public class LogInfoServiceImpl implements LogInfoService{

	@Override
	public List<Map<String, Object>> getAllData(Page page,String column) {
		return new LogInfoDao().getAllData(page,column);
	}

	@Override
	public int getAllCounts() {
		return new LogInfoDao().getAllCounts();
	}

	@Override
	public boolean insert(HttpServletRequest request,String JspInfo,String description ) {
		
		String account = request.getSession().getAttribute("user").toString();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date());
		SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
		String time = df2.format(new Date());
		String ip = request.getSession().getAttribute("IP").toString();
		String city = request.getSession().getAttribute("City").toString();
		LogInfo log = new LogInfo();
		log.setAccount(account);
		log.setDate(date);
		log.setTime(time);
		log.setDescription(description);
		log.setIpInfo(ip);
		log.setJspInfo(JspInfo);
		log.setLocation(city);
		
		return new LogInfoDao().insert(log);
	}

	@Override
	public boolean delete() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(cal.getTime());
		
		return new LogInfoDao().delete(date);
	}

	@Override
	public List<Map<String, Object>> getAllData() {
		return new LogInfoDao().getAllData();
	}

	@Override
	public  boolean getOutDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(cal.getTime());
		List<Map<String, Object>> ls = new LogInfoDao().getOutDate(date);
		boolean flag = false;
		if(ls.size()>1){
			flag = true;
		}
		return flag;
	}

	@Override
	public List<Map<String, Object>> getDataByID(String[] idList) {
		LogInfoDao dao = new LogInfoDao();
		return dao.getDataByID(idList);
	}

}
