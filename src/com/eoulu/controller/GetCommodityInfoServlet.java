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
@WebServlet("/GetCommodityInfo")
public class GetCommodityInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public GetCommodityInfoServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		QuoteSystemService service = new QuoteSystemServiceImpl();
		String type = req.getParameter("Type");
		String classify = req.getParameter("classify");
		if(type.equals("common")){
			resp.getWriter().write(new Gson().toJson(service.getCommodityInfo()));
		}else if(classify.equals("型号")){
			String model = req.getParameter("param");
			resp.getWriter().write(new Gson().toJson(service.getCommodityInfoByModel(model)));
		}else{
			String name = req.getParameter("param");
			resp.getWriter().write(new Gson().toJson(service.getCommodityInfoByTitle(name)));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
