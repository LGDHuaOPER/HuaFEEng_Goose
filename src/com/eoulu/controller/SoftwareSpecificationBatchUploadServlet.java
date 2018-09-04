package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.DevelopmentSpecificationService;
import com.eoulu.service.impl.DevelopmentSpecificationServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class SoftwareSpecificationBatchUploadServlet
 */
@WebServlet(description = "批量上传", urlPatterns = { "/SoftwareSpecificationBatchUpload" })
public class SoftwareSpecificationBatchUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SoftwareSpecificationBatchUploadServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DevelopmentSpecificationService service = new DevelopmentSpecificationServiceImpl();
	System.out.println("规范----");
		response.getWriter().write(new Gson().toJson(service.moreAdd(request)));
	}

}
