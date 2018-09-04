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
import com.itextpdf.text.pdf.PRAcroForm;
@WebServlet("/GetAfterSaleByPageOne")
public class GetAfterSaleByPageOneServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public GetAfterSaleByPageOneServlet(){
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
		String classify = req.getParameter("type1");
		
		String parameter = req.getParameter("searchContent1").trim();
		if(parameter.equals("待解决")){
			parameter = "1";
		}else if(parameter.equals("已完结")){
			parameter = "2";
		}
		
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getCountByClassify(classify, parameter));
		req.setAttribute("afterSale",service.getAfterSaleByPageInOne(classify, parameter, page));
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("afterSaleCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("classify1", classify);
		req.setAttribute("parameter1", parameter);
		req.setAttribute("queryType", "singleSelect");
		req.setAttribute("customers", new CustomerDao().getAllCustomer());
		req.getRequestDispatcher("WEB-INF//afterSale.jsp").forward(req, resp);
		
	}
	

}
