package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.TransportDirectiveService;
import com.eoulu.service.impl.TransportDirectiveServiceImpl;
import com.google.gson.Gson;
@WebServlet("/TransportDirectiveModel")
public class TransportDirectiveModelServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public TransportDirectiveModelServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TransportDirectiveService service = new TransportDirectiveServiceImpl();
		int id = Integer.parseInt(req.getParameter("ID"));
		resp.getWriter().write(new Gson().toJson(service.getModel(id)));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	

}
