package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

public interface RoutineVisitService {

	public List<Map<String, Object>> getAllData(Page page);
	
	public int getAllCounts();
	
	public boolean operate(HttpServletRequest request);
	
	
	public boolean deleteProject(int id);
	
	public List<Map<String, Object>> getPerData(int id);
	
	
	
}
