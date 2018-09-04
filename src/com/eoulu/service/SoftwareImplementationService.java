package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

public interface SoftwareImplementationService {
	
	public List<Map<String,Object>> getAllData(Page page,String content,String column,String order);
	
	public int getAllCounts(String content,String column);
	
	public String operate(HttpServletRequest request);
	
	public Map<String,Object> getOperatePreview(HttpServletRequest request);
	
	public List<Map<String,Object>> getPreview(HttpServletRequest request);
	

}
