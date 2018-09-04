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

import com.eoulu.commonality.AuthorityResource;
import com.eoulu.commonality.Page;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.TaskService;
import com.eoulu.service.impl.TaskServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class TaskingServlet
 */
@WebServlet("/Tasking")
public class TaskingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskingServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Type") == null?"common":request.getParameter("Type");
		String column1 = request.getParameter("Column1") == null?"":request.getParameter("Column1");
		String content1 = request.getParameter("Content1") == null?"":request.getParameter("Content1");
		String column2 = request.getParameter("Column2") == null?"":request.getParameter("Column2");
		String content2 = request.getParameter("Content2") == null?"":request.getParameter("Content2");
		String currentPage = request.getParameter("CurrentPage");
		TaskService service = new TaskServiceImpl();
		Page page = new Page();
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getCounts(type, column1, content1, column2, content2));
		List<Map<String, Object>> list = service.getDataByPage(page, type, column1, content1, column2, content2);
		Map<String, Object> map = new HashMap<>();
		map.put("data", list);
		map.put("pageCount", page.getPageCounts());
		map.put("currentPage", currentPage);
		response.getWriter().write(new Gson().toJson(map));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthorityResource auth = new AuthorityResource();
		boolean authority = auth.isExist(request, "Tasks");
		if(authority){
			new AccessStatistics().operateAccess(request, "任务分配");
			request.getRequestDispatcher("WEB-INF//Tasking.jsp").forward(request, response);
		}else{
			response.getWriter().write(new Gson().toJson("{message:没有对应权限}"));
		}
	}

}
