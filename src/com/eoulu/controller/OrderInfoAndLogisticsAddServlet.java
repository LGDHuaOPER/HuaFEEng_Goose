package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.Logistics;
import com.eoulu.entity.OrderInfo;
import com.eoulu.service.OrderService;
import com.eoulu.service.impl.OrderServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class OrderInfoAndLogisticsAddServlet
 */
@WebServlet("/OrderInfoAndLogisticsAdd")
public class OrderInfoAndLogisticsAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderInfoAndLogisticsAddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		OrderInfo orderInfo = new OrderInfo();
		Logistics logistics = new Logistics();
		
		orderInfo.setOrderID(Integer.parseInt(request.getParameter("order_id").toString()));
		orderInfo.setEquipmentModel(Integer.parseInt(request.getParameter("model")));
		orderInfo.setNumber(Integer.parseInt(request.getParameter("equipment_counts")));
		orderInfo.setLogisticsNumber(orderInfo.getNumber());
		logistics.setOrderID(Integer.parseInt(request.getParameter("order_id").toString()));
		
		OrderService orderService = new OrderServiceImpl();
		if(orderService.OrderInfoAndLogisticsAdd(orderInfo, logistics)){
			response.getWriter().write(new Gson().toJson("{\"message\":\"success\"}"));
			return;
		}else{
			response.getWriter().write(new Gson().toJson("{\"message\":\"defeat\"}"));
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
