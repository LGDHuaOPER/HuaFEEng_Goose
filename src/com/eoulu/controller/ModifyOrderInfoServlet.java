package com.eoulu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


/**
 * Servlet implementation class ModifyOrderInfoServlet
 */
@WebServlet("/ModifyOrderInfo")
public class ModifyOrderInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyOrderInfoServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		Logistics logistics = new Logistics();
		OrderInfo orderInfo = new OrderInfo();
		LogisticService logisticService = new LogisticServiceImpl();
		InventoryInfoService inventoryInfoService = new InventoryInfoServiceImpl();
		String[] columns = request.getParameterValues("columns[]");//接收数组，存放字段
//		System.out.println("shuzu"+Arrays.toString(columns));
//		System.out.println(columns);
		List<String> ls = new ArrayList<>();//存放物流被修改字段
		List<String> ol = new ArrayList<>();//存放订单信息被修改字段
		List<String> boo = new ArrayList<>();
		String[] param1 = null;
		String[] param2 = null;
		if(columns != null){
			System.out.println(123);
			for(int i=0 ; i<columns.length ; i++){
				if(columns[i].equals("POAmount")||columns[i].equals("RMBPOAmount")||columns[i].equals("PONO")||columns[i].equals("SONO")||columns[i].equals("Supplier")||columns[i].equals("FactoryShipment")||columns[i].equals("EstimatedPaymentTime")||columns[i].equals("ActualPaymentTime")){
					ls.add(columns[i]);
				}
				if(columns[i].equals("ExceptDate")||columns[i].equals("Date")||columns[i].equals("DeliveryNumber")||columns[i].equals("LogisticsNumber")||columns[i].equals("Status")){
					ol.add(columns[i]);
				}
			}
			ls.add("ID");
			ol.add("ID");
			
			param1 = new String[ls.size()];;//物流被修改字段
			
			for(int i=0;i<param1.length;i++){
				if(ls.get(i).equals("ID")){
					param1[param1.length-1] = ls.get(i);
				}else{
					param1[i] = ls.get(i);
				}
				
			}
			
			param2 = new String[ol.size()];//订单被修改字段
			for(int i=0;i<param2.length;i++){
				System.out.println(ol.get(i));
				if(ol.get(i).equals("ID")){
					param2[param2.length-1] = ol.get(i);
				}else{
					param2[i] = ol.get(i);
				}
				
			}
		}
		
		System.out.println("ls:"+Arrays.toString(param1)+";"+"ol:"+Arrays.toString(param2));
		String str  =request.getParameter("id");
		MethodUtil mu = new MethodUtil();
		int[] ids = mu.StringtoInt(str);
		System.out.println(Arrays.toString(ids));
		if(columns != null){
			for(int i = 0; i < ids.length; i++){
				logistics.setOrderInfoID(ids[i]);
				orderInfo.setID(ids[i]);
				for(int j = 0 ; j < columns.length ; j++){
					//物流
					if(columns[j].equals("POAmount")){
						int po_amount = Integer.parseInt(request.getParameter("po_amount").equals("")?"0":request.getParameter("po_amount"));
						if(i == 0){
							logistics.setPOAmount(po_amount);
						}else{
							logistics.setPOAmount(0);
						}
						
					}
					if(columns[j].equals("RMBPOAmount")){
						int rmb_po_amount =Integer.parseInt(request.getParameter("rmb_po_amount").equals("")?"0":request.getParameter("rmb_po_amount"));
					
						if(i == 0){
							logistics.setRMBPOAmount(rmb_po_amount);
						}else{
							logistics.setRMBPOAmount(0);
						}
					}
					if(columns[j].equals("PONO")){
						String po_no = request.getParameter("po_no");//PO-NO
						
						if(po_no.equals("--")){
							po_no = "";
						}
						logistics.setPONO(po_no);
					}
					if(columns[j].equals("SONO")){
						String so_no = request.getParameter("so_no");//SO-NO
						if(so_no.equals("--")){
							so_no = "";
						}
						logistics.setSONO(so_no);
					}
					if(columns[j].equals("Supplier")){
						
						int supplier =0;
						if(request.getParameter("supplier").equals("--")){
							supplier = 0;
						}else{
							supplier = Integer.parseInt(request.getParameter("supplier").toString());
						}
						
						logistics.setSupplier(supplier);
					}
					if(columns[j].equals("FactoryShipment")){
						String factory_shipment = request.getParameter("factory_shipment");
						
						if(factory_shipment.equals("--")){
							factory_shipment = "";
						}
						logistics.setFactoryShipment(factory_shipment);
					}
					if(columns[j].equals("EstimatedPaymentTime")){
						String estimated_payment_time = request.getParameter("estimated_payment_time");//预计付款时间
						logistics.setEstimatedPaymentTime(estimated_payment_time);
					}
					if(columns[j].equals("ActualPaymentTime")){
						String actual_payment_time = request.getParameter("actual_payment_time");//实际付款时间
						logistics.setActualPaymentTime(actual_payment_time);
					}
					//订单信息
					if(columns[j].equals("ExceptDate")){
						String except_date = request.getParameter("except_date");//预计货期
						orderInfo.setExceptDate(except_date);
					}
					if(columns[j].equals("Date")){
						String date = request.getParameter("date");//实际货期
						orderInfo.setDate(date);
					}
					if(columns[j].equals("DeliveryNumber")){
						String delivery_number = request.getParameter("delivery_number");//货运单号
						orderInfo.setDeliveryNumber(delivery_number);
					}
					if(columns[j].equals("LogisticsNumber")){
						int logistics_number = 0;	
						if(request.getParameter("logistics_number").equals("--")){
							logistics_number = 0;	
						}else{
							logistics_number = Integer.parseInt(request.getParameter("logistics_number"));
						}
						orderInfo.setLogisticsNumber(logistics_number);
					}
					if(columns[j].equals("Status")){
						int status = 6;
						if(request.getParameter("status").equals("--")){
							status = 6;
						}else{
							status = Integer.parseInt(request.getParameter("status"));//订单状态-6
						}
						orderInfo.setStatus(status);
					}
					
				}
				
				if(logisticService.orderInfoModify(orderInfo, logistics,param1,param2)){
					if(ls.contains("FactoryShipment")){
						String factoryDate = logistics.getFactoryShipment();
						if(!factoryDate.equals("")){
							inventoryInfoService.importInInventoryInfo(logistics.getOrderInfoID(),logistics,orderInfo);
						}
					}
					
					if(ol.contains("Date")){
						String actureDate = orderInfo.getDate();
						
						if(!actureDate.equals("")){
							inventoryInfoService.importOutInventoryInfo(logistics.getOrderInfoID(),logistics,orderInfo);
						}
					}
					
					boo.add("true");
					LogInfoService log = new LogInfoServiceImpl();
					String JspInfo = "物流页面";
					String description = "修改订单";
					log.insert(request, JspInfo, description);
				}else{
					boo.add("false");
				}
			}
		}
		if(!boo.contains("false")){
		
			response.getWriter().write(new Gson().toJson("{\"message\":\"true\"}"));
		}else{
			response.getWriter().write(new Gson().toJson("{\"message\":\"false\"}"));
		}
		/*
		int po_amount = Integer.parseInt(request.getParameter("po_amount"));
		
		int rmb_po_amount =Integer.parseInt(request.getParameter("rmb_po_amount"));
	
		String po_no = request.getParameter("po_no");//PO-NO
		if(po_no.equals("--")){
			po_no = "";
		}
		
		
		String so_no = request.getParameter("so_no");//SO-NO
		if(so_no.equals("--")){
			so_no = "";
		}
		
		int supplier =0;
		if(request.getParameter("supplier").equals("--")){
			supplier = 0;
		}else{
			supplier = Integer.parseInt(request.getParameter("supplier").toString());
		}
		
		String factory_shipment = request.getParameter("factory_shipment");
		if(factory_shipment.equals("--")){
			factory_shipment = "";
		}
		
		String estimated_payment_time = request.getParameter("estimated_payment_time");//预计付款时间
		String actual_payment_time = request.getParameter("actual_payment_time");//实际付款时间
		String except_date = request.getParameter("except_date");//预计货期
		String date = request.getParameter("date");//实际货期
		String delivery_number = request.getParameter("delivery_number");//货运单号
	
		int logistics_number = 0;	
		if(request.getParameter("logistics_number").equals("--")){
			logistics_number = 0;	
		}else{
			logistics_number = Integer.parseInt(request.getParameter("logistics_number"));
		}
		int status = 6;
		if(request.getParameter("status").equals("--")){
			status = 6;
		}else{
			status = Integer.parseInt(request.getParameter("status"));//订单状态-6
		}
		
	
		boolean boo = false;
		String str  =request.getParameter("id");
		MethodUtil mu = new MethodUtil();
		int[] ids = mu.StringtoInt(str);
		System.out.println(Arrays.toString(ids));
		for(int i = 0; i < ids.length; i++){
			if(i == 0){
				logistics.setPOAmount(po_amount);
				logistics.setRMBPOAmount(rmb_po_amount);
			}else{
				logistics.setPOAmount(0);
				logistics.setRMBPOAmount(0);
			}
			logistics.setOrderInfoID(ids[i]);
			logistics.setPONO(po_no);
			logistics.setSONO(so_no);
			logistics.setSupplier(supplier);
			logistics.setFactoryShipment(factory_shipment);
			logistics.setEstimatedPaymentTime(estimated_payment_time);
			logistics.setActualPaymentTime(actual_payment_time);
			
			orderInfo.setID(ids[i]);
			orderInfo.setExceptDate(except_date);
			orderInfo.setDate(date);
			orderInfo.setDeliveryNumber(delivery_number);
			try{orderInfo.setLogisticsNumber(logistics_number);}catch(Exception e){}
			orderInfo.setStatus(status);
			String[] param1= null;
			String[] param2=null;
			if(logisticService.orderInfoModify(orderInfo, logistics,param1,param2)){
				String factoryDate = logistics.getFactoryShipment();
				String actureDate = orderInfo.getDate();
				
				//
				if(!factoryDate.equals("")){
					inventoryInfoService.importInInventoryInfo(logistics.getOrderInfoID(),logistics,orderInfo);
				}
				if(!actureDate.equals("")){
					inventoryInfoService.importOutInventoryInfo(logistics.getOrderInfoID(),logistics,orderInfo);
				}
				
				boo = true;
				
			}
			
		}
		*/

		
	}

	
			  
			
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
