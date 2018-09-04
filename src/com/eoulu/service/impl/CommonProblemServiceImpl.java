package com.eoulu.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;

import com.eoulu.commonality.Page;
import com.eoulu.dao.CommonProblemDao;
import com.eoulu.dao.SoftwareDepartmentDao;
import com.eoulu.entity.DocumentUpload;
import com.eoulu.service.CommonProblemService;
import com.eoulu.util.DocumentUploadUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.ReadImportDocUtil;
import com.eoulu.util.ReadZipUtil;

public class CommonProblemServiceImpl implements CommonProblemService {

	@Override
	public List<Map<String, Object>> getAllData(Page page, String column, String order) {
		return new CommonProblemDao().getAllData(page, column, order);
	}

	@Override
	public int getAllCounts() {
		return new CommonProblemDao().getAllCounts();
	}

	@Override
	public String insert(HttpServletRequest request) {
		String path01 = "D:\\tempZipFile";
		File filePath = new File(path01);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		String tempPath = "E:\\LogisticsFile\\File\\";
		ReadZipUtil util = new ReadZipUtil();
		Map<String, String> map = new HashMap<>();
		try {
			map = util.getForm(filePath, request, tempPath);
		} catch (FileUploadException | IOException e) {

			e.printStackTrace();
		}
		String name = map.get("fileName");
		String path = map.get("filePath");
		String isExist = map.get("isExist");
		String DocuType = map.get("DocuType");
		ReadImportDocUtil read = new ReadImportDocUtil();
		String content = "";
		if (path.endsWith(".doc")) {
			content = read.extractDoc(path, path.replaceAll(".doc", ".txt"));
		}
		if (path.endsWith(".docx")) {
			content = read.extractDoc(path, path.replaceAll(".docx", ".txt"));
		}
		String author = map.get("fileName").split("-")[1];
		String password = map.get("password");
		DocumentUpload doc = new DocumentUpload();
		doc.setFileName(name);
		doc.setFilePath(path);
		doc.setAuthor(author);
		doc.setContent(content);
		doc.setDocuType(DocuType);
		if(isExist.equals("exists")){
			new CommonProblemDao().delete(name);
		}
		
		SoftwareDepartmentDao dao = new SoftwareDepartmentDao();
		DocumentUploadUtil mail = new DocumentUploadUtil(request, password);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date()).replaceAll("-", ".");
		String subject = "Eoulu:部门文档-软件部文档更新";
		String contentMail = dao.getRealName(request.getSession().getAttribute("user").toString()) + "于" + date + "在软件开发常见问题库上传："
				+ name + "。请查收！";
		String[] fileList = new String[] { path };
		contentMail = new MethodUtil().getEmailSign(contentMail, null);
		String result="上传成功！";
		if(password==null||password.equals("")){
			result = "邮箱密码不为空!";
		}else{
			boolean temp = mail.doSendHtmlEmail(subject, contentMail, null);
			if(!temp){
				result = "邮件发送失败！";
			}else{
				boolean flag = new CommonProblemDao().insert(doc);
				if(!flag){
					result = "上传失败!";
				}
			}
		}
		

