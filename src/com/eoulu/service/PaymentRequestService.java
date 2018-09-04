package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.PaymentRequest;

public interface PaymentRequestService {
	
	public String insert(PaymentRequest request);
	
	public String update(PaymentRequest request);
	
	public List<Map<String, Object>> getDataByPage(Page page);
	
	public int getCounts();
	
	public boolean sendMail(PaymentRequest request);
	
	public boolean updatePayState(int ID);
	
	

}
