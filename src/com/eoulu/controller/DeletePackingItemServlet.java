package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LogInfoService;
import com.eoulu.service.PackingListService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.PackingListServiceImpl;
import com.google.gson.Gson;
@WebServlet("/DeletePackingItem")
public class DeletePackingItemServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	
	public DeletePackingItemServlet(){
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PackingListService service = new PackingListServiceImpl();
		int id = Integer.parseInt(request.getParameter("itemID"));
		boolean flag = service.deletePackingItem(id);
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "箱单页面";
			String description = "删除-配置信息"+id;
			log.insert(request, JspInfo, description);
		}
		
		response.getWriter().write(new Gson().toJson(flag));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
}
