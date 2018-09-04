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
import com.eoulu.service.OriginService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.OriginServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;
@WebServlet("/Origin")
public class OriginServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public OriginServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OriginService service = new OriginServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getAllCounts());
		req.setAttribute("queryType", "common");
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("originCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("origin", service.getOrigin(page));
		new AccessStatistics().operateAccess(req, "原产地证明");
		req.getRequestDispatcher("WEB-INF//Origin.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	
}
