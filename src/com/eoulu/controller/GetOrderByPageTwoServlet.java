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
import com.eoulu.service.OrderService;
import com.eoulu.service.StatisticsService;
import com.eoulu.service.impl.ContractStatusServiceImpl;
import com.eoulu.service.impl.OrderServiceImpl;
import com.eoulu.service.impl.OrderStatusServiceImpl;
import com.eoulu.service.impl.StatisticsServiceImpl;
import com.eoulu.service.impl.SupplierServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class GetOrderByPageTwoServlet
 * 
 * ������������ϲ�ѯ
 */
@WebServlet("/GetOrderByPageTwo")
public class GetOrderByPageTwoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetOrderByPageTwoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderService orderService = new OrderServiceImpl();
		StatisticsService statisticsService = new StatisticsServiceImpl();
		String ActualDelivery = request.getParameter("ActualDelivery");
		String column = request.getParameter("column");
		String condition = request.getParameter("condition")==null?"All":request.getParameter("condition");
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		String classify1 = request.getParameter("type1");
		String parameter1 = request.getParameter("searchContent1").trim();
		String classify2 = request.getParameter("type2");
		String parameter2 = request.getParameter("searchContent2").trim();
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		if(parameter1.equals("")&&parameter2.equals("")&&request.getParameter("start_time1").equals("")&&request.getParameter("start_time2").equals("")&&request.getParameter("end_time1").equals("")&&request.getParameter("end_time2").equals("")){
			request.getRequestDispatcher("Transport").forward(request, response);
		}else{
		Map<String,Object> map1 = new HashMap<String,Object>();
		switch(classify1){
		case "合同货期":
		case "实际货期":
		case "预计货期":map1.put("1", request.getParameter("start_time1"));map1.put("2", request.getParameter("end_time1"));break;
		default :map1.put("1", parameter1);
		}
		Map<String,Object> map2 = new HashMap<String,Object>();
		switch(classify2){
		case "合同货期":
		case "实际货期":
		case "预计货期":map2.put("1", request.getParameter("start_time2"));map2.put("2", request.getParameter("end_time2"));break;
		default :map2.put("1",parameter2);
		}
		if(map1.size()==2||map2.size()==2){
			if(condition.equals("All")){
				page.setRecordCounts(orderService.getCountByTimeClassify(classify1, map1,classify2, map2,0));
				request.setAttribute("orders", orderService.queryResultAddReview(orderService.getOrderByPageInTwoTime(classify1, map1,classify2, map2, page,0)));//
			}else if(ActualDelivery.equals("yes")){
				page.setRecordCounts(orderService.getCountByTimeClassify(classify1, map1,classify2, map2,condition,0));
				request.setAttribute("orders", orderService.queryResultAddReview(orderService.getOrderByPageInTwoTime(classify1, map1,classify2, map2, page,condition,0)));//
			}
			request.setAttribute("start_time1", request.getParameter("start_time1"));
			request.setAttribute("end_time1", request.getParameter("end_time1"));
			request.setAttribute("start_time2", request.getParameter("start_time2"));
			request.setAttribute("end_time2", request.getParameter("end_time2"));
		}else{
			if(condition.equals("All")){
				page.setRecordCounts(orderService.getCountByClassify(classify1, parameter1,classify2, parameter2,0));
				request.setAttribute("orders", orderService.queryResultAddReview(orderService.getOrderByPageInTwo(classify1, parameter1,classify2, parameter2, page,0)));//
			}else if(ActualDelivery.equals("yes")){
				page.setRecordCounts(orderService.getCountByClassify(classify1, parameter1,classify2, parameter2,condition,0));
				request.setAttribute("orders", orderService.queryResultAddReview(orderService.getOrderByPageInTwo(classify1, parameter1,classify2, parameter2, page,condition,0)));//
			}
			
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
		request.setAttribute("queryType", "mixSelect");
		request.setAttribute("classify1", classify1);
		request.setAttribute("parameter1", parameter1);
		request.setAttribute("classify2", classify2);
		request.setAttribute("parameter2", parameter2);
		request.setAttribute("suppliers", new SupplierServiceImpl().getAllSupplier());
		request.setAttribute("order_status", new OrderStatusServiceImpl().getAllOrderStatus());
		request.setAttribute("ContractStatus", new ContractStatusServiceImpl().getAllStatus());

		
		/*
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH)+1;
		request.setAttribute("AreaStatisticsByYear", statisticsService.getStatisticsByArea(year+"-01-01", year+1+"-01-01",false));
		String startTime = year+"-"+(month>9?month:"0"+month);
		String endTime = year+"-"+((month+1)>9?(month+1):"0"+(month+1));
		request.setAttribute("AreaStatisticsByMonth", statisticsService.getStatisticsByArea(startTime+"-01",endTime+"-01",false));
		*/
			request.getRequestDispatcher("WEB-INF\\transport.jsp").forward(request, response);
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
