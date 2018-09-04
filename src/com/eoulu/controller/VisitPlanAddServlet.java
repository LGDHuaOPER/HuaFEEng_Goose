package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.VisitPlanService;
import com.eoulu.service.impl.VisitPlanserviceImpl;
import com.google.gson.Gson;

@WebServlet("/VisitPlanAdd")
public class VisitPlanAddServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		VisitPlanService service = new VisitPlanserviceImpl();
		
		String MachineDetailsID = request.getParameter("MachineDetailsID");
		String[] ID = request.getParameterValues("ID[]");
		String[] VisitName = request.getParameterValues("VisitName[]");
		String[] VisitTime = request.getParameterValues("VisitTime[]");
		String[] Engineer = request.getParameterValues("Engineer[]");
		String[] Details = request.getParameterValues("Details[]");
		boolean flag = true;
		for(int i=0;i<VisitName.length;i++) {
			if("-1".equals(ID[i])) {
				flag &=service.inset(Integer.parseInt(MachineDetailsID),VisitName[i],VisitTime[i],Engineer[i],Details[i]);
			}
		}
		response.getWriter().write(new Gson().toJson(flag));
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
