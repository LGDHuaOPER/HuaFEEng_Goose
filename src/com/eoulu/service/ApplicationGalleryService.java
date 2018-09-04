package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.ProductDrawings;
import com.eoulu.entity.Scheme;

public interface ApplicationGalleryService {
	
	public boolean addScheme(Scheme scheme);
	
	public boolean updateScheme(Scheme scheme); 
	
	public List<Map<String, Object>> getSchemeByPage(Page page,String type,String column1,String content1,String column2,String content2);
	
	
	public int getSchemeCount(String type,String column1,String content1,String column2,String content2);
	
	
	public boolean addProduct(ProductDrawings drawings);
	
	
	public boolean updateProduct(ProductDrawings drawings);
	
	
	public List<Map<String, Object>> getDrawingsByPage(Page page,String category,String type,String column1,String content1,String column2,String content2);

	
	public int getDrawingsCount(String category,String type,String column1,String content1,String column2,String content2);
	
}
