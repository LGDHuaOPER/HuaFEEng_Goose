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
import com.eoulu.service.SoftwareProductService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.SoftwareProductServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;

/**
 * Servlet implementation class SoftwareProductServlet
 */
@WebServlet("/SoftwareProduct")
public class SoftwareProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SoftwareProductServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String queryType = request.getParameter("queryType")==null?"common":request.getParameter("queryType");
		String content = request.getParameter("content");
		queryType = (content==null||content.equals(""))?"common":queryType;
		int currentPage = Integer
				.parseInt(request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage"));
		SoftwareProductService service = new SoftwareProductServiceImpl();
		Page page = new Page();
		page.setRows(10);
		switch (queryType) {
		case "select":
			page.setRecordCounts(service.getQueryCounts(content));
			currentPage = page.getPageCounts()<currentPage?1:currentPage;
			page.setCurrentPage(currentPage);
			page.setRecordCounts(service.getQueryCounts(content));
			request.setAttribute("datas", service.getQueryResult(content, page));
			break;
		default:
			page.setCurrentPage(currentPage);
			page.setRecordCounts(service.getAllCounts());
			request.setAttribute("datas", service.getAllData(page));
			new AccessStatistics().operateAccess(request, "软件产品管理");
			break;
		}
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("queryType", queryType);
		request.setAttribute("content", content);
		request.setAttribute("currentPage", currentPage);
		System.out.println(page.getPageCounts()<currentPage);
		request.getRequestDispatcher("WEB-INF/SoftwareProduct.jsp").forward(request, response);
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
