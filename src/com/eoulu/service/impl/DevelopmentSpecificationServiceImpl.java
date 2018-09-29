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
import com.eoulu.dao.DevelopmentSpecificationDao;
import com.eoulu.dao.SoftwareDepartmentDao;
import com.eoulu.entity.DocumentUpload;
import com.eoulu.service.DevelopmentSpecificationService;
import com.eoulu.service.LogInfoService;
import com.eoulu.util.DocumentUploadUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.ReadImportDocUtil;
import com.eoulu.util.ReadZipUtil;

public class DevelopmentSpecificationServiceImpl implements DevelopmentSpecificationService {

	@Override
	public List<Map<String, Object>> getAllData(Page page, String column, String order) {
		return new DevelopmentSpecificationDao().getAllData(page, column, order);
	}

	@Override
	public int getAllCounts() {
		return new DevelopmentSpecificationDao().getAllCounts();
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
		String password = map.get("password");
		String name = map.get("fileName");
		String path = map.get("filePath");
		// String remarks = map.get("remarks");

		ReadImportDocUtil read = new ReadImportDocUtil();
		String remarks = "";
		if (path.endsWith(".doc")) {
			remarks = read.extractDoc(path, path.replaceAll(".doc", ".txt"));
		}
		if (path.endsWith(".docx")) {
			remarks = read.extractDoc(path, path.replaceAll(".docx", ".txt"));
		}
		String isExist = map.get("isExist");
		if (isExist.equals("exists")) {
			new DevelopmentSpecificationDao().delete(name);
		}
		DocumentUpload doc = new DocumentUpload();
		doc.setFileName(name);
		doc.setFilePath(path);
		doc.setRemarks(remarks);
		boolean flag = new DevelopmentSpecificationDao().insert(doc);
		DocumentUploadUtil mail = new DocumentUploadUtil(request, password);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date()).replaceAll("-", ".");
		String subject = "Eoulu:部门文档-软件部文档更新";
		SoftwareDepartmentDao dao = new SoftwareDepartmentDao();
		String content = dao.getRealName(request.getSession().getAttribute("user").toString()) + "于" + date + "上传"
				+ name + "请查收！";
		String[] fileList = new String[] { path };
		content = new MethodUtil().getEmailSign(content, null);
		String result = "上传成功!";
		if (password == null || password.equals("")) {
			if (flag) {
				result = "上传成功，邮件发送失败!";

				LogInfoService log = new LogInfoServiceImpl();
				String JspInfo = "文档上传";
				String description = "开发规范上传-" + name;
				log.insert(request, JspInfo, description);
			} else {
				result = "上传失败！";
			}
		} else {
			boolean temp = mail.doSendHtmlEmail(subject, content, null);
			if (!temp) {
				result = "邮件发送失败！";
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
		Map<String, String> map = ls.get(ls.size() - 1);
		String remarks = map.get("remarks");
		String password = map.get("password");
		String[] fileList = new String[ls.size() - 1];
		String file = "";
		for (int i = 0; i < ls.size() - 1; i++) {
			Map<String, String> tempMap = ls.get(i);
			String name = tempMap.get("fileName");
			if (i != ls.size() - 2) {
				file += "<span style='font-family:微软雅黑;font-size:14px;'>" + name + "，</span><br>";
			} else {
				file += "<span style='font-family:微软雅黑;font-size:14px;'>" + name + "。</span><br>";
			}
		}

		DocumentUploadUtil mail = new DocumentUploadUtil(request, password);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date()).replaceAll("-", ".");
		String subject = "Eoulu:部门文档-软件部文档更新";
		SoftwareDepartmentDao dao = new SoftwareDepartmentDao();
		String content = "<span style='font-family:微软雅黑;font-size:14px;'>"
				+ dao.getRealName(request.getSession().getAttribute("user").toString()) + "于" + date + "在软件开发规范中上传:</span><br>"
				+ file + "<span style='font-family:微软雅黑;font-size:14px;'>请查收！</span><br><br>";
		content = new MethodUtil().getEmailSign(content, null);

		String result = "上传成功！";
		if (password == null || password.equals("")) {
			result = "邮箱密码不为空！";
		} else {
			boolean temp = mail.doSendHtmlEmail(subject, content, null);
			if (!temp) {
				result = "邮件发送失败！";
			} else {
				for (int i = 0; i < ls.size() - 1; i++) {
					Map<String, String> tempMap = ls.get(i);
					String name = tempMap.get("fileName");
					String path = tempMap.get("filePath");
					DocumentUpload doc = new DocumentUpload();
					doc.setFileName(name);
					doc.setFilePath(path);
					doc.setRemarks(remarks);
					boolean temp2 = new DevelopmentSpecificationDao().insert(doc);

					if (temp2) {
						fileList[i] = path;
						flags.add("true");
					} else {
						flags.add("flase");
					}
				}
				if (flags.contains("false")) {
					result = "上传失败！";
				}
			}
		}

		return result;
	}

	@Override
	public List<Map<String, Object>> queryAllData(Page page, String content, String column, String order) {
		content = "%" + content + "%";
		return new DevelopmentSpecificationDao().queryAllData(page, content, column, order);
	}

	@Override
	public int queryAllCounts(String content) {
		content = "%" + content + "%";
		return new DevelopmentSpecificationDao().queryAllCounts(content);
	}

	@Override
	public String getDevelopmentSpecificationByID(int id) {
		return new DevelopmentSpecificationDao().getDevelopmentSpecificationByID(id);
	}

	@Override
	public boolean delete(int id) {
		return new DevelopmentSpecificationDao().delete(id);
	}
	
	

}
