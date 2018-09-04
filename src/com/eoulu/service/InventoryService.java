package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

public interface InventoryService {

	public List<Map<String,Object>> getAllData(Page page,String classify,String content,String column);
	
	public int getAllCounts(String classify,String content);
	/**
	 * 增改库存信息
	 * @param request
	 * @return
	 */
	public String operate(HttpServletRequest request);
	/**
	 * 根据型号模糊查询
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> QueryModel(HttpServletRequest request);
	/**
	 * 增改入库/出库信息
	 * @param request
	 * @return
	 */
	public boolean operateStoreInfo(HttpServletRequest request);
	/**
	 * 查询入库/出库信息
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> getStoreInfo(HttpServletRequest request);
	/**
	 * 增改客户预定信息
	 * @param request
	 * @return
	 */
	public boolean operateCustomerOrder(HttpServletRequest request);
	
	public boolean deleteCustomerOrder(HttpServletRequest request);
	
	/**
	 * 查询客户预定信息
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> getCustomerOrder(HttpServletRequest request);
	/**
	 * 模糊查询客户名称
	 * @param request
	 * @return
	 */
	public List<Map<String,Object>> getAllCustomer(HttpServletRequest request);
	
	
	public boolean deleteInventory(HttpServletRequest request);
	/**
	 * 导出
	 * @return
	 */
	public List<Map<String,Object>> getAllData();
	
	public String addPNCode(int CommodityID,String PNCode);
	
	public boolean updatePNCode(int CommodityID,String PNCode);
	
	public List<Map<String, Object>> getInventory(String PNCode);
	
	public List<Map<String, Object>> getInfoByWarehouse(String warehouse,int commodityID);
	
	
	//更新最新盘点时间
	public boolean updateLatestInventory(String time);
	
	public List<Map<String, Object>> getTimeInfo();
}
