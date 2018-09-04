package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.InstallationImageService;
import com.eoulu.service.InstallationManualService;
import com.eoulu.service.impl.InstallationImageServiceImpl;
import com.eoulu.service.impl.InstallationManualServiceImpl;
@WebServlet("/InstallationImage")
public class InstallationImageServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;
	public InstallationImageServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InstallationImageService service = new InstallationImageServiceImpl();
		
		Page page = new Page();
		int currentPage = Integer.parseInt(req.getParameter("currentPage")==null ? "1":req.getParameter("currentPage"));
		page.setCurrentPage(currentPage);
		page.setRows(10);
		page.setRecordCounts(service.getAllcounts());
		req.setAttribute("image", service.getInstallationImage(page));
		req.setAttribute("queryType", "common");
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("imageCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		
		req.getRequestDispatcher("WEB-INF//documentUpload.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	

}
