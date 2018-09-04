package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.ReceivingService;
import com.eoulu.service.impl.ReceivingServiceImpl;
import com.google.gson.Gson;
@WebServlet("/ReceivingAdd")
public class ReceivingAddServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	public ReceivingAddServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ReceivingService service = new ReceivingServiceImpl();
		
		resp.getWriter().write(new Gson().toJson(service.addReceiving(req)));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	

}
