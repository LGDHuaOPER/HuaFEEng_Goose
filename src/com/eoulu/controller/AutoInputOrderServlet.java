package com.eoulu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
 * Servlet implementation class AutoInputOrderServlet
 */
@WebServlet("/AutoInputOrder")
public class AutoInputOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutoInputOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*OrderService orderService = new OrderServiceImpl();
		orderService.autoInputOrder(request);导入订单
		response.getWriter().write("{ error:'error', msg:'msg',imgurl:'dd'}");*/
		response.setContentType("text/html;charset=utf-8");
		OrderServiceImpl OrderServiceImpl=new OrderServiceImpl();
		Map<String, Object> resultMap=OrderServiceImpl.InputContractConfiguration(request);
		List<OrderInfo> ExcelList =(List<OrderInfo>)resultMap.get("ExcelList");
		List<OrderInfo> FailExcelList =(List<OrderInfo>)resultMap.get("FailExcelList");
		int order_id=Integer.parseInt((String)resultMap.get("order_id"));
		//List<OrderInfo> FailExcel=null;
		if(ExcelList==null){
			response.getWriter().write(new Gson().toJson("{\"message\":\"defeat\"}"));
		}
		for(int i=0;i<ExcelList.size();i++){
			OrderInfo orderInfo = new OrderInfo();
			Logistics logistics = new Logistics();
			OrderInfo orderInfo1=ExcelList.get(i);
			orderInfo.setOrderID(order_id);
			orderInfo.setEquipmentModel(orderInfo1.getEquipmentModel());
			orderInfo.setNumber(orderInfo1.getNumber());
			orderInfo.setLogisticsNumber(orderInfo.getNumber());
			logistics.setOrderID(order_id);
			OrderService orderService = new OrderServiceImpl();
			if(orderService.OrderInfoAndLogisticsAdd(orderInfo, logistics)){
				FailExcelList.add(orderInfo1);
				continue;
			}
		}
		if(FailExcelList==null){
			//response.getWriter().write(new Gson().toJson("{\"message\":\"success\"}"));
			//全部上传成功
			response.getWriter().write(new Gson().toJson("success"));
			return;
		}else{
			//response.getWriter().write(new Gson().toJson("{\"message\":\"defeat\",\"FailExcelList\":\""+FailExcelList+"\"}"));
			//部分上传成功
			String str="";
			for(int i=0;i<FailExcelList.size()-1;i++){
				str+=FailExcelList.get(i).getEquipmentModel2()+",";
			}
			str+=FailExcelList.get(FailExcelList.size()-1).getEquipmentModel2();
			response.getWriter().write(new Gson().toJson(str));
		}
	}
}
