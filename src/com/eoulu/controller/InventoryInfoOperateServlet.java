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

/**
 * Servlet implementation class InventoryInfoOperate
 */
@WebServlet("/InventoryInfoOperate")
public class InventoryInfoOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InventoryInfoOperateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		InventoryService service = new InventoryServiceImpl();
		String classify = request.getParameter("classify")==null?"":request.getParameter("classify");
		switch (classify) {
		case "delete":
			response.getWriter().write(new Gson().toJson(service.deleteInventory(request)));
			break;
		case "check":
			String time = request.getParameter("CheckTime")==null?"":request.getParameter("CheckTime");
			response.getWriter().write(new Gson().toJson(service.updateLatestInventory(time)));
			break;
		default:
			response.getWriter().write(new Gson().toJson(service.operateStoreInfo(request)));
			break;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InventoryService service = new InventoryServiceImpl();
		String classify = request.getParameter("classify")==null?"":request.getParameter("classify");
		switch (classify) {
		case "delete":
			response.getWriter().write(new Gson().toJson(service.deleteCustomerOrder(request)));
			break;
		default:
			response.getWriter().write(new Gson().toJson(service.operateCustomerOrder(request)));
			break;
		}
	}

}
