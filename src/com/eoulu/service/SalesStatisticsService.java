package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;

public interface SalesStatisticsService {

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getSalesStatistics(Page page);
	
	/**
	 * 总记录数
	 * @return
	 */
	public int getAllcounts();
	
	/**
	 * 单项分页查询
	 * @param classify 参数名
	 * @param parameter 参数
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getSalesStatisticsByPageInOne(String classify, Object parameter, Page page );
	/**
	 * 单项查询总条数
	 * @param classify 参数名
	 * @param parameter 参数
	 * @return
	 */
	public int getCountByClassify(String classify, Object parameter);
	
	
	public List<Map<String,Object>> getHotProduct(Page page);
	
	public int getHotProductCount();
	
	public List<Map<String,Object>> getHotProductByTime(Page page,String startTime,String endTime,String cost);
	
	public int getHotProductByTimeCount(Page page,String startTime,String endTime);
}
