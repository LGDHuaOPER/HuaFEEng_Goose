package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.google.gson.Gson;
@WebServlet("/QuoteOtherRMBPO")
public class QuoteOtherRMBPOServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public QuoteOtherRMBPOServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QuoteSystemService service = new QuoteSystemServiceImpl();
		int id = Integer.parseInt(req.getParameter("QuoteID"));
		System.out.println(service.getOtherRMBPO(id));
		resp.getWriter().write(new Gson().toJson(service.getOtherRMBPO(id)));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	}
	

}
