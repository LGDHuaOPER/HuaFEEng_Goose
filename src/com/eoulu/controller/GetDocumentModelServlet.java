package com.eoulu.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.eoulu.service.DocumentModelService;
import com.eoulu.service.impl.DocumentModelServiceImpl;
import com.eoulu.service.impl.EquipmentServiceImpl;
import com.eoulu.util.DocExcelToHtmlUtil;
import com.eoulu.util.DocumentModelUtil;
import com.google.gson.Gson;

@WebServlet("/GetDocumentModel")
public class GetDocumentModelServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public GetDocumentModelServlet() {
		super();
	}
	 protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("utf-8");
		 DocumentModelService service = new DocumentModelServiceImpl();
		 DocumentModelUtil util = new DocumentModelUtil();
		 DocExcelToHtmlUtil dt = new DocExcelToHtmlUtil();
		 String name = request.getParameter("name");//new String(request.getParameter("name").getBytes("ISO-8859-1"),"utf-8");
		 System.out.println(name);
		 String filePath=service.getModelByName(name);
		 System.out.println("shujuku:"+filePath);
		 if(filePath != null){

			 String content = "";
			 String htmlPath = "";

			 if(filePath.endsWith(".doc")){
				 htmlPath = filePath.replaceFirst(".doc", ".html");
			 }
			 if(filePath.endsWith(".docx")){
				 htmlPath = filePath.replaceFirst(".docx", ".html");
				 }
			 System.out.println("html路径："+htmlPath);
			 try {
				content = dt.getWordAndStyle(filePath, htmlPath);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			 if(content.equals("") || content != null){
				 System.out.println("content:"+content);
				 response.getWriter().write(new Gson().toJson(content));
			 }
			 
		 }
		
	 }
}
