package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.util.TransportToolsUtil;
import com.google.gson.Gson;
@WebServlet("/GetFlightNo")
public class GetFlightNoServlet extends HttpServlet{

	public GetFlightNoServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String date = req.getParameter("date");
		String orgCity = req.getParameter("orgCity");
		String dstCity = req.getParameter("dstCity");
		String type = req.getParameter("Type");
		TransportToolsUtil util = new TransportToolsUtil();
		String result = null;
		if(type.equals("flight")){
			String url = util.getFlightUrl(orgCity, dstCity, date);
			result = util.getFlightResult(url);
		}else{
			String url = util.getUrl(orgCity, dstCity, date);
			result = util.getResult(url);
		}
		resp.getWriter().write(new Gson().toJson(result));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	
}
