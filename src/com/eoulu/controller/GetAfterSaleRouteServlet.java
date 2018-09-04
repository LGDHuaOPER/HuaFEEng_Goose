package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/GetAfterSaleRoute")
public class GetAfterSaleRouteServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public GetAfterSaleRouteServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	

		String str = req.getParameter("selected");
		
		switch(str){
		case "singleSelect":req.getRequestDispatcher("GetAfterSaleByPageOne").forward(req, resp); break;
		case "mixSelect": req.getRequestDispatcher("GetAfterSaleByPageTwo").forward(req, resp);
		}
	}
	
	

}
