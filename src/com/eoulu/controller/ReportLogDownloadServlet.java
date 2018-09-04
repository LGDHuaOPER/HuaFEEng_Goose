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

import com.eoulu.service.InstallationReportLogService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.InstallationReportLogServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.util.DownloadUrl;
import com.eoulu.util.ReadZipUtil;
@WebServlet("/ReportLogDownload")
public class ReportLogDownloadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public ReportLogDownloadServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InstallationReportLogService service = new InstallationReportLogServiceImpl();
		int id = Integer.parseInt(req.getParameter("ID"));
		List<Map<String,Object>> ls = service.getInstallationReportLogByID(id);
		String name = ls.get(1).get("FileName").toString();
		String path = ls.get(1).get("FilePath").toString();
		String downloadPath = new DownloadUrl().getRootUrl()+path.substring(path.lastIndexOf("\\")+1);//相对路径
		LogInfoService logs = new LogInfoServiceImpl();
		String JspInfo = "文档上传";
		String description = "下载-装机报告和装机日志";
		logs.insert(req, JspInfo, description);
		resp.getWriter().write(downloadPath);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	

}
