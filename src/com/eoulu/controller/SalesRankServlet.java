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
 * 销售排名的接口，访问该接口可以统计出销售的排名情况
 */
@WebServlet("/SalesRank")
public class SalesRankServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SalesRankServlet() {
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
		response.getWriter().write(new Gson().toJson(statisticsService.getStatisticsBySales(startTime,endTime)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
