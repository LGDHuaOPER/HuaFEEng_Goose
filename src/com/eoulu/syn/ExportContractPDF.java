package com.eoulu.syn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.eoulu.util.ConventerToPDFUtil;
import com.eoulu.util.Java2Word;

public class ExportContractPDF {
	private Lock lock = new ReentrantLock();// 锁对象
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportContractRMB(HttpServletRequest request) {
		lock.lock();// 得到锁
		for(int i=0; i <20;i++){
			System.out.println("i--"+i);
		}
		long time1 = System.currentTimeMillis();

		try {
			String ContractNO = request.getParameter("ContractNO");
			String Year = request.getParameter("Year");
			String Month = request.getParameter("Month");
			String Day = request.getParameter("Day");
			String CustomerCompany = request.getParameter("CustomerCompany");
			String CustomerTel = request.getParameter("CustomerTel");
			String CustomerFax = request.getParameter("CustomerFax");
			String CustomerContact = request.getParameter("CustomerContact");
			String SecondContact = request.getParameter("SecondContact");
			String Total = request.getParameter("Total");
			String BigTotal = request.getParameter("BigTotal");
			String Address = request.getParameter("Address");

			String[] parts = request.getParameterValues("Parts[]");
			String[] description = request.getParameterValues("Description[]");
			String[] qty = request.getParameterValues("Qty[]");
			String[] each = request.getParameterValues("Each[]");
			String[] extended = request.getParameterValues("Extended[]");

			Java2Word word = new Java2Word();
			// 替换word中相关的字段
			HashMap data = new HashMap();
			data.put("${ContractNO}", ContractNO);
			data.put("${Year}", Year);
			data.put("${Month}", Month);
			data.put("${Day}", Day);
			data.put("${Company}", CustomerCompany);
			data.put("${Tel}", CustomerTel);
			data.put("${Fax}", CustomerFax);
			data.put("${FirstContact}", CustomerContact);
			data.put("${Contact}", SecondContact);
			data.put("${Total}", Total);
			data.put("${Total2}", Total);
			data.put("${BigTotal}", BigTotal);
			data.put("${Total3}", Total);
			data.put("${BigTotal2}", BigTotal);
			data.put("${ReceivingContact}", CustomerContact);
			data.put("${ReceivingTel}", CustomerTel);
			data.put("${Address}", Address);
			if (parts != null) {
				ArrayList table = new ArrayList(parts.length);
				String[] fieldName = { "1", "2", "3", "4", "5", "6" };
				table.add(fieldName);
				for (int i = 0; i < parts.length; i++) {
					String[] field = { i + 1 + "", parts[i], description[i], qty[i], each[i], extended[i] };
					table.add(field);
				}
				data.put("table$2@3", table);
			}

			String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\EOULU苏州人民币合同.doc";

			word.toWord("E:/Model/EOULU苏州人民币合同范本.doc", downLoadUrl, data,"end");

			String loadUrl = "down\\" + "EOULU苏州人民币合同.doc";
			if (request.getParameter("Type").equals("PDF")) {
				ConventerToPDFUtil util = new ConventerToPDFUtil();
				util.word2pdf(downLoadUrl);
				loadUrl = "down\\" + "EOULU苏州人民币合同.pdf";
			}
			System.out.println("time cost : " + (System.currentTimeMillis() - time1));
			return loadUrl;
		} finally {
			lock.unlock();// 释放锁
		}

	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportContractUSD(HttpServletRequest request) {
		lock.lock();// 得到锁
		long time1 = System.currentTimeMillis();
		try {
			QuoteSystemService service = new QuoteSystemServiceImpl();
			String ContractNO = request.getParameter("ContractNO");
			String Date = request.getParameter("Date");
			String Versions = request.getParameter("Versions");
			String Buyer = request.getParameter("Buyer");
			String Buyer2 = request.getParameter("Buyer2");
			String Add = request.getParameter("Add");
			String Tel = request.getParameter("Tel");
			String Fax = request.getParameter("Fax");
			String EndUser = request.getParameter("EndUser");
			String EndUser2 = request.getParameter("EndUser2");
			String Total = request.getParameter("Total");
			String BigTotal = request.getParameter("BigTotal");
			String CIP = request.getParameter("CIP");
			String Shipment = request.getParameter("Shipment");
			String Shipment2 = request.getParameter("Shipment2");
			String Destination = request.getParameter("Destination");
			String Destination2 = request.getParameter("Destination2");
			String ShipmentTime = request.getParameter("ShipmentTime");
			String ShipmentTime2 = request.getParameter("ShipmentTime2");
			String ShippingMark = request.getParameter("ShippingMark");
			String Manufacturer = request.getParameter("Manufacturer");
			String Manufacturer2 = request.getParameter("Manufacturer2");
			String Payment = request.getParameter("Payment");
			String Payment2 = request.getParameter("Payment2");
			String PayTime = request.getParameter("PayTime");
			String[] parts = request.getParameterValues("Parts[]");
			String[] description = request.getParameterValues("Description[]");
			String[] qty = request.getParameterValues("Qty[]");
			String[] each = request.getParameterValues("Each[]");
			String[] extended = request.getParameterValues("Extended[]");
			String[] unit = request.getParameterValues("unit[]");
			 
			Java2Word word = new Java2Word();
			 //替换word中相关的字段
	        HashMap data = new HashMap();
	        data.put("${ContractNO}",ContractNO);
	        data.put("${Date}",Date);
	        data.put("${Versions}",Versions);
	        data.put("${Buyer1}",Buyer);
	        data.put("${Buyer2}",Buyer2);
	        data.put("${Buyer3}",Add);
	        data.put("${Tel}",Tel);
	        data.put("${Fax}", Fax);
	        data.put("${EndUser}",EndUser);
	        data.put("${EndUser2}",EndUser2);
	        data.put("${BigTotal}",BigTotal);
	        data.put("${CIP}", CIP);
	        data.put("${Total}",Total);
	        data.put("${Shipment}",Shipment);
	        data.put("${Shipment2}",Shipment2);
	        data.put("${ShipmentTime}",ShipmentTime);
	        data.put("${ShipmentTime2}",ShipmentTime2);
	        data.put("${Manufacturer}",Manufacturer);
	        data.put("${Manufacturer2}",Manufacturer2);
	        data.put("${Destination}",Destination);
	        data.put("${Destination2}",Destination2);
	        data.put("${ShippingMark}",ShippingMark);
	        data.put("${CIP2}",CIP);
	        data.put("${Payment}",Payment);
	        data.put("${Payment2}",Payment);
	        data.put("${PayTime}",PayTime);
	        if(parts!=null){
	        	  ArrayList table = new ArrayList(parts.length);
	              String[] fieldName = {"1","2","3","4","5","6","7"};
	              table.add(fieldName);
	              for(int i=0; i<parts.length;i++){
	              	 String[] field = {"1-"+(i+1),description[i],parts[i],unit[i],qty[i],each[i],extended[i]};
	                   table.add(field);
	              }
	              data.put("table$3@2",table);
	        }
	      
	        String downLoadUrl = request.getServletContext().getRealPath("/")+"down\\EOULU美金合同.doc";
	        word.toWord("E:/Model/EOULU美金合同范本-171221.doc",downLoadUrl,data,"end");
	        System.out.println("time cost : " + (System.currentTimeMillis() - time1));
			String loadUrl = "down\\"  + "EOULU美金合同.doc";
			if (request.getParameter("Type").equals("PDF")) {
				ConventerToPDFUtil util = new ConventerToPDFUtil();
				util.word2pdf(downLoadUrl);
				loadUrl = "down\\" + "EOULU美金合同.pdf";
			}
			return loadUrl;
		} finally {
			lock.unlock();// 释放锁
		}

	}
	
	
}
