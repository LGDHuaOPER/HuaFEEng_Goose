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

import com.eoulu.commonality.Page;
import com.eoulu.entity.TrainingRecords;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.TrainingRecordsService;
import com.eoulu.service.impl.TrainingRecordsServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class TrainingRecordsServlet
 */
@WebServlet("/TrainingRecords")
public class TrainingRecordsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrainingRecordsServlet() {
        super();
    
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loadType = request.getParameter("LoadType") == null?"":request.getParameter("LoadType");
		if(loadType.equals("data")){

			String column = request.getParameter("Column") == null?"":request.getParameter("Column");
			String content = request.getParameter("Content") == null?"":request.getParameter("Content");
			String currentPage = request.getParameter("CurrentPage");
			TrainingRecordsService service = new TrainingRecordsServiceImpl();
			Page page = new Page();
			page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
			page.setRows(10);
			page.setRecordCounts(service.getCounts(column,content));
			List<Map<String, Object>> list = service.getDataByPage(page,column, content);
			Map<String, Object> map = new HashMap<>();
			map.put("data", list);
			map.put("pageCount", page.getPageCounts());
			map.put("currentPage", currentPage);
			response.getWriter().write(new Gson().toJson(map));
		}else{
			new AccessStatistics().operateAccess(request, "培训记录");
			request.getRequestDispatcher("WEB-INF//TrainingRecords.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Type") == null?"":request.getParameter("Type");
		int ID = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
		int staffID = request.getParameter("StaffID") == null?0:Integer.parseInt(request.getParameter("StaffID"));
		String trainingPeriods = request.getParameter("TrainingPeriods") == null?"":request.getParameter("TrainingPeriods");
		String refreshTime = request.getParameter("RefreshTime") == null?"":request.getParameter("RefreshTime");
		
		TrainingRecords records = new TrainingRecords();
		records.setID(ID);
		records.setStaffID(staffID);
		records.setTrainingPeriods(trainingPeriods);
		records.setRefreshTime(refreshTime);
		
		TrainingRecordsService service = new TrainingRecordsServiceImpl();
		boolean result = true;
		switch (type) {
		case "add":
			result = service.insert(records);
			break;

		case "update":
			result = service.update(records);
			break;
		}
		response.getWriter().write(new Gson().toJson(result));
		
	}

}
