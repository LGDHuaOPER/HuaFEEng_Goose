package com.eoulu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.entity.UserAccess;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.TransportDirectiveService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.TransportDirectiveServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;
@WebServlet("/TransportDirective")
public class TransportDirectiveServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public TransportDirectiveServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TransportDirectiveService service = new TransportDirectiveServiceImpl();
		Page page = new Page();
		String queryType = req.getParameter("queryType");
		String currentPage = req.getParameter("currentPage");
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		List<Map<String, Object>> data = null;
		if(queryType==null||"common".equals(queryType)) {
			queryType = "common";
			page.setRecordCounts(service.getAllCounts());
			data = service.getAllData(page);
			new AccessStatistics().operateAccess(req, "运输指令");
		}else if("singleSelect".equals(queryType)) {
			String type1 = req.getParameter("type1");
			req.setAttribute("type1", type1);
			String searchContent1 = req.getParameter("searchContent1");
			req.setAttribute("searchContent1", searchContent1);
			page.setRecordCounts(service.getAllCounts(type1,searchContent1));
			data = service.getAllData(page,type1,searchContent1);
		}else {
			String type1 = req.getParameter("type1");
			req.setAttribute("type1", type1);
			String searchContent1 = req.getParameter("searchContent1");
			req.setAttribute("searchContent1", searchContent1);
			String type2 = req.getParameter("type2");
			req.setAttribute("type2", type2);
			String searchContent2 = req.getParameter("searchContent2");
			req.setAttribute("searchContent2", searchContent2);
			page.setRecordCounts(service.getAllCounts(type1,searchContent1,type2,searchContent2));
			data = service.getAllData(page,type1,searchContent1,type2,searchContent2);
		}
		req.setAttribute("queryType", queryType);
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("Counts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("data", data);
		
		req.getRequestDispatcher("WEB-INF//DomesticTransport.jsp").forward(req, resp);
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}

}
