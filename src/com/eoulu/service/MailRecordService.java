package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.MailRecord;
import com.eoulu.entity.OriginFactory;

public interface MailRecordService {
	public List<Map<String, Object>> getAllData(Page page);

	public int getAllCounts();
	
	public boolean insert(HttpServletRequest request);

	public boolean update(HttpServletRequest request);

	public boolean updateType(MailRecord factory);

	public boolean delete(int id);
}
