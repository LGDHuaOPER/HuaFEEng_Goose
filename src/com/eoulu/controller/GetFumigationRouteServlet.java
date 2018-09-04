package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/GetFumigationRoute")
public class GetFumigationRouteServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;
	public GetFumigationRouteServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String select = req.getParameter("selected");
		switch(select){
		case "singleSelect":req.getRequestDispatcher("GetFumigationByPageOne").forward(req, resp); break;
		case "mixSelect": req.getRequestDispatcher("GetFumigationByPageTwo").forward(req, resp);
		}
	}
	

}
