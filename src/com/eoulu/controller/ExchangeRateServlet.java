package com.eoulu.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.eoulu.util.ExchangeRateQueryUtil;
import com.google.gson.Gson;

@WebServlet("/ExchangeRate")
public class ExchangeRateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ExchangeRateServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ExchangeRateQueryUtil util = new ExchangeRateQueryUtil();
		String scur = req.getParameter("scur");
		String tcur = req.getParameter("tcur");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
		String key = df.format(new Date());
		int count = 0;
		HashMap<String, int[]> intCounter = new HashMap<String, int[]>(); 
		if(intCounter.size() == 0){
			 intCounter.put(key, new int[] { 1 }); 
			 count = 1;
		}else{
			int[] counts = intCounter.get(key);
			counts[0]++;
			count = counts[0];
		}
		
		if(count > 50){
			Map<String,String> map = new HashMap<>();
			map.put("message", "本时段的访问次数已达到上限！");
			map.put("scur", scur);
			map.put("tcur", tcur);
			resp.getWriter().write(new Gson().toJson(map));
		}else{
			String url = util.getURL(scur, tcur);
			JSONObject obj = util.getReturn(url);
			String result = util.getResult(obj);
			Map<String,String> map = new HashMap<>();
			map.put("message", result);
			map.put("scur", scur);
			map.put("tcur", tcur);
			resp.getWriter().write(new Gson().toJson(map));
		}
		
	}
	

}
