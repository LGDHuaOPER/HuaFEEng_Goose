package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.CompleteOrder;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.OrderService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.OrderServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class ModifyOrderServlet
 */
@WebServlet("/ModifyOrder")
public class ModifyOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
	
		OrderService orderService = new OrderServiceImpl();
		CompleteOrder completeOrder = orderService.reqToObject(request);
		
		if(orderService.modifyOrder(completeOrder)){
			
			response.getWriter().write(new Gson().toJson(orderService.getOrderByID(id)));
			return;
		}else{
			response.getWriter().write(new Gson().toJson("修改失败"));
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		OrderService orderService = new OrderServiceImpl();
//		CompleteOrder completeOrder = orderService.reqToObject(request);
//		
//		boolean flag = orderService.modifyOrder(completeOrder);
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("message", "success");
//		response.getWriter().write(new Gson().toJson(map));
		OrderService orderService = new OrderServiceImpl();
		CompleteOrder completeOrder = orderService.reqToObject(request);
		String id = request.getParameter("id");
		
		if(orderService.modifyOrder(completeOrder)){
			String contractNo = request.getParameter("contract_no");
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "合同统计";
			String description = "修改-合同信息-"+contractNo;
			if(request.getParameter("PageType").equals("1")){
				JspInfo = "库存采购";
				description = "修改-库存采购-"+contractNo;
			}
			log.insert(request, JspInfo, description);
			response.getWriter().write(new Gson().toJson(orderService.getOrderByID(id)));
			return;
		}else{
			response.getWriter().write(new Gson().toJson("修改失败"));
		}
	}

}
