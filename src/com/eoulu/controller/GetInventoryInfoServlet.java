package com.eoulu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.InventoryInfoService;
import com.eoulu.service.impl.InventoryInfoServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class GetInventoryInfoServlet
 */
@WebServlet("/GetInventoryInfo")
public class GetInventoryInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetInventoryInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String classify = request.getParameter("type");
		System.out.println(classify);
		int equipmentID = Integer.parseInt(request.getParameter("equipment_id"));
		//System.out.println(equipmentID);
		String Model=request.getParameter("Model");
		//System.out.println(Model);
		InventoryInfoService inventoryInfoService = new InventoryInfoServiceImpl();
		List<Map<String,Object>> ls = null;
		
		switch(classify){
		case "in":ls = inventoryInfoService.getInInventoryInfo1(equipmentID, 1,Model);break;
		case "out":ls = inventoryInfoService.getInInventoryInfo(equipmentID, 2);;break;
		}
		
		response.getWriter().write(new Gson().toJson(ls));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
