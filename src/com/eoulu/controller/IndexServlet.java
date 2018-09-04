package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.eoulu.commonality.AuthorityResource;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.UserAccessServiceImpl;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/Index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		
		HttpSession ss = request.getSession();
		AuthorityResource auth = new AuthorityResource();
		boolean flag = auth.isExist(request, "Software");
		boolean flag2 = auth.isExist(request, "OtherDocument");
		request.getSession().setAttribute("software", flag);
		request.getSession().setAttribute("otherDocument", flag2);
		UserAccessService service= new UserAccessServiceImpl();
		request.setAttribute("datas", service.getAllData(request));
		request.getRequestDispatcher("WEB-INF//index.jsp").forward(request, response);;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
