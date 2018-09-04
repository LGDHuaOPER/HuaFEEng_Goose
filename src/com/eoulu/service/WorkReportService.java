package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.WorkReport;

public interface WorkReportService {
	
	public boolean insert(WorkReport report);
	
	public boolean update(WorkReport report);
	
	public List<Map<String, Object>> getDataByPage(Page page);
	
	public int getCounts();
	
	public boolean urge(String name);
	
	public String sendComments(int ID,String name,String text,String umail);
	
	public boolean sendLog(HttpServletRequest request);

}
