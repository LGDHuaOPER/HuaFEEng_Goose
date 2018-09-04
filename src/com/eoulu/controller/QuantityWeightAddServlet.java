package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.QuantityWeightService;
import com.eoulu.service.impl.QuantityWeightServiceImpl;
import com.google.gson.Gson;
@WebServlet("/QuantityWeightAdd")
public class QuantityWeightAddServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public QuantityWeightAddServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QuantityWeightService service = new QuantityWeightServiceImpl();
		
		resp.getWriter().write(new Gson().toJson(service.addQuantityWeight(req)));
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doPost(req, resp);
	}
	

}
