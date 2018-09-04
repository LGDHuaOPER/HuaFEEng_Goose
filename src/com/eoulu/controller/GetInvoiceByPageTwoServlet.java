package com.eoulu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.InvoiceService;
import com.eoulu.service.impl.InvoiceServiceImpl;

@WebServlet("/GetInvoiceByPageTwo")
public class GetInvoiceByPageTwoServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	public GetInvoiceByPageTwoServlet() {
		super();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		String classify1 = request.getParameter("type1");
		String parameter1 = request.getParameter("searchContent1").trim();
		String classify2 = request.getParameter("type2");
		String parameter2 = request.getParameter("searchContent2").trim();
		System.out.println("qingqiu2");
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		Map<String,Object> map1 = new HashMap<String,Object>();
		switch(classify1){
		case "出发时间":
		case "发票签订时间":
		case "操作时间":map1.put("1", request.getParameter("start_time1"));map1.put("2", request.getParameter("end_time1"));break;
		default :map1.put("1", parameter1);
		}
		Map<String,Object> map2 = new HashMap<String,Object>();
		switch(classify2){
		case "出发时间":
		case "发票签订时间":
		case "操作时间":map2.put("1", request.getParameter("start_time2"));map2.put("2", request.getParameter("end_time2"));break;
		default :map2.put("1",parameter2);
		}
		InvoiceService service = new InvoiceServiceImpl();
		if(map1.size()==2||map2.size()==2){
			page.setRecordCounts(service.getCountByTimeClassify(classify1, map1,classify2, map2));
			request.setAttribute("invoice", service.getCountByTimeClassify(classify1, map1, classify2, map2));//
			request.setAttribute("start_time1", request.getParameter("start_time1"));
			request.setAttribute("end_time1", request.getParameter("end_time1"));
			request.setAttribute("start_time2", request.getParameter("start_time2"));
			request.setAttribute("end_time2", request.getParameter("end_time2"));
		}else{
			page.setRecordCounts(service.getCountByClassify(classify1, parameter1,classify2, parameter2));
			request.setAttribute("invoice", service.getInvoiceByPageInTwo(classify1, parameter1, classify2, parameter2, page));//
		}
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("invoiceCounts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("queryType", "mixSelect");
		request.setAttribute("classify1", classify1);
		request.setAttribute("parameter1", parameter1);
		request.setAttribute("classify2", classify2);
		request.setAttribute("parameter2", parameter2);
		request.setAttribute("str", "mixSelect");
		request.getRequestDispatcher("WEB-INF\\invoice.jsp").forward(request, response);
	}

}
