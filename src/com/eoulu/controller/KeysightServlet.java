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
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.KeysightService;
import com.eoulu.service.impl.KeysightServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class KeysightServlet
 */
@WebServlet("/Keysight")
public class KeysightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KeysightServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loadType = request.getParameter("LoadType") == null?"":request.getParameter("LoadType");
		if(loadType.equals("data")){
			String queryType = request.getParameter("QueryType") == null?"common":request.getParameter("QueryType");
			String column1 = request.getParameter("Column1") == null?"":request.getParameter("Column1");
			String content1 = request.getParameter("Content1") == null?"":request.getParameter("Content1");
			String column2 = request.getParameter("Column2") == null?"":request.getParameter("Column2");
			String content2 = request.getParameter("Content2") == null?"":request.getParameter("Content2");
			String currentPage = request.getParameter("CurrentPage");
			KeysightService service = new KeysightServiceImpl();
			Page page = new Page();
			page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
			page.setRows(10);
			page.setRecordCounts(service.getCounts(queryType, column1, content1, column2, content2));
			List<Map<String, Object>> list = service.getDataByPage(page, queryType, column1, content1, column2, content2);
			Map<String, Object> map = new HashMap<>();
			map.put("data", list);
			map.put("pageCount", page.getPageCounts());
			map.put("currentPage", currentPage);
			response.getWriter().write(new Gson().toJson(map));
		}else{
			new AccessStatistics().operateAccess(request, "Keysight");
			request.getRequestDispatcher("WEB-INF//Keysight.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
