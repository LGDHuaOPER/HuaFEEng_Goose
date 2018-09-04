package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.InstallationReportLogService;
import com.eoulu.service.impl.InstallationReportLogServiceImpl;
@WebServlet("/ReportLog")
public class ReportLogServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;
	public ReportLogServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InstallationReportLogService service = new InstallationReportLogServiceImpl();
		String area = req.getParameter("Area");
		String year = req.getParameter("Year");
		Page page = new Page();
		int currentPage = Integer.parseInt(req.getParameter("currentPage")==null ? "1":req.getParameter("currentPage"));
		page.setCurrentPage(currentPage);
		page.setRows(10);
		page.setRecordCounts(service.getAllcounts(area,year));
		req.setAttribute("logs", service.getInstallationReportLog(page, area, year));
		req.setAttribute("queryType", "common");
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("logsCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("year", year);
		req.setAttribute("area", area);
		req.getRequestDispatcher("WEB-INF//documentUpload.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	

}
