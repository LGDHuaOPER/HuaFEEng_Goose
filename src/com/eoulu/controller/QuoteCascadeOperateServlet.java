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
@WebServlet("/QuoteCascadeOperate")
public class QuoteCascadeOperateServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public QuoteCascadeOperateServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QuoteSystemService service = new QuoteSystemServiceImpl();
		String Type = req.getParameter("Type")==null?"":req.getParameter("Type");
		if(Type.equals("Parts")){
			resp.getWriter().write(new Gson().toJson(service.operateCascadePO(req)));
		}else{
			resp.getWriter().write(new Gson().toJson(service.operateCascadePOComplete(req)));
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	

}
