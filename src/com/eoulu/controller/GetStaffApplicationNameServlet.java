package com.eoulu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.StaffInfoService;
import com.eoulu.service.impl.StaffInfoServiceImpl;
import com.google.gson.Gson;


@WebServlet("/GetStaffApplicationName")
public class GetStaffApplicationNameServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		StaffInfoService service = new StaffInfoServiceImpl();
		List<Map<String, Object>> list = service.getStaffName(req);
		resp.getWriter().write(new Gson().toJson(list));
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	

}
