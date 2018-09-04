package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.SoftwareImplementationService;
import com.eoulu.service.impl.SoftwareImplementationServiceImpl;

/**
 * Servlet implementation class SoftwareImplementationServlet
 */
@WebServlet(description = "软件实施记录-页面入口", urlPatterns = { "/SoftwareImplementation" })
public class SoftwareImplementationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SoftwareImplementationServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SoftwareImplementationService service = new SoftwareImplementationServiceImpl();
		int currentPage = Integer.parseInt(request.getParameter("currentPage")==null?"1":request.getParameter("currentPage"));
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		String column = request.getParameter("column")==null?"":request.getParameter("column");
		String order = request.getParameter("order")==null?"DESC":request.getParameter("order");
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setRows(10);
		page.setRecordCounts(service.getAllCounts(content, column));
		request.setAttribute("datas", service.getAllData(page, content, column, order));
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("content", content);
		request.setAttribute("column", column);
		request.setAttribute("order", order);
		
		new AccessStatistics().operateAccess(request, "软件实施管理");
		request.getRequestDispatcher("WEB-INF/Implementation.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		SoftwareImplementationService service = new SoftwareImplementationServiceImpl();
		response.getWriter().write(service.operate(request));
	
	}

}
