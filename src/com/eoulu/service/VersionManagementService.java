package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.entity.VersionManagement;

public interface VersionManagementService {
	
	public List<Map<String, Object>> getData(String ProjectName);
	
	public String addVersion(VersionManagement version);

}
