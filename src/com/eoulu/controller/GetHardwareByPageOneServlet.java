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


@WebServlet("/GetHardwareByPageOne")
public class GetHardwareByPageOneServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;

	public GetHardwareByPageOneServlet(){
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
		String classify = req.getParameter("type1");
		
		String parameter = req.getParameter("searchContent1").trim();
		req.setAttribute("parameter1", parameter);
		switch (parameter) {
		case "交付":
			parameter = "1";
			break;
		case "尾款":
			parameter = "2";
			break;
		case "完结":
			parameter = "3";
			break;

		default:
			break;
		}
		
		System.out.println(parameter);
		
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getCountByClassify(classify, parameter));
		req.setAttribute("hardware", service.getHardwareAdvancesByPageInOne(classify, parameter, page));
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("hardwareCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("classify1", classify);
		
		req.setAttribute("queryType", "singleSelect");
		req.getRequestDispatcher("WEB-INF//hardware.jsp").forward(req, resp);
		
	}
	
}
