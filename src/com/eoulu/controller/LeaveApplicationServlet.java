package com.eoulu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.AuthorityResource;
import com.eoulu.commonality.Page;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.StaffApplicationService;
import com.eoulu.service.impl.StaffApplicationServiceImpl;

/**
 * Servlet implementation class LeaveApplicationServlet
 */
@WebServlet(description = "请假申请", urlPatterns = { "/LeaveApplication" })
public class LeaveApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LeaveApplicationServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AuthorityResource authority = new AuthorityResource();
		String reviewRange = "";
		if(authority.isExist(request, "AllApplication")){
			reviewRange = "all";
		}else if(authority.isExist(request, "DepartmentApplication")){
			reviewRange = "department";
		}else{
			reviewRange = "no";
		}
		int currentPage = Integer
				.parseInt(request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage"));
		String year = request.getParameter("Year")==null?"":request.getParameter("Year");
		String month = request.getParameter("Month")==null?"":request.getParameter("Month");
		String startTime = "";
		String endTime = "";
		if(!year.equals("")){
				
			if (month.equals("All")) {//按年份查询
				 startTime = year+"-"+"-01";
				 endTime = year+"-12";
			}else{//按年月查询
				 startTime = year+"-"+month;
				 endTime = year+"-"+month;
			}
				
		}
		StaffApplicationService service = new StaffApplicationServiceImpl();
		String email = request.getSession().getAttribute("email").toString();
		List<Map<String, Object>> list = new StaffInfoDao().getTelAndName(email);
		String RealName = "";
		if(list.size()>1){
			RealName = list.get(1).get("StaffName").toString();
		}
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setRows(10);
		page.setRecordCounts(service.getCounts(RealName, reviewRange,startTime,endTime));
		request.setAttribute("datas", service.getAllData(page, RealName, reviewRange,startTime,endTime));
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		new AccessStatistics().operateAccess(request, "请假申请");
		request.getRequestDispatcher("WEB-INF/LeaveApplication.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		
	}

}
