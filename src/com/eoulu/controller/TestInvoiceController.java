package com.eoulu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.service.InvoiceService;
import com.eoulu.service.impl.InvoiceServiceImpl;

public class TestInvoiceController {

	public static void main(String[] args) {
	String classify = "Applicant";
	String parameter = "A";
//	String classify1 = "合同名称";
//	String classify2 = "发票签订时间";
//	String parameter1 = "2";
//	String parameter2 = "2";
	InvoiceService service = new InvoiceServiceImpl();
	Page page = new Page();
	int a = service.getCountByClassify(classify, parameter);
	
	
//	int b = service.getCountByClassifyTime(classify, start_time1, end_time1);
	//int c = service.getCountByClassify(classify1, parameter1, classify2, parameter2);
	
	System.out.println(a);
	page.setCurrentPage(1);
	page.setRows(10);
	page.setRecordCounts(a);
	List<Map<String,Object>> ls = service.getInvoiceByPageInOne(classify, parameter, page);
//	List<Map<String,Object>> ls = service.getInvoiceByTime(classify, start_time1, end_time1, page);
//	List<Map<String,Object>> ls = service.getInvoiceByPageInTwo(classify1, parameter1, classify2, parameter2, page);
	System.out.println(ls.size());
	System.out.println(ls.get(1));
	}

}
