package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.TransportDirective;

public interface TransportDirectiveService {

	public List<Map<String, Object>> getAllData(Page page);
	
	public int getAllCounts();
	
	public boolean operate(HttpServletRequest request);
	
	public List<Map<String, Object>> getModel(int id);
	
	public String sendEmail(HttpServletRequest request);
	
	public boolean deleteAddress(HttpServletRequest request);

	public int getAllCounts(String type1, String searchContent1);

	public List<Map<String, Object>> getAllData(Page page, String type1, String searchContent1);

	public int getAllCounts(String type1, String searchContent1, String type12, String searchContent12);

	public List<Map<String, Object>> getAllData(Page page, String type1, String searchContent1, String type12,
			String searchContent12);
	
}
