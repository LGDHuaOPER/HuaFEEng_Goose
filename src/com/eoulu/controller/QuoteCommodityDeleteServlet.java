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
@WebServlet("/QuoteCommodityDelete")
public class QuoteCommodityDeleteServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public QuoteCommodityDeleteServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		QuoteSystemService service = new QuoteSystemServiceImpl();
		int id = Integer.parseInt(req.getParameter("CommodityID"));
		int quoteID = Integer.parseInt(req.getParameter("QuoteID"));
		String model = req.getParameter("Model");
		int commodityID = Integer.parseInt(req.getParameter("Commodity"));
	
		resp.getWriter().write(new Gson().toJson(service.deleteCommodityTemp(id,quoteID,commodityID,model)));
	
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	
	}
	
	

}
