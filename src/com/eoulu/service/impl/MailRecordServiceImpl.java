package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.MailRecordDao;
import com.eoulu.entity.MailRecord;
import com.eoulu.service.MailRecordService;

public class MailRecordServiceImpl implements MailRecordService{

	@Override
	public List<Map<String, Object>> getAllData(Page page) {
		return new MailRecordDao().getAllData(page);
	}

	@Override
	public int getAllCounts() {
		
		return new MailRecordDao().getAllCounts();
	}

	@Override
	public boolean update(HttpServletRequest request) {

		String to = request.getParameter("ToEmail");
		String copy = request.getParameter("CopyTo");
		String type = request.getParameter("Type");
		String name = request.getParameter("NickName");;
		String quoteID = request.getParameter("QuoteID");
		String id  = request.getParameter("ID");
		
		MailRecord record = new MailRecord();
		record.setCopyTo(copy);
		record.setID(Integer.parseInt(id));
		record.setNickName(name);
		record.setQuoteID(Integer.parseInt(quoteID));
		record.setToEmail(to);
		record.setType(type);
		
		return new MailRecordDao().update(record);
	}

	@Override
	public boolean updateType(MailRecord factory) {
		return new MailRecordDao().updateType(factory);
	}

	@Override
	public boolean delete(int id) {
		return new MailRecordDao().delete(id);
	}

	@Override
	public boolean insert(HttpServletRequest request) {
		String to = request.getParameter("ToEmail");
		String copy = request.getParameter("CopyTo");
		String type = request.getParameter("Type");
		String name = request.getParameter("NickName");;
		String quoteID = request.getParameter("QuoteID");
		
		MailRecord record = new MailRecord();
		record.setCopyTo(copy);
		record.setNickName(name);
		record.setQuoteID(Integer.parseInt(quoteID));
		record.setToEmail(to);
		record.setType(type);
		return  new MailRecordDao().insert(record);
	}

}
