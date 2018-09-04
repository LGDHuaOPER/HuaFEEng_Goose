package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.FumigationService;
import com.eoulu.service.impl.FumigationServiceImpl;
import com.google.gson.Gson;
@WebServlet("/FumigationAdd")
public class FumigationAddServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;
	public FumigationAddServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		FumigationService service = new FumigationServiceImpl();
		
		resp.getWriter().write(new Gson().toJson(service.addFumigation(req)));
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	

}
