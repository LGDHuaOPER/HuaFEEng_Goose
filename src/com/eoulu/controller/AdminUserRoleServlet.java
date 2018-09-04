package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.Role;
import com.eoulu.entity.UserRole;
import com.eoulu.service.AdminService;
import com.eoulu.service.impl.AdminServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class AdminUserRoleServlet
 */
@WebServlet("/AdminUserRole")
public class AdminUserRoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUserRoleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String classify = null;
		boolean flag = false;
		UserRole userRole = new UserRole();
		AdminService adminService = new AdminServiceImpl();
		
		
		
		try{classify=request.getParameter("classify");}catch(Exception e){}
		try{userRole.setUserID(Integer.parseInt(request.getParameter("name_id")));}catch(Exception e){userRole.setUserID(0);}
		try{userRole.setRole(request.getParameter("authority"));}catch(Exception e){}
		
	
//		System.out.println(new Gson().toJson(userRole));
		
		switch(classify){
		case "修改":flag = adminService.modifyUserRole(userRole);break;
		}
		
		
		request.setAttribute("sign", flag);
		request.setAttribute("message", classify+(flag?"成功":"失败"));	
		
		
		request.getRequestDispatcher("Admin").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
