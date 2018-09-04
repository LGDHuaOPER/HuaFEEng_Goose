package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;

public interface SalesWeightService {

	public List<Map<String,Object>> getSalesWeight();

	public Boolean update(int ID, double UnitPrice, double Quantity, double Value, double Client,
			double Shipping);
	

}
