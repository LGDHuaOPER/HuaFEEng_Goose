package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LogInfoService;
import com.eoulu.service.OrderService;
import com.eoulu.service.TransportService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.OrderServiceImpl;
import com.eoulu.service.impl.TransportServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class ModifyContractStatusServlet
 */
@WebServlet("/ModifyContractStatus")
public class ModifyContractStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyContractStatusServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean flag = false;
		TransportService transportService = new TransportServiceImpl();
		OrderService orderService = new OrderServiceImpl();
		
		
		int orderID = Integer.parseInt(request.getParameter("order_id"));
		int status = Integer.parseInt(request.getParameter("status"));
		
		
		
		flag = transportService.modifyContractStatus(orderID, status);
		
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "物流页面";
			String description = "修改-订单状态"+orderID+"为"+status;
			log.insert(request, JspInfo, description);
		}
		
		
		response.getWriter().write(new Gson().toJson(orderService.getOrderByID(orderID+"")));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
