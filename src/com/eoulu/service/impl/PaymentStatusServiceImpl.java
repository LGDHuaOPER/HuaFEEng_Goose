/**
 * 
 */
package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.dao.PaymentStatusDao;
import com.eoulu.service.PaymentStatusService;

/**
 * @author zhangkai
 *
 */
public class PaymentStatusServiceImpl implements PaymentStatusService {

	/* (non-Javadoc)
	 * @see com.eoulu.service.PaymentStatusService#getAllPaymentStatus()
	 */
	@Override
	public List<Map<String, Object>> getAllPaymentStatus() {
		// TODO Auto-generated method stub
		return new PaymentStatusDao().getAllPaymentStatus();
	}

}
