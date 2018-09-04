package com.eoulu.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.eoulu.service.CustomerInquiryService;
import com.eoulu.service.impl.CustomerInquiryServiceImpl;
import com.eoulu.util.ReadZipUtil;

/**
 * Servlet implementation class CustomerInquiryOperateServlet
 */
@WebServlet("/CustomerInquiryOperate")
public class CustomerInquiryOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerInquiryOperateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CustomerInquiryService service = new CustomerInquiryServiceImpl();
		response.getWriter().write(service.operate(request));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReadZipUtil util = new ReadZipUtil();
		String path01 = "D:\\tempZipFile";
		File filePath = new File(path01);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		Properties pro = new Properties();
		pro.load(ReadZipUtil.class.getResourceAsStream("download.properties"));
		String tempPath = pro.getProperty("FileUrl");
		try {
			Map<String, String>  map = util.getForm(filePath, request, tempPath);
			String fileName = map.get("fileName");
			response.getWriter().write((map.get("filePath")==null||tempPath==null)?"":fileName);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	
	}

}
