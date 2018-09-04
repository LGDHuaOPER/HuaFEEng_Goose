package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.RequirementService;
import com.eoulu.service.impl.RequirementServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class GetCustomerServlet
 */
@WebServlet("/GetCustomer")
public class GetCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCustomerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	
		RequirementService service = new RequirementServiceImpl();
		String content = request.getParameter("CustomerName")==null?"":request.getParameter("CustomerName");
		response.getWriter().write(new Gson().toJson(service.getAllCustomer(content)));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
