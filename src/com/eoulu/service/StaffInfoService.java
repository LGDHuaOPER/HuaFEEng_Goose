package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;

public interface StaffInfoService {

	public List<Map<String, Object>> getAllData(Page page);
	
	public List<Map<String, Object>> singleQuery(HttpServletRequest request,Page page);
	
	public List<Map<String,Object>> maxQuery(HttpServletRequest request,Page page);
	
	public void exportExcel(HttpServletResponse resp);
	
	public int getSingleQueryCount(HttpServletRequest request);
	
	public int getMaxQueryCount(HttpServletRequest request);
	
	public int getAllCounts();
	
	public boolean operate(HttpServletRequest request);
	
	public boolean delete(HttpServletRequest request);
	
	public List<Map<String,Object>> getStaffName(HttpServletRequest request);
	

	public List<Map<String,Object>> getNameAndMail(HttpServletRequest request);
	
	public String uploadFile(HttpServletRequest request);
	
	public void exportPhoto(HttpServletResponse response);

	
	
	
}
