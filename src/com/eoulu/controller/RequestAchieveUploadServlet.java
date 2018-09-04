package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.RequestAchieveService;
import com.eoulu.service.impl.RequestAchieveServiceImpl;
import com.google.gson.Gson;
@WebServlet("/RequestAchieveUpload")
public class RequestAchieveUploadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public RequestAchieveUploadServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		RequestAchieveService service = new RequestAchieveServiceImpl();
		
		resp.getWriter().write(new Gson().toJson(service.moreRequestAchieveAdd(req)));
	}
	

}
