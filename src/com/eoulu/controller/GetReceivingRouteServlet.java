package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.ReceivingService;
import com.eoulu.service.impl.ReceivingServiceImpl;
@WebServlet("/GetReceivingRoute")
public class GetReceivingRouteServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public GetReceivingRouteServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String select = req.getParameter("selected");
		
		switch(select){
		case "singleSelect":req.getRequestDispatcher("GetReceivingByPageOne").forward(req, resp); break;
		case "mixSelect": req.getRequestDispatcher("GetReceivingByPageTwo").forward(req, resp);
		}
		
	}
	
	

}
