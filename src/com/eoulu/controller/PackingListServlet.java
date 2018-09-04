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
import com.eoulu.service.PackingListService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.PackingListServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;


@WebServlet("/PackingList")
public class PackingListServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;
	
	public PackingListServlet(){
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		PackingListService service = new PackingListServiceImpl();
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getAllCounts());
		request.setAttribute("queryType", "common");
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("packingCounts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("packingList", service.getAllPacking(page));
		request.setAttribute("todayCounts", service.getTodayPackingCounts());
		new AccessStatistics().operateAccess(request, "箱单制作");
		request.getRequestDispatcher("WEB-INF\\PackingList.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}
