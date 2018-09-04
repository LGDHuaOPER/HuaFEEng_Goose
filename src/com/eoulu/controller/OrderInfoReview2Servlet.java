package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.TransportService;
import com.eoulu.service.impl.TransportServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class OrderInfoReview2Servlet
 */
@WebServlet("/OrderInfoReview2")
public class OrderInfoReview2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderInfoReview2Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean flag = false;
		
		int orderInfoID = 0,review2 = 0,userID = 0;
		TransportService transportService = new TransportServiceImpl();
		
		try{orderInfoID = Integer.parseInt(request.getParameter("order_info_id"));}catch(Exception e){}
		try{review2 = Integer.parseInt(request.getParameter("review2"));}catch(Exception e){}
		try{userID = Integer.parseInt(request.getParameter("review_name2"));}catch(Exception e){}
		
		if(orderInfoID!=0 && userID!=0)
			flag = transportService.modifyReview2Status(review2, userID, orderInfoID);
		
		response.getWriter().write(new Gson().toJson("{\"message\":\""+flag+"\"}"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
