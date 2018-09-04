package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.ReimburseService;
import com.eoulu.service.impl.ReimburseServiceImpl;
import com.eoulu.util.DownloadUrl;

/**
 * Servlet implementation class ReimburseAttachmentServlet
 */
@WebServlet("/ReimburseAttachment")
public class ReimburseAttachmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReimburseAttachmentServlet() {
        super();
     
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Type");
		switch (type) {
		case "export":
			ReimburseService service = new ReimburseServiceImpl();
			String ids = request.getParameter("ids")==null?"":request.getParameter("ids");
			String year = request.getParameter("Year")==null?"":request.getParameter("Year");
			String month = request.getParameter("Month")==null?"":request.getParameter("Month");
			response.getWriter().write(service.exportFile(ids,year,month));
			break;

		case "download":
			String ID = request.getParameter("ID");
			String name = request.getParameter("StaffName");
			String folderMonth = request.getParameter("FolderMonth");
			String filename = request.getParameter("FileName");
			
			String rootUrl = new DownloadUrl().getRootUrl();
			String filePath = rootUrl+"报销申请"+folderMonth+"-"+name+"-"+ID+"\\"+filename;
			response.getWriter().write(filePath);
			break;
		}

	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReimburseService service = new ReimburseServiceImpl();
		response.getWriter().write(service.batchUpload(request));
	}

}
