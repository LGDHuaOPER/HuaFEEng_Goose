/**
 * 
 */
package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.dao.OrderStatusDao;
import com.eoulu.service.OrderStatusService;

/**
 * @author zhangkai
 *
 */
public class OrderStatusServiceImpl implements OrderStatusService {

	@Override
	public List<Map<String, Object>> getAllOrderStatus() {
		// TODO Auto-generated method stub
		return new OrderStatusDao().getAllOrderStatus();
	}

}
