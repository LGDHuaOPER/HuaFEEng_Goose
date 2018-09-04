package com.eoulu.syn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.dao.QuoteSystemDao;
import com.eoulu.util.ConventerToPDFUtil;
import com.eoulu.util.Java2Word;

public class POAsEmailFilePDF {

	private Lock lock = new ReentrantLock();// 锁对象

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, String> exportCascadePOFile(HttpServletRequest request) {
		lock.lock();// 得到锁
		long time1 = System.currentTimeMillis();
		Map<String, String> result = new HashMap<String, String>();
		String loadUrl = "";
		try {
			QuoteSystemDao dao = new QuoteSystemDao();
			int id = Integer.parseInt(request.getParameter("QuoteID"));
			String user = request.getSession().getAttribute("user") == null ? ""
					: request.getSession().getAttribute("user").toString();
			if(user.endsWith("@eoulu.com")){
				int index = user.indexOf("@");
				user = user.substring(0, index);	
			}
			String email = request.getSession().getAttribute("email") == null ? ""
					: request.getSession().getAttribute("email").toString();
			List<Map<String, Object>> ls = dao.getQuoteCascadePO(id);
			List<Map<String, Object>> part = dao.getCascadeTemp(id);
			result.put("POID", ls.get(1).get("ID").toString());
			Java2Word word = new Java2Word();
			String number = "";
			HashMap data = new HashMap();
			String endOne = "";
			if (ls.size() > 1) {
				Map<String, Object> map = ls.get(1);
				number = map.get("Number") == null ? "" : map.get("Number").toString();
				String Ref = map.get("RefNO") == null ? "" : map.get("RefNO").toString();
				String version = map.get("Version") == null ? "" : map.get("Version").toString();
				String forOne = map.get("ForwarderOne") == null ? "" : map.get("ForwarderOne").toString();
				String forTwo = map.get("ForwarderTwo") == null ? "" : map.get("ForwarderTwo").toString();
				String forThree = map.get("ForwarderThree") == null ? "" : map.get("ForwarderThree").toString();
				String forFour = map.get("ForwarderFour") == null ? "" : map.get("ForwarderFour").toString();
				String shipOne = map.get("ShipCompany") == null ? "" : map.get("ShipCompany").toString();
				String shipTwo = map.get("ShipAddr") == null ? "" : map.get("ShipAddr").toString();
				String shipThree = map.get("ShipTel") == null ? "" : map.get("ShipTel").toString();
				String shipFour = map.get("ShipAttn") == null ? "" : map.get("ShipAttn").toString();
				endOne = map.get("EndCompany") == null ? "" : map.get("EndCompany").toString();
				String endTwo = map.get("EndAddr") == null ? "" : map.get("EndAddr").toString();
				String endThree = map.get("ContactPerson") == null ? "" : map.get("ContactPerson").toString();
				String endFour = map.get("EndTel") == null ? "" : map.get("EndTel").toString();
				String DeliveryTerm = map.get("DeliveryTerm") == null ? "" : map.get("DeliveryTerm").toString();
				String ShippingMark = map.get("ShippingMark") == null ? "" : map.get("ShippingMark").toString();
				String ContractNO = map.get("ContractNO") == null ? "" : map.get("ContractNO").toString();
				String ShipmentPort = map.get("ShipmentPort") == null ? "" : map.get("ShipmentPort").toString();
				String SubTotal = map.get("SubTotal") == null ? "" : map.get("SubTotal").toString();
				String Discounted = map.get("Discounted") == null ? "" : map.get("Discounted").toString();
				String FinalTotal = map.get("FinalTotal") == null ? "" : map.get("FinalTotal").toString();
				System.out.println(endOne);
				data.put("${Numbers}", number);
				data.put("${RefNo}", Ref);
				data.put("${Versions}", version);
				data.put("${ForwarderOne}", forOne);
				data.put("${ForwarderTwo}", forTwo);
				data.put("${ForwarderThree}", forThree);
				data.put("${ForwarderFour}", forFour);
				data.put("${ShipOne}", shipOne);
				data.put("${ShipTwo}", shipTwo);
				data.put("${ShipThree}", shipThree);
				data.put("${ShipFour}", shipFour);
				data.put("${EndOne}", endOne);
				data.put("${EndTwo}", endTwo);
				data.put("${EndThree}", endThree);
				data.put("${EndFour}", endFour);
				data.put("${DeliveryTerm}", DeliveryTerm);
				data.put("${ShippingMark}", ShippingMark);
				data.put("${ContractNO}", ContractNO);
				data.put("${Shipment}", ShipmentPort);
				data.put("${SubTotal}", "$" + SubTotal);
				data.put("${Discounted}", Discounted);
				data.put("${FinalTotal}", "$" + FinalTotal);
				data.put("${Contact}", user);
				data.put("${Email}", email);

			} else {
				data.put("${Numbers}", "");
				data.put("${RefNo}", "");
				data.put("${Versions}", "");
				data.put("${ForwarderOne}", "");
				data.put("${ForwarderTwo}", "");
				data.put("${ForwarderThree}", "");
				data.put("${ForwarderFour}", "");
				data.put("${ShipOne}", "");
				data.put("${ShipTwo}", "");
				data.put("${ShipThree}", "");
				data.put("${ShipFour}", "");
				data.put("${EndOne}", "");
				data.put("${EndTwo}", "");
				data.put("${EndThree}", "");
				data.put("${EndFour}", "");
				data.put("${DeliveryTerm}", "");
				data.put("${ShippingMark}", "");
				data.put("${ContractNO}", "");
				data.put("${Shipment}", "");
				data.put("${SubTotal}", "");
				data.put("${Discounted}", "");
				data.put("${FinalTotal}", "");
				data.put("${Contact}", user);
				data.put("${Email}", email);
			}
			result.put("endOne", endOne);
			result.put("number", number);
			ArrayList table = new ArrayList(part.size());
			String[] fieldName = { "1", "2", "3", "4", "5", "6" };
			table.add(fieldName);
			if (part.size() < 1) {
				String Model = "";
				String Description = "";
				String Quantity = "";
				String UnitPrice = "";
				String ExtendedPrice = "";
				String[] field = { "" + 1, Model, Description, Quantity, UnitPrice, ExtendedPrice };
				table.add(field);
			} else {
				for (int i = 1; i < part.size(); i++) {
					String Model = part.get(i).get("Model") == null ? "" : part.get(i).get("Model").toString();
					String Description = part.get(i).get("Description") == null ? ""
							: part.get(i).get("Description").toString();
					String Quantity = part.get(i).get("Quantity") == null ? "" : part.get(i).get("Quantity").toString();
					String UnitPrice = part.get(i).get("UnitPrice") == null ? ""
							: part.get(i).get("UnitPrice").toString();
					String ExtendedPrice = part.get(i).get("ExtendedPrice") == null ? ""
							: part.get(i).get("ExtendedPrice").toString();
					String[] field = { "" + i, Model, Description, Quantity, "$" + UnitPrice, "$" + ExtendedPrice };
					table.add(field);
				}
			}

			data.put("table$2@7", table);
			String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\CascadePO-" + number + ".doc";
			long time2 = System.currentTimeMillis();
			word.toWord("E:/Model/CascadePO-word模板.docx", downLoadUrl, data,"end");
			System.out.println("time cost : " + (System.currentTimeMillis() - time1));
			loadUrl = "down\\CascadePO-" + number + ".doc";
			ConventerToPDFUtil cf = new ConventerToPDFUtil();
			cf.word2pdf(downLoadUrl);
			loadUrl = "down\\" + "CascadePO-" + number + ".pdf";
			result.put("loadUrl", loadUrl);
		} finally {
			lock.unlock();// 释放锁
		}
		return result;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, String> exportCascadePOCompleteFile(HttpServletRequest request) {
		lock.lock();// 得到锁
		long time1 = System.currentTimeMillis();
		Map<String, String> result = new HashMap<String, String>();
		String loadUrl = "";
		try {
			QuoteSystemDao dao = new QuoteSystemDao();
			int id = Integer.parseInt(request.getParameter("QuoteID"));
			String user = request.getSession().getAttribute("user") == null ? ""
					: request.getSession().getAttribute("user").toString();
			if(user.endsWith("@eoulu.com")){
				int index = user.indexOf("@");
				user = user.substring(0, index);	
			}
			String email = request.getSession().getAttribute("email") == null ? ""
					: request.getSession().getAttribute("email").toString();
			List<Map<String, Object>> ls = dao.getQuoteCompleteCascadePO(id);
			List<Map<String, Object>> part = dao.getCascadeCompleteTemp(id);
			result.put("POID", ls.get(1).get("ID").toString());
			Java2Word word = new Java2Word();
			String number = "";
			HashMap data = new HashMap();
			String endOne = "";
			if (ls.size() > 1) {
				Map<String, Object> map = ls.get(1);
				number = map.get("Number") == null ? "" : map.get("Number").toString();
				String Ref = map.get("RefNO") == null ? "" : map.get("RefNO").toString();
				String version = map.get("Version") == null ? "" : map.get("Version").toString();
				String forOne = map.get("ForwarderOne") == null ? "" : map.get("ForwarderOne").toString();
				String forTwo = map.get("ForwarderTwo") == null ? "" : map.get("ForwarderTwo").toString();
				String forThree = map.get("ForwarderThree") == null ? "" : map.get("ForwarderThree").toString();
				String forFour = map.get("ForwarderFour") == null ? "" : map.get("ForwarderFour").toString();
				String shipOne = map.get("ShipCompany") == null ? "" : map.get("ShipCompany").toString();
				String shipTwo = map.get("ShipAddr") == null ? "" : map.get("ShipAddr").toString();
				String shipThree = map.get("ShipTel") == null ? "" : map.get("ShipTel").toString();
				String shipFour = map.get("ShipAttn") == null ? "" : map.get("ShipAttn").toString();
				endOne = map.get("EndCompany") == null ? "" : map.get("EndCompany").toString();
				String endTwo = map.get("EndAddr") == null ? "" : map.get("EndAddr").toString();
				String endThree = map.get("ContactPerson") == null ? "" : map.get("ContactPerson").toString();
				String endFour = map.get("EndTel") == null ? "" : map.get("EndTel").toString();
				String DeliveryTerm = map.get("DeliveryTerm") == null ? "" : map.get("DeliveryTerm").toString();
				String ShippingMark = map.get("ShippingMark") == null ? "" : map.get("ShippingMark").toString();
				String ContractNO = map.get("ContractNO") == null ? "" : map.get("ContractNO").toString();
				String ShipmentPort = map.get("ShipmentPort") == null ? "" : map.get("ShipmentPort").toString();
				String SubTotal = map.get("SubTotal") == null ? "" : map.get("SubTotal").toString();
				String Discounted = map.get("Discounted") == null ? "" : map.get("Discounted").toString();
				String FinalTotal = map.get("FinalTotal") == null ? "" : map.get("FinalTotal").toString();
				
				System.out.println(endOne);
				data.put("${Numbers}", number);
				data.put("${RefNo}", Ref);
				data.put("${Versions}", version);
				data.put("${ForwarderOne}", forOne);
				data.put("${ForwarderTwo}", forTwo);
				data.put("${ForwarderThree}", forThree);
				data.put("${ForwarderFour}", forFour);
				data.put("${ShipOne}", shipOne);
				data.put("${ShipTwo}", shipTwo);
				data.put("${ShipThree}", shipThree);
				data.put("${ShipFour}", shipFour);
				data.put("${EndOne}", endOne);
				data.put("${EndTwo}", endTwo);
				data.put("${EndThree}", endThree);
				data.put("${EndFour}", endFour);
				data.put("${DeliveryTerm}", DeliveryTerm);
				data.put("${ShippingMark}", ShippingMark);
				data.put("${ContractNO}", ContractNO);
				data.put("${Shipment}", ShipmentPort);
				data.put("${SubTotal}", "$" + SubTotal);
				data.put("${Discounted}", Discounted);
				data.put("${FinalTotal}", "$" + FinalTotal);
				data.put("${Contact}", user);
				data.put("${Email}", email);

			} else {
				data.put("${Numbers}", "");
				data.put("${RefNo}", "");
				data.put("${Versions}", "");
				data.put("${ForwarderOne}", "");
				data.put("${ForwarderTwo}", "");
				data.put("${ForwarderThree}", "");
				data.put("${ForwarderFour}", "");
				data.put("${ShipOne}", "");
				data.put("${ShipTwo}", "");
				data.put("${ShipThree}", "");
				data.put("${ShipFour}", "");
				data.put("${EndOne}", "");
				data.put("${EndTwo}", "");
				data.put("${EndThree}", "");
				data.put("${EndFour}", "");
				data.put("${DeliveryTerm}", "");
				data.put("${ShippingMark}", "");
				data.put("${ContractNO}", "");
				data.put("${Shipment}", "");
				data.put("${SubTotal}", "");
				data.put("${Discounted}", "");
				data.put("${FinalTotal}", "");
				data.put("${Contact}", user);
				data.put("${Email}", email);
			}
			result.put("endOne", endOne);
			result.put("number", number);
			ArrayList table = new ArrayList(part.size());
			String[] fieldName = { "1", "2", "3", "4"};
			table.add(fieldName);
			if (part.size() < 1) {
				String Model = "";
				String Description = "";
				String Quantity = "";
				String[] field = { "" + 1, Model, Description, Quantity};
				table.add(field);
			} else {
				for (int i = 1; i < part.size(); i++) {
					String Model = part.get(i).get("Model") == null ? "" : part.get(i).get("Model").toString();
					String Description = part.get(i).get("Description") == null ? ""
							: part.get(i).get("Description").toString();
					String Quantity = part.get(i).get("Quantity") == null ? "" : part.get(i).get("Quantity").toString();
					String[] field = { "" + i, Model, Description, Quantity};
					table.add(field);
				}
			}

			data.put("table$2@7", table);
			String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\CascadePO-" + number + ".doc";
			long time2 = System.currentTimeMillis();
			word.toWord("E:/Model/CascadePO-整机word模板.docx", downLoadUrl, data,"end");
			System.out.println("time cost : " + (System.currentTimeMillis() - time1));
			loadUrl = "down\\CascadePO-" + number + ".doc";
			ConventerToPDFUtil cf = new ConventerToPDFUtil();
			cf.word2pdf(downLoadUrl);
			loadUrl = "down\\" + "CascadePO-" + number + ".pdf";
			result.put("loadUrl", loadUrl);
		} finally {
			lock.unlock();// 释放锁
		}
		return result;
	}
}
