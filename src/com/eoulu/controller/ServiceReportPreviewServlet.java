package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.ServiceReportService;
import com.eoulu.service.impl.ServiceReportServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class ServiceReportPreviewServlet
 */
@WebServlet("/ServiceReportPreview")
public class ServiceReportPreviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceReportPreviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int reportID = request.getParameter("ReportID") == null?0:Integer.parseInt(request.getParameter("ReportID"));
		ServiceReportService service = new ServiceReportServiceImpl();
		response.getWriter().write(new Gson().toJson(service.preview(reportID)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Type");
		String previewJson = request.getParameter("PreviewJson") == null?"":request.getParameter("PreviewJson");
		String Number = request.getParameter("Number") == null?"":request.getParameter("Number").trim();
		int reportID = request.getParameter("ReportID") == null?0:Integer.parseInt(request.getParameter("ReportID"));
		ServiceReportService service = new ServiceReportServiceImpl();
		switch (type) {
		case "save":
			response.getWriter().write(new Gson().toJson(service.savePreview(previewJson, reportID)));
			break;

		case "download":
			response.getWriter().write(service.exportFile(request));
			break;
		}
		
		
	}

}
