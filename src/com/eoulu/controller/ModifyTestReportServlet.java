package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.TestReportService;
import com.eoulu.service.impl.TestReportServiceImpl;
import com.google.gson.Gson;
@WebServlet("/ModifyTestReport")
public class ModifyTestReportServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public ModifyTestReportServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TestReportService service =new  TestReportServiceImpl();
		resp.getWriter().write(new Gson().toJson(service.updateTestReport(req)));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	doPost(req, resp);
	}
	
	
}
