package com.eoulu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.eoulu.entity.SizeInfo;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.LogisticService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.LogisticServiceImpl;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.ReadExcelContentUtil;
import com.eoulu.util.SendEmailToBusinessUtil;
import com.eoulu.util.SendMailUtil;
import com.google.gson.Gson;
@WebServlet("/SendToBusiness")
public class SendToBusinessServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public SendToBusinessServlet() {
		super();
	
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		LogisticService service = new LogisticServiceImpl();
		String uploadFilePath="c:\\temp\\";
	    File uploadFile=new File(uploadFilePath);
	    if (!uploadFile.exists()) {
	        uploadFile.mkdirs();
	    }
	    String tempFilePath = "c:\\temp\\file\\";
	    File tempPathFile = new File(tempFilePath);
        if (!tempPathFile.exists()) {
            tempPathFile.mkdirs();
        }
		SendEmailToBusinessUtil util = new SendEmailToBusinessUtil();
		String[] fileList = new String[1];
		boolean flag = false;
		String content = "";
		String ContractTitle = "";
		String ContractNo = "";
		Map<String, String> map;
		try {
			map = util.getForm(tempPathFile, request, uploadFilePath);
			Set<String> keys = map.keySet();
			for(String key : keys){
				if(key.equals("content")){
					content  = map.get(key);
				}
				if(key.equals("fileName")){
					fileList[0]  = map.get(key);
				}
				if(key.equals("ContractTitle")){
					ContractTitle = map.get(key);
					
				}
				if(key.equals("ContractNo")){
					ContractNo = map.get(key);
				}
				
			}
			content  = map.get("content");
			fileList[0] = map.get("fileName");
			ContractTitle = map.get("ContractTitle");
			ContractNo = map.get("ContractNo");
			
			List<SizeInfo> info = new ReadExcelContentUtil().readExcelContent(map.get("fileName"), ContractNo);
			flag = service.addSizeInfo(info);
			
		} catch (FileUploadException e) {
			
			e.printStackTrace();
		}
		MethodUtil mu =  new MethodUtil();
		String input = "<span style='font-family:微软雅黑,SimSun;font-size:14px;'>附件内容是</span>"+
		ContractTitle+","+ContractNo+"<span style='font-family:微软雅黑,SimSun;font-size:14px;'>的订单尺寸信息，请阅读！</span><br><br>";
		
		content = mu.getEmailSign(input, null);
		String subject = "Eoulu:"+ContractNo+"订单货物尺寸信息";
//		String subject = "Eoulu:后台测试";
		boolean success = util.doSendHtmlEmail( subject, content, fileList);
		if(success && flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "物流页面";
			String description = "邮件-"+ContractNo+"订单货物尺寸信息";
			log.insert(request, JspInfo, description);
			response.getWriter().write(new Gson().toJson("{\"message\":\"true\"}"));
		}else{
			response.getWriter().write(new Gson().toJson("{\"message\":\"false\"}"));
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
