package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.MailConfig;
import com.eoulu.service.MailConfigService;
import com.eoulu.service.impl.MailConfigServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class MailConfigServlet
 */
@WebServlet("/MailConfig")
public class MailConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailConfigServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("Page");
		MailConfigService service = new MailConfigServiceImpl();
		response.getWriter().write(new Gson().toJson(service.getConfig(page)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String Page = request.getParameter("Page");
		String ToList = request.getParameter("ToList");
		String CopyList = request.getParameter("CopyList");
		
		MailConfigService service = new MailConfigServiceImpl();
		MailConfig config = new MailConfig();
		config.setPage(Page);
		config.setToList(ToList);
		config.setCopyList(CopyList);
		response.getWriter().write(service.modifyConfig(config));
	}

}
