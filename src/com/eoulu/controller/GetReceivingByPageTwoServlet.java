package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.ReceivingService;
import com.eoulu.service.impl.ReceivingServiceImpl;
@WebServlet("/GetReceivingByPageTwo")
public class GetReceivingByPageTwoServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public GetReceivingByPageTwoServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ReceivingService service = new ReceivingServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		String classify1 = req.getParameter("type1");
		String parameter1 = req.getParameter("searchContent1").trim();
		String classify2 = req.getParameter("type2");
		String parameter2 = req.getParameter("searchContent2").trim();
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getCountByClassifyInTwo(classify1, parameter1, classify2, parameter2));
		req.setAttribute("receiving", service.getReceivingByClassifyInTwo(classify1, parameter1, classify2, parameter2, page));
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("receivingCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("classify1", classify1);
		req.setAttribute("parameter1", parameter1);
		req.setAttribute("classify2", classify2);
		req.setAttribute("parameter2", parameter2);
		req.setAttribute("queryType", "mixSelect");
		req.setAttribute("str", "mixSelect");
		req.getRequestDispatcher("WEB-INF//Receiving.jsp").forward(req, resp);
	}
	
	

}
