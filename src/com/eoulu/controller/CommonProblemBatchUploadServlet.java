package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.CommonProblemService;
import com.eoulu.service.impl.CommonProblemServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class CommonProblemBatchUploadServlet
 */
@WebServlet(description = "常见问题文档批量上传", urlPatterns = { "/CommonProblemBatchUpload" })
public class CommonProblemBatchUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommonProblemBatchUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		
		CommonProblemService service = new CommonProblemServiceImpl();
		
		response.getWriter().write(new Gson().toJson(service.moreAdd(request)));
		
	}

}
