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
import com.eoulu.dao.SoftwareDepartmentDao;
import com.eoulu.entity.SoftwareDepartment;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.SoftwareDepartmentService;
import com.eoulu.util.DocumentUploadUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.ReadZipUtil;

public class SoftwareDepartmentServiceImpl implements SoftwareDepartmentService{

	@Override
	public List<Map<String, Object>> getSoftwareDepartment(Page page, String type,String order) {
		
		return new SoftwareDepartmentDao().getSoftwareDepartment(page, type,order);
	}

	@Override
	public int getAllcounts(String type) {
		
		return new SoftwareDepartmentDao().getAllCounts(type);
	}

	@Override
	public String softwareDepartmentAdd(HttpServletRequest request) {
		boolean flag = false;
		String path01 = "D:\\tempZipFile";
		File filePath = new File(path01);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		String tempPath = "E:\\LogisticsFile\\File\\";
		ReadZipUtil util =new ReadZipUtil();
		Map<String,String> map = new HashMap<>();
		try {
			map = util.getForm(filePath, request, tempPath);
		} catch (FileUploadException | IOException e) {
			
			e.printStackTrace();
		}
		System.out.println(123);
		SoftwareDepartment doc = new SoftwareDepartment();
		String password = map.get("password");
		String name = map.get("fileName");
		String path = map.get("filePath");
		String type = map.get("type");
		System.out.println(path);
		String isExist = map.get("isExist");
		if(isExist.equals("exists")){
			new SoftwareDepartmentDao().delete(name);
		}
		doc.setType(type);
		doc.setFileName(name);
		doc.setFilePath(path);
		SoftwareDepartmentDao dao = new SoftwareDepartmentDao();
		flag= dao.insert(doc);
		DocumentUploadUtil mail = new DocumentUploadUtil(request, password);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date()).replaceAll("-", ".");
		String subject = "Eoulu:部门文档-软件部文档更新";
		String content = dao.getRealName(request.getSession().getAttribute("user").toString())+"于"+date+"上传"+name+"请查收！";
		String[] fileList = new String[]{path};
		content = new MethodUtil().getEmailSign(content, null);
		String result = "上传成功!";
		if(password==null||password.equals("")){
			if(flag){
				result = "上传成功!邮箱验证失败!";
				
				LogInfoService log = new LogInfoServiceImpl();
				String JspInfo = "文档上传";
				String description = "EUCP软件实施上传-" + name;
				log.insert(request, JspInfo, description);
			}else{
				result = "上传失败！";
			}
		}else{
			boolean temp =  mail.doSendHtmlEmail(subject, content, null);
			if(!temp){
				result = "上传成功！邮件发送失败！";
			}
		}
		System.out.println("------=="+result);
		return result;
		
	}

	@Override
	public List<Map<String, Object>> getSoftwareDepartmentByID(int id) {
		
		return new SoftwareDepartmentDao().getSoftwareDepartmentByID(id);
	}
	public String moreSoftwareDepartmentAdd(HttpServletRequest request) {
		List<String> flags = new ArrayList<>();
		boolean flag = false;
		String path01 = "D:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 7.0\\temp";
		File filePath = new File(path01);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		
		String tempPath = "E:\\LogisticsFile\\File\\";
		ReadZipUtil util =new ReadZipUtil();
		List<Map<String,String>> ls = new ArrayList<Map<String,String>>();
		try {
			ls = util.getMoreForm(filePath, request, tempPath);
		} catch (FileUploadException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		SoftwareDepartmentDao dao = new SoftwareDepartmentDao();
		Map<String,String> map = ls.get(ls.size()-1);
		String area = map.get("area");
		String type = map.get("type");
		String password = map.get("password");
		String[] fileList = new String[ls.size()-1];
		String file = "";
		for(int i = 0; i < ls.size()-1; i++){
			Map<String, String> tempMap = ls.get(i);
			String name = tempMap.get("fileName");
			if (i != ls.size() - 2) {
				file +=  "<span style='font-family:微软雅黑;font-size:14px;'>"+name + "，</span><br>";
			} else {
				file += "<span style='font-family:微软雅黑;font-size:14px;'>"+name + "。</span><br>";
			}
		}
		
		System.out.println("password--"+password);
		DocumentUploadUtil mail = new DocumentUploadUtil(request, password);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date()).replaceAll("-", ".");
		String subject = "Eoulu:部门文档-软件部文档更新";
		String content = "<span style='font-family:微软雅黑;font-size:14px;'>"+
				dao.getRealName(request.getSession().getAttribute("user").toString()) + "于" + date + "在软件项目实施进度中上传:</span><br>"
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
				for(int i=0 ; i< ls.size()-1 ; i++){
					Map<String,String> tempMap = ls.get(i);
					SoftwareDepartment doc = new SoftwareDepartment();
					String name = tempMap.get("fileName");
					String path = tempMap.get("filePath");
					doc.setType(type);
					doc.setFileName(name);
					doc.setFilePath(path);
					boolean temp2 = dao.insert(doc);
					if(temp2){
						fileList[i] = path;
						
						flags.add("true");
					}else{
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
	public List<Map<String, Object>> querySoftwareDepartment(Page page, String type, String content,String order) {

		content = "%"+content+"%";
		return  new SoftwareDepartmentDao().querySoftwareDepartment(page, type, content,order);
	}

	@Override
	public int queryAllCounts(String type, String content) {
		content = "%"+content+"%";
		return  new SoftwareDepartmentDao().queryAllCounts(type, content);
	}
}
