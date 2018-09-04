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
@WebServlet("/AcceptanceDelete")
public class AcceptanceDeleteServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;

	public AcceptanceDeleteServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AcceptanceService service = new AcceptanceServiceImpl();
		int id = Integer.parseInt(req.getParameter("ID"));
		resp.getWriter().write(new Gson().toJson(service.deleteAcceptance(id)));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	
}
