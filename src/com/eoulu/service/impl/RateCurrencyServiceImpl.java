package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.dao.RateCurrencyDao;
import com.eoulu.service.RateCurrencyService;

public class RateCurrencyServiceImpl implements RateCurrencyService{

	@Override
	public List<Map<String, Object>> getAllData() {
		return new RateCurrencyDao().getAllData();
	}

}
