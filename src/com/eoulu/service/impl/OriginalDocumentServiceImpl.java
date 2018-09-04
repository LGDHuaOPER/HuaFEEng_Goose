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
import com.eoulu.dao.OriginalDocumentDao;
import com.eoulu.entity.OriginalDocument;
import com.eoulu.service.OriginalDocumentService;
import com.eoulu.util.ReadZipUtil;

public class OriginalDocumentServiceImpl implements OriginalDocumentService{

	@Override
	public List<Map<String, Object>> getOriginalDocument(Page page, String type) {
		
		return new OriginalDocumentDao().getOriginalDocument(page, type);
	}

	@Override
	public int getAllcounts(String type) {
		
		return new OriginalDocumentDao().getAllCounts(type);
	}

	@Override
	public boolean originalDocumentAdd(HttpServletRequest request) {
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
		OriginalDocument doc = new OriginalDocument();
		String name = map.get("fileName");
		String path = map.get("filePath");
		String type = map.get("type");
		String isExist = map.get("isExist");
		if(isExist.equals("exists")){
			new OriginalDocumentDao().delete(name);
		}
		doc.setType(type);
		doc.setFileName(name);
		doc.setFilePath(path);
		
		flag= new OriginalDocumentDao().insert(doc);
		
		return flag;
		
	}

	@Override
	public List<Map<String, Object>> getOriginalDocumentByID(int id) {
		
		return new OriginalDocumentDao().getOriginalDocumentByID(id);
	}
	public boolean moreOriginalDocumentAdd(HttpServletRequest request) {
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
		String type = map.get("type");
		for(int i=0 ; i< ls.size()-1 ; i++){
			Map<String,String> tempMap = ls.get(i);
			OriginalDocument doc = new OriginalDocument();
			String name = tempMap.get("fileName");
			String path = tempMap.get("filePath");
			
			String isExist = tempMap.get("isExist");
			if(isExist.equals("exists")){
				new OriginalDocumentDao().delete(name);
			}
			doc.setType(type);
			doc.setFileName(name);
			doc.setFilePath(path);
			boolean temp = new OriginalDocumentDao().insert(doc);
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
	public List<Map<String, Object>> queryOriginalDocument(Page page, String type, String content) {
		content = "%"+content+"%";
		return new OriginalDocumentDao().queryOriginalDocument(page, type, content);
	}

	@Override
	public int queryAllCounts(String type, String content) {
		content = "%"+content+"%";
		return new OriginalDocumentDao().queryAllCounts(type, content);
	}
}
