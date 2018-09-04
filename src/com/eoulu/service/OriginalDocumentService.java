package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

public interface OriginalDocumentService {

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getOriginalDocument(Page page,String type);
	
	/**
	 * 总记录数
	 * @return
	 */
	public int getAllcounts(String type);
	/**
	 * 添加
	 */
	public boolean originalDocumentAdd(HttpServletRequest request);
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getOriginalDocumentByID(int id);
	
	public boolean moreOriginalDocumentAdd(HttpServletRequest request) ;
	
	public List<Map<String, Object>> queryOriginalDocument(Page page, String type,String content);
	
	public int queryAllCounts(String type,String content);
	
}
