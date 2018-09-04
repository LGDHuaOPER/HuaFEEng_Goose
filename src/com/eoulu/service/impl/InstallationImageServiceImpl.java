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
import com.eoulu.dao.InstallationImageDao;
import com.eoulu.entity.InstallationImage;
import com.eoulu.service.InstallationImageService;
import com.eoulu.service.LogInfoService;
import com.eoulu.util.ReadZipUtil;

public class InstallationImageServiceImpl implements InstallationImageService{

	@Override
	public List<Map<String, Object>> getInstallationImage(Page page) {
		
		return new InstallationImageDao().getInstallationImage(page);
	}

	@Override
	public int getAllcounts() {
		
		return new InstallationImageDao().getAllCounts();
	}

	@Override
	public boolean installationImageAdd(HttpServletRequest request) {
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
		InstallationImage image = new InstallationImage();
		String name = map.get("fileName");
		String path = map.get("filePath");
		String isExist = map.get("isExist");
		if(isExist.equals("exists")){
			new InstallationImageDao().delete(name);
		}
		
		image.setFileName(name);
		image.setFilePath(path);
	    flag= new InstallationImageDao().insert(image);
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "文档上传";
			String description = "上传-装机图片";
			log.insert(request, JspInfo, description);
		}
		
		return flag;
	}

	@Override
	public List<Map<String, Object>> getInstallationImageByID(int id) {
		
		return new InstallationImageDao().getInstallationImageByID(id);
	}
	public boolean moreImageAdd(HttpServletRequest request) {
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
		for(int i=0 ; i< ls.size() ; i++){
			Map<String,String> tempMap = ls.get(i);
			InstallationImage image = new InstallationImage();
			String name = tempMap.get("fileName");
			String path = tempMap.get("filePath");
			String isExist = tempMap.get("isExist");
//			if(isExist.equals("exists")){
//				new InstallationManualDao().delete(name);
//			}
			System.out.println("文件名"+name);
			image.setFileName(name);
			image.setFilePath(path);
			boolean temp =  new InstallationImageDao().insert(image);
			if(temp){
				flags.add("true");
			}else{
				flags.add("flase");
			}
			
		}
		if(!flags.contains("flase")){
			flag = true;
		}
		
		return flag;
	}

	@Override
	public List<Map<String, Object>> queryInstallationImage(Page page, String content) {
		content = "%"+content+"%";
		return  new InstallationImageDao().queryInstallationImage(page, content);
	}

	@Override
	public int queryAllCounts(String content) {
		content = "%"+content+"%";
		return new InstallationImageDao().queryAllCounts(content);
	}
}
