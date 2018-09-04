package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.User;
import com.eoulu.service.IndexService;
import com.eoulu.service.impl.IndexServiceImpl;
import com.eoulu.util.MD5Util;
import com.google.gson.Gson;

/**
 * Servlet implementation class ModifyUserPasswordServlet
 */
@WebServlet("/ModifyUserPassword")
public class ModifyUserPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyUserPasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("test;;;"+123);
		User user = new User();
		IndexService indexService = new IndexServiceImpl();
		
		user.setID(Integer.parseInt(request.getParameter("user_id")));
		user.setUserName(request.getParameter("user_name"));
		user.setUserPwd(new MD5Util().md5ToString(request.getParameter("user_pwd")));
		String newPwd = request.getParameter("new_pwd");
		
		boolean flag = indexService.modifyPassword(user,newPwd)>0?true:false;
		
		response.getWriter().write(new Gson().toJson("{message:"+flag+"}"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
