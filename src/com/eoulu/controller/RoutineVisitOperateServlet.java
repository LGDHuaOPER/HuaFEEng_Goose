package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.RoutineVisitService;
import com.eoulu.service.impl.RoutineVisitServiceImpl;
import com.google.gson.Gson;
@WebServlet("/RoutineVisitOperate")
public class RoutineVisitOperateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public RoutineVisitOperateServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RoutineVisitService service = new RoutineVisitServiceImpl();
		resp.getWriter().write(new Gson().toJson(service.operate(req)));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	
}
