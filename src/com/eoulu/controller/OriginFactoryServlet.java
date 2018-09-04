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
import com.eoulu.service.OriginFactoryService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.OriginFactoryServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;
@WebServlet("/OriginFactory")
public class OriginFactoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public OriginFactoryServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OriginFactoryService service = new OriginFactoryServiceImpl();
		
		Page page = new Page();
		int currentPage = Integer.parseInt(req.getParameter("currentPage")==null ? "1":req.getParameter("currentPage"));
		page.setCurrentPage(currentPage);
		page.setRows(10);
		page.setRecordCounts(service.getAllCounts());
		req.setAttribute("POInfo", service.getAllData(page));
		req.setAttribute("queryType", "common");
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("factoryCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		new AccessStatistics().operateAccess(req, "FORMFACTOR");
		req.getRequestDispatcher("WEB-INF//OriginFactory.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	
	}
	
	
}
