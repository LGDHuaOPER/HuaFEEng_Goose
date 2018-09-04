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
import com.eoulu.service.SalesStatisticsService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.SalesStatisticsServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;
@WebServlet("/SalesStatistics")
public class SalesStatisticsServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public SalesStatisticsServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SalesStatisticsService service = new SalesStatisticsServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getAllcounts());
		req.setAttribute("queryType", "common");
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("salesCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("sales", service.getSalesStatistics(page));
		new AccessStatistics().operateAccess(req, "销售统计");
		req.getRequestDispatcher("WEB-INF//salesStatistics.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
}
