package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.util.FormfactorMailUtil;

/**
 * Servlet implementation class FormfactorMailTestServlet
 */
@WebServlet("/FormfactorMailTest")
public class FormfactorMailTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormfactorMailTestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("dddddddddd");
		FormfactorMailUtil util = new FormfactorMailUtil("AChen3@formfactor.com", "AChen3@formfactor.com","w04c0me!");
		util.sendHtmlEmail("test", "test", null, new String[]{"sunmengying@eoulu.com"}, new String[]{"sunmengying@eoulu.com"});
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
