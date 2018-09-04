package com.eoulu.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.ScheduleService;
import com.eoulu.service.impl.ScheduleServiceImpl;
import com.google.gson.Gson;
@WebServlet("/ScheduleProvince")
public class ScheduleProvinceServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public ScheduleProvinceServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ScheduleService service = new ScheduleServiceImpl();
		System.out.println(req.getParameter("StartTime"));
		String name = req.getParameter("Name")==null?"":req.getParameter("Name");
		String startTime = req.getParameter("StartTime")==null?"0000-00-00":req.getParameter("StartTime");
		String endTime = req.getParameter("EndTime")==null?"0000-00-00":req.getParameter("EndTime");
		
		List<Map<String, Object>> ls = service.getProvinceOrder(startTime, endTime,name);
//		String name = new String(req.getParameter("Name").getBytes("ISO-8859-1"), "utf-8");
		
		resp.getWriter().write(new Gson().toJson(ls));
		
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	
	}
	

}
