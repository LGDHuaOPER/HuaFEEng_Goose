package com.eoulu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.DevelopmentSpecificationService;
import com.eoulu.service.SoftwareDepartmentService;
import com.eoulu.service.impl.DevelopmentSpecificationServiceImpl;
import com.eoulu.service.impl.SoftwareDepartmentServiceImpl;
import com.eoulu.syn.PreviewSoftwarePDF;
import com.eoulu.util.DownloadUrl;

/**
 * Servlet implementation class SoftwareSpecificationDownloadServlet
 */
@WebServlet(description = "软件规范下载", urlPatterns = { "/SoftwareSpecificationDownload" })
public class SoftwareSpecificationDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SoftwareSpecificationDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DevelopmentSpecificationService service = new DevelopmentSpecificationServiceImpl();
		int id = Integer.parseInt(request.getParameter("ID"));
		String path = service.getDevelopmentSpecificationByID(id);
		String downloadPath =new DownloadUrl().getRootUrl()+path.substring(path.lastIndexOf("\\")+1);//相对路径
		String Preview = request.getParameter("Preview")==null?"":request.getParameter("Preview");
		if(Preview.equals("Preview")){
			PreviewSoftwarePDF pdf = new PreviewSoftwarePDF();
			downloadPath = pdf.exportSoftware(path,path);
		}
		response.getWriter().write(downloadPath);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
