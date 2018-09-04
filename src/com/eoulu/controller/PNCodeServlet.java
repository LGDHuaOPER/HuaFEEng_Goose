package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.AuthorityResource;
import com.eoulu.dao.InventoryDao;
import com.eoulu.service.InventoryService;
import com.eoulu.service.impl.InventoryServiceImpl;
import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Servlet implementation class PNCodeServlet
 */
@WebServlet("/PNCode")
public class PNCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PNCodeServlet() {
        super();
 
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String PNCode = request.getParameter("PNCode") == null?"":request.getParameter("PNCode");
		InventoryService service = new InventoryServiceImpl();
		response.getWriter().write(new Gson().toJson(service.getInventory(PNCode)));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean authority = AuthorityResource.isExist(request, "DeleteSchedule");
		if(!authority){
			response.getWriter().write("没有权限！");
		}
		String type = request.getParameter("Type") == null?"add":request.getParameter("Type");
		int CommodityID = request.getParameter("CommodityID")==null?0:Integer.parseInt(request.getParameter("CommodityID"));
		String PNCode = request.getParameter("PNCode") == null?"":request.getParameter("PNCode");
		InventoryService service = new InventoryServiceImpl();
		switch (type) {
		case "add":
			response.getWriter().write(service.addPNCode(CommodityID, PNCode));
			
			break;

		case "update":
			response.getWriter().write(new Gson().toJson(service.updatePNCode(CommodityID, PNCode)));
			break;
		}
	}

}
