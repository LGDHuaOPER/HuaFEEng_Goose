package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.SalesWeightService;
import com.eoulu.service.impl.SalesWeightServiceImpl;
import com.google.gson.Gson;

@WebServlet("/SaveSaleWeight")
public class SaveSaleWeightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ID = request.getParameter("ID");
		String UnitPrice = request.getParameter("UnitPrice");
		String Quantity = request.getParameter("Quantity");
		String Value = request.getParameter("Value");
		String Client = request.getParameter("Client");
		String Shipping = request.getParameter("Shipping");
		SalesWeightService service2 = new SalesWeightServiceImpl();
		Boolean result = service2.update(Integer.parseInt(ID),Double.parseDouble(UnitPrice),Double.parseDouble(Quantity),Double.parseDouble(Value),Double.parseDouble(Client),Double.parseDouble(Shipping));
		response.getWriter().write(new Gson().toJson(result));
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
