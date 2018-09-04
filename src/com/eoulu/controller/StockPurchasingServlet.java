package com.eoulu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.OrderService;
import com.eoulu.service.RequirementService;
import com.eoulu.service.impl.AreaServiceImpl;
import com.eoulu.service.impl.ContractStatusServiceImpl;
import com.eoulu.service.impl.DutyFreeServiceImpl;
import com.eoulu.service.impl.OrderServiceImpl;
import com.eoulu.service.impl.PaymentStatusServiceImpl;
import com.eoulu.service.impl.PaymentTermsServiceImpl;
import com.eoulu.service.impl.RequirementServiceImpl;
import com.eoulu.service.impl.SalesRepresentativeServiceImpl;

/**
 * Servlet implementation class StockPurchasingServlet
 */
@WebServlet("/StockPurchasing")
public class StockPurchasingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockPurchasingServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderService orderService = new OrderServiceImpl();
		//StatisticsService statisticsService = new StatisticsServiceImpl();
		RequirementService requirementService = new RequirementServiceImpl();

		String ActualDelivery = request.getParameter("ActualDelivery");//只要点击了合同货期或实际货期或预计货期，就传过来yes
		
		String column = request.getParameter("column");//order by
		
		String condition = request.getParameter("condition");
		
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		if(condition.equals("All")){
			page.setRecordCounts(orderService.getAllCounts(1));
			new AccessStatistics().operateAccess(request, "库存采购");
		}else if(ActualDelivery.equals("yes")){
			page.setRecordCounts(orderService.getAllCountsIfActualDelivery(condition,column,1));
		}
			
		//��ҳ����
		request.setAttribute("queryType", "common");
		request.setAttribute("Areas", new AreaServiceImpl().getAllArea());
		request.setAttribute("ContractStatus", new ContractStatusServiceImpl().getAllStatus());
		request.setAttribute("PayTerms", new PaymentTermsServiceImpl().getAllPayTerms());
		request.setAttribute("DutyFree", new DutyFreeServiceImpl().getAllDuty());
		request.setAttribute("SalesRep", new SalesRepresentativeServiceImpl().getAllSalesRep());
		if(condition.equals("All")){ //什么都不点
			request.setAttribute("orders", orderService.getAllOrder(page,1));
		}else if(ActualDelivery.equals("yes")){//点击实际货期,不排序
			request.setAttribute("orders", orderService.getAllOrderByActualDelivery(page,condition,column,1));
			
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
		
		//�������������ͳ��
		/*
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH)+1;
		//request.setAttribute("AreaStatisticsByLastYear", statisticsService.getStatisticsByArea("2017-01", "2017-12"));
		//request.setAttribute("AreaStatisticsByYear", statisticsService.getStatisticsByArea(year+"-01", year+"-12"));
		String startTime = year+"-"+(month>9?month:"0"+month);
		String endTime = startTime;
		String startTime1 = "2017"+"-"+(month>9?month:"0"+month);
		String endTime1 = startTime1;
//		String endTime = year+"-"+((month+1)>9?(month+1):"0"+(month+1));
//		System.out.println(startTime+"   "+endTime);
		//request.setAttribute("AreaStatisticsByMonthOld", statisticsService.getStatisticsByArea(startTime1,endTime1));
		//request.setAttribute("AreaStatisticsByMonth", statisticsService.getStatisticsByArea(startTime,endTime));
		String thisYear = year+"";
		request.setAttribute("AreaStatisticsPerMonth", statisticsService.getStatisticsByAreaPerMonth(thisYear));
//		System.out.println(statisticsService.getStatisticsByArea(year+"-01", year+"-12"));
		*/
		request.getRequestDispatcher("WEB-INF\\StockPurchase.jsp").forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "E:\\LogisticsFile\\File\\";
		String fileName = request.getParameter("FileName") == null?"":request.getParameter("FileName");
	
		File file = new File(path+fileName);
		if(!file.exists()){
			response.getWriter().write("文件已被删除！");
		}else{
			response.setContentType("application/x-msdownload");  
			//fileName = new String(fileName.getBytes(), "ISO-8859-1");
			response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
		    FileInputStream in = new FileInputStream(path+fileName);
		    OutputStream out = response.getOutputStream();
	        byte buffer[] = new byte[1024];
	        int len = 0;
	        while((len=in.read(buffer))>0){
	            out.write(buffer, 0, len);
	        }
	        in.close();
	        out.close();
		}
	}

}
