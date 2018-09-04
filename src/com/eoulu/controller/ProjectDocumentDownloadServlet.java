package com.eoulu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.SoftwareProjectService;
import com.eoulu.service.impl.SoftwareProjectServiceImpl;

@WebServlet("/ProjectDocumentDownload")
public class ProjectDocumentDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SoftwareProjectService service = new SoftwareProjectServiceImpl();
		String path = service.getPath(req);
		String fileName = path.split("_")[path.split("_").length - 1];
		System.out.println(fileName);
		File file = new File(path);
		if(!file.exists()){
			resp.getWriter().write("文件已被删除！");
		}else{
			resp.setContentType("application/x-msdownload");  
			fileName = new String(fileName.getBytes(), "ISO8859-1");
			resp.setHeader("content-disposition", "attachment;filename=" + fileName);
		    FileInputStream in = new FileInputStream(path);
		    OutputStream out = resp.getOutputStream();
	        byte buffer[] = new byte[1024];
	        int len = 0;
	        while((len=in.read(buffer))>0){
	            out.write(buffer, 0, len);
	        }
	        in.close();
	        out.close();
		}
		
	

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
	
	

}
