package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/GetProposalRoute")
public class GetProposalRouteServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public GetProposalRouteServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String str = req.getParameter("selected");
		switch(str){
		case "singleSelect":req.getRequestDispatcher("GetProposalByPageOne").forward(req, resp); break;
		case "mixSelect": req.getRequestDispatcher("GetProposalByPageTwo").forward(req, resp);
		}
	}

}
