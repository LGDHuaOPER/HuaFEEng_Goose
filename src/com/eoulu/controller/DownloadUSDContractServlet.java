package com.eoulu.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LogInfoService;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.eoulu.syn.ExportContractPDF;
import com.eoulu.util.ConventerToPDFUtil;
import com.eoulu.util.Java2Word;
@WebServlet("/DownloadUSDContract")
public class DownloadUSDContractServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public DownloadUSDContractServlet(){
		super();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		ExportContractPDF pdf = new ExportContractPDF();
		String loadUrl = pdf.exportContractUSD(request);
		URLDecoder.decode(loadUrl, "UTF-8");
		LogInfoService log = new LogInfoServiceImpl();
		String JspInfo = "报价系统";
		String description2 = "导出-EOULU美金合同";
		log.insert(request, JspInfo, description2);
		resp.getWriter().write(loadUrl);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	
	}
	

}
