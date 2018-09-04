package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/GetQuantityWeightRoute")
public class GetQuantityWeightRouteServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public GetQuantityWeightRouteServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String select = req.getParameter("selected");
		switch(select){
		case "singleSelect":req.getRequestDispatcher("GetQuantityWeightByPageOne").forward(req, resp); break;
		case "mixSelect": req.getRequestDispatcher("GetQuantityWeightByPageTwo").forward(req, resp);
		}
	}
	
	
	
}
