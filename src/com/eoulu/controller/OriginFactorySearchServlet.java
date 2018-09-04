package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.OriginFactoryService;
import com.eoulu.service.impl.OriginFactoryServiceImpl;

@WebServlet("/OriginFactorySearch")
public class OriginFactorySearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public OriginFactorySearchServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String queryType = request.getParameter("queryType");
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		String type1 = request.getParameter("type1");
		String content1 = request.getParameter("content1");
		OriginFactoryService service = new OriginFactoryServiceImpl();
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setRows(10);
		
		if("mix".equals(queryType)) {
			String type2 = request.getParameter("type2");
			String content2 = request.getParameter("content2");
			request.setAttribute("type2", type2);
			request.setAttribute("content2",content2);
			page.setRecordCounts(service.getAllCountsByTwo(type1,content1,type2,content2));
			request.setAttribute("POInfo",service.getAllDataByTwo(page,type1,content1,type2,content2) );
			request.setAttribute("factoryCounts", page.getRecordCounts());
			request.setAttribute("pageCounts", page.getPageCounts());
		}else {
			page.setRecordCounts(service.getAllCountsByOne(type1,content1));
			request.setAttribute("POInfo",service.getAllDataByone(page,type1,content1) );
			request.setAttribute("factoryCounts", page.getRecordCounts());
			request.setAttribute("pageCounts", page.getPageCounts());
		}
		request.setAttribute("type1", type1);
		request.setAttribute("content1",content1);
		request.setAttribute("queryType", queryType);
		request.setAttribute("currentPage",currentPage);
		request.getRequestDispatcher("WEB-INF//OriginFactory.jsp").forward(request, response);
	}

}
