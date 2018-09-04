package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.eoulu.dao.DocumentModelDao;
import com.eoulu.service.DocumentModelService;

public class DocumentModelServiceImpl implements DocumentModelService{

	@Override
	public String getModelByName(String name) {
		DocumentModelDao dao = new DocumentModelDao();
		List<Map<String, Object>> ls = dao.getFileByName(name);
		String filePath = "";
		Map<String, Object> map = ls.get(1);
		Set<String> keys = map.keySet();
		
		for(String key : keys){
		
			filePath =  map.get(key).toString();
			
		}
		return filePath;
	}

	@Override
	public List<Map<String, Object>> getAllFileName() {
		DocumentModelDao dao = new DocumentModelDao();
		return dao.getAllName();
	}

//	public static void main(String[] args) {
//		String name="Commercial Invoice-GNHCHNHI17033111LXJ.doc";
//		DocumentModelService ss = new DocumentModelServiceImpl();
//		String test = ss.getModelByName(name);
//		System.out.println(test);
//	}
}
