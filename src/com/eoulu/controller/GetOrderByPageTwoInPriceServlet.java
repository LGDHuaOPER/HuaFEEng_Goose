package com.eoulu.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.dao.CustomerDao;
import com.eoulu.service.OrderService;
import com.eoulu.service.RequirementService;
import com.eoulu.service.StatisticsService;
import com.eoulu.service.impl.AreaServiceImpl;
import com.eoulu.service.impl.CommodityServiceImpl;
import com.eoulu.service.impl.ContractStatusServiceImpl;
import com.eoulu.service.impl.DutyFreeServiceImpl;
import com.eoulu.service.impl.EquipmentServiceImpl;
import com.eoulu.service.impl.OrderServiceImpl;
import com.eoulu.service.impl.OrderStatusServiceImpl;
import com.eoulu.service.impl.PaymentStatusServiceImpl;
import com.eoulu.service.impl.PaymentTermsServiceImpl;
import com.eoulu.service.impl.RequirementServiceImpl;
import com.eoulu.service.impl.SalesRepresentativeServiceImpl;
import com.eoulu.service.impl.StatisticsServiceImpl;
import com.eoulu.service.impl.SupplierServiceImpl;

/**
 * Servlet implementation class GetOrderByPageTwoInPrice
 */
@WebServlet("/GetOrderByPageTwoInPrice")
public class GetOrderByPageTwoInPriceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetOrderByPageTwoInPriceServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderService orderService = new OrderServiceImpl();
		StatisticsService statisticsService = new StatisticsServiceImpl();
		RequirementService requirementService = new RequirementServiceImpl();

		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		String classify1 = request.getParameter("type1");
		String parameter1 = request.getParameter("searchContent1").trim();
		String classify2 = request.getParameter("type2");
		String parameter2 = request.getParameter("searchContent2").trim();
		int type = Integer.parseInt(request.getParameter("PageType"));
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
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
			page.setRecordCounts(orderService.getCountByTimeClassify(classify1, map1,classify2, map2,type));
			request.setAttribute("orders", orderService.queryResultAddReview(orderService.getOrderByPageInTwoTime(classify1, map1,classify2, map2, page,type)));
			request.setAttribute("start_time1", request.getParameter("start_time1"));
			request.setAttribute("end_time1", request.getParameter("end_time1"));
			request.setAttribute("start_time2", request.getParameter("start_time2"));
			request.setAttribute("end_time2", request.getParameter("end_time2"));
		}else{
			page.setRecordCounts(orderService.getCountByClassify(classify1, parameter1,classify2, parameter2,type));
			request.setAttribute("orders", orderService.getOrderByPageInTwo(classify1, parameter1,classify2, parameter2, page,type));
		}
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

		//����������
		request.setAttribute("Areas", new AreaServiceImpl().getAllArea());
		request.setAttribute("ContractStatus", new ContractStatusServiceImpl().getAllStatus());
		request.setAttribute("PayTerms", new PaymentTermsServiceImpl().getAllPayTerms());
		request.setAttribute("DutyFree", new DutyFreeServiceImpl().getAllDuty());
		request.setAttribute("SalesRep", new SalesRepresentativeServiceImpl().getAllSalesRep());
		request.setAttribute("whether_to_pay", new PaymentStatusServiceImpl().getAllPaymentStatus());
		//request.setAttribute("equipments", new CommodityServiceImpl().getEquipmentByName(""));
		request.setAttribute("contract_category", requirementService.getAllRequirementClassify());
		//request.setAttribute("customers", new CustomerDao().getAllCustomer());
		//�������������ͳ��
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH)+1;
		request.setAttribute("AreaStatisticsByLastYear", statisticsService.getStatisticsByArea("2017-01", "2017-12",false));
		request.setAttribute("AreaStatisticsByYear", statisticsService.getStatisticsByArea(year+"-01", year+"-12",false));
		String startTime = year+"-"+(month>9?month:"0"+month);
		String endTime = startTime;
		String startTime1 = "2017"+"-"+(month>9?month:"0"+month);
		String endTime1 = startTime1;
		request.setAttribute("AreaStatisticsByMonthOld", statisticsService.getStatisticsByArea(startTime1,endTime1,true));
		request.setAttribute("AreaStatisticsByMonth", statisticsService.getStatisticsByArea(startTime,endTime,false));
		String thisYear = year+"";
		request.setAttribute("AreaStatisticsPerMonth", statisticsService.getStatisticsByAreaPerMonth(thisYear));
		
		if(type==1){
			request.getRequestDispatcher("WEB-INF\\StockPurchase.jsp").forward(request, response);
		}else{
			request.getRequestDispatcher("WEB-INF\\price.jsp").forward(request, response);
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
