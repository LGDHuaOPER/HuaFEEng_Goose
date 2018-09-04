package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.dao.InvoiceDao;
import com.eoulu.entity.UserAccess;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.InvoiceService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.InvoiceServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;

@WebServlet("/Invoice")
public class InvoiceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public InvoiceServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		InvoiceDao dao = new InvoiceDao();
		InvoiceService service = new InvoiceServiceImpl();
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		page.setCurrentPage(currentPage == null ? 1 : Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getAllcounts());
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("invoiceCounts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("queryType", "common");
		request.setAttribute("invoice", dao.getOnlyInvoice(page));
		new AccessStatistics().operateAccess(request, "发票制作");
		request.getRequestDispatcher("WEB-INF\\invoice.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}
}
