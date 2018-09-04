package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

public interface RequestAchieveService {

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getRequestAchieve(Page page,String area);
	
	/**
	 * 总记录数
	 * @return
	 */
	public int getAllcounts(String area);
	/**
	 * 添加
	 */
	public boolean requestAchieveAdd(HttpServletRequest request);
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getRequestAchieveByID(int id);
	
	
	public boolean moreRequestAchieveAdd(HttpServletRequest request);
	
	public List<Map<String, Object>> queryRequestAchieve(Page page, String area,String content);
	
	public int queryAllCounts(String area,String content);
}
