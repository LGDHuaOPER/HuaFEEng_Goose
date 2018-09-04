package com.eoulu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.AdminService;
import com.eoulu.service.impl.AdminServiceImpl;
import com.google.gson.Gson;

@WebServlet("/User")
public class UserServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AdminService service = new AdminServiceImpl();
		String queryType = req.getParameter("QueryType") == null?"":req.getParameter("QueryType");
		String currentPage = req.getParameter("CurrentPage");

		
		Page page = new Page();
		
		Map<String,Object> map = new HashMap<>();
	

		
		if(queryType.equals("")){
			page.setRows(10);
			page.setRecordCounts(service.getUserCount());
			page.setCurrentPage(currentPage == null?1:Integer.parseInt(currentPage));
			
			map.put("data", service.getUserByPage(page));
			map.put("currentPage", page.getCurrentPage());
			map.put("pageCounts", page.getPageCounts());
			
		}else{
			String content = req.getParameter("Content") == null?"":req.getParameter("Content");
			page.setRows(10);
			page.setRecordCounts(service.getQueryCount(queryType, content));
			page.setCurrentPage(currentPage == null?1:Integer.parseInt(currentPage));
			map.put("data", service.query(queryType, content,page));
			map.put("currentPage", page.getCurrentPage());
			map.put("pageCounts", page.getPageCounts());
		}
		resp.getWriter().write(new Gson().toJson(map));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
	

}
