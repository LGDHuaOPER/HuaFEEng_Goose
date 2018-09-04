package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

public interface InstallationImageService {

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getInstallationImage(Page page);
	
	/**
	 * 总记录数
	 * @return
	 */
	public int getAllcounts();
	/**
	 * 添加
	 */
	public boolean installationImageAdd(HttpServletRequest request);
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getInstallationImageByID(int id);
	
	public boolean moreImageAdd(HttpServletRequest request);
	
	public List<Map<String, Object>> queryInstallationImage(Page page,String content);
	
	public int queryAllCounts(String content);
	
}
