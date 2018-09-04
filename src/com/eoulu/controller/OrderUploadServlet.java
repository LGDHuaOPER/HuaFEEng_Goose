package com.eoulu.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.eoulu.service.OrderService;
import com.eoulu.service.impl.OrderServiceImpl;

/**
 * Servlet implementation class OrderUploadServlet
 */
@WebServlet("/OrderUpload")
public class OrderUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderUploadServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		OrderServiceImpl service = new OrderServiceImpl();
		String path = "E:\\LogisticsFile\\File\\";
		String path01 = "C:\\tempZipFile";
		File file01 = new File(path01);
		if(!file01.exists()){
			file01.mkdirs();
		}
		System.out.println("上传了么");
		try {
			Map<String, String> map = service.getForm(file01, request, path);
			String classify = map.get("classify");
			String fileName = map.get("fileName");
			request.getSession().setAttribute(classify, fileName);
			System.out.println(fileName);
			response.getWriter().write(fileName);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	
	}

}
