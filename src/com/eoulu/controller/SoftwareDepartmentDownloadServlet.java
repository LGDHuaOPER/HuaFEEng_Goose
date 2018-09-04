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

import com.eoulu.service.InstallationImageService;
import com.eoulu.service.SoftwareDepartmentService;
import com.eoulu.service.impl.InstallationImageServiceImpl;
import com.eoulu.service.impl.SoftwareDepartmentServiceImpl;
import com.eoulu.syn.PreviewSoftwarePDF;
import com.eoulu.util.DownloadUrl;
import com.eoulu.util.ReadZipUtil;
import com.google.gson.Gson;
@WebServlet("/SoftwareDepartmentDownload")
public class SoftwareDepartmentDownloadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public SoftwareDepartmentDownloadServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SoftwareDepartmentService service = new SoftwareDepartmentServiceImpl();
		int id = Integer.parseInt(req.getParameter("ID"));
		List<Map<String,Object>> ls = service.getSoftwareDepartmentByID(id);
		String name = ls.get(1).get("FileName").toString();
		String path = ls.get(1).get("FilePath").toString();
		String downloadPath = new DownloadUrl().getRootUrl()+path.substring(path.lastIndexOf("\\")+1);//相对路径
		String Preview = req.getParameter("Preview")==null?"":req.getParameter("Preview");
		if(Preview.equals("Preview")){
			PreviewSoftwarePDF pdf = new PreviewSoftwarePDF();
			downloadPath = pdf.exportSoftware(path,downloadPath);
		}
		System.out.println(downloadPath);
		resp.getWriter().write(downloadPath);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	

}
