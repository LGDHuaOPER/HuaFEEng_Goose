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
import com.eoulu.service.CertificateQaulityService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.CertificateQualityServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;
@WebServlet("/Quality")
public class QualityServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public QualityServlet(){
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CertificateQaulityService service = new CertificateQualityServiceImpl();
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getAllCounts());
		request.setAttribute("queryType", "common");
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("qualityCounts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("quality", service.getCertificateQuality(page));
		new AccessStatistics().operateAccess(request, "质量证明");
		request.getRequestDispatcher("WEB-INF//Quality.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
		

	}
}
