package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.InvoiceService;
import com.eoulu.service.impl.InvoiceServiceImpl;
@WebServlet("/GetInvoiceByPageOne")
public class GetInvoiceByPageOneServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;
	public GetInvoiceByPageOneServlet() {
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
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		InvoiceService service = new InvoiceServiceImpl();
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		String classify = request.getParameter("type1");
		System.out.println("classify:"+classify);
		String parameter = request.getParameter("searchContent1").trim();
		System.out.println("parameter:"+parameter);
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		if(classify.equals("出发时间")||classify.equals("发票签订时间")||classify.equals("操作时间")){
			String start_time1=request.getParameter("start_time1");
			String end_time1=request.getParameter("end_time1");
			page.setRecordCounts(service.getCountByClassifyTime(classify, start_time1, end_time1));
			request.setAttribute("invoice", service.getInvoiceByTime(classify, start_time1, end_time1, page));
			request.setAttribute("start_time1", start_time1);
			request.setAttribute("end_time1", end_time1);
		}else{
			page.setRecordCounts(service.getCountByClassify(classify, parameter));
			request.setAttribute("invoice", service.getInvoiceByPageInOne(classify, parameter, page));
		}
		
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("invoiceCounts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("classify1", classify);
		request.setAttribute("parameter1", parameter);
		request.setAttribute("queryType", "singleSelect");
		request.setAttribute("str", "singleSelect");
		request.getRequestDispatcher("WEB-INF\\invoice.jsp").forward(request, response);
	}
}
