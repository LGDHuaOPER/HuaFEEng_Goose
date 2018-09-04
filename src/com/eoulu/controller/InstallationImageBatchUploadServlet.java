package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.InstallationImageService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.InstallationImageServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.google.gson.Gson;
@WebServlet("/InstallationImageBatchUpload")
public class InstallationImageBatchUploadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public InstallationImageBatchUploadServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
InstallationImageService service = new InstallationImageServiceImpl();
		boolean flag = service.moreImageAdd(req);
		if(flag){
			
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "文档上传";
			String description = "批量上传-装机图片";
			log.insert(req, JspInfo, description);
		}
		resp.getWriter().write(new Gson().toJson(flag));
	}

}
