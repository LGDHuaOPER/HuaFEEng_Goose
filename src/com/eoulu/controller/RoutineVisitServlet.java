package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.entity.UserAccess;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.RoutineVisitService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.RoutineVisitServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;

@WebServlet("/RoutineVisit")
public class RoutineVisitServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public RoutineVisitServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RoutineVisitService service = new RoutineVisitServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getAllCounts());
		req.setAttribute("queryType", "common");
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("Counts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("visit", service.getAllData(page));
		new AccessStatistics().operateAccess(req, "例行拜访");
		req.getRequestDispatcher("WEB-INF\\Visit.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	

}
