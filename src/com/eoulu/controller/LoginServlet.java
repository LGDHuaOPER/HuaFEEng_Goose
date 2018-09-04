package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.User;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.LoginService;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.LoginServiceImpl;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.eoulu.util.IPLocationUtil;
import com.eoulu.util.MD5Util;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;




	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogInfoService log = new LogInfoServiceImpl();
		if(log.getOutDate()){
			log.delete();
		}
		new IPLocationUtil().getIPAndCity(request);
		if(request.getSession().getAttribute("user")==null)
			request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
		else
			request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogInfoService log = new LogInfoServiceImpl();
		if(log.getOutDate()){
			log.delete();
		}
		new IPLocationUtil().getIPAndCity(request);
		User user = new User();
		MD5Util md = new MD5Util();
		try {
			user.setUserName(request.getParameter("user_name"));
			user.setUserPwd(md.md5ToString(request.getParameter("user_pwd")));
		} catch (Exception e) {
			request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
			return;
		}
		QuoteSystemService service = new QuoteSystemServiceImpl();
		String email = "";
		if(service.getCurrentUserEmail(request.getParameter("user_name")).size()>1){
			email = service.getCurrentUserEmail(request.getParameter("user_name")).get(1).get("Email").toString();
		}

		
		LoginService ls = new LoginServiceImpl();
		int sign = ls.toLogin(user);
	
		
		if(sign==0){
			request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
		}else if(sign>=1){
			System.out.println(123);
			request.getSession().setAttribute("user", user.getUserName());
			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("userID", sign);
			request.getSession().setAttribute("role",ls.getUserRole(user.getUserName()) );
			request.getSession().setAttribute("authorities", ls.getAuthorityByID(sign+""));
			String time = this.getServletContext().getAttribute("startTime").toString();
			System.out.println(time);
			request.getSession().setAttribute("startTime", time);
			ls.loginSuccess(ls.getAuthorityByID(sign+""), request, response);
			ls.setLastLogin(user.getUserName());
		}else if(sign==-1){
			request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
		}



		//�ж��û��������Ƿ���ȷ��0�û������ڣ�1��¼�ɹ���2���벻��ȷ��3��֤�����=======����

	}

}
