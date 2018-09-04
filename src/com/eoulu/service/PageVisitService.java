package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.entity.PageVisit;

public interface PageVisitService {
	
	public void savePVData(List<Map<String, Object>> pvData);
	
	public List<Map<String, Object>> getData();
	
	public void updateCollection(List<Map<String, Object>> pvData,PageVisit visit);
	
		
		
	

}
