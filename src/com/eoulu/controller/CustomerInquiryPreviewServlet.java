package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.CustomerInquiryService;
import com.eoulu.service.impl.CustomerInquiryServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class CustomerInquiryPreviewServlet
 */
@WebServlet("/CustomerInquiryPreview")
public class CustomerInquiryPreviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerInquiryPreviewServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerInquiryService service = new CustomerInquiryServiceImpl();
		String classify = request.getParameter("classify");
		int id = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		switch (classify) {
		case "Modify":
			response.getWriter().write(new Gson().toJson(service.getOperateData(id)));
			break;

		case "Preview":
			response.getWriter().write(new Gson().toJson(service.getPreviewData(id)));
			break;
		}
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerInquiryService service = new CustomerInquiryServiceImpl();
		int id = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		response.getWriter().write(new Gson().toJson(service.updateServiceTime(id)));
	}

}
