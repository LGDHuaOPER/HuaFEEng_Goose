package com.eoulu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.impl.EquipmentServiceImpl;
import com.eoulu.service.impl.QuotationServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class GetQuotationConfServlet
 */
@WebServlet("/GetQuotationConf")
public class GetQuotationConfServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetQuotationConfServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String OrderID =request.getParameter("OrderID");
		
		//获取报价单设备信息
		List<Map<String, Object>> equipment=new EquipmentServiceImpl().getAllEquipmentByOrderID(OrderID);
		
		response.getWriter().write(new Gson().toJson(equipment));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
