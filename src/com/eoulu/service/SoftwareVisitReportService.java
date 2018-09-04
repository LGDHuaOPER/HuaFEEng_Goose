package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.VisitReport;

public interface SoftwareVisitReportService {

	public List<Map<String, Object>> getAllData(Page page,String content,String year);
	
	public int getCounts( String content,String year);
	
	public String getFileName(int id);
	
	public boolean insert(HttpServletRequest request);
	
	
	public boolean moreManualAdd(HttpServletRequest request);
}
