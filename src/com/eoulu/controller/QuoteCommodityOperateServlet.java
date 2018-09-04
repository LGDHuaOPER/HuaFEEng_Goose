package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.CommodityService;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.CommodityServiceImpl;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.google.gson.Gson;
@WebServlet("/QuoteCommodityOperate")
public class QuoteCommodityOperateServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public QuoteCommodityOperateServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//		QuoteSystemService service = new QuoteSystemServiceImpl();
		CommodityService service = new CommodityServiceImpl();
		resp.getWriter().write(new Gson().toJson(service.operateCommodityInfo(req)));
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	

}
