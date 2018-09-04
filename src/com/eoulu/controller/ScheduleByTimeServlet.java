package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.ScheduleService;
import com.eoulu.service.impl.ScheduleServiceImpl;
import com.google.gson.Gson;
@WebServlet("/ScheduleByTime")
public class ScheduleByTimeServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public ScheduleByTimeServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ScheduleService service = new ScheduleServiceImpl();
		String name = req.getParameter("Name")==null?"":req.getParameter("Name");
		String startTime = req.getParameter("StartTime")==null?"0000-00-00":req.getParameter("StartTime");
		String endTime = req.getParameter("EndTime")==null?"0000-00-00":req.getParameter("EndTime");
		System.out.println(new Gson().toJson(service.getDataByTime(startTime, endTime,name)));
		resp.getWriter().write(new Gson().toJson(service.getDataByTime(startTime, endTime,name)));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	
	
}
