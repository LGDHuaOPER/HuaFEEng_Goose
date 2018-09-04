package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.entity.UserAccess;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.SupplierService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.SupplierServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;

/**
 * Servlet implementation class SupplierServlet
 */
@WebServlet("/Supplier")
public class SupplierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupplierServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		SupplierService supplierService = new SupplierServiceImpl();
		String column1 = request.getParameter("Column1") == null?"":request.getParameter("Column1");
		String content1 = request.getParameter("Content1") == null?"":request.getParameter("Content1");
		Page page = new Page();
		try{page.setRows(10);}catch(Exception e){}
		try{page.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));}catch(Exception e){page.setCurrentPage(1);}
		page.setRecordCounts(supplierService.getSupplierCounts(column1,content1));
		
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("currentPage", page.getCurrentPage());

		request.setAttribute("suppliers", supplierService.getSupplierByPage(page,column1,content1));
		new AccessStatistics().operateAccess(request, "供应商管理");
		request.getRequestDispatcher("WEB-INF\\supplier.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
