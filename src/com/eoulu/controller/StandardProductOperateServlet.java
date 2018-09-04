package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.AuthorityResource;
import com.eoulu.service.StandardProductService;
import com.eoulu.service.impl.StandardProductServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class StandardProductOperateServlet
 */
@WebServlet("/StandardProductOperate")
public class StandardProductOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StandardProductOperateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = request.getParameter("ID")==null?0:Integer.parseInt(request.getParameter("ID"));
	
		boolean flag = AuthorityResource.isExist(request, "StandardProductReview");
		if(flag){
			StandardProductService service = new StandardProductServiceImpl();
			response.getWriter().write(new Gson().toJson(service.review(ID)));
		}else{
			response.getWriter().write("没有权限！");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = request.getParameter("ID")==null?0:Integer.parseInt(request.getParameter("ID"));
		String title = request.getParameter("Title")==null?"":request.getParameter("Title");
		StandardProductService service = new StandardProductServiceImpl();
		response.getWriter().write(service.sendReviewMail(ID, title));

		
	}

}
