package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.RequirementService;
import com.eoulu.service.impl.RequirementServiceImpl;
import com.google.gson.Gson;
@WebServlet("/SendRequirementEmail")
public class SendRequirementEmailServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	
	public SendRequirementEmailServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequirementService service = new RequirementServiceImpl();
		
		resp.getWriter().write(new Gson().toJson(service.sendEmail(req)));
		
	}

}
