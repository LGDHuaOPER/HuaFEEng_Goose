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
@WebServlet("/QuoteCascadePO")
public class QuoteCascadePOServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public QuoteCascadePOServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QuoteSystemService service = new QuoteSystemServiceImpl();
		int id = Integer.parseInt(req.getParameter("quoteID"));
		String Type = req.getParameter("Type")==null?"":req.getParameter("Type");
		if(Type.equals("Parts")){
			resp.getWriter().write(new Gson().toJson(service.getCascadePO(id)));
		}else{
			resp.getWriter().write(new Gson().toJson(service.getCascadeCompletePO(id)));
		}
		
//		System.out.println(service.getCascadePO(id).get(0).get("part"));
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	

}
