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

@WebServlet("/SalesWeight")
public class SalesWeightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SalesWeightServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SalesWeightService service = new SalesWeightServiceImpl();
		response.getWriter().write(new Gson().toJson(service.getSalesWeight()));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
