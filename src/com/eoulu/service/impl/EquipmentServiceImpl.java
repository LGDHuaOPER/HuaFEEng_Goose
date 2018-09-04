package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eoulu.dao.EquipmentDao;
import com.eoulu.service.EquipmentService;
import com.google.gson.Gson;

/**
 * @author zhangkai
 *
 */
public class EquipmentServiceImpl implements EquipmentService {

	@Override
	public List<Map<String, Object>> getAllEquipmentByName(String model) {
		// TODO Auto-generated method stub
		return new EquipmentDao().getEquipmentByName(model);
	}

	@Override
	public List<Map<String, Object>> getAllEquipmentByOrderID(String OrderID) {
		// TODO Auto-generated method stub
		return new EquipmentDao().getEquipmentByOrderID(OrderID);
	}

	@Override
	public List<Map<String, Object>> getAllEquipmentInfo() {
		// TODO Auto-generated method stub
		return new EquipmentDao().getAllEquipmentInfo();
	}

}
