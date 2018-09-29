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
import com.eoulu.service.CommonProblemService;
import com.eoulu.service.DevelopmentSpecificationService;
import com.eoulu.service.SoftwareDepartmentService;
import com.eoulu.service.impl.CommonProblemServiceImpl;
import com.eoulu.service.impl.DevelopmentSpecificationServiceImpl;
import com.eoulu.service.impl.SoftwareDepartmentServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class DocumentUploadRouteServlet
 */
@WebServlet("/Software")
public class SoftwareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SoftwareServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String type = request.getParameter("Type");
		String content = request.getParameter("content");
		String  currentPage = request.getParameter("currentPage");
		String queryType = request.getParameter("queryType");
		String column = request.getParameter("column")==null?"OperatingTime":request.getParameter("column");
		String order = request.getParameter("Reorder")==null?"desc":"asc";
		Page page = new Page();
		page.setCurrentPage(currentPage==null? 1: Integer.parseInt(currentPage));
		page.setRows(10);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("currentPage", currentPage);
		List<Map<String,Object>> ls = null;
		if (queryType == null || queryType.equals("common")) {
			if(type.equals("Software")){//EUCP软件管理文档
				SoftwareDepartmentService service = new SoftwareDepartmentServiceImpl();
				page.setRecordCounts(service.getAllcounts(type));
				ls = service.getSoftwareDepartment(page, type,order);
				map.put("pageCounts", page.getPageCounts());
		
			}
			if(type.equals("CommonProblem")){//常见问题
				CommonProblemService service = new CommonProblemServiceImpl();
				page.setRows(5);
				page.setRecordCounts(service.getAllCounts());
				ls = service.getAllData(page,column,order);
				System.out.println(ls);
				map.put("pageCounts", page.getPageCounts());
			}
			if(type.equals("Specification")){//开发规范
				DevelopmentSpecificationService service = new DevelopmentSpecificationServiceImpl();
				page.setRecordCounts(service.getAllCounts());
				ls = service.getAllData(page,column,order);
				map.put("pageCounts", page.getPageCounts());
			}
		}
		if(queryType.equals("SingleSelect")){
			if(type.equals("Software")){
				SoftwareDepartmentService service = new SoftwareDepartmentServiceImpl();
				page.setRecordCounts(service.queryAllCounts(type, content));
				ls = service.querySoftwareDepartment(page, type, content,order);
				map.put("pageCounts", page.getPageCounts());
		
			}
			if(type.equals("CommonProblem")){
				CommonProblemService service = new CommonProblemServiceImpl();
				page.setRows(5);
				page.setRecordCounts(service.queryAllCounts(content));
				ls = service.queryAllData(page, content,column,order);
				map.put("pageCounts", page.getPageCounts());
			}
			if(type.equals("Specification")){
				DevelopmentSpecificationService service = new DevelopmentSpecificationServiceImpl();
				page.setRecordCounts(service.queryAllCounts(content));
				ls = service.queryAllData(page, content,column,order);
				map.put("pageCounts", page.getPageCounts());
			}
		}
		
	
		map.put("Type", type);
		map.put("content", content);
		map.put("datas", ls);
	
		response.getWriter().write(new Gson().toJson(map));
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("ID"));
		DevelopmentSpecificationService service = new DevelopmentSpecificationServiceImpl();

		response.getWriter().write(new Gson().toJson(service.delete(id)));
		
	}

}
