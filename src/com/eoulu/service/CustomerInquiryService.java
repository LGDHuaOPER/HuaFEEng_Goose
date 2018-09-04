package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

public interface CustomerInquiryService {

	/**
	 * 分页查询
	 * @param page
	 * @param content
	 * @return
	 */
	public List<Map<String,Object>> getAllData(Page page,String content);
	
	public int getAllCounts(String content);
	
	/**
	 * 添加、修改
	 * @param request
	 * @return
	 */
	public String operate(HttpServletRequest request);
	
	/**
	 * 发起服务
	 * @param id
	 * @return
	 */
	public boolean updateServiceTime(int id);
	/**
	 * 修改弹框的回显
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getOperateData(int id);
	/**
	 * 预览
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getPreviewData(int id);
	/**
	 * 客户信息查询
	 * @return
	 */
	public List<Map<String,Object>> getAllCustomer(String content);
	/**
	 * 软件产品管理型号信息
	 * @return
	 */
	public List<Map<String,Object>> getAllProductModel(String content,String column);
	/**
	 * 导出EXcel
	 * @param path
	 */
	public void buildExcel(String path);
	/**
	 * 下载
	 * @param id
	 * @return
	 */
	public String getFileName(int id);
	
}
