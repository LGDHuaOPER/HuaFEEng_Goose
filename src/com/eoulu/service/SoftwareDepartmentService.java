package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

public interface SoftwareDepartmentService {

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getSoftwareDepartment(Page page,String type,String order);
	
	/**
	 * 总记录数
	 * @return
	 */
	public int getAllcounts(String type);
	/**
	 * 添加
	 */
	public String softwareDepartmentAdd(HttpServletRequest request);
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getSoftwareDepartmentByID(int id);
	
	public String  moreSoftwareDepartmentAdd(HttpServletRequest request);
	
	public List<Map<String, Object>> querySoftwareDepartment(Page page, String type,String content,String order);
	
	public int queryAllCounts(String type,String content);
	
}
