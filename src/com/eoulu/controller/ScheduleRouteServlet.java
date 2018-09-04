package com.eoulu.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.ScheduleService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.ScheduleServiceImpl;
import com.google.gson.Gson;
@WebServlet("/ScheduleRoute")
public class ScheduleRouteServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public ScheduleRouteServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		ScheduleService service = new ScheduleServiceImpl();
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		System.out.println("currentPage:"+currentPage);
//		String name = request.getParameter("Name");
		String classify = request.getParameter("classify");
		String param = request.getParameter("parameter") == null?"":request.getParameter("parameter");
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.queryCounts(param, classify));
//		page.setRecordCounts(service.getCountsByName(name));
		request.setAttribute("queryType", "SingleSelect");
		request.setAttribute("parameter", param);
		request.setAttribute("classify", classify);
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("Counts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("allDate", service.getDate(param, classify));
		request.setAttribute("schedules", service.query(classify, param, page));
		request.setAttribute("engineer", service.getEngineer());
//		request.setAttribute("allDate", service.getDateByName(name));
//		request.setAttribute("schedules", service.getAllDataByName(name, page));
		System.out.println(service.query(classify, param, page).size());
		request.getRequestDispatcher("WEB-INF//Schedule.jsp").forward(request, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	
	
}
