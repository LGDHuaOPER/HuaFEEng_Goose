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
import com.eoulu.entity.BiddingDocument;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.BiddingDocumentService;
import com.eoulu.service.impl.BiddingDocumentServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class BiddingDocumentServlet
 */
@WebServlet("/BiddingDocument")
public class BiddingDocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BiddingDocumentServlet() {
        super();
        // TODO Auto-generated constructor stub
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
			BiddingDocumentService service = new BiddingDocumentServiceImpl();
			Page page = new Page();
			page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
			page.setRows(10);
			page.setRecordCounts(service.getCounts(column, content));
			List<Map<String, Object>> list = service.getDataByPage(page, column, content);
			Map<String, Object> map = new HashMap<>();
			map.put("data", list);
			map.put("pageCount", page.getPageCounts());
			map.put("currentPage", currentPage);
			response.getWriter().write(new Gson().toJson(map));
		}else{
			new AccessStatistics().operateAccess(request, "招标文件");
			request.getRequestDispatcher("WEB-INF//BiddingDocument.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = request.getParameter("ID")==null?0:Integer.parseInt(request.getParameter("ID"));
		String Year = request.getParameter("Year")==null?"":request.getParameter("Year");
		String Submitter = request.getParameter("Submitter")==null?"":request.getParameter("Submitter").trim();
		String Score = request.getParameter("Score")==null?"":request.getParameter("Score");
		BiddingDocument document = new BiddingDocument();
		document.setID(ID);
		document.setSubmitter(Submitter);
		document.setScore(Score);
		document.setYear(Year);
		BiddingDocumentService service = new BiddingDocumentServiceImpl();
		response.getWriter().write(new Gson().toJson(service.update(document)));
	}

}
