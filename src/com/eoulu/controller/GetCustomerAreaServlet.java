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
 * Servlet implementation class GetCustomerAreaServlet
 */
@WebServlet("/GetCustomerArea")
public class GetCustomerAreaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCustomerAreaServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequirementService service = new RequirementServiceImpl();
		
		int id = Integer.parseInt(request.getParameter("CustomerID")==null?"0":request.getParameter("CustomerID"));
	
		response.getWriter().write(new Gson().toJson(service.getProvince(id)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
