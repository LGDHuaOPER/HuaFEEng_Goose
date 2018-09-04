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
public interface QuotesService {

	public List<Map<String,Object>> GetQuotesByID(String id);
	
}
