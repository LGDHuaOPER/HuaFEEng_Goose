package com.eoulu.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.InstallationManualService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.InstallationManualServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.util.DownloadUrl;
@WebServlet("/ManualDownload")
public class ManualDownloadServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;
	public ManualDownloadServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("application/x-download");
		
		InstallationManualService service = new InstallationManualServiceImpl();
		int id = Integer.parseInt(req.getParameter("ID"));
		System.out.println(id);
		List<Map<String,Object>> ls = service.getInstallationManualByID(id);
		String name = ls.get(1).get("FileName").toString();
		String path = ls.get(1).get("FilePath").toString();
		String downloadPath = new DownloadUrl().getRootUrl()+path.substring(path.lastIndexOf("\\")+1);//相对路径
		URLDecoder.decode(downloadPath, "UTF-8");

		/*
		resp.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(name, "UTF-8"));
		 ServletOutputStream out = resp.getOutputStream();
	        FileInputStream in = new FileInputStream(zip);
	        try {
	            int len;
	            byte[] buffer = new byte[1024];
	            buffer = Base64.encodeBase64(buffer);
	            while ((len = in.read(buffer)) != -1) {
	            	buffer = Base64.encodeBase64(buffer);
	                out.write(buffer, 0, len);
	                
	            }
	            
	            out.flush();
	        } finally{
	            try {
	                if (in != null) {
	                    in.close();
	                    in = null;
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        
	        
        */
		LogInfoService log = new LogInfoServiceImpl();
		String JspInfo = "文档上传";
		String description = "下载-装机手册";
		log.insert(req, JspInfo, description);
		resp.getWriter().write(downloadPath);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	
	

}
