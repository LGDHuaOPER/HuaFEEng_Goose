package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.StatisticsService;
import com.eoulu.service.impl.StatisticsServiceImpl;

/**
 * Servlet implementation class ExportStatisticsServlet
 */
@WebServlet("/ExportStatistics")
public class ExportStatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public ExportStatisticsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StatisticsService service = new StatisticsServiceImpl();
		String year = request.getParameter("year");
		String title = "";
		if(year.equals("All")){
			title = "全部销售统计信息";
		}else{
			title = year+"年销售统计信息";
		}
		service.exportExcel(service.getExcelData(year), title, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
