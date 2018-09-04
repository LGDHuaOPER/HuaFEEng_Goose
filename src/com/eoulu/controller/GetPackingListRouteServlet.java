package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.PackingListService;
import com.eoulu.service.impl.PackingListServiceImpl;
import com.google.gson.Gson;
@WebServlet("/GetPackingListRoute")
public class GetPackingListRouteServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	public GetPackingListRouteServlet(){
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str = request.getParameter("selected");
		switch(str){
		case "singleSelect":request.getRequestDispatcher("GetPackingListByOne").forward(request, response); break;
		case "mixSelect": request.getRequestDispatcher("GetPackingListByTwo").forward(request, response);
		}
	}
	
	
}
