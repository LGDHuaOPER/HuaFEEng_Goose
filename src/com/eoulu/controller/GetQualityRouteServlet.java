package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.CertificateQaulityService;
import com.eoulu.service.impl.CertificateQualityServiceImpl;
import com.google.gson.Gson;
@WebServlet("/GetQualityRoute")
public class GetQualityRouteServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;

	public GetQualityRouteServlet(){
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		String str = request.getParameter("selected");
		System.out.println(str);
		switch(str){
		case "singleSelect":request.getRequestDispatcher("GetQualityByPageOne").forward(request, response); break;
		case "mixSelect": request.getRequestDispatcher("GetQualityByPageTwo").forward(request, response);
		}

	}

}
