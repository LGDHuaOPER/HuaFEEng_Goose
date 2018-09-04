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
import com.eoulu.service.HardwareAdvancesService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.HardwareAdvancesServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;
@WebServlet("/Hardware")
public class HardwareServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public HardwareServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HardwareAdvancesService service = new HardwareAdvancesServiceImpl();
		Page page = new Page();
		int currentPage = Integer.parseInt(req.getParameter("currentPage")==null ? "1":req.getParameter("currentPage"));
		page.setCurrentPage(currentPage);
		page.setRows(10);
		page.setRecordCounts(service.getAllcounts());
		req.setAttribute("hardware", service.getHardwareAdvances(page));
		req.setAttribute("queryType", "common");
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("hardwareCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		new AccessStatistics().operateAccess(req, "装机进展");
		req.getRequestDispatcher("WEB-INF//hardware.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		super.doPost(req, resp);
	}
	

}
