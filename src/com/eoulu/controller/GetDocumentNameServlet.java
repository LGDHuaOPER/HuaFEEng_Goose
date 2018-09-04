package com.eoulu.controller;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.DocumentModelService;
import com.eoulu.service.impl.DocumentModelServiceImpl;
import com.google.gson.Gson;

@WebServlet("/GetDocumentName")
public class GetDocumentNameServlet  extends HttpServlet{

	
	private static final long serialVersionUID = 1L;

	public GetDocumentNameServlet(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println(123);;
		DocumentModelService DocService = new DocumentModelServiceImpl();
	
		response.getWriter().write(new Gson().toJson(DocService.getAllFileName()));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}
}
