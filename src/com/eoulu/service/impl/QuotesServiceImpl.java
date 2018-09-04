/**
 * 
 */
package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.dao.QuotesDao;
import com.eoulu.service.QuotesService;

/**
 * @author zhangkai
 *
 */
public class QuotesServiceImpl implements QuotesService {

	/* (non-Javadoc)
	 * @see com.eoulu.service.QuotesService#GetQuotesByID()
	 */
	@Override
	public List<Map<String, Object>> GetQuotesByID(String id) {
		// TODO Auto-generated method stub
		return new QuotesDao().getQuotesByID(id);
	}

}
