package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.dao.SalesWeightDao;
import com.eoulu.service.SalesWeightService;

public class SalesWeightServiceImpl implements SalesWeightService {

	@Override
	public List<Map<String, Object>> getSalesWeight() {
		return new SalesWeightDao().getSalesWeight();
	}

	@Override
	public Boolean update(int ID, double UnitPrice, double Quantity, double Value, double Client, double Shipping) {
		return new SalesWeightDao().update(ID, UnitPrice,Quantity,Value,Client,Shipping);
	}

}
