package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.SoftwareProjectService;
import com.eoulu.service.impl.SoftwareProjectServiceImpl;
import com.google.gson.Gson;

@WebServlet("/ProjectDocumentUpload")
public class ProjectDocumentUploadServlet extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SoftwareProjectService service = new SoftwareProjectServiceImpl();

		resp.getWriter().write(new Gson().toJson(service.upload(req)));	
	}

	private static final long serialVersionUID = 1L;

}