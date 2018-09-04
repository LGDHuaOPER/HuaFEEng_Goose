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

@WebServlet("/ManualUpload")
public class ManualUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ManualUploadServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// InstallationManualService service = new
		// InstallationManualServiceImpl();
		// System.out.println(111111);
		// resp.getWriter().write(new
		// Gson().toJson(service.installationManualAdd(req)));

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		InstallationManualService service = new InstallationManualServiceImpl();
		boolean flag = service.installationManualAdd(req);
		if (flag) {
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "文档上传";
			String description = "上传-装机手册";
			log.insert(req, JspInfo, description);
		}
		System.out.println("批量上传");
		resp.getWriter().write(new Gson().toJson(flag));
	}

}
