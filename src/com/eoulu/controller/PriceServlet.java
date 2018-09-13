package com.eoulu.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.OrderService;
import com.eoulu.service.RequirementService;
import com.eoulu.service.StatisticsService;
import com.eoulu.service.impl.AreaServiceImpl;
import com.eoulu.service.impl.ContractStatusServiceImpl;
import com.eoulu.service.impl.DutyFreeServiceImpl;
import com.eoulu.service.impl.OrderServiceImpl;
import com.eoulu.service.impl.PaymentStatusServiceImpl;
import com.eoulu.service.impl.PaymentTermsServiceImpl;
import com.eoulu.service.impl.RequirementServiceImpl;
import com.eoulu.service.impl.SalesRepresentativeServiceImpl;
import com.eoulu.service.impl.StatisticsServiceImpl;

/**
 * ���ʺ�ͬͳ��ҳ�棨ԭ���ı���ҳ�棩����֧���޲�ѯ�����ķ�ҳ
 */
@WebServlet("/Price")
public class PriceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PriceServlet() {
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

		String ActualDelivery = request.getParameter("ActualDelivery");//只要点击了合同货期或实际货期或预计货期，就传过来yes
		
		String column = request.getParameter("column");//order by
		
		String condition = request.getParameter("condition");
		
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		if(condition.equals("All")){
			new AccessStatistics().operateAccess(request, "合同统计");
			page.setRecordCounts(orderService.getAllCounts(0));
		}else if(ActualDelivery.equals("yes")){
			page.setRecordCounts(orderService.getAllCountsIfActualDelivery(condition,column,0));
		}
			
		//��ҳ����
		request.setAttribute("queryType", "common");
		request.setAttribute("Areas", new AreaServiceImpl().getAllArea());
		request.setAttribute("ContractStatus", new ContractStatusServiceImpl().getAllStatus());
		request.setAttribute("PayTerms", new PaymentTermsServiceImpl().getAllPayTerms());
		request.setAttribute("DutyFree", new DutyFreeServiceImpl().getAllDuty());
		request.setAttribute("SalesRep", new SalesRepresentativeServiceImpl().getAllSalesRep());
		if(condition.equals("All")){ //什么都不点
			request.setAttribute("orders", orderService.getAllOrder(page,0));
		}else if(ActualDelivery.equals("yes")){//点击实际货期,不排序
			request.setAttribute("orders", orderService.getAllOrderByActualDelivery(page,condition,column,0));
			
		}
		
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("orderCounts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		
		//����������
		//request.setAttribute("equipments", new CommodityServiceImpl().getEquipmentByName(""));
		request.setAttribute("Areas", new AreaServiceImpl().getAllArea());
		request.setAttribute("ContractStatus", new ContractStatusServiceImpl().getAllStatus());
		request.setAttribute("PayTerms", new PaymentTermsServiceImpl().getAllPayTerms());
		request.setAttribute("DutyFree", new DutyFreeServiceImpl().getAllDuty());
		request.setAttribute("SalesRep", new SalesRepresentativeServiceImpl().getAllSalesRep());
		request.setAttribute("whether_to_pay", new PaymentStatusServiceImpl().getAllPaymentStatus());
		request.setAttribute("payment_terms", new PaymentTermsServiceImpl().getAllPayTerms());
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
//		String endTime = year+"-"+((month+1)>9?(month+1):"0"+(month+1));
//		System.out.println(startTime+"   "+endTime);
		request.setAttribute("AreaStatisticsByMonthOld", statisticsService.getStatisticsByArea(startTime1,endTime1,true));
		request.setAttribute("AreaStatisticsByMonth", statisticsService.getStatisticsByArea(startTime,endTime,false));
		String thisYear = year+"";
		request.setAttribute("AreaStatisticsPerMonth", statisticsService.getStatisticsByAreaPerMonth(thisYear));
//		System.out.println(statisticsService.getStatisticsByArea(year+"-01", year+"-12"));
//		System.out.println(statisticsService.getStatisticsByArea(year+"-01", year+"-12"));
//		System.out.println(statisticsService.getStatisticsByArea(year+"-01", year+"-12"));
		request.getRequestDispatcher("WEB-INF\\price.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String path = request.getServletContext().getRealPath("/")+"down\\合同统计"+format.format(new Date())+".xlsx";
		OrderService service = new OrderServiceImpl();
		service.exportOrderExcel(path, 0);
		response.getWriter().write("down/合同统计"+format.format(new Date())+".xlsx");
		
	}

}
