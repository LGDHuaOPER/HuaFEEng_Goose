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
import com.eoulu.service.ReimburseService;
import com.eoulu.service.impl.ReimburseServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class ReimburseMailListServlet
 */
@WebServlet("/ReimburseMailList")
public class ReimburseMailListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReimburseMailListServlet() {
        super();
 
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String loadType = request.getParameter("LoadType")==null?"":request.getParameter("LoadType");

		if(loadType.equals("data")){
			int currentPage = Integer
					.parseInt(request.getParameter("CurrentPage") == null ? "1" : request.getParameter("CurrentPage"));
			ReimburseService service = new ReimburseServiceImpl();
			Page page = new Page();
			page.setCurrentPage(currentPage);
			page.setRows(10);
			page.setRecordCounts(service.getListCount());
			Map<String, Object> map = new HashMap<>();
			map.put("currentPage",currentPage);
			map.put("datas", service.getList(page));
			map.put("pageCount", page.getPageCounts());
			response.getWriter().write(new Gson().toJson(map));
		}else {
			request.getRequestDispatcher("WEB-INF\\reimburseMailList.jsp").forward(request, response);

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
