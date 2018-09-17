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

import com.eoulu.service.LabService;
import com.eoulu.service.impl.LabServiceImpl;
import com.eoulu.util.DocumentUploadUtilY;

/**
 * Servlet implementation class UploadLabPictureServlet
 */
@WebServlet("/UploadLabPicture")
public class UploadLabPictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadLabPictureServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int LabID = request.getParameter("LabID")==null?0:Integer.parseInt(request.getParameter("LabID"));
		String Model = request.getParameter("Model");
		String path = request.getServletContext().getRealPath("/") + "down\\实验室配置.pdf";
		LabService service = new LabServiceImpl();
		service.exportConfig(LabID, Model, path);
		File file = new File(path);
		if(!file.exists()){
			response.getWriter().write("导出配置失败！");
		}else{
			response.setContentType("application/x-msdownload");  
			//fileName = new String(fileName.getBytes(), "ISO-8859-1");
			String fileName = "实验室配置.pdf";
			response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
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
		DocumentUploadUtilY util = new DocumentUploadUtilY();
		response.getWriter().write(util.uploadNotRename(request, "E:\\LogisticsFile\\File\\Lab").get("Response"));
	}

}
