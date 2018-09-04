package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.TrainingRecordsService;
import com.eoulu.service.impl.TrainingRecordsServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class TrainingDetailsServlet
 */
@WebServlet("/TrainingDetails")
public class TrainingDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrainingDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int recordID = request.getParameter("RecordID") == null?0:Integer.parseInt(request.getParameter("RecordID"));
		TrainingRecordsService service = new TrainingRecordsServiceImpl();
		response.getWriter().write(new Gson().toJson(service.getTrainingDetails(recordID)));
				
				
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String detailsJson = request.getParameter("DetailsJson") == null?"":request.getParameter("DetailsJson");
		TrainingRecordsService service = new TrainingRecordsServiceImpl();
		response.getWriter().write(new Gson().toJson(service.saveTrainingDetails(detailsJson)));
		
	
		
	}

}
