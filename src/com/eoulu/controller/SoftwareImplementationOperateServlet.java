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

import com.eoulu.service.SoftwareImplementationService;
import com.eoulu.service.impl.SoftwareImplementationServiceImpl;
import com.eoulu.util.ReadZipUtil;
import com.google.gson.Gson;

/**
 * Servlet implementation class SoftwareImplementationOperateServlet
 */
@WebServlet("/SoftwareImplementationOperate")
public class SoftwareImplementationOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SoftwareImplementationOperateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SoftwareImplementationService service = new SoftwareImplementationServiceImpl();
		String classify = request.getParameter("classify")==null?"":request.getParameter("classify");
		switch (classify) {
		case "Modify":
			response.getWriter().write(new Gson().toJson(service.getOperatePreview(request)));
			break;

		default:
			response.getWriter().write(new Gson().toJson(service.getPreview(request)));
			break;
		}
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
