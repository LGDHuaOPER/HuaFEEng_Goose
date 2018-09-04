package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.CertificateQaulityService;
import com.eoulu.service.impl.CertificateQualityServiceImpl;

@WebServlet("/GetQualityByPageOne")
public class GetQualityByPageOneServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	
	public GetQualityByPageOneServlet(){
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CertificateQaulityService service = new CertificateQualityServiceImpl();
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		String classify = request.getParameter("type1");
		
		String parameter = request.getParameter("searchContent1").trim();
		
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getCountByClassifyInOne(classify, parameter));
		request.setAttribute("quality", service.getCertificateQualityByClassifyInOne(classify, parameter, page));
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("qualityCounts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("classify1", classify);
		request.setAttribute("parameter1", parameter);
		request.setAttribute("queryType", "singleSelect");
	
		request.getRequestDispatcher("WEB-INF\\Quality.jsp").forward(request, response);
		
	}
	
	
}
