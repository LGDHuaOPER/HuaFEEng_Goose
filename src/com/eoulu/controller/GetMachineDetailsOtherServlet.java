package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.MachineDetailsService;
import com.eoulu.service.impl.MachineDetailsServiceImpl;
import com.google.gson.Gson;
@WebServlet("/GetMachineDetailsOther")
public class GetMachineDetailsOtherServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public GetMachineDetailsOtherServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MachineDetailsService service = new MachineDetailsServiceImpl();
		resp.getWriter().write(new Gson().toJson(service.getConfigure(req.getParameter("ContractNO"))));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	
}
