package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.dao.RoleDao;
import com.eoulu.entity.Role;
import com.eoulu.service.AdminService;
import com.eoulu.service.impl.AdminServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class AdminRoleServlet
 */
@WebServlet("/AdminRole")
public class AdminRoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminRoleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String classify = null;
		boolean flag = false;
		Role role = new Role();
		AdminService adminService = new AdminServiceImpl();
		
		
		
		try{classify=request.getParameter("classify");}catch(Exception e){}
		try{role.setID(Integer.parseInt(request.getParameter("id")));}catch(Exception e){}
		try{role.setAuthority(request.getParameter("authority"));}catch(Exception e){}
		try{role.setName(request.getParameter("name"));}catch(Exception e){}
	
		
		switch(classify){
		case "新增":flag = adminService.insertRole(role);break;
		case "修改":flag = adminService.modifyRole(role);break;
		}
		
		
		request.setAttribute("sign", flag);
		request.setAttribute("message", classify+(flag?"成功":"失败"));	

//		System.out.println(new Gson().toJson(role));
		
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
