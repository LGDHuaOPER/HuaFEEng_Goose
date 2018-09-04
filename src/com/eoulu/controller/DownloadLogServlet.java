package com.eoulu.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.util.ExportLogUtil;
@WebServlet("/DownloadLog")
public class DownloadLogServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public DownloadLogServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ExportLogUtil util = new ExportLogUtil();
		LogInfoService service = new LogInfoServiceImpl();
		String path = req.getServletContext().getRealPath("/") + "down\\"  + "操作日志.xlsx";
		//String path = "";
		/*
		try {
			path = DownloadLogServlet.class.getResource("/").toURI().getPath();
			System.out.println(path.split("Logistics")[1]);
			path=path.replaceAll(path.split("Logistics")[1], "/down/操作日志.xlsx");
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		*/
		
//		try {
//		String 	a = DownloadLogServlet.class.getResource("/").toURI().getPath();
//		//C:\Program Files\Apache Software Foundation\Tomcat 7.0\webapps\Logistics\down\操作日志.xlsx
//		System.out.println(a);
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
		List<Map<String,Object>> ls = null;
		String[] idList = req.getParameterValues("idList[]");
		if(idList !=null){
			ls = service.getDataByID(idList);
		}else{
			ls = service.getAllData();
		}
		util.buidExcel(ls, path);
		String loadUrl = "down\\"  + "操作日志.xlsx";
		URLDecoder.decode(loadUrl,"URF-8");
		System.out.println(loadUrl);
		resp.getWriter().write(loadUrl);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
