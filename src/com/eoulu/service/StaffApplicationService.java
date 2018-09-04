package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

public interface StaffApplicationService {
	/**
	 * 分页
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getAllData(Page page,String user,String authority,String startTime,String endTime);
	
	public int getCounts(String user,String authority,String startTime,String endTime);
	
	
	public boolean exportExcel(String user,String authority,String startTime,String endTime,String path);
	/**
	 * 添加、修改
	 * @param request
	 * @return
	 */
	public String operate(HttpServletRequest request);
	/**
	 * 预览、导出
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getDataByID(int id);
	/**
	 * 审批
	 * @param request
	 * @return
	 */
	public String updateReview(HttpServletRequest request);
	
	/**销假
	 * @param request
	 * @return
	 */
	public String updateEliminate(HttpServletRequest request); 
}
