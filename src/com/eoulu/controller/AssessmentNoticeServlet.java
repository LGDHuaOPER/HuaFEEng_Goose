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
 * Servlet implementation class AssessmentNoticeServlet
 */
@WebServlet("/AssessmentNotice")
public class AssessmentNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssessmentNoticeServlet() {
        super();
   
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int currentPage = Integer.parseInt(request.getParameter("CurrentPage") == null?"1":request.getParameter("CurrentPage"));
		String column1 = request.getParameter("Column1")==null?"":request.getParameter("Column1");
		String content1 = request.getParameter("Content1")==null?"":request.getParameter("Content1");
		ExaminationService service = new ExaminationServiceImpl();
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setRows(10);
		page.setRecordCounts(service.getAssessmentNoticeCount(column1, content1));
		List<Map<String, Object>> list = service.getAssessmentNotice(page, column1, content1);
		Map<String, Object> map = new HashMap<>();
		map.put("data", list);
		map.put("pageCount", page.getPageCounts());
		map.put("currentPage", currentPage);
		response.getWriter().write(new Gson().toJson(map));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Type");
		int subjectID = Integer.parseInt(request.getParameter("SubjectID")==null?"0":request.getParameter("SubjectID"));
		String[] examiners = request.getParameterValues("Examiners[]");
		String[] examinerID = request.getParameterValues("ExaminerID[]");
		String subject = request.getParameter("Subject") == null?"":request.getParameter("Subject");
		String number = request.getParameter("Number") == null?"":request.getParameter("Number");
		String time = request.getParameter("Time") == null?"":request.getParameter("Time");
		String department = request.getParameter("Department") == null?"":request.getParameter("Department");
		Examination examination = new Examination();
		examination.setID(subjectID);
		examination.setSubject(subject);
		examination.setNumber(number);
	
		examination.setTime(time);
		examination.setDepartment(department);
		
		ExaminationService service = new ExaminationServiceImpl();
		switch (type) {
		case "send":
			String recipient = request.getParameter("Recipient")==null?"":request.getParameter("Recipient");
			response.getWriter().write(service.sendAssessmentNotice(examination, examiners,examinerID,recipient));
			break;

		case "save":
			response.getWriter().write(service.saveAssessmentNotice(examination, examiners,examinerID));
			break;
		}
		
		
	}

}
