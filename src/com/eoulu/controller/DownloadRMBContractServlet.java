package com.eoulu.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.syn.ExportContractPDF;
import com.eoulu.util.ConventerToPDFUtil;
import com.eoulu.util.Java2Word;
@WebServlet("/DownloadRMBContract")
public class DownloadRMBContractServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public DownloadRMBContractServlet(){
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		ExportContractPDF pdf = new ExportContractPDF();
		String loadUrl = pdf.exportContractRMB(request);
		URLDecoder.decode(loadUrl, "UTF-8");
		LogInfoService log = new LogInfoServiceImpl();
		String JspInfo = "报价系统";
		String description2 = "导出-EOULU苏州人民币合同";
		log.insert(request, JspInfo, description2);
		resp.getWriter().write(loadUrl);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
