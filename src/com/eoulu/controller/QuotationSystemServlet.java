package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.QuoteSystemServiceImpl;

@WebServlet("/QuotationSystem")
public class QuotationSystemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public QuotationSystemServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		QuoteSystemService service = new QuoteSystemServiceImpl();

		Page page = new Page();
	
		String currentPage = request.getParameter("currentPage");
		String classify1 = request.getParameter("classify1");
		String param1 = request.getParameter("param1");
		String classify2 = request.getParameter("classify2");
		String param2 = request.getParameter("param2");
		String queryType = request.getParameter("queryType");
		page.setCurrentPage(currentPage == null ? 1 : Integer.parseInt(currentPage));
		page.setRows(10);
		if (queryType == null || queryType.equals("common")) {
			new AccessStatistics().operateAccess(request, "报价系统");
			request.setAttribute("classify1", "");
			request.setAttribute("param1", "");
			page.setRecordCounts(service.getAllCounts(classify1, param1,classify2, param2));
			request.setAttribute("quotes", service.getAllQuoteInfo(page, classify1, param1,null, null));
			request.setAttribute("queryType", "common");
		} else {
			if (queryType.equals("SingleSelect")) {

				request.setAttribute("classify1", classify1);
				request.setAttribute("param1", param1);
				page.setRecordCounts(service.getAllCounts(classify1, param1,classify2, param2));
				request.setAttribute("quotes", service.getAllQuoteInfo(page, classify1, param1,null, null));
				request.setAttribute("queryType", queryType);
			}
			if (queryType.equals("MixSelect")) {
				
				request.setAttribute("classify1", classify1);
				request.setAttribute("param1", param1);
				request.setAttribute("classify2", classify2);
				request.setAttribute("param2", param2);
				page.setRecordCounts(service.getAllCounts(classify1, param1,classify2, param2));
				request.setAttribute("quotes", service.getAllQuoteInfo(page, classify1, param1,classify2, param2));
				request.setAttribute("queryType", queryType);
			}
		}

		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("quotesCounts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("department", "商务部");

		request.getRequestDispatcher("WEB-INF\\quotelist.jsp").forward(request, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
