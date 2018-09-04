package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.log.AccessStatistics;
import com.eoulu.service.ExaminationService;
import com.eoulu.service.impl.ExaminationServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class AssessmentStatisticsServlet
 */
@WebServlet("/AssessmentStatistics")
public class AssessmentStatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssessmentStatisticsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		String loadType = request.getParameter("LoadType") == null?"":request.getParameter("LoadType");
		if(loadType.equals("data")){
			String Number = request.getParameter("Number") == null?"E1-1":request.getParameter("Number");
			ExaminationService service = new ExaminationServiceImpl();
			response.getWriter().write(new Gson().toJson(service.getDepartmentStatistics(Number)));
		}else{
			new AccessStatistics().operateAccess(request, "考核明细");
			request.getRequestDispatcher("WEB-INF//examination.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
