package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.StaffInfoService;
import com.eoulu.service.impl.StaffInfoServiceImpl;
import com.google.gson.Gson;



/**
 * Servlet implementation class StaffInfoOperateServlet
 */
@WebServlet("/StaffInfoOperate")
public class StaffInfoOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StaffInfoOperateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StaffInfoService service = new StaffInfoServiceImpl();
		String type = request.getParameter("type") == null?"":request.getParameter("type");
		if(type.equals("delete")){
			response.getWriter().write(new Gson().toJson(service.delete(request)));
		}else{
			response.getWriter().write(new Gson().toJson(service.operate(request)));
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StaffInfoService service = new StaffInfoServiceImpl();
		response.getWriter().write(service.uploadFile(request));
	}

}
