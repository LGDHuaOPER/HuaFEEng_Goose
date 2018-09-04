package com.eoulu.service;

import java.util.List;
import java.util.Map;

public interface DocumentModelService {

	public String getModelByName(String name);
	
	public List<Map<String, Object>> getAllFileName();
}
