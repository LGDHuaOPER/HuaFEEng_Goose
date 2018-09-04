package com.eoulu.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.AuthorityResource;
import com.eoulu.commonality.Page;
import com.eoulu.entity.UserAccess;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.ScheduleService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.ScheduleServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;
import com.google.gson.Gson;
@WebServlet("/Schedule")
public class ScheduleServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public ScheduleServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		ScheduleService service = new ScheduleServiceImpl();
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		String date = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(request.getParameter("Date")== null || request.getParameter("Date").equals("")||request.getParameter("Date").equals("0000-00-00")||request.getParameter("Date").equals(" ")){
			date = df.format(new Date());
		}else{
			date = request.getParameter("Date");
		}
		AuthorityResource auth = new AuthorityResource();
		boolean authority = auth.isExist(request, "DeleteSchedule");
		page.setRecordCounts(service.getAllCounts(date));
		System.out.println("dddddddddddd"+page.getPageCounts());
		request.setAttribute("queryType", "common");
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("Counts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("schedules", service.getAllData(page, date));
		request.setAttribute("date", date);
		System.out.println(date);
		request.setAttribute("authority", authority);
		request.setAttribute("engineer", service.getEngineer());
		new AccessStatistics().operateAccess(request, "员工行程");
		System.out.println("Gggggggggggg");
				
		request.getRequestDispatcher("WEB-INF//Schedule.jsp").forward(request, resp);
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	

}
