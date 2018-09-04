package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.CertificateQaulityService;
import com.eoulu.service.impl.CertificateQualityServiceImpl;
import com.google.gson.Gson;
@WebServlet("/QualityDelete")
public class QualityDeleteServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public QualityDeleteServlet(){
		super();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CertificateQaulityService service = new CertificateQualityServiceImpl();
		request.setCharacterEncoding("utf-8");
		int id = Integer.parseInt(request.getParameter("ID"));
		response.getWriter().write(new Gson().toJson(service.deleteCertificateQuality(id)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		


	}

}
