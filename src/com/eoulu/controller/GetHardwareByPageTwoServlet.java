package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.HardwareAdvancesService;
import com.eoulu.service.impl.HardwareAdvancesServiceImpl;
@WebServlet("/GetHardwareByPageTwo")
public class GetHardwareByPageTwoServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public GetHardwareByPageTwoServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HardwareAdvancesService service = new HardwareAdvancesServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		String classify1 = req.getParameter("type1");
		String parameter1 = req.getParameter("searchContent1").trim();
		String classify2 = req.getParameter("type2");
		String parameter2 = req.getParameter("searchContent2").trim();
		req.setAttribute("parameter1", parameter1);
		req.setAttribute("parameter2", parameter2);
		switch (parameter1) {
		case "交付":
			parameter1 = "1";
			break;
		case "尾款":
			parameter1 = "2";
			break;
		case "完结":
			parameter1 = "3";
			break;

		default:
			break;
		}
		
		switch (parameter2) {
		case "交付":
			parameter2 = "1";
			break;
		case "尾款":
			parameter2 = "2";
			break;
		case "完结":
			parameter2 = "3";
			break;

		default:
			break;
		}
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getCountByClassify(classify1, parameter1, classify2, parameter2));
		req.setAttribute("hardware", service.getHardwareAdvancesByPageInTwo(classify1, parameter1, classify2, parameter2, page));
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("hardwareCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("classify1", classify1);
		
		req.setAttribute("classify2", classify2);
		
		req.setAttribute("queryType", "mixSelect");
		req.getRequestDispatcher("WEB-INF//hardware.jsp").forward(req, resp);
	}
	

}
