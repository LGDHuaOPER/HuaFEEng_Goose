package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.FumigationService;
import com.eoulu.service.impl.FumigationServiceImpl;
@WebServlet("/GetFumigationByPageOne")
public class GetFumigationByPageOneServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	public GetFumigationByPageOneServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FumigationService service = new FumigationServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		String classify = req.getParameter("type1");
		System.out.println(classify);
		String parameter = req.getParameter("searchContent1").trim();
		System.out.println(parameter);
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getCountByClassifyInOne(classify, parameter));
		req.setAttribute("fumigation", service.getFumigationByClassifyInOne(classify, parameter, page));
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("fumigationCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("classify1", classify);
		req.setAttribute("parameter1", parameter);
		req.setAttribute("queryType", "singleSelect");
		req.getRequestDispatcher("WEB-INF//Fumigation.jsp").forward(req, resp);
	}
	

}
