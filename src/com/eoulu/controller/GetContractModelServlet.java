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
@WebServlet("/GetContractModel")
public class GetContractModelServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public GetContractModelServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	QuoteSystemService service = new QuoteSystemServiceImpl();
	String type = req.getParameter("Type");
	int id= Integer.parseInt(req.getParameter("ID"));
	switch(type){
	case "RMBContract":resp.getWriter().write(new Gson().toJson(service.getRMBModelInfo(id)));;break;
	case "USDContract":resp.getWriter().write(new Gson().toJson(service.getUSDModelInfo(id)));;break;
	}
	
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	
	

}
