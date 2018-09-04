package com.eoulu.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.eoulu.service.impl.QuotesServiceImpl;
import com.google.gson.Gson;

@WebServlet("/Normalize")
public class NormalizeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long beginTime = System.currentTimeMillis();
		String[] modelList = req.getParameterValues("ModelList[]");
		QuoteSystemService service = new QuoteSystemServiceImpl();
		Map<String, String[]> resultMap = service.getRaderByNames(modelList);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		System.out.println("花费时间为："+costTime);
		resp.getWriter().write(new Gson().toJson(resultMap));	
	}

	
	

}
