package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.dao.VisitPlanDao;
import com.eoulu.service.VisitPlanService;

public class VisitPlanserviceImpl implements VisitPlanService {

	@Override
	public List<Map<String,Object>> getVisitPlanByPage(String page) {
		return new VisitPlanDao().getVisitPlanByPage(page);
		
		
		
	}

	@Override
	public List<Map<String,Object>> getVisitPlanByPageAndContent(String page, String content) {
		return new VisitPlanDao().getVisitPlanByPageAndContent(page,content);
	}

	@Override
	public int getVisitPlanCount() {
		return new VisitPlanDao().getVisitPlanCount();
	}

	@Override
	public int getVisitPlanCountByContent(String Content) {
		return new VisitPlanDao().getVisitPlanCountByContent(Content);
	}

	@Override
	public boolean inset(int ID, String Name, String time, String Engineer, String Details) {
		return new VisitPlanDao().inset(ID,Name,time,Engineer,Details);
	}

	public List<Map<String,Object>> GetVisitPlan(int ID) {
		return new VisitPlanDao().GetVisitPlan(ID);
	}

}
