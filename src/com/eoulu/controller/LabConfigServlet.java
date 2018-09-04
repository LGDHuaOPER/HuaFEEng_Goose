package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LabService;
import com.eoulu.service.impl.LabServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class LabConfigServlet
 */
@WebServlet("/LabConfig")
public class LabConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LabConfigServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int LabID = request.getParameter("LabID")==null?0:Integer.parseInt(request.getParameter("LabID"));
		LabService service = new LabServiceImpl();
		response.getWriter().write(new Gson().toJson(service.getConfig(LabID)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String configJson = request.getParameter("ConfigJson")==null?"":request.getParameter("ConfigJson");
		int LabID = request.getParameter("LabID")==null?0:Integer.parseInt(request.getParameter("LabID"));
		LabService service = new LabServiceImpl();
		response.getWriter().write(new Gson().toJson(service.saveConfig(configJson, LabID)));

	}

}
