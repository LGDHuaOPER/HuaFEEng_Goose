package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.PackingList;

public interface PackingListService {

	/**
	 * 所有的箱单数量
	 * @return
	 */
	public int getAllCounts();
	/**
	 * 分页查询箱单信息
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getAllPacking(Page page);
	/**
	 * 根据箱单id查询对应的item信息
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getPackingItemByID(int id);
	
	/**
	 * 添加箱单信息与货物尺寸信息
	 * @param request
	 * @return
	 */
	public boolean addPackingListAndGoodsSize(HttpServletRequest request);
	/**
	 * 修改货物尺寸
	 * @param request
	 * @return
	 */
	public boolean ModifyPackingListAll(HttpServletRequest request);
	/**
	 * 删除PackingItem
	 * @param id
	 * @return
	 */
	public boolean deletePackingItem(int id);
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
	public List<Map<String,Object>>  getPackingListByClassifyInOne(String classify, Object parameter,Page page);
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
	public List<Map<String,Object>>  getPackingListByClassifyInTwo(String classify1, Object parameter1,String classify2, Object parameter2,Page page);
	
	public int getTodayPackingCounts();
	

	public  List<Map<String,Object>> getGoodsSizeById(int id);
	/**
	 * 根据箱单ID获取所有的配置信息
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getOtherAll(int id,String contractNO);

	public boolean deletePackingSize(int id);
	
	public boolean deletePackingGoods(int id);
	
	public boolean operatePackingItem(HttpServletRequest request);
	
	public boolean getContractConfigure(HttpServletRequest request);
	
	public boolean sendLogisticsMail(PackingList pList);
}
