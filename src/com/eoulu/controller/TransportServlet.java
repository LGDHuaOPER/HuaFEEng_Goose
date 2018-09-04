package com.eoulu.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.OrderService;
import com.eoulu.service.impl.ContractStatusServiceImpl;
import com.eoulu.service.impl.OrderServiceImpl;
import com.eoulu.service.impl.OrderStatusServiceImpl;
import com.eoulu.service.impl.SupplierServiceImpl;
import com.google.gson.Gson;

/**
 * ��������ҳ��Ľӿڣ��˽ӿڿ��Խ��в�����ѯ�����ķ�ҳ�������ݽ��м���
 */
@WebServlet("/Transport")
public class TransportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TransportServlet() {
		super();
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderService orderService = new OrderServiceImpl();

		String ActualDelivery = request.getParameter("ActualDelivery");
		String column = request.getParameter("column");
		String condition = request.getParameter("condition")==null?"All":request.getParameter("condition");
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		if(condition.equals("All")){
			page.setRecordCounts(orderService.getAllCounts(0));
		}else if(ActualDelivery.equals("yes")){
			page.setRecordCounts(orderService.getAllCountsIfActualDelivery(condition,column,0));
		}
		
		request.setAttribute("queryType", "common");
		if(condition.equals("All")){ //什么都不点
			new AccessStatistics().operateAccess(request, "物流统计");
			request.setAttribute("orders", orderService.getAllOrder(page,0));
		}else if(ActualDelivery.equals("yes")){//点击实际货期,不排序
			request.setAttribute("orders", orderService.getAllOrderByActualDelivery(page,condition,column,0));
			
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
		String month = simpleDateFormat.format(new Date());

		Map<String, Integer> map = new HashMap<>();
		map.put("AllNoSend", orderService.getAllCountsIfActualDelivery("AllNoSend","CargoPeriod" , 0));
		map.put("OtherNoSend",orderService.getAllCountsIfActualDelivery("OtherNoSend","CargoPeriod" , 0));
		map.put("Overdue",orderService.getAllCountsIfActualDelivery("Overdue", "CargoPeriod", 0));
		map.put("OverdueRisk",orderService.getAllCountsIfActualDelivery("OverdueRisk", "CargoPeriod", 0));
		map.put("ShippedMonth",orderService.getShippedCounts(0,month,month));
		System.out.println(orderService.getShippedCounts(0,month,month));
		request.setAttribute("Statistics", new Gson().toJson(map));
	
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("orderCounts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("suppliers", new SupplierServiceImpl().getAllSupplier());
		request.setAttribute("order_status", new OrderStatusServiceImpl().getAllOrderStatus());
		request.setAttribute("ContractStatus", new ContractStatusServiceImpl().getAllStatus());
		
		request.getRequestDispatcher("WEB-INF\\transport.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
