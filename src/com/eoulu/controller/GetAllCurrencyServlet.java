package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.RateCurrencyService;
import com.eoulu.service.impl.RateCurrencyServiceImpl;
import com.google.gson.Gson;
@WebServlet("/GetAllCurrency")
public class GetAllCurrencyServlet extends HttpServlet{

	private static final long serialVersionUID = 2276911908080781235L;
	
	public GetAllCurrencyServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		RateCurrencyService service = new RateCurrencyServiceImpl();
		resp.getWriter().write(new Gson().toJson(service.getAllData()));
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
