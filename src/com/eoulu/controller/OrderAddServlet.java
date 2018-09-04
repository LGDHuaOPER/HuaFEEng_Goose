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
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderAdd")
public class OrderAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderAddServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//注：合同号为必填，否则报错
		OrderService oi = new OrderServiceImpl();
		try{
			int result = oi.orderAdd(oi.reqToObject(request));
			if(result>0){
				String contractNo = request.getParameter("contract_no");
				LogInfoService log = new LogInfoServiceImpl();
				String JspInfo = "合同统计";
				String description = "新增-合同信息-"+contractNo;
				if(request.getParameter("PageType").equals("1")){
					JspInfo = "库存采购";
					description = "新增-库存采购-"+contractNo;
				}
				log.insert(request, JspInfo, description);
				response.getWriter().write(new Gson().toJson("{'message':'添加成功'}"));
				return;
			}else if (result == -1) {
				response.getWriter().write(new Gson().toJson("{'message':'合同号已存在'}"));
				return;
			}
			response.getWriter().write(new Gson().toJson("{'message':'添加失败'}"));
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().write(new Gson().toJson("{'message':'添加失败'}"));
		}
	

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
