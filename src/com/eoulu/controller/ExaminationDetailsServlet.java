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
import com.eoulu.entity.Examination;
import com.eoulu.service.ExaminationService;
import com.eoulu.service.impl.ExaminationServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class ExaminationDetailsServlet
 */
@WebServlet("/ExaminationDetails")
public class ExaminationDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExaminationDetailsServlet() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		String type = request.getParameter("Type");
		ExaminationService service = new ExaminationServiceImpl();

		switch (type) {
		case "number":
			String Subject = request.getParameter("Subject")==null?"":request.getParameter("Subject");
			response.getWriter().write(new Gson().toJson(service.getNumber(Subject)));
			break;
		case "subject":
			response.getWriter().write(new Gson().toJson(service.getSubject()));
			break;

		case "details":
			int currentPage = Integer.parseInt(request.getParameter("CurrentPage") == null?"1":request.getParameter("CurrentPage"));
			String department = request.getParameter("Department")==null?"":request.getParameter("Department");
			String number = request.getParameter("Number")==null?"":request.getParameter("Number");
			Page page = new Page();
			page.setCurrentPage(currentPage);
			page.setRows(10);
			page.setRecordCounts(service.queryCounts(department, number));
			List<Map<String, Object>> list = service.queryDetails(page, department, number);
			Map<String, Object> map = new HashMap<>();
			map.put("data", list);
			map.put("pageCount", page.getPageCounts());
			map.put("currentPage", currentPage);
			response.getWriter().write(new Gson().toJson(map));
			break;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String item = request.getParameter("Item") == null?"":request.getParameter("Item");
		String type = request.getParameter("Type") == null?"":request.getParameter("Type");
		
		ExaminationService service = new ExaminationServiceImpl();
		boolean result = true;
		switch (item) {
		

		case "examination":
			int examinationID = Integer.parseInt(request.getParameter("ID") == null?"0":request.getParameter("ID"));
			String SerialNumber = request.getParameter("SerialNumber").trim();
			String Subject = request.getParameter("Subject").trim();
			String Number = request.getParameter("Number").trim();
			String Time = request.getParameter("Time").equals("")?"0000-00-00":request.getParameter("Time");
			String Classify = request.getParameter("Classify") == null?"":request.getParameter("Classify");
			Examination examination = new Examination();
			examination.setID(examinationID);
			examination.setSerialNumber(SerialNumber);
			examination.setSubject(Subject);
			examination.setNumber(Number);
			examination.setTime(Time);
			examination.setClassify(Classify);
			if(type.equals("add")){
				result = service.insertExamination(examination);
			}else if(type.equals("update")){
				result = service.updateExamination(examination);
			}
			response.getWriter().write(new Gson().toJson(result));
			break;
			
		case "score":
			String scoreJson = request.getParameter("ScoreJson") == null?"":request.getParameter("ScoreJson");
			response.getWriter().write(service.saveScore(scoreJson));
			break;
		
		}
		
		
		
	}

}
