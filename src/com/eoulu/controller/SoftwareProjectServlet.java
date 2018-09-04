package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.AuthorityResource;
import com.eoulu.commonality.Page;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.SoftwareProjectService;
import com.eoulu.service.impl.SoftwareProjectServiceImpl;


@WebServlet("/SoftwareProject")
public class SoftwareProjectServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SoftwareProjectServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		SoftwareProjectService service = new SoftwareProjectServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		String column = req.getParameter("Column")==null?"":req.getParameter("Column");
		String content = req.getParameter("Content")==null?"":req.getParameter("Content");
		String order = req.getParameter("Order")==null?"":req.getParameter("Order");
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		boolean authority = AuthorityResource.isExist(req, "SoftwareProject");
	
		page.setRecordCounts(service.getAllCounts(column,content));
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("datas",service.getAllData(page,column,content,order));
		req.setAttribute("authority", authority);
		req.setAttribute("column", column);
		req.setAttribute("content", content);
		req.setAttribute("order", order);
		
		new AccessStatistics().operateAccess(req, "开发项目管理");
//		UserAccessService acc = new UserAccessServiceImpl();
//		int userID = Integer.parseInt(req.getSession().getAttribute("userID").toString());
//		int count = acc.getCounts(userID, "软件项目管理");
//		UserAccess access = new UserAccess();
//		access.setJspName("软件项目管理");
//		access.setUserID(userID);
//		access.setAccessCount(count++);
//		System.out.println(acc.operate(access));
		req.getRequestDispatcher("WEB-INF//SoftwareProject.jsp").forward(req, resp);
			
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	

}
