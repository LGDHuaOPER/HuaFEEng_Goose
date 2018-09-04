package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.InventoryService;
import com.eoulu.service.impl.InventoryServiceImpl;
import com.google.gson.Gson;


@WebServlet("/InventoryAdd")
public class InventoryAddServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InventoryAddServlet(){
		super();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		InventoryInfoService infoService = new InventoryInfoServiceImpl();
		//System.out.println(request.getParameter("Name"));
		//System.out.println(request.getParameter("Model"));
		System.out.println(request.getParameter("AddInfo"));
		System.out.println(request.getParameter("TelFax"));
		if(infoService.addInventoryInfo(request)){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "库存页面";
			String description = "新增-型号为"+request.getParameter("Model");
			log.insert(request, JspInfo, description);
			response.getWriter().write(new Gson().toJson("{'message':'添加成功'}"));
		}else{
			response.getWriter().write(new Gson().toJson("{'message':'添加失败'}"));
		}
		*/
		InventoryService service = new InventoryServiceImpl();
		response.getWriter().write(service.operate(request));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InventoryService service = new InventoryServiceImpl();
		response.getWriter().write(new Gson().toJson(service.QueryModel(request)));
		
	}

}
