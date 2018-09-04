package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LogInfoService;
import com.eoulu.service.OrderService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.OrderServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class OrderInfoModifyServlet
 */
@WebServlet("/OrderInfoModify")
public class OrderInfoModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderInfoModifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String counts = request.getParameter("equipment_counts");
		OrderService orderService = new OrderServiceImpl();
		
		if(orderService.modifyOrderInfo(id, counts)){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "合同统计";
			String description = "修改-合同配置";
			log.insert(request, JspInfo, description);
			response.getWriter().write(new Gson().toJson("{\"message\":\"success\"}"));
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
