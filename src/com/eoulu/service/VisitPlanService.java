package com.eoulu.service;

import java.util.List;
import java.util.Map;

public interface VisitPlanService {

	List<Map<String,Object>> getVisitPlanByPage(String page);

	List<Map<String,Object>> getVisitPlanByPageAndContent(String page, String content);

	int getVisitPlanCount();

	int getVisitPlanCountByContent(String Content);

	boolean inset(int ID, String Name, String time, String Engineer, String Details);
	
	public List<Map<String,Object>> GetVisitPlan(int ID);

}
