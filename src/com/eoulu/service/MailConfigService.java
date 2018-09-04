package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.entity.MailConfig;

public interface MailConfigService {
	
	public List<Map<String, Object>> getConfig(String page);
	
	public String modifyConfig(MailConfig config);

}
