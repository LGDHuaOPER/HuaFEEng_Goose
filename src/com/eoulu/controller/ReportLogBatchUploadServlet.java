package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.InstallationReportLogService;
import com.eoulu.service.impl.InstallationReportLogServiceImpl;
import com.google.gson.Gson;

@WebServlet("/ReportLogBatchUpload")
public class ReportLogBatchUploadServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;
	public ReportLogBatchUploadServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		InstallationReportLogService service = new InstallationReportLogServiceImpl();
		
		resp.getWriter().write(new Gson().toJson(service.moreReportLogAdd(req)));
	}
	

}
