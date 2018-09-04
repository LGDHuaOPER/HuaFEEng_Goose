package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.MachineDetailsService;
import com.eoulu.service.impl.MachineDetailsServiceImpl;
@WebServlet("/GetMachineDetailsRoute")
public class GetMachineDetailsRouteServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public GetMachineDetailsRouteServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		

		String str = req.getParameter("selected");
		System.out.println("str:"+str);
		switch(str){
		case "singleSelect":req.getRequestDispatcher("GetMachineDetailsByPageOne").forward(req, resp); break;
		case "mixSelect": req.getRequestDispatcher("GetMachineDetailsByPageTwo").forward(req, resp);
		}
	}
	

}
