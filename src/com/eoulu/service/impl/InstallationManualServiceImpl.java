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
import com.eoulu.entity.InstallationManual;
import com.eoulu.service.InstallationManualService;
import com.eoulu.util.ReadZipUtil;

public class InstallationManualServiceImpl implements InstallationManualService{

	@Override
	public List<Map<String, Object>> getInstallationManual(Page page,String area,String type) {
		
		return new InstallationManualDao().getInstallationManual(page, area,type);
	}

	@Override
	public int getAllcounts(String area,String type) {
		
		return new InstallationManualDao().getAllCounts(area,type);
	}

	@Override
	public boolean installationManualAdd(HttpServletRequest request) {
		boolean flag = false;
		String path01 = "D:\\tempZipFile";
		File filePath = new File(path01);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		
		String tempPath = "E:\\LogisticsFile\\File\\";
		
		ReadZipUtil util =new ReadZipUtil();
		Map<String,String> map = new HashMap<String,String>();
		try {
			map = util.getForm(filePath, request, tempPath);
		} catch (FileUploadException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		InstallationManual manual = new InstallationManual();
		String name = map.get("fileName");
		String path = map.get("filePath");
		String area = map.get("area");
		String type = map.get("type");
		System.out.println("ssss"+type);
	
		String isExist = map.get("isExist");
		if(isExist.equals("exists")){
			new InstallationManualDao().delete(name,type);
		}
		manual.setArea(area);
		manual.setFileName(name);
		manual.setFilePath(path);
		manual.setType(type);

//		//上传时需要解压
//		if(name.toLowerCase().endsWith(".rar")){
//			flag = util.unRarFile(path, path01);
//		}
//		if(name.toLowerCase().endsWith(".zip")){
//			try {
//				flag = util.unZipFiles(path, path01);
//			} catch (Exception e) {
//				
//				e.printStackTrace();
//			}
//		}
//		
//		if(flag){
//		
//		}
		flag= new InstallationManualDao().insert(manual);

		return flag;
	}

	@Override
	public List<Map<String, Object>> getInstallationManualByID(int id) {
		
		return new InstallationManualDao().getInstallationManualByID(id);
	}

	public boolean moreManualAdd(HttpServletRequest request) {
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
			InstallationManual manual = new InstallationManual();
			String name = tempMap.get("fileName");
			String path = tempMap.get("filePath");
			String isExist = tempMap.get("isExist");
			String type = ls.get(ls.size()-1).get("DocuType");
		
	
//			if(isExist.equals("exists")){
//				new InstallationManualDao().delete(name);
//			}
			System.out.println("文件名"+type);
			manual.setArea(area);
			manual.setFileName(name);
			manual.setFilePath(path);
			manual.setType(type);
	
			boolean temp = new InstallationManualDao().insert(manual);
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
	public List<Map<String, Object>> getSearch(Page page, String content) {

		return new InstallationManualDao().getSearch(page, content);
	}

	@Override
	public int getSearchCounts(Page page, String content) {
		return  new InstallationManualDao().getSearchCounts(page, content);
	}

	@Override
	public List<Map<String, Object>> queryInstallationManual(Page page, String area, String content,String type) {
		content = "%"+content+"%";
		return new InstallationManualDao().queryInstallationManual(page, area, content,type);
	}

	@Override
	public int queryAllCounts(String area, String content,String type) {
		content = "%"+content+"%";
		return new InstallationManualDao().queryAllCounts(area, content,type);
	}

	@Override
	public boolean saveInstallationPackage(InstallationManual manual) {
		// r
		return new InstallationManualDao().insert(manual);
	}
	
	
}
