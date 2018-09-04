package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.dao.CustomerDao;
import com.eoulu.entity.UserAccess;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.AfterSaleService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.AfterSaleServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;
@WebServlet("/AfterSale")
public class AfterSaleServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public AfterSaleServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AfterSaleService service = new AfterSaleServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getAllcounts());
		req.setAttribute("queryType", "common");
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("afterSaleCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("afterSale", service.getAfterSale(page));
		//req.setAttribute("customers", new CustomerDao().getAllCustomer());
		new AccessStatistics().operateAccess(req, "售后维修");
		req.getRequestDispatcher("WEB-INF//afterSale.jsp").forward(req, resp);
		
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	

}
