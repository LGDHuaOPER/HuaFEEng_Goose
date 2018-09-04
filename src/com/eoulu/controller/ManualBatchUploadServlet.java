package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.InstallationManualService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.InstallationManualServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.google.gson.Gson;
@WebServlet("/ManualBatchUpload")
public class ManualBatchUploadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public ManualBatchUploadServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InstallationManualService service = new InstallationManualServiceImpl();
		boolean flag = service.moreManualAdd(req);
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "文档上传";
			String description = "装机手册-批量上传";
			log.insert(req, JspInfo, description);
		}
		resp.getWriter().write(new Gson().toJson(flag));
//		req.setAttribute("Area", "south");
//		req.setAttribute("catalog", "Manual");
//		req.setAttribute("Year", "2014");
//		service.moreManualAdd(req);
//		req.getRequestDispatcher("DocumentUpload").forward(req, resp);
		
	}
	
	

}
