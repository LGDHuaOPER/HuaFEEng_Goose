package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.PackingListService;
import com.eoulu.service.impl.PackingListServiceImpl;
@WebServlet("/GetPackingListByOne")
public class GetPackingListByOneServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	public GetPackingListByOneServlet(){
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
		PackingListService service = new PackingListServiceImpl();
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		String classify = request.getParameter("type1");
		
		String parameter = request.getParameter("searchContent1").trim();
		
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getCountByClassifyInOne(classify, parameter));
		request.setAttribute("packingList", service.getPackingListByClassifyInOne(classify, parameter, page));
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("packingListCounts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("classify1", classify);
		request.setAttribute("parameter1", parameter);
		request.setAttribute("queryType", "singleSelect");
		request.setAttribute("todayCounts", service.getTodayPackingCounts());
		request.setAttribute("str", "singleSelect");
		request.getRequestDispatcher("WEB-INF\\PackingList.jsp").forward(request, response);
		
	}
	
}
