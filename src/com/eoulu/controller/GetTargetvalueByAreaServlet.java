package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.StatisticsService;
import com.eoulu.service.impl.StatisticsServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class GetTargetvalueByAreaServlet
 */
@WebServlet("/GetTargetvalueByArea")
public class GetTargetvalueByAreaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTargetvalueByAreaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
		StatisticsService statisticsService = new StatisticsServiceImpl();
		response.getWriter().write(new Gson().toJson(statisticsService.getTargetValueByArea(startTime,endTime)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
