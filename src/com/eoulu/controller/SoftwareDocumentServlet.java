package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.AuthorityResource;
import com.eoulu.commonality.Page;
import com.eoulu.entity.UserAccess;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.RequestAchieveService;
import com.eoulu.service.SoftwareDepartmentService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.RequestAchieveServiceImpl;
import com.eoulu.service.impl.SoftwareDepartmentServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;

@WebServlet("/SoftwareDocument")
public class SoftwareDocumentServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	SoftwareDepartmentService softService = new SoftwareDepartmentServiceImpl();
	RequestAchieveService achieveService = new RequestAchieveServiceImpl();
	
	public SoftwareDocumentServlet() {
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String content = req.getParameter("content");
		String queryType = req.getParameter("queryType");
		Page page = new Page();
		String type="Software";
		AuthorityResource auth = new AuthorityResource();
		boolean flag = auth.isExist(req, "SoftwareDocument");
		if(flag){
			int currentPage = Integer
					.parseInt(req.getParameter("currentPage") == null ? "1" : req.getParameter("currentPage"));
			page.setCurrentPage(currentPage);
			page.setRows(10);
			if (queryType == null || queryType.equals("common")) {
				new AccessStatistics().operateAccess(req, "软件部文档");
				page.setRecordCounts(softService.getAllcounts(type));
				req.setAttribute("manual", softService.getSoftwareDepartment(page, type,"DESC"));
		
			}else{
					page.setRecordCounts(softService.getAllcounts(type));
					req.setAttribute("manual", softService.getSoftwareDepartment(page, type,"DESC"));
					req.setAttribute("content", content);
				
			}

		}
		
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("manualCounts", page.getRecordCounts());
		req.setAttribute("queryType",queryType);
		req.setAttribute("softwareDocument",flag);
		req.getRequestDispatcher("WEB-INF\\SoftwareDocument.jsp").forward(req, resp);

	
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	
	

}
