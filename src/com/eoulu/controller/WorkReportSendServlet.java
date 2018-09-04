package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.AuthorityResource;
import com.eoulu.service.WorkReportService;
import com.eoulu.service.impl.WorkReportServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class WorkReportSendServlet
 */
@WebServlet("/WorkReportSend")
public class WorkReportSendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorkReportSendServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("Name").trim();
		WorkReportService service = new WorkReportServiceImpl();
		response.getWriter().write(new Gson().toJson(service.urge(name)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Type");
		int ID = request.getParameter("ID")==null?0:Integer.parseInt(request.getParameter("ID"));

		WorkReportService service = new WorkReportServiceImpl();
		boolean result = false;
		switch (type) {
		case "comments":
			boolean flag = AuthorityResource.isExist(request, "SendComments");
			if(flag){
				String text = request.getParameter("Text")==null?"":request.getParameter("Text");
				String email = request.getSession().getAttribute("email").toString();
				String name = request.getParameter("Name").trim();
				response.getWriter().write(service.sendComments(ID,name, text, email));
			}else{
				response.getWriter().write("您无此权限！");

			}
			break;

		case "log":
			result = service.sendLog(request);
			response.getWriter().write(new Gson().toJson(result));
			break;
		}
		
	}

}
