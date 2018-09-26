package com.eoulu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.AuthorityResource;
import com.eoulu.commonality.Page;
import com.eoulu.entity.Reimburse;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.ReimburseService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.ReimburseServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class ReimburseServlet
 */
@WebServlet("/Reimburse")
public class ReimburseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReimburseServlet() {
        super();
   
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loadType = request.getParameter("LoadType")==null?"":request.getParameter("LoadType");
		if(loadType.equals("data")){
			int currentPage = Integer
					.parseInt(request.getParameter("CurrentPage") == null ? "1" : request.getParameter("CurrentPage"));
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
			boolean auth = AuthorityResource.isExist(request,"AllReimburse");
			
			ReimburseService service = new ReimburseServiceImpl();

			Page page = new Page();
			Map<String, Object> map = new HashMap<>();
			page.setCurrentPage(currentPage);
			page.setRows(10);
			if(auth){
				page.setRecordCounts(service.getCounts(startTime,endTime));
				map.put("datas", service.getAllData(page, startTime, endTime));
			}else{
				String email = request.getSession().getAttribute("email").toString();
				page.setRecordCounts(service.getOnlyCounts(startTime, endTime,email));
				map.put("datas", service.getOnlyData(page, startTime, endTime,email));
			}
			
		
			map.put("currentPage",currentPage);
			
			map.put("pageCount", page.getPageCounts());
			response.getWriter().write(new Gson().toJson(map));
		}else{
			ReimburseService service = new ReimburseServiceImpl();
			String email = (String) request.getSession().getAttribute("email");
	
			request.setAttribute("name",service.getUserName(email) );
			new AccessStatistics().operateAccess(request, "报销申请");
			request.getRequestDispatcher("WEB-INF\\reimburse.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Type");
		int ID = request.getParameter("ID")==null?0:Integer.parseInt(request.getParameter("ID"));
		String Name = request.getParameter("Name")==null?"":request.getParameter("Name").trim();
		String Department = request.getParameter("Department")==null?"":request.getParameter("Department").trim();
		String Pass = request.getParameter("Pass");
		double TotalAmount = Double.parseDouble(request.getParameter("TotalAmount")==null?"0.0":request.getParameter("TotalAmount"));
		float TravelDay = Float.parseFloat(request.getParameter("TravelDay")==null?"0":request.getParameter("TravelDay"));
		
		String detailJson = request.getParameter("DetailJson")==null?"":request.getParameter("DetailJson");
		String travelJson = request.getParameter("TravelJson")==null?"":request.getParameter("TravelJson");
		Reimburse reimburse = new Reimburse();
		reimburse.setID(ID);
		reimburse.setName(Name);
		reimburse.setDepartment(Department);
		reimburse.setPass(Pass);
		reimburse.setTotalAmount(TotalAmount);
		reimburse.setTravelDay(TravelDay);

		
		ReimburseService service = new ReimburseServiceImpl();
		LogInfoService service2 = new LogInfoServiceImpl();
		switch (type) {
		case "add":
			service2.insert(request, "报销申请", "添加"+Name);
			response.getWriter().write(service.addReimburse(reimburse, detailJson, travelJson));
			break;

		case "update":
			service2.insert(request, "报销申请", "修改"+Name);
			response.getWriter().write(new Gson().toJson(service.updateReimburse(reimburse, detailJson, travelJson)));
			break;
		}
	
	
		
	}

}
