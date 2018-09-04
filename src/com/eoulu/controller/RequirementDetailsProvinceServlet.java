package com.eoulu.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.RequirementService;
import com.eoulu.service.impl.RequirementServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class RequirementDetailsProvinceServlet
 */
@WebServlet("/RequirementDetailsProvince")
public class RequirementDetailsProvinceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequirementDetailsProvinceServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String currYear = Calendar.getInstance().get(Calendar.YEAR)+"";
		String QueryType = request.getParameter("QueryType")==null?"Common": request.getParameter("QueryType");
		String year = request.getParameter("Year")==null?currYear: request.getParameter("Year");
		String month = request.getParameter("Month")==null?"All": request.getParameter("Month");
		String startTime = year + "-" + month + "-01";
		String endTime = year + "-" + month + "-30";
		if (month.equals("All")) {
			startTime = year + "-01-01";
			endTime = year + "-12-31";
		}
		if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07") || month.equals("08")
				|| month.equals("10") || month.equals("12")) {
			endTime = year + "-" + month + "-31";
		}
		if (month.equals("02")) {
			if ((Integer.parseInt(year) % 100 == 0) && (Integer.parseInt(year) % 400 == 0)) {
				endTime = year + "-" + month + "-29";
			} else if ((Integer.parseInt(year) % 100 != 0) && (Integer.parseInt(year) % 4 == 0)) {
				endTime = year + "-" + month + "-29";
			} else {
				endTime = year + "-" + month + "-28";
			}
		}
		RequirementService service = new RequirementServiceImpl();
		
			Map<String,Object> map = new HashMap<>();
			if(month.equals("All")){
				map.put("areasStatics", service.getStatisticsByProvince(startTime, endTime));
			}else{
				map.put("areasStatics", service.getStatisticsByProvince(startTime, endTime));
			}
		
			map.put("Year", year);
			map.put("Month", month);
			map.put("QueryType", QueryType);
			
			response.getWriter().write(new Gson().toJson(map));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currYear = Calendar.getInstance().get(Calendar.YEAR)+"";
		String year = request.getParameter("Year")==null?currYear: request.getParameter("Year");
		String month = request.getParameter("Month")==null?"All": request.getParameter("Month");
		String startTime = year + "-" + month + "-01";
		String endTime = year + "-" + month + "-30";
		if (month.equals("All")) {
			startTime = year + "-01-01";
			endTime = year + "-12-31";
		}
		if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07") || month.equals("08")
				|| month.equals("10") || month.equals("12")) {
			endTime = year + "-" + month + "-31";
		}
		if (month.equals("02")) {
			if ((Integer.parseInt(year) % 100 == 0) && (Integer.parseInt(year) % 400 == 0)) {
				endTime = year + "-" + month + "-29";
			} else if ((Integer.parseInt(year) % 100 != 0) && (Integer.parseInt(year) % 4 == 0)) {
				endTime = year + "-" + month + "-29";
			} else {
				endTime = year + "-" + month + "-28";
			}
		}
		RequirementService service = new RequirementServiceImpl();
		Map<String,Object> map = new HashMap<>();
		map.put("Statistics", service.getProvinceMap(startTime, endTime));
		map.put("Year", year);
		map.put("Month", month);
		
		response.getWriter().write(new Gson().toJson(map));
	}

}
