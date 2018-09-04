package com.eoulu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.InstallationManualService;
import com.eoulu.service.impl.InstallationManualServiceImpl;

/**
 * Servlet implementation class InstallationPackageDownloadServlet
 */
@WebServlet("/InstallationPackageDownload")
public class InstallationPackageDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InstallationPackageDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		InstallationManualService service = new InstallationManualServiceImpl();
		int id = Integer.parseInt(request.getParameter("ID"));
		System.out.println(id);
		List<Map<String,Object>> ls = service.getInstallationManualByID(id);
		String fileName = ls.get(1).get("FileName").toString();
		String path = ls.get(1).get("FilePath").toString();
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
