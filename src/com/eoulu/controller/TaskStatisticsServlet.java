package com.eoulu.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.TaskService;
import com.eoulu.service.impl.TaskServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class TaskStatistics
 */
@WebServlet("/TaskStatistics")
public class TaskStatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskStatisticsServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currYear = Calendar.getInstance().get(Calendar.YEAR)+"";
		String year = request.getParameter("Year") == null?currYear:request.getParameter("Year");
		String month = request.getParameter("Month") == null? "All":request.getParameter("Month");
		String type = request.getParameter("Type") == null? "Amount":request.getParameter("Type");
		String queryType = request.getParameter("QueryType")==null?"Common": request.getParameter("QueryType");
		TaskService service = new TaskServiceImpl();
		if(queryType.equals("Common")){
			request.setAttribute("Year", year);
			request.setAttribute("Month", month);
			request.setAttribute("Data",service.getStatistics(year, month,type));
			request.getRequestDispatcher("WEB-INF/taskingstatistics.jsp").forward(request, response);
		}else{
		
			Map<String,Object> map = new HashMap<>();
			map.put("Data", service.getStatistics(year, month,type));
			map.put("Year", year);
			map.put("Month", month);
			response.getWriter().write(new Gson().toJson(map));
		}
	}
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
