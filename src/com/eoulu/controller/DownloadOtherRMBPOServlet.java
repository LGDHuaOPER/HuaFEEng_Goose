package com.eoulu.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.dao.QuoteSystemDao;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.eoulu.syn.ExportPOPDF;
import com.eoulu.util.ConventerToPDFUtil;
import com.eoulu.util.Java2Word;

@WebServlet("/DownloadOtherRMBPO")
public class DownloadOtherRMBPOServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DownloadOtherRMBPOServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ExportPOPDF pdf= new ExportPOPDF();
		String loadUrl = pdf.exportOtherRMBPO(req);
		resp.getWriter().write(loadUrl);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
