package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.dao.MailConfigDao;
import com.eoulu.entity.MailConfig;
import com.eoulu.service.MailConfigService;

public class MailConfigServiceImpl implements MailConfigService{

	@Override
	public List<Map<String, Object>> getConfig(String page) {
	
		return new MailConfigDao().getConfig(page);
	}

	@Override
	public String modifyConfig(MailConfig config) {

		boolean result = new MailConfigDao().modifyConfig(config);
		
		return result?"设置成功":"设置失败";
	}

}
