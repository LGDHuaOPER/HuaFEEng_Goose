package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.StaffInfoService;
import com.eoulu.service.impl.StaffInfoServiceImpl;

@WebServlet("/ExportStaffInfo")
public class ExportStaffInfoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		StaffInfoService service = new StaffInfoServiceImpl();
        service.exportExcel(resp);  
      
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		StaffInfoService service = new StaffInfoServiceImpl();
		  service.exportPhoto(resp);
	}

	


}
