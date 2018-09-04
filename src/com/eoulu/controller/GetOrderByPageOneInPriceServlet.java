package com.eoulu.controller;

import java.io.IOException;
import java.util.Calendar;

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
 * Servlet implementation class GetOrderByPageOneInPriceServlet
 */
@WebServlet("/GetOrderByPageOneInPrice")
public class GetOrderByPageOneInPriceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetOrderByPageOneInPriceServlet() {
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
		int type = Integer.parseInt(request.getParameter("PageType"));
		String classify = request.getParameter("type1");
		String parameter = request.getParameter("searchContent1").trim();
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		if(classify.equals("合同货期") || classify.equals("实际货期") || classify.equals("预计货期")){
			String start_time1=request.getParameter("start_time1");
			String end_time1=request.getParameter("end_time1");
			request.setAttribute("orders", orderService.queryResultAddReview(orderService.getOrderByTime(classify, start_time1, end_time1, page,type)));
			page.setRecordCounts(orderService.getCountByClassifyTime(classify,start_time1, end_time1,type));
			request.setAttribute("start_time1", start_time1);
			request.setAttribute("end_time1", end_time1);
		}else{
			page.setRecordCounts(orderService.getCountByClassify(classify, parameter,type));
			request.setAttribute("orders", orderService.getOrderByPageInOne(classify, parameter, page,type));
		}
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("orderCounts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("classify1", classify);
		request.setAttribute("parameter1", parameter);
		request.setAttribute("queryType", "singleSelect");
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
		if (type == 1) {
			request.getRequestDispatcher("WEB-INF\\StockPurchase.jsp").forward(request, response);
		} else {
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
