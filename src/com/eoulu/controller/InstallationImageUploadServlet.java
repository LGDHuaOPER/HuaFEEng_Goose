package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.InstallationImageService;
import com.eoulu.service.impl.InstallationImageServiceImpl;
import com.google.gson.Gson;
@WebServlet("/InstallationImageUpload")
public class InstallationImageUploadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public InstallationImageUploadServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		InstallationImageService service = new InstallationImageServiceImpl();
		
		resp.getWriter().write(new Gson().toJson(service.installationImageAdd(req)));
	}

}
