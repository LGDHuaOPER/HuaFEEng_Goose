package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.SoftwareDepartmentService;
import com.eoulu.service.impl.SoftwareDepartmentServiceImpl;
import com.google.gson.Gson;
@WebServlet("/SoftwareDepartmentBatchUpload")
public class SoftwareDepartmentBatchUploadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public SoftwareDepartmentBatchUploadServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	SoftwareDepartmentService service = new SoftwareDepartmentServiceImpl();
		System.out.println("EUCP========");
		resp.getWriter().write(new Gson().toJson(service.moreSoftwareDepartmentAdd(req)));	}
	

}
