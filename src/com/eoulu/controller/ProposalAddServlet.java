package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.InsuranceSlipService;
import com.eoulu.service.impl.InsuranceSlipServiceImpl;
import com.google.gson.Gson;
@WebServlet("/ProposalAdd")
public class ProposalAddServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public ProposalAddServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InsuranceSlipService service = new InsuranceSlipServiceImpl();
		resp.getWriter().write(new Gson().toJson(service.addInsuranceSlip(req)));
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
