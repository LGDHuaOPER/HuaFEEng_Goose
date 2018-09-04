package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.InsuranceDirectiveService;
import com.eoulu.service.impl.InsuranceDirectiveServiceImpl;
import com.google.gson.Gson;
@WebServlet("/InsuranceAdd")
public class InsuranceAddServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public InsuranceAddServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InsuranceDirectiveService service = new InsuranceDirectiveServiceImpl();
		resp.getWriter().write(new Gson().toJson(service.addInsuranceDirective(req)));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
