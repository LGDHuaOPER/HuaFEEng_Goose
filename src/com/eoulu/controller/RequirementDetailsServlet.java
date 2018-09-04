package com.eoulu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
 * Servlet implementation class RequirementDetailsServlet
 */
@WebServlet(description = "需求录入页面的详细记录", urlPatterns = { "/RequirementDetails" })
public class RequirementDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RequirementDetailsServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
		
		if(QueryType.equals("Common")){
			
			if(month.equals("All")){
				System.out.println(service.getStatisticsByArea(startTime, endTime));
				request.setAttribute("areasStatics", service.getStatisticsByArea(startTime, endTime));
			}else{
				System.out.println(135641);
				request.setAttribute("areasStatics", service.getStatisticsByAreaPerMonth(startTime, endTime));
			}
			request.setAttribute("Year", year);
			request.setAttribute("Month", month);
			request.setAttribute("QueryType", QueryType);
			request.getRequestDispatcher("WEB-INF/requirementStatics.jsp").forward(request, response);
		}else{
			Map<String,Object> map = new HashMap<>();
			if(month.equals("All")){
				map.put("areasStatics", service.getStatisticsByArea(startTime, endTime));
			}else{
				map.put("areasStatics", service.getStatisticsByAreaPerMonth(startTime, endTime));
			}
		
			map.put("Year", year);
			map.put("Month", month);
			map.put("QueryType", QueryType);
			
			response.getWriter().write(new Gson().toJson(map));
		}
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequirementService service = new RequirementServiceImpl();
		Map<String,Object> map = null;
		String classify = request.getParameter("classify");
		String month = request.getParameter("Month")==null?"All": request.getParameter("Month");
		switch (classify) {
		case "Export":
			String currYear = Calendar.getInstance().get(Calendar.YEAR)+"";
			String QueryType = request.getParameter("QueryType")==null?"Common": request.getParameter("QueryType");
			String year = request.getParameter("Year")==null?currYear: request.getParameter("Year");
//			String month = request.getParameter("Month")==null?"All": request.getParameter("Month");
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
			System.out.println(startTime);
			System.out.println(endTime);
			if (month.equals("02")) {
				if ((Integer.parseInt(year) % 100 == 0) && (Integer.parseInt(year) % 400 == 0)) {
					endTime = year + "-" + month + "-29";
				} else if ((Integer.parseInt(year) % 100 != 0) && (Integer.parseInt(year) % 4 == 0)) {
					endTime = year + "-" + month + "-29";
				} else {
					endTime = year + "-" + month + "-28";
				}
			}
			if(QueryType.equals("Common")){
				if(month.equals("All")){
					map = service.getStatisticsByArea(startTime, endTime);
				}else{
					map =  service.getStatisticsByAreaPerMonth(startTime, endTime);
				}
			}else{
				if(month.equals("All")){
					map = service.getStatisticsByArea(startTime, endTime);
				}else{
					map = service.getStatisticsByAreaPerMonth(startTime, endTime);
				}
			
			}
			String path = request.getServletContext().getRealPath("/")+"down/需求详细记录.xlsx";
			new RequirementServiceImpl().builDetailsExcel(map, path,month);
			response.getWriter().write("down/需求详细记录.xlsx");
			break;

		case "ExportAll":
			List<Map<String,Object>> list = new ArrayList<>();
			List<Map<String,Object>> ls = service.getTime();
			int start = 2016;//Integer.parseInt(ls.get(1).get("StartTime").toString().split("-")[0]);
			int end = Integer.parseInt(ls.get(1).get("EndTime").toString().split("-")[0]);
			for(int i=start;i<=end;i++){
				map = service.getStatisticsByArea(i+"-01-01", i+"-12-31");
				list.add(map);
			}
			path = request.getServletContext().getRealPath("/")+"down/全部需求详细记录.xlsx";
			new RequirementServiceImpl().builAllDetailsExcel(list, path,start);
			response.getWriter().write("down/全部需求详细记录.xlsx");
			break;
		}
		
	}

}
