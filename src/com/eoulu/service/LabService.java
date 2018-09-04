package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Laboratory;

public interface LabService {
	
	public List<Map<String, Object>> getDataByPage(Page page);
	
	public boolean insert(Laboratory lab);
	
	public boolean update(Laboratory lab);
	
	public int getCounts();
	
	public boolean saveConfig(String configJson,int LabID);
	
	public List<Map<String, Object>> getConfig(int LabID);
	
	public void exportConfig(int LabID,String Model,String path);
	
}
