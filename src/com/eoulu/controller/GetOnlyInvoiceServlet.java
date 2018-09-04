package com.eoulu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.dao.InvoiceDao;
import com.eoulu.service.InvoiceService;
import com.eoulu.service.impl.InvoiceServiceImpl;
import com.google.gson.Gson;
@WebServlet("/GetOnlyInvoice")
public class GetOnlyInvoiceServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	public GetOnlyInvoiceServlet(){
		super();
	}
	 @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 //小眼睛
		 InvoiceService service = new InvoiceServiceImpl();

//		 Page page = new Page();
//		 String currentPage = request.getParameter("currentPage");
//		 page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
//		 page.setRows(10);
//		 page.setRecordCounts(service.getAllcounts());
//		 request.setAttribute("currentPage", page.getCurrentPage());
//		 request.setAttribute("invoiceCounts", page.getRecordCounts());
//		 request.setAttribute("pageCounts", page.getPageCounts());
//		 request.setAttribute("queryType", "common");
		 System.out.println(request.getParameter("ID"));
		 int ID = Integer.parseInt(request.getParameter("ID"));
		 //List<Map<String,Object>> ls = service.getAllInvoice(ID);
		 //request.setAttribute("invoice", service.getAllInvoice(ID));

		
		//request.getRequestDispatcher("WEB-INF\\invoice.jsp").forward(request, response);
		 response.getWriter().write(new Gson().toJson(service.getAllInvoice(ID)));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	
	}
}
