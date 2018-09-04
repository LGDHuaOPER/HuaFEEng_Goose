/**
 * 
 */package com.eoulu.service;

import java.util.List;
import java.util.Map;

/** @author  作者 : zhangkai
* @date 创建时间：2017年5月31日 下午6:19:51 
* @version 1.0  
* @since  
* @return  
*/
/**
* @author zhangkai
*
*/
public interface TransportService {

	/**
	 * 修改合同状态
	 * */
	public boolean modifyContractStatus(int orderID,int status);
	
	/**
	 * 获取合同类型
	 * */
	public String getOrderType(int orderID);
	
	
	/**
	 * 根据ID统计USD和RMB金额
	 * */
	public List<Map<String,Object>> getPOAmountsByID(int orderID);
	
	/**
	 * 修改审核状态1
	 * */
	public boolean modifyReview1Status(int review1,int userID,int orderInfoID);
	
	
	/**
	 * 修改审核状态2
	 * */
	public boolean modifyReview2Status(int review2,int userID,int orderInfoID);
	
	/**
	 * 导出合同和物流的信息（合同号对应PO号码）
	 * 
	 * @param content   需要导出的内容
	 * 
	 * @return 生成的文件地址
	 * */
	public String ExportOrderAndLogisticsInfo(List<Map<String,Object>> content);
}
