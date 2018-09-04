package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.Logistics;
import com.eoulu.entity.OrderInfo;
import com.eoulu.service.InventoryInfoService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.LogisticService;
import com.eoulu.service.impl.InventoryInfoServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.LogisticServiceImpl;
import com.eoulu.util.MethodUtil;
import com.google.gson.Gson;

@WebServlet("/ModifyOrderInfoAlone")
public class ModifyOrderInfoAloneServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyOrderInfoAloneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//单独修改每一项记录
		Logistics logistics = new Logistics();
		OrderInfo orderInfo = new OrderInfo();
		LogisticService logisticService = new LogisticServiceImpl();
		InventoryInfoService inventoryInfoService = new InventoryInfoServiceImpl();

		logistics.setOrderInfoID(Integer.parseInt(request.getParameter("id")));
		logistics.setPONO(request.getParameter("po_no"));
		logistics.setSONO(request.getParameter("so_no"));
		try{logistics.setPOAmount(Integer.parseInt(request.getParameter("po_amount")));}catch(Exception e){}
		try{logistics.setRMBPOAmount(Integer.parseInt(request.getParameter("rmb_po_amount")));}catch(Exception e){}
		logistics.setSupplier(Integer.parseInt(request.getParameter("supplier").toString()));
		logistics.setFactoryShipment(request.getParameter("factory_shipment"));
		logistics.setEstimatedPaymentTime(request.getParameter("estimated_payment_time"));
		logistics.setActualPaymentTime(request.getParameter("actual_payment_time"));
		
		orderInfo.setID(Integer.parseInt(request.getParameter("id")));
		orderInfo.setExceptDate(request.getParameter("except_date"));
		orderInfo.setDate(request.getParameter("date"));
		orderInfo.setDeliveryNumber(request.getParameter("delivery_number"));
		//try{orderInfo.setStockNumber(Integer.parseInt(request.getParameter("stock_number")));}catch(Exception e){}
		try{orderInfo.setLogisticsNumber(Integer.parseInt(request.getParameter("logistics_number")));}catch(Exception e){}
		orderInfo.setStatus(Integer.parseInt(request.getParameter("status")));
		
		if(logisticService.orderInfoModify2(orderInfo, logistics)){
			String factoryDate = logistics.getFactoryShipment();
			String actureDate = orderInfo.getDate();
			//
			if(!factoryDate.equals("")){
				inventoryInfoService.importInInventoryInfo(logistics.getOrderInfoID(),logistics,orderInfo);
			}
			if(!actureDate.equals("")){
				inventoryInfoService.importOutInventoryInfo(logistics.getOrderInfoID(),logistics,orderInfo);
			}
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "物流页面";
			String description = "修改订单-第一条"+request.getParameter("po_no");
			log.insert(request, JspInfo, description);
			response.getWriter().write(new Gson().toJson("{\"message\":\"true\"}"));
		}else{
			response.getWriter().write(new Gson().toJson("{\"message\":\"false\"}"));
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
