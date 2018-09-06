package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.VersionManagement;
import com.eoulu.service.VersionManagementService;
import com.eoulu.service.impl.VersionManagementServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class VersionManagementServlet
 */
@WebServlet("/VersionManagement")
public class VersionManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VersionManagementServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ProjectName = request.getParameter("ProjectName")==null?"futureCT1":request.getParameter("ProjectName");
		VersionManagementService service = new VersionManagementServiceImpl();
		
		response.getWriter().write(new Gson().toJson(service.getData(ProjectName)));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ProjectName = request.getParameter("ProjectName");
		String Version = request.getParameter("Version");
		String Registrant = request.getParameter("Registrant");
		String BoardingTime = request.getParameter("BoardingTime");
		String UpdatedContent = request.getParameter("UpdatedContent");
		String Password = request.getParameter("Password");
		String from = request.getSession().getAttribute("email").toString();
		String vmClassifyHref = request.getParameter("vmClassifyHref");
		
		VersionManagement version = new VersionManagement();
		version.setProjectName(ProjectName);
		version.setVersion(Version);
		version.setRegistrant(Registrant);
		version.setBoardingTime(BoardingTime);
		version.setUpdatedContent(UpdatedContent);
		version.setPassword(Password);
		version.setFrom(from);
		version.setVmClassifyHref(vmClassifyHref);
		
		VersionManagementService service = new VersionManagementServiceImpl();
		response.getWriter().write(new Gson().toJson(service.addVersion(version)));
	}

}
