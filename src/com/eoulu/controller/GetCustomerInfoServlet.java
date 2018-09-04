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
@WebServlet("/GetCustomerInfo")
public class GetCustomerInfoServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public GetCustomerInfoServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		QuoteSystemService service = new QuoteSystemServiceImpl();
		String type = req.getParameter("Type");
		if(type.equals("common")){
			resp.getWriter().write(new Gson().toJson(service.getCustomerInfo()));
		}else{
			String classify = req.getParameter("classify");
			String param = req.getParameter("param");
			resp.getWriter().write(new Gson().toJson(service.getCustomerInfoByColumn(classify, param)));
		}
	
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
	

}
