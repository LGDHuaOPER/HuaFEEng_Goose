package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.CustomerInquiryService;
import com.eoulu.service.impl.CustomerInquiryServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class CustomerInquiryServlet
 */
@WebServlet("/CustomerInquiry")
public class CustomerInquiryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerInquiryServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String content = request.getParameter("Content")==null?"":request.getParameter("Content");
		int currentPage = Integer.parseInt(request.getParameter("currentPage")==null?"1":request.getParameter("currentPage"));
		CustomerInquiryService service = new CustomerInquiryServiceImpl();
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setRows(10);
		page.setRecordCounts(service.getAllCounts(content));
		request.setAttribute("datas",service.getAllData(page, content));
		request.setAttribute("Content",content);
		request.setAttribute("currentPage",currentPage);
		request.setAttribute("pageCounts",page.getPageCounts());
		new AccessStatistics().operateAccess(request, "客户询价记录");
		request.getRequestDispatcher("WEB-INF/CustomerInquiry.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String classify = request.getParameter("classify");
		String content  = request.getParameter("Content");
		String column = request.getParameter("column")==null?"":request.getParameter("column");
		CustomerInquiryService service = new CustomerInquiryServiceImpl();
		switch (classify) {
		case "Customer":
			response.getWriter().write(new Gson().toJson(service.getAllCustomer(content)));
			break;

		case "SoftwareProduct":
			response.getWriter().write(new Gson().toJson(service.getAllProductModel(content,column)));
			break;
		}
	}

}
