/**
 * 
 */package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Quotation;
import com.eoulu.entity.QuotationConfiguration;

/** @author  作者 : zhangkai
* @date 创建时间：2017年5月24日 下午3:10:29 
* @version 1.0  
* @since  
* @return  
*/
/**
* @author zhangkai
*
*/
public interface QuotationService {

	/**
	 * 按照页数获取报价的信息
	 * */
	public List<Map<String,Object>> getQuotationByPage(Page page);
	/**
	 * 按照报价单号获取报价的信息
	 * */
	public List<Map<String,Object>> getQuotationByQuotationID(String QuotationID);
	/**
	 * 获取报价信息的总页数
	 * */
	public int getQuotationCounts();
	
	/**
	 * 插入报价信息
	 * */
	public boolean insertQuotation(Quotation quotation);
	
	
	/**
	 * 修改报价信息
	 * */
	public boolean modifyQuotation(Quotation quotation);
	
	
	/**
	 * 获取配置信息
	 * */
	public List<Map<String,Object>> getQuotationConf(int id);
	
	/**
	 * 插入配置信息
	 * */
	public boolean insertQuotationConf(QuotationConfiguration configuration);
	/**
	 * 删除配置信息
	 * */
	public boolean deleteQuotationConf(QuotationConfiguration configuration);
	
	/**
	 * 修改配置信息
	 * */
	public boolean modifyQuotationConf(QuotationConfiguration configuration);
	
}
