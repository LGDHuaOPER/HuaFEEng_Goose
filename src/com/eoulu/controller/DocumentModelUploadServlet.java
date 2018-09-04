package com.eoulu.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.eoulu.util.DocumentModelUtil;
import com.google.gson.Gson;

@WebServlet("/DocumentModelUpload")
public class DocumentModelUploadServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	private String filePath = "c:\\modelFiles"; // 文件存放目录
	private String tempPath = "c:\\modelFiles\\file\\"; // 临时文件目录

	public DocumentModelUploadServlet() {
		super();
	}
	  protected void doGet(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {


	    }
	
	  
	  protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
		  DocumentModelUtil util = new DocumentModelUtil();
	        // 如果路径不存在，则创建路径
	        File pathFile = new File(filePath);
	        File pathTemp = new File(tempPath);
	        if (!pathFile.exists()) {
	            pathFile.mkdirs();
	        }
	        if (!pathTemp.exists()) {
	            pathTemp.mkdirs();
	        }
	       
	        boolean success = false;
			try {
				success = util.getForm(pathTemp, request, tempPath);
			} catch (FileUploadException e) {
				
				e.printStackTrace();
			}
			
			if(success){
				response.getWriter().write(new Gson().toJson("{\"message\":\"true\"}"));
			}else{
				response.getWriter().write(new Gson().toJson("{\"message\":\"false\"}"));
			}

	    }

}
