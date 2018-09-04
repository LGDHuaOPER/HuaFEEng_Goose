package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.StandardProduct;

public interface StandardProductService {
	
	public List<Map<String, Object>> getAllData(Page page,String type,String column1,String content1,String column2,String content2);
	
	public int getCounts(String type,String column1,String content1,String column2,String content2);
	
	public boolean insert(StandardProduct product);
	
	public boolean update(StandardProduct product);
	
	public String sendReviewMail(int ID, String title);
	
	public boolean review(int ID);

}
