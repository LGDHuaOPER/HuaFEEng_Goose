package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.BiddingDocumentDao;
import com.eoulu.entity.BiddingDocument;
import com.eoulu.service.BiddingDocumentService;
import com.eoulu.util.DocumentUploadUtilY;

public class BiddingDocumentServiceImpl implements BiddingDocumentService{

	@Override
	public List<Map<String, String>> batchUpload(HttpServletRequest request) {
		DocumentUploadUtilY util = new DocumentUploadUtilY();
		BiddingDocumentDao dao = new BiddingDocumentDao();
		Map<String, Object> map = util.batchUpload(request, "E:\\LogisticsFile\\File\\BiddingDocument");
		String year = (String) map.get("Year");
		@SuppressWarnings("unchecked")
		List<Map<String, String>> fileInfo = (List<Map<String, String>>) map.get("FileInfo");
		for(int i = 0;i < fileInfo.size();i ++){
			
			String message = fileInfo.get(i).get("Message");
			if(message.equals("上传成功")){
				String fileName = fileInfo.get(i).get("FileName");
				String path = fileInfo.get(i).get("Path");
				BiddingDocument document = new BiddingDocument();
				document.setFileName(fileName);
				document.setPath(path);
				document.setYear(year);
				dao.insert(document);
				
			}
		}
		return fileInfo;
	}

	@Override
	public int getCounts(String column, String content) {
		BiddingDocumentDao dao = new BiddingDocumentDao();
		return dao.getCounts(column, content);
	}

	@Override
	public List<Map<String, Object>> getDataByPage(Page page, String column, String content) {
		BiddingDocumentDao dao = new BiddingDocumentDao();
		return dao.getAllData(page, column, content);
	}

	@Override
	public boolean update(BiddingDocument document) {
		BiddingDocumentDao dao = new BiddingDocumentDao();
		return dao.update(document);
	}
	
	



}
