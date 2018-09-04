package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.ServiceReport;

public interface ServiceReportService {
	
	public List<Map<String, Object>> getAllData(Page page,String type,String column1,String content1,String column2,String content2);
	
	public int getCounts(String type,String column1,String content1,String column2,String content2);
	
	public boolean insert(ServiceReport report);
	
	public boolean update(ServiceReport report);
	
	public String getReportNumber(String email);
	
	public List<Map<String, Object>> preview(int reportID);
	
	public boolean savePreview(String previewJson,int reportID);
	
	public String exportFile(HttpServletRequest request);
	
	public boolean deleteRecord(int ID);

}
