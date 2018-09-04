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
import com.eoulu.service.AdminService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.AdminServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/Authority")
public class AuthorityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthorityServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AdminService adminService = new AdminServiceImpl();
//		LogInfoService service = new LogInfoServiceImpl();
//		String currentPage = request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage");
//		String column = request.getParameter("Column");
//		Page page = new Page();
//		page.setCurrentPage(Integer.parseInt(currentPage));
//		page.setRows(6);
//		page.setRecordCounts(service.getAllCounts());
		int model = 0;
		try {
			model = Integer.parseInt(request.getParameter("model"));
		} catch (Exception e) {
			model = 0;
		}
		String userName = request.getParameter("UserName") == null?"":request.getParameter("UserName");
		request.setAttribute("roles", adminService.getAllRole());
		request.setAttribute("authorities", adminService.getUserAuthority(userName));
		request.setAttribute("model", model);
		
		
		Map<String,Object> map = new HashMap<>();
		map.put("roles", adminService.getAllRole());
		map.put("authorities", adminService.getUserAuthority(userName));
		map.put("model", model);
		response.getWriter().write(new Gson().toJson(map));
		
//		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
