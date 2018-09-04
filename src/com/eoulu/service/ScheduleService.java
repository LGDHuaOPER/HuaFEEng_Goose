package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

import net.sf.json.JSONObject;

public interface ScheduleService {

	public List<Map<String, Object>> getAllData(Page page,String date);
	
	public int getAllCounts(String date);
	
	public boolean insert(HttpServletRequest request);
	
	public boolean update(HttpServletRequest request);
	
	public boolean delete(HttpServletRequest request);
	
	public List<Map<String, Object>> getPerson();
	
	public int getCountsByName(String name);
	
	public List<Map<String, Object>> getAllDataByName(String name,Page page);
	
	public List<Map<String, Object>> query(String classify,String param,Page page);
	
	public int queryCounts(String param,String classify);
	
	public List<Map<String, Object>> getDateByName(String name);
	
	public  Map<String, Object> getDataByTime(String startTime,String endTime,String name);
	
	//public  String[] getProvince(String startTime,String endTime,String name);
	public List<Map<String, Object>> getProvinceOrder(String startTime, String endTime,String name);
	
	public List<Map<String, Object>> getDate(String param,String classify);
	
	public List<Map<String, Object>>getEngineer();
	
	public List<Map<String, Object>> getDistanceOrder(String startTime,String endTime);
	
	public List<Map<String, Object>> getFrequenceOrder(String startTime,String endTime);
	
	public List<Map<String, Object>> getExpenseOrder(String startTime,String endTime);
}
