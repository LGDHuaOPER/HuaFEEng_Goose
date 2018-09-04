package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.StaffApplicationService;
import com.eoulu.service.impl.StaffApplicationServiceImpl;

/**
 * Servlet implementation class LeaveApplicationOperateServlet
 */
@WebServlet("/LeaveApplicationOperate")
public class LeaveApplicationOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeaveApplicationOperateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StaffApplicationService service = new StaffApplicationServiceImpl();
		
		if(request.getParameter("Type").equals("Review")){
			response.getWriter().write(service.updateReview(request));
		}else{
			response.getWriter().write(service.operate(request));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
