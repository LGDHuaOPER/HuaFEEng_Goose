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
public interface PaymentTermsService {

	
	/**
	 * 获得所有的付款条件
	 * */
	public List<Map<String,Object>> getAllPayTerms();
}
