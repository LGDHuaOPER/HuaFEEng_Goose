package com.eoulu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.util.DocumentUploadUtilY;
import com.google.gson.Gson;

/**
 * Servlet implementation class BatchuploadServlet
 */

//全局批量上传，新建文件夹
@WebServlet("/BatchUpload")
public class BatchUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BatchUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		DocumentUploadUtilY util = new DocumentUploadUtilY();		
		Map<String, Object> map = util.folderBatchUpload(request, "E:\\LogisticsFile\\File\\");
		@SuppressWarnings("unchecked")
		List<Map<String, String>> fileInfo = (List<Map<String, String>>) map.get("FileInfo");
		
		response.getWriter().write(new Gson().toJson(fileInfo));
		
	}

}
