package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.SoftwareVisitReportService;
import com.eoulu.service.impl.SoftwareVisitReportServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class SoftwareVisitReportOperateBatch
 */
@WebServlet("/SoftwareVisitReportOperateBatch")
public class SoftwareVisitReportOperateBatch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SoftwareVisitReportOperateBatch() {
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
		SoftwareVisitReportService service = new SoftwareVisitReportServiceImpl();
		System.out.println(12121);
		boolean flag = service.moreManualAdd(request);
		response.getWriter().write(new Gson().toJson(flag));
	}

}
