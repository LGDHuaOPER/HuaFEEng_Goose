package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

public interface SoftwareProductService {
	
	public List<Map<String,Object>> getAllData(Page page);
	
	public int  getAllCounts();
	
	public boolean operate(HttpServletRequest request);
	
	public List<Map<String,Object>> getQueryResult(String content,Page page);
	
	public int getQueryCounts(String content);

}
