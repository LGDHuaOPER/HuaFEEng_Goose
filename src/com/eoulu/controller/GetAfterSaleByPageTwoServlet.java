package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.dao.CustomerDao;
import com.eoulu.service.AfterSaleService;
import com.eoulu.service.impl.AfterSaleServiceImpl;
@WebServlet("/GetAfterSaleByPageTwo")
public class GetAfterSaleByPageTwoServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public GetAfterSaleByPageTwoServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AfterSaleService service = new AfterSaleServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		String classify1 = req.getParameter("type1");
		String parameter1 = req.getParameter("searchContent1").trim();
		String classify2 = req.getParameter("type2");
		String parameter2 = req.getParameter("searchContent2").trim();
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getCountByClassify(classify1, parameter1, classify2, parameter2));
		req.setAttribute("afterSale",service.getAfterSaleByPageInTwo(classify1, parameter1, classify2, parameter2, page));
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("afterSaleCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("classify1", classify1);
		req.setAttribute("parameter1", parameter1);
		req.setAttribute("classify2", classify2);
		req.setAttribute("parameter2", parameter2);
		req.setAttribute("queryType", "mixSelect");
		req.setAttribute("customers", new CustomerDao().getAllCustomer());
		req.getRequestDispatcher("WEB-INF//afterSale.jsp").forward(req, resp);
	}
	
	
}
