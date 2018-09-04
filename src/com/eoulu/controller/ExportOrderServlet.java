package com.eoulu.controller;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LogInfoService;
import com.eoulu.service.OrderService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.OrderServiceImpl;

/**
 * Servlet implementation class ExportOrderImpl
 */
@WebServlet("/ExportOrder")
public class ExportOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExportOrderServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Object[] classify = new Object[2];
		Object[] parameter = new Object[2];
		String ActualDelivery = request.getParameter("ActualDelivery");
		String column = request.getParameter("column");
		int type = request.getParameter("Type") == null?0:Integer.parseInt(request.getParameter("Type"));
		
		String condition = request.getParameter("condition");
		String queryType = request.getParameter("query_type");
		
		classify[0] = request.getParameter("classify1");
		classify[1] = request.getParameter("classify2");
		parameter[0] = request.getParameter("parameter1");
		parameter[1] = request.getParameter("parameter2");
		// String fileName = new Date().getTime().t;

		Calendar calendar = Calendar.getInstance();

		String fileName = calendar.get(Calendar.YEAR) + "" + (calendar.get(Calendar.MONTH) + 1) + ""
				+ calendar.get(Calendar.DAY_OF_MONTH);
		String path = request.getServletContext().getRealPath("/") + "down\\";
		OrderService orderService = new OrderServiceImpl();
		LogInfoService log = new LogInfoServiceImpl();
		if(type == 0){
			fileName += "-物流统计.xlsx";
			path = path + fileName;
			if(condition.equals("All")){
				orderService.exportOrderIncludePO(queryType, classify, parameter, path);
			}else if(ActualDelivery.equals("yes")){
				orderService.exportOrderIncludePO(queryType, classify, parameter, path,condition);
			}
			String JspInfo = "物流页面";
			String description = "导出";
			log.insert(request, JspInfo, description);
		}else{
			fileName += "-库存采购.xlsx";
			path = path + fileName;
			if(condition.equals("All")){
				orderService.exportOrderStock(queryType, classify, parameter, path);
			}else if(ActualDelivery.equals("yes")){
				orderService.exportOrderStock(queryType, classify, parameter, path,condition);
			}
			String JspInfo = "库存采购";
			String description = "导出";
			log.insert(request, JspInfo, description);
		}
		
		
		response.getWriter().write("down//" + fileName);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
