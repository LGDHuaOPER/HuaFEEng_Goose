package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.User;
import com.eoulu.service.AdminService;
import com.eoulu.service.impl.AdminServiceImpl;
import com.google.gson.Gson;

/**
 * �û���Ϣ�Ĳ����࣬���޸ĺ������û���Ϣ
 */
@WebServlet("/UserOperate")
public class UserOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserOperateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String classify = request.getParameter("classify");
		AdminService adminService = new AdminServiceImpl();
		
		User user = new User();
		String flag = "false";
		
		try{user.setID(Integer.parseInt(request.getParameter("id")));}catch(Exception e){}
		try{user.setUserName(request.getParameter("user_name"));}catch(Exception e){}
		try{user.setUserPwd(request.getParameter("user_pwd"));}catch(Exception e){}
		try{user.setSex(request.getParameter("sex"));}catch(Exception e){}
		try{user.setEmail(request.getParameter("email"));}catch(Exception e){}
		try{user.setPhone(request.getParameter("phone"));}catch(Exception e){}
		
		
		switch(classify){
		case "新增":flag = adminService.insertUser(user)+"";break;
		case "修改":flag = adminService.modifyUser(user)+"";break;
		case "删除":flag = adminService.deleteUser(user.getID())+"";break;
		}
		
		
		
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
