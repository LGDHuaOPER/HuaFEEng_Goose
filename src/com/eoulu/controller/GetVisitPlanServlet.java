package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.impl.VisitPlanserviceImpl;
import com.google.gson.Gson;

@WebServlet("/GetVisitPlan")
public class GetVisitPlanServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ID = request.getParameter("ID");
		response.getWriter().write(new Gson().toJson(new VisitPlanserviceImpl().GetVisitPlan(Integer.parseInt(ID))));
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
