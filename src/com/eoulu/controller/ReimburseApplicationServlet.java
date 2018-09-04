package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.AuthorityResource;
import com.eoulu.service.ReimburseService;
import com.eoulu.service.impl.ReimburseServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class ReimburseApplicationServlet
 */
@WebServlet("/ReimburseApplication")
public class ReimburseApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReimburseApplicationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int RequestID = request.getParameter("RequestID")==null?0:Integer.parseInt(request.getParameter("RequestID"));
		ReimburseService service = new ReimburseServiceImpl();
		response.getWriter().write(new Gson().toJson(service.getApplication(RequestID)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	//审核
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		boolean flag = AuthorityResource.isExist(request, "ReimburseReview");
		if(flag){
			int ID = request.getParameter("ID")==null?0:Integer.parseInt(request.getParameter("ID"));
			String state = request.getParameter("State");
			String name = request.getParameter("Name");
			String filingDate = request.getParameter("FilingDate");
			String reason = request.getParameter("Reason");
			ReimburseService service = new ReimburseServiceImpl();
			response.getWriter().write(service.review(ID, state, name, reason, filingDate));
		}else{
			response.getWriter().write("您无此权限！");
		}
		
		
	}

}
