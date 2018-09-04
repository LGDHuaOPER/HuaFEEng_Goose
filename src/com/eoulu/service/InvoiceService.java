package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.CompeteInvoice;
import com.eoulu.entity.Invoice;
import com.eoulu.entity.Item;

public interface InvoiceService {

	/**
	 * 分页查询出所有的发票信息
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getOnlyInvoice(Page page);
	/**
	 * 
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getAllInvoice(int page);
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
	public List<Map<String,Object>> getInvoiceByPageInOne(String classify, Object parameter, Page page );
	/**
	 * 单项查询结果
	 * @param queryList
	 * @return
	 */
	public List<Map<String,Object>> queryResult(List<Map<String,Object>> queryList);
	/**
	 * 单项按时间分页查询
	 * @param classify 参数名
	 * @param start_time1 起始时间
	 * @param end_time1   截止时间
	 * @param page     页数
	 * @return
	 */
	public List<Map<String,Object>> getInvoiceByTime(String classify, String start_time1, String end_time1,Page page);
	/**
	 * 单项查询总条数
	 * @param classify 参数名
	 * @param parameter 参数
	 * @return
	 */
	public int getCountByClassify(String classify, Object parameter);
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
	 * 组合查询总条数
	 * @param classify1 参数1名
	 * @param parameter1 参数1
	 * @param classify2  参数2名
	 * @param parameter2 参数2
	 * @return
	 */
	public int getCountByClassify(String classify1, Object parameter1, String classify2, Object parameter2);
	/**
	 * 组合按时间分页查询
	 * @param classify1
	 * @param map1
	 * @param classify2
	 * @param map2
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getInvoiceByPageInTwoTime(String classify1, Map<String, Object> map1,
			String classify2, Map<String, Object> map2, Page page);
	/**
	 * 组合查询
	 * @param classify1  参数1名
	 * @param parameter1 参数1
	 * @param classify2  参数2名
	 * @param parameter2 参数2
	 * @param page 页数
	 * @return
	 */
	public List<Map<String, Object>> getInvoiceByPageInTwo(String classify1, Object parameter1, String classify2,
			Object parameter2, Page page);
	
	/**
	 * 用于修改单条的invoice与item
	 * @param request
	 * @return
	 */
	public CompeteInvoice UpdateInvoiceByRequest(HttpServletRequest request);
	
	/**
	 * 发票添加
	 * @param invoice 发票对象
	 * @param request item参数的值以数组的形式传递过来
	 * @return
	 */
	public int invoiceAdd(Invoice invoice,HttpServletRequest request);
	/**
	 * 发票修改
	 * @param invoice 发票对象
	 * @param request item参数的值以数组的形式传递过来
	 * @return
	 */
	public int InvoiceModify(Invoice invoice,HttpServletRequest request);
	
	

}
