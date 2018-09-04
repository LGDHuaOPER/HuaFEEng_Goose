package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.OriginalDocumentService;
import com.eoulu.service.impl.OriginalDocumentServiceImpl;
import com.google.gson.Gson;

@WebServlet("/OriginalDocumentBatchUpload")
public class OriginalDocumentBatchUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public OriginalDocumentBatchUploadServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OriginalDocumentService service = new OriginalDocumentServiceImpl();

		resp.getWriter().write(new Gson().toJson(service.moreOriginalDocumentAdd(req)));
	}

}
