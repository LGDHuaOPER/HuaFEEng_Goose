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
 * Servlet implementation class GetOrderByPageServlet
 * 
 * ���������Ĳ�ѯ
 */
@WebServlet("/GetOrderByPageOne")
public class GetOrderByPageOneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetOrderByPageOneServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		OrderService orderService = new OrderServiceImpl();
		StatisticsService statisticsService = new StatisticsServiceImpl();
		String ActualDelivery = request.getParameter("ActualDelivery");
		String column = request.getParameter("column");
		String condition = request.getParameter("condition")==null?"All": request.getParameter("condition");
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		String classify = request.getParameter("type1");
	
		String parameter = request.getParameter("searchContent1").trim();
	
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		if(classify.equals("合同货期") || classify.equals("实际货期") || classify.equals("预计货期")){
			parameter = "货期";
		}
		if(parameter.equals("")){
			request.getRequestDispatcher("Transport").forward(request, response);
		}else{
			
		if(classify.equals("PO(只能单一搜索)") || classify.equals("SO(只能单一搜索)") || classify.equals("供应商(只能单一搜索)")){
			if(condition.equals("All")){
				request.setAttribute("orders", orderService.queryResultAddReview(orderService.getOrderByPageInOneByPoSo(classify, parameter, page,0)));
				page.setRecordCounts(orderService.getOrderByPageInOneByPoSoCount(classify, parameter,0));
			}else if(ActualDelivery.equals("yes")){
				request.setAttribute("orders", orderService.queryResultAddReview(orderService.getOrderByPageInOneByPoSo(classify, parameter, page,condition,0)));
				page.setRecordCounts(orderService.getOrderByPageInOneByPoSoCount(classify, parameter,condition,0));
			}
			
		}else if(classify.equals("合同货期") || classify.equals("实际货期") || classify.equals("预计货期")){
			
			String start_time1=request.getParameter("start_time1");
			String end_time1=request.getParameter("end_time1");
			if(condition.equals("All")){
				System.out.println("走了么");
				request.setAttribute("orders", orderService.queryResultAddReview(orderService.getOrderByTime(classify, start_time1, end_time1, page,0)));
				page.setRecordCounts(orderService.getCountByClassifyTime(classify,start_time1, end_time1,0));
			}else if(ActualDelivery.equals("yes")){
				request.setAttribute("orders", orderService.queryResultAddReview(orderService.getOrderByTime(classify, start_time1, end_time1, page,condition,0)));
				page.setRecordCounts(orderService.getCountByClassifyTime(classify,start_time1, end_time1,condition,0));
			}
			
			request.setAttribute("start_time1", start_time1);
			request.setAttribute("end_time1", end_time1);
		}else{
			if(condition.equals("All")){
				request.setAttribute("orders", orderService.queryResultAddReview(orderService.getOrderByPageInOne(classify, parameter, page,0)));
				page.setRecordCounts(orderService.getCountByClassify(classify, parameter,0));
			}else if(ActualDelivery.equals("yes")){
				request.setAttribute("orders", orderService.queryResultAddReview(orderService.getOrderByPageInOne(classify, parameter, page,condition,0)));
				page.setRecordCounts(orderService.getCountByClassify(classify, parameter,condition,0));
			}
			
		}
		
		/*switch(condition){
		case "���ʱ��":
		case "����ʱ��":map.put("1", request.getParameter("start_time1"));map.put("2", request.getParameter("end_time1"));break;
		default :map.put("1", request.getParameter("searchContent1"));
		}*/
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
		request.setAttribute("classify1", classify);
		request.setAttribute("parameter1", parameter);
		request.setAttribute("queryType", "singleSelect");
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
