package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.entity.QuoteCascadePO;
import com.eoulu.entity.QuoteOtherPO;
import com.eoulu.entity.QuoteOtherSupplierPO;
import com.eoulu.entity.QuoteSystemModel;

public interface QuoteSystemService {

	/**
	 * 所有客户资料
	 * @return
	 */
	public List<Map<String,Object>> getCustomerInfo();
	
	public int getCommodityCounts();
	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getCustomerInfoByColumn(String classify,String param);
	
	/**
	 * 所有业务员
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getStaffInfo(String department);
	/**
	 * 根据条件
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getStaffInfoByColumn(String classify,String param);
	
	/**
	 * 所有商品
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getCommodityInfo();
	
	
	public List<Map<String, Object>> getCommodityModel(String model);
	/**
	 * 商品信息分页
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getCommodityInfo(Page page);
	
//	public boolean upadteCommodityInfo(CommodityInfo info);
	/**
	 * 根据型号
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getCommodityInfoByModel(String model);
	
	
	
	public List<Map<String, Object>> getCommodityInfoByTitle(String name);
	/**
	 * 分页查询报价单信息
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getAllQuoteInfo(Page page,String classify1,String param1,String classify2,String param2);
	/**
	 * 总数量
	 * @return
	 */
	public int getAllCounts(String classify1,String param1,String classify2,String param2);
	/**
	 * 添加
	 * @param invoice
	 * @param db
	 * @return
	 */
	public boolean insert(HttpServletRequest request);
	
	/**
	 * 修改
	 * @param invoice
	 * @param db
	 * @return
	 */
	public boolean update(HttpServletRequest request);
	/**
	 * 今天录入的第几条报价单
	 * @return
	 */
	public int getTodayCount();
	/**
	 * 删除
	 * @return
	 */
	public boolean delete(HttpServletRequest request);
	/**
	 * 查詢報價單的商品
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getCommodityByQouteID(int id,String type);
	
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
	public List<Map<String,Object>>  getQueryByClassifyInOne(String classify, Object parameter,Page page);
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
	public List<Map<String,Object>> getQueryByClassifyInTwo(String classify1, Object parameter1,String classify2, Object parameter2,Page page);
	
	/**
	 * 修改RMB合同模板
	 * @param request
	 * @return
	 */
	public boolean ModifyRMBContractModel(HttpServletRequest request);
	/**
	 * 修改USD合同模板
	 * @param request
	 * @return
	 */
	public boolean ModifyUSDContractModel(HttpServletRequest request);
	/**
	 * 根据报价ID查看人民币合同
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getRMBModelInfo(int id);
	/**
	 * 根据报价ID查看美金合同
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getUSDModelInfo(int id);
	/**
	 * 删除人民币合同商品
	 * @param id
	 * @return
	 */
	public boolean deleteRMBCommodity(int id);
	/**
	 * 删除美金合同商品
	 * @param id
	 * @return
	 */
	public boolean deleteUSDCommodity(int id);
	/**
	 * 修改备货清单
	 * @param id
	 * @return
	 */
	public boolean modifyDelivery(int id);
	/**
	 * 修改备货清单
	 * @param id
	 * @return
	 */
	public boolean deleteDelivery(int id);
	
	/**
	 * 包装与运输要求添加
	 * @param req
	 * @return
	 */
	public boolean addQuoteRequest(HttpServletRequest request);
	/**
	 * 包装与运输要求修改
	 * @param req
	 * @return
	 */
	public boolean modifyQuoteRequest(HttpServletRequest request);
	/**
	 * 包装与运输要求显示
	 * @param req
	 * @return
	 */
	public List<Map<String,Object>> getQuoteRequest(int quoteID);
	
	public List<Map<String, Object>> getExcelQuoteRequest(int quoteID);
	/**
	 * 导出备货清单
	 * @param quoteID
	 * @return
	 */
	public void exportExcel(List<Map<String,Object>> ls,String path);
	/**
	 * 编辑Cascade PO模板
	 * @param request
	 * @return
	 */
	public boolean operateCascadePO(HttpServletRequest request);
	/**
	 * 编辑Cascade PO整机模板
	 * @param request
	 * @return
	 */
	public boolean operateCascadePOComplete(HttpServletRequest request);
	/**
	 * 编辑其他 PO模板
	 * @param request
	 * @return
	 */
	public boolean operateOtherPO(HttpServletRequest request);
	/**
	 * 删除Cascade PO 中商品
	 * @param id
	 * @return
	 */
	public boolean deleteCascadeTemp(int id);
	/**
	 * 删除其他供应商 PO 中商品
	 * @param id
	 * @return
	 */
	public boolean deleteOtherTemp(int id);
	
