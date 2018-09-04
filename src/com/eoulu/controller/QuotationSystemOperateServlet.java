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
@WebServlet("/QuotationSystemOperate")
public class QuotationSystemOperateServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	public QuotationSystemOperateServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		QuoteSystemService service = new QuoteSystemServiceImpl();
		String type = req.getParameter("Type");
		switch(type.toString()){
		case "Add":resp.getWriter().write(new Gson().toJson(service.insert(req)));break;
		case "Modify":resp.getWriter().write(new Gson().toJson(service.update(req)));break;
		}
	
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
