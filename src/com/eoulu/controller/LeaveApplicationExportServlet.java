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
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.service.StaffApplicationService;
import com.eoulu.service.impl.StaffApplicationServiceImpl;
import com.eoulu.syn.ExportApplication;

/**
 * Servlet implementation class LeaveApplicationExportServlet
 */
@WebServlet("/LeaveApplicationExport")
public class LeaveApplicationExportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeaveApplicationExportServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ExportApplication export = new ExportApplication();
	
		response.getWriter().write(export.exportWord(request));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthorityResource authority = new AuthorityResource();
		String reviewRange = "";
		if(authority.isExist(request, "AllApplication")){
			reviewRange = "all";
		}else if(authority.isExist(request, "DepartmentApplication")){
			reviewRange = "department";
		}else{
			reviewRange = "no";
		}
		
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
	
		String path  = request.getServletContext().getRealPath("/") + "down\\请假申请统计表-"+year+month
				+ ".xlsx";
		boolean result = service.exportExcel(RealName,reviewRange,startTime, endTime, path);
		if(result){
			response.getWriter().write("down\\请假申请统计表-"+year+month
					+ ".xlsx");
		}else{
			response.getWriter().write("false");
		}
	}

}
