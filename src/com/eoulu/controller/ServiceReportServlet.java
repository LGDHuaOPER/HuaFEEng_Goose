package com.eoulu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.AuthorityResource;
import com.eoulu.commonality.Page;
import com.eoulu.entity.ServiceReport;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.ServiceReportService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.ServiceReportServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class ServiceReportServlet
 */
@WebServlet("/ServiceReport")
public class ServiceReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loadType = request.getParameter("LoadType") == null?"":request.getParameter("LoadType");
		if(loadType.equals("data")){
			String queryType = request.getParameter("QueryType") == null?"common":request.getParameter("QueryType");
			String column1 = request.getParameter("Column1") == null?"":request.getParameter("Column1");
			String content1 = request.getParameter("Content1") == null?"":request.getParameter("Content1");
			String column2 = request.getParameter("Column2") == null?"":request.getParameter("Column2");
			String content2 = request.getParameter("Content2") == null?"":request.getParameter("Content2");
			String currentPage = request.getParameter("CurrentPage");
			ServiceReportService service = new ServiceReportServiceImpl();
			Page page = new Page();
			page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
			page.setRows(10);
			page.setRecordCounts(service.getCounts(queryType, column1, content1, column2, content2));
			List<Map<String, Object>> list = service.getAllData(page, queryType, column1, content1, column2, content2);
			Map<String, Object> map = new HashMap<>();
			map.put("data", list);
			map.put("pageCount", page.getPageCounts());
			map.put("currentPage", currentPage);
			response.getWriter().write(new Gson().toJson(map));
		}else if(loadType.equals("staffCode")){
			ServiceReportService service = new ServiceReportServiceImpl();
			String email = request.getSession().getAttribute("email").toString();
			response.getWriter().write(service.getReportNumber(email));
			
		}else{
			new AccessStatistics().operateAccess(request, "服务完成报告");
			request.getRequestDispatcher("WEB-INF//ServiceReport.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Type") == null?"":request.getParameter("Type");
		int ID = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
		String Number = request.getParameter("Number") == null?"":request.getParameter("Number").trim();
		String Project = request.getParameter("Project") == null?"":request.getParameter("Project").trim();
		String CustomerTitle = request.getParameter("CustomerTitle") == null?"":request.getParameter("CustomerTitle").trim();
		String CustomerName = request.getParameter("CustomerName") == null?"":request.getParameter("CustomerName").trim();
		String LinkInfo = request.getParameter("LinkInfo") == null?"":request.getParameter("LinkInfo").trim();
		String StaffName = request.getParameter("StaffName") == null?"":request.getParameter("StaffName").trim();
		String ContractNo = request.getParameter("ContractNo") == null?"":request.getParameter("ContractNo").trim();
		String ProductVersion = request.getParameter("ProductVersion") == null?"":request.getParameter("ProductVersion").trim();
		String FileName = request.getParameter("FileName") == null?"":request.getParameter("FileName").trim();
		
		ServiceReport report = new ServiceReport();
		report.setID(ID);
		report.setNumber(Number);
		report.setProject(Project);
		report.setCustomerTitle(CustomerTitle);
		report.setCustomerName(CustomerName);
		report.setLinkInfo(LinkInfo);
		report.setStaffName(StaffName);
		report.setContractNo(ContractNo);
		report.setProductVersion(ProductVersion);
		report.setFileName(FileName);
		ServiceReportService service = new ServiceReportServiceImpl();
		boolean result = true;
		switch (type) {
		case "add":
			result = service.insert(report);
			if(result){
				LogInfoService logService = new LogInfoServiceImpl();
				logService.insert(request, "服务完成报告","添加完成报告-"+Number);
			}
			response.getWriter().write(new Gson().toJson(result));
			break;

		case "update":
			result = service.update(report);
			if(result){
				LogInfoService logService = new LogInfoServiceImpl();
				logService.insert(request, "服务完成报告","修改完成报告-"+Number);
			}
			response.getWriter().write(new Gson().toJson(result));
			break;
		case "delete":
			boolean exist = AuthorityResource.isExist(request,"DeleteServiceReport");
			if(exist){
				result = service.deleteRecord(ID);
				if(result){
					LogInfoService logService = new LogInfoServiceImpl();
					logService.insert(request, "服务完成报告","删除");
				}
				response.getWriter().write(new Gson().toJson(result));
			}
			else{
				response.getWriter().write("没有权限！");
			}
		}
			
		
		
		
	}

}
