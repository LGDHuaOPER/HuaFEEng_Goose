package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

public interface OriginService {

	/**
	 * 所有数量
	 * @return
	 */
	public int getAllCounts();
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getOrigin(Page page);
	
	/**
	 * 添加
	 * @param request
	 * @return
	 */
	public boolean addOrigin(HttpServletRequest request);
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	public boolean updateOrigin(HttpServletRequest request);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean deleteOrigin(int id);
	/**
	 * 单一查询的记录数
	 * @param classify
	 * @param parameter
	 * @return
	 */
	public int getCountByClassifyInOne(String classify, Object parameter );
	/**
	 * 单一查询返回结果
	 * @param classify
	 * @param parameter
	 * @return
	 */
	public List<Map<String,Object>>  getOriginByClassifyInOne(String classify, Object parameter,Page page);
	/**
	 * 组合查询记录数
	 * @param classify1
	 * @param parameter1
	 * @param classify2
	 * @param parameter2
	 * @return
	 */
    public int getCountByClassifyInTwo(String classify1, Object parameter1,String classify2, Object parameter2 );
	/**
	 * 组合查询结果
	 * @param classify1
	 * @param parameter1
	 * @param classify2
	 * @param parameter2
	 * @return
	 */
	public List<Map<String,Object>>  getOriginByClassifyInTwo(String classify1, Object parameter1,String classify2, Object parameter2,Page page);
	
	public List<Map<String,Object>> getGoods(int id);
	
	public boolean deleteGoods(int id);
	
}
