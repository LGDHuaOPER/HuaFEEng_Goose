package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.InsuranceDirectiveService;
import com.eoulu.service.impl.InsuranceDirectiveServiceImpl;
@WebServlet("/GetInsuranceByPageOne")
public class GetInsuranceByPageOneServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public GetInsuranceByPageOneServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InsuranceDirectiveService service = new InsuranceDirectiveServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		String classify = new String(req.getParameter("type1").getBytes("iso-8859-1"),"UTF-8");
		System.out.println(classify);
		String parameter =new String(req.getParameter("searchContent1").getBytes("iso-8859-1"),"UTF-8");
		System.out.println(parameter);
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getCountByClassifyInOne(classify, parameter));
		req.setAttribute("insurance", service.getInsuranceDirectiveByClassifyInOne(classify, parameter, page));
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("insuranceCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("classify1", classify);
		req.setAttribute("parameter1", parameter);
		req.setAttribute("queryType", "singleSelect");
		req.getRequestDispatcher("WEB-INF//Insurance.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InsuranceDirectiveService service = new InsuranceDirectiveServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		String classify = req.getParameter("type1");
		System.out.println(classify);
		String parameter = req.getParameter("searchContent1").trim();
		System.out.println(parameter);
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getCountByClassifyInOne(classify, parameter));
		req.setAttribute("insurance", service.getInsuranceDirectiveByClassifyInOne(classify, parameter, page));
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("insuranceCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("classify1", classify);
		req.setAttribute("parameter1", parameter);
		req.setAttribute("queryType", "singleSelect");
		req.getRequestDispatcher("WEB-INF//Insurance.jsp").forward(req, resp);
		
	}

}
