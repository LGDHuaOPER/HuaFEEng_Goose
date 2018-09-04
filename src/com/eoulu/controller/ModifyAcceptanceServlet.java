package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.AcceptanceService;
import com.eoulu.service.impl.AcceptanceServiceImpl;
import com.google.gson.Gson;
@WebServlet("/ModifyAcceptance")
public class ModifyAcceptanceServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public ModifyAcceptanceServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AcceptanceService service = new AcceptanceServiceImpl();
		
		resp.getWriter().write(new Gson().toJson(service.updateAcceptance(req)));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	
}
