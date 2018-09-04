package com.eoulu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.PageVisit;
import com.eoulu.service.PageVisitService;
import com.eoulu.service.impl.PageVisitServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class PageVisitServlet
 */
@WebServlet("/PageVisit")
public class PageVisitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PageVisitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loadType = request.getParameter("LoadType")==null?"":request.getParameter("LoadType");
		if(loadType.equals("data")){
			PageVisitService service = new PageVisitServiceImpl();
			response.getWriter().write(new Gson().toJson(service.getData()));
		}else{
			request.getRequestDispatcher("WEB-INF/PageVisit.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String PageName = request.getParameter("PageName")==null?"":request.getParameter("PageName");
		String Department = request.getParameter("Department")==null?"":request.getParameter("Department");
		PageVisit visit = new PageVisit();
		visit.setPageName(PageName);
		visit.setDepartment(Department);
		List<Map<String, Object>> pvData = (List<Map<String, Object>>) request.getServletContext().getAttribute("pvData");
		PageVisitService service = new PageVisitServiceImpl();
		service.updateCollection(pvData, visit);
		response.getWriter().write("更新成功");
		
	}

}
