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
import com.eoulu.dao.InstallationManualDao;
import com.eoulu.dao.InstallationReportLogDao;
import com.eoulu.dao.SoftwareDepartmentDao;
import com.eoulu.entity.InstallationManual;
import com.eoulu.entity.InstallationReportLog;
import com.eoulu.service.InstallationReportLogService;
import com.eoulu.service.LogInfoService;
import com.eoulu.util.ReadZipUtil;

public class InstallationReportLogServiceImpl implements InstallationReportLogService{

	@Override
	public List<Map<String, Object>> getInstallationReportLog(Page page, String area, String year) {
		
		return new InstallationReportLogDao().getInstallationReportLog(page, area, year);
	}

	@Override
	public int getAllcounts(String area, String year) {
		
		return new InstallationReportLogDao().getAllCounts(area, year);
	}
	@Override
	public boolean installationReportLogAdd(HttpServletRequest request) {
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
		InstallationReportLog log = new InstallationReportLog();
		String name = map.get("fileName");
		String path = map.get("filePath");
		String area = map.get("area");
		String year = map.get("year");
		String isExist = map.get("isExist");
		if(isExist.equals("exists")){
			new InstallationReportLogDao().delete(name);
		}
		log.setArea(area);
		log.setFileName(name);
		log.setFilePath(path);
		log.setYear(year);
		
		flag= new InstallationReportLogDao().insert(log);
		if(flag){
			LogInfoService logs = new LogInfoServiceImpl();
			String JspInfo = "文档上传";
			String description = "上传-装机报告和装机日志";
			logs.insert(request, JspInfo, description);
			flag = true;
		}
		return flag;
		
	}

	@Override
	public List<Map<String, Object>> getInstallationReportLogByID(int id) {
		
		return new InstallationReportLogDao().getInstallationReportLogByID(id);
	}

	public boolean moreReportLogAdd(HttpServletRequest request) {
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
		System.out.println("ls.size:"+ls.size());
		Map<String,String> map = ls.get(ls.size()-1);
		String area = map.get("area");
		String year = map.get("year");
		for(int i=0 ; i< ls.size()-1 ; i++){
			Map<String,String> tempMap = ls.get(i);
			InstallationReportLog log = new InstallationReportLog();
			String name = tempMap.get("fileName");
			System.out.println("name123:"+name);
			String path = tempMap.get("filePath");
			
			String isExist = tempMap.get("isExist");
//			if(isExist.equals("exists")){
//				new InstallationManualDao().delete(name);
//			}
			log.setArea(area);
			log.setFileName(name);
			log.setFilePath(path);
			log.setYear(year);
			boolean temp = new InstallationReportLogDao().insert(log);
			if(temp){
				flags.add("true");
			}else{
				flags.add("flase");
			}
		}
		if(!flags.contains("flase")){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "文档上传";
			String description = "批量上传-装机报告和装机日志";
			log.insert(request, JspInfo, description);
			flag = true;
		}
		return flag;
	}

	@Override
	public List<Map<String, Object>> queryInstallationReportLog(Page page, String area, String year, String content) {

		content = "%"+content+"%";
		return new InstallationReportLogDao().queryInstallationReportLog(page, area, year, content);
	}

	@Override
	public int queryAllCounts(String area, String year, String content) {
		content = "%"+content+"%";
		return new InstallationReportLogDao().queryAllCounts(area, year, content);
	}
	
}
