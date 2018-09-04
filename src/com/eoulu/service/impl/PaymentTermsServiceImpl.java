/**
 * 
 */
package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.dao.PaymentStatusDao;
import com.eoulu.dao.PaymentTermsDao;
import com.eoulu.service.PaymentTermsService;

/**
 * @author zhangkai
 *
 */
public class PaymentTermsServiceImpl implements PaymentTermsService{

	@Override
	public List<Map<String, Object>> getAllPayTerms() {
		// TODO Auto-generated method stub
		return new PaymentTermsDao().getAllPayTerms();
	}

}
