package com.eoulu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.RoutineVisitDao;
import com.eoulu.entity.RoutineVisit;
import com.eoulu.entity.RoutineVisitProject;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.RoutineVisitService;

public class RoutineVisitServiceImpl implements RoutineVisitService{

	@Override
	public List<Map<String, Object>> getAllData(Page page) {
		return new RoutineVisitDao().getAllData(page);
	}

	@Override
	public int getAllCounts() {
		return new RoutineVisitDao().getAllCounts();
	}


	@Override
	public boolean deleteProject(int id) {
		return new RoutineVisitDao().deleteProject(id);
	}

	@Override
	public List<Map<String, Object>> getPerData(int id) {
		List<Map<String, Object>> ls = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> basic = new RoutineVisitDao().getPerData(id);
		List<Map<String, Object>> pro = new RoutineVisitDao().getAllProject(id);
		map.put("basic", basic);
		map.put("project", pro);
		ls.add(map);
		return ls;
	}

	@Override
	public boolean operate(HttpServletRequest request) {

		List<String> ls = new ArrayList<>();
		RoutineVisitDao dao = new RoutineVisitDao();
		
		int visitID = Integer.parseInt(request.getParameter("ID"));
		String unit = request.getParameter("CustomerUnit");
		String user = request.getParameter("EndUser");
		String tel = request.getParameter("EndTel");
		String engineer = request.getParameter("ToEngineer");
		String model = request.getParameter("ModelAndSN");
		String instrument = request.getParameter("Instrument");
		String visitDate = request.getParameter("VisitDate");
		String content = request.getParameter("CheckContent");
		String addDate = request.getParameter("AddDate");
		String exist = request.getParameter("Exist");
		String conclusion = request.getParameter("Conclusion");
		
		RoutineVisit visit = new RoutineVisit();
		visit.setID(visitID);
		visit.setCustomerUnit(unit);
		visit.setEndUser(user);
		visit.setEndTel(tel);
		visit.setToEngineer(engineer);
		visit.setModelAndSN(model);
		visit.setInstrument(instrument);
		visit.setVisitDate(visitDate);
		visit.setCheckContent(content);
		visit.setAddDate(addDate);
		visit.setConclusion(conclusion);
		boolean flag = false;
		if(visitID == 0){
			flag = dao.insert(visit);
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "服务部-例行拜访";
			String description = "新增-"+unit;
			log.insert(request, JspInfo, description);
		}else{
			flag = dao.update(visit);
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "服务部-例行拜访";
			String description = "修改-"+unit;
			log.insert(request, JspInfo, description);
		}
		
		if(visitID == 0){
			visitID = dao.getVisitID(model, content, visitDate);
		}
		
		if(exist.equals("yes")){
			String[] ids = request.getParameterValues("ProjectID[]");
			String[] project= request.getParameterValues("Project[]");
			String[] situation= request.getParameterValues("CheckSituation[]");
			String[] evolve= request.getParameterValues("Evolve[]");
			String[] another= request.getParameterValues("EvolveAnother[]");

			for(int i=0 ; i<ids.length ; i++){
				RoutineVisitProject pro = new RoutineVisitProject();
				pro.setID(Integer.parseInt(ids[i]));
				pro.setProject(project[i]);
				pro.setCheckSituation(situation[i]);
				pro.setEvolve(Integer.parseInt(evolve[i]));
				pro.setEvolveAnother(Integer.parseInt(another[i]));
				pro.setVisitID(visitID);
				
				if(Integer.parseInt(ids[i]) == 0){
					if(dao.insertProject(pro)){
						ls.add("true");
					}else{
						ls.add("false");
					}
				}else{
					if(dao.updateProject(pro)){
						ls.add("true");
					}else{
						ls.add("false");
					}
				}
			}
			
		}
		if(!ls.contains("false") && flag){
			flag = true;
		}
		
		return flag;
	}

}
