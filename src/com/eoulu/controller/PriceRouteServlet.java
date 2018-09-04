package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.impl.AreaServiceImpl;
import com.eoulu.service.impl.ContractStatusServiceImpl;
import com.eoulu.service.impl.DutyFreeServiceImpl;
import com.eoulu.service.impl.EquipmentServiceImpl;
import com.eoulu.service.impl.PaymentStatusServiceImpl;
import com.eoulu.service.impl.PaymentTermsServiceImpl;
import com.eoulu.service.impl.SalesRepresentativeServiceImpl;

/**
 * 合同统计页面的路由借口，可以根据用户选择的单一查询和组合查询来动态的控制所需要访问的接口
 */
@WebServlet("/PriceRoute")
public class PriceRouteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PriceRouteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str = request.getParameter("selected");
		switch(str){
		case "singleSelect":request.getRequestDispatcher("GetOrderByPageOneInPrice").forward(request, response); break;
		case "mixSelect": request.getRequestDispatcher("GetOrderByPageTwoInPrice").forward(request, response);
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
