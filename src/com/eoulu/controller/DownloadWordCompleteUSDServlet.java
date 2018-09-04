package com.eoulu.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.syn.ExportQuotePDF;
import com.eoulu.util.ConventerToPDFUtil;
import com.eoulu.util.Java2Word;
@WebServlet("/DownloadWordCompleteUSD")
public class DownloadWordCompleteUSDServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public DownloadWordCompleteUSDServlet(){
		super();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		ExportQuotePDF pdf = new ExportQuotePDF();
		String loadUrl = pdf.exportCompleteUSD(request);
		URLDecoder.decode(loadUrl, "UTF-8");
		LogInfoService log = new LogInfoServiceImpl();
		String JspInfo = "报价系统";
		String description2 = "导出-USD整机报价单";
		log.insert(request, JspInfo, description2);
		resp.getWriter().write(loadUrl);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	

}
