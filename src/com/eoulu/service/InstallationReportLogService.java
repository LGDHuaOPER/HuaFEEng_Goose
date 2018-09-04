package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

public interface InstallationReportLogService {

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getInstallationReportLog(Page page,String area,String Year);
	
	/**
	 * 总记录数
	 * @return
	 */
	public int getAllcounts(String area,String Year);
	/**
	 * 添加
	 */
	public boolean installationReportLogAdd(HttpServletRequest request);
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getInstallationReportLogByID(int id);
	/**
	 * 批量上传
	 * @param request
	 * @return
	 */
	public boolean moreReportLogAdd(HttpServletRequest request);
	
	public List<Map<String, Object>> queryInstallationReportLog(Page page, String area, String year,String content);
	
	public int queryAllCounts(String area, String year,String content);
	
}
