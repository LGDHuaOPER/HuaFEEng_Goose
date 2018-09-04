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
import com.eoulu.service.TestReportService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.TestReportServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;


@WebServlet("/TestReport")
public class TestReportServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	
	public TestReportServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		TestReportService service =new  TestReportServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getAllCounts());
		req.setAttribute("queryType", "common");
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("testReportCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("testReport", service.getTestReport(page));
		new AccessStatistics().operateAccess(req, "测试报告");
		req.getRequestDispatcher("WEB-INF//TestReport.jsp").forward(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		super.doPost(req, resp);
	}

	
	
}
