package com.eoulu.service.impl;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eoulu.commonality.Page;
import com.eoulu.constant.ModelClassification;
import com.eoulu.dao.QuoteSystemDao;
import com.eoulu.entity.CascadePOMail;
import com.eoulu.entity.CommodityContracts;
import com.eoulu.entity.CommodityTemp;
import com.eoulu.entity.CommodityUSDContracts;
import com.eoulu.entity.QuoteCascadePO;
import com.eoulu.entity.QuoteCascadeTemp;
import com.eoulu.entity.QuoteContractRMB;
import com.eoulu.entity.QuoteContractUSD;
import com.eoulu.entity.QuoteDelivery;
import com.eoulu.entity.QuoteOtherPO;
import com.eoulu.entity.QuoteOtherSupplierPO;
import com.eoulu.entity.QuoteOtherSupplierTemp;
import com.eoulu.entity.QuoteOtherTemp;
import com.eoulu.entity.QuoteRequest;
import com.eoulu.entity.QuoteSystem;
import com.eoulu.entity.QuoteSystemModel;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.util.DBUtil;
import com.eoulu.util.OrderWrapper;
import com.eoulu.util.POExcelUtil;
import com.eoulu.util.SendMailUtil;
import com.eoulu.util.StockListExcelUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QuoteSystemServiceImpl implements QuoteSystemService {

	public static final Map<String, Object> classify_MAP;

	static {
		Map<String, Object> map = new HashMap<>();
		map.put("商品名称", "CommodityName");
		map.put("型号规格", "Model");
		map.put("单位", "Unit");
		map.put("交货期", "DeliveryPeriod");
		map.put("商品产地", "ProducingArea");
		map.put("成本单价", "CostPrice");
		map.put("折扣前成本", "DiscountCost");
		map.put("商品售价一", "SellerPriceOne");
		map.put("商品售价二", "SellerPriceTwo");
		map.put("商品售价三", "SellerPriceThree");
		map.put("主要供应商", "Supplier");
		map.put("过期价格", "CostPrice");

		map.put("客户名称", "CustomerCompany");
		map.put("联系人", "CustomerName");
		map.put("联络电话", "CustomerTel");
		map.put("销售", "StaffName");
		map.put("报价日期", "Datesent");
		map.put("报价单号", "Number");
		map.put("产品型号", "Model");
		map.put("备货请求", "MailStatus");

		classify_MAP = map;
	}

	@Override
	public List<Map<String, Object>> getCustomerInfo() {
		return new QuoteSystemDao().getCustomerInfo();
	}

	@Override
	public List<Map<String, Object>> getCustomerInfoByColumn(String classify, String param) {
		List<Map<String, Object>> ls = null;
		param = "%" + param + "%";
		if (classify.equals("客户名称")) {
			ls = new QuoteSystemDao().getCustomerInfoByCustomerName(param);
		}
		if (classify.equals("联系人")) {
			ls = new QuoteSystemDao().getCustomerInfoByContact(param);
		}
		return ls;
	}

	@Override
	public List<Map<String, Object>> getStaffInfo(String department) {
		return new QuoteSystemDao().getStaffInfo(department);
	}

	@Override
	public List<Map<String, Object>> getStaffInfoByColumn(String classify, String param) {
		List<Map<String, Object>> ls = null;
		param = "%" + param + "%";
		ls = new QuoteSystemDao().getStaffInfoByStaffName(param);
		return ls;
	}

	@Override
	public List<Map<String, Object>> getCommodityInfo() {
		return new QuoteSystemDao().getCommodityInfo();
	}

	@Override
	public List<Map<String, Object>> getCommodityInfoByModel(String model) {
		return new QuoteSystemDao().getCommodityInfoByModel(model);
	}
	
	public List<Map<String, Object>> getCommodityModel(String model){
		return new QuoteSystemDao().getCommodityModel(model);
	}

	@Override
	public List<Map<String, Object>> getAllQuoteInfo(Page page, String classify1, String param1, String classify2,
			String param2) {
		List<Map<String, Object>> ls = null;
		if (classify1 == null) {
			ls = new QuoteSystemDao().getQuoteSystem(page, null, null, null, null);
		} else {
			String parameter1 = classify_MAP.get(classify1).toString();
			String content1 = "";

			if (parameter1.equals("Datesent")) {
				content1 = param1;
			} else {
				content1 = "%" + param1 + "%";
			}
			if (classify2 != null) {
				String parameter2 = classify_MAP.get(classify2).toString();
				String content2 = "";
				if (parameter2.equals("Datesent")) {
					content2 = param2;
				} else {
					content2 = "%" + param2 + "%";
				}
				ls = new QuoteSystemDao().getQuoteSystem(page, parameter1, content1, parameter2, content2);
			} else {
//				System.out.println("Impl:" + content1);
				ls = new QuoteSystemDao().getQuoteSystem(page, parameter1, content1, null, null);
			}

		}

		return ls;
	}

	@Override
	public int getAllCounts(String classify1, String param1, String classify2, String param2) {
		int count = 0;
		if (classify1 == null) {
			count = new QuoteSystemDao().getAllCounts(null, null, null, null);
		} else {
			String parameter1 = classify_MAP.get(classify1).toString();
			String content1 = "";

			if (classify1.equals("Datesent")) {
				content1 = param1;
			} else {
				content1 = "%" + param1 + "%";
			}
			if (classify2 != null) {
				String parameter2 = classify_MAP.get(classify2).toString();
				String content2 = "";
				if (classify2.equals("Datesent")) {
					content2 = param2;
				} else {
					content2 = "%" + param2 + "%";
				}
				count = new QuoteSystemDao().getAllCounts(parameter1, content1, parameter2, content2);
			} else {
				count = new QuoteSystemDao().getAllCounts(parameter1, content1, null, null);
			}

		}
		return count;
	}

	/**
	 * 报价系统中合同、备货清单、PO的商品添加
	 * @param id 商品管理的ID
	 * @param quoteID  报价信息的ID
	 * @return
	 */
	public boolean getCommodity(int id, int quoteID) {
		boolean flag = false;
		QuoteSystemDao dao = new QuoteSystemDao();
		List<Map<String, Object>> ls = new QuoteSystemDao().getCommodityInfoByID(id, quoteID);//报价单的商品信息
		String commodityModel = ls.get(1).get("Model").toString();
		String remark = ls.get(1).get("Remark").toString();
		String quantity = ls.get(1).get("Qty").toString();
		String unitPrice = ls.get(1).get("UnitPrice").toString();
		String unit = ls.get(1).get("Unit").toString();
		CommodityContracts comm = new CommodityContracts();
		comm.setCommodityModel(commodityModel);
		comm.setRemark(remark);
		comm.setQuantity(Integer.parseInt(quantity));
		comm.setUnitPrice(Double.parseDouble(unitPrice));
		comm.setUnit(unit);
		comm.setQuoteID(quoteID);
		boolean temp1 = dao.insertContractCommodity(comm);//RMB合同模板的商品
		CommodityUSDContracts com = new CommodityUSDContracts();
		com.setCommodityModel(commodityModel);
		com.setRemark(remark);
		com.setQuantity(Integer.parseInt(quantity));
		com.setUnitPrice(Double.parseDouble(unitPrice));
		com.setUnit(unit);
		com.setQuoteID(quoteID);
		boolean temp2 = dao.insertUSDContractCommodity(com);//USD合同模板的商品

		QuoteDelivery delivery = new QuoteDelivery();
		delivery.setModel(commodityModel);
		delivery.setDescription(remark);
		delivery.setQuantity(Integer.parseInt(quantity));
		delivery.setRemarks("");
		delivery.setQuoteID(quoteID);

		boolean temp3 = dao.insertDelivery(delivery);//备货清单模板的商品

		QuoteCascadeTemp temp = new QuoteCascadeTemp();
		temp.setPartID(id);
		temp.setQuoteID(quoteID);
		temp.setType("Parts");
		boolean temp4 = dao.insertCascadeTemp(temp);//Cascade PO配件模板的商品

		QuoteCascadeTemp complete = new QuoteCascadeTemp();
		complete.setPartID(id);
		complete.setQuoteID(quoteID);
		complete.setType("Complete");
		boolean temp7 = dao.insertCascadeTemp(complete);//Cascade PO整机模板的商品
		
		QuoteOtherTemp other = new QuoteOtherTemp();
		other.setPartID(id);
		other.setQuoteID(quoteID);
		boolean temp5 = dao.insertOtherTemp(other);//其他供应商USD模板的商品

		QuoteOtherSupplierTemp rmb = new QuoteOtherSupplierTemp();
		rmb.setPartID(id);
		rmb.setQuoteID(quoteID);
		boolean temp6 = dao.insertOtherSupplierTemp(rmb);//其他供应商RMB模板的商品

		if (temp1 && temp2 && temp3 && temp4 && temp5 && temp6 && temp7) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 报价系统中合同、备货清单的商品修改
	 * 
	 * 注：PO的商品完全取自备货清单
	 * @param id 商品管理的ID
	 * @param quoteID  报价信息的ID
	 * @return
	 */
	public boolean updateCommodity(int id, int quoteID) {
		boolean flag = false;

		QuoteSystemDao dao = new QuoteSystemDao();
		System.out.println("ffff"+id);
		System.out.println("fffffff"+quoteID);
		List<Map<String, Object>> ls = new QuoteSystemDao().getCommodityInfoByID(id, quoteID);
		System.out.println(ls);
		String commodityModel = ls.get(1).get("Model").toString();
		String remark = ls.get(1).get("Remark").toString();
		String quantity = ls.get(1).get("Qty").toString();
		String unitPrice = ls.get(1).get("UnitPrice").toString();
		String unit = ls.get(1).get("Unit").toString();
		CommodityContracts comm = new CommodityContracts();
		comm.setCommodityModel(commodityModel);
		comm.setRemark(remark);
		comm.setQuantity(Integer.parseInt(quantity));
		comm.setUnitPrice(Double.parseDouble(unitPrice));
		comm.setUnit(unit);
		comm.setQuoteID(quoteID);
		boolean temp1 = dao.updateContractCommodity2(comm);//修改RMB合同的商品
		CommodityUSDContracts com = new CommodityUSDContracts();
		com.setCommodityModel(commodityModel);
		com.setRemark(remark);
		com.setQuantity(Integer.parseInt(quantity));
		com.setUnitPrice(Double.parseDouble(unitPrice));
		com.setUnit(unit);
		com.setQuoteID(quoteID);
		boolean temp2 = dao.updateUSDContractCommodity2(com);//修改USD合同的商品
		QuoteDelivery delivery = new QuoteDelivery();
		delivery.setModel(commodityModel);
		delivery.setDescription(remark);
		delivery.setQuantity(Integer.parseInt(quantity));
		delivery.setRemarks("");
		delivery.setQuoteID(quoteID);

		boolean temp3 = dao.upadteDelivery2(delivery);//修改备货清单的商品
		/*
		 * QuoteCascadeTemp temp = new QuoteCascadeTemp(); temp.setPartID(id);
		 * temp.setQuoteID(quoteID); boolean temp4 =
		 * dao.insertCascadeTemp(temp);
		 * 
		 * QuoteOtherTemp other = new QuoteOtherTemp(); other.setPartID(id);
		 * other.setQuoteID(quoteID); boolean temp5 =
		 * dao.insertOtherTemp(other);
		 * 
		 * QuoteOtherSupplierTemp rmb = new QuoteOtherSupplierTemp();
		 * rmb.setPartID(id); rmb.setQuoteID(quoteID); boolean temp6 =
		 * dao.insertOtherSupplierTemp(rmb);
		 */
		if (temp1 && temp2 && temp3) {
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean insert(HttpServletRequest request) {
		QuoteSystemDao dao = new QuoteSystemDao();
		List<String> ls = new ArrayList<String>();
		QuoteSystem quote = new QuoteSystem();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String customerCode = request.getParameter("CustomerCode");
		String customerCompany = request.getParameter("CustomerCompany");
		String customerName = request.getParameter("CustomerName");
		String customerTel = request.getParameter("CustomerTel");
		String customerFax = request.getParameter("CustomerFax");
		String leadTime = request.getParameter("LeadTime");
		String payment = request.getParameter("Payment");
		String staffName = request.getParameter("StaffName");
		String department = request.getParameter("Department");
		String shipmentCost = request.getParameter("ShipmentCost");
		String deliveryWay = request.getParameter("DeliveryWay");
		String exchangeRate = request.getParameter("ExchangeRate");
		String valid = request.getParameter("Valid");
		String currency = request.getParameter("Currency");
		String taxCategories = request.getParameter("TaxCategories");
		String versions = request.getParameter("Versions");
		String datesent = request.getParameter("Datesent");
		String number = request.getParameter("Number");
		Calendar calendar = Calendar.getInstance();
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		int currentYear = calendar.get(Calendar.YEAR);
		int day = calendar.get(Calendar.DATE);
		String date = "" + day;
		if (day < 10) {
			date = "0" + day;
		}
		String year = "" + currentYear;
		year = year.substring(2, 4);
		int count = dao.getTodayCount();
		String c = "-" + count;
		if (count < 10) {
			c = "-0" + count;
		}
		// QU+A024（是业务员的部门及编号）+170321（是17年3月21日）+005（是今天做的第5个报价单）。
		number = "QU" + dao.getStaffCode(staffName) + year + "0" + currentMonth + date + c;
		String staffMail = request.getParameter("StaffMail");
		String staffTel = request.getParameter("StaffTel");
		String customerMail = request.getParameter("CustomerMail");
		String exist = request.getParameter("Exist");
		quote.setCustomerCode(customerCode);
		quote.setCustomerCompany(customerCompany);
		quote.setCustomerName(customerName);
		quote.setCustomerTel(customerTel);
		quote.setCustomerFax(customerFax);
		quote.setLeadTime(leadTime);
		quote.setPayment(payment);
		quote.setStaffName(staffName);
		quote.setDepartment(department);
		quote.setMailStatus("no");
		quote.setCascadeStatus("no");
		quote.setCascadeCompleteStatus("no");
		quote.setOtherStatus("no");
		quote.setOtherRMBStatus("no");
		if (shipmentCost.equals("") || shipmentCost == null) {
			quote.setShipmentCost(0);
		} else {
			quote.setShipmentCost(Double.parseDouble(shipmentCost));
		}
		quote.setDeliveryWay(deliveryWay);
		if (exchangeRate.equals("") || exchangeRate == null) {
			quote.setExchangeRate(0);
		} else {
			quote.setExchangeRate(Double.parseDouble(exchangeRate));
		}
		quote.setValid(valid);
		quote.setCurrency(currency);
		if (taxCategories.equals("") || taxCategories == null) {
			quote.setTaxCategories(0);
		} else {
			quote.setTaxCategories(Double.parseDouble(taxCategories));
		}
		if (versions.equals("") || versions == null) {
			quote.setVersions(0);
		} else {
			quote.setVersions(Double.parseDouble(versions));
		}
		if (datesent.equals("") || datesent == null) {
			quote.setDatesent("0000-00-00");
		} else {
			quote.setDatesent(datesent);
		}
		quote.setNumber(number);
		quote.setStaffMail(staffMail);
		quote.setStaffTel(staffTel);
		quote.setCustomerMail(customerMail);
		String countNO = year + "0" + currentMonth + date + c;
		quote.setCountNO(countNO);

		boolean temp = dao.insert(quote);
		int id = new QuoteSystemDao().getQuoteSystemID(countNO);
		if (temp) {
			ls.add("true");

			if (id != 0) {
				if (exist.equals("yes")) {//如果有添加商品
					CommodityTemp comm = new CommodityTemp();
					String[] commodity = request.getParameterValues("Commodity[]");
					String[] description = request.getParameterValues("Description[]");
					String[] paymentDate = request.getParameterValues("PaymentDate[]");
					String[] orderNO = request.getParameterValues("OrderNO[]");
					String[] qty = request.getParameterValues("Qty[]");
					for (int i = 0; i < commodity.length; i++) {
						comm.setCommodity(Integer.parseInt(commodity[i]));
						comm.setDescription("");
						if (qty[i].equals("") || qty[i] == null) {
							comm.setQty(0);
						} else {
							comm.setQty(Integer.parseInt(qty[i]));
						}
						comm.setPaymentDate("0000-00-00");
						comm.setOrderNO("");
						comm.setQuoteID(id);
						boolean flag = new QuoteSystemDao().insertCommodity(comm);//报价系统的商品
						if (flag) {
							ls.add("true");
						} else {
							ls.add("false");
						}
						if (getCommodity(Integer.parseInt(commodity[i]), id)) {
							ls.add("true");
						} else {
							ls.add("false");
						}
					}
					
				}
				List<Map<String, Object>> ls2 = dao.getTotalPrice(id);
				double total = 0;
				if (ls2.size() > 1) {
					total = Double.parseDouble(ls2.get(1).get("FinalTotal").toString().replaceAll(",", ""));
				}
				QuoteSystemModel model = new QuoteSystemModel();
				model.setFinalTotal(total + Double.parseDouble(shipmentCost));
				model.setGifts("");
				model.setGiftsTotal(0);
				model.setQuoteID(id);
				model.setSubTotal(total);
				model.setType("CompleteUSD");
				dao.insertModel(model,null);//生成总价，在整机USD模板
				
				QuoteContractRMB rmb = new QuoteContractRMB();
				String contractNO = "SUZ-" + (df.format(new Date()).replaceAll("-", ""));
				rmb.setContractNO(contractNO);
				rmb.setCustomerCompany(customerCompany);
				rmb.setCustomerTel(customerTel);
				rmb.setCustomerFax(customerFax);
				rmb.setCustomerContact(customerName);
				rmb.setSecondContact(staffName);
				rmb.setPayment(payment);
				rmb.setLeadTime(leadTime);
				rmb.setQuoteID(id);
				boolean rmbTemp = new QuoteSystemDao().insertRMBContract(rmb);//人民币合同初始生成
				if (rmbTemp) {
					ls.add("true");
				} else {
					ls.add("false");
				}
				QuoteContractUSD usd = new QuoteContractUSD();
				String contractNOUSD = "HK-" + (df.format(new Date()).replaceAll("-", ""));
				usd.setDate(df.format(new Date()));
				usd.setContractNO(contractNOUSD);
				usd.setCustomerCompany(customerCompany);
				usd.setCustomerTel(customerTel);
				usd.setCustomerFax(customerFax);
				usd.setCustomerContact(customerName);
				usd.setPayment(payment);
				usd.setVersion(versions);
				usd.setShippingMark(contractNOUSD);
				usd.setQuoteID(id);
				boolean usdTemp = new QuoteSystemDao().insertUSDContract(usd);//USD合同初始生成
				if (usdTemp) {
					ls.add("true");
				} else {
					ls.add("false");
				}

			}

		} else {
			ls.add("false");
		}
		if (ls.contains("false")) {
			temp = false;
		} else {
			temp = true;
		
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "报价系统";
			String description = "新增-" + customerCompany;
			log.insert(request, JspInfo, description);
		}
		return temp;
	}

	@Override
	public boolean update(HttpServletRequest request) {
		QuoteSystemDao dao = new QuoteSystemDao();
		List<String> ls = new ArrayList<String>();
		QuoteSystem quote = new QuoteSystem();
		int id = Integer.parseInt(request.getParameter("ID"));
		String customerCode = request.getParameter("CustomerCode");
		String customerCompany = request.getParameter("CustomerCompany");
		String customerName = request.getParameter("CustomerName");
		String customerTel = request.getParameter("CustomerTel");
		String customerFax = request.getParameter("CustomerFax");
		String leadTime = request.getParameter("LeadTime");
		String payment = request.getParameter("Payment");
		String staffName = request.getParameter("StaffName");
		String department = request.getParameter("Department");
		String shipmentCost = request.getParameter("ShipmentCost");
		String deliveryWay = request.getParameter("DeliveryWay");
		String exchangeRate = request.getParameter("ExchangeRate");
		String valid = request.getParameter("Valid");
		String currency = request.getParameter("Currency");
		String taxCategories = request.getParameter("TaxCategories");
		String versions = request.getParameter("Versions");
		String datesent = request.getParameter("Datesent");
		String number = request.getParameter("Number");
		String staffMail = request.getParameter("StaffMail");
		String staffTel = request.getParameter("StaffTel");
		String customerMail = request.getParameter("CustomerMail");
		String exist = request.getParameter("Exist");
		quote.setCustomerCode(customerCode);
		quote.setCustomerCompany(customerCompany);
		quote.setCustomerName(customerName);
		quote.setCustomerTel(customerTel);
		quote.setCustomerFax(customerFax);
		quote.setLeadTime(leadTime);
		quote.setPayment(payment);
		quote.setStaffName(staffName);
		quote.setDepartment(department);
		if (shipmentCost.equals("") || shipmentCost == null) {
			quote.setShipmentCost(0);
		} else {
			quote.setShipmentCost(Double.parseDouble(shipmentCost));
		}
		quote.setDeliveryWay(deliveryWay);
		if (exchangeRate.equals("") || exchangeRate == null) {
			quote.setExchangeRate(0);
		} else {
			quote.setExchangeRate(Double.parseDouble(exchangeRate));
		}
		quote.setValid(valid);
		quote.setCurrency(currency);
		if (taxCategories.equals("") || taxCategories == null) {
			quote.setTaxCategories(0);
		} else {
			quote.setTaxCategories(Double.parseDouble(taxCategories));
		}
		if (versions.equals("") || versions == null) {
			quote.setVersions(0);
		} else {
			quote.setVersions(Double.parseDouble(versions));
		}
		if (datesent.equals("") || datesent == null) {
			quote.setDatesent("0000-00-00");
		} else {
			quote.setDatesent(datesent);
		}
		quote.setNumber(number);
		quote.setStaffMail(staffMail);
		quote.setStaffTel(staffTel);
		quote.setCustomerMail(customerMail);
		quote.setID(id);
		System.out.println("EEEEEEEE"+id);

		boolean temp = dao.update(quote);  
		if (temp) {
			ls.add("true");
			if (exist.equals("yes")) {
				CommodityTemp comm = new CommodityTemp();
				String[] commodity = request.getParameterValues("Commodity[]");
				String[] ids = request.getParameterValues("CommodityID[]");
				String[] description = request.getParameterValues("Description[]");
				String[] paymentDate = request.getParameterValues("PaymentDate[]");
				String[] orderNO = request.getParameterValues("OrderNO[]");
				String[] qty = request.getParameterValues("Qty[]");
				for (int i = 0; i < commodity.length; i++) {
					comm.setCommodity(Integer.parseInt(commodity[i]));
					comm.setDescription("");
					if (qty[i].equals("") || qty[i] == null) {
						comm.setQty(0);
					} else {
						comm.setQty(Integer.parseInt(qty[i]));
					}
					comm.setPaymentDate("0000-00-00");
					comm.setOrderNO("");

					if (Integer.parseInt(ids[i]) == 0) {
						comm.setQuoteID(id);
						boolean flag = new QuoteSystemDao().insertCommodity(comm);//修改时新增商品
						if (flag) {
							ls.add("true");
						} else {
							ls.add("false");
						}
						if (getCommodity(Integer.parseInt(commodity[i]), id)) {//修改时将新增商品添加至各个模板
							ls.add("true");
						} else {
							ls.add("false");
						}
					} else {
						comm.setID(Integer.parseInt(ids[i]));
						boolean flag = new QuoteSystemDao().updateCommodity(comm);//修改报价信息对应的商品
						if (flag) {
							ls.add("true");
						} else {
							ls.add("false");
						}

						if (updateCommodity(Integer.parseInt(commodity[i]), id)) {//修改各个模板的商品
							ls.add("true");
						} else {
							ls.add("false");
						}
					}

				}
			}
			List<Map<String, Object>> ls2 = dao.getTotalPrice(id);
			double subTotal = 0;
			if (ls2.size() > 1) {
				subTotal = Double.parseDouble(ls2.get(1).get("FinalTotal").toString().replaceAll(",", ""));
			}
			double gifts = 0;
			List<Map<String, Object>> ls3 = dao.getGiftsTotal(id, "CompleteUSD");
			if (ls3.size() > 1) {
				gifts = Double.parseDouble(ls3.get(1).get("GiftsTotal").toString());
			}
			QuoteSystemModel model = new QuoteSystemModel();
			model.setFinalTotal(subTotal + Double.parseDouble(shipmentCost) + gifts);
			model.setQuoteID(id);
			model.setSubTotal(subTotal);
			model.setType("CompleteUSD");
			dao.updateModel2(model);

		} else {
			ls.add("false");
		}
		if (ls.contains("false")) {
			temp = false;
		} else {
			temp = true;
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "报价系统";
			String description = "修改-" + customerCompany;
			log.insert(request, JspInfo, description);
		}
	
		return temp;
	}

	@Override
	public int getTodayCount() {
		return new QuoteSystemDao().getTodayCount();
	}

	@Override
	public boolean delete(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("ID"));
		return new QuoteSystemDao().delete(id);
	}

	@Override
	public List<Map<String, Object>> getCommodityByQouteID(int id, String type) {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> item = new QuoteSystemDao().getQuoteTemp(id);
		List<Map<String, Object>> price = new QuoteSystemDao().getModelInfo(id, type);
		map.put("item", item);
		map.put("price", price);
		List<Map<String, Object>> ls = new ArrayList<>();
		ls.add(map);
		return ls;
	}

	@Override
	public boolean ModifyRMBContractModel(HttpServletRequest request) {

		List<String> ls = new ArrayList<>();
		QuoteSystemDao dao = new QuoteSystemDao();
		String ContractNO = request.getParameter("ContractNO");
		String SignDate = request.getParameter("SignDate");
		String CustomerCompany = request.getParameter("CustomerCompany");
		String CustomerTel = request.getParameter("CustomerTel");
		String CustomerFax = request.getParameter("CustomerFax");
		String CustomerContact = request.getParameter("CustomerContact");
		String SecondContact = request.getParameter("SecondContact");
		String TotalPrice = request.getParameter("TotalPrice");
		String Payment = request.getParameter("Payment");
		String LeadTime = request.getParameter("LeadTime");
		String DeliveryPoint = request.getParameter("DeliveryPoint");
		String ID = request.getParameter("ID");
		String quoteID = request.getParameter("QuoteID");

		QuoteContractRMB rmb = new QuoteContractRMB();
		rmb.setContractNO(ContractNO);
		rmb.setCustomerCompany(CustomerCompany);
		rmb.setCustomerContact(CustomerContact);
		rmb.setCustomerFax(CustomerFax);
		rmb.setCustomerTel(CustomerTel);
		rmb.setDeliveryPoint(DeliveryPoint);
		rmb.setID(Integer.parseInt(ID));
		rmb.setLeadTime(LeadTime);
		rmb.setPayment(Payment);

		if (SignDate.equals("--") || SignDate == null || SignDate.equals("")) {
			rmb.setSignDate("0000-00-00");
		} else {
			rmb.setSignDate(SignDate);
		}
		rmb.setSecondContact(SecondContact);
		rmb.setTotalPrice(Double.parseDouble(TotalPrice));
		boolean temp = dao.updateRMBContract(rmb);
		String[] ids = request.getParameterValues("RMBID[]");
		delete(ids, Integer.parseInt(quoteID), "RMB");
		if (request.getParameter("Exist").equals("yes") && temp) {

			String[] remark = request.getParameterValues("Remark[]");
			String[] model = request.getParameterValues("Model[]");
			String[] unit = request.getParameterValues("Unit[]");
			String[] unitPrice = request.getParameterValues("UnitPrice[]");
			String[] quantity = request.getParameterValues("Quantity[]");
			for (int i = 0; i < ids.length; i++) {
				CommodityContracts comm = new CommodityContracts();
				comm.setID(Integer.parseInt(ids[i]));
				comm.setRemark(remark[i]);
				comm.setCommodityModel(model[i]);
				comm.setUnit(unit[i]);
				comm.setUnitPrice(Double.parseDouble(unitPrice[i]));
				comm.setQuantity(Integer.parseInt(quantity[i]));
				if (Integer.parseInt(ids[i]) != 0) {
					if (dao.updateContractCommodity(comm)) {
						ls.add("true");
					} else {
						ls.add("false");
					}
				} else {
					comm.setQuoteID(Integer.parseInt(quoteID));
					if (dao.insertContractCommodity(comm)) {
						ls.add("true");
					} else {
						ls.add("false");
					}
				}

			}
		}
		boolean flag = false;
		if (!ls.contains("false")) {
			flag = true;
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "报价系统";
			String description = "修改-" + dao.getQuoteNumber(Integer.parseInt(quoteID)) + "-RMB合同";
			log.insert(request, JspInfo, description);
		}

		return flag;
	}

	@Override
	public boolean ModifyUSDContractModel(HttpServletRequest request) {
		List<String> ls = new ArrayList<>();
		QuoteSystemDao dao = new QuoteSystemDao();
		String contractNO = request.getParameter("ContractNO");
		String date = request.getParameter("Date");
		String customerCompany = request.getParameter("CustomerCompany");
		String customerTel = request.getParameter("CustomerTel");
		String customerFax = request.getParameter("CustomerFax");
		String customerContact = request.getParameter("CustomerContact");
		String totalPrice = request.getParameter("TotalPrice");
		String payment = request.getParameter("Payment");
		String ID = request.getParameter("ID");
		String airPort = request.getParameter("AirPort");
		String customerAdd = request.getParameter("CustomerAdd");
		String deliveryPoint = request.getParameter("DeliveryPoint");
		String destination = request.getParameter("Destination");
		String manufacturer = request.getParameter("Manufacturer");
		String shipment = request.getParameter("Shipment");
		String shipmentTime = request.getParameter("ShipmentTime");
		String shippingMark = request.getParameter("ShippingMark");
		String version = request.getParameter("Version");
		String quoteID = request.getParameter("QuoteID");
		String payTime = request.getParameter("PayTime");
		QuoteContractUSD usd = new QuoteContractUSD();
		usd.setPayTime(payTime);
		usd.setAirPort(airPort);
		usd.setContractNO(contractNO);
		usd.setCustomerAdd(customerAdd);
		usd.setCustomerCompany(customerCompany);
		usd.setCustomerContact(customerContact);
		usd.setCustomerFax(customerFax);
		usd.setCustomerTel(customerTel);
		if (date.equals("--") || date == null || date.equals("")) {
			usd.setDate("0000-00-00");
		} else {
			usd.setDate(date);
		}
		usd.setDeliveryPoint(deliveryPoint);
		usd.setDestination(destination);
		usd.setID(Integer.parseInt(ID));
		usd.setManufacturer(manufacturer);
		usd.setPayment(payment);
		usd.setShipment(shipment);
		usd.setShipmentTime(shipmentTime);
		usd.setShippingMark(contractNO);
		usd.setTotalPrice(Double.parseDouble(totalPrice));
		usd.setVersion(version);

		boolean temp = false;
		temp = dao.updateUSDContract(usd);
		String[] ids = request.getParameterValues("USDID[]");
		delete(ids, Integer.parseInt(quoteID), "USD");
		if (request.getParameter("Exist").equals("yes") && temp) {

			String[] remark = request.getParameterValues("Remark[]");
			String[] model = request.getParameterValues("Model[]");
			String[] unit = request.getParameterValues("Unit[]");
			String[] unitPrice = request.getParameterValues("UnitPrice[]");
			String[] quantity = request.getParameterValues("Quantity[]");
			for (int i = 0; i < ids.length; i++) {
				CommodityUSDContracts comm = new CommodityUSDContracts();
				comm.setID(Integer.parseInt(ids[i]));
				comm.setRemark(remark[i]);
				comm.setCommodityModel(model[i]);
				comm.setUnit(unit[i]);
				comm.setUnitPrice(Double.parseDouble(unitPrice[i]));
				comm.setQuantity(Integer.parseInt(quantity[i]));
				if (Integer.parseInt(ids[i]) != 0) {
					if (dao.updateUSDContractCommodity(comm)) {
						ls.add("true");
					} else {
						ls.add("false");
					}
				} else {
					comm.setQuoteID(Integer.parseInt(quoteID));
					if (dao.insertUSDContractCommodity(comm)) {
						ls.add("true");
					} else {
						ls.add("false");
					}
				}

			}
		}
		boolean flag = false;
		if (!ls.contains("false")) {
			flag = true;
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "报价系统";
			String description = "修改-" + dao.getQuoteNumber(Integer.parseInt(quoteID)) + "的USD合同";
			log.insert(request, JspInfo, description);
		}

		return flag;
	}

	@Override
	public List<Map<String, Object>> getRMBModelInfo(int id) {
		QuoteSystemDao dao = new QuoteSystemDao();
		List<Map<String, Object>> list = new ArrayList<>();
		List<Map<String, Object>> ls = dao.getRMBContract(id);
		List<Map<String, Object>> comm = dao.getContractCommodity(id);

		Map<String, Object> map = new HashMap<String, Object>();
		if (ls != null) {
			map.put("RMBContract", ls);
		}
		if (comm != null) {
			map.put("RMBCommoditys", comm);
		}
		list.add(map);
		return list;
	}

	@Override
	public List<Map<String, Object>> getUSDModelInfo(int id) {
		QuoteSystemDao dao = new QuoteSystemDao();
		List<Map<String, Object>> list = new ArrayList<>();
		List<Map<String, Object>> ls = dao.getUSDContract(id);
		List<Map<String, Object>> comm = dao.getUSDContractCommodity(id);
		Map<String, Object> map = new HashMap<String, Object>();
		if (ls != null) {
			map.put("USDContract", ls);
		}
		if (comm != null) {
			map.put("USDCommoditys", comm);
		}
		list.add(map);
		return list;
	}

	@Override
	public boolean deleteRMBCommodity(int id) {
		QuoteSystemDao dao = new QuoteSystemDao();
		boolean flag = dao.deleteContractCommodity(id);

		return flag;
	}

	@Override
	public boolean deleteUSDCommodity(int id) {
		QuoteSystemDao dao = new QuoteSystemDao();
		boolean flag = false;
		if (dao.deleteUSDContractCommodity(id)) {
			// List<Map<String,Object>> ls2 = dao.getTotalPrice(id);
			// double subTotal = 0;
			// if(ls2.size()>1){
			// subTotal =
			// Double.parseDouble(ls2.get(1).get("FinalTotal").toString());
			// }
			// double gifts = 0;
			// List<Map<String,Object>> ls3 = dao.getGiftsTotal(id,
			// "CompleteUSD");
			// if(ls3.size()>1){
			// gifts =
			// Double.parseDouble(ls3.get(1).get("GiftsTotal").toString());
			// }
			// QuoteSystemModel model = new QuoteSystemModel();
			// model.setFinalTotal(subTotal+Double.parseDouble(shipmentCost)+gifts);
			// model.setQuoteID(id);
			// model.setSubTotal(subTotal);
			// model.setType("CompleteUSD");
			// flag = dao.updateModel2(model);
			flag = true;
		}

		return flag;
	}

	@Override
	public boolean modifyDelivery(int id) {

		return false;
	}

	@Override
	public boolean deleteDelivery(int id) {
		return new QuoteSystemDao().deleteDelivery(id);
	}


	@Override
	public boolean addQuoteRequest(HttpServletRequest request) {

		List<String> ls = new ArrayList<>();
		QuoteSystemDao dao = new QuoteSystemDao();
		String Fumigation = request.getParameter("Fumigation");
		String Size = request.getParameter("Size");
		String Weight = request.getParameter("Weight");
		String ProductImg = request.getParameter("ProductImg");
		String NamePlateImg = request.getParameter("NamePlateImg");
		String OriginInfo = request.getParameter("OriginInfo");
		String ProductName = request.getParameter("ProductName");
		String PackingQty = request.getParameter("PackingQty");
		String ShippingMark = request.getParameter("ShippingMark");
		String Departure = request.getParameter("Departure");
		String Destination = request.getParameter("Destination");
		String Receiving = request.getParameter("Receiving");
		String SplitShipment = request.getParameter("SplitShipment");
		String Airelift = request.getParameter("Airelift");
		String Truck = request.getParameter("Truck");
		String FastMail = request.getParameter("FastMail");
		String TailCar = request.getParameter("TailCar");
		String Unloading = request.getParameter("Unloading");
		String QuoteID = request.getParameter("QuoteID");
		String ContractNO = request.getParameter("ContractNO");
		String PO = request.getParameter("PO").trim();
		String SO = request.getParameter("SO").trim();
		String exist = request.getParameter("Exist");

		QuoteRequest req = new QuoteRequest();
		req.setFastMail(FastMail);
		req.setFumigation(Fumigation);
		req.setSize(Size);
		req.setWeight(Weight);
		req.setProductImg(ProductImg);
		req.setNamePlateImg(NamePlateImg);
		req.setOriginInfo(OriginInfo);
		req.setProductName(ProductName);
		req.setPackingQty(PackingQty);
		req.setShippingMark(ShippingMark);
		req.setDeparture(Departure);
		req.setDestination(Destination);
		req.setReceiving(Receiving);
		req.setSplitShipment(SplitShipment);
		req.setAirelift(Airelift);
		req.setTruck(Truck);
		req.setTailCar(TailCar);
		req.setUnloading(Unloading);
		req.setContractNO(ContractNO);
		req.setPO(PO);
		req.setSO(SO);
		req.setQuoteID(Integer.parseInt(QuoteID));
		boolean temp = dao.insertQuoteRequest(req);
		if (temp) {
			ls.add("true");
			String[] ids = request.getParameterValues("DeliveryID[]");
			delete(ids, Integer.parseInt(QuoteID), "Delivery");
			if (exist.equals("yes")) {
				String[] Model = request.getParameterValues("Model[]");
				String[] Description = request.getParameterValues("Description[]");
				String[] Quantity = request.getParameterValues("Quantity[]");
				String[] Remarks = request.getParameterValues("Remarks[]");

				for (int i = 0; i < ids.length; i++) {
					QuoteDelivery delivery = new QuoteDelivery();
					delivery.setModel(Model[i]);
					delivery.setDescription(Description[i]);
					delivery.setQuantity(Integer.parseInt(Quantity[i]));
					delivery.setRemarks(Remarks[i]);
					if (Integer.parseInt(ids[i]) == 0) {
						delivery.setQuoteID(Integer.parseInt(QuoteID));
						boolean tempD = dao.insertDelivery(delivery);
						if (tempD) {
							ls.add("true");
						} else {
							ls.add("false");
						}
					} else {
						delivery.setID(Integer.parseInt(ids[i]));
						boolean tempD = dao.upadteDelivery(delivery);
						if (tempD) {
							ls.add("true");
						} else {
							ls.add("false");
						}
					}
				}
			}
		} else {
			ls.add("false");
		}

		boolean flag = false;
		if (!ls.contains("false")) {
			flag = true;
			LogInfoService logs = new LogInfoServiceImpl();
			String JspInfo = "报价系统";
			String description = "新增-" + new QuoteSystemDao().getQuoteNumber(Integer.parseInt(QuoteID)) + "备货请求";
			logs.insert(request, JspInfo, description);
		}
		return flag;
	}

	@Override
	public boolean modifyQuoteRequest(HttpServletRequest request) {
		List<String> ls = new ArrayList<>();
		QuoteSystemDao dao = new QuoteSystemDao();
		String ID = request.getParameter("ID");
		String Fumigation = request.getParameter("Fumigation");
		String Size = request.getParameter("Size");
		String Weight = request.getParameter("Weight");
		String ProductImg = request.getParameter("ProductImg");
		String NamePlateImg = request.getParameter("NamePlateImg");
		String OriginInfo = request.getParameter("OriginInfo");
		String ProductName = request.getParameter("ProductName");
		String PackingQty = request.getParameter("PackingQty");
		String ShippingMark = request.getParameter("ShippingMark");
		String Departure = request.getParameter("Departure");
		String Destination = request.getParameter("Destination");
		String Receiving = request.getParameter("Receiving");
		String SplitShipment = request.getParameter("SplitShipment");
		String Airelift = request.getParameter("Airelift");
		String Truck = request.getParameter("Truck");
		String FastMail = request.getParameter("FastMail");
		String TailCar = request.getParameter("TailCar");
		String Unloading = request.getParameter("Unloading");
		String QuoteID = request.getParameter("QuoteID");
		String ContractNO = request.getParameter("ContractNO");
		String PO = request.getParameter("PO").trim();
		String SO = request.getParameter("SO").trim();
		String Name = request.getParameter("Name");
		String exist = request.getParameter("Exist");

		QuoteRequest req = new QuoteRequest();
		req.setID(Integer.parseInt(ID));
		req.setFastMail(FastMail);
		req.setFumigation(Fumigation);
		req.setSize(Size);
		req.setWeight(Weight);
		req.setProductImg(ProductImg);
		req.setNamePlateImg(NamePlateImg);
		req.setOriginInfo(OriginInfo);
		req.setProductName(ProductName);
		req.setPackingQty(PackingQty);
		req.setShippingMark(ShippingMark);
		req.setDeparture(Departure);
		req.setDestination(Destination);
		req.setReceiving(Receiving);
		req.setSplitShipment(SplitShipment);
		req.setAirelift(Airelift);
		req.setTruck(Truck);
		req.setTailCar(TailCar);
		req.setUnloading(Unloading);
		req.setContractNO(ContractNO);
		req.setPO(PO);
		req.setSO(SO);
		req.setName(Name);
		req.setQuoteID(Integer.parseInt(QuoteID));
		boolean temp = false;
		if (Integer.parseInt(ID) != 0) {
			temp = dao.upadteQuoteRequest(req);
		} else {

			temp = dao.insertQuoteRequest(req);
		}
		if (temp) {
			ls.add("true");
			String[] ids = request.getParameterValues("DeliveryID[]");
			delete(ids, Integer.parseInt(QuoteID), "Delivery");
			if (exist.equals("yes")) {
				String[] Model = request.getParameterValues("Model[]");
				String[] Description = request.getParameterValues("Description[]");
				String[] Quantity = request.getParameterValues("Quantity[]");
				String[] Remarks = request.getParameterValues("Remarks[]");

				for (int i = 0; i < ids.length; i++) {
					QuoteDelivery delivery = new QuoteDelivery();
					delivery.setModel(Model[i]);
					delivery.setDescription(Description[i]);
					delivery.setQuantity(Integer.parseInt(Quantity[i]));
					delivery.setRemarks(Remarks[i]);
					delivery.setQuoteID(Integer.parseInt(QuoteID));
					if (Integer.parseInt(ids[i]) == 0) {
						boolean tempD = dao.insertDelivery(delivery);
						if (tempD) {
							ls.add("true");
						} else {
							ls.add("false");
						}
					} else {
						delivery.setID(Integer.parseInt(ids[i]));
						boolean tempD = dao.upadteDelivery(delivery);
						if (tempD) {
							ls.add("true");
						} else {
							ls.add("false");
						}
					}
				}
			}
		} else {
			ls.add("false");
		}

		boolean flag = false;
		if (!ls.contains("false")) {
			flag = true;
			LogInfoService logs = new LogInfoServiceImpl();
			String JspInfo = "报价系统";
			String description = "修改-" + new QuoteSystemDao().getQuoteNumber(Integer.parseInt(QuoteID)) + "备货请求";
			logs.insert(request, JspInfo, description);
		}
		return flag;
	}

	@Override
	public List<Map<String, Object>> getQuoteRequest(int quoteID) {
		QuoteSystemDao dao = new QuoteSystemDao();
		List<Map<String, Object>> ls = new ArrayList<>();
		// List<Map<String, Object>> list = dao.getDelivery(quoteID);
		List<Map<String, Object>> list = dao.getDeliveryInPO(quoteID);
		List<Map<String, Object>> list2 = dao.getQuoteRequest(quoteID);
		List<Map<String, Object>> list3 = dao.getDeliveryNotInPO(quoteID);
		Map<String, Object> map = new HashMap<>();
		map.put("Delivery", list);
		map.put("Request", list2);
		map.put("NotInPO", list3);
		ls.add(map);
		return ls;
	}

	@Override
	public void exportExcel(List<Map<String, Object>> ls, String path) {
		List<Map<String, Object>> pack1 = (List<Map<String, Object>>) ls.get(0).get("Packing");
		List<Map<String, Object>> pack = new ArrayList<>();
		for (int i = 0; i < pack1.size(); i++) {
			Map<String, Object> map = new HashMap<>();
			if (i != 0) {
				Map<String, Object> temp = pack1.get(i);
				for (String key : temp.keySet()) {
					map.put(key, temp.get(key));
					pack.add(map);
				}
			}
		}
		List<Map<String, Object>> delivery1 = (List<Map<String, Object>>) ls.get(0).get("Delivery");
		List<Map<String, Object>> delivery = new ArrayList<>();
		for (int i = 0; i < delivery1.size(); i++) {
			if (i != 0) {
				delivery.add(delivery1.get(i));
			}
		}
		List<Map<String, Object>> transport1 = (List<Map<String, Object>>) ls.get(0).get("Transport");
		List<Map<String, Object>> transport = new ArrayList<>();
		for (int i = 0; i < transport1.size(); i++) {
			if (i != 0) {
				Map<String, Object> map = new HashMap<>();
				Map<String, Object> temp = transport1.get(i);
				for (String key : temp.keySet()) {
					map.put(key, temp.get(key));

				}
				transport.add(map);
			}
		}
		List<Map<String, Object>> list = (List<Map<String, Object>>) ls.get(0).get("PO");

		List<Map<String, Object>> name = (List<Map<String, Object>>) ls.get(0).get("Name");
		String contractName = "";
		if (name.size() > 1) {
			contractName = name.get(1).get("Name").toString();
		}
		String contractNO = "";
		String po = "";
		String so = "";
		if (list.size() > 1) {
			contractNO = list.get(1).get("ContractNO").toString();
			po = list.get(1).get("PO").toString();
			so = list.get(1).get("SO").toString();
		}

		XSSFWorkbook xwk = new XSSFWorkbook();
		StockListExcelUtil util = new StockListExcelUtil();
		util.buildStockListExcel(delivery, path, contractName, xwk, 0, "备货清单");
		util.buildPackingExcel(pack, contractNO, po, so, path, xwk, 1, "包装要求");
		util.buildTransportExcel(transport, contractNO, po, so, path, xwk, 2, "运输要求");
		FileOutputStream fo;

		try {
			fo = new FileOutputStream(path);
			xwk.write(fo);
			fo.flush();
			fo.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>> getExcelQuoteRequest(int quoteID) {
		QuoteSystemDao dao = new QuoteSystemDao();
		List<Map<String, Object>> ls = new ArrayList<>();
		List<Map<String, Object>> list1 = dao.getDelivery(quoteID);
		List<Map<String, Object>> list2 = dao.getQuoteRequestByPacking(quoteID);
		List<Map<String, Object>> list3 = dao.getQuoteRequestByTransport(quoteID);
		List<Map<String, Object>> list4 = dao.getQuoteRequestPO(quoteID);
		List<Map<String, Object>> list5 = dao.getQuoteRequestName(quoteID);

		Map<String, Object> map = new HashMap<>();
		map.put("Delivery", list1);
		map.put("Packing", list2);
		map.put("Transport", list3);
		map.put("PO", list4);
		map.put("Name", list5);
		ls.add(map);
		return ls;
	}

	@Override
	public boolean operateCascadePO(HttpServletRequest request) {
		QuoteSystemDao dao = new QuoteSystemDao();
		QuoteCascadePO po = new QuoteCascadePO();
		int ID = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		int id = Integer.parseInt(request.getParameter("CompleteID")==null?"0":request.getParameter("CompleteID"));
		String Number = request.getParameter("Number");
		String Version = request.getParameter("Version");
		String ForwarderOne = request.getParameter("ForwarderOne");
		String ForwarderTwo = request.getParameter("ForwarderTwo");
		String ForwarderThree = request.getParameter("ForwarderThree");
		String ForwarderFour = request.getParameter("ForwarderFour");
		String ShipCompany = request.getParameter("ShipCompany");
		String ShipAddr = request.getParameter("ShipAddr");
		String ShipTel = request.getParameter("ShipTel");
		String ShipAttn = request.getParameter("ShipAttn");
		
		String str = "";
		try {
			str = new String(new byte[]{(byte) 0xc2,(byte) 0xa0},"UTF-8" );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String EndCompany = request.getParameter("EndCompany");
		EndCompany = EndCompany.replace(str, " ");
		String EndAddr = request.getParameter("EndAddr");
		EndAddr = EndAddr.replace(str, " ");
		String EndTel = request.getParameter("EndTel");
		String ContactPerson = request.getParameter("ContactPerson");
		ContactPerson = ContactPerson.replace(str, " ");
		String DeliveryTerm = request.getParameter("DeliveryTerm");
		String ShippingMark = request.getParameter("ShippingMark");
		String ContractNO = request.getParameter("ContractNO");
		String ShipmentPort = request.getParameter("ShipmentPort");
		String SubTotal = request.getParameter("SubTotal");
		String Discounted = request.getParameter("Discounted");
		String FinalTotal = request.getParameter("FinalTotal");
		int QuoteID = Integer.parseInt(request.getParameter("QuoteID"));
		String exist = request.getParameter("Exist");
		String ref = request.getParameter("RefNO");
		String[] ids = request.getParameterValues("POID[]");
		delete(ids, QuoteID, "Cascade");//删除商品表的数据
		po.setRefNO(ref);
		po.setContactPerson(ContactPerson);
		po.setContractNO(ContractNO);
		po.setDeliveryTerm(DeliveryTerm);
		if (Discounted == null || Discounted.equals("--") || Discounted.equals("")) {
			po.setDiscounted(0);
		} else {
			po.setDiscounted(Double.parseDouble(Discounted));
		}
		po.setEndAddr(EndAddr);
		po.setEndCompany(EndCompany);
		if (FinalTotal.equals("--") || FinalTotal.equals("") || FinalTotal == null) {
			po.setFinalTotal(0);
		} else {
			po.setFinalTotal(Double.parseDouble(FinalTotal));
		}
		po.setForwarderFour(ForwarderFour);
		po.setForwarderOne(ForwarderOne);
		po.setForwarderThree(ForwarderThree);
		po.setForwarderTwo(ForwarderTwo);
		po.setID(ID);
		po.setNumber(Number);
		po.setQuoteID(QuoteID);
		po.setShipAddr(ShipAddr);
		po.setShipAttn(ShipAttn);
		po.setShipCompany(ShipCompany);
		po.setShipmentPort(ShipmentPort);
		po.setShippingMark(ShippingMark);
		po.setShipTel(ShipTel);
		po.setEndTel(EndTel);
		if (SubTotal.equals("--") || SubTotal.equals("") || SubTotal == null) {
			po.setSubTotal(0);
		} else {
			po.setSubTotal(Double.parseDouble(SubTotal));
		}
		if (Version.equals("--") || Version.equals("") || Version == null) {
			po.setVersion("");
		} else {
			po.setVersion(Version);
		}
		po.setType("Parts");

		boolean temp = false;
		String description = "";
		if (ID == 0) {
			temp = dao.insertCascadePO(po);
			description = "新增-" + dao.getQuoteNumber(QuoteID) + "Cascade PO配件";
		} else {
			temp = dao.updateCascadePO(po);
			description = "修改-" + dao.getQuoteNumber(QuoteID) + "Cascade PO配件";
		}
		System.out.println("id==="+id);
		switch (id) {
		case 0:
			po.setType("Complete");
			temp = dao.insertCascadePOEndUser(po);
			System.out.println("insert");
			break;

		default:
			po.setType("Complete");
			po.setID(id);
			temp = dao.updateCascadePOEndUser(po);
			System.out.println("update");
			break;
		}
		if (temp) {
			LogInfoService logs = new LogInfoServiceImpl();
			String JspInfo = "报价系统";
			logs.insert(request, JspInfo, description);
		}
		return temp;
	}

	@Override
	public boolean operateCascadePOComplete(HttpServletRequest request) {
		QuoteSystemDao dao = new QuoteSystemDao();
		QuoteCascadePO po = new QuoteCascadePO();
		int id = Integer.parseInt(request.getParameter("PartID")==null?"0":request.getParameter("PartID"));
		int ID = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		String Number = request.getParameter("Number");
		String Version = request.getParameter("Version");
		String ForwarderOne = request.getParameter("ForwarderOne");
		String ForwarderTwo = request.getParameter("ForwarderTwo");
		String ForwarderThree = request.getParameter("ForwarderThree");
		String ForwarderFour = request.getParameter("ForwarderFour");
		String ShipCompany = request.getParameter("ShipCompany");
		String ShipAddr = request.getParameter("ShipAddr");
		String ShipTel = request.getParameter("ShipTel");
		String ShipAttn = request.getParameter("ShipAttn");
		String str = "";
		try {
			str = new String(new byte[]{(byte) 0xc2,(byte) 0xa0},"UTF-8" );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String EndCompany = request.getParameter("EndCompany");
		EndCompany = EndCompany.replace(str, " ");
		String EndAddr = request.getParameter("EndAddr");
		EndAddr = EndAddr.replace(str, " ");
		String EndTel = request.getParameter("EndTel");
		String ContactPerson = request.getParameter("ContactPerson");
		ContactPerson = ContactPerson.replace(str, " ");
		String DeliveryTerm = request.getParameter("DeliveryTerm");
		String ShippingMark = request.getParameter("ShippingMark");
		String ContractNO = request.getParameter("ContractNO");
		String ShipmentPort = request.getParameter("ShipmentPort");
		String SubTotal = request.getParameter("SubTotal");
		String Discounted = request.getParameter("Discounted");
		String FinalTotal = request.getParameter("FinalTotal");
		int QuoteID = Integer.parseInt(request.getParameter("QuoteID"));
		String exist = request.getParameter("Exist");
		String ref = request.getParameter("RefNO");
		String[] ids = request.getParameterValues("POID[]");
		delete(ids, QuoteID, "CascadeComplete");
		po.setRefNO(ref);
		po.setContactPerson(ContactPerson);
		po.setContractNO(ContractNO);
		po.setDeliveryTerm(DeliveryTerm);
//		System.out.println("Cascade:" + Discounted + "--" + FinalTotal);
		if (Discounted == null || Discounted.equals("--") || Discounted.equals("")) {
			po.setDiscounted(0);
		} else {
			po.setDiscounted(Double.parseDouble(Discounted));
		}
		po.setEndAddr(EndAddr);
		po.setEndCompany(EndCompany);
		if (FinalTotal.equals("--") || FinalTotal.equals("") || FinalTotal == null) {
			po.setFinalTotal(0);
		} else {
			po.setFinalTotal(Double.parseDouble(FinalTotal));
		}
		po.setForwarderFour(ForwarderFour);
		po.setForwarderOne(ForwarderOne);
		po.setForwarderThree(ForwarderThree);
		po.setForwarderTwo(ForwarderTwo);
		po.setID(ID);
		po.setNumber(Number);
		po.setQuoteID(QuoteID);
		po.setShipAddr(ShipAddr);
		po.setShipAttn(ShipAttn);
		po.setShipCompany(ShipCompany);
		po.setShipmentPort(ShipmentPort);
		po.setShippingMark(ShippingMark);
		po.setShipTel(ShipTel);
		po.setEndTel(EndTel);
		if (SubTotal.equals("--") || SubTotal.equals("") || SubTotal == null) {
			po.setSubTotal(0);
		} else {
			po.setSubTotal(Double.parseDouble(SubTotal));
		}
		if (Version.equals("--") || Version.equals("") || Version == null) {
			po.setVersion("");
		} else {
			po.setVersion(Version);
		}
		po.setType("Complete");

		boolean temp = false;
		String description = "";
		if (ID == 0) {
			temp = dao.insertCascadePO(po);
			description = "新增-" + dao.getQuoteNumber(QuoteID) + "Cascade PO整机";
		} else {
			temp = dao.updateCascadePO(po);
			description = "修改-" + dao.getQuoteNumber(QuoteID) + "Cascade PO整机";
		}
		switch (id) {
		case 0:
			po.setType("Parts");
			temp = dao.insertCascadePOEndUser(po);
			break;

		default:
			po.setType("Parts");
			po.setID(id);
			temp = dao.updateCascadePOEndUser(po);
			break;
		}
		if (temp) {
			LogInfoService logs = new LogInfoServiceImpl();
			String JspInfo = "报价系统";
			logs.insert(request, JspInfo, description);
		}
		return temp;
	}
	
	@Override
	public boolean operateOtherPO(HttpServletRequest request) {
		QuoteSystemDao dao = new QuoteSystemDao();
		QuoteOtherPO po = new QuoteOtherPO();
		int ID = Integer.parseInt(request.getParameter("ID"));
		String Number = request.getParameter("Number");
		String Version = request.getParameter("Version");
		String ForwarderOne = request.getParameter("ForwarderOne");
		String ForwarderTwo = request.getParameter("ForwarderTwo");
		String ForwarderThree = request.getParameter("ForwarderThree");
		String ForwarderFour = request.getParameter("ForwarderFour");
		String ShipCompany = request.getParameter("ShipCompany");
		String ShipAddr = request.getParameter("ShipAddr");
		String ShipTel = request.getParameter("ShipTel");
		String ShipAttn = request.getParameter("ShipAttn");
		String VendorOne = request.getParameter("VendorOne");
		String VendorTwo = request.getParameter("VendorTwo");
		String VendorThree = request.getParameter("VendorThree");
		String VendorFour = request.getParameter("VendorFour");
		String DeliveryTerm = request.getParameter("DeliveryTerm");
		String ShippingMark = request.getParameter("ShippingMark");
		String ContractNO = request.getParameter("ContractNO");
		String ShipmentPort = request.getParameter("ShipmentPort");
		String SubTotal = request.getParameter("SubTotal");
		String Discounted = request.getParameter("Discounted");
		String FinalTotal = request.getParameter("FinalTotal");
		String CreditTerm = request.getParameter("CreditTerm");
		int QuoteID = Integer.parseInt(request.getParameter("QuoteID"));
		String exist = request.getParameter("Exist");
		String[] ids = request.getParameterValues("POID[]");
		delete(ids, QuoteID, "Other");
		po.setVendorOne(VendorOne);
		po.setContractNO(ContractNO);
		po.setDeliveryTerm(DeliveryTerm);
//		System.out.println("Discounted:" + Discounted);
		if (Discounted.equals("--") || Discounted.equals("") || Discounted == null) {
			po.setDiscounted(0);
		} else {
			po.setDiscounted(Double.parseDouble(Discounted));
		}
		po.setVendorTwo(VendorTwo);
		po.setVendorThree(VendorThree);
		po.setVendorFour(VendorFour);
		if (FinalTotal.equals("--") || FinalTotal.equals("") || FinalTotal == null) {
			po.setFinalTotal(0);
		} else {
			po.setFinalTotal(Double.parseDouble(FinalTotal));
		}
		po.setForwarderFour(ForwarderFour);
		po.setForwarderOne(ForwarderOne);
		po.setForwarderThree(ForwarderThree);
		po.setForwarderTwo(ForwarderTwo);
		po.setID(ID);
		po.setNumber(Number);
		po.setQuoteID(QuoteID);
		po.setShipAddr(ShipAddr);
		po.setShipAttn(ShipAttn);
		po.setShipCompany(ShipCompany);
		po.setShipmentPort(ShipmentPort);
		po.setShippingMark(ShippingMark);
		po.setShipTel(ShipTel);
		po.setCreditTerm(CreditTerm);
		if (SubTotal.equals("--") || SubTotal.equals("") || SubTotal == null) {
			po.setSubTotal(0);
		} else {
			po.setSubTotal(Double.parseDouble(SubTotal));
		}
		if (Version.equals("--") || Version.equals("") || Version == null) {
			po.setVersion("");
		} else {
			po.setVersion(Version);
		}
		String description = "";
		boolean temp = false;
		if (ID == 0) {
			temp = dao.insertOtherPO(po);
			description = "新增-" + dao.getQuoteNumber(QuoteID) + "其他供应商PO";
		} else {
			temp = dao.updateOtherPO(po);
			description = "修改-" + dao.getQuoteNumber(QuoteID) + "其他供应商PO";

		}
		if (temp) {
			LogInfoService logs = new LogInfoServiceImpl();
			String JspInfo = "报价系统";
			logs.insert(request, JspInfo, description);
		}
		return temp;
	}

	@Override
	public boolean deleteCascadeTemp(int id) {
		return new QuoteSystemDao().deleteCascadeTemp(id);
	}

	@Override
	public boolean deleteOtherTemp(int id) {

		return new QuoteSystemDao().deleteOtherTemp(id);
	}

	@Override
	public List<Map<String, Object>> getCascadePO(int id) {
		QuoteSystemDao dao = new QuoteSystemDao();
		List<Map<String, Object>> ls = new ArrayList<>();
		List<Map<String, Object>> part = dao.getCascadeTemp(id);
		List<Map<String, Object>> po = dao.getQuoteCascadePO(id);
		Map<String, Object> map = new HashMap<>();
		map.put("part", part);
		map.put("POInfo", po);
		map.put("CompleteID", dao.getPOID(id, "Complete", "CompleteID"));
		ls.add(map);
		return ls;
	}
	
	@Override
	public List<Map<String, Object>> getCascadeCompletePO(int id) {
		QuoteSystemDao dao = new QuoteSystemDao();
		List<Map<String, Object>> ls = new ArrayList<>();
		List<Map<String, Object>> part = dao.getCascadeCompleteTemp(id);
		List<Map<String, Object>> po = dao.getQuoteCompleteCascadePO(id);
		Map<String, Object> map = new HashMap<>();
		map.put("part", part);
		map.put("POInfo", po);
		map.put("PartID", dao.getPOID(id, "Parts", "PartID"));
		ls.add(map);
		return ls;
	}
	
	public static void main(String[] args) {
		
		QuoteSystemService service = new QuoteSystemServiceImpl();
		String[] modelList = {"ACP40-A-GSG-100","ACP40-A-GSG-125"};
		/*
		Map<String, String[]> result = service.getDataByNames("探针台");
		for(Map.Entry<String, String[]> entry:result.entrySet()){
			System.out.println(entry.getKey());
			for(int i = 0;i < entry.getValue().length;i ++){
				System.out.println(entry.getValue()[i]);
			}
			
		}*/
		/*String[] modelList = {"探针台","射频探针","直流探针","线缆","定位器","校准片"};
		Map<String, String[]> resultMap = service.getRaderByNames(modelList);
		for(Map.Entry<String, String[]> entry:resultMap.entrySet()){
			System.out.println(entry.getKey());
			for(int i = 0;i < entry.getValue().length;i ++){
				System.out.println(entry.getValue()[i]);
			}
			
		}*/
		
		
		
	}

	@Override
	public List<Map<String, Object>> getOtherPO(int id) {
		QuoteSystemDao dao = new QuoteSystemDao();
		List<Map<String, Object>> ls = new ArrayList<>();
		List<Map<String, Object>> part = dao.getOtherTemp(id);
		List<Map<String, Object>> po = dao.getQuoteOtherPO(id);
		Map<String, Object> map = new HashMap<>();
		map.put("part", part);
		map.put("POInfo", po);
		ls.add(map);

		return ls;
	}

	@Override
	public boolean exportCascadePOExcel(int id, String path, String image, String user, String email) {
		boolean flag = false;
		QuoteSystemDao dao = new QuoteSystemDao();
		List<Map<String, Object>> part1 = dao.getCascadeTemp(id);
		List<Map<String, Object>> part = new ArrayList<>();
		for (int i = 0; i < part1.size(); i++) {
			if (i != 0) {
				part.add(part1.get(i));
			}
		}
		List<Map<String, Object>> po1 = dao.getQuoteCascadePO(id);
		List<Map<String, Object>> po = new ArrayList<>();
		for (int i = 0; i < po1.size(); i++) {
			if (i != 0) {
				po.add(po1.get(i));
			}
		}
		POExcelUtil util = new POExcelUtil();
		if (part.size() == 0 || po.size() == 0) {

		} else {
			flag = true;
			util.buildCascadePO(part, po, path, image, user, email);
		}
		return flag;
	}

	@Override
	public boolean exportOtherPOExcel(int id, String path, String image, String user, String email) {
		QuoteSystemDao dao = new QuoteSystemDao();
		List<Map<String, Object>> part1 = dao.getOtherTemp(id);
		List<Map<String, Object>> part = new ArrayList<>();
		for (int i = 0; i < part1.size(); i++) {
			if (i != 0) {
				part.add(part1.get(i));
			}
		}
		List<Map<String, Object>> po1 = dao.getQuoteOtherPO(id);
		List<Map<String, Object>> po = new ArrayList<>();
		for (int i = 0; i < po1.size(); i++) {
			if (i != 0) {
				po.add(po1.get(i));
			}
		}
		boolean flag = false;
		POExcelUtil util = new POExcelUtil();
		if (po.size() == 0) {

		} else {
			flag = true;
			util.buildOtherPO(part, po, path, image, user, email);
		}

		return flag;
	}

	@Override
	public boolean updatePOContact(QuoteCascadePO other) {
		return new QuoteSystemDao().updateCascadePOContact(other);
	}

	@Override
	public boolean updateOtherPOContact(QuoteOtherPO other) {
		// return new QuoteSystemDao().updateOtherPOContact(other);
		return false;
	}

	@Override
	public List<Map<String, Object>> getCurrentUserEmail(String name) {
		return new QuoteSystemDao().getCurrentUserEmail(name);
	}

	@Override
	public boolean deleteCommodityTemp(int id,int quoteID,int commodityID,String model) {
	
		QuoteSystemDao dao = new QuoteSystemDao();
		boolean result = dao.deleteCommodityTemp(id);
		if(result){
			dao.deleteContractCommodity(quoteID,model);
			dao.deleteUSDContractCommodity(quoteID,model);
			dao.deleteDelivery(quoteID,model);
			dao.deleteCascadeTemp(quoteID,commodityID);
			dao.deleteOtherTemp(quoteID,commodityID);
			dao.deleteOtherSupplierTemp(quoteID,commodityID);
		}
		return result;
				
	}

	/*
	 * @Override public boolean insertCommodityInfo(HttpServletRequest request)
	 * { boolean flag = false; String name =
	 * request.getParameter("CommodityName"); String model =
	 * request.getParameter("Model"); String unit =
	 * request.getParameter("Unit"); String producingArea =
	 * request.getParameter("ProducingArea"); String deliveryPeriod =
	 * request.getParameter("DeliveryPeriod"); String costPrice =
	 * request.getParameter("CostPrice"); String discountCost =
	 * request.getParameter("DiscountCost"); String sellerPriceOne =
	 * request.getParameter("SellerPriceOne"); String sellerPriceTwo =
	 * request.getParameter("SellerPriceTwo"); String sellerPriceThree =
	 * request.getParameter("SellerPriceThree"); String supplier =
	 * request.getParameter("Supplier"); String ProductCategory =
	 * request.getParameter("ProductCategory"); String QuoteTime =
	 * request.getParameter("QuoteTime")==null?"0000-00-00":request.getParameter
	 * ("QuoteTime").toString(); int id =
	 * Integer.parseInt(request.getParameter("ID")); CommodityInfo info = new
	 * CommodityInfo(); info.setProductCategory(ProductCategory);
	 * info.setCommodityName(name); info.setModel(model); info.setUnit(unit);
	 * info.setProducingArea(producingArea);
	 * info.setDeliveryPeriod(deliveryPeriod); if (costPrice == null ||
	 * costPrice.equals("--") || costPrice.equals("")) { info.setCostPrice(0); }
	 * else { info.setCostPrice(Double.parseDouble(costPrice)); } if
	 * (discountCost == null || discountCost.equals("--") ||
	 * discountCost.equals("")) { info.setDiscountCost(0); } else {
	 * info.setDiscountCost(Double.parseDouble(discountCost)); } if
	 * (sellerPriceOne == null || sellerPriceOne.equals("--") ||
	 * sellerPriceOne.equals("")) { info.setSellerPriceOne(0); } else {
	 * info.setSellerPriceOne(Double.parseDouble(sellerPriceOne)); } if
	 * (sellerPriceTwo == null || sellerPriceTwo.equals("--") ||
	 * sellerPriceTwo.equals("")) { info.setSellerPriceTwo(0); } else {
	 * info.setSellerPriceTwo(Double.parseDouble(sellerPriceTwo)); } if
	 * (sellerPriceThree == null || sellerPriceThree.equals("--") ||
	 * sellerPriceThree.equals("")) { info.setSellerPriceThree(0); } else {
	 * info.setSellerPriceThree(Double.parseDouble(sellerPriceThree)); }
	 * info.setSupplier(supplier); info.setQuoteTime(QuoteTime); info.setID(id);
	 * if (id == 0) { flag = new QuoteSystemDao().insertCommodityInfo(info); }
	 * else { flag = new QuoteSystemDao().updateCommodityInfo(info); }
	 * 
	 * return flag; }
	 */
	@Override
	public boolean operateOtherRMBPO(HttpServletRequest request) {
		QuoteSystemDao dao = new QuoteSystemDao();
		QuoteOtherSupplierPO po = new QuoteOtherSupplierPO();
		int ID = Integer.parseInt(request.getParameter("ID"));
		String Number = request.getParameter("Number");
		String Version = request.getParameter("Version");
//		System.out.println(Number + "--ii--" + Version);
		// String ForwarderOne = request.getParameter("ForwarderOne");
		// String ForwarderTwo = request.getParameter("ForwarderTwo");
		// String ForwarderThree = request.getParameter("ForwarderThree");
		// String ForwarderFour = request.getParameter("ForwarderFour");
		// String ShipCompany = request.getParameter("ShipCompany");
		// String ShipAddr = request.getParameter("ShipAddr");
		String ShipTel = request.getParameter("ShipTel");
		String ShipAttn = request.getParameter("ShipAttn");
		String VendorOne = request.getParameter("VendorOne");
		String VendorTwo = request.getParameter("VendorTwo");
		String VendorThree = request.getParameter("VendorThree");
		String VendorFour = request.getParameter("VendorFour");
		String VendorFive = request.getParameter("VendorFive");
		String BillContact = request.getParameter("BillContact");
		String BillEmail = request.getParameter("BillEmail");
		String DeliveryTerm = request.getParameter("DeliveryTerm");
		String ShippingMark = request.getParameter("ShippingMark");
		String ContractNO = request.getParameter("ContractNO");
		String ShipmentPort = request.getParameter("ShipmentPort");
		String SubTotal = request.getParameter("SubTotal");
		String Discounted = request.getParameter("Discounted");
		String FinalTotal = request.getParameter("FinalTotal");
		String CreditTerm = request.getParameter("CreditTerm");
		int QuoteID = Integer.parseInt(request.getParameter("QuoteID"));
		String[] ids = request.getParameterValues("POID[]");
//		System.out.println("ids:" + Arrays.toString(ids));
		delete(ids, QuoteID, "OtherRMB");
		String exist = request.getParameter("Exist");
		po.setVendorOne(VendorOne);
		po.setContractNO(ContractNO);
		po.setDeliveryTerm(DeliveryTerm);
		if (Discounted.equals("--") || Discounted.equals("") || Discounted == null) {
			po.setDiscounted(0);
		} else {
			po.setDiscounted(Double.parseDouble(Discounted));
		}
		po.setVendorTwo(VendorTwo);
		po.setVendorThree(VendorThree);
		if (FinalTotal.equals("--") || FinalTotal.equals("") || FinalTotal == null) {
			po.setFinalTotal(0);
		} else {
			po.setFinalTotal(Double.parseDouble(FinalTotal));
		}
		// po.setForwarderFour(ForwarderFour);
		// po.setForwarderOne(ForwarderOne);
		// po.setForwarderThree(ForwarderThree);
		// po.setForwarderTwo(ForwarderTwo);
		po.setID(ID);
		po.setNumber(Number);
		po.setQuoteID(QuoteID);
		// po.setShipAddr(ShipAddr);
		po.setShipAttn(ShipAttn);
		// po.setShipCompany(ShipCompany);
		po.setVendorFour(VendorFour);
		po.setVendorFive(VendorFive);
		po.setBillContact(BillContact);
		po.setBillEmail(BillEmail);
		po.setShipmentPort(ShipmentPort);
		po.setShippingMark(ShippingMark);
		po.setShipTel(ShipTel);
		po.setCreditTerm(CreditTerm);
		if (SubTotal.equals("--") || SubTotal.equals("") || SubTotal == null) {
			po.setSubTotal(0);
		} else {
			po.setSubTotal(Double.parseDouble(SubTotal));
		}
		if (Version.equals("--") || Version.equals("") || Version == null) {
			po.setVersion("");
		} else {
			po.setVersion(Version);
		}

		boolean temp = false;
		String description = "";
		if (ID == 0) {
			temp = dao.insertOtherSupplierPO(po);
			description = "新增-" + dao.getQuoteNumber(QuoteID) + "其他供应商RMB PO-";
		} else {
			temp = dao.updateQuoteOtherSupplierPO(po);
			description = "修改" + dao.getQuoteNumber(QuoteID) + "其他供应商RMB PO-";
		}
		if (temp) {
			LogInfoService logs = new LogInfoServiceImpl();
			String JspInfo = "报价系统";
			logs.insert(request, JspInfo, description);
		}
		return temp;
	}

	@Override
	public boolean deleteOtherRMBTemp(int id) {
		return new QuoteSystemDao().deleteOtherSupplierTemp(id);
	}

	@Override
	public List<Map<String, Object>> getOtherRMBPO(int id) {
		QuoteSystemDao dao = new QuoteSystemDao();
		List<Map<String, Object>> ls = new ArrayList<>();
		List<Map<String, Object>> part = dao.getOtherSupplierTemp(id);
		List<Map<String, Object>> po = dao.getQuoteOtherSupplierPO(id);
		Map<String, Object> map = new HashMap<>();
		map.put("part", part);
		map.put("POInfo", po);
		ls.add(map);
		return ls;
	}

	@Override
	public boolean exportOtherRMBPOExcel(int id, String path, String image, String user, String email) {
		QuoteSystemDao dao = new QuoteSystemDao();
		List<Map<String, Object>> part1 = dao.getOtherSupplierTemp(id);
		List<Map<String, Object>> part = new ArrayList<>();
		for (int i = 0; i < part1.size(); i++) {
			if (i != 0) {
				part.add(part1.get(i));
			}
		}
		List<Map<String, Object>> po1 = dao.getQuoteOtherSupplierPO(id);
		List<Map<String, Object>> po = new ArrayList<>();
		for (int i = 0; i < po1.size(); i++) {
			if (i != 0) {
				po.add(po1.get(i));
			}
		}
		POExcelUtil util = new POExcelUtil();
		boolean flag = false;
		if (po.size() == 0) {

		} else {
			flag = true;
			util.buildOtherRMBPO(part, po, path, image, user, email);
		}
		return flag;

	}

	@Override
	public boolean updateOtherRMBPOContact(QuoteOtherSupplierPO other) {

		return new QuoteSystemDao().updateQuoteOtherSupplierPO(other);
	}

	@Override
	public int getCommodityCounts() {

		return new QuoteSystemDao().getCommodityCounts();
	}

	@Override
	public List<Map<String, Object>> getCommodityInfo(Page page) {
		return new QuoteSystemDao().getCommodityInfo(page);
	}

	@Override
	public int getCountByClassifyInOne(String classify, Object parameter) {
		Object[] obj = null;
		switch (classify_MAP.get(classify).toString()) {
		case "DeliveryPeriod":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "CostPrice":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "DiscountCost":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "SellerPriceOne":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "SellerPriceTwo":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "SellerPriceThree":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		default:
			obj = new Object[1];
			obj[0] = "%" + parameter + "%";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "select count(ID) from t_commodity_info  ";
		for (int i = 0; i < obj.length; i++) {
			if (classify_MAP.get(classify).equals("DeliveryPeriod") || classify_MAP.get(classify).equals("DiscountCost")
					|| classify_MAP.get(classify).equals("SellerPriceOne")
					|| classify_MAP.get(classify).equals("SellerPriceTwo")
					|| classify_MAP.get(classify).equals("SellerPriceThree")) {
				sql += "where  t_commodity_info." + classify_MAP.get(classify) + " =?";
			} else if (classify_MAP.get(classify).equals("CostPrice")) {
				sql += "where  t_commodity_info." + classify_MAP.get(classify) + " =? and t_commodity_info.QuoteTime<"
						+ "'" + df.format(new Date()) + "'";
			} else {
				sql += "where  t_commodity_info." + classify_MAP.get(classify) + " like ?";
			}
		}

		return new DBUtil().getCountsByName(sql, obj);
	}

	@Override
	public List<Map<String, Object>> getQueryByClassifyInOne(String classify, Object parameter, Page page) {
		Object[] obj = null;
//		System.out.println(classify);
		switch (classify_MAP.get(classify).toString()) {
		case "DeliveryPeriod":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "CostPrice":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "DiscountCost":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "SellerPriceOne":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "SellerPriceTwo":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "SellerPriceThree":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		default:
			obj = new Object[1];
			obj[0] = "%" + parameter + "%";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "select t_commodity_info.ID,t_commodity_info.CommodityName,t_commodity_info.Model,"
				+ "t_commodity_info.Unit,t_commodity_info.DeliveryPeriod,t_commodity_info.ProducingArea,"
				+ "t_commodity_info.CostPrice,t_commodity_info.DiscountCost,"
				+ "t_commodity_info.SellerPriceOne UnitPrice,t_commodity_info.SellerPriceTwo,"
				+ "t_commodity_info.SellerPriceThree,t_commodity_info.Supplier,t_commodity_info.ProductCategory ,"
				+ "t_commodity_info.QuoteTime from t_commodity_info ";
		for (int i = 0; i < obj.length; i++) {
			if (classify_MAP.get(classify).equals("DeliveryPeriod") || classify_MAP.get(classify).equals("DiscountCost")
					|| classify_MAP.get(classify).equals("SellerPriceOne")
					|| classify_MAP.get(classify).equals("SellerPriceTwo")
					|| classify_MAP.get(classify).equals("SellerPriceThree")) {
				sql += "where  t_commodity_info." + classify_MAP.get(classify) + " =?";
			} else if (classify_MAP.get(classify).equals("CostPrice")) {
				sql += "where  t_commodity_info." + classify_MAP.get(classify) + " =? and t_commodity_info.QuoteTime<"
						+ "'" + df.format(new Date()) + "'";
			} else {
				sql += "where  t_commodity_info." + classify_MAP.get(classify) + " like ?";
			}
			
		}
		sql += " order by ID desc limit ?,?";
		Object[] param = new Object[3];
		param[0] = obj[0];
		param[1] = (page.getCurrentPage() - 1) * page.getRows();
		param[2] = page.getRows();
		return new QuoteSystemDao().getQueryList(sql, param);
	}

	@Override
	public int getCountByClassifyInTwo(String classify1, Object parameter1, String classify2, Object parameter2) {
		Object[] obj1 = null;
		switch (classify_MAP.get(classify1).toString()) {
		case "DeliveryPeriod":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "CostPrice":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "DiscountCost":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "SellerPriceOne":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "SellerPriceTwo":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "SellerPriceThree":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + parameter1 + "%";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sql1 = "select count(t_commodity_info.ID) from t_commodity_info  where";
		for (int i = 0; i < obj1.length; i++) {
			if (classify_MAP.get(classify1).equals("DeliveryPeriod") || classify_MAP.get(classify1).equals("DiscountCost")
					|| classify_MAP.get(classify1).equals("SellerPriceOne")
					|| classify_MAP.get(classify1).equals("SellerPriceTwo")
					|| classify_MAP.get(classify1).equals("SellerPriceThree")) {
				sql1 += "  t_commodity_info." + classify_MAP.get(classify1) + " =?";
			} else if (classify_MAP.get(classify1).equals("CostPrice")) {
				sql1 += "  t_commodity_info." + classify_MAP.get(classify1) + " =? and t_commodity_info.QuoteTime<"
						+ "'" + df.format(new Date()) + "'";
			} else {
				sql1 += "  t_commodity_info." + classify_MAP.get(classify1) + " like ?";
			}
		}

		Object[] obj2 = null;
		String sql2 = "";

		switch (classify_MAP.get(classify2).toString()) {
		case "DeliveryPeriod":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "CostPrice":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "DiscountCost":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "SellerPriceOne":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "SellerPriceTwo":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "SellerPriceThree":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		default:
			obj2 = new Object[1];
			obj2[0] = "%" + parameter2 + "%";
		}
		for (int i = 0; i < obj2.length; i++) {
			if (classify_MAP.get(classify2).equals("DeliveryPeriod") || classify_MAP.get(classify2).equals("DiscountCost")
					|| classify_MAP.get(classify2).equals("SellerPriceOne")
					|| classify_MAP.get(classify2).equals("SellerPriceTwo")
					|| classify_MAP.get(classify2).equals("SellerPriceThree")) {
				sql2 += "  t_commodity_info." + classify_MAP.get(classify2) + " =?";
			} else if (classify_MAP.get(classify2).equals("CostPrice")) {
				sql2 += "  t_commodity_info." + classify_MAP.get(classify2) + " =? and t_commodity_info.QuoteTime<"
						+ "'" + df.format(new Date()) + "'";
			} else {
				sql2 += "  t_commodity_info." + classify_MAP.get(classify2) + " like ?";
			}
		}
		String sql = sql1 + " and " + sql2;
		Object[] param = new Object[obj1.length + obj2.length];
		param[0] = obj1[0];
		param[1] = obj2[0];
		return new DBUtil().getCountsByName(sql, param);
	}

	@Override
	public List<Map<String, Object>> getQueryByClassifyInTwo(String classify1, Object parameter1, String classify2,
			Object parameter2, Page page) {
		Object[] obj1 = null;
		switch (classify_MAP.get(classify1).toString()) {
		case "DeliveryPeriod":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "CostPrice":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "DiscountCost":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "SellerPriceOne":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "SellerPriceTwo":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "SellerPriceThree":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + parameter1 + "%";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sql1 = "select t_commodity_info.ID,t_commodity_info.CommodityName,t_commodity_info.Model,"
				+ "t_commodity_info.Unit,t_commodity_info.DeliveryPeriod,t_commodity_info.ProducingArea,"
				+ "t_commodity_info.CostPrice,t_commodity_info.DiscountCost,"
				+ "t_commodity_info.SellerPriceOne UnitPrice,t_commodity_info.SellerPriceTwo,"
				+ "t_commodity_info.SellerPriceThree,t_commodity_info.Supplier,t_commodity_info.ProductCategory ,"
				+ "t_commodity_info.QuoteTime from t_commodity_info ";
		for (int i = 0; i < obj1.length; i++) {
			if (classify_MAP.get(classify1).equals("DeliveryPeriod") || classify_MAP.get(classify1).equals("DiscountCost")
					|| classify_MAP.get(classify1).equals("SellerPriceOne")
					|| classify_MAP.get(classify1).equals("SellerPriceTwo")
					|| classify_MAP.get(classify1).equals("SellerPriceThree")) {
				sql1 += "where  t_commodity_info." + classify_MAP.get(classify1) + " =?";
			} else if (classify_MAP.get(classify1).equals("CostPrice")) {
				sql1 += "where  t_commodity_info." + classify_MAP.get(classify1) + " =? and t_commodity_info.QuoteTime<"
						+ "'" + df.format(new Date()) + "'";
			} else {
				sql1 += "where  t_commodity_info." + classify_MAP.get(classify1) + " like ?";
			}
		}

		Object[] obj2 = null;
		String sql2 = "";

		switch (classify_MAP.get(classify2).toString()) {
		case "DeliveryPeriod":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "CostPrice":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "DiscountCost":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "SellerPriceOne":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "SellerPriceTwo":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "SellerPriceThree":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		default:
			obj2 = new Object[1];
			obj2[0] = "%" + parameter2 + "%";
		}
		for (int i = 0; i < obj2.length; i++) {
			if (classify_MAP.get(classify2).equals("DeliveryPeriod") || classify_MAP.get(classify2).equals("DiscountCost")
					|| classify_MAP.get(classify2).equals("SellerPriceOne")
					|| classify_MAP.get(classify2).equals("SellerPriceTwo")
					|| classify_MAP.get(classify2).equals("SellerPriceThree")) {
				sql2 += "  t_commodity_info." + classify_MAP.get(classify2) + " =?";
			} else if (classify_MAP.get(classify2).equals("CostPrice")) {
				sql2 += "  t_commodity_info." + classify_MAP.get(classify2) + " =? and t_commodity_info.QuoteTime<"
						+ "'" + df.format(new Date()) + "'";
			} else {
				sql2 += "  t_commodity_info." + classify_MAP.get(classify2) + " like ?";
			}
		}
		String sql = sql1 + " and " + sql2 + " order by ID desc limit ?,?";
		Object[] param = new Object[obj1.length + obj2.length + 2];
		param[0] = obj1[0];
		param[1] = obj2[0];
		param[2] = (page.getCurrentPage() - 1) * page.getRows();
		param[3] = page.getRows();
		return new QuoteSystemDao().getQueryList(sql, param);
	}

	@Override
	public List<Map<String, Object>> getCascadePOByQuoteID(int quoteID) {
		return new QuoteSystemDao().getCascadePOByQuoteID(quoteID);
	}

	@Override
	public List<Map<String, Object>> getOtherPOByQuoteID(int quoteID) {
		return new QuoteSystemDao().getOtherPOByQuoteID(quoteID);
	}

	@Override
	public List<Map<String, Object>> getOtherRMBPOByQuoteID(int quoteID) {
		return new QuoteSystemDao().getOtherRMBPOByQuoteID(quoteID);
	}

	@Override
	public boolean insertModel(QuoteSystemModel model) {
		String modelJson = model.getModelJson();
		List<Map<String, Object>> list = new ArrayList<>();
		if(modelJson!=null && !modelJson.equals("")){
			JSONArray array = JSONArray.fromObject(modelJson);
			Map<String,Object> updateMap = null;
			for(int i = 0;i < array.size();i ++){
				JSONObject object = array.getJSONObject(i);
				updateMap = new HashMap<>();
				updateMap.put("ID", Integer.parseInt(object.get("ID").toString()));
				if(model.getType().equals("PartsUSD")){
					updateMap.put("NewUnitPrice",Double.parseDouble(object.get("NewUnitPrice").toString()));
					updateMap.put("NewExtended",Double.parseDouble(object.get("NewExtended").toString()));
					updateMap.put("UnitPriceColumn","UnitPriceUSD");
					updateMap.put("ExtendedColumn","ExtendedUSD");
					
				}
				if(model.getType().equals("PartsRMB")){
					updateMap.put("NewUnitPrice",Double.parseDouble(object.get("NewUnitPrice").toString()));
					updateMap.put("NewExtended",Double.parseDouble(object.get("NewExtended").toString()));
					updateMap.put("UnitPriceColumn","UnitPriceRMB");
					updateMap.put("ExtendedColumn","ExtendedRMB");
				}
				updateMap.put("Qty", Integer.parseInt(object.get("Qty").toString()));
				list.add(updateMap);
			}
			
		}
		QuoteSystemDao dao = new QuoteSystemDao();
		boolean flag = dao.insertModel(model,list);
		
		return flag;
	}

	@Override
	public boolean modifyModel(QuoteSystemModel model) {
		String modelJson = model.getModelJson();
		List<Map<String, Object>> list = new ArrayList<>();
		if(modelJson!=null && !modelJson.equals("")){
			JSONArray array = JSONArray.fromObject(modelJson);
			Map<String,Object> updateMap = null;
			for(int i = 0;i < array.size();i ++){
				JSONObject object = array.getJSONObject(i);
				updateMap = new HashMap<>();
				updateMap.put("ID", Integer.parseInt(object.get("ID").toString()));
				if(model.getType().equals("PartsUSD")){
					updateMap.put("NewUnitPrice",Double.parseDouble(object.get("NewUnitPrice").toString()));
					updateMap.put("NewExtended",Double.parseDouble(object.get("NewExtended").toString()));
					updateMap.put("UnitPriceColumn","UnitPriceUSD");
					updateMap.put("ExtendedColumn","ExtendedUSD");
					
				}
				if(model.getType().equals("PartsRMB")){
					updateMap.put("NewUnitPrice",Double.parseDouble(object.get("NewUnitPrice").toString()));
					updateMap.put("NewExtended",Double.parseDouble(object.get("NewExtended").toString()));
					updateMap.put("UnitPriceColumn","UnitPriceRMB");
					updateMap.put("ExtendedColumn","ExtendedRMB");
				}
				updateMap.put("Qty", Integer.parseInt(object.get("Qty").toString()));
				list.add(updateMap);
			}
			
		}
		return new QuoteSystemDao().updateModel(model,list);
	}

	@Override
	public String getQuoteNumber(int id) {
		return new QuoteSystemDao().getQuoteNumber(id);
	}

	public void delete(String[] ids, int quoteID, String type) {// 删除合同、备货清单、PO的商品信息
//		System.out.println(Arrays.toString(ids));

		DBUtil db = new DBUtil();
		Object[] param = new Object[] { quoteID };
		String sql = "SELECT ID FROM ";
		String sql2 = "delete from ";
		if (type.equals("RMB")) {
			sql += "t_commodity_contracts";
			sql2 += "t_commodity_contracts";
		}
		if (type.equals("USD")) {
			sql += "t_commodity_contracts_usd";
			sql2 += "t_commodity_contracts_usd";
		}
		if (type.equals("Delivery")) {
			sql += "t_quote_delivery";
			sql2 += "t_quote_delivery";
		}
		if (type.equals("Cascade")||type.equals("CascadeComplete")) {
			sql += "t_quote_cascade_temp";
			sql2 += "t_quote_cascade_temp";
		}
		
		if (type.equals("Other")) {
			sql += "t_quote_other_temp";
			sql2 += "t_quote_other_temp";
		}
		if (type.equals("OtherRMB")) {
			sql += "t_quote_other_supplier_temp";
			sql2 += "t_quote_other_supplier_temp";
		}
		if(type.equals("Cascade")){
			sql += " WHERE QuoteID=? and Type='Parts'";
		}else
		if(type.equals("CascadeComplete")){
			sql += " WHERE QuoteID=? and Type='Complete'";
		}else{
			sql += " WHERE QuoteID=?";
		}
		sql2 += " where ID=?";
		List<Map<String, Object>> ls = db.QueryToList(sql, param);// 数据库的id
		for (int j = 1; j < ls.size(); j++) {
			String temp = ls.get(j).get("ID").toString();
			boolean flag = false;
			if (ids != null) {
				for (int i = 0; i < ids.length; i++) {
					if (!ids[i].equals("0") && ids[i] != null) {
						if (temp.equals(ids[i])) {
							flag = true;
							break;
						}
					}
				}
			}

			if (!flag) {
				DBUtil db2 = new DBUtil();
				Object[] param2 = new Object[] { Integer.parseInt(temp) };
				try {
					int count = db2.executeUpdateNotClose(sql2, param2);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	public void exportCostExcel(int id,HttpServletResponse response){
		List<Map<String, Object>> item = new QuoteSystemDao().getQuoteTemp(id);
		List<Map<String, Object>> price = new QuoteSystemDao().getModelInfo(id, "CompleteUSD");
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("成本对比表"+".xls");
	    HSSFCellStyle styleRight = workbook.createCellStyle();  
	    styleRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT); 
	    styleRight.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    styleRight.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	    styleRight.setBorderBottom(BorderStyle.THIN);
	    styleRight.setBottomBorderColor(HSSFColor.BLACK.index);
	    styleRight.setBorderTop(BorderStyle.THIN);
	    styleRight.setTopBorderColor(HSSFColor.BLACK.index);
	    styleRight.setBorderLeft(BorderStyle.THIN);
	    styleRight.setLeftBorderColor(HSSFColor.BLACK.index);
	    styleRight.setBorderRight(BorderStyle.THIN);
	    styleRight.setRightBorderColor(HSSFColor.BLACK.index);
	    styleRight.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
	   
	 
	    HSSFCellStyle styleCenter = workbook.createCellStyle(); 
	    styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    
	
	    CellRangeAddress title = new CellRangeAddress(0,0,0,5);
  	    sheet.addMergedRegion(title);  
  	    HSSFRow titleRow = sheet.createRow(0);
  	   
  	    titleRow.setHeight((short) 500);
	    HSSFCell titleCell = titleRow.createCell(0);
	    HSSFCellStyle styleTitle = workbook.createCellStyle();
  	    HSSFFont font = workbook.createFont();
  	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
  	    styleTitle.setFont(font);//单元格样式
  	    styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
  	    titleCell.setCellStyle(styleTitle);//给cell1这个单元格设置样式
	    titleCell.setCellValue("成本对比表");
	    
	    HSSFCellStyle styleHeader = workbook.createCellStyle(); 
	    styleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    styleHeader.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	    styleHeader.setBorderBottom(BorderStyle.THIN);
	    styleHeader.setBottomBorderColor(HSSFColor.BLACK.index);
	    styleHeader.setBorderTop(BorderStyle.THIN);
	    styleHeader.setTopBorderColor(HSSFColor.BLACK.index);
	    styleHeader.setBorderLeft(BorderStyle.THIN);
	    styleHeader.setLeftBorderColor(HSSFColor.BLACK.index);
	    styleHeader.setBorderRight(BorderStyle.THIN);
	    styleHeader.setRightBorderColor(HSSFColor.BLACK.index);

	    HSSFRow header = sheet.createRow(1);

	    HSSFCell head = header.createCell(0);
	    head.setCellValue("Item");
	    head.setCellStyle(styleHeader);
	    HSSFCell head1 = header.createCell(1);
	    head1.setCellValue("Part Number");
	    head1.setCellStyle(styleHeader);
	    HSSFCell head2 = header.createCell(2);
	    head2.setCellValue("Description");
	    head2.setCellStyle(styleHeader);
	    HSSFCell head3 = header.createCell(3);
	    head3.setCellValue("Qty");
	    head3.setCellStyle(styleHeader);
	    HSSFCell head4 = header.createCell(4);
	    head4.setCellValue("Unit Price");
	    head4.setCellStyle(styleHeader);
	    HSSFCell head5 = header.createCell(5);
	    head5.setCellValue("Total Price");
	    head5.setCellStyle(styleHeader);
	    for(int i = 1;i < item.size();i ++){
	    	HSSFRow row = sheet.createRow(i+1);
	    	HSSFCell cell = row.createCell(0);
	    	cell.setCellValue(i);
	    	cell.setCellStyle(styleCenter);
	    	HSSFCell cell1 = row.createCell(1);
	    	cell1.setCellValue(item.get(i).get("Model").toString());
	    	cell1.setCellStyle(styleCenter);
	    	HSSFCell cell2 = row.createCell(2);
	    	cell2.setCellValue(item.get(i).get("Description").toString());
	    	cell2.setCellStyle(styleCenter);
	    	HSSFCell cell3 = row.createCell(3);
	    	int qty = item.get(i).get("Qty").equals("--")?0:Integer.parseInt(item.get(i).get("Qty").toString());
	    	cell3.setCellValue(qty);
	    	cell3.setCellStyle(styleCenter);
	    	HSSFCell cell4 = row.createCell(4);
	    	double costPrice = item.get(i).get("CostPrice").equals("--")?0.00:Double.parseDouble(item.get(i).get("CostPrice").toString());
	    	cell4.setCellValue(costPrice);
	    	cell4.setCellStyle(styleCenter);
	    	HSSFCell cell5 = row.createCell(5);
	    	cell5.setCellFormula("PRODUCT(D"+(i+2)+",E"+(i+2)+")");
	    	cell5.setCellStyle(styleCenter);
	    }
	    
	    int start = item.size()+1;
	    CellRangeAddress cra1 = new CellRangeAddress(start,start,0,4);
	    sheet.addMergedRegion(cra1);
	    CellRangeAddress cra2 = new CellRangeAddress(start+1,start+1,0,4);
	    sheet.addMergedRegion(cra2);
	    CellRangeAddress cra3 = new CellRangeAddress(start+2,start+2,0,4);
	    sheet.addMergedRegion(cra3);
	    CellRangeAddress cra4 = new CellRangeAddress(start+3,start+3,0,4);
	    sheet.addMergedRegion(cra4);
	    CellRangeAddress cra5 = new CellRangeAddress(start+4,start+4,0,4);
	    sheet.addMergedRegion(cra5);
	
	    HSSFRow row1 = sheet.createRow(start);
	    HSSFCell cell11 = row1.createCell(0);
	    cell11.setCellValue("运费USD：");
	    for(int i = 1;i < 5;i ++){
	    	HSSFCell cell  = row1.createCell(i);
	    	cell.setCellStyle(styleRight);
	    }
	    HSSFCell cell12 = row1.createCell(5);
	    double finalTotal = price.get(1).get("FinalTotal").equals("--")?0.00:Double.parseDouble(price.get(1).get("FinalTotal").toString());
	    double subTotal = price.get(1).get("SubTotal").equals("--")?0.00:Double.parseDouble(price.get(1).get("SubTotal").toString());
	    
	    cell12.setCellValue(finalTotal - subTotal);
	    HSSFRow row2 = sheet.createRow(start+1);
	    HSSFCell cell21 = row2.createCell(0);
	    cell21.setCellValue("当前成本USD：");
	    for(int i = 1;i < 5;i ++){
	    	HSSFCell cell  = row2.createCell(i);
	    	cell.setCellStyle(styleRight);
	    }
	    HSSFCell cell22 = row2.createCell(5);
	    cell22.setCellFormula("SUM(F3:F"+start+")");
	    HSSFRow row3 = sheet.createRow(start+2);
	    HSSFCell cell31 = row3.createCell(0);
	    cell31.setCellValue("目前报价USD：");
	    for(int i = 1;i < 5;i ++){
	    	HSSFCell cell  = row3.createCell(i);
	    	cell.setCellStyle(styleRight);
	    }
	    HSSFCell cell32 = row3.createCell(5);
	    cell32.setCellValue(finalTotal);
	    HSSFRow row4 = sheet.createRow(start+3);
	    HSSFCell cell41 = row4.createCell(0);
	    cell41.setCellValue("总利润USD：");
	    for(int i = 1;i < 5;i ++){
	    	HSSFCell cell  = row4.createCell(i);
	    	cell.setCellStyle(styleRight);
	    }
	    HSSFCell cell42 = row4.createCell(5);
	    cell42.setCellFormula("IMSUB(F"+(start+3)+",F"+(start+2)+")");
	    HSSFRow row5 = sheet.createRow(start+4);
	    HSSFCell cell51 = row5.createCell(0);
	    cell51.setCellValue("利润率：");
	    for(int i = 1;i < 5;i ++){
	    	HSSFCell cell  = row5.createCell(i);
	    	cell.setCellStyle(styleRight);
	    }
	    HSSFCell cell52 = row5.createCell(5);
	    cell52.setCellFormula("F"+(start+3)+"/F"+(start+2));
	    cell11.setCellStyle(styleRight);
	    cell12.setCellStyle(styleRight);
	    cell21.setCellStyle(styleRight);
	    cell22.setCellStyle(styleRight);
	    cell31.setCellStyle(styleRight);
	    cell32.setCellStyle(styleRight);
	    cell41.setCellStyle(styleRight);
	    cell42.setCellStyle(styleRight);
	    cell51.setCellStyle(styleRight);
	    
	    HSSFCellStyle rate = workbook.createCellStyle();  
	    rate.setAlignment(HSSFCellStyle.ALIGN_RIGHT); 
	    rate.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    rate.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	    rate.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
	    rate.setBorderBottom(BorderStyle.THIN);
	    rate.setBottomBorderColor(HSSFColor.BLACK.index);
	    rate.setBorderTop(BorderStyle.THIN);
	    rate.setTopBorderColor(HSSFColor.BLACK.index);
	    rate.setBorderLeft(BorderStyle.THIN);
	    rate.setLeftBorderColor(HSSFColor.BLACK.index);
	    rate.setBorderRight(BorderStyle.THIN);
	    rate.setRightBorderColor(HSSFColor.BLACK.index);

	    cell52.setCellStyle(rate);
	    sheet.autoSizeColumn((short)0);
	    sheet.autoSizeColumn((short)1);
	    sheet.autoSizeColumn((short)2);
	    sheet.autoSizeColumn((short)3);
	    sheet.autoSizeColumn((short)4);
	    sheet.autoSizeColumn((short)5);
	    BufferedOutputStream fos = null;  
	      try {  
	          String fileName = "成本对比表" + ".xls";  
	          response.setContentType("application/x-msdownload");  
	          response.setHeader("Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));  
	          fos = new BufferedOutputStream(response.getOutputStream());  
	          workbook.write(fos);  
	      } catch (Exception e) {  
	          e.printStackTrace();  
	      } finally {  
	          if (fos != null) {  
	              try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
	          }  
	      }  
		
		
	}
	


	@Override
	public String getCommodityName(String model) {
		return new QuoteSystemDao().getCommodityName(model);
	}

	@Override
	public List<String> GetModelByCategory(HttpServletRequest request) {
		QuoteSystemDao dao = new QuoteSystemDao();
		
		String ProductCategory = request.getParameter("ProductCategory");
		List<String> resultList = new ArrayList<String>();
		if(!ProductCategory.equals("探针台")){
			List<Map<String, Object>> list = new QuoteSystemDao().GetModelByCategory(ProductCategory);
			for(int i=1;i<list.size();i++) {
				resultList.add(list.get(i).get("Model").toString()); //获取需要显示的列表
			}
			
		}else{
			List<Map<String, Object>> list = new QuoteSystemDao().GetModelByCategory(ProductCategory);
			List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
			List<String> ModelList = ModelClassification.getModelList();
			Map<String,String> ClassifyMap = ModelClassification.getClassifyMap();
			List<String> ClassifyList = new ArrayList<String>();
			list2.add(list.get(0));
			for(int i=1;i<list.size();i++) {
				String model = list.get(i).get("Model").toString();
				
				boolean flag = false;
				for(String Model:ModelList) {
					flag = flag||Model.equals(model);
					if(flag) {
						break;   //需要归类
					}
				}
				if(!flag) {   //无需归类
					int numberValue = Integer.parseInt(dao.getNumberValueByRadar(model).get(1).get("Quantity").toString());
					if(numberValue == 0){
						continue;
					}
					list2.add(list.get(i));
					continue;
				}
				
				String Classify = ClassifyMap.get(model);  //获取所属类型
				if(!ClassifyList.contains(Classify)) {    
					int numberValue = Integer.parseInt(dao.getNumberValueByRadar(Classify).get(1).get("Quantity").toString());
					if(numberValue == 0){
						continue;
					}
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("Model",Classify);
					list2.add(map);   
					ClassifyList.add(Classify);//类型列表
				}
				
			}
		
	
			for(int i=1;i<list2.size();i++) {
				resultList.add(list2.get(i).get("Model").toString()); //获取需要显示的列表
			}
		}
		
		 List<OrderWrapper> modelOrderList = new ArrayList<OrderWrapper>(); 
		 Iterator<String> iterator = resultList.iterator();
		 while(iterator.hasNext()){
			 modelOrderList.add(new OrderWrapper(iterator.next()));
		 }
		
		Collections.sort(modelOrderList);
		List<String> orderList = new ArrayList<>();
		for(int i = 0;i < modelOrderList.size();i ++){
			orderList.add(modelOrderList.get(i).getName());
		}
		return orderList;
		
	
		
	}

	@Override
	public Double getUnitPriceScoreByModel(String model) {
		QuoteSystemDao dao = new QuoteSystemDao();
		// 平均值，重复的型号不算！！！！
		double avg = dao.getUnitPriceAvgByModel();
		// 自己的值，重复的型号选择最大的！！！
		double value = dao.getUnitPriceValueByModel(model);
		// 标准差，重复的型号不算！！！
		double stdev = dao.getUnitPriceStdevByModel();

		return stdev == 0 ? 0.5 : (value - avg) / stdev;
	}

	@Override
	public Double getQuantityScoreByModel(String model) {
		return null;
	}

	@Override
	public Double getValueScoreByModel(String model,String startTime,String endTime) {
		QuoteSystemDao dao = new QuoteSystemDao();
		double avg = dao.getValueAvgByModel();
		double value = dao.getValueValueByModel(model,startTime,endTime);
		double stdev = dao.getValueStdevByModel();
		return stdev == 0 ? 0.5 : (value - avg) / stdev;
	}

	@Override
	public Double getClientScoreByModel(String model,String startTime,String endTime) {
		QuoteSystemDao dao = new QuoteSystemDao();
		double avg = dao.getClientAvgByModel();
		double value = dao.getClientValueByModel(model,startTime,endTime);
		double stdev = dao.getClientStdevByModel();
		return stdev == 0 ? 0.5 : (value - avg) / stdev;
	}

	@Override
	public Double getShippingScoreByModel(String model) {
		return 0.5;// stdev==0?0.5:(value-avg)/stdev;
	}
/*
	@Override
	public String[] getRaderByName(String name,String category) {
		QuoteSystemDao dao = new QuoteSystemDao();
		return dao.getRaderByName(name,category);
	}
	*/

	public static void operateBeforeDatas(){
		String sql = "SELECT QuoteID,PartID FROM t_quote_cascade_temp";
		DBUtil db = new DBUtil();
		List<Map<String,Object>> ls = db.QueryToList(sql, null);
		for(int i=1;i<ls.size();i++){
			int QuoteID = Integer.parseInt(ls.get(i).get("QuoteID").toString());
			int PartID = Integer.parseInt(ls.get(i).get("PartID").toString());
			QuoteCascadeTemp temp = new QuoteCascadeTemp();
			temp.setPartID(PartID);
			temp.setQuoteID(QuoteID);
			temp.setType("Complete");
			boolean flag = new QuoteSystemDao().insertCascadeTemp(temp);
		}
		
	}


	@Override
	public boolean operateHuaEgoParts(HttpServletRequest request) {

		List<String> ls = new ArrayList<>();
		CommodityTemp temp = new CommodityTemp();
		String[] ids = request.getParameterValues("ID[]");
		String[] unit = request.getParameterValues("UnitUSD[]");
		String[] DiscountedUSD = request.getParameterValues("DiscountedUSD[]");
		String[] total = request.getParameterValues("TotalPrice[]");
		String[] percent = request.getParameterValues("DiscountedPercent[]");
		String[] remarks = request.getParameterValues("Remarks[]");
		for(int i=0;i<ids.length;i++){
			temp.setID(Integer.parseInt(ids[i]));
			if(unit[i].equals("--")){
				unit[i]="0";
			}
			if(DiscountedUSD[i].equals("--")){
				DiscountedUSD[i]="0";
			}
			if(total[i].equals("--")){
				total[i]="0";
			}
			if(percent[i].equals("--")){
				percent[i]="0";
			}
			if(remarks[i].equals("--")){
				remarks[i]="";
			}
			temp.setUnitUSD(Double.parseDouble(unit[i]));
			temp.setDiscountedUSD(Double.parseDouble(DiscountedUSD[i]));
			temp.setTotalPrice(Double.parseDouble(total[i]));
			temp.setDiscountedPercent(Double.parseDouble(percent[i]));
			temp.setRemarks(remarks[i]);
			if(new QuoteSystemDao().operateHuaEgoParts(temp)){
				ls.add("true");
			}else{
				ls.add("false");
			}
		}
		boolean flag = false;
		if(!ls.contains("false")){
			flag = true;
		}
		
		return flag;
	}

	@Override
	public List<Map<String, Object>> getHuaEgoModel(HttpServletRequest request) {

		int id = request.getParameter("QuoteID")==null?0:Integer.parseInt(request.getParameter("QuoteID"));
		String type = request.getParameter("ExcelType")==null?"":request.getParameter("ExcelType");
		if(type.equals("Parts")){
			return new QuoteSystemDao().getHuaEgoParts(id);
		}else{
			return new QuoteSystemDao().getHuaEgoComplete(id);
		}
		
	}

	@Override
	public List<Map<String,Object>> getCommodityOtherInfo(int iD) {
		return new QuoteSystemDao().getCommodityOtherInfo(iD);
		
	}

	@Override
	public int getCountByOne(String type1, String contect1) {
		
		return new QuoteSystemDao().getCountByOne(type1,contect1);
	}

	@Override
	public List<Map<String, Object>> getcustomersByOne(String type1, String contect1, int currentPage) {
		return new QuoteSystemDao().getcustomersByOne(type1,contect1,currentPage);
	}

	@Override
	public int getCountByTwo(String type1, String contect1, String type2, String contect2) {
		return new QuoteSystemDao().getCountByTwo(type1,contect1,type2,contect2);
	}

	@Override
	public List<Map<String, Object>> getcustomersByTwo(String type1, String contect1, String type2, String contect2,int currentPage) {
		return new QuoteSystemDao().getcustomersByTwo(type1,contect1,type2,contect2,currentPage);
	}

	/*
	public Map<String,String[]>  getRaderByNames(String name, String category) {
		QuoteSystemDao dao = new QuoteSystemDao();
		Map<String,String[]>  map = new HashMap<String,String[]>();
		QuoteSystemService service = new QuoteSystemServiceImpl();
		List<String> ModelList = service.GetModelByCategoryName(name);
		for(String model:ModelList) {
			map.put(model,dao.getRaderByName(model,category));
		}
		return map;
				
	}
	*/
	public Map<String,String[]>  getRaderByNames(String[] modelList) {
		QuoteSystemDao dao = new QuoteSystemDao();
		Map<String,String[]>  map = new LinkedHashMap<String,String[]>();
		QuoteSystemService service = new QuoteSystemServiceImpl();
		double[] maxAndMin = dao.getRaderMaxAndMinByModelList(modelList);
		for(String model:modelList) {
			map.put(model,dao.getRaderByName(model,maxAndMin));
		}
		return map;
	}

	public Map<String, String[]> getDataByNames(String name){
		QuoteSystemDao dao = new QuoteSystemDao();
		Map<String,String[]>  map = new LinkedHashMap<String,String[]>();
		QuoteSystemService service = new QuoteSystemServiceImpl();
		List<String> ModelList = service.GetModelByCategoryName(name);
		long beginTime = System.currentTimeMillis();
		for(String model:ModelList) {
			if(Integer.parseInt(dao.getNumberValueByRadar(model).get(1).get("Quantity").toString())==0){
				continue;
			}
			map.put(model,dao.getDataByName(model));
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		System.out.println("逐个获取原始数据时间为："+costTime);
		return map;
		
	}

	@Override
	public List<String> GetModelByCategoryName(String ProductCategory) {
		List<String> resultList = new ArrayList<String>();
		if(!ProductCategory.equals("探针台")){
			List<Map<String, Object>> list = new QuoteSystemDao().GetModelByCategory(ProductCategory);
			for(int i=1;i<list.size();i++) {
				resultList.add(list.get(i).get("Model").toString());
			}
		}else{
			
			List<Map<String, Object>> list = new QuoteSystemDao().GetModelByCategory(ProductCategory);
			List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
			List<String> ModelList = ModelClassification.getModelList();
			Map<String,String> ClassifyMap = ModelClassification.getClassifyMap();
			List<String> ClassifyList = new ArrayList<String>();
			list2.add(list.get(0));
			for(int i=1;i<list.size();i++) {
				String model = list.get(i).get("Model").toString();
				boolean flag = false;
				for(String Model:ModelList) {
					flag = flag||Model.equals(model);
					if(flag) {
						break;
					}
				}
				if(!flag) {
					list2.add(list.get(i));
					continue;
				}
				String Classify = ClassifyMap.get(model);
				if(!ClassifyList.contains(Classify)) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("Model",Classify);
					list2.add(map);
					ClassifyList.add(Classify);
				}
				
			}
			for(int i=1;i<list2.size();i++) {
				resultList.add(list2.get(i).get("Model").toString());
			}
		}
		 List<OrderWrapper> modelOrderList = new ArrayList<OrderWrapper>(); 
		 Iterator<String> iterator = resultList.iterator();
		 while(iterator.hasNext()){
			 modelOrderList.add(new OrderWrapper(iterator.next()));
		 }
		
		Collections.sort(modelOrderList);
		List<String> orderList = new ArrayList<>();
		for(int i = 0;i < modelOrderList.size();i ++){
			orderList.add(modelOrderList.get(i).getName());
		}
		return orderList;
	
	
	}

	@Override
	public List<Map<String, Object>> getAllQuoteNumber(String content) {
		return new QuoteSystemDao().getAllQuoteNumber(content);
	}
	

	@Override
	public List<Map<String, Object>> getCommodityInfo(int id) {
		return new QuoteSystemDao().getCommodityInfo(id);
	}

	@Override
	public String[] getDataByName(String name) {
		QuoteSystemDao dao = new QuoteSystemDao();
		return dao.getDataByName(name);
	}

	@Override
	public boolean operateCascadeMail(HttpServletRequest request,int poID) {
		String Recepter = request.getParameter("Recepter")==null?"":request.getParameter("Recepter");
		String CopyList = request.getParameter("CopyList")==null?"":request.getParameter("CopyList");
		String Content1 = request.getParameter("Content1")==null?"":request.getParameter("Content1");
		String Content = request.getParameter("Content")==null?"":request.getParameter("Content");
		int id  = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		System.out.println("======"+Content);
		System.out.println(request.getParameter("Content1"));
		System.out.println("id=="+id);
		CascadePOMail mail = new CascadePOMail();
		mail.setContent(Content);
		mail.setContent1(Content1);
		mail.setCopyList(CopyList);
		mail.setRecepter(Recepter);
		mail.setID(id);
		mail.setPOID(poID);
		return id==0?new QuoteSystemDao().insert(mail):new QuoteSystemDao().update(mail);
	}

	@Override
	public List<Map<String, Object>> getCascadeMail(int poID) {
		return new QuoteSystemDao().getCascadeMail(poID);
	}

	@Override
	public boolean queryQuoteNumber(String number) {
		QuoteSystemDao dao = new QuoteSystemDao();
		List<Map<String, Object>> ls = dao.queryQuoteNumber(number);
		if(ls.size()>1){
			return true; 
		}else{
			return false;
		}
		
	}

	@Override
	public List<Map<String, Object>> getCommodityInfoByTitle(String name) {
		return new QuoteSystemDao().getCommodityInfoByTitle(name);
	}
	

	public void updateClassify(){
		
		List<Map<String, Object>> list = new QuoteSystemDao().GetModelByCategory("探针台");

		String classify = "";
		for(int i=1;i<list.size();i++) {
			String model = list.get(i).get("Model").toString().trim();
			String temp = model.replaceAll(" ", "");
			if(temp.toUpperCase().startsWith("1495".toUpperCase())){
				classify = "PA300PS-MA";
			}
			else if(temp.toUpperCase().startsWith("CM300-F".toUpperCase())){
				classify = "CM300-F";
			}
			else if(temp.toUpperCase().startsWith("CM300-O".toUpperCase())){
				classify = "CM300-O";
			}
			else if(temp.toUpperCase().startsWith("CM300-S".toUpperCase())){
				classify = "CM300-S";
			}
			else if(temp.toUpperCase().startsWith("ELITE300/AP".toUpperCase())){
				classify = "ELITE 300/AP";
			}
			else if(temp.toUpperCase().startsWith("ELITE300/M".toUpperCase())){
				classify = "ELITE 300/M";
			}
			else if(temp.toUpperCase().startsWith("SPS200TESLA".toUpperCase())){
				classify = "SPS200TESLA";
			}
			else if(temp.toUpperCase().startsWith("TESLA300/AP".toUpperCase())){
				classify = "TESLA 300/AP";
			}
			else if(temp.toUpperCase().startsWith("TESLA300/M".toUpperCase())){
				classify = "TESLA 300/M";
			}
			else if(temp.toUpperCase().startsWith("APS200TESLA".toUpperCase())){
				classify = "APS200TESLA";
			}
			else if(temp.toUpperCase().startsWith("PA200-BR".toUpperCase())){
				classify = "PA200-BR";
			}
			else if(temp.toUpperCase().startsWith("PA200DS-BR".toUpperCase())){
				classify = "PA200DS-BR";
			}
			else if(temp.toUpperCase().startsWith("PM8-COAX".toUpperCase())){
				classify = "PM8-COAX";
			}
			else if(temp.toUpperCase().startsWith("EPS150RF".toUpperCase())||temp.toUpperCase().startsWith("EPS150LT".toUpperCase())){
				classify = "EPS150RF";
			}
			else if(temp.toUpperCase().startsWith("EPS150COAX".toUpperCase())||temp.toUpperCase().startsWith("MPS150".toUpperCase())
					||temp.toUpperCase().startsWith("ES-150-PACK".toUpperCase())||temp.toUpperCase().startsWith("M150".toUpperCase())){
				classify = "EPS150COAX";
			}
			else if(temp.toUpperCase().startsWith("PLV50".toUpperCase())){
				classify = "PLV50";
			}
			else if(temp.toUpperCase().startsWith("PMV200".toUpperCase())){
				classify = "PMV200";
			}else{
				continue;
			}
			
			DBUtil dbUtil = new DBUtil();
			String sql = "update t_commodity_info set Classify = ? WHERE Model =? ";
			System.out.println(dbUtil.executeUpdate(sql, new Object[]{classify,model}));

		}		

	}

	@Override
	public String setRadaTime(String startTime,String endTIme) {
		boolean flag = false;
		Properties prop = new Properties();
		try{
			 
	        InputStream fis = SendMailUtil.class.getResourceAsStream("rada.properties");  
	        prop.load(fis);  
	        fis.close();  
	        String path = SendMailUtil.class.getResource("rada.properties").getPath();
	        System.out.println(path);
	        OutputStream fos = new FileOutputStream(SendMailUtil.class.getResource("rada.properties").getPath().replace("%20", " "));  
	        prop.setProperty("startTime", startTime);  
	        prop.setProperty("endTime", endTIme);

	        prop.store(fos, null);  
	        flag = true;
	        fos.close();  
		} catch (IOException e) {
			e.printStackTrace();
		
		}
		return flag?"设置成功":"设置失败";
	}

	@Override
	public Map<String, String> getTime() {
		Properties prop = new Properties();
		String startTime = "";
		String endTime = "";
		try{
			 
	        InputStream fis = SendMailUtil.class.getResourceAsStream("rada.properties");  
	        prop.load(fis);  
	        startTime = prop.getProperty("startTime");
	        endTime = prop.getProperty("endTime");
	        fis.close();  
		}catch(IOException e){
			e.printStackTrace();
			
		}
		Map<String, String> map = new HashMap<>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return map;
	}

	@Override
	public List<Map<String, Object>> getSalesVolume(String category,String model,String column, Page page) {
		QuoteSystemDao dao = new QuoteSystemDao();

		return dao.GetModelByPage(category, model,column, page);
	}

	@Override
	public int getModelCounts(String category,String model) {
		QuoteSystemDao dao = new QuoteSystemDao();
		int count = dao.getModelCounts(category,model);
		return count;
	}
	

       
}
