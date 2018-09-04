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
@WebServlet("/ProposalGoods")
public class ProposalGoodsServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public ProposalGoodsServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		InsuranceSlipService service = new InsuranceSlipServiceImpl();
		int id = Integer.parseInt(req.getParameter("ID"));
		resp.getWriter().write(new Gson().toJson(service.getInsuranceGoods(id)));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
