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
import com.eoulu.entity.Laboratory;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.LabService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.LabServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class LabServlet
 */
@WebServlet("/Lab")
public class LabServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LabServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loadType = request.getParameter("LoadType") == null?"":request.getParameter("LoadType");
		if(loadType.equals("data")){
			String currentPage = request.getParameter("CurrentPage");
			String Laboratory = request.getParameter("Laboratory")==null?"苏州":request.getParameter("Laboratory");
			LabService service = new LabServiceImpl();
			Page page = new Page();
			page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
			page.setRows(10);
			page.setRecordCounts(service.getCounts(Laboratory));
			List<Map<String, Object>> list = service.getDataByPage(Laboratory,page);
			Map<String, Object> map = new HashMap<>();
			map.put("data", list);
			map.put("pageCount", page.getPageCounts());
			map.put("currentPage", currentPage);
			response.getWriter().write(new Gson().toJson(map));
		}else{
			new AccessStatistics().operateAccess(request, "设备清单");
			request.getRequestDispatcher("WEB-INF//Lab.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Type") == null?"":request.getParameter("Type");
		int ID = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
		String Model = request.getParameter("Model");
		String Description = request.getParameter("Description");
		String Number = request.getParameter("Number") == null?"":request.getParameter("Number");
		String Laboratory = request.getParameter("Laboratory") == null?"":request.getParameter("Laboratory");
		String Picture = request.getParameter("Picture")== null?"":request.getParameter("Picture");
		String Document = request.getParameter("Document");
		
		Laboratory lab = new Laboratory();
		lab.setID(ID);
		lab.setModel(Model);
		lab.setDescription(Description);
		lab.setLaboratory(Laboratory);
		lab.setNumber(Number);
		lab.setPicture(Picture);
		lab.setDocument(Document);
		
		LabService service = new LabServiceImpl();
		boolean result = false;
		switch (type) {
		case "add":
			result = service.insert(lab);
			if(result){
				LogInfoService log = new LogInfoServiceImpl();
				String JspInfo = "实验室";
				String description = "新增-"+Model;
				log.insert(request, JspInfo, description);
			}
			break;

		case "update":
			result = service.update(lab);
			if(result){
				LogInfoService log = new LogInfoServiceImpl();
				String JspInfo = "实验室";
				String description = "修改-"+Model;
				log.insert(request, JspInfo, description);
			}
			break;
		}
		response.getWriter().write(new Gson().toJson(result));
	}

}