	public List<Map<String, Object>> getCascadePO(int id);
	
	public List<Map<String, Object>> getCascadeCompletePO(int id);
	
	public List<Map<String, Object>> getOtherPO(int id);
	
	public boolean exportCascadePOExcel(int id,String path,String image,String user,String email);
	
	public boolean exportOtherPOExcel(int id,String path,String image,String user,String email);
	
	public boolean updatePOContact(QuoteCascadePO other);
	
	public boolean updateOtherPOContact(QuoteOtherPO other);
	
	public List<Map<String, Object>> getCurrentUserEmail(String name);
	
	public boolean deleteCommodityTemp(int id,int quoteID,int commodityID,String model);
	
//	public boolean insertCommodityInfo(HttpServletRequest request);
	
	/**
	 * 编辑其他RMB PO模板
	 * @param request
	 * @return
	 */
	public boolean operateOtherRMBPO(HttpServletRequest request);
	
	public boolean deleteOtherRMBTemp(int id);
	
	public List<Map<String, Object>> getOtherRMBPO(int id);
	
	public boolean exportOtherRMBPOExcel(int id,String path,String image,String user,String email);
	
	public boolean updateOtherRMBPOContact(QuoteOtherSupplierPO other);
	
	public List<Map<String, Object>> getCascadePOByQuoteID(int quoteID);
	
	public List<Map<String, Object>> getOtherPOByQuoteID(int quoteID);
	
	public List<Map<String, Object>> getOtherRMBPOByQuoteID(int quoteID);
	
	public boolean insertModel(QuoteSystemModel model);
	
	public boolean modifyModel(QuoteSystemModel model);
	
	public String getQuoteNumber(int id);
	
	public String getCommodityName(String model);

	public List<String> GetModelByCategory(HttpServletRequest request);

	public Double getUnitPriceScoreByModel(String model);

	public Double getQuantityScoreByModel(String model);

	public Double getValueScoreByModel(String model,String startTime,String endTime);

	public Double getClientScoreByModel(String model,String startTime,String endTime);

	public Double getShippingScoreByModel(String model);
	
	public void exportCostExcel(int id,HttpServletResponse response);

	//public String[] getRaderByName(String name,String category);
	/**
	 * 华为模板
	 * @param id
	 * @return
	 */
	public Map<String,String[]>  getRaderByNames(String[] modelList);
	
	public String[] getDataByName(String name);
	
	public Map<String, String[]> getDataByNames(String name);
	
	public List<Map<String,Object>> getHuaEgoModel(HttpServletRequest request);
	
	public boolean operateHuaEgoParts(HttpServletRequest request);

	public List<Map<String,Object>> getCommodityOtherInfo(int iD);

	public int getCountByOne(String type1, String contect1);

	public List<Map<String,Object>> getcustomersByOne(String type1, String contect1,int currentPage);

	public int getCountByTwo(String type1, String contect1, String type2, String contect2);

	public List<Map<String,Object>> getcustomersByTwo(String type1, String contect1, String type2, String contect2,int currentPage);

	//public Map<String, String[]> getRaderByNames(String name, String category);

	public List<String> GetModelByCategoryName(String name);
	
	public List<Map<String,Object>> getAllQuoteNumber(String content);
	
	public List<Map<String,Object>> getCommodityInfo(int id);
	/**
	 * 邮件模板处理
	 * @param request
	 * @param poID
	 * @return
	 */
	public boolean operateCascadeMail(HttpServletRequest request,int poID);
	/**
	 * 邮件模板显示
	 * @param poID
	 * @return
	 */
	public List<Map<String,Object>> getCascadeMail(int poID);
	
	public boolean queryQuoteNumber(String number);
	
	public String setRadaTime(String startTime,String endTime);
	
	public Map<String, String> getTime();
	
	//历年销量对比
	public List<Map<String, Object>> getSalesVolume(String category,String model,String column,Page page);
	
	public int getModelCounts(String category,String model);
	
	public void updateClassify();

}
