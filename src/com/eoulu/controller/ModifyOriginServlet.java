package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.OriginService;
import com.eoulu.service.impl.OriginServiceImpl;
import com.google.gson.Gson;
@WebServlet("/ModifyOrigin")
public class ModifyOriginServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;
	public ModifyOriginServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OriginService service = new OriginServiceImpl();
		
		resp.getWriter().write(new Gson().toJson(service.updateOrigin(req)));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	

}
