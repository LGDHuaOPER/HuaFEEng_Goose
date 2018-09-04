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
 * Servlet implementation class SoftwareVisitReportOperate
 */
@WebServlet("/SoftwareVisitReportOperate")
public class SoftwareVisitReportOperate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SoftwareVisitReportOperate() {
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
		boolean flag = false;
//		if(upload.equals("only")){
			flag = service.insert(request);
//		}
//		if(upload.equals("more")){
//			flag = service.moreManualAdd(request);
//		}
		response.getWriter().write(new Gson().toJson(flag));
	}

}
