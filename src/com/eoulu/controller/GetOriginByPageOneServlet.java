package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.OriginService;
import com.eoulu.service.impl.OriginServiceImpl;
@WebServlet("/GetOriginByPageOne")
public class GetOriginByPageOneServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	public GetOriginByPageOneServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		OriginService service = new OriginServiceImpl();
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		String classify = request.getParameter("type1");
		
		String parameter = request.getParameter("searchContent1").trim();
		
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getCountByClassifyInOne(classify, parameter));
		request.setAttribute("origin", service.getOriginByClassifyInOne(classify, parameter, page));
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("originCounts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("classify1", classify);
		request.setAttribute("parameter1", parameter);
		request.setAttribute("queryType", "singleSelect");
	
		request.getRequestDispatcher("WEB-INF\\Origin.jsp").forward(request, resp);
	}
	

}
