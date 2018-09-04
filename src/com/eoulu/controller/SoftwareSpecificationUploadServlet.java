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
 * Servlet implementation class SoftwareSpecificationUploadServlet
 */
@WebServlet(description = "开发规范上传", urlPatterns = { "/SoftwareSpecificationUpload" })
public class SoftwareSpecificationUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SoftwareSpecificationUploadServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DevelopmentSpecificationService service = new DevelopmentSpecificationServiceImpl();
		
		response.getWriter().write(service.insert(request));
	
	}

}
