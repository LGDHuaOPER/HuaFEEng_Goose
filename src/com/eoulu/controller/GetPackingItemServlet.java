package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.PackingListService;
import com.eoulu.service.impl.PackingListServiceImpl;
import com.google.gson.Gson;

@WebServlet("/GetPackingItem")
public class GetPackingItemServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	public GetPackingItemServlet(){
		super();
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("ID"));
	
		PackingListService service = new PackingListServiceImpl();
		response.getWriter().write(new Gson().toJson(service.getPackingItemByID(id)));

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
