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
import com.eoulu.service.ReceivingService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.ReceivingServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;
@WebServlet("/Receiving")
public class ReceivingServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public ReceivingServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		ReceivingService service = new ReceivingServiceImpl();
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getAllCounts());
		request.setAttribute("queryType", "common");
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("receivingCounts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("receiving", service.getReceiving(page));
		new AccessStatistics().operateAccess(request, "验收报告");
		request.getRequestDispatcher("WEB-INF//Receiving.jsp").forward(request, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	
	

}
