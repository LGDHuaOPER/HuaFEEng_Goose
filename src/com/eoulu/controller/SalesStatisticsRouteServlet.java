package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.SalesStatisticsService;
import com.eoulu.service.impl.SalesStatisticsServiceImpl;
@WebServlet("/SalesStatisticsRoute")
public class SalesStatisticsRouteServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public SalesStatisticsRouteServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	System.out.println(123131);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String select = req.getParameter("selected");
		System.out.println(1212);
		System.out.println(select+"--");
//		if(select.equals("singleSelect")){
			String currentPage = req.getParameter("currentPage");
			String classify = req.getParameter("type1");
			String parameter = req.getParameter("searchContent1").trim();
			SalesStatisticsService service = new SalesStatisticsServiceImpl();
			Page page = new Page();
			page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
			page.setRows(10);
			page.setRecordCounts(service.getCountByClassify(classify, parameter));
			req.setAttribute("sales", service.getSalesStatisticsByPageInOne(classify, parameter, page));
			req.setAttribute("currentPage", page.getCurrentPage());
			req.setAttribute("salesCounts", page.getRecordCounts());
			req.setAttribute("pageCounts", page.getPageCounts());
			req.setAttribute("classify1", classify);
			req.setAttribute("parameter1", parameter);
			req.setAttribute("queryType", "singleSelect");
			req.getRequestDispatcher("WEB-INF\\salesStatistics.jsp").forward(req, resp);
//		}
		
	}
	

}
