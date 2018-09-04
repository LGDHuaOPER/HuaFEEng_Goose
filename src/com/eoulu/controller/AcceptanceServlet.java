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
import com.eoulu.service.AcceptanceService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.LoginService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.AcceptanceServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;
@WebServlet("/Acceptance")
public class AcceptanceServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	public AcceptanceServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AcceptanceService service = new AcceptanceServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getAllCounts());
		req.setAttribute("queryType", "common");
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("acceptanceCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("acceptance", service.getAcceptance(page));
		new AccessStatistics().operateAccess(req, "FAT");
		req.getRequestDispatcher("WEB-INF//FAT.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	

}
