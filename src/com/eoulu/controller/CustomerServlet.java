package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.entity.UserAccess;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.InformationBankService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.InformationBankServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;

/**
 * Servlet implementation class CustomerServlet
 */
@WebServlet("/Customer")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Page page = new Page();
		InformationBankService informationBankService = new InformationBankServiceImpl();
		
		String content="";
		int currentPage = 0;
		try{content = request.getParameter("content")==null?"":request.getParameter("content");}catch(Exception e){content = "";}
		try{currentPage =Integer.parseInt(request.getParameter("currentPage").toString());}catch(Exception e){currentPage = 1;}
		page.setCurrentPage(currentPage);
		page.setRows(10);
		page.setRecordCounts(informationBankService.CustomerQueryCounts(content));
		new AccessStatistics().operateAccess(request, "客户信息表");
		
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("customers", informationBankService.CustomerQueryByName(page, content));
		request.setAttribute("content", content);
		
		request.getRequestDispatcher("WEB-INF\\customer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
