package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

public interface CommonProblemService {

	public List<Map<String,Object>> getAllData(Page page,String column,String order);
	
	public int getAllCounts();
	
	public String insert(HttpServletRequest request);
	
	public String moreAdd(HttpServletRequest request) ;
	
	public List<Map<String,Object>> queryAllData(Page page,String content,String column,String order);
	
	public int queryAllCounts(String content);
	
	public String getCommonProblemByID(int id);

	public String update(HttpServletRequest request);
	
}
