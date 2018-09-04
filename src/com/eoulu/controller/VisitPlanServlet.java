package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.dao.CustomerDao;
import com.eoulu.service.MachineDetailsService;
import com.eoulu.service.VisitPlanService;
import com.eoulu.service.impl.MachineDetailsServiceImpl;
import com.eoulu.service.impl.VisitPlanserviceImpl;

@WebServlet("/VisitPlan")
public class VisitPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		VisitPlanService service = new VisitPlanserviceImpl();
		String page = request.getParameter("Page");
		String queryType = request.getParameter("queryType");
		page = page==null?"1":page;
		String Content = request.getParameter("Content");
		if(queryType==null||"common".equals(queryType)) {
			int count = service.getVisitPlanCount();
			int AllPage = count%10==0?count/10:count/10+1;
			request.setAttribute("queryType", "common");
			request.setAttribute("AllPage", AllPage);
			request.setAttribute("Page", page);
			request.setAttribute("VisitPlans",service.getVisitPlanByPage(page));
		}else {
			
			int count = service.getVisitPlanCountByContent(Content);
			int AllPage = count%10==0?count/10:count/10+1;
			request.setAttribute("Content", Content);
			request.setAttribute("AllPage", AllPage);
			request.setAttribute("queryType", "search");
			request.setAttribute("Page", page);
			request.setAttribute("VisitPlans",service.getVisitPlanByPageAndContent(page,Content));
		}
		
		request.getRequestDispatcher("WEB-INF\\VisitPlan.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
