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
@WebServlet("/ModifyGoodsSize")
public class ModifyGoodsSizeServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	
	public ModifyGoodsSizeServlet(){
		super();
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PackingListService service = new PackingListServiceImpl();
		//response.getWriter().write(new Gson().toJson(service.ModifyGoodsSize(request)));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
}
