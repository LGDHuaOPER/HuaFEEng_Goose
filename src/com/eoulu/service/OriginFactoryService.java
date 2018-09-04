package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.OriginFactory;
import com.google.gson.JsonElement;

public interface OriginFactoryService {

	public List<Map<String, Object>> getAllData(Page page);
	
	public int getAllCounts();
	
	public String update(HttpServletRequest request);
	
	public String sendEmail(HttpServletRequest request);
	
	public boolean updateType(OriginFactory factory);
	
	public boolean delete (int id);
	
	public boolean insert(OriginFactory factory);
	
	/**
	 * 判断PO是否已向原厂下了
	 * @param po
	 * @return
	 */
	public boolean getInfoByPO(int poID,String type);

	public boolean upLoadFiles(HttpServletRequest request);

	public String downLoadFiles(HttpServletRequest request);

	public int getAllCountsByTwo(String type1, String content1, String type2, String content2);

	public List<Map<String, Object>> getAllDataByTwo(Page page, String type1, String content1, String type2, String content2);

	public int getAllCountsByOne(String type1, String content1);

	public List<Map<String, Object>> getAllDataByone(Page page, String type1, String content1);
	
	public List<Map<String,Object>> getMail(int factoryID,String type);
}
