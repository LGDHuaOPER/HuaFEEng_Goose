package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.dao.CustomerDao;
import com.eoulu.entity.UserAccess;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.MachineDetailsService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.MachineDetailsServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;
@WebServlet("/MachineDetails")
public class MachineDetailsServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public MachineDetailsServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MachineDetailsService service = new MachineDetailsServiceImpl();
		Page page = new Page();
		int currentPage = Integer.parseInt(req.getParameter("currentPage")==null ? "1":req.getParameter("currentPage"));
		page.setCurrentPage(currentPage);
		page.setRows(10);
		page.setRecordCounts(service.getAllcounts());
		req.setAttribute("machine", service.getMachineDetails(page));
		req.setAttribute("queryType", "common");
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("machineCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		//req.setAttribute("customers", new CustomerDao().getAllCustomer());
		new AccessStatistics().operateAccess(req, "机台统计");
		req.getRequestDispatcher("WEB-INF//machine.jsp").forward(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	
}
