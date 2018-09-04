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
import com.eoulu.service.InsuranceSlipService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.InsuranceSlipServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;
@WebServlet("/Proposal")
public class ProposalServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public ProposalServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		InsuranceSlipService service = new InsuranceSlipServiceImpl();
		Page page = new Page();
		String currentPage = request.getParameter("currentPage");
		
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getAllCounts());
		request.setAttribute("queryType", "common");
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("proposalCounts", page.getRecordCounts());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("proposal", service.getInsuranceSlip(page));
		new AccessStatistics().operateAccess(request, "运输保险");
		request.getRequestDispatcher("WEB-INF//Proposal.jsp").forward(request, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	

}