		return result;
	}

	@Override
	public String moreAdd(HttpServletRequest request) {

		List<String> flags = new ArrayList<>();
		boolean flag = false;
		String path01 = "D:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 7.0\\temp";
		File filePath = new File(path01);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		String tempPath = "E:\\LogisticsFile\\File\\";
		ReadZipUtil util = new ReadZipUtil();
		List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
		
		try {
			ls = util.getMoreForm(filePath, request, tempPath);
		} catch (FileUploadException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		System.out.println(ls);
		Map<String, String> map = ls.get(ls.size() - 1);
		String content = "";
		String password = map.get("password");
		String[] fileList = new String[ls.size() - 1];
		String file = "";
		boolean nameFlag = true;
		for(int i = 0; i < ls.size()-1; i++){
			Map<String, String> tempMap = ls.get(i);
			String name = tempMap.get("fileName");
			if(name.split("-").length!=3){
				nameFlag = false;
				break;
			}
			if (i != ls.size() - 2) {
				file +=  "<span style='font-family:微软雅黑;font-size:14px;'>"+name + "，</span><br>";
			} else {
				file += "<span style='font-family:微软雅黑;font-size:14px;'>"+name + "。</span><br>";
			}
		}
		
		SoftwareDepartmentDao dao = new SoftwareDepartmentDao();
		DocumentUploadUtil mail = new DocumentUploadUtil(request, password);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date()).replaceAll("-", ".");
		String subject = "Eoulu:部门文档-软件部文档更新";
		String contentMail = "<span style='font-family:微软雅黑;font-size:14px;'>"+
				dao.getRealName(request.getSession().getAttribute("user").toString()) + "于" + date + "在软件开发常见问题库中上传:</span><br>"
				+ file + "<span style='font-family:微软雅黑;font-size:14px;'>请查收！</span><br><br>";
		contentMail = new MethodUtil().getEmailSign(contentMail, null);
		String result = "上传成功！";
		if(nameFlag){
			if(password==null||password.equals("")){
				result = "邮箱密码不为空！";
			}else{
				boolean temp = mail.doSendHtmlEmail(subject, contentMail, null);
				if(!temp){
					result = "邮件发送失败！";
				}else{
					for (int i = 0; i < ls.size()-1; i++) {
						Map<String, String> tempMap = ls.get(i);
						String name = tempMap.get("fileName");
						String DocuType = tempMap.get("DocuType");
						String path = tempMap.get("filePath");
						ReadImportDocUtil read = new ReadImportDocUtil();
						
						if (path.endsWith(".doc")) {
							content = read.extractDoc(path, path.replaceAll(".doc", ".txt"));
						}
						if (path.endsWith(".docx")) {
							content = read.extractDoc(path, path.replaceAll(".docx", ".txt"));
						}
						System.out.println("批量---"+tempMap.get("fileName"));
						String author = tempMap.get("fileName").split("-")[1];
						DocumentUpload doc = new DocumentUpload();
						doc.setFileName(name);
						doc.setFilePath(path);
						doc.setAuthor(author);
						doc.setContent(content);
						doc.setDocuType(DocuType);
						boolean temp2 = new CommonProblemDao().insert(doc);

						if (temp2) {
							fileList[i] = path;
							flags.add("true");
						} else {
							flags.add("flase");
						}
						

					}
					if(flags.contains("false")){
						result = "上传失败！";
					}
				}
			}
		}else{
			result = "文件名格式有误！";
		}
		
		return result;
	}

	@Override
	public List<Map<String, Object>> queryAllData(Page page, String content, String column, String order) {

		content = "%" + content + "%";
		return new CommonProblemDao().queryAllData(page, content, column, order);
	}

	@Override
	public int queryAllCounts(String content) {
		content = "%" + content + "%";
		return new CommonProblemDao().queryAllCounts(content);
	}

	@Override
	public String getCommonProblemByID(int id) {
		return new CommonProblemDao().getCommonProblemByID(id);
	}

	@Override
	public String update(HttpServletRequest request) {

		String path01 = "D:\\tempZipFile";
		File filePath = new File(path01);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		String tempPath = "E:\\LogisticsFile\\File\\";
		ReadZipUtil util = new ReadZipUtil();
		Map<String, String> map = new HashMap<>();
		try {
			map = util.getForm(filePath, request, tempPath);
		} catch (FileUploadException | IOException e) {

			e.printStackTrace();
		}
		String ID = map.get("DocuType");
		String name = map.get("fileName");
		String path = map.get("filePath");
		String isExist = map.get("isExist");
		String DocuType = map.get("DocuType");
		ReadImportDocUtil read = new ReadImportDocUtil();
		String content = "";
		if (path.endsWith(".doc")) {
			content = read.extractDoc(path, path.replaceAll(".doc", ".txt"));
		}
		if (path.endsWith(".docx")) {
			content = read.extractDoc(path, path.replaceAll(".docx", ".txt"));
		}
		String author = map.get("fileName").split("-")[1];
		String password = map.get("password");
		DocumentUpload doc = new DocumentUpload();
		doc.setID(Integer.parseInt(ID));
		doc.setFileName(name);
		doc.setFilePath(path);
		doc.setAuthor(author);
		doc.setContent(content);
		doc.setDocuType(DocuType);
		if(isExist.equals("exists")){
			new CommonProblemDao().delete(name);
		}
		
		SoftwareDepartmentDao dao = new SoftwareDepartmentDao();
		DocumentUploadUtil mail = new DocumentUploadUtil(request, password);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date()).replaceAll("-", ".");
		String subject = "EOULU:部门文档-软件部文档更新";
		String contentMail = dao.getRealName(request.getSession().getAttribute("user").toString()) + "于" + date + "在软件开发常见问题库修改ID为"
				+ID+"的记录的文件为："+ name + "。请查收！";
		String[] fileList = new String[] { path };
		contentMail = new MethodUtil().getEmailSign(contentMail, null);
		String result="上传成功！";
		if(password==null||password.equals("")){
			result = "邮箱密码不为空!";
		}else{
			boolean temp = mail.doSendHtmlEmail(subject, contentMail, null);
			if(!temp){
				result = "邮件发送失败！";
			}else{
				boolean flag = new CommonProblemDao().update(doc);
				if(!flag){
					result = "上传失败!";
				}
			}
		}
		return result;
	
	}

}
