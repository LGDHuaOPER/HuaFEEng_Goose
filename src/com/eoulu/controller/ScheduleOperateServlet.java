package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.AuthorityResource;
import com.eoulu.service.ScheduleService;
import com.eoulu.service.impl.ScheduleServiceImpl;
import com.google.gson.Gson;
@WebServlet("/ScheduleOperate")
public class ScheduleOperateServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	public ScheduleOperateServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ScheduleService service = new ScheduleServiceImpl();
		String str = req.getParameter("OperateType");
		switch (str) {
		case "Add":
			resp.getWriter().write(new Gson().toJson(service.insert(req)));
			break;

		case "Modify":resp.getWriter().write(new Gson().toJson(service.update(req)));
			break;
		case "Delete":
			resp.getWriter().write(new Gson().toJson(service.delete(req)));
			break;
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	
	
	
	

}
