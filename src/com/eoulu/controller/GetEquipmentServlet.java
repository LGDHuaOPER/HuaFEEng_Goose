package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.dao.EquipmentDao;
import com.eoulu.service.EquipmentService;
import com.eoulu.service.impl.EquipmentServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class GetEquipmentServlet
 */
@WebServlet("/GetEquipment")
public class GetEquipmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEquipmentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			EquipmentService ei = new EquipmentServiceImpl();
			
			String model = "";
			
			response.getWriter().write(new Gson().toJson(new EquipmentServiceImpl().getAllEquipmentByName(model)));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
