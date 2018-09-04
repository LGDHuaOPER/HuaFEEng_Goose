package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.OriginalQuotation;

public interface OriginalQuotationService {
	public List<Map<String, Object>> getDataByPage(Page page,String type,String column1,String content1,String column2,String content2);
	
	public int getCounts(String type,String column1,String content1,String column2,String content2);
	
	public boolean insert(OriginalQuotation originalQuotation);
	
	public boolean update(OriginalQuotation originalQuotation);
	
	public String getQuoteTotal(String fileName);
	

}
