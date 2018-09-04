package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.OriginFactoryService;
import com.eoulu.service.impl.OriginFactoryServiceImpl;
import com.google.gson.Gson;
@WebServlet("/OriginFactoryOperate")
public class OriginFactoryOperateServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public OriginFactoryOperateServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OriginFactoryService service = new OriginFactoryServiceImpl();
		
		resp.getWriter().write(service.update(req));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OriginFactoryService service = new OriginFactoryServiceImpl();
		
		resp.getWriter().write(service.sendEmail(req));
	}
	

}
