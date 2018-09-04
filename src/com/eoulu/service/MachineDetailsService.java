package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.MachineDetails;

public interface MachineDetailsService {
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getMachineDetails(Page page);
	
	/**
	 * 总记录数
	 * @return
	 */
	public int getAllcounts();
	/**
	 * 添加
	 */
	public boolean machineDetailsAdd(HttpServletRequest request);
	/**
	 * 修改
	 */
	public boolean machineDetailsUpdate(HttpServletRequest request);
	/**
	 * 删除
	 */
	public boolean machineDetailsDelete(HttpServletRequest request);
	
	/**
	 * 根据合同号获取合同配置信息
	 */
	public List<Map<String,Object>> getConfigure(String contractNo);
	/**
	 * 单项分页查询
	 * @param classify 参数名
	 * @param parameter 参数
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getMachineDetailsByPageInOne(String classify, Object parameter, Page page );
	/**
	 * 单项查询总条数
	 * @param classify 参数名
	 * @param parameter 参数
	 * @return
	 */
	public int getCountByClassify(String classify, Object parameter);
	/**
	 * 单项按时间分页查询
	 * @param classify 参数名
	 * @param start_time1 起始时间
	 * @param end_time1   截止时间
	 * @param page     页数
	 * @return
	 */
	public List<Map<String,Object>> getMachineDetailsByTime(String classify, String start_time1, String end_time1,Page page);
	/**
	 * 单项按时间查询
	 * @param classify 参数名
	 * @param start_time1 起始时间
	 * @param end_time1 截止时间
	 * @return
	 */
	public int getCountByClassifyTime(String classify, String start_time1, String end_time1);
	/**
	 * 组合按时间查询总条数
	 * @param classify1 参数1名
	 * @param map1
	 * @param classify2参数2名
	 * @param map2
	 * @return
	 */
	public int getCountByTimeClassify(String classify1, Map<String, Object> map1, String classify2,Map<String, Object> map2);
	/**
	 * 组合按时间分页查询
	 * @param classify1
	 * @param map1
	 * @param classify2
	 * @param map2
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getMachineDetailsByPageInTwoTime(String classify1, Map<String, Object> map1,
			String classify2, Map<String, Object> map2, Page page);
	/**
	 * 组合查询总条数
	 * @param classify1 参数1名
	 * @param parameter1 参数1
	 * @param classify2  参数2名
	 * @param parameter2 参数2
	 * @return
	 */
	public int getCountByClassify(String classify1, Object parameter1, String classify2, Object parameter2);
	
	/**
	 * 组合查询
	 * @param classify1  参数1名
	 * @param parameter1 参数1
	 * @param classify2  参数2名
	 * @param parameter2 参数2
	 * @param page 页数
	 * @return
	 */
	public List<Map<String, Object>> getMachineDetailsByPageInTwo(String classify1, Object parameter1, String classify2,
			Object parameter2, Page page);
	
}
