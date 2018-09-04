/**
 * 
 */
package com.eoulu.service;

import java.util.List;
import java.util.Map;

/**
 * @author zhangkai
 *
 */
public interface OrderStatusService {

	/**
	 * 获取所有的订单状态
	 * */
	public List<Map<String,Object>> getAllOrderStatus();
	
}
