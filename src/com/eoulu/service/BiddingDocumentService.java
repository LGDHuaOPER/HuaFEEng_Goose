package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.BiddingDocument;

public interface BiddingDocumentService {
	
	public List<Map<String, String>> batchUpload(HttpServletRequest request);
	
	public int getCounts(String column,String content);
	
	public List<Map<String, Object>> getDataByPage(Page page,String column, String content);
	
	public boolean update(BiddingDocument document);

}
