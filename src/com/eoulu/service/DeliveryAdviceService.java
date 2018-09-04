package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.entity.DeliveryAdvice;

public interface DeliveryAdviceService {
	public Map<String,Object> getAdvice(int quoteID);
	
	public String opreate(HttpServletRequest request);
	
	public String getContractFile(int quoteID);

}
