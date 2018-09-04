package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.SupplierInfo;

public interface CommodityService {

	public List<Map<String, Object>> getCommodityInfo(Page page);
	
	public int getCountByClassifyInOne(String classify, Object parameter);
	
	public List<Map<String, Object>> getQueryByClassifyInOne(String classify, Object parameter, Page page);
	
	public int getCountByClassifyInTwo(String classify1, Object parameter1, String classify2, Object parameter2);
	
	public List<Map<String, Object>> getQueryByClassifyInTwo(String classify1, Object parameter1, String classify2,
			Object parameter2, Page page);
	
	public String operateCommodityInfo(HttpServletRequest request);
	
	public boolean operateFile(SupplierInfo info);
	
	public String sendMail(HttpServletRequest request);
	
	public String getPath(int id);
	
	public Map<String,Object> getAllInfo(int id);
	
	public String getFileName(int id);
	
	public List<Map<String, Object>> getEquipmentByName(String equipment);
}
