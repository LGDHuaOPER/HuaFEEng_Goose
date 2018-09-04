package com.eoulu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.google.gson.Gson;
@WebServlet("/OperationLog")
public class OperationLogServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public OperationLogServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		LogInfoService service = new LogInfoServiceImpl();
		
		String currentPage = req.getParameter("currentPage")==null?"1":req.getParameter("currentPage");
		String column = req.getParameter("Column");
		Page page = new Page();
		page.setCurrentPage(Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getAllCounts());
		Map<String,Object> map = new HashMap<>();
		map.put("logs", service.getAllData(page,null));
		map.put("currentPage", page.getCurrentPage());
		map.put("pageCounts", page.getPageCounts());
//		req.setAttribute("datas", service.getAllData(page, column));
//		req.setAttribute("currentPage", page.getCurrentPage());
//		req.setAttribute("Counts", page.getRecordCounts());
//		req.setAttribute("pageCounts", page.getPageCounts());
//		
//		req.getRequestDispatcher("WEB-INF/admin.jsp").forward(req, resp);
		resp.getWriter().write(new Gson().toJson(map));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	
//	public static void main(String[] args) {
//		LogInfoService service = new LogInfoServiceImpl();
//		Page page = new Page();
//		page.setCurrentPage(1);
//		page.setRows(6);
//		page.setRecordCounts(service.getAllCounts());
//		Map<String,Object> map = new HashMap<>();
//		map.put("logs", service.getAllData(page,null));
//		map.put("currentPage", page.getCurrentPage());
//		map.put("pageCounts", page.getPageCounts());
//		System.out.println(map);
//	}

}
