package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.InstallationManual;

public interface InstallationManualService {

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getInstallationManual(Page page,String area,String type);
	
	/**
	 * 总记录数
	 * @return
	 */
	public int getAllcounts(String area,String type);
	/**
	 * 添加
	 */
	public boolean installationManualAdd(HttpServletRequest request);
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getInstallationManualByID(int id);
	/**
	 * 批量上传
	 * @param request
	 * @return
	 */
	public boolean moreManualAdd(HttpServletRequest request);
	
	public List<Map<String, Object>> getSearch(Page page, String content) ;
	
	public int getSearchCounts(Page page, String content);
	
	public List<Map<String, Object>> queryInstallationManual(Page page, String area,String content,String type);
	
	public int queryAllCounts(String area,String content,String type);
	
	public boolean saveInstallationPackage(InstallationManual manual);
}
