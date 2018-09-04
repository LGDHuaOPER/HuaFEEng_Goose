package com.eoulu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.google.gson.Gson;

@WebServlet("/OriginalData")
public class OriginalDataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String productCategory = req.getParameter("ProductCategory")==null?"":req.getParameter("ProductCategory");
		String model = req.getParameter("Model")==null?"":req.getParameter("Model");
		String[] result;
		Map<String, String[]> resultMap = new HashMap<>();
		QuoteSystemService service = new QuoteSystemServiceImpl();
		if(model.equals("All")){
			resultMap = service.getDataByNames(productCategory);
			resp.getWriter().write(new Gson().toJson(resultMap));
		}else if(model.equals("")){
			result = service.getDataByName(productCategory);
			resp.getWriter().write(new Gson().toJson(result));
		}else{
			result = service.getDataByName(model);
			resp.getWriter().write(new Gson().toJson(result));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String startTime = req.getParameter("StartTime");
		String endTime = req.getParameter("endTime");
		
		QuoteSystemService service = new QuoteSystemServiceImpl();
		resp.getWriter().write(service.setRadaTime(startTime, endTime));
		
		
	}



}
