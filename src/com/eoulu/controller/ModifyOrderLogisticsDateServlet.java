package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.Order;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.OrderService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.OrderServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class ModifyOrderLogisticsDateServlet
 */
@WebServlet("/ModifyOrderLogisticsDate")
public class ModifyOrderLogisticsDateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyOrderLogisticsDateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Order order = new Order(); 
		String actual_delivery = request.getParameter("actual_delivery");
		
		if(request.getParameter("actual_delivery").equals("")|| request.getParameter("actual_delivery").equals(null)){
			actual_delivery = "0000-00-00";
		}
		System.out.println("实际货期"+actual_delivery);
		String expected_delivery_period = request.getParameter("expected_delivery_period");
		if(request.getParameter("expected_delivery_period").equals("")||request.getParameter("expected_delivery_period").equals(null)){
			expected_delivery_period = "0000-00-00";
		}
		System.out.println("预计货期"+expected_delivery_period);
		System.out.println(request.getParameter("order_id"));
		try{order.setID(Integer.parseInt(request.getParameter("order_id").toString()));}catch(Exception e){}
		try{order.setActualDelivery(actual_delivery);}catch(Exception e){}
		try{order.setExpectedDeliveryPeriod(expected_delivery_period);}catch(Exception e){}
		
		OrderService orderService = new OrderServiceImpl();
		if(orderService.modifyLogisticsTime(order)){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "物流页面";
			String description = "修改订单-实际货期与预计货期"+request.getParameter("order_id");
			log.insert(request, JspInfo, description);
			response.getWriter().write(new Gson().toJson(orderService.getOrderByID(order.getID()+"")));
			return;
		}else{
			response.getWriter().write(new Gson().toJson("{message:修改是失败}"));
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
