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
@WebServlet("/GetPackingListByTwo")
public class GetPackingListByTwoServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public GetPackingListByTwoServlet(){
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
		String currentPage = request.getParameter("currentPage");//new String(request.getParameter("TotalGrossWeight").getBytes("ISO-8859-1"),"utf-8")
		String classify1 = request.getParameter("type1");
		String parameter1 = request.getParameter("searchContent1").trim();//new String(request.getParameter("searchContent1").trim().getBytes("ISO-8859-1"),"utf-8");
		String classify2 = request.getParameter("type2");
		String parameter2 = new String(request.getParameter("searchContent2").trim().getBytes("ISO-8859-1"),"utf-8");
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getCountByClassifyInTwo(classify1, parameter1, classify2, parameter2));
		request.setAttribute("packingList", service.getPackingListByClassifyInTwo(classify1, parameter1, classify2, parameter2, page));
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("packingListCounts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("classify1", classify1);
		request.setAttribute("parameter1", parameter1);
		request.setAttribute("classify2", classify2);
		request.setAttribute("parameter2", parameter2);
		request.setAttribute("queryType", "mixSelect");
		request.setAttribute("todayCounts", service.getTodayPackingCounts());
		request.setAttribute("str", "mixSelect");
		request.getRequestDispatcher("WEB-INF\\PackingList.jsp").forward(request, response);
		
	}
}
