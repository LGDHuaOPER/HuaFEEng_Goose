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
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Servlet implementation class InventoryQueryServlet
 */
@WebServlet("/InventoryQuery")
public class InventoryQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InventoryQueryServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		InventoryService service = new InventoryServiceImpl();
		String classify = request.getParameter("classify")==null?"":request.getParameter("classify");
		switch (classify) {
		case "Customer":
			response.getWriter().write(new Gson().toJson(service.getAllCustomer(request)));
			break;
		case "CustomerOrder":
			response.getWriter().write(new Gson().toJson(service.getCustomerOrder(request)));
			break;
			
		default:
			response.getWriter().write(new Gson().toJson(service.getStoreInfo(request)));
			break;
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InventoryService service = new InventoryServiceImpl();
		String warehouseAddress = request.getParameter("Warehouse");
		int commodityID = request.getParameter("CommodityID") == null?0:Integer.parseInt(request.getParameter("CommodityID"));
		response.getWriter().write(new Gson().toJson(service.getInfoByWarehouse(warehouseAddress, commodityID)));
	}

}
