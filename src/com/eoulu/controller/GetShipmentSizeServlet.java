package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.ShipmentService;
import com.eoulu.service.impl.ShipmentServiceImpl;
import com.google.gson.Gson;
@WebServlet("/GetShipmentSize")
public class GetShipmentSizeServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	public GetShipmentSizeServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ShipmentService service = new ShipmentServiceImpl();
		int id = Integer.parseInt(req.getParameter("ID"));
	
		resp.getWriter().write(new Gson().toJson(service.getShipmentSize(id)));
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	doPost(req, resp);
	}

}
