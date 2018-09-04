package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.dao.CustomerDao;
import com.eoulu.dao.PaymentTermsDao;
import com.eoulu.service.QuotationService;
import com.eoulu.service.SalesRepresentativeService;
import com.eoulu.service.impl.EquipmentServiceImpl;
import com.eoulu.service.impl.PaymentTermsServiceImpl;
import com.eoulu.service.impl.QuotationServiceImpl;
import com.eoulu.service.impl.SalesRepresentativeServiceImpl;

/**
 * 报价单页面的数据显示页面，可支持无查询参数的分页
 */
@WebServlet("/Quotation")
public class QuotationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuotationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		QuotationService quotationService = new QuotationServiceImpl();
		SalesRepresentativeService representativeService = new SalesRepresentativeServiceImpl();
		
		
		Page page = new Page();
		page.setRows(10);
		page.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")==null?1+"":request.getParameter("currentPage")));
		//获取报价单总数量
		page.setRecordCounts(quotationService.getQuotationCounts());
		//获取报价单关键信息
		request.setAttribute("quotations", quotationService.getQuotationByPage(page));
		//获取所有业务员信息
		request.setAttribute("sales", representativeService.getAllSalesRep());
		//获取所有客户信息
		request.setAttribute("customers", new CustomerDao().getAllCustomer());
		//获取所有设备信息
		request.setAttribute("equipments", new EquipmentServiceImpl().getAllEquipmentByName(""));
		//获取支付方式信息
		request.setAttribute("paymentTerms", new PaymentTermsDao().getAllPayTerms());
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.getRequestDispatcher("WEB-INF//quotation.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
