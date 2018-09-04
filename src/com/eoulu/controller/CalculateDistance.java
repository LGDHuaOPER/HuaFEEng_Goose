package com.eoulu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.eoulu.dao.ScheduleDao;
import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class CalculateDistance
 */
@WebServlet("/CalculateDistance")
public class CalculateDistance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalculateDistance() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ScheduleDao  dao= new ScheduleDao();
		List<Map<String,Object>> ls = dao.getCity();
		for(int i = 1;i < ls.size();i ++){
			String departure = ls.get(i).get("Departure").toString();
			if(!departure.endsWith("市")&&!departure.endsWith("东")&&!departure.endsWith("西")&&!departure.endsWith("南")&&!departure.endsWith("北")&&!departure.endsWith("上海虹桥")){
				Map<String, Object> old = ls.get(i);
				if(departure.length()>6){
					old.put("Departure", "福州");
				}else{
					old.put("Departure", departure+"市");
				}
				
			}
			
		}
		System.out.println(ls);
		
		response.getWriter().write(new Gson().toJson(ls));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ds = request.getParameter("originDistanceDataBack");  
		JSONArray json=JSONArray.fromObject(ds); 
		JSONObject jsonOne;  
		List<Map<String, String>> updataMap = new ArrayList<>();
		 Map<String,String> map=null;  
		for(int i=0;i<json.size();i++){  
		    jsonOne = json.getJSONObject(i);  
		    map = new HashMap<>();
		    map.put("ID", (String) jsonOne.get("ID"));  
		    map.put("Distance", (String) jsonOne.get("Distance"));  
		    updataMap.add(map);
		 }  
		for(int i = 0; i < updataMap.size();i ++){
			System.out.println(updataMap.get(i));
		}
		ScheduleDao  dao= new ScheduleDao();
		dao.updateDistance(updataMap);
		
		
	
	}

}
