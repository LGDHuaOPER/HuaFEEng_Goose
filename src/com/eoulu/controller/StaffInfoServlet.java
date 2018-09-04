package com.eoulu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.StaffInfoService;
import com.eoulu.service.impl.StaffInfoServiceImpl;

/**
 * Servlet implementation class StaffInfoServlet
 */
@WebServlet(description = "员工信息", urlPatterns = { "/StaffInfo" })
public class StaffInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StaffInfoServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StaffInfoService service = new StaffInfoServiceImpl();
		int currentPage = Integer.parseInt(request.getParameter("currentPage")==null?"1":request.getParameter("currentPage"));
		Page page = new Page();
		page.setRows(10);
		page.setCurrentPage(currentPage);
		String field1 = request.getParameter("field1")==null?"":request.getParameter("field1");
		String content1 = request.getParameter("content1")==null?"":request.getParameter("content1");
		String field2 = request.getParameter("field2")==null?"":request.getParameter("field2");
		String content2 = request.getParameter("content2")==null?"":request.getParameter("content2");
		
		
		String queryType = request.getParameter("queryType")==null?"":request.getParameter("queryType");
		if(!queryType.equals("")){
			if(queryType.equals("singleQuery")){
				page.setRecordCounts(service.getSingleQueryCount(request));
				request.setAttribute("datas",service.singleQuery(request, page));
				request.setAttribute("queryType",queryType);
			}else if (queryType.equals("maxQuery")){
				page.setRecordCounts(service.getMaxQueryCount(request));
				request.setAttribute("datas",service.maxQuery(request, page));	
			}
		}else{
			new AccessStatistics().operateAccess(request, "员工信息");
				page.setRecordCounts(service.getAllCounts());
				request.setAttribute("datas", service.getAllData(page));
		}
			
	
	
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("field1",field1);
		request.setAttribute("field2",field2);
		request.setAttribute("content1",content1);
		request.setAttribute("content2",content2);
		request.setAttribute("queryType",queryType);
		request.getRequestDispatcher("WEB-INF\\StaffInfo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fileName = request.getParameter("FileName");
	
		String path = "E:\\LogisticsFile\\File\\Photo\\" +fileName;
		File file = new File(path);
		if(!file.exists()){
			response.getWriter().write("文件已被删除！");
		}else{
			response.setContentType("application/x-msdownload");  
			fileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setHeader("content-disposition", "attachment;filename=" + fileName);
		    FileInputStream in = new FileInputStream(path);
		    OutputStream out = response.getOutputStream();
	        byte buffer[] = new byte[1024];
	        int len = 0;
	        while((len=in.read(buffer))>0){
	            out.write(buffer, 0, len);
	        }
	        in.close();
	        out.close();
		}
	}

}
