package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.dao.ApplicationGalleryDao;
import com.eoulu.entity.ProductDrawings;
import com.eoulu.entity.Scheme;
import com.eoulu.service.ApplicationGalleryService;

public class ApplicationGalleryServiceImpl implements ApplicationGalleryService{

	@Override
	public boolean addScheme(Scheme scheme) {
		ApplicationGalleryDao dao = new ApplicationGalleryDao();
		
		return dao.addScheme(scheme);
	}

	@Override
	public boolean updateScheme(Scheme scheme) {
		ApplicationGalleryDao dao = new ApplicationGalleryDao();
		
		return dao.updateScheme(scheme);
	}

	@Override
	public List<Map<String, Object>> getSchemeByPage(Page page, String type, String column1, String content1,
			String column2, String content2) {
		ApplicationGalleryDao dao = new ApplicationGalleryDao();
		
		return dao.getSchemeByPage(page, type, column1, content1, column2, content2);
	}

	@Override
	public boolean addProduct(ProductDrawings drawings) {
		ApplicationGalleryDao dao = new ApplicationGalleryDao();

		return dao.addProduct(drawings);
	}

	@Override
	public boolean updateProduct(ProductDrawings drawings) {
		ApplicationGalleryDao dao = new ApplicationGalleryDao();

		return dao.updateProduct(drawings);
	}

	@Override
	public List<Map<String, Object>> getDrawingsByPage(Page page, String category, String type, String column1,
			String content1, String column2, String content2) {
		ApplicationGalleryDao dao = new ApplicationGalleryDao();

		return dao.getDrawingsByPage(page, category, type, column1, content1, column2, content2);
	}

	@Override
	public int getSchemeCount(String type, String column1, String content1, String column2, String content2) {
		
		ApplicationGalleryDao dao = new ApplicationGalleryDao();

		return dao.getSchemeCount(type, column1, content1, column2, content2);
	}

	@Override
	public int getDrawingsCount(String category, String type, String column1, String content1, String column2,
			String content2) {
		
		ApplicationGalleryDao dao = new ApplicationGalleryDao();

		return dao.getDrawingsCount(category, type, column1, content1, column2, content2);
	}

}
