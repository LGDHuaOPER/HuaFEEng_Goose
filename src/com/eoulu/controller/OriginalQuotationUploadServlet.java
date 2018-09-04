package com.eoulu.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.eoulu.service.OriginalQuotationService;
import com.eoulu.service.impl.OriginalQuotationServiceImpl;
import com.eoulu.util.MethodUtil;


/**
 * Servlet implementation class OriginalQuotationUploadServlet
 */
@WebServlet("/OriginalQuotationUpload")
public class OriginalQuotationUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OriginalQuotationUploadServlet() {
        super();
   
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MethodUtil util = new MethodUtil();
		OriginalQuotationService service = new OriginalQuotationServiceImpl();
		String savePath = "E:\\LogisticsFile\\File\\"; 
		File tempPath = new File("D:\\tempZipFile");
		String fileName = "";
		try {
			fileName = util.getForm(tempPath, request, savePath).get(0).get("fileName");
			String quoteTotal = "";
			try{
				quoteTotal = service.getQuoteTotal(fileName);
				response.getWriter().write(quoteTotal);
			}catch(Exception e){
				e.printStackTrace();
			
				response.getWriter().write("读取报价失败，请手输");
			}
			
		} catch (FileUploadException e) {
			
			e.printStackTrace();
			response.getWriter().write("上传失败");
		}	
		
	}

}
