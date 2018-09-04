package com.eoulu.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.InstallationReportLog;
import com.eoulu.service.InstallationManualService;
import com.eoulu.service.InstallationReportLogService;
import com.eoulu.service.OriginalDocumentService;
import com.eoulu.service.impl.InstallationManualServiceImpl;
import com.eoulu.service.impl.InstallationReportLogServiceImpl;
import com.eoulu.service.impl.OriginalDocumentServiceImpl;
import com.eoulu.util.DownloadUrl;
import com.eoulu.util.ReadZipUtil;
@WebServlet("/OriginalDocumentDownload")
public class OriginalDocumentDownloadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public OriginalDocumentDownloadServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OriginalDocumentService service = new OriginalDocumentServiceImpl();
		int id = Integer.parseInt(req.getParameter("ID"));
		System.out.println(id);
		List<Map<String,Object>> ls = service.getOriginalDocumentByID(id);
		System.out.println(ls);
		String name = ls.get(1).get("FileName").toString();
		String path = ls.get(1).get("FilePath").toString();
		String downloadPath = new DownloadUrl().getRootUrl()+path.substring(path.lastIndexOf("\\")+1);//相对路径
		System.out.println(downloadPath);
		resp.getWriter().write(downloadPath);
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	

}
