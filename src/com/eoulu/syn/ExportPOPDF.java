package com.eoulu.syn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.dao.QuoteSystemDao;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.eoulu.util.ConventerToPDFUtil;
import com.eoulu.util.Java2Word;

public class ExportPOPDF {

	private Lock lock = new ReentrantLock();// 锁对象

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportCascadePO(HttpServletRequest request) {
		lock.lock();// 得到锁
		long time1 = System.currentTimeMillis();

		try {
			QuoteSystemService service = new QuoteSystemServiceImpl();
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
			boolean flag = false;
			String description = "";
			String loadUrl = "";
			List<Map<String, Object>> ls = dao.getQuoteCascadePO(id);
			List<Map<String, Object>> part = dao.getCascadeTemp(id);
			Java2Word word = new Java2Word();
			// 替换word中相关的字段
			HashMap data = new HashMap();
			String number = "";
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
				String endOne = map.get("EndCompany") == null ? "" : map.get("EndCompany").toString();
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
			String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\CascadePO配件-" + number + ".doc";
			word.toWord("E:/Model/CascadePO-word模板.docx", downLoadUrl, data,"end");
			System.out.println("time cost : " + (System.currentTimeMillis() - time1));
			loadUrl = "down\\CascadePO配件-" + number + ".doc";
			ConventerToPDFUtil util = new ConventerToPDFUtil();
			util.word2pdf(downLoadUrl);
			loadUrl = "down\\" + "CascadePO配件-" + number + ".pdf";
			if (flag) {
				LogInfoService log = new LogInfoServiceImpl();
				String JspInfo = "报价系统";

				log.insert(request, JspInfo, description);
			}
			return loadUrl;
		} finally {
			lock.unlock();// 释放锁
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportCascadePOComplete(HttpServletRequest request) {
		lock.lock();// 得到锁
		long time1 = System.currentTimeMillis();

		try {
			QuoteSystemService service = new QuoteSystemServiceImpl();
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
			boolean flag = false;
			String description = "";
			String loadUrl = "";
			List<Map<String, Object>> ls = dao.getQuoteCompleteCascadePO(id);
			List<Map<String, Object>> part = dao.getCascadeCompleteTemp(id);
			Java2Word word = new Java2Word();
			// 替换word中相关的字段
			HashMap data = new HashMap();
			String number = "";
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
				String endOne = map.get("EndCompany") == null ? "" : map.get("EndCompany").toString();
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
			ArrayList table = new ArrayList(part.size());
			String[] fieldName = { "1", "2", "3", "4" };
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
					String[] field = { "" + i, Model, Description, Quantity };
					table.add(field);
				}
			}

			data.put("table$2@7", table);
			String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\CascadePO整机-" + number + ".doc";
			word.toWord("E:/Model/CascadePO-整机word模板.docx", downLoadUrl, data,"end");
			System.out.println("time cost : " + (System.currentTimeMillis() - time1));
			loadUrl = "down\\CascadePO整机-" + number + ".doc";
			ConventerToPDFUtil util = new ConventerToPDFUtil();
			util.word2pdf(downLoadUrl);
			loadUrl = "down\\" + "CascadePO整机-" + number + ".pdf";
			if (flag) {
				LogInfoService log = new LogInfoServiceImpl();
				String JspInfo = "报价系统";

				log.insert(request, JspInfo, description);
			}
			return loadUrl;
		} finally {
			lock.unlock();// 释放锁
		}

	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportOtherPO(HttpServletRequest request) {
		lock.lock();// 得到锁
		long time1 = System.currentTimeMillis();

		try {
			QuoteSystemService service = new QuoteSystemServiceImpl();
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
			String description = "";
			String loadUrl = "";
			HashMap<String, Object> data = new HashMap<>();
			List<Map<String, Object>> ls = dao.getQuoteOtherPO(id);
			List<Map<String, Object>> part = dao.getOtherTemp(id);
			String number = "";
			if (ls.size() > 1) {
				number = ls.get(1).get("Number") == null ? "" : ls.get(1).get("Number").toString();
				String version = ls.get(1).get("Version") == null ? "" : ls.get(1).get("Version").toString();
				String vendorOne = ls.get(1).get("VendorOne") == null ? "" : ls.get(1).get("VendorOne").toString();
				String vendorTwo = ls.get(1).get("VendorTwo") == null ? "" : ls.get(1).get("VendorTwo").toString();
				String vendorThree = ls.get(1).get("VendorThree") == null ? ""
						: ls.get(1).get("VendorThree").toString();
				String vendorFour = ls.get(1).get("VendorFour") == null ? "" : ls.get(1).get("VendorFour").toString();
				String forwarderOne = ls.get(1).get("ForwarderOne") == null ? ""
						: ls.get(1).get("ForwarderOne").toString();
				String forwarderTwo = ls.get(1).get("ForwarderTwo") == null ? ""
						: ls.get(1).get("ForwarderTwo").toString();
				String forwarderThree = ls.get(1).get("ForwarderThree") == null ? ""
						: ls.get(1).get("ForwarderThree").toString();
				String forwarderFour = ls.get(1).get("ForwarderFour") == null ? ""
						: ls.get(1).get("ForwarderFour").toString();
				String ShipOne = ls.get(1).get("ShipCompany") == null ? "" : ls.get(1).get("ShipCompany").toString();
				String ShipTwo = ls.get(1).get("ShipAddr") == null ? "" : ls.get(1).get("ShipAddr").toString();
				String ShipThree = ls.get(1).get("ShipTel") == null ? "" : ls.get(1).get("ShipTel").toString();
				String ShipFour = ls.get(1).get("ShipAttn") == null ? "" : ls.get(1).get("ShipAttn").toString();
				String creditTerm = ls.get(1).get("CreditTerm") == null ? "" : ls.get(1).get("CreditTerm").toString();
				String deliveryTerm = ls.get(1).get("DeliveryTerm") == null ? ""
						: ls.get(1).get("DeliveryTerm").toString();
				String shippingMark = ls.get(1).get("ShippingMark") == null ? ""
						: ls.get(1).get("ShippingMark").toString();
				String contractNO = ls.get(1).get("ContractNO") == null ? "" : ls.get(1).get("ContractNO").toString();
				String shipment = ls.get(1).get("ShipmentPort") == null ? "" : ls.get(1).get("ShipmentPort").toString();
				String subTotal = ls.get(1).get("SubTotal") == null ? "" : ls.get(1).get("SubTotal").toString();
				String discounted = ls.get(1).get("Discounted") == null ? "" : ls.get(1).get("Discounted").toString();
				String finalTotal = ls.get(1).get("FinalTotal") == null ? "" : ls.get(1).get("FinalTotal").toString();

				data.put("${Numbers}", number);
				data.put("${Versions}", version);
				data.put("${VendorOne}", vendorOne);
				data.put("${VendorTwo}", vendorThree);
				data.put("${VendorThree}", vendorTwo);
				data.put("${VendorFour}", vendorFour);
				data.put("${ForwarderOne}", forwarderOne);
				data.put("${ForwarderTwo}", forwarderTwo);
				data.put("${ForwarderThree}", forwarderThree);
				data.put("${ForwarderFour}", forwarderFour);
				data.put("${ShipOne}", ShipOne);
				data.put("${ShipTwo}", ShipTwo);
				data.put("${ShipThree}", ShipThree);
				data.put("${ShipFour}", ShipFour);
				data.put("${CreditTerm}", creditTerm);
				data.put("${DeliveryTerm}", deliveryTerm);
				data.put("${ShippingMark}", shippingMark);
				data.put("${ContractNO}", contractNO);
				data.put("${Shipment}", shipment);
				data.put("${SubTotal}", "$" + subTotal);
				data.put("${Discounted}", discounted);
				data.put("${FinalTotal}", "$" + finalTotal);
				data.put("${Contact}", user);
				data.put(" ${Email}", email);
			} else {
				data.put("${Numbers}", "");
				data.put("${Versions}", "");
				data.put("${VendorOne}", "");
				data.put("${VendorTwo}", "");
				data.put("${VendorThree}", "");
				data.put("${VendorFour}", "");
				data.put("${ForwarderOne}", "");
				data.put("${ForwarderTwo}", "");
				data.put("${ForwarderThree}", "");
				data.put("${ForwarderFour}", "");
				data.put("${ShipOne}", "");
				data.put("${ShipTwo}", "");
				data.put("${ShipThree}", "");
				data.put("${ShipFour}", "");
				data.put("${CreditTerm}", "");
				data.put("${DeliveryTerm}", "");
				data.put("${ShippingMark}", "");
				data.put("${ContractNO}", "");
				data.put("${Shipment}", "");
				data.put("${SubTotal}", "");
				data.put("${Discounted}", "");
				data.put("${FinalTotal}", "");
				data.put("${Contact}", user);
				data.put(" ${Email}", email);
			}
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
			Java2Word word = new Java2Word();
			String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\(USD)OtherSupplierPO-" + number
					+ ".doc";
			word.toWord("E:/Model/其他供应商PO-美金word模板.docx", downLoadUrl, data,"end");
			System.out.println("time cost : " + (System.currentTimeMillis() - time1));
			loadUrl = "down\\(USD)OtherSupplierPO-" + number + ".doc";
			ConventerToPDFUtil util = new ConventerToPDFUtil();
			util.word2pdf(downLoadUrl);
			loadUrl = "down\\" + "(USD)OtherSupplierPO-" + number + ".pdf";
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "报价系统";
			log.insert(request, JspInfo, description);
			return loadUrl;
		} finally {
			lock.unlock();// 释放锁
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportOtherRMBPO(HttpServletRequest request) {
		lock.lock();// 得到锁
		long time1 = System.currentTimeMillis();
		try {
			QuoteSystemService service = new QuoteSystemServiceImpl();
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
			// String image = req.getServletContext().getRealPath("/") +
			// "image\\EOULUlogo.png";
			// String path = req.getServletContext().getRealPath("/") + "down\\" +
			// "其他供应商人民币PO.xlsx";
			// URLDecoder.decode(path, "UTF-8");
			// boolean flag = service.exportOtherRMBPOExcel(id,
			// path,image,user,email);
			// System.out.println(flag);
			// String loadUrl = "down\\" + "其他供应商人民币PO.xlsx";
			// URLDecoder.decode(loadUrl, "UTF-8");
			HashMap<String, Object> data = new HashMap<>();
			List<Map<String, Object>> ls = dao.getQuoteOtherSupplierPO(id);
			List<Map<String, Object>> part = dao.getOtherSupplierTemp(id);
			String number = "";
			if (ls.size() > 1) {
				 number = ls.get(1).get("Number") == null ? "" : ls.get(1).get("Number").toString();
				String version = ls.get(1).get("Version") == null ? "" : ls.get(1).get("Version").toString();
				String vendorOne = ls.get(1).get("VendorOne") == null ? "" : ls.get(1).get("VendorOne").toString();
				String vendorTwo = ls.get(1).get("VendorTwo") == null ? "" : ls.get(1).get("VendorTwo").toString();
				String vendorThree = ls.get(1).get("VendorThree") == null ? "" : ls.get(1).get("VendorThree").toString();
				String vendorFour = ls.get(1).get("VendorFour") == null ? "" : ls.get(1).get("VendorFour").toString();
				String VendorFive = ls.get(1).get("VendorFive") == null ? "" : ls.get(1).get("VendorFive").toString();
				String BillContact = ls.get(1).get("BillContact") == null ? "" : ls.get(1).get("BillContact").toString();
				String BillEmail = ls.get(1).get("BillEmail") == null ? "" : ls.get(1).get("BillEmail").toString();
				String ShipThree = ls.get(1).get("ShipTel") == null ? "" : ls.get(1).get("ShipTel").toString();
				String ShipFour = ls.get(1).get("ShipAttn") == null ? "" : ls.get(1).get("ShipAttn").toString();
				String creditTerm = ls.get(1).get("CreditTerm") == null ? "" : ls.get(1).get("CreditTerm").toString();
				String deliveryTerm = ls.get(1).get("DeliveryTerm") == null ? "" : ls.get(1).get("DeliveryTerm").toString();
				String shippingMark = ls.get(1).get("ShippingMark") == null ? "" : ls.get(1).get("ShippingMark").toString();
				String contractNO = ls.get(1).get("ContractNO") == null ? "" : ls.get(1).get("ContractNO").toString();
				String shipment = ls.get(1).get("ShipmentPort") == null ? "" : ls.get(1).get("ShipmentPort").toString();
				String subTotal = ls.get(1).get("SubTotal") == null ? "" : ls.get(1).get("SubTotal").toString();
				String discounted = ls.get(1).get("Discounted") == null ? "" : ls.get(1).get("Discounted").toString();
				String finalTotal = ls.get(1).get("FinalTotal") == null ? "" : ls.get(1).get("FinalTotal").toString();

				data.put("${Numbers}", number);
				data.put("${Versions}", version);
				data.put("${VendorOne}", vendorOne);
				data.put("${VendorTwo}", vendorTwo);
				data.put("${VendorThree}", vendorThree);
				data.put("${VendorFour}", vendorFour);
				data.put("${VendorFive}", VendorFive);
				data.put("${BillContact}", BillContact);
				data.put("${BillEmail}", BillEmail);
				data.put("${ShipTel}", ShipThree);
				data.put("${ShipContact}", ShipFour);
				data.put("${CreditTerm}", creditTerm);
				data.put("${DeliveryTerm}", deliveryTerm);
				data.put("${ShippingMark}", shippingMark);
				data.put("${ContractNO}", contractNO);
				data.put("${Shipment}", shipment);
				data.put("${SubTotal}", "$" + subTotal);
				data.put("${Discounted}", discounted);
				data.put("${FinalTotal}", "$" + finalTotal);
				data.put("${Contact}", user);
				data.put(" ${Email}", email);
			} else {
				data.put("${Numbers}", "");
				data.put("${Versions}", "");
				data.put("${VendorOne}", "");
				data.put("${VendorTwo}", "");
				data.put("${VendorThree}", "");
				data.put("${VendorFour}", "");
				data.put("${VendorFive}", "");
				data.put("${BillContact}", "");
				data.put("${BillEmail}", "");
				data.put("${ShipTel}", "");
				data.put("${ShipContact}", "");
				data.put("${CreditTerm}", "");
				data.put("${DeliveryTerm}", "");
				data.put("${ShippingMark}", "");
				data.put("${ContractNO}", "");
				data.put("${Shipment}", "");
				data.put("${SubTotal}", "");
				data.put("${Discounted}", "");
				data.put("${FinalTotal}", "");
				data.put("${Contact}", user);
				data.put(" ${Email}", email);
			}
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
					String UnitPrice = part.get(i).get("UnitPrice") == null ? "" : part.get(i).get("UnitPrice").toString();
					String ExtendedPrice = part.get(i).get("ExtendedPrice") == null ? ""
							: part.get(i).get("ExtendedPrice").toString();
					String[] field = { "" + i, Model, Description, Quantity, "$" + UnitPrice, "$" + ExtendedPrice };
					table.add(field);
				}
			}

			data.put("table$2@7", table);
			Java2Word word = new Java2Word();
			String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\(RMB)OtherSupplierPO"+number+".doc";
			word.toWord("E:/Model/其他供应商PO-人民币word模板.docx", downLoadUrl, data,"end");
			String loadUrl = "down\\(RMB)OtherSupplierPO"+number+".doc";
			ConventerToPDFUtil util = new ConventerToPDFUtil();
			util.word2pdf(downLoadUrl);
			loadUrl = "down\\" + "(RMB)OtherSupplierPO"+number+".pdf";
			
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "报价系统";
			String description = "下载-其他供应商PO-人民币.pdf" + id;
			log.insert(request, JspInfo, description);
			return loadUrl;
		} finally {
			lock.unlock();// 释放锁
		}

	}

}
