package com.eoulu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.entity.WorkReport;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.ReimburseService;
import com.eoulu.service.WorkReportService;
import com.eoulu.service.impl.ReimburseServiceImpl;
import com.eoulu.service.impl.WorkReportServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class WorkReportServlet
 */
@WebServlet("/WorkReport")
public class WorkReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorkReportServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loadType = request.getParameter("LoadType")==null?"":request.getParameter("LoadType");
		if(loadType.equals("data")){
			int currentPage = Integer
					.parseInt(request.getParameter("CurrentPage") == null ? "1" : request.getParameter("CurrentPage"));
			
			WorkReportService service = new WorkReportServiceImpl();
			Page page = new Page();
			page.setCurrentPage(currentPage);
			page.setRows(10);
			page.setRecordCounts(service.getCounts());
			Map<String, Object> map = new HashMap<>();
			map.put("currentPage",currentPage);
			map.put("datas", service.getDataByPage(page));
			map.put("pageCount", page.getPageCounts());
			response.getWriter().write(new Gson().toJson(map));
		}else{
			new AccessStatistics().operateAccess(request, "工作汇报");
			String email = (String) request.getSession().getAttribute("email");
			ReimburseService service = new ReimburseServiceImpl();
			request.setAttribute("name",service.getUserName(email) );
			request.getRequestDispatcher("WEB-INF\\workReport.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Type");
		int ID = request.getParameter("ID")==null?0:Integer.parseInt(request.getParameter("ID"));
		String Name = request.getParameter("Name")==null?"":request.getParameter("Name").trim();
		String Department = request.getParameter("Department")==null?"":request.getParameter("Department").trim();
		String Morning = request.getParameter("Morning")==null?"":request.getParameter("Morning");
		String Afternoon = request.getParameter("Afternoon")==null?"":request.getParameter("Afternoon");
		String MorningPlan = request.getParameter("MorningPlan")==null?"":request.getParameter("MorningPlan"); 
		String AfternoonPlan = request.getParameter("AfternoonPlan")==null?"":request.getParameter("AfternoonPlan"); 
		String Introspection = request.getParameter("Introspection")==null?"":request.getParameter("Introspection"); 
		
		WorkReport report = new WorkReport();
		report.setID(ID);
		report.setName(Name);
		report.setDepartment(Department);
		report.setMorning(Morning);
		report.setAfternoon(Afternoon);
		report.setMorningPlan(MorningPlan);
		report.setAfternoonPlan(AfternoonPlan);
		report.setIntrospection(Introspection);
		
		WorkReportService service = new WorkReportServiceImpl();
		boolean result = false;
		switch (type) {
		case "add":
			result = service.insert(report);
			break;

		case "update":
			result = service.update(report);
			break;
		}
		response.getWriter().write(new Gson().toJson(result));
	}

}
