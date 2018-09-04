package com.eoulu.syn;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.util.Java2Word;
import com.eoulu.util.Word2Pdf;

public class ExportQuotePDF {
	
	

	private Lock lock = new ReentrantLock();// 锁对象
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportCompleteRMB(HttpServletRequest request) {
		lock.lock();// 得到锁
		for(int i=0; i <20;i++){
			System.out.println("i--"+i);
		}
		long time1 = System.currentTimeMillis();

		try {
			String versions = request.getParameter("Versions");
			String datesent = request.getParameter("Datesent");
			String number = request.getParameter("Number");
			String customerCompany = request.getParameter("CustomerCompany");
			String customerName = request.getParameter("CustomerName");
			String customerTel = request.getParameter("CustomerTel");
			String customerMail = request.getParameter("CustomerMail");
			String staffName = request.getParameter("StaffName");
			String staffTel = request.getParameter("StaffTel");
			String staffMail = request.getParameter("StaffMail");
			String fax = request.getParameter("Fax");
			System.out.println(customerName);
			String leadTime = request.getParameter("LeadTime");
			String payment = request.getParameter("Payment");
			String valid = request.getParameter("Valid");
			
			String subTotal = request.getParameter("SubTotal");
			String gift = request.getParameter("Gift");
			String giftTotal = request.getParameter("GiftTotal");
			String shipmentCost = request.getParameter("ShipmentCost");
			String finalTotal = request.getParameter("FinalTotal");
			
			String[] parts = request.getParameterValues("Parts[]");
			String[] description = request.getParameterValues("Description[]");
			String[] qty = request.getParameterValues("Qty[]");
			
			Java2Word word = new Java2Word();
			
			
			 //替换word中相关的字段
	        HashMap data = new HashMap();
	        data.put("${Number}",number);
	        data.put("${Date}",datesent);
	        data.put("${Version}",versions);
	        data.put("${Customers}",customerCompany);
	        data.put("${CustomerContact}",customerName);
	        data.put("${CustomerTel}",customerTel);
	        data.put("${CustomerEmail}",customerMail);
	        data.put("${Fax}", fax);
	        data.put("${SalesMan}", staffName);
	        data.put("${Tel}",staffTel);
	        data.put("${Email}", staffMail);
	        BigDecimal a = new BigDecimal(subTotal);  
            DecimalFormat df=new DecimalFormat(",###,##0");
	        data.put("${Total}",df.format(a));
	        if(gift==null && gift.equals("")){
	        	 data.put("${Gift}", "");
	        }else{
	        	 data.put("${Gift}", gift);
	        }
	       if(giftTotal==null && giftTotal.equals("")){
	    	   data.put("${gifts}", "");
	       }else{
	    	   data.put("${gifts}",giftTotal);
	       }
	        a = new BigDecimal(shipmentCost);  
	        data.put("${Insurance}", df.format(a));
	        a = new BigDecimal(finalTotal);  
	        data.put("${FinalTotal}", df.format(a));
	        data.put("${valid}", valid);
	        data.put("${leadTime}",leadTime);
	        data.put("${Payment}",payment);
	        if(parts!=null){
	        	 ArrayList table = new ArrayList(parts.length);
	             String[] fieldName = {"1","2","3","4"};
	             table.add(fieldName);
	             for(int i=0; i<parts.length;i++){
	             	 String[] field = {i+1+"",parts[i],description[i],qty[i]};
	                  table.add(field);
	             }
	             data.put("table$2@2",table);
	        }
	       
	        String downLoadUrl = request.getServletContext().getRealPath("/")+"down\\RMB整机报价单.doc";
	        word.toWord("E:/Model/人民币整机报价单.docx",downLoadUrl,data,"end");
	        System.out.println("time cost : " + (System.currentTimeMillis() - time1));
			String loadUrl = "down\\"  + "RMB整机报价单.doc";
			if (request.getParameter("Type").equals("PDF")) {
				
				Word2Pdf.toPdf(downLoadUrl,request.getServletContext().getRealPath("/")+"down\\" + "RMB整机报价单.pdf" );
				loadUrl = "down\\" + "RMB整机报价单.pdf";
			}
			return loadUrl;
		} finally {
			lock.unlock();// 释放锁
		}

	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportCompleteUSD(HttpServletRequest request) {
		lock.lock();// 得到锁
		for(int i=0; i <20;i++){
			System.out.println("i--"+i);
		}
		long time1 = System.currentTimeMillis();

		try {
			String versions = request.getParameter("Versions");
			String datesent = request.getParameter("Datesent");
			String number = request.getParameter("Number");
			String customerCompany = request.getParameter("CustomerCompany");
			String customerName = request.getParameter("CustomerName");
			String customerTel = request.getParameter("CustomerTel");
			String customerMail = request.getParameter("CustomerMail");
			String fax = request.getParameter("Fax");
			String staffName = request.getParameter("StaffName");
			String staffTel = request.getParameter("StaffTel");
			String staffMail = request.getParameter("StaffMail");
			System.out.println(customerName);
			String leadTime = request.getParameter("LeadTime");
			String payment = request.getParameter("Payment");
			String valid = request.getParameter("Valid");
			String subTotal = request.getParameter("SubTotal");
			String gift = request.getParameter("Gift");
			String giftTotal = request.getParameter("GiftTotal");
			String shipmentCost = request.getParameter("ShipmentCost");
			String finalTotal = request.getParameter("FinalTotal");
			String[] parts = request.getParameterValues("Parts[]");
			String[] description = request.getParameterValues("Description[]");
			String[] qty = request.getParameterValues("Qty[]");
			
			Java2Word word = new Java2Word();
			BigDecimal a = new BigDecimal(subTotal);  
	        DecimalFormat df=new DecimalFormat(",###,##0");
			 //替换word中相关的字段
	        HashMap data = new HashMap();
	        data.put("${Number}",number);
	        data.put("${Date}",datesent);
	        data.put("${Version}",versions);
	        data.put("${Customers}",customerCompany);
	        data.put("${CustomerContact}",customerName);
	        data.put("${CustomerTel}",customerTel);
	        data.put("${CustomerEmail}",customerMail);
	        data.put("${SalesMan}", staffName);
	        data.put("${Tel}",staffTel);
	        data.put("${Email}", staffMail);
	        data.put("${Total}",df.format(a));
	        data.put("${Fax}", fax);
	        if(gift==null && gift.equals("")){
	        	 data.put("${Gift}", "");
	        }else{
	        	 data.put("${Gift}", gift);
	        }
	       if(giftTotal==null && giftTotal.equals("")){
	    	   data.put("${gifts}", "");
	       }else{
	    	   data.put("${gifts}",giftTotal);
	       }
	        a = new BigDecimal(shipmentCost); 
	        data.put("${Insurance}", df.format(a));
	        a = new BigDecimal(finalTotal); 
	        data.put("${FinalTotal}", df.format(a));
	        data.put("${valid}", valid);
	        data.put("${leadTime}",leadTime);
	        data.put("${Payment}",payment);
	        if(parts!=null){
	        	 ArrayList table = new ArrayList(parts.length);
	             String[] fieldName = {"1","2","3","4"};
	             table.add(fieldName);
	             for(int i=0; i<parts.length;i++){
	             	 String[] field = {i+1+"",parts[i],description[i],qty[i]};
	                  table.add(field);
	             }
	             data.put("table$2@2",table);
	        }
	       
	        String downLoadUrl = request.getServletContext().getRealPath("/")+"down\\USD整机报价单.doc";
	        word.toWord("E:/Model/美金整机报价单.docx",downLoadUrl,data,"end");
	        System.out.println("time cost : " + (System.currentTimeMillis() - time1));
			String loadUrl = "down\\"  + "USD整机报价单.doc";

			if (request.getParameter("Type").equals("PDF")) {
				Word2Pdf.toPdf(downLoadUrl, request.getServletContext().getRealPath("/")+"down\\USD整机报价单.pdf");
				loadUrl = "down\\" + "USD整机报价单.pdf";
			}
			return loadUrl;
		} finally {
			lock.unlock();// 释放锁
		}

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportPartRMB(HttpServletRequest request) {
		lock.lock();// 得到锁
		for(int i=0; i <20;i++){
			System.out.println("i--"+i);
		}
		long time1 = System.currentTimeMillis();

		try {
			String versions = request.getParameter("Versions");
			String datesent = request.getParameter("Datesent");
			String number = request.getParameter("Number");
			String customerCompany = request.getParameter("CustomerCompany");
			String customerName = request.getParameter("CustomerName");
			String customerTel = request.getParameter("CustomerTel");
			String customerMail = request.getParameter("CustomerMail");
			String fax = request.getParameter("Fax");
			String staffName = request.getParameter("StaffName");
			String staffTel = request.getParameter("StaffTel");
			String staffMail = request.getParameter("StaffMail");
			System.out.println(customerName);
			String leadTime = request.getParameter("LeadTime");
			String payment = request.getParameter("Payment");
			String valid = request.getParameter("Valid");

			String subTotal = request.getParameter("SubTotal");
			String gift = request.getParameter("Gift");
			String giftTotal = request.getParameter("GiftTotal");
			String shipmentCost = request.getParameter("ShipmentCost");
			String finalTotal = request.getParameter("FinalTotal");

			String[] parts = request.getParameterValues("Parts[]");
			String[] description = request.getParameterValues("Description[]");
			String[] qty = request.getParameterValues("Qty[]");
			String[] each = request.getParameterValues("Each[]");
			String[] extended = request.getParameterValues("Extended[]");

			Java2Word word = new Java2Word();
			BigDecimal a = new BigDecimal(subTotal);  
	        DecimalFormat df=new DecimalFormat(",###,##0");
			// 替换word中相关的字段
			HashMap data = new HashMap();
			data.put("${Number}", number);
			data.put("${Date}", datesent);
			data.put("${Version}", versions);
			data.put("${Customers}", customerCompany);
			data.put("${CustomerContact}", customerName);
			data.put("${CustomerTel}", customerTel);
			data.put("${CustomerEmail}", customerMail);
			data.put("${SalesMan}", staffName);
			data.put("${Tel}", staffTel);
			data.put("${Email}", staffMail);
			data.put("${Total}", df.format(a));
			data.put("${Fax}", fax);
			if (gift == null && gift.equals("")) {
				data.put("${Gift}", "");
			} else {
				data.put("${Gift}", gift);
			}
			if (giftTotal == null && giftTotal.equals("")) {
				data.put("${gifts}", "");
			} else {
				data.put("${gifts}", giftTotal);
			}
			a = new BigDecimal(shipmentCost);
			data.put("${Insurance}", df.format(a));
			a = new BigDecimal(finalTotal);
			data.put("${FinalTotal}", df.format(a));
			data.put("${valid}", valid);
			data.put("${leadTime}", leadTime);
			data.put("${Payment}", payment);
			if (parts!=null) {
				ArrayList table = new ArrayList(parts.length);
				String[] fieldName = { "1", "2", "3", "4", "5", "6" };
				table.add(fieldName);
				for (int i = 0; i < parts.length; i++) {
					String[] field = { i + 1 + "", parts[i], description[i], each[i], qty[i], extended[i] };
					table.add(field);
				}
				data.put("table$2@2", table);
			}

			String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\RMB配件报价单.doc";
			String input = "E:/Model/人民币配件报价单.docx";
			word.toWord(input, downLoadUrl, data,"end");
			System.out.println("time cost : " + (System.currentTimeMillis() - time1));
			String loadUrl = "down\\" + "RMB配件报价单.doc";

			if (request.getParameter("Type").equals("PDF")) {
				Word2Pdf.toPdf(downLoadUrl, request.getServletContext().getRealPath("/")+ "down\\RMB配件报价单.pdf");
				loadUrl = "down\\" + "RMB配件报价单.pdf";
			}
			return loadUrl;
		} finally {
			lock.unlock();// 释放锁
		}

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportPartUSD(HttpServletRequest request) {
		lock.lock();// 得到锁
		for(int i=0; i <20;i++){
			System.out.println("i--"+i);
		}
		long time1 = System.currentTimeMillis();

		try {
			String versions = request.getParameter("Versions");
			String datesent = request.getParameter("Datesent");
			String number = request.getParameter("Number");
			String customerCompany = request.getParameter("CustomerCompany");
			String customerName = request.getParameter("CustomerName");
			String customerTel = request.getParameter("CustomerTel");
			String customerMail = request.getParameter("CustomerMail");
			String fax = request.getParameter("Fax");
			String staffName = request.getParameter("StaffName");
			String staffTel = request.getParameter("StaffTel");
			String staffMail = request.getParameter("StaffMail");
			System.out.println(customerName);
			String leadTime = request.getParameter("LeadTime");
			String payment = request.getParameter("Payment");
			String valid = request.getParameter("Valid");

			String subTotal = request.getParameter("SubTotal");
			String gift = request.getParameter("Gift");
			String giftTotal = request.getParameter("GiftTotal");
			String shipmentCost = request.getParameter("ShipmentCost");
			String finalTotal = request.getParameter("FinalTotal");

			String[] parts = request.getParameterValues("Parts[]");
			String[] description = request.getParameterValues("Description[]");
			String[] qty = request.getParameterValues("Qty[]");
			String[] each = request.getParameterValues("Each[]");
			String[] extended = request.getParameterValues("Extended[]");

			Java2Word word = new Java2Word();
			BigDecimal a = new BigDecimal(subTotal);  
	        DecimalFormat df=new DecimalFormat(",###,##0");
			// 替换word中相关的字段
			HashMap data = new HashMap();
			data.put("${Number}", number);
			data.put("${Date}", datesent);
			data.put("${Version}", versions);
			data.put("${Customers}", customerCompany);
			data.put("${CustomerContact}", customerName);
			data.put("${CustomerTel}", customerTel);
			data.put("${CustomerEmail}", customerMail);
			data.put("${SalesMan}", staffName);
			data.put("${Tel}", staffTel);
			data.put("${Email}", staffMail);
			data.put("${Total}", df.format(a));
			data.put("${Fax}", fax);
			if (gift == null && gift.equals("")) {
				data.put("${Gift}", "");
			} else {
				data.put("${Gift}", gift);
			}
			if (giftTotal == null && giftTotal.equals("")) {
				data.put("${gifts}", "");
			} else {
				data.put("${gifts}", giftTotal);
			}
			a = new BigDecimal(shipmentCost);
			data.put("${Insurance}", df.format(a));
			a = new BigDecimal(finalTotal);
			data.put("${FinalTotal}", df.format(a));
			data.put("${valid}", valid);
			data.put("${leadTime}", leadTime);
			data.put("${Payment}", payment);
			if(parts!=null){
				ArrayList table = new ArrayList(parts.length);
				String[] fieldName = { "1", "2", "3", "4", "5", "6" };
				table.add(fieldName);
				for (int i = 0; i < parts.length; i++) {
					String[] field = { i + 1 + "", parts[i], description[i], each[i], qty[i], extended[i] };
					table.add(field);
				}
				data.put("table$2@2", table);
			}
			
			String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\USD配件报价单.doc";
			word.toWord("E:/Model/美金配件报价单.docx", downLoadUrl, data,"end");

			String loadUrl = "down\\" + "USD配件报价单.doc";

			if(request.getParameter("Type").equals("PDF")){
				Word2Pdf.toPdf(downLoadUrl,request.getServletContext().getRealPath("/") + "down\\USD配件报价单.pdf");
				loadUrl = "down\\" + "USD配件报价单.pdf";
			}
			System.out.println("time cost : " + (System.currentTimeMillis() - time1));
			
			return loadUrl;
		} finally {
			lock.unlock();// 释放锁
		}

	}
	
	
	
}
