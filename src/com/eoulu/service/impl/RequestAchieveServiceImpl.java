package com.eoulu.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;

import com.eoulu.commonality.Page;
import com.eoulu.dao.RequestAchieveDao;
import com.eoulu.entity.RequestAchieve;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.RequestAchieveService;
import com.eoulu.util.ReadZipUtil;

public class RequestAchieveServiceImpl implements RequestAchieveService{

	@Override
	public List<Map<String, Object>> getRequestAchieve(Page page, String area) {
		
		return new RequestAchieveDao().getRequestAchieve(page, area);
	}

	@Override
	public int getAllcounts(String area) {
		
		return new RequestAchieveDao().getAllCounts(area);
	}

	@Override
	public boolean requestAchieveAdd(HttpServletRequest request) {
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
		RequestAchieve report = new RequestAchieve();
		String name = map.get("fileName");
		String path = map.get("filePath");
		String area = map.get("area");
		String isExist = map.get("isExist");
		if(isExist.equals("exists")){
			new RequestAchieveDao().delete(name);
		}
		report.setArea(area);
		report.setFileName(name);
		report.setFilePath(path);
	
		flag= new RequestAchieveDao().insert(report);

		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "文档上传";
			String description = "上传-服务请求和服务完成报告";
			log.insert(request, JspInfo, description);
		}
		
		return flag;
	}

	@Override
	public List<Map<String, Object>> getRequestAchieveByID(int id) {
		
		return new RequestAchieveDao().getRequestAchieveByID(id);
	}
	public boolean moreRequestAchieveAdd(HttpServletRequest request) {
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
		Map<String,String> map = ls.get(ls.size()-1);
		String area = map.get("area");
		for(int i=0 ; i< ls.size()-1 ; i++){
			Map<String,String> tempMap = ls.get(i);
			RequestAchieve report = new RequestAchieve();
			String name = tempMap.get("fileName");
			String path = tempMap.get("filePath");
			String isExist = tempMap.get("isExist");
//			if(isExist.equals("exists")){
//				new InstallationManualDao().delete(name);
//			}
			report.setArea(area);
			report.setFileName(name);
			report.setFilePath(path);
			boolean temp = new RequestAchieveDao().insert(report);
			if(temp){
				flags.add("true");
			}else{
				flags.add("flase");
			}
			
		}
		if(!flags.contains("flase")){
			flag = true;
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "文档上传";
			String description = "批量上传-服务请求和服务完成报告";
			log.insert(request, JspInfo, description);
		}
		
		return flag;
	}

	@Override
	public List<Map<String, Object>> queryRequestAchieve(Page page, String area, String content) {

		content = "%"+content+"%";
		return new RequestAchieveDao().queryRequestAchieve(page, area, content);
	}

	@Override
	public int queryAllCounts(String area, String content) {
		content = "%"+content+"%";
		return new RequestAchieveDao().queryAllCounts(area, content);
	}
}
