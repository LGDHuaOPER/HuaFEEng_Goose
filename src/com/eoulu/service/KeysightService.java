package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Keysight;

public interface KeysightService {
	public List<Map<String, Object>> getDataByPage(Page page,String type,String column1,String content1,String column2,String content2);
	
	public boolean insert(Keysight keysight);
	
	public boolean update(Keysight keysight);
	
	public int getCounts(String type,String column1,String content1,String column2,String content2);
	
	public void exportExcel(List<Map<String, Object>> list,HttpServletResponse response);
	
	public List<Map<String,Object>> getExcelData();
}
