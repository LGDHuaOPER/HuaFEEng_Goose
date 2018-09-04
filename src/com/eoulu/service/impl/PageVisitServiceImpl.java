package com.eoulu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.dao.PageVisitDao;
import com.eoulu.entity.PageVisit;
import com.eoulu.service.PageVisitService;

public class PageVisitServiceImpl implements PageVisitService{

	@Override
	public void savePVData(List<Map<String, Object>> pvData) {
		PageVisitDao dao = new PageVisitDao();
		PageVisit visit = new PageVisit();
		for(int i = 1;i < pvData.size();i ++){	
			Map<String, Object> map = pvData.get(i);
			visit.setPageName(map.get("PageName").toString());
			visit.setDepartment(map.get("Department").toString());
			visit.setVisitingTime(Integer.parseInt(map.get("VisitingTime").toString()));
			if(dao.isExist(visit)){
				dao.update(visit);
			}else{
				dao.insert(visit);
			}
			
		}
	
	}

	@Override
	public List<Map<String, Object>> getData() {
		PageVisitDao dao = new PageVisitDao();
		return dao.getData();
	}

	@Override
	public void updateCollection(List<Map<String, Object>> pvData,PageVisit visit) {
		boolean isExist = false;
		for(int i = 1;i < pvData.size();i ++){
			Map<String, Object> map = pvData.get(i);
			if(map.get("PageName").equals(visit.getPageName())&&map.get("Department").equals(visit.getDepartment())){
				isExist = true;
				int times = Integer.parseInt(map.get("VisitingTime").toString())+1;
				map.put("VisitingTime",times );
				break;
			}
		}
		if(isExist==false){
			Map<String, Object> newMap = new HashMap<>();
			newMap.put("PageName", visit.getPageName());
			newMap.put("Department", visit.getDepartment());
			newMap.put("VisitingTime", 1);
			pvData.add(newMap);
		}
			
	}
	
	

}
