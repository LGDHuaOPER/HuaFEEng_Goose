package com.eoulu.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.User;
import com.eoulu.service.AdminService;
import com.eoulu.service.impl.AdminServiceImpl;

/**
 * Servlet implementation class AdminUserServlet
 */
@WebServlet("/AdminUser")
public class AdminUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String classify = null;
		boolean flag = false;
		AdminService adminService = new AdminServiceImpl();
		try{classify = request.getParameter("classify");}catch(Exception e){}
		
		
		User user = new User();
		
		try{user.setID(Integer.parseInt(request.getParameter("id")));}catch(Exception e){}
		try{user.setUserName(request.getParameter("user_name"));}catch(Exception e){}
		try{user.setSex(request.getParameter("sex"));}catch(Exception e){}
		try{user.setEmail(request.getParameter("email"));}catch(Exception e){}
		try{user.setPhone(request.getParameter("phone"));}catch(Exception e){}
		
		String role = request.getParameter("Role") == null?"":request.getParameter("Role");
		String[] idStr = request.getParameterValues("idList[]");
		int[] idList = null;
		if(idStr!=null){
			idList = new int[idStr.length];
			for(int i = 0;i < idStr.length;i ++){
				idList[i] = Integer.parseInt(idStr[i]);
			}
		}
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		String createDate = sdFormat.format(new Date());
		user.setRole(role);
		user.setCreateDate(createDate);
	
		
		

		
		switch(classify){
		case "新增": flag = adminService.insertUser(user);break;
		case "修改": flag = adminService.modifyUser(user);break;
		case "删除": flag = adminService.deleteUser(user.getID());break;
		case "批量删除":flag =  adminService.deleteBatch(idList);break;
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
