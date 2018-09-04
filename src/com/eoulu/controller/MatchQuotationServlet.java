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
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.OrderServiceImpl;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class MatchQuotationServlet
 */
@WebServlet("/MatchQuotation")
public class MatchQuotationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MatchQuotationServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		OrderService order = new OrderServiceImpl();
		List<Map<String, Object>> result = order.MatchQuotation(request);
		if(result==null){
			System.out.println("匹配不成功");
		}else if(result.size()<2){
			System.out.println("未匹配到");
		}
		int OrderID = Integer.parseInt(request.getParameter("OrderID")==null?"0":request.getParameter("OrderID"));
		response.getWriter().write(new Gson().toJson(order.getOrderInfoById(OrderID)));

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
