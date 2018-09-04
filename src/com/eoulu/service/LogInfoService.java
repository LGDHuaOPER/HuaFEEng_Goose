package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.LogInfo;

public interface LogInfoService {

	public List<Map<String,Object>> getAllData();
	
	public List<Map<String,Object>> getAllData(Page page,String column);
	
	public int getAllCounts();
	
	public boolean insert(HttpServletRequest request,String JspInfo,String Description );
	
	public boolean delete();
	
	public boolean getOutDate();
	
	public List<Map<String, Object>> getDataByID(String[] idList) ;
}
