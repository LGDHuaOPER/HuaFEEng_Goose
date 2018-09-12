/**
 * 
 */
package com.eoulu.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.eoulu.commonality.Page;
import com.eoulu.dao.LogisticsDao;
import com.eoulu.dao.OrderDao;
import com.eoulu.dao.OrderInfoDao;
import com.eoulu.dao.PaymentStatusDao;
import com.eoulu.dao.PaymentTermsDao;
import com.eoulu.dao.QuotesDao;
import com.eoulu.dao.SupplierBankDao;
import com.eoulu.entity.BillCharge;
import com.eoulu.entity.CompleteOrder;
import com.eoulu.entity.Consignee;
import com.eoulu.entity.Logistics;
import com.eoulu.entity.Order;
import com.eoulu.entity.OrderInfo;
import com.eoulu.entity.PurchaseInfo;
import com.eoulu.entity.Quotes;
import com.eoulu.entity.SupplierBank;
import com.eoulu.service.OrderService;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.RequirementService;
import com.eoulu.service.TransportService;
import com.eoulu.syn.ExportApplicationInvoice;
import com.eoulu.util.DBUtil;
import com.eoulu.util.EXCELUtil;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;
import com.eoulu.util.ZipUtils;
import com.mysql.jdbc.Connection;

/**
 * @author zhangkai
 *
 */
public class OrderServiceImpl implements OrderService {

	/**
	 * 常量键值对，order表的信息，用来搜索的时候方便处理前台的数据，前台搜索条件于数据库表的对应关系
	 */
	public static final Map<String, Object> classify_MAP;

	static {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("销售代表","SalesRepresentative");
		map.put("采购人员", "SalesRepresentative");
		map.put("合同号", "ContractNo");
		map.put("客户名称", "Customer");
		map.put("供应商名称", "Customer");
		map.put("区域", "Area");
		map.put("合同名称", "ContractTitle");
		map.put("联系人", "Contact");
		map.put("联系方式", "ContactInfo");
		map.put("合同签订日期", "DateOfSign");
		map.put("合同货期", "CargoPeriod");
		map.put("实际货期", "ActualDelivery");
		map.put("预计货期", "ExpectedDeliveryPeriod");
		map.put("合同状态", "Status");
		map.put("装机时间", "InstalledTime");
		map.put("装机地点", "InstalledSite");
		map.put("备注", "Remarks");
		map.put("合同类型", "ContractCategory");
		map.put("型号", "Model");

		classify_MAP = map;
	}

	/**
	 * 
	 * 插入报价信息，包含合同配置信息
	 * 
	 * @return 0为失败 1为成功
	 * 
	 */
	@Override
	public int orderAdd(CompleteOrder completeOrder) {

		int flag = 0;
		Order order = completeOrder.getOrder();
		Quotes quotes = completeOrder.getQuotes();
		// System.out.println(order.getID());
		OrderDao od = new OrderDao();
		QuotesDao qd = new QuotesDao();
		DBUtil db = new DBUtil();
		Connection connection = db.getConnection();

		int orderID = 0;

		// 根据是否付款的中文，从数据库中读取，是否存在该是否付款，如果不存在则添加到数据库

		// 订单的添加 开始
		try {
			// 事务管理 合同配置与订单共同添加
			connection.setAutoCommit(false);
			od.insert(order, db); // 添加订单
			if(od.isExist(order)){
				return -1;
			}
			orderID = od.getOrderID(order.getContractNo(), db);
			//System.out.println("hey" + orderID);
			quotes.setOrderID(orderID);
			qd.insert(quotes, db); // 添加合同配置
			connection.commit();
			flag = 1;
		} catch (SQLException e1) {
			try {
				connection.rollback();
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			e1.printStackTrace();
		} finally {
			db.closed();
		}
		// 订单的添加 结束
		// if(od.insert(order,db)){
		// orderID = od.getOrderID(order.getContractNo());
		// if(orderID>0){
		// quotes.setOrderID(orderID);
		// if(qd.insert(quotes,db)){
		// flag = 1;
		// }
		// }
		// }

		return flag;
	}

	/**
	 * 
	 * 将添加的合同信息转化成对象
	 * 
	 */
	@Override
	public CompleteOrder reqToObject(HttpServletRequest request) {

		CompleteOrder completeOrder = new CompleteOrder();
		Order order = new Order();
		RequirementService service = new RequirementServiceImpl();
		
		// orderID获取
		int id = Integer.parseInt(request.getParameter("id")==null?"0":request.getParameter("id"));
		//int customerID = Integer.parseInt(request.getParameter("customerID")==null?"0":request.getParameter("customerID"));
		int areaNum = 0;
		if(request.getParameter("PageType").equals("0")){
			int customerID = Integer.parseInt(service.getCustomerID(request.getParameter("customer")));
			System.out.println(customerID);
			String area = service.getProvince(customerID).get("area").toString();
	
			switch(area){
			case"南方":areaNum = 2;break;
			case"北方":areaNum = 3;break;
			case"西南区":areaNum = 1;break;
			}
		}else{
			areaNum = Integer.parseInt(request.getParameter("area").equals("")?"1":request.getParameter("area"));
			
		}
		try {
			order.setID(id);
		} catch (Exception e) {
		}

		// 订单信息获取开始=======================================
		
		try {
			order.setActualPaymentTime(request.getParameter("ActualPaymentTime") == null?"":request.getParameter("ActualPaymentTime"));
			
		} catch (Exception e) {
		}
		try {
			order.setCustomer(request.getParameter("customer"));
		} catch (Exception e) {
		}
		try {
			order.setArea(areaNum);
		} catch (Exception e) {
		}
		try {
			order.setContractNo(request.getParameter("contract_no"));
		} catch (Exception e) {
		}
		try {
			order.setContractTitle(request.getParameter("contract_title"));
		} catch (Exception e) {
		}
		try {
			order.setSalesRepresentative(Integer.parseInt(request.getParameter("sales_representative")));
		} catch (Exception e) {
		}
		try {
			order.setContact(request.getParameter("contact"));
		} catch (Exception e) {
		}
		try {
			order.setContactInfo(request.getParameter("contact_info"));
		} catch (Exception e) {
		}
		try {
			order.setDateOfSign(request.getParameter("date_of_sign").equals("")?"0000-00-00":request.getParameter("date_of_sign"));
		} catch (Exception e) {
		}

		try {
			order.setCargoPeriod(request.getParameter("cargo_period"));
		} catch (Exception e) {
		}
		try {
			order.setActualDelivery(request.getParameter("actual_delivery"));
		} catch (Exception e) {
		}
		try {
			order.setExpectedDeliveryPeriod(request.getParameter("expected_delivery_period"));
		} catch (Exception e) {
		}

		try {
			order.setStatus(Integer.parseInt(request.getParameter("status")));
		} catch (Exception e) {
		}
		try {
			order.setInstalledTime(request.getParameter("installed_time"));
		} catch (Exception e) {
		}
		try {
			order.setInstalledSite(request.getParameter("installed_site"));
		} catch (Exception e) {
		}
		try {
			order.setRemarks(request.getParameter("remarks"));
		} catch (Exception e) {
		}
		try {
			order.setContractCategory(Integer.parseInt(request.getParameter("contract_category")));
		} catch (Exception e) {
			order.setContractCategory(8);
		}

		try {
			order.setExpectedReceiptDate(request.getParameter("expected_receipt_date"));
		} catch (Exception e) {
		}
		try {
			order.setIsSend("否");
		} catch (Exception e) {
		}
		order.setPageType(Integer.parseInt(request.getParameter("PageType")));
		
		order.setContractPath(request.getParameter("ContractPath")==null?"":request.getParameter("ContractPath").toString());

		order.setTechnologyPath(request.getParameter("TechnologyPath")==null?"":request.getParameter("TechnologyPath").toString());
		int quoteNumber = Integer.parseInt(request.getParameter("QuoteNumber")==null?"0":request.getParameter("QuoteNumber").toString());
		order.setQuoteNumber(quoteNumber);
		if(id!=0){
			OrderDao dao = new OrderDao();
			if(!dao.getQuoteNumber(id, quoteNumber)){
				boolean flag = new OrderInfoDao().deleteCommodity(id);
				System.out.println("删除原匹配"+flag);
			}
		}
		
		// 订单信息获取结束=======================================

		// order.setCustomer(request.getParameter("customer"));
		// order.setArea(Integer.parseInt(request.getParameter("area")));
		// order.setContractNo(request.getParameter("contract_no"));
		// order.setContractTitle(request.getParameter("contract_title"));
		// order.setSalesRepresentative(Integer.parseInt(request.getParameter("sales_representative")));
		// order.setContact(request.getParameter("contact"));
		// order.setContactInfo(request.getParameter("contact_info"));
		// order.setDateOfSign(request.getParameter("date_of_sign"));
		// order.setCargoPeriod(request.getParameter("cargo_period"));
		// order.setActualDelivery(request.getParameter("actual_delivery"));
		// order.setExpectedDeliveryPeriod(request.getParameter("expected_delivery_period"));
		// order.setStatus(Integer.parseInt(request.getParameter("status")));
		// order.setInstalledTime(request.getParameter("installed_time"));
		// order.setInstalledSite(request.getParameter("installed_site"));
		// order.setRemarks(request.getParameter("remarks"));

		completeOrder.setOrder(order);

		// 合同明細信息赋值
		Quotes quotes = new Quotes();
		quotes.setOrderID(order.getID());
		try {
			quotes.setRMBQuotes(Double.parseDouble(request.getParameter("rmb_quotes")));
		} catch (Exception e) {
		}
		try {
			quotes.setUSDQuotes(Double.parseDouble(request.getParameter("usd_quotes")));
		} catch (Exception e) {
		}

		// System.out.println(request.getParameter("payment_terms"));
		try {
			quotes.setPaymentTerms(getPaymentTermsID(request.getParameter("payment_terms")));
		} catch (Exception e) {
		}
		try {
			quotes.setWhetherToPay(getPaymentStatusID(request.getParameter("whether_to_pay")));
		} catch (Exception e) {
		}
		try {
			quotes.setWhetherToPayRemarks(request.getParameter("whether_to_pay_remarks"));
		} catch (Exception e) {
		}
		try {
			quotes.setWhetherToInvoice(request.getParameter("whether_to_invoice").equals("1") ? 1 : 0);
		} catch (Exception e) {
		}
		try {
			quotes.setWhetherToInvoiceRemarks(request.getParameter("whether_to_invoice_remarks"));
		} catch (Exception e) {
		}
		try {
			quotes.setDutyFree(Integer.parseInt(request.getParameter("duty_free")));
		} catch (Exception e) {
		}
		try {
			quotes.setDutyFreeRemarks(request.getParameter("duty_free_remarks"));
		} catch (Exception e) {
		}

		try {
			quotes.setReceiptDate1(request.getParameter("receipt_date1"));
		} catch (Exception e) {
		}
		try {
			quotes.setReceiptAmount1(Float.parseFloat(request.getParameter("receipt_amount1")));
		} catch (Exception e) {
		}
		try {
			quotes.setReceiptDate2(request.getParameter("receipt_date2"));
		} catch (Exception e) {
		}
		try {
			quotes.setReceiptAmount2(Float.parseFloat(request.getParameter("receipt_amount2")));
		} catch (Exception e) {
		}
		try {
			quotes.setReceiptDate3(request.getParameter("receipt_date3"));
		} catch (Exception e) {
		}
		try {
			quotes.setReceiptAmount3(Float.parseFloat(request.getParameter("receipt_amount3")));
		} catch (Exception e) {
		}
		try {
			quotes.setBillingDate(
					(request.getParameter("billing_date") == null ? "" : request.getParameter("billing_date")));
		} catch (Exception e) {
		}
		try{
			quotes.setPayDate(request.getParameter("pay_date") == null? "":request.getParameter("pay_date"));
		} catch (Exception e){
		}
		try {
			quotes.setTrackingNo(request.getParameter("tracking_no") == null? "":request.getParameter("tracking_no"));
		}catch (Exception e){
			
		}
	

		completeOrder.setQuotes(quotes);

		return completeOrder;
	}

	/**
	 * 
	 * 修改合同号对应订单的合同配置
	 * 
	 * @return 0为失败 1为成功
	 */
	@Override
	public int modifyQuotes(Quotes quotes, int id) {
		int flag = 0;
		OrderDao od = new OrderDao();
		QuotesDao qd = new QuotesDao();
		int ID = id;

		if (qd.modify(quotes)) {
			flag = 1;
		}
		return flag;
	}

	/**
	 * 插入订单明细信息
	 * 
	 * @param orderInfo
	 * 
	 * @return 插入的行数
	 * 
	 */
	@Override
	public int insertOrderInfo(HttpServletRequest request) {

		OrderInfo orderInfo = new OrderInfo();

		// orderInfo属性填充
		orderInfo.setOrderID(new OrderDao().getOrderID(request.getParameter("contract_no")));
		orderInfo.setEquipmentModel(1);
		orderInfo.setNumber(Integer.parseInt(request.getParameter("number")));
		orderInfo.setLogisticsNumber(Integer.parseInt(request.getParameter("number")));
		orderInfo.setDate(request.getParameter("date"));
		orderInfo.setDeliveryNumber(request.getParameter("delivery_number"));
		orderInfo.setStatus(Integer.parseInt(request.getParameter("status")));
		orderInfo.setRemarks(request.getParameter("remarks"));

		int flag = new OrderInfoDao().insert(orderInfo);

		return flag;
	}

	/**
	 * 获取所有订单记录数量
	 * 
	 */
	@Override
	public int getAllCounts(int type) {

		return new OrderDao().getAllCounts(type);
	}

	// /**
	// * 可以根据查询条件来获取对应的订单信息数量
	// *
	// * @param classify 查询条件数组
	// * @param parameter 参数名
	// *
	// * */
	// @Override
	// public int getCountByClassify(String[] classify, Object[] parameter) {
	// String sql = "select count(ID) from t_order where ";
	//
	// int length = classify.length;
	//
	// for(int i=0; i<length; i++){
	// parameter[i] = "%"+parameter[i]+"%";
	// }
	//
	// for(int i=0; i<length; i++){
	// sql+=classify_MAP.get(classify[i])+" like ?";
	// if(i<length-1)
	// sql+=" or ";
	// }
	//
	// return new OrderDao().getCountsByName(sql, parameter);
	// }

	/**
	 * 根据单个条件查询记录数量 物流页面单一查询
	 */
	@Override
	public int getCountByClassify(String classify, Object parameter, String condition,int PageType) {
		String sql = null;
		Object[] obj = null;
		if (condition.equals("AllNoSend")) {
			condition = " and t_order.Status<>14 and";
		}
		if (condition.equals("OtherNoSend")) {
			condition = " and t_order.Status<>17 and t_order.Status<>14 and";
		}
		if (condition.equals("All")) {
			condition = "";
		}
		switch (classify_MAP.get(classify).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj = getIDByBlur(classify, parameter.toString());
			break;
		case "ContractCategory":
			obj = getIDByBlur(classify, parameter.toString());
			break;
		default:
			obj = new Object[1];
			obj[0] = "%" + parameter + "%";
		}
		int length = obj.length;
		sql = "select count(ID) from t_order where PageType=? ";
		for (int i = 0; i < length; i++) {
			sql += " and  t_order." + classify_MAP.get(classify) + " like ?";
			if (i < length - 1)
				sql += " or ";
		}
		sql += condition + " ActualDelivery='0000-00-00' or ActualDelivery is null";
		return new OrderDao().getCountsByName(sql, obj);
	}

	/**
	 * 根据多个条件模糊查询数量
	 */
	@Override
	public int getCountByClassify(String classify1, Object parameter1, String classify2, Object parameter2, int type) {
		// 前半句sql语句构建
		String sql1 = null;
		Object[] obj1 = null;

		switch (classify_MAP.get(classify1).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj1 = getIDByBlur(classify1, parameter1.toString());
			break;
		case "ContractCategory":
			obj1 = getIDByBlur(classify1, parameter1.toString());
			break;
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + parameter1 + "%";
		}
		int length1 = obj1.length;
		sql1 = "select count(ID) from t_order where ";
		if (type != 3) {
			sql1 += "PageType=? and ";
		}
		if (classify1.equals("型号")) {
			sql1 += " t_order.ID in (select t_order_info.OrderID ID from t_order_info left join "
					+ "t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID " + "where t_commodity_info.Model like ?)";
		} else {
			sql1+="(";
			for (int i = 0; i < length1; i++) {
				sql1 += "t_order." + classify_MAP.get(classify1) + " like ?";
				if (i < length1 - 1)
					sql1 += " or ";
			}
			sql1+=")";
		}

		// 后半句sql语句构建
		String sql2 = null;
		Object[] obj2 = null;

		switch (classify_MAP.get(classify2).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj2 = getIDByBlur(classify2, parameter2.toString());
			break;
		case "ContractCategory":
			obj2 = getIDByBlur(classify2, parameter2.toString());
			break;
		default:
			obj2 = new Object[1];
			obj2[0] = "%" + parameter2 + "%";
		}
		int length2 = obj2.length;
System.out.println("obj="+Arrays.toString(obj2));
		if (classify2.equals("型号")) {
			sql2 = " t_order.ID in (select t_order_info.OrderID ID from t_order_info left join "
					+ "t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID " + "where t_commodity_info.Model like ?)";
		} else {
			sql2 = "select t_order." + classify_MAP.get(classify2) + " from t_order where ";
			for (int i = 0; i < length2; i++) {
				sql2 += "t_order." + classify_MAP.get(classify2) + " like ?";
				if (i < length2 - 1)
					sql2 += " or ";
			}

		}

		String sql = sql1 + " and (t_order." + classify_MAP.get(classify2) + " in(" + sql2 + "))";
		if(classify2.equals("型号")){
			sql = sql1 + " and " + sql2 ;
		}
		int allCounts = obj1.length + obj2.length;
		Object[] sqlObj = new Object[allCounts];
		int times = 0;
		if (type != 3) {
			allCounts += 1;
			sqlObj = new Object[allCounts];
			sqlObj[0] = type;
			times = 1;
		}
		for (int i = 0; i < length1; i++) {
			sqlObj[times++] = obj1[i];
		}
		for (int i = 0; i < length2; i++) {
			sqlObj[times++] = obj2[i];
		}
		System.out.println(sql);
		return new OrderDao().getCountsByName(sql, sqlObj);
	}

	/**
	 * 通过模糊搜索去属性表中搜索符合条件的ID组
	 * 
	 * @return 符合条件的ID数组
	 */
	@Override
	public Object[] getIDByBlur(String classify, String parameter) {
		String sql = null;
		switch (classify_MAP.get(classify).toString()) {
		case "Area":
			sql = "select ID from t_area where AreaName like ?";
			break;
		case "Status":
			sql = "select ID from t_contract_status where Status like ?";
			break;
		case "SalesRepresentative":
			sql = "select ID from t_sales_representative where Name like ?";
			break;
		case "ContractCategory":
			sql = "select ID from t_requirement_classify where Classify like ?";
			break;
		}

		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, new Object[] { "%" + parameter + "%" });
		// Object[] obj = = new Object[ls.size()-1];
		Object[] obj = null;
		if (ls.size() != 0) {
			obj = new Object[ls.size() - 1];
		} else {
			obj = new Object[1];
		}

		int length = obj.length;
		for (int i = 0; i < length; i++) {
			obj[i] = ls.get(i + 1).get("ID");
		}
		// System.out.println("ID为："+Arrays.toString(obj));
		return obj;
	}

	/**
	 * 根据poso号查询记录数量 物流页面单一查询
	 */
	@Override
	public int getOrderByPageInOneByPoSoCount(String classify, Object parameter, String condition,int PageType) {
		if (condition.equals("AllNoSend")) {
			condition = "a.Status<>14 and";
		}
		if (condition.equals("OtherNoSend")) {
			condition = "a.Status<>17 and a.Status<>14 and";
		}
		if (condition.equals("All")) {
			condition = "";
		}
		String sql = "select a.ID,a.Status from (";
		String sql1 = "";
		switch (classify) {
		case "PO(只能单一搜索)":
			sql1 = "select t_order.ID,t_order.`Status`,t_order.ActualDelivery from t_logistics left join t_order on t_order.ID=t_logistics.OrderID  where t_order.PageType=? and PONO like ? group by OrderID";
			break;
		case "SO(只能单一搜索)":
			sql1 = "select t_order.ID,t_order.`Status`,t_order.ActualDelivery from t_logistics left join t_order on t_order.ID=t_logistics.OrderID  where t_order.PageType=? and SONO like ? group by OrderID";
			break;
		case "供应商(只能单一搜索)":
			sql1 = "select * from t_order where t_order.PageType=? and ID in(select OrderID from t_logistics where  t_logistics.Supplier in (select t_supplier.ID from t_supplier where t_supplier.`Name` like ?))";
			break;
		}
		sql += sql1 + ")a where " + condition + " a.ActualDelivery='0000-00-00' or a.ActualDelivery is null";
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, new Object[] {PageType, "%" + parameter + "%" });

		return ls.size() - 1;
	}

	/**
	 * 通过模糊搜索去搜索POSO符合条件的订单ID
	 * 
	 * @return 符合条件的ID数组
	 */
	@Override
	public List<Map<String, Object>> getOrderByPageInOneByPoSo(String classify, Object parameter, Page page,int PageType) {
		String sql = "select t_quote_system.Number,t_order.TechnologyPath,t_order.ContractPath,t_order.isSend,t_order.ExpectedReceiptDate,t_order.ID,t_order.Customer,t_area.AreaName Area,t_requirement_classify.Classify ContractCategory,t_order.ContractNo,t_order.ContractTitle,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks,DATEDIFF(t_order.ActualDelivery,t_order.CargoPeriod) D3_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod) D3_D4,DATEDIFF(t_order.ExpectedDeliveryPeriod,t_order.CargoPeriod) D4_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.DateOfSign) D3_D1 from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID "
				+ "left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID "
				+ " left join t_quote_system on t_order.QuoteNumber=t_quote_system.ID where PageType=? and t_order.ID in ";
		switch (classify) {
		case "PO(只能单一搜索)":
			sql += "(select OrderID from t_logistics where PONO like ?) ORDER BY t_order.DateOfSign desc limit ?,?";
			break;
		case "SO(只能单一搜索)":
			sql += "(select OrderID from t_logistics where SONO like ?) ORDER BY t_order.DateOfSign desc limit ?,?";
			break;
		case "供应商(只能单一搜索)":
			sql += "(select OrderID from t_logistics where t_logistics.Supplier in (select t_supplier.ID from t_supplier where t_supplier.`Name` like ?)) ORDER BY t_order.DateOfSign desc limit ?,?";
			break;
		}

		Object[] para = new Object[4];
		para[0] = PageType;
		para[1] = "%" + parameter + "%";
		para[2] = (page.getCurrentPage() - 1) * page.getRows();
		para[3] = page.getRows();

		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, para);

		return ls;
	}

	/**
	 * 查询出所有的按照POSO供应商查询的内容
	 */
	public List<Map<String, Object>> getOrderByPageInOneByPoSo(String classify, Object parameter,int type) {
		String sql = "select t_order.ID,t_order.ContractTitle,t_quotes.BillingDate,t_quotes.RMBQuotes,t_payment_terms.ID PaymentTermsID,t_order.ExpectedReceiptDate,t_quotes.USDQuotes,t_payment_terms.Condition PaymentTerms,t_payment_status.Status WhetherToPay,t_duty_free.Status DutyFree,t_quotes.WhetherToInvoice,"
				+ "t_order.ID,t_order.Customer,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,t_quotes.ReceiptDate1,t_quotes.ReceiptAmount1,t_quotes.ReceiptDate2,t_quotes.ReceiptAmount2,t_requirement_classify.Classify ContractCategory,t_quotes.ReceiptDate3,t_quotes.ReceiptAmount3,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID  "
				+ " left join t_quotes on t_quotes.OrderID=t_order.ID left join t_payment_terms on t_quotes.PaymentTerms=t_payment_terms.ID left join t_payment_status "
				+ "on t_quotes.WhetherToPay=t_payment_status.ID left join t_duty_free on t_quotes.DutyFree=t_duty_free.ID left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID where t_order.PageType=? and t_order.ID in ";
		switch (classify) {
		case "PO(只能单一搜索)":
			sql += "(select OrderID from t_logistics where PONO like ?) ORDER BY t_order.DateOfSign desc";
			break;
		case "SO(只能单一搜索)":
			sql += "(select OrderID from t_logistics where SONO like ?) ORDER BY t_order.DateOfSign desc";
			break;
		case "供应商(只能单一搜索)":
			sql += "(select OrderID from t_logistics where t_logistics.Supplier in (select t_supplier.ID from t_supplier where t_supplier.`Name` like ?)) ORDER BY t_order.DateOfSign desc";
			break;
		}

		Object[] para = new Object[]{type,"%" + parameter + "%"};

		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, para);

		return ls;
	}

	// @Test
	// public void test(){
	// Order order = new Order();
	// order.setID(238);
	// order.setActualDelivery("2017-01-11");
	// order.setExpectedDeliveryPeriod("2017-12-10");
	// Page page = new Page();
	// page.setRows(10);
	// page.setCurrentPage(1);
	//
	// System.out.println(getOrderByPageInOneByPoSoCount("供应商(只能单一搜索)",""));
	// }

	/**
	 * 分页获取order信息
	 * 
	 * @param classify
	 *            搜索条件
	 * 
	 * @param parameter
	 *            具体参数
	 * 
	 * @param page
	 *            参数信息
	 * 
	 */
	@Override
	public List<Map<String, Object>> getOrderByPageInOne(String classify, Object parameter, Page page, int type) {
		String sql = null;
		Object[] obj = null;

		String temp = null;
		// 生成参数数组
		switch (classify_MAP.get(classify).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj = getIDByBlur(classify, parameter.toString());
			break;
		case "ContractCategory":
			obj = getIDByBlur(classify, parameter.toString());
			break;
		default:
			obj = new Object[1];
			obj[0] = "%" + parameter + "%";
		}

		// 构建sql语句
		int length = obj.length; // 可能0

		sql = "select t_order.QuoteNumber,t_quote_system.Number,t_order.TechnologyPath,t_order.ContractPath,t_payment_status.Status WhetherToPay,t_order.isSend,t_order.ExpectedReceiptDate,t_order.ActualPaymentTime,t_order.ID,t_order.Customer,t_requirement_classify.Classify ContractCategory,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks,DATEDIFF(t_order.ActualDelivery,t_order.CargoPeriod) D3_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod) D3_D4,DATEDIFF(t_order.ExpectedDeliveryPeriod,t_order.CargoPeriod) D4_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.DateOfSign) D3_D1 from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID "
				+ "left join  t_quotes on t_quotes.OrderID=t_order.ID "
				+ "left join t_payment_status on t_quotes.WhetherToPay=t_payment_status.ID "
				+ "left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID "
				+ " left join t_quote_system on t_order.QuoteNumber=t_quote_system.ID  where ";
		if (type != 3) {
			sql += " PageType=? and ";
		}
		if (classify.equals("型号")) {
			sql += "t_order.ID in (select t_order_info.OrderID ID from t_order_info "
					+ "left join t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID "
					+ " where t_commodity_info.Model like ?)";
		} else {
			if (length == 0) {
				sql += "t_order." + classify_MAP.get(classify) + " = ?";
			}else{
				sql +="(";
			for (int i = 0; i < length; i++) {

				sql += "t_order." + classify_MAP.get(classify) + " like ?";
				if (i < length - 1)
					sql += " or ";
			}
			sql+=")";
			}
		}

		sql += " order by t_order.DateOfSign desc limit ?,?";

		// 构建带有分页信息的参数数组
		Object[] param = null;
		if (length == 0) {
			param = new Object[obj.length + 3];
			param[length] = 0;
			param[length + 1] = (page.getCurrentPage() - 1) * page.getRows();
			param[length + 2] = page.getRows();
			if (type != 3) {
				param = new Object[] { type, 0, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
			}
		} else {
			param = new Object[obj.length + 2];
			for (int i = 0; i < length; i++) {
				param[i] = obj[i];
			}

			param[length] = (page.getCurrentPage() - 1) * page.getRows();
			param[length + 1] = page.getRows();
			if (type != 3) {
				param = new Object[obj.length + 3];
				param[0] = type;
				for (int i = 1; i < length + 1; i++) {
					param[i] = obj[i - 1];
				}

				param[length + 1] = (page.getCurrentPage() - 1) * page.getRows();
				param[length + 2] = page.getRows();
			}
		}

		// System.out.println("参数数组"+Arrays.toString(param));

		return new OrderDao().getOrder(sql, param);
	}

	/**
	 * 单一查询出所有的结果
	 */
	public List<Map<String, Object>> getOrderByPageInOne(String classify, Object parameter) {
		String sql = null;
		Object[] obj = null;

		// 生成参数数组
		switch (classify_MAP.get(classify).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj = getIDByBlur(classify, parameter.toString());
			break;
		case "ContractCategory":
			obj = getIDByBlur(classify, parameter.toString());
			break;
		default:
			obj = new Object[1];
			obj[0] = "%" + parameter + "%";
		}

		// 构建sql语句
		int length = obj.length;
		sql = "select t_order.QuoteNumber,t_quote_system.Number,t_order.TechnologyPath,t_order.ActualPaymentTime,t_order.ContractPath,t_order.ID,t_quotes.BillingDate,t_quotes.RMBQuotes,t_payment_terms.ID PaymentTermsID,t_order.ExpectedReceiptDate,t_quotes.USDQuotes,t_payment_terms.Condition PaymentTerms,t_payment_status.Status WhetherToPay,t_duty_free.Status DutyFree,t_quotes.WhetherToInvoice,"
				+ "t_order.ID,t_order.Customer,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,t_quotes.ReceiptDate1,t_quotes.ReceiptAmount1,t_quotes.ReceiptDate2,t_quotes.ReceiptAmount2,t_requirement_classify.Classify ContractCategory,t_quotes.ReceiptDate3,t_quotes.ReceiptAmount3,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID  "
				+ " left join t_quotes on t_quotes.OrderID=t_order.ID left join t_payment_terms on t_quotes.PaymentTerms=t_payment_terms.ID left join t_payment_status "
				+ "on t_quotes.WhetherToPay=t_payment_status.ID left join t_duty_free on t_quotes.DutyFree=t_duty_free.ID left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID "
				+ "  left join t_quote_system on t_order.QuoteNumber=t_quote_system.ID where ";
		for (int i = 0; i < length; i++) {
			sql += "t_order." + classify_MAP.get(classify) + " like ?";
			if (i < length - 1)
				sql += " or ";
		}
		sql += " order by t_order.DateOfSign desc";

		// 构建带有分页信息的参数数组
		Object[] param = new Object[obj.length];
		for (int i = 0; i < length; i++) {
			param[i] = obj[i];
		}

		return new OrderDao().getOrder(sql, param);
	}

	/**
	 * 分页获取order信息
	 * 
	 * @param classify1
	 *            第一个索条件
	 * 
	 * @param parameter1
	 *            参数1
	 * 
	 * @param classify2
	 *            第二个索条件
	 * 
	 * @param parameter2
	 *            参数2
	 * 
	 * @param page
	 *            分页信息
	 * 
	 */
	@Override
	public List<Map<String, Object>> getOrderByPageInTwo(String classify1, Object parameter1, String classify2,
			Object parameter2, Page page, int type) {

		// 前半句sql语句构建
		String sql1 = null;
		Object[] obj1 = null;
		Object[] sqlObj = null;// 参数
		String sql = null;// 整句

		switch (classify_MAP.get(classify1).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj1 = getIDByBlur(classify1, parameter1.toString());
			break;
		case "ContractCategory":
			obj1 = getIDByBlur(classify1, parameter1.toString());
			break;
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + parameter1 + "%";
		}
		int length1 = obj1.length;
		sql1 = "select t_order.QuoteNumber,t_quote_system.Number,t_order.TechnologyPath,t_order.ActualPaymentTime,t_order.ContractPath,t_payment_status.Status WhetherToPay,t_order.ExpectedReceiptDate,t_order.ID,t_order.Customer,t_area.AreaName Area,t_requirement_classify.Classify ContractCategory,t_order.ContractNo,t_order.ContractTitle,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks,DATEDIFF(t_order.ActualDelivery,t_order.CargoPeriod) D3_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod) D3_D4,DATEDIFF(t_order.ExpectedDeliveryPeriod,t_order.CargoPeriod) D4_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.DateOfSign) D3_D1 from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID "
				+ "left join  t_quotes on t_quotes.OrderID=t_order.ID "
				+ "left join t_payment_status on t_quotes.WhetherToPay=t_payment_status.ID "
				+ "left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID "
				+ " left join t_quote_system on t_order.QuoteNumber=t_quote_system.ID  where ";// 去掉1(
		if (type != 3) {
			sql1 += "t_order.PageType=? and ";
		}
		if (classify1.equals("型号")) {
			sql1 += " t_order.ID in (select t_order_info.OrderID ID from t_order_info "
					+ "left join t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID "
					+ " where t_commodity_info.Model like ?)";
		} else {
			if (length1 == 0) {
				sql1 += "t_order." + classify_MAP.get(classify1) + " = ?";
			}else{
			sql1+="(";
			for (int i = 0; i < length1; i++) {
				sql1 += "t_order." + classify_MAP.get(classify1) + " like ?";

				if (i < length1 - 1)
					sql1 += " or ";
			}
			sql1+=")";
			}
		}

		// 后半句sql语句构建
		// String sql2=null;
		Object[] obj2 = null;

		switch (classify_MAP.get(classify2).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj2 = getIDByBlur(classify2, parameter2.toString());
			break;
		case "ContractCategory":
			obj2 = getIDByBlur(classify2, parameter2.toString());
			break;
		default:
			obj2 = new Object[1];
			obj2[0] = "%" + parameter2 + "%";
		}
		int length2 = obj2.length;
		sql1 += " and ";
		if (classify2.equals("型号")) {
			sql1 += "  t_order.ID in (select t_order_info.OrderID ID from t_order_info "
					+ "left join t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID "
					+ " where t_commodity_info.Model like ?)";
		} else {
			if (length2 == 0) {
				sql1 += "  t_order." + classify_MAP.get(classify2) + " = ?";
			}
			// sql2 = "select t_order."+classify_MAP.get(classify2)+" from
			// t_order
			// where ";
			sql1+="(";
			for (int i = 0; i < length2; i++) {
				// sql2+="t_order."+classify_MAP.get(classify2)+" like ?";
				sql1 += " t_order." + classify_MAP.get(classify2) + " like ?";
				if (i < length2 - 1)
					sql1 += " or ";
			}
			sql1+=")";
		}

		// String sql = sql1 +" ) and t_order."+classify_MAP.get(classify2)+"
		// in(" + sql2+")";
		sql = sql1;
		// 构建带有分页信息的参数数组
		int allCounts = obj1.length + obj2.length;
		int times = 0;
		if(type!=3){
			allCounts +=1;
			times+=1;
		}
		if (length1 == 0 && length2 != 0) {
			sqlObj = new Object[allCounts + 3];
			sqlObj[0] = 0;
			if(type!=3){
				sqlObj[0] = type;
				sqlObj[1] = 0;
			}
			for (int i = 0; i < length2; i++) {
				sqlObj[times++] = obj2[i];
			}
			sqlObj[allCounts + 1] = (page.getCurrentPage() - 1) * page.getRows();
			sqlObj[allCounts + 2] = page.getRows();
			
		} else if (length1 != 0 && length2 == 0) {
			sqlObj = new Object[allCounts + 3];
			for (int i = 0; i < length1; i++) {
				sqlObj[times++] = obj1[i];
			}
			
			sqlObj[1] = 0;
			if(type!=3){
				sqlObj[1] = type;
				sqlObj[2] = 0;
			}
			sqlObj[allCounts + 1] = (page.getCurrentPage() - 1) * page.getRows();
			sqlObj[allCounts + 2] = page.getRows();
		} else if (length1 == 0 && length2 == 0) {
			sqlObj = new Object[allCounts + 4];
			sqlObj[0] = 0;
			sqlObj[1] = 0;
			if(type!=3){
				sqlObj[0] = type;
				sqlObj[1] = 0;
				sqlObj[2] = 0;
			}
			sqlObj[allCounts + 2] = (page.getCurrentPage() - 1) * page.getRows();
			sqlObj[allCounts + 3] = page.getRows();
		} else {
			sqlObj = new Object[allCounts + 2];
			if(type!=3){
				sqlObj[0] = type;
			}
			for (int i = 0; i < length1; i++) {
				sqlObj[times++] = obj1[i];
			}
			for (int i = 0; i < length2; i++) {
				sqlObj[times++] = obj2[i];
			}
			sqlObj[allCounts] = (page.getCurrentPage() - 1) * page.getRows();
			sqlObj[allCounts + 1] = page.getRows();
		}

		sql += " order by t_order.DateOfSign desc limit ?,?";

		 System.out.println("参数：" + Arrays.toString(sqlObj));
		 System.out.println("组合查询： " + sql);
		return new OrderDao().getOrder(sql, sqlObj);
	}

	/**
	 * 获取所有的订单
	 * 
	 */
	@Override
	public List<Map<String, Object>> getAllOrder(Page page, int type) {

		return new OrderDao().getAllOrder(page, type);

	}

	/**
	 * 获取订单明细
	 * 
	 */
	@Override
	public List<Map<String, Object>> getOrderInfoById(int orderID) {

		OrderInfoDao oID = new OrderInfoDao();

		return oID.getOrderInfoByID(orderID);

	}

	/**
	 * 修改合同信息
	 * 
	 * @return true 修改成功 false 修改失败
	 */
	@Override
	public boolean modifyOrder(CompleteOrder completeOrder) {

		boolean flag = false;
		Order order = completeOrder.getOrder();
		OrderDao orderDao = new OrderDao();
		Quotes quotes = completeOrder.getQuotes();
		QuotesDao quotesDao = new QuotesDao();
		DBUtil db = new DBUtil();
		Connection conn = db.getConnection();

		// System.out.println(new Gson().toJson(quotes));

		try {
			conn.setAutoCommit(false);
			orderDao.modify(order, db);
			quotesDao.modify(quotes, db);

			conn.commit();
			conn.setAutoCommit(true);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			db.closed();
		}

		return flag;
	}

	@Override
	public List<Map<String, Object>> getLogisticsByID(int id) {

		return new LogisticsDao().getLogisticsByID(id);
	}

	@Override
	public List<Map<String, Object>> getQuotesByID(String id) {

		return new QuotesDao().getQuotesByID(id);
	}

	/**
	 * 
	 * 合同配置新增
	 * 
	 */
	@Override
	public boolean OrderInfoAndLogisticsAdd(OrderInfo orderInfo, Logistics logistics) {

		boolean flag = false;
		DBUtil db = new DBUtil();
		Connection conn = db.getConnection();
		OrderInfoDao orderInfoDao = new OrderInfoDao();
		LogisticsDao logisticsDao = new LogisticsDao();
		try {
			conn.setAutoCommit(false);

			orderInfoDao.insertOrderInfo(orderInfo, db);
			int id = orderInfoDao.getID(orderInfo.getOrderID(), orderInfo.getEquipmentModel(), db);
			logistics.setOrderInfoID(id);
			logisticsDao.insertEmpty(logistics, db);

			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			try {
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 删除合同配置
	 */
	@Override
	public boolean deleteOrderInfo(String orderInfoId) {

		boolean flag = false;
		DBUtil db = new DBUtil();
		OrderInfoDao orderInfoDao = new OrderInfoDao();
		LogisticsDao logisticsDao = new LogisticsDao();
		Connection conn = db.getConnection();

		try {
			conn.setAutoCommit(false);

			orderInfoDao.deletOrderInfo(orderInfoId, db);
			logisticsDao.deletLogistics(orderInfoId, db);

			conn.commit();
			flag = true;
			conn.setAutoCommit(true);

		} catch (Exception e) {

			try {
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return flag;
	}

	/**
	 * 查询订单配置
	 */
	@Override
	public List<Map<String, Object>> getOrderInfo(String orderID) {

		return null;
	}

	/**
	 * 修改orderInfo
	 */
	@Override
	public boolean modifyOrderInfo(String id, String counts) {

		return new OrderInfoDao().modifyOrderInfo(id, counts);
	}

	/**
	 * 删除订单信息,删除订单信息，订单配置，合同明细，物流信息
	 */
	@Override
	public boolean deleteOrder(String id) {

		boolean flag = false;

		DBUtil db = new DBUtil();
		OrderDao orderDao = new OrderDao();
		OrderInfoDao orderInfoDao = new OrderInfoDao();
		QuotesDao quotesDao = new QuotesDao();
		LogisticsDao logisticsDao = new LogisticsDao();

		Connection conn = db.getConnection();

		try {
			conn.setAutoCommit(false);

			orderDao.deleteOrder(id, db);
			orderInfoDao.deleteOrderInfo(id, db);
			quotesDao.deleteQuotes(id, db);
			logisticsDao.deleteLogistics(id, db);

			conn.commit();
			flag = true;
			conn.setAutoCommit(true);
		} catch (Exception e) {
			try {
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 订单自动导入
	 */
	@Override
	public boolean autoInputOrder(HttpServletRequest request) {
		boolean flag = false;

		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Set factory constraints
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = null;

		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {

			e.printStackTrace();
		}

		// FileUtil fu = new FileUtil(path1);

		Iterator<FileItem> it = items.iterator();
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				InputStream is = null;
				try {
					is = item.getInputStream();
					Map<String, Object> map = new EXCELUtil().excelToMap(item.getName(), is);
					/**
					 * 这里放 导入订单的语句
					 * 
					 */
					orderToObject(map);
					// System.out.println(map);
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 将转换后的excel文件得到的map内容转化为CompleteOrder数组
	 */
	@Override
	public CompleteOrder[] orderToObject(Map<String, Object> map) {

		List<Map<String, Object>> ls = (ArrayList<Map<String, Object>>) map.get("one");
		// System.out.println(ls.get(1).get("联系方式"));
		int dataSize = ls.size() - 1;
		CompleteOrder[] completeOrders = new CompleteOrder[dataSize];

		// 数据转换为可插入的数据 开始

		for (int i = 0; i < dataSize; i++) {
			CompleteOrder compOrder = new CompleteOrder();
			Order order = compOrder.getOrder();
			Quotes quotes = compOrder.getQuotes();

			order.setCustomer(ls.get(i + 1).get("客户名称").toString().trim());

		}

		// 数据转换为可插入的数据 结束

		return null;
	}

	/**
	 * 单条件查询订单信息
	 */
	@Override
	public List<Map<String, Object>> getOrderByOne(String classify, Object parameter,int type) {
		String sql = null;
		Object[] obj = null;

		// 生成参数数组
		switch (classify_MAP.get(classify).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj = getIDByBlur(classify, parameter.toString());
			break;
		case "ContractCategory":
			obj = getIDByBlur(classify, parameter.toString());
			break;
		
		default:
			obj = new Object[1];
			obj[0] = "%" + parameter + "%";
		}

		// 构建sql语句
		int length = obj.length;
		sql = "select t_order.ContractTitle,t_quotes.BillingDate,t_order.ActualPaymentTime,t_quotes.RMBQuotes,t_payment_terms.ID PaymentTermsID,t_order.ExpectedReceiptDate,t_quotes.USDQuotes,t_payment_terms.Condition PaymentTerms,t_payment_status.Status WhetherToPay,t_duty_free.Status DutyFree,t_quotes.WhetherToInvoice,"
				+ "t_order.ID,t_order.Customer,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,t_quotes.ReceiptDate1,t_quotes.ReceiptAmount1,t_quotes.ReceiptDate2,t_quotes.ReceiptAmount2,t_requirement_classify.Classify ContractCategory,t_quotes.ReceiptDate3,t_quotes.ReceiptAmount3,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks  from  t_order"
				+ " left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID  "
				+ " left join t_quotes on t_quotes.OrderID=t_order.ID left join t_payment_terms on t_quotes.PaymentTerms=t_payment_terms.ID left join t_payment_status "
				+ "on t_quotes.WhetherToPay=t_payment_status.ID left join t_duty_free on t_quotes.DutyFree=t_duty_free.ID left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID where PageType=? ";
		for (int i = 0; i < length; i++) {
			if(classify_MAP.get(classify).equals("Contact"))
			{
				sql += " and t_order.Contact like ?";
			}else{
				sql += " and " +classify_MAP.get(classify) + " like ?";
			}
			if (i < length - 1)
				sql += " or ";
		}
		sql += " order by t_order.DateOfSign desc";

		// 构建带有分页信息的参数数组
		Object[] param = new Object[obj.length+1];
		param[0] = type;
		for (int i = 0; i < length; i++) {
			param[i+1] = obj[i];
		}

		List<Map<String, Object>> ls = new OrderDao().getOrder(sql, param);
		return ls;
	}

	/**
	 * 多条件查询订单信息包括合同明细
	 */
	@Override
	public List<Map<String, Object>> getOrderByTwo(String classify1, Object parameter1, String classify2,
			Object parameter2,int type) {
		// 前半句sql语句构建
		String sql1 = null;
		Object[] obj1 = null;

		switch (classify_MAP.get(classify1).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj1 = getIDByBlur(classify1, parameter1.toString());
			break;
		case "ContractCategory":
			obj1 = getIDByBlur(classify1, parameter1.toString());
			break;
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + parameter1 + "%";
		}
		int length1 = obj1.length;
		sql1 = "select t_order.ID,t_order.ContractTitle,t_quotes.BillingDate,t_order.ActualPaymentTime,t_quotes.RMBQuotes,t_payment_terms.ID PaymentTermsID,t_order.ExpectedReceiptDate,t_quotes.USDQuotes,t_payment_terms.Condition PaymentTerms,t_payment_status.Status WhetherToPay,t_duty_free.Status DutyFree,t_quotes.WhetherToInvoice,"
				+ "t_order.ID,t_order.Customer,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,t_quotes.ReceiptDate1,t_quotes.ReceiptAmount1,t_quotes.ReceiptDate2,t_quotes.ReceiptAmount2,t_requirement_classify.Classify ContractCategory,t_quotes.ReceiptDate3,t_quotes.ReceiptAmount3,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID  "
				+ " left join t_quotes on t_quotes.OrderID=t_order.ID left join t_payment_terms on t_quotes.PaymentTerms=t_payment_terms.ID left join t_payment_status "
				+ "on t_quotes.WhetherToPay=t_payment_status.ID left join t_duty_free on t_quotes.DutyFree=t_duty_free.ID left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID where t_order.PageType=? ";
		for (int i = 0; i < length1; i++) {
			if(classify_MAP.get(classify1).equals("Contact"))
			{
				sql1 += " and t_order.Contact like ?";
			}else{
				sql1 += " and "+classify_MAP.get(classify1) + " like ?";
			}
			if (i < length1 - 1)
				sql1 += " or ";
		}

		// 后半句sql语句构建
		String sql2 = null;
		Object[] obj2 = null;

		switch (classify_MAP.get(classify2).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj2 = getIDByBlur(classify2, parameter2.toString());
			break;
		case "ContractCategory":
			obj2 = getIDByBlur(classify2, parameter2.toString());
			break;
		default:
			obj2 = new Object[1];
			obj2[0] = "%" + parameter2 + "%";
		}
		int length2 = obj2.length;
		sql2 = "select " + classify_MAP.get(classify2) + " from t_order where ";
		for (int i = 0; i < length2; i++) {
			if(classify_MAP.get(classify2).equals("Contact"))
			{
				sql2 += " and t_order.Contact like ?";
			}else{
				sql2 += classify_MAP.get(classify2) + " like ?";
			}
			if (i < length2 - 1)
				sql2 += " or ";
		}

		String sql = sql1 + " and " + classify_MAP.get(classify2) + " in(" + sql2 + ")";
		// 构建带有分页信息的参数数组
		int allCounts = obj1.length + obj2.length+1;
		Object[] sqlObj = new Object[allCounts];
		int times = 1;
		sqlObj[0] = type;
		for (int i = 0; i < length1; i++) {
			sqlObj[times++] = obj1[i];
		}
		for (int i = 0; i < length2; i++) {
			sqlObj[times++] = obj2[i];
		}
		sql += " order by t_order.DateOfSign desc";

		return new OrderDao().getOrder(sql, sqlObj);
	}

	/**
	 * 无条件查询订单信息包括合同明细
	 */
	@Override
	public List<Map<String, Object>> getOrderByNone(int type) {

		String sql = "select t_order.ID,t_order.ContractTitle,t_order.ActualPaymentTime,t_quotes.BillingDate,t_quotes.RMBQuotes,t_payment_terms.ID PaymentTermsID,t_order.ExpectedReceiptDate,t_quotes.USDQuotes,t_payment_terms.Condition PaymentTerms,t_payment_status.Status WhetherToPay,t_duty_free.Status DutyFree,t_quotes.WhetherToInvoice,"
				+ "t_order.ID,t_order.Customer,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,t_quotes.ReceiptDate1,t_quotes.ReceiptAmount1,t_quotes.ReceiptDate2,t_quotes.ReceiptAmount2,t_requirement_classify.Classify ContractCategory,t_quotes.ReceiptDate3,t_quotes.ReceiptAmount3,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID  "
				+ " left join t_quotes on t_quotes.OrderID=t_order.ID left join t_payment_terms on t_quotes.PaymentTerms=t_payment_terms.ID left join t_payment_status "
				+ "on t_quotes.WhetherToPay=t_payment_status.ID left join t_duty_free on t_quotes.DutyFree=t_duty_free.ID left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID where PageType=? order by t_order.DateOfSign desc";
           
		return new DBUtil().QueryToList(sql, new Object[]{type});
	}

	/**
	 * 导出订单不包括PONO信息
	 */
	@Override
	public List<Map<String, Object>> exportOrder(String queryType, Object[] classify, Object[] parameter, String path) {

		List<Map<String, Object>> ls = null;
		if (queryType.equals("mixSelect")) {
			ls = getOrderByTwo(classify[0].toString(), parameter[0].toString(), classify[1].toString(),
					parameter[1].toString(),0);
		} else if (queryType.equals("singleSelect")) {
			if (classify[0].equals("PO(只能单一搜索)") || classify[0].equals("SO(只能单一搜索)")
					|| classify[0].equals("供应商(只能单一搜索)")) {
				ls = getOrderByPageInOneByPoSo(classify[0].toString(), parameter[0],0);
			} else if (classify[0].equals("合同货期") || classify[0].equals("实际货期") || classify[0].equals("预计货期")) {

				ls = getOrderExportByTime(classify[0].toString(), parameter[0], parameter[1],0);
			} else {

				ls = getOrderByOne(classify[0].toString(), parameter[0],0);
			}
		} else if (queryType.equals("common")) {
			ls = getOrderByNone(0);
		}

		return ls;

	}
	public List<Map<String, Object>> exportStockOrder(String queryType, Object[] classify, Object[] parameter, String path) {

		List<Map<String, Object>> ls = null;
		if (queryType.equals("mixSelect")) {
			ls = getOrderByTwo(classify[0].toString(), parameter[0].toString(), classify[1].toString(),
					parameter[1].toString(),1);
		} else if (queryType.equals("singleSelect")) {
			if (classify[0].equals("合同货期") || classify[0].equals("实际货期") || classify[0].equals("预计货期")) {

				ls = getOrderExportByTime(classify[0].toString(), parameter[0], parameter[1],1);
			} else {

				ls = getOrderByOne(classify[0].toString(), parameter[0],1);
			}
		} else if (queryType.equals("common")) {
			ls = getOrderByNone(1);
		}

		return ls;

	}

	/**
	 * 导出订单包括PONO信息
	 */
	@Override
	public Boolean exportOrderIncludePO(String queryType, Object[] classify, Object[] parameter, String path) {
		boolean flag = false;

		List<Map<String, Object>> ls = exportOrder(queryType, classify, parameter, path);
		System.out.println("exportOrder:"+ls.size());
		ls = getPOInfo(ls, queryType, classify, parameter, path);
		System.out.println("ls:"+ls.size());
		String[] colName = new String[] { "客户名称", "联系人", "联系方式", "合同地区", "合同名称", "合同号", "合同类别", "销售代表", "合同签订日期",
				"合同货期", "实际货期", "预计货期", "合同状态", "装机时间", "装机地点", "备注", "合同金额USD", "合同金额RMB", "付款条件", "是否付款", "是否开具发票",
				"是否办理免税信用证", "收款日期1", "收款金额1", "收款日期2", "收款金额2", "收款日期3", "收款金额3", "预计收款日期", "开票日期", "PO号码", "SO号码",
				"PO金额USD", "PO金额RMB", "供应商", "工厂货期", "发货日期", "预计付款日期", "实际付款日期" };
		String[] dataName = new String[] { "Customer", "Contact", "ContactInfo", "Area", "ContractTitle", "ContractNo",
				"ContractCategory", "SalesRepresentative", "DateOfSign", "CargoPeriod", "ActualDelivery",
				"ExpectedDeliveryPeriod", "Status", "InstalledTime", "InstalledSite", "Remarks", "USDQuotes",
				"RMBQuotes", "PaymentTerms", "WhetherToPay", "WhetherToInvoice", "DutyFree", "ReceiptDate1",
				"ReceiptAmount1", "ReceiptDate2", "ReceiptAmount2", "ReceiptDate3", "ReceiptAmount3",
				"ExpectedReceiptDate", "BillingDate", "PONO", "SONO", "USD", "RMB", "Supplier", "FactoryShipment",
				"ActualDate", "EstimatedPaymentTime", "ActualPaymentTime" };
		try {
			new EXCELUtil().buildExcel(colName, dataName, ls, path);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}
	List<Map<String, Object>> addOrderInfo(List<Map<String, Object>> ls){
		OrderInfoDao dao = new OrderInfoDao();
		for(int i = 1;i < ls.size();i++){
			int orderID = Integer.parseInt(ls.get(i).get("ID").toString());
			List<Map<String,Object>> orderInfo = dao.getOrderInfoForExcel(orderID);
			ls.get(i).put("OrderInfo", orderInfo);
		}
		return ls;
	}
	
	List<Map<String, Object>> addPurchaseInfo(List<Map<String, Object>> ls){
		OrderDao dao = new OrderDao();
		for(int i = 1;i < ls.size();i++){
			int orderID = Integer.parseInt(ls.get(i).get("ID").toString());
			List<Map<String,Object>> purchaseInfo = dao.getPurchaseInfoForExcel(orderID);
			ls.get(i).put("PurchaseInfo", purchaseInfo);
		}
		return ls;
	}
	
	
	
	public Boolean exportOrderStock(String queryType, Object[] classify, Object[] parameter, String path) {
		boolean flag = false;

		List<Map<String, Object>> ls = exportStockOrder(queryType, classify, parameter, path);
		System.out.println("exportOrder:"+ls.size());
		ls = addOrderInfo(ls);
		ls = addPurchaseInfo(ls);
		
	
		String[] colName = new String[] { "供应商名称", "合同地区", "合同号", "合同名称", "采购人员", "合同类型", "联系人", "联系方式", "合同货期",
				"合同签订日期", "装机地点", "装机时间", "预计付款时间", "实际付款时间", "备注", "合同金额USD", "合同金额RMB", "付款条件", "是否付款", "是否开具发票",
				"是否办理免税信用证", "收款日期1", "收款金额1", "收款日期2", "收款金额2", "收款日期3", "收款金额3", "开票日期","合同配置"};
		String[] dataName = new String[] { "Customer","Area", "ContractNo","ContractTitle", "SalesRepresentative",
				"ContractCategory","Contact", "ContactInfo", "CargoPeriod", "DateOfSign","InstalledSite","InstalledTime",
				"ExpectedReceiptDate","ActualPaymentTime","Remarks","USDQuotes","RMBQuotes",
			  
				 "PaymentTerms", "WhetherToPay", "WhetherToInvoice", "DutyFree", "ReceiptDate1",
				"ReceiptAmount1", "ReceiptDate2", "ReceiptAmount2", "ReceiptDate3", "ReceiptAmount3",
				"BillingDate","OrderInfo"};
		String[] orderInfoColName = new String[]{"型号","描述","数量","货期","预计货期","货运单号","订单状态"};
		String[] orderInfoDataName = new String[]{"EquipmentModel","Remarks","Number","Date","ExceptDate","DeliveryNumber","Status"};
		String[] purchaseInfoColName = new String[]{"供应商","产品","采购合同号","币种","金额","用途","公司","账号","开户行"};
		String[] purchaseInfoDataName = new String[]{"Customer","Product","ContractPath","Currency","Money","UseFor","Company","Account","Bank"};
		Map<String, String[]> map = new HashMap<>();
		map.put("colName", colName);
		map.put("dataName", dataName);
		map.put("orderInfoColName", orderInfoColName);
		map.put("orderInfoDataName", orderInfoDataName);
		map.put("purchaseInfoColName", purchaseInfoColName);
		map.put("purchaseInfoDataName", purchaseInfoDataName);
		try {
			new EXCELUtil().buildStockExcel(map,ls, path);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}
	
	
	public Boolean exportOrderStock(String queryType, Object[] classify, Object[] parameter, String path,String condition) {
		boolean flag = false;

		List<Map<String, Object>> ls = exportStockOrder(queryType, classify, parameter, path,condition);
		System.out.println("exportOrder:"+ls.size());
		ls = addOrderInfo(ls);
		ls = addPurchaseInfo(ls);
	
		String[] colName = new String[] { "供应商名称", "合同地区", "合同号", "合同名称", "采购人员", "合同类型", "联系人", "联系方式", "合同货期",
				"合同签订日期", "装机地点", "装机时间", "预计付款时间", "实际付款时间", "备注", "合同金额USD", "合同金额RMB", "付款条件", "是否付款", "是否开具发票",
				"是否办理免税信用证", "收款日期1", "收款金额1", "收款日期2", "收款金额2", "收款日期3", "收款金额3", "开票日期","合同配置"};
		String[] dataName = new String[] { "Customer","Area", "ContractNo","ContractTitle", "SalesRepresentative",
				"ContractCategory","Contact", "ContactInfo", "CargoPeriod", "DateOfSign","InstalledSite","InstalledTime",
				"ExpectedReceiptDate","ActualPaymentTime","Remarks","USDQuotes","RMBQuotes",
			  
				 "PaymentTerms", "WhetherToPay", "WhetherToInvoice", "DutyFree", "ReceiptDate1",
				"ReceiptAmount1", "ReceiptDate2", "ReceiptAmount2", "ReceiptDate3", "ReceiptAmount3",
				"BillingDate","OrderInfo"};
		String[] orderInfoColName = new String[]{"型号","描述","数量","货期","预计货期","货运单号","订单状态"};
		String[] orderInfoDataName = new String[]{"EquipmentModel","Remarks","Number","Date","ExceptDate","DeliveryNumber","Status"};
		String[] purchaseInfoColName = new String[]{"供应商","产品","采购合同号","币种","金额","用途","公司","账号","开户行"};
		String[] purchaseInfoDataName = new String[]{"Customer","Product","ContractPath","Currency","Money","UseFor","Company","Account","Bank"};
		Map<String, String[]> map = new HashMap<>();
		map.put("colName", colName);
		map.put("dataName", dataName);
		map.put("orderInfoColName", orderInfoColName);
		map.put("orderInfoDataName", orderInfoDataName);
		map.put("purchaseInfoColName", purchaseInfoColName);
		map.put("purchaseInfoDataName", purchaseInfoDataName);
		try {
			new EXCELUtil().buildStockExcel(map,ls, path);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}
	

	/**
	 * 根据合同信息去获取PO信息
	 */
	public List<Map<String, Object>> getPOInfo(List<Map<String, Object>> orderInfo, String queryType, Object[] classify,
			Object[] parameter, String path) {

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> ssmap = new HashMap<String, Object>();
		ssmap.put("11", "11");
		resultList.add(ssmap);
		int length = orderInfo.size();
		TransportService transportService = new TransportServiceImpl();
		for (int i = 1; i < length; i++) {
			Map<String, Object> midMap = orderInfo.get(i);
			int orderID = Integer.parseInt(midMap.get("ID").toString());

			// 根据合同ID统计出物流的信息,往list中增加物流信息的字段
			List<Map<String, Object>> poList = transportService.getPOAmountsByID(orderID);
			// System.out.println("物流size:"+poList.size());
			if (poList.size() > 1) {
				int poListLength = poList.size();
				for (int j = 1; j < poListLength; j++) {
					// System.out.println("poList:"+poList.get(j));
					midMap = exportOrder(queryType, classify, parameter, path).get(i);// 导出订单不包括PONO信息
					// System.out.println(midMap);
					if (!poList.get(j).get("PONO").toString().equals("--")) {
						Map<String, Object> resultMap = midMap;
						resultMap.put("PONO", poList.get(j).get("PONO"));
						resultMap.put("ActualDate", poList.get(j).get("ActualDate"));
						resultMap.put("ActualPaymentTime", poList.get(j).get("ActualPaymentTime"));
						resultMap.put("EstimatedPaymentTime", poList.get(j).get("EstimatedPaymentTime"));
						resultMap.put("FactoryShipment", poList.get(j).get("FactoryShipment"));
						resultMap.put("Supplier", poList.get(j).get("Supplier"));
						resultMap.put("SONO", poList.get(j).get("SONO"));
						resultMap.put("USD", poList.get(j).get("USD"));
						resultMap.put("RMB", poList.get(j).get("RMB"));
						resultList.add(resultMap);
						// if(orderID == 2642){
						// System.out.println(111111111);
						// }
					} else if (poList.size() >= 2 && poList.get(j).get("PONO").toString().equals("--")) {
						Map<String, Object> resultMap = midMap;
						resultMap.put("PONO", "--");
						resultMap.put("ActualDate", "--");
						resultMap.put("ActualPaymentTime", "--");
						resultMap.put("EstimatedPaymentTime", "--");
						resultMap.put("FactoryShipment", "--");
						resultMap.put("Supplier", "--");
						resultMap.put("SONO", "--");
						resultMap.put("USD", "--");
						resultMap.put("RMB", "--");

						// if(orderID == 2642){
						// System.out.println(222222222);
						// }
						resultList.add(resultMap);
					}
				}
			} else if (poList.size() <= 1) {
				Map<String, Object> resultMap = midMap;
				resultMap.put("PONO", "--");
				resultMap.put("ActualDate", "--");
				resultMap.put("ActualPaymentTime", "--");
				resultMap.put("EstimatedPaymentTime", "--");
				resultMap.put("FactoryShipment", "--");
				resultMap.put("Supplier", "--");
				resultMap.put("SONO", "--");
				resultMap.put("USD", "--");
				resultMap.put("RMB", "--");
				// if(orderID == 2642){
				// System.out.println(333333333);
				// }

				resultList.add(resultMap);
			}

		}

		return resultList;
	}

	// 根据输入的是否付款的中文 返回是否付款的ID
	@Override
	public int getPaymentStatusID(String status) {
		int result = 0;

		PaymentStatusDao paymentStatusDao = new PaymentStatusDao();
		List<Map<String, Object>> ls = paymentStatusDao.pamentStatusIsExit(status);
		if (ls.size() > 1) {
			result = Integer.parseInt(ls.get(1).get("ID").toString());
		} else {
			if (paymentStatusDao.insert(status) > 0) {
				ls = paymentStatusDao.pamentStatusIsExit(status);
				result = Integer.parseInt(ls.get(1).get("ID").toString());
			}
		}

		return result;
	}

	// @Test
	// public void test(){
	// System.out.println(getPaymentTermsID("测试条件"));
	// }

	// 根据输入的付款条件的中文 返回是否付款的ID
	@Override
	public int getPaymentTermsID(String condition) {
		int result = 0;

		PaymentTermsDao paymentTermsDao = new PaymentTermsDao();
		List<Map<String, Object>> ls = paymentTermsDao.pamentStatusIsExit(condition);
		if (ls.size() > 1) {
			result = Integer.parseInt(ls.get(1).get("ID").toString());
		} else {
			if (paymentTermsDao.insert(condition) > 0) {
				ls = paymentTermsDao.pamentStatusIsExit(condition);
				result = Integer.parseInt(ls.get(1).get("ID").toString());
			}
		}

		return result;
	}

	/**
	 * 修改订单的实际货期和预计货期
	 */
	@Override
	public boolean modifyLogisticsTime(Order order) {
		OrderDao orderDao = new OrderDao();
		return orderDao.modifyLogisticsTime(order);
	}

	/**
	 * 根据ID获取订单信息，返回的是多表查询的订单信息
	 */
	@Override
	public List<Map<String, Object>> getOrderByID(String id) {

		String sql = "select t_order.ID,t_order.Customer,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.ActualPaymentTime,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID  where t_order.ID=?";
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[] { id };

		return db.QueryToList(sql, parameter);
	}

	/*
	 * 将查询出来的结果新增加一个字段显示，合同配置是否已经完全审核过 了
	 */
	@Override
	public List<Map<String, Object>> queryResultAddReview(List<Map<String, Object>> queryList) {

		int length = queryList.size();
		queryList.get(0).put("colAdd1", "Review");

		for (int i = 1; i < length; i++) {

			String sql = "select B.Review1,B.Review2 from  (select A.Review1,A.Review2 from (select t_order_info.ID,t_order_info.OrderID,t_order_info.Review1,t_order_info.Review2 from t_order_info where t_order_info.OrderID=?)A  )B where B.Review1=1 or B.Review2=1";
			Object[] parameter = new Object[] { queryList.get(i).get("ID") };

			List<Map<String, Object>> midList = new DBUtil().QueryToList(sql, parameter);

			if (midList.size() > 1) {
				queryList.get(i).put("Review", "0");
			} else {
				queryList.get(i).put("Review", "1");
			}

		}
		return queryList;
	}

	/**
	 * 根据多个时间条件模糊查询数量
	 */

	@Override
	public List<Map<String, Object>> getOrderByTime(String classify, String start_time1, String end_time1, Page page,
			int type) {
		
	
		String sql = null;
		Object[] obj = new Object[2];

		// 生成参数数组
		obj[0] = start_time1;
		obj[1] = end_time1;

		// 构建sql语句
		int length = obj.length;
		sql = "select t_quote_system.Number,t_order.TechnologyPath,t_order.ActualPaymentTime,t_order.ContractPath,t_payment_status.Status WhetherToPay,t_order.ExpectedReceiptDate,t_order.ID,t_order.Customer,t_requirement_classify.Classify ContractCategory,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks,DATEDIFF(t_order.ActualDelivery,t_order.CargoPeriod) D3_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod) D3_D4,DATEDIFF(t_order.ExpectedDeliveryPeriod,t_order.CargoPeriod) D4_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.DateOfSign) D3_D1 from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID "
				+ "left join  t_quotes on t_quotes.OrderID=t_order.ID "
				+ "left join t_payment_status on t_quotes.WhetherToPay=t_payment_status.ID "
				+ "left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID  "
				+ " left join t_quote_system on t_order.QuoteNumber=t_quote_system.ID ";
		Object[] param;
		if (!"".equals(start_time1) && !"".equals(end_time1)) {

			if (type != 3) {
				sql += " where PageType=? and t_order." + classify_MAP.get(classify) + " between ? and ?";
				param = new Object[] { type, start_time1, end_time1, (page.getCurrentPage() - 1) * page.getRows(),
						page.getRows() };
			} else {
				sql += " where t_order." + classify_MAP.get(classify) + " between ? and ?";
				param = new Object[] { start_time1, end_time1, (page.getCurrentPage() - 1) * page.getRows(),
						page.getRows() };
			}

			switch (classify) {
			case "合同货期":
				sql += " order by t_order.CargoPeriod desc limit ?,?";
				break;
			case "预计货期":
				sql += " order by t_order.ExpectedDeliveryPeriod desc limit ?,?";
				break;
			default:
				sql += " order by t_order.DateOfSign desc limit ?,?";
				break;
			}

		} else {
			param = new Object[] { (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
			if (type != 3) {
				sql += " where PageType=?";
				param = new Object[] { type, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
			}
			switch (classify) {
			case "合同货期":
				sql += " order by t_order.CargoPeriod desc limit ?,?";
				break;
			case "预计货期":
				sql += " order by t_order.ExpectedDeliveryPeriod desc limit ?,?";
				break;
			default:
				sql += " order by t_order.DateOfSign desc limit ?,?";
				break;
			}

		}
	

		return new OrderDao().getOrder(sql, param);
	}

	@Override
	public int getCountByTimeClassify(String classify1, Map<String, Object> map1, String classify2,
			Map<String, Object> map2, int type) {
		// 前半句sql语句构建
		String sql1 = null;
		Object[] obj1 = null;

		/*
		 * map.put("合同货期", "CargoPeriod"); map.put("实际货期", "ActualDelivery");
		 * map.put("预计货期", "ExpectedDeliveryPeriod");
		 */
		switch (classify_MAP.get(classify1).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
		case "ContractCategory":
			obj1 = getIDByBlur(classify1, map1.get("1").toString());
			break;
		case "CargoPeriod":
		case "ActualDelivery":
		case "ExpectedDeliveryPeriod":
			obj1 = new Object[2];
			obj1[0] = map1.get("1").toString();
			obj1[1] = map1.get("2").toString();
			break;
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + map1.get("1").toString() + "%";
		}
		int length11 = map1.size();
		sql1 = "select count(ID) from t_order  where ";
		if(classify1.equals("型号")){
			if (type != 3) {
				sql1 += "PageType=? and ";
			}
			sql1 += " t_order.ID in (select t_order_info.OrderID ID from t_order_info left join "
					+ "t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID " + "where t_commodity_info.Model like ?) and ";
		}else{
			if (length11 == 1) {
				
				if (type != 3) {
					sql1 += " PageType=? and ";
				}
				// for (int i = 0; i < obj1.length; i++) {
				sql1 += "t_order." + classify_MAP.get(classify1) + " like ?";
				// if (i < obj1.length - 1){
				// sql1 += " or ";
				// }
				//
				// System.out.println(length11+"----"+map1+"----"+classify1);
				// }
				sql1 += " and ";
			} else if (length11 == 2) {
				if (!"".equals(map1.get("1").toString()) && !"".equals(map1.get("2").toString())) {
					if (type != 3) {
						sql1 += " PageType=? and ";
					}
					sql1 += "t_order." + classify_MAP.get(classify1) + " between ? and ?";
					sql1 += " and ";
				} else {
					if (type != 3) {
						sql1 += " PageType=? and ";
					}
					obj1 = null;
				}
			}

		}
		
		// 后半句sql语句构建
		String sql2 = null;
		Object[] obj2 = null;

		switch (classify_MAP.get(classify2).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
		case "ContractCategory":
			obj2 = getIDByBlur(classify2, map2.get("1").toString());
			break;
		case "CargoPeriod":
		case "ActualDelivery":
		case "ExpectedDeliveryPeriod":
			obj2 = new Object[2];
			obj2[0] = map2.get("1").toString();
			obj2[1] = map2.get("2").toString();
			break;
		default:
			obj2 = new Object[1];
			obj2[0] = "%" + map2.get("1").toString() + "%";
		}
		int length22 = map2.size();
		if (length22 == 1) {
			sql2 = "select t_order." + classify_MAP.get(classify2) + " from t_order where ";
			for (int i = 0; i < obj2.length; i++) {
				sql2 += "t_order." + classify_MAP.get(classify2) + " like ?";
				if (i < obj2.length - 1)
					sql2 += " or ";
			}
		} else if (length22 == 2) {
			if (!"".equals(map2.get("1").toString()) && !"".equals(map2.get("2").toString())) {
				sql2 = "select t_order." + classify_MAP.get(classify2) + " from t_order where ";
				sql2 += " t_order." + classify_MAP.get(classify2) + " between ? and ?";
			} else {
				sql2 = "select t_order." + classify_MAP.get(classify2) + " from t_order ";
				obj2 = null;
			}
		}
		String sql = sql1 + "t_order." + classify_MAP.get(classify2) + " in(" + sql2 + ")";
		if(classify2.equals("型号")){
			 sql = sql1 +" t_order.ID in (select t_order_info.OrderID ID from t_order_info left join "
					+ "t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID " + "where t_commodity_info.Model like ?)";
		}
		int length1;
		int length2;
		int allCounts = 0;
		if (obj1 != null && obj2 != null) {
			allCounts = obj1.length + obj2.length;
			length1 = obj1.length;
			length2 = obj2.length;
		} else if (obj1 == null && obj2 != null) {
			allCounts = obj2.length;
			length1 = 0;
			length2 = obj2.length;
		} else if (obj2 == null && obj1 != null) {
			allCounts = obj1.length;
			length2 = 0;
			length1 = obj1.length;
		} else {
			allCounts = 0;
			length1 = 0;
			length2 = 0;
		}
		Object[] sqlObj = new Object[allCounts];
		int times = 0;
		if (type != 3) {
			allCounts = allCounts + 1;
			sqlObj = new Object[allCounts];
			sqlObj[0] = type;
			times = 1;
		}

		for (int i = 0; i < length1; i++) {
			sqlObj[times++] = obj1[i];
			System.out.println(obj1[i] + "----" + times);
		}

		for (int i = 0; i < length2; i++) {
			sqlObj[times++] = obj2[i];
		}
		System.out.println(times + Arrays.toString(sqlObj));
		System.out.println(sql);
		return new OrderDao().getCountsByName(sql, sqlObj);
	}

	/**
	 * 根据多个条件时间查询加分页
	 * 
	 */
	@Override
	public List<Map<String, Object>> getOrderByPageInTwoTime(String classify1, Map<String, Object> map1,
			String classify2, Map<String, Object> map2, Page page, int type) {
		// 前半句sql语句构建
		String sql1 = null;
		Object[] obj1 = null;

		switch (classify_MAP.get(classify1).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
		case "ContractCategory":
			obj1 = getIDByBlur(classify1, map1.get("1").toString());
			break;
		case "CargoPeriod":
		case "ActualDelivery":
		case "ExpectedDeliveryPeriod":
			obj1 = new Object[2];
			obj1[0] = map1.get("1").toString();
			obj1[1] = map1.get("2").toString();
			break;
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + map1.get("1").toString() + "%";
		}
		sql1 = "select t_order.QuoteNumber,t_order.TechnologyPath,t_order.ActualPaymentTime,t_order.ContractPath,t_payment_status.Status WhetherToPay,t_order.ExpectedReceiptDate,t_order.ID,t_order.Customer,t_area.AreaName Area,t_requirement_classify.Classify ContractCategory,t_order.ContractNo,t_order.ContractTitle,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks,DATEDIFF(t_order.ActualDelivery,t_order.CargoPeriod) D3_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod) D3_D4,DATEDIFF(t_order.ExpectedDeliveryPeriod,t_order.CargoPeriod) D4_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.DateOfSign) D3_D1 from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID "
				+ "left join  t_quotes on t_quotes.OrderID=t_order.ID "
				+ "left join t_payment_status on t_quotes.WhetherToPay=t_payment_status.ID "
				+ "left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID  ";
		int length1 = map1.size();
		sql1 += "where PageType=? ";
		if(classify1.equals("型号")){
			 sql1 += " and t_order.ID in (select t_order_info.OrderID ID from t_order_info left join "
						+ "t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID " + "where t_commodity_info.Model like ?)";
		}else{
			if (length1 == 1) {
				sql1+="(";
				for (int i = 0; i < obj1.length; i++) {
					sql1 += (i==0)?" and ":"";
					sql1 += "t_order." + classify_MAP.get(classify1) + " like ?";
					if (i < obj1.length - 1)
						sql1 += " or ";
				}
				sql1+=")";
			} else if (length1 == 2) {
				if (!"".equals(map1.get("1").toString()) && !"".equals(map1.get("2").toString())) {
					sql1 += " and  t_order." + classify_MAP.get(classify1) + " between ? and ?";
				} else {
					obj1 = null;
				}
			}
		}
		

		// 后半句sql语句构建
		String sql2 = null;
		Object[] obj2 = null;

		switch (classify_MAP.get(classify2).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
		case "ContractCategory":
			obj2 = getIDByBlur(classify2, map2.get("1").toString());
			break;
		case "CargoPeriod":
		case "ActualDelivery":
		case "ExpectedDeliveryPeriod":
			obj2 = new Object[2];
			obj2[0] = map2.get("1").toString();
			obj2[1] = map2.get("2").toString();
			break;
		default:
			obj2 = new Object[1];
			obj2[0] = "%" + map2.get("1").toString() + "%";
		}
		int length2 = map2.size();

		if (length2 == 1) {
			sql2 = "select t_order." + classify_MAP.get(classify2) + " from t_order where ";
			for (int i = 0; i < obj2.length; i++) {
				sql2 += "t_order." + classify_MAP.get(classify2) + " like ?";
				if (i < obj2.length - 1)
					sql2 += " or ";
			}
		} else if (length2 == 2) {
			if (!"".equals(map2.get("1").toString()) && !"".equals(map2.get("2").toString())) {
				sql2 = "select t_order." + classify_MAP.get(classify2) + " from t_order where ";
				sql2 += "t_order." + classify_MAP.get(classify2) + " between ? and ?";
			} else {
				sql2 = "select t_order." + classify_MAP.get(classify2) + " from t_order ";
				obj2 = null;
			}
		}

		String sql = sql1 + " and t_order." + classify_MAP.get(classify2) + " in(" + sql2 + ")";
		if(classify2.equals("型号")){
			 sql = sql1 + " and  " +" t_order.ID in (select t_order_info.OrderID ID from t_order_info left join "
						+ "t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID " + "where t_commodity_info.Model like ?)";
		}
		// 构建带有分页信息的参数数组
		int allCounts = 0;
		if (obj1 == null && obj2 != null) {
			allCounts = obj2.length;
			length2 = obj2.length;
			length1 = 0;
		} else if (obj2 == null && obj1 != null) {
			allCounts = obj1.length;
			length1 = obj1.length;
			length2 = 0;
		} else if (obj1 != null && obj2 != null) {
			allCounts = obj1.length + obj2.length;
			length1 = obj1.length;
			length2 = obj2.length;
		} else {
			allCounts = 0;
			length1 = 0;
			length2 = 0;
		}
		Object[] sqlObj = new Object[allCounts + 2];
		int times = 0;
		if (type != 3) {
			allCounts += 1;
			sqlObj = new Object[allCounts + 2];
			sqlObj[0] = type;
			times = 1;
		}
		for (int i = 0; i < length1; i++) {
			sqlObj[times++] = obj1[i];
		}
		for (int i = 0; i < length2; i++) {
			sqlObj[times++] = obj2[i];
		}
		sql += " order by t_order.DateOfSign desc limit ?,?";
		sqlObj[allCounts] = (page.getCurrentPage() - 1) * page.getRows();
		sqlObj[allCounts + 1] = page.getRows();
		System.out.println(Arrays.toString(sqlObj));
		System.out.println(sql);
		return new OrderDao().getOrder(sql, sqlObj);
	}

	/**
	 * 物流页面单一查询
	 */
	@Override
	public int getCountByClassifyTime(String classify, String start_time1, String end_time1, String condition,int PageType) {
		String sql = null;
		Object[] obj;
		if (condition.equals("AllNoSend")) {
			condition = " and t_order.Status<>14 and";
		}
		if (condition.equals("OtherNoSend")) {
			condition = " and t_order.Status<>17 and t_order.Status<>14 and";
		}
		if (condition.equals("All")) {
			condition = "";
		}
		sql = "select count(ID) from t_order where  PageType=? ";
		if (!"".equals(start_time1) && !"".equals(end_time1)) {
			obj = new Object[]{PageType,start_time1,end_time1};
			sql += " and "+classify_MAP.get(classify) + " between ? and ? ";
		} else {
			obj = new Object[]{PageType};

		}
		sql += condition + " ActualDelivery='0000-00-00' or ActualDelivery is null";
		return new OrderDao().getCountsByName(sql, obj);
	}

	// 读取excel返回一个OrderInfo对象的集合
	public static Map<String, List<OrderInfo>> GetexcelWaferID(String filepath) {
		List<OrderInfo> ExcelList = new ArrayList<OrderInfo>();
		List<OrderInfo> FailExcelList = new ArrayList<OrderInfo>();
		Map<String, List<OrderInfo>> map = new HashMap<String, List<OrderInfo>>();
		try {
			FileInputStream excelFileInputStream = new FileInputStream(filepath);
			XSSFWorkbook workbook;
			try {
				workbook = new XSSFWorkbook(excelFileInputStream);
				excelFileInputStream.close();
				XSSFSheet sheet = workbook.getSheetAt(0);
				for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum() - 2; rowIndex++) {

					XSSFRow row = sheet.getRow(rowIndex);
					if ("".equals(row.getCell(0).toString()) && "".equals(row.getCell(1).toString())) {
						break;
					}
					// System.out.println(row.getCell(0)+","+row.getCell(1));
					List<Map<String, Object>> ModelList = new EquipmentServiceImpl()
							.getAllEquipmentByName(row.getCell(0).toString().toString());
					// System.out.println(ModelList.get(1));
					if (ModelList.size() >= 2) {
						int model = (Integer) Integer.parseInt(ModelList.get(1).get("ID").toString());
						OrderInfo orderInfo = new OrderInfo(model, (int) Double.parseDouble(row.getCell(1).toString()));
						ExcelList.add(orderInfo);
					} else {
						// System.out.println("上传失败："+row.getCell(0)+","+row.getCell(1));
						// 下拉框找不到
						OrderInfo orderInfo = new OrderInfo(row.getCell(0).toString(),
								(int) Double.parseDouble(row.getCell(1).toString()));
						FailExcelList.add(orderInfo);
					}
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		map.put("ExcelList", ExcelList);
		map.put("FailExcelList", FailExcelList);
		return map;
	}

	// 获取表单文件
	public Map<String, Object> InputContractConfiguration(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<OrderInfo> ExcelList = null;
		List<OrderInfo> FailExcelList = null;
		String order_id = null;
		try {
			Date date = new Date();
			String path4 = Thread.currentThread().getContextClassLoader().getResource("../../").getPath()
					+ date.getTime() + "\\";
			String temp = path4;
			String tempPath = temp + "EOULU\\file\\";// zip压缩文件
			String path = temp + "EOULU\\file1";// 缓存文件
			path = URLDecoder.decode(temp, "gbk");
			File file = new File(tempPath);
			File file01 = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			if (!file01.exists()) {
				file01.mkdirs();
			}

			Map<String, String> map = getForm(file01, request, tempPath);
			String fileName = map.get("fileName");
			order_id = map.get("order_id");
			Map<String, List<OrderInfo>> remap = GetexcelWaferID(fileName);
			ExcelList = remap.get("ExcelList");
			FailExcelList = remap.get("FailExcelList");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (FileUploadException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		resultMap.put("ExcelList", ExcelList);
		resultMap.put("FailExcelList", FailExcelList);
		resultMap.put("order_id", order_id);
		return resultMap;
	}

	public Map<String, String> getForm(File file01, HttpServletRequest request, String tempPath)
			throws FileUploadException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		String fileName = null;
		String order_id = null;
		String classify = null;
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(file01);// 设置临时目录
		factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
		ServletFileUpload upload = new ServletFileUpload(factory);
		java.util.List<org.apache.commons.fileupload.FileItem> items = null;
		items = upload.parseRequest(request);
		byte data[] = new byte[1024];
		int i = 0;
		if (items != null) {
			for (Iterator iterator = items.iterator(); iterator.hasNext();) {
				FileItem item = (FileItem) iterator.next();
				if (item.isFormField()) {
					if ("id".equals(item.getFieldName())) {
						order_id = item.getString("utf-8");
						// System.out.println("order_id:"+order_id);
					}
					if ("classify".equals(item.getFieldName())) {
						classify = item.getString("utf-8");
						// System.out.println("order_id:"+order_id);
					}
				} else {
					fileName = item.getName().substring(item.getName().lastIndexOf(File.separator) + 1,
							item.getName().length());
					InputStream inputStream = item.getInputStream();
					OutputStream outputStream = new FileOutputStream(tempPath + fileName);
					while ((i = inputStream.read(data)) != -1) {
						outputStream.write(data, 0, i);
					}
					inputStream.close();
					outputStream.close();
					fileName = tempPath + fileName;
				}
			}
		}
		map.put("fileName", fileName);
		map.put("order_id", order_id);
		map.put("classify", classify);
		return map;
	}

	// @Test
	// public void test(){
	//
	// TransportService transportService = new TransportServiceImpl();
	// System.out.println(transportService.getPOAmountsByID(2075));
	// }
	public static void main(String[] args) {
		new OrderServiceImpl().exportOrderStock("singleSelect", new Object[]{"采购人员"}, new Object[]{"蒋亚平"}, "E:/开票申请/测试.xls");
	}

	@Override
	public List<Map<String, Object>> getAllOrderByActualDelivery(Page page, String condition, String column, int type) {
		return new OrderDao().getAllOrderByActualDelivery(page, condition, column, type);
	}

	@Override
	public int getAllCountsIfActualDelivery(String condition, String column, int type) {

		return new OrderDao().getAllCountsIfActualDelivery(condition, column, type);
	}

	@Override
	public int updateIsSend(Order order) {
		String sql = "update t_order set isSend=? where ID=?";
		// System.out.println("test: " + order.getIsSend());
		Object[] parameter = new Object[] { order.getIsSend(), order.getID() };
		OrderDao dao = new OrderDao();
		int flag = dao.executeUpdate(sql, parameter);

		return flag;
	}

	@Override
	public boolean ConsigneeAdd(Consignee consignee) {

		return new OrderDao().insertConsignee(consignee);
	}

	@Override
	public int getOrderByPageInOneByPoSoCount(String classify, Object parameter,int PageType) {

		String sql1 = "";
		switch (classify) {
		case "PO(只能单一搜索)":
			sql1 = "select t_order.ID,t_order.`Status` from t_logistics left join t_order on t_order.ID=t_logistics.OrderID  where t_order.PageType=? and PONO like ? group by OrderID";
			break;
		case "SO(只能单一搜索)":
			sql1 = "select t_order.ID,t_order.`Status` from t_logistics left join t_order on t_order.ID=t_logistics.OrderID  where t_order.PageType=? and SONO like ? group by OrderID";
			break;
		case "供应商(只能单一搜索)":
			sql1 = "select * from t_order where PageType=? and ID in(select OrderID from t_logistics where t_logistics.Supplier in (select t_supplier.ID from t_supplier where t_supplier.`Name` like ?))";
			break;
		}

		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql1, new Object[] { PageType,"%" + parameter + "%" });

		return ls.size() - 1;
	}

	@Override
	public int getCountByClassifyTime(String classify, String start_time1, String end_time1, int type) {
		String sql = null;
		Object[] obj;

		sql = "select count(ID) from t_order  ";
		if (!"".equals(start_time1) && !"".equals(end_time1)) {

			if (type != 3) {
				sql += "where PageType=? and " + classify_MAP.get(classify) + " between ? and ?";
				obj = new Object[] { type, start_time1, end_time1 };
			} else {
				sql += "where " + classify_MAP.get(classify) + " between ? and ?";
				obj = new Object[2];
				// 生成参数数组
				obj[0] = start_time1;
				obj[1] = end_time1;
			}

		} else {
			obj = null;

		}
		return new OrderDao().getCountsByName(sql, obj);
	}

	@Override
	public int getCountByClassify(String classify, Object parameter, int type) {
		String sql = null;
		Object[] obj = null;

		switch (classify_MAP.get(classify).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj = getIDByBlur(classify, parameter.toString());
			break;
		case "ContractCategory":
			obj = getIDByBlur(classify, parameter.toString());
			break;
		default:
			obj = new Object[1];
			obj[0] = "%" + parameter + "%";
		}
		int length = obj.length;
		sql = "select count(ID) from t_order where PageType=? ";
		Object[] param = null;
		if (classify.equals("型号")) {
			param = obj;
			if (type != 3) {
				sql += " and ";
				param = new Object[length + 1];
				param[0] = type;
				for (int i = 1; i < length + 1; i++) {
					param[i] = obj[i - 1];
				}
			}
			sql += " t_order.ID in (select t_order_info.OrderID ID from t_order_info "
					+ "left join t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID "
					+ " where t_commodity_info.Model like ?)";
		} else {
			param = obj;
			if (type != 3) {
				
				param = new Object[length + 1];
				param[0] = type;
				for (int i = 1; i < length + 1; i++) {
					param[i] = obj[i - 1];
				}
			}
			for (int i = 0; i < length; i++) {
				if(i==0){
					sql += " and (";
				}
				sql += "  t_order." + classify_MAP.get(classify) + " like ?";
				if (i < length - 1)
					sql += " or ";
				if(i==length-1){
					sql += " )";
				}
			}
		}
		return new OrderDao().getCountsByName(sql, param);
	}

	/**
	 * 物流页面单一查询
	 */
	@Override
	public List<Map<String, Object>> getOrderByPageInOneByPoSo(String classify, Object parameter, Page page,
			String condition,int PageType) {
		if (condition.equals("AllNoSend")) {
			condition = " and t_order.Status<>14 ";
		}
		if (condition.equals("OtherNoSend")) {
			condition = " and t_order.Status<>17 and t_order.Status<>14 ";
		}
		if (condition.equals("All")) {
			condition = "";
		}
		String sql = "select t_order.ExpectedReceiptDate,t_order.ID,t_order.Customer,t_area.AreaName Area,t_requirement_classify.Classify ContractCategory,t_order.ContractNo,t_order.ContractTitle,t_payment_status.Status WhetherToPay,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks,DATEDIFF(t_order.ActualDelivery,t_order.CargoPeriod) D3_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod) D3_D4,DATEDIFF(t_order.ExpectedDeliveryPeriod,t_order.CargoPeriod) D4_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.DateOfSign) D3_D1 from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID "
				+ "left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID where PageType=? and t_order.ID in ";
		switch (classify) {
		case "PO(只能单一搜索)":
			sql += "(select OrderID from t_logistics where PONO like ?) ORDER BY t_order.DateOfSign desc limit ?,?";
			break;
		case "SO(只能单一搜索)":
			sql += "(select OrderID from t_logistics where SONO like ?) ORDER BY t_order.DateOfSign desc limit ?,?";
			break;
		case "供应商(只能单一搜索)":
			sql += "(select OrderID from t_logistics where t_logistics.Supplier in (select t_supplier.ID from t_supplier where t_supplier.`Name` like ?)) ORDER BY t_order.DateOfSign desc limit ?,?";
			break;
		}
		sql += condition + " and t_order.ActualDelivery='0000-00-00' or t_order.ActualDelivery is null ";
		Object[] para = new Object[4];
		para[0] = PageType;
		para[1] = "%" + parameter + "%";
		para[2] = (page.getCurrentPage() - 1) * page.getRows();
		para[3] = page.getRows();

		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, para);

		return ls;
	}

	/**
	 * 物流页面单一查询
	 */
	@Override
	public List<Map<String, Object>> getOrderByTime(String classify, String start_time1, String end_time1, Page page,
			String condition,int PageType) {
		String sql = null;
		Object[] obj = new Object[2];
		if (condition.equals("AllNoSend")) {
			condition = " and t_order.Status<>14 ";
		}
		if (condition.equals("OtherNoSend")) {
			condition = " and t_order.Status<>17 and t_order.Status<>14 ";
		}
		if (condition.equals("All")) {
			condition = "";
		}
		// 生成参数数组
		obj[0] = start_time1;
		obj[1] = end_time1;
		// 构建sql语句
		int length = obj.length;
		sql = "select t_payment_status.Status WhetherToPay,t_order.ExpectedReceiptDate,t_order.ID,t_order.Customer,t_requirement_classify.Classify ContractCategory,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks,DATEDIFF(t_order.ActualDelivery,t_order.CargoPeriod) D3_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod) D3_D4,DATEDIFF(t_order.ExpectedDeliveryPeriod,t_order.CargoPeriod) D4_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.DateOfSign) D3_D1 from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID "
				+ "left join  t_quotes on t_quotes.OrderID=t_order.ID "
				+ "left join t_payment_status on t_quotes.WhetherToPay=t_payment_status.ID "
				+ "left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID  where t_order.PageType=?";
		Object[] param;
		if (!"".equals(start_time1) && !"".equals(end_time1)) {
			sql += " and (" + classify_MAP.get(classify) + " between ? and ?)";
			switch (classify) {
			case "合同货期":
				sql += condition + " and ActualDelivery='0000-00-00' order by t_order.CargoPeriod desc limit ?,?";
				break;
			case "预计货期":
				sql += condition
						+ " and ActualDelivery='0000-00-00' order by t_order.ExpectedDeliveryPeriod desc limit ?,?";
				break;
			default:
				sql += condition + " order by t_order.DateOfSign desc limit ?,?";
				break;
			}

			// 构建带有分页信息的参数数组
			param = new Object[obj.length + 3];
			param[0] = PageType;
			for (int i = 0; i < length; i++) {
				param[i+1] = obj[i];
			}
			param[length] = (page.getCurrentPage() - 1) * page.getRows();
			param[length + 1] = page.getRows();
			for(int i = 0;i < param.length;i ++){
				System.out.println("uuuuuuuuuuuu"+param[i]);
			}
		} else {
			switch (classify) {
			case "合同货期":
				sql += " order by t_order.CargoPeriod desc limit ?,?";
				break;
			case "预计货期":
				sql += " order by t_order.ExpectedDeliveryPeriod desc limit ?,?";
				break;
			default:
				sql += " order by t_order.DateOfSign desc limit ?,?";
				break;
			}
			param = new Object[] { PageType,(page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		}
		
		System.out.println(sql);
		return new OrderDao().getOrder(sql, param);
	}

	/**
	 * 按货期导出用的
	 * 
	 * @param classify
	 * @param parameter
	 * @param parameter2
	 * @param page
	 * @param condition
	 * @return
	 */
	public List<Map<String, Object>> getOrderExportByTime(String classify, Object parameter, Object parameter2,
			String condition,int type) {
		String sql = null;
		Object[] obj = new Object[2];
		if (condition.equals("AllNoSend")) {
			condition = " and t_order.Status<>14 ";
		}
		if (condition.equals("OtherNoSend")) {
			condition = " and t_order.Status<>17 and t_order.Status<>14 ";
		}
		if (condition.equals("All")) {
			condition = "";
		}
		// 生成参数数组
		obj[0] = parameter;
		obj[1] = parameter2;
		// 构建sql语句
		int length = obj.length;
		sql = "select t_payment_status.Status WhetherToPay,t_order.ActualPaymentTime,t_order.ExpectedReceiptDate,t_order.ID,t_order.Customer,t_requirement_classify.Classify ContractCategory,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID "
				+ "left join  t_quotes on t_quotes.OrderID=t_order.ID "
				+ "left join t_payment_status on t_quotes.WhetherToPay=t_payment_status.ID "
				+ "left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID where t_order.PageType=? ";
		Object[] param = null;
		if (!"".equals(parameter) && !"".equals(parameter2)) {
			sql += " and t_order." + classify_MAP.get(classify) + " between ? and ?" + condition;

			switch (classify) {
			case "合同货期":
				sql += " and ActualDelivery='0000-00-00' order by t_order.CargoPeriod desc ";
				break;
			case "预计货期":
				sql += " and ActualDelivery='0000-00-00' order by t_order.ExpectedDeliveryPeriod desc ";
				break;
			default:
				sql += " order by t_order.DateOfSign desc ";
				break;
			}

			// 构建带有分页信息的参数数组
			param = new Object[obj.length+1];
			param[0] = type;
			for (int i = 0; i < length; i++) {
				param[i+1] = obj[i];
			}

		} else {
			switch (classify) {
			case "合同货期":
				sql += " order by t_order.CargoPeriod desc ";
				break;
			case "预计货期":
				sql += " order by t_order.ExpectedDeliveryPeriod desc ";
				break;
			default:
				sql += " order by t_order.DateOfSign desc ";
				break;
			}
			param = new Object[]{type};
		}

		return new OrderDao().getOrder(sql, param);
	}

	public List<Map<String, Object>> getOrderExportByTime(String classify, Object parameter, Object parameter2,int type) {
		String sql = null;
		Object[] obj = new Object[2];

		// 生成参数数组
		obj[0] = parameter;
		obj[1] = parameter2;
		// 构建sql语句
		int length = obj.length;
		sql = "select t_payment_status.Status WhetherToPay,t_order.ActualPaymentTime,t_order.ExpectedReceiptDate,t_order.ID,t_order.Customer,t_requirement_classify.Classify ContractCategory,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID "
				+ "left join  t_quotes on t_quotes.OrderID=t_order.ID "
				+ "left join t_payment_status on t_quotes.WhetherToPay=t_payment_status.ID "
				+ "left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID where t_order.PageType=? ";
		Object[] param = null;
		if (!"".equals(parameter) && !"".equals(parameter2)) {
			sql += " and t_order." + classify_MAP.get(classify) + " between ? and ?";
			switch (classify) {
			case "合同货期":
				sql += "  order by t_order.CargoPeriod desc ";
				break;
			case "预计货期":
				sql += " order by t_order.ExpectedDeliveryPeriod desc ";
				break;
			default:
				sql += " order by t_order.DateOfSign desc ";
				break;
			}
			// 构建带有分页信息的参数数组
			param = new Object[obj.length+1];
			param[0] = type;
			for (int i = 0; i < length; i++) {
				param[i+1] = obj[i];
			}

		} else {
			switch (classify) {
			case "合同货期":
				sql += " order by t_order.CargoPeriod desc ";
				break;
			case "预计货期":
				sql += " order by t_order.ExpectedDeliveryPeriod desc ";
				break;
			default:
				sql += " order by t_order.DateOfSign desc ";
				break;
			}
			param = new Object[]{type};
		}

		return new OrderDao().getOrder(sql, param);
	}

	/**
	 * 物流页面单一查询
	 */
	@Override
	public List<Map<String, Object>> getOrderByPageInOne(String classify, Object parameter, Page page,
			String condition,int PageType) {
		String sql = null;
		Object[] obj = null;
		if (condition.equals("AllNoSend")) {
			condition = " and t_order.Status<>14 and";
		}
		if (condition.equals("OtherNoSend")) {
			condition = " and t_order.Status<>17 and t_order.Status<>14 and";
		}
		if (condition.equals("All")) {
			condition = "";
		}
		String temp = null;
		// 生成参数数组
		switch (classify_MAP.get(classify).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj = getIDByBlur(classify, parameter.toString());
			break;
		case "ContractCategory":
			obj = getIDByBlur(classify, parameter.toString());
			break;
		default:
			obj = new Object[1];
			obj[0] = "%" + parameter + "%";
		}

		// 构建sql语句
		int length = obj.length; // 可能0
		System.out.println("参数长度"+length);

		sql = "select t_order.QuoteNumber,t_quote_system.Number,t_order.TechnologyPath,t_order.ActualPaymentTime,t_order.ContractPath,t_payment_status.Status WhetherToPay,t_order.isSend,t_order.ExpectedReceiptDate,t_order.ID,t_order.Customer,t_requirement_classify.Classify ContractCategory,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks,DATEDIFF(t_order.ActualDelivery,t_order.CargoPeriod) D3_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod) D3_D4,DATEDIFF(t_order.ExpectedDeliveryPeriod,t_order.CargoPeriod) D4_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.DateOfSign) D3_D1 from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID "
				+ "left join  t_quotes on t_quotes.OrderID=t_order.ID "
				+ "left join t_payment_status on t_quotes.WhetherToPay=t_payment_status.ID "
				+ "left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID "
				+ "  left join t_quote_system on t_order.QuoteNumber=t_quote_system.ID where t_order.PageType=? ";

		if (length == 0) {
			sql += " and t_order." + classify_MAP.get(classify) + " = ?";
		}
		for (int i = 0; i < length; i++) {

			sql += " and t_order." + classify_MAP.get(classify) + " like ?";
			if (i < length - 1)
				sql += " or ";
		}
		sql += condition
				+ " t_order.ActualDelivery='0000-00-00' or t_order.ActualDelivery is null order by t_order.DateOfSign desc limit ?,?";

		// 构建带有分页信息的参数数组
		Object[] param = null;
		if (length == 0) {
			param = new Object[ 3];
			param[0] = PageType;
			param[1] = (page.getCurrentPage() - 1) * page.getRows();
			param[2] = page.getRows();
		} else {
			param = new Object[obj.length + 3];
			param[0] = PageType;
			for (int i = 0; i < length; i++) {
				param[i+1] = obj[i];
			}

			param[length+1] = (page.getCurrentPage() - 1) * page.getRows();
			param[length + 2] = page.getRows();
		}

		// System.out.println("参数数组"+Arrays.toString(param));

		return new OrderDao().getOrder(sql, param);
	}

	/**
	 * 物流页面单一查询
	 */
	@Override
	public int getCountByTimeClassify(String classify1, Map<String, Object> map1, String classify2,
			Map<String, Object> map2, String condition,int type) {
		// 前半句sql语句构建
		String sql1 = null;
		Object[] obj1 = null;
		if (condition.equals("AllNoSend")) {
			condition = " and t_order.Status<>14 and";
		}
		if (condition.equals("OtherNoSend")) {
			condition = " and t_order.Status<>17 and t_order.Status<>14 and";
		}
		if (condition.equals("All")) {
			condition = "";
		}
		/*
		 * map.put("合同货期", "CargoPeriod"); map.put("实际货期", "ActualDelivery");
		 * map.put("预计货期", "ExpectedDeliveryPeriod");
		 */
		switch (classify_MAP.get(classify1).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
		case "ContractCategory":
			obj1 = getIDByBlur(classify1, map1.get("1").toString());
			break;
		case "CargoPeriod":
		case "ActualDelivery":
		case "ExpectedDeliveryPeriod":
			obj1 = new Object[2];
			obj1[0] = map1.get("1").toString();
			obj1[1] = map1.get("2").toString();
			break;
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + map1.get("1").toString() + "%";
		}
		int length11 = map1.size();

		if (length11 == 1) {
			sql1 = "select count(ID) from t_order  where PageType=?";
			for (int i = 0; i < obj1.length; i++) {
				sql1 += (i==0)?" and ":"";
				sql1 += "t_order." + classify_MAP.get(classify1) + " like ?";
				if (i < obj1.length - 1)
					sql1 += " or ";
			}
			sql1 += " and ";
		} else if (length11 == 2) {
			if (!"".equals(map1.get("1").toString()) && !"".equals(map1.get("2").toString())) {
				sql1 = "select count(ID) from t_order  where PageType=? and ";
				sql1 += "t_order." + classify_MAP.get(classify1) + " between ? and ?";
				sql1 += " and ";
			} else {
				sql1 = "select count(ID) from t_order where PageType=? and ";
				obj1 = null;
			}
		}
		// 后半句sql语句构建
		String sql2 = null;
		Object[] obj2 = null;

		switch (classify_MAP.get(classify2).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
		case "ContractCategory":
			obj2 = getIDByBlur(classify2, map2.get("1").toString());
			break;
		case "CargoPeriod":
		case "ActualDelivery":
		case "ExpectedDeliveryPeriod":
			obj2 = new Object[2];
			obj2[0] = map2.get("1").toString();
			obj2[1] = map2.get("2").toString();
			break;
		default:
			obj2 = new Object[1];
			obj2[0] = "%" + map2.get("1").toString() + "%";
		}
		int length22 = map2.size();

		if (length22 == 1) {
			sql2 = "select t_order." + classify_MAP.get(classify2) + " from t_order where ";
			for (int i = 0; i < obj2.length; i++) {
				sql2 += "t_order." + classify_MAP.get(classify2) + " like ?";
				if (i < obj2.length - 1)
					sql2 += " or ";
			}
		} else if (length22 == 2) {
			if (!"".equals(map2.get("1").toString()) && !"".equals(map2.get("2").toString())) {
				sql2 = "select t_order." + classify_MAP.get(classify2) + " from t_order where ";
				sql2 += " t_order." + classify_MAP.get(classify2) + " between ? and ?";
			} else {
				sql2 = "select t_order." + classify_MAP.get(classify2) + " from t_order ";
				obj2 = null;
			}
		}
		String sql = sql1 + "t_order." + classify_MAP.get(classify2) + " in(" + sql2 + ")" + condition
				+ "  t_order.ActualDelivery='0000-00-00' or t_order.ActualDelivery is null ";
		int length1;
		int length2;
		int allCounts = 0;
		if (obj1 != null && obj2 != null) {
			allCounts = obj1.length + obj2.length;
			length1 = obj1.length;
			length2 = obj2.length;
		} else if (obj1 == null && obj2 != null) {
			allCounts = obj2.length;
			length1 = 0;
			length2 = obj2.length;
		} else if (obj2 == null && obj1 != null) {
			allCounts = obj1.length;
			length2 = 0;
			length1 = obj1.length;
		} else {
			allCounts = 0;
			length1 = 0;
			length2 = 0;
		}
		Object[] sqlObj = new Object[allCounts+1];
		sqlObj[0] = type;
		int times = 1;
		for (int i = 0; i < length1; i++) {
			sqlObj[times++] = obj1[i];
		}
		for (int i = 0; i < length2; i++) {
			sqlObj[times++] = obj2[i];
		}
		return new OrderDao().getCountsByName(sql, sqlObj);
	}

	/**
	 * 物流页面单一查询
	 */
	@Override
	public int getCountByClassify(String classify1, Object parameter1, String classify2, Object parameter2,
			String condition,int type) {
		if (condition.equals("AllNoSend")) {
			condition = " and t_order.Status<>14 and";
		}
		if (condition.equals("OtherNoSend")) {
			condition = " and t_order.Status<>17 and t_order.Status<>14 and";
		}
		if (condition.equals("All")) {
			condition = "";
		}
		String sql1 = null;
		Object[] obj1 = null;

		switch (classify_MAP.get(classify1).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj1 = getIDByBlur(classify1, parameter1.toString());
			break;
		case "ContractCategory":
			obj1 = getIDByBlur(classify1, parameter1.toString());
			break;
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + parameter1 + "%";
		}
		int length1 = obj1.length;
		sql1 = "select count(ID) from t_order where PageType=? ";
		for (int i = 0; i < length1; i++) {
			sql1 += (i==0)?" and ":"";
			sql1 += "t_order." + classify_MAP.get(classify1) + " like ?";
			if (i < length1 - 1)
				sql1 += " or ";
		}

		// 后半句sql语句构建
		String sql2 = null;
		Object[] obj2 = null;

		switch (classify_MAP.get(classify2).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj2 = getIDByBlur(classify2, parameter2.toString());
			break;
		case "ContractCategory":
			obj2 = getIDByBlur(classify2, parameter2.toString());
			break;
		default:
			obj2 = new Object[1];
			obj2[0] = "%" + parameter2 + "%";
		}
		int length2 = obj2.length;
		sql2 = "select t_order." + classify_MAP.get(classify2) + " from t_order where ";
		for (int i = 0; i < length2; i++) {
			sql2 += "t_order." + classify_MAP.get(classify2) + " like ?";
			if (i < length2 - 1)
				sql2 += " or ";
		}

		String sql = sql1 + " and t_order." + classify_MAP.get(classify2) + " in(" + sql2 + ")" + condition
				+ " ActualDelivery='0000-00-00' or ActualDelivery is null";
		int allCounts = obj1.length + obj2.length;
		Object[] sqlObj = new Object[allCounts];
		int times = 0;
		for (int i = 0; i < length1; i++) {
			sqlObj[times++] = obj1[i];
		}
		for (int i = 0; i < length2; i++) {
			sqlObj[times++] = obj2[i];
		}

		return new OrderDao().getCountsByName(sql, sqlObj);
	}

	/**
	 * 物流页面单一查询
	 */
	@Override
	public List<Map<String, Object>> getOrderByPageInTwoTime(String classify1, Map<String, Object> map1,
			String classify2, Map<String, Object> map2, Page page, String condition,int type) {
		// 前半句sql语句构建
		String sql1 = null;
		Object[] obj1 = null;
		if (condition.equals("AllNoSend")) {
			condition = " and t_order.Status<>14 and";
		}
		if (condition.equals("OtherNoSend")) {
			condition = " and t_order.Status<>17 and t_order.Status<>14 and";
		}
		if (condition.equals("All")) {
			condition = "";
		}
		switch (classify_MAP.get(classify1).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
		case "ContractCategory":
			obj1 = getIDByBlur(classify1, map1.get("1").toString());
			break;
		case "CargoPeriod":
		case "ActualDelivery":
		case "ExpectedDeliveryPeriod":
			obj1 = new Object[2];
			obj1[0] = map1.get("1").toString();
			obj1[1] = map1.get("2").toString();
			break;
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + map1.get("1").toString() + "%";
		}
		sql1 = "select t_quote_system.Number,t_order.TechnologyPath,t_order.ActualPaymentTime,t_order.ContractPath,t_payment_status.Status WhetherToPay,t_order.ExpectedReceiptDate,t_order.ID,t_order.Customer,t_area.AreaName Area,t_requirement_classify.Classify ContractCategory,t_order.ContractNo,t_order.ContractTitle,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks,DATEDIFF(t_order.ActualDelivery,t_order.CargoPeriod) D3_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod) D3_D4,DATEDIFF(t_order.ExpectedDeliveryPeriod,t_order.CargoPeriod) D4_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.DateOfSign) D3_D1 from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID "
				+ "left join  t_quotes on t_quotes.OrderID=t_order.ID "
				+ "left join t_payment_status on t_quotes.WhetherToPay=t_payment_status.ID "
				+ "left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID "
				+ "  left join t_quote_system on t_order.QuoteNumber=t_quote_system.ID ";
		int length1 = map1.size();
		if (length1 == 1) {
			sql1 += "where t_order.PageType=? ";
			for (int i = 0; i < obj1.length; i++) {
				sql1 += (i==0)?" and ":"";
				sql1 += "t_order." + classify_MAP.get(classify1) + " like ?";
				if (i < obj1.length - 1)
					sql1 += " or ";
			}
		} else if (length1 == 2) {
			if (!"".equals(map1.get("1").toString()) && !"".equals(map1.get("2").toString())) {
				sql1 += "where t_order.PageType=? and t_order.";
				sql1 += classify_MAP.get(classify1) + " between ? and ?";
			} else {
				obj1 = null;
			}
		}

		// 后半句sql语句构建
		String sql2 = null;
		Object[] obj2 = null;

		switch (classify_MAP.get(classify2).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
		case "ContractCategory":
			obj2 = getIDByBlur(classify2, map2.get("1").toString());
			break;
		case "CargoPeriod":
		case "ActualDelivery":
		case "ExpectedDeliveryPeriod":
			obj2 = new Object[2];
			obj2[0] = map2.get("1").toString();
			obj2[1] = map2.get("2").toString();
			break;
		default:
			obj2 = new Object[1];
			obj2[0] = "%" + map2.get("1").toString() + "%";
		}
		int length2 = map2.size();

		if (length2 == 1) {
			sql2 = "select t_order." + classify_MAP.get(classify2) + " from t_order where ";
			for (int i = 0; i < obj2.length; i++) {
				sql2 += "t_order." + classify_MAP.get(classify2) + " like ?";
				if (i < obj2.length - 1)
					sql2 += " or ";
			}
		} else if (length2 == 2) {
			if (!"".equals(map2.get("1").toString()) && !"".equals(map2.get("2").toString())) {
				sql2 = "select t_order." + classify_MAP.get(classify2) + " from t_order where ";
				sql2 += "t_order." + classify_MAP.get(classify2) + " between ? and ?";
			} else {
				sql2 = "select t_order." + classify_MAP.get(classify2) + " from t_order ";
				obj2 = null;
			}
		}

		String sql = sql1 + " and t_order." + classify_MAP.get(classify2) + " in(" + sql2 + ")";
		// 构建带有分页信息的参数数组
		int allCounts = 0;
		if (obj1 == null && obj2 != null) {
			allCounts = obj2.length;
			length2 = obj2.length;
			length1 = 0;
		} else if (obj2 == null && obj1 != null) {
			allCounts = obj1.length;
			length1 = obj1.length;
			length2 = 0;
		} else if (obj1 != null && obj2 != null) {
			allCounts = obj1.length + obj2.length;
			length1 = obj1.length;
			length2 = obj2.length;
		} else {
			allCounts = 0;
			length1 = 0;
			length2 = 0;
		}
		Object[] sqlObj = new Object[allCounts + 3];
		sqlObj[0] = type;
		int times = 1;
		for (int i = 0; i < length1; i++) {
			sqlObj[times++] = obj1[i];
		}
		for (int i = 0; i < length2; i++) {
			sqlObj[times++] = obj2[i];
		}
		
		sql += condition
				+ " ActualDelivery='0000-00-00' or ActualDelivery is null order by t_order.DateOfSign desc limit ?,?";
		sqlObj[allCounts+1] = (page.getCurrentPage() - 1) * page.getRows();
		sqlObj[allCounts + 2] = page.getRows();
		return new OrderDao().getOrder(sql, sqlObj);

	}

	/**
	 * 物流页面单一查询
	 */
	@Override
	public List<Map<String, Object>> getOrderByPageInTwo(String classify1, Object parameter1, String classify2,
			Object parameter2, Page page, String condition,int type) {
		// 前半句sql语句构建
		String sql1 = null;
		Object[] obj1 = null;
		Object[] sqlObj = null;// 参数
		String sql = null;// 整句
		if (condition.equals("AllNoSend")) {
			condition = " and t_order.Status<>14 and";
		}
		if (condition.equals("OtherNoSend")) {
			condition = " and t_order.Status<>17 and t_order.Status<>14 and";
		}
		if (condition.equals("All")) {
			condition = "";
		}
		switch (classify_MAP.get(classify1).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj1 = getIDByBlur(classify1, parameter1.toString());
			break;
		case "ContractCategory":
			obj1 = getIDByBlur(classify1, parameter1.toString());
			break;
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + parameter1 + "%";
		}
		int length1 = obj1.length;
		sql1 = "select t_order.QuoteNumber,t_quote_system.Number,t_order.TechnologyPath,t_order.ContractPath,t_payment_status.Status WhetherToPay,t_order.ExpectedReceiptDate,t_order.ID,t_order.Customer,t_area.AreaName Area,t_requirement_classify.Classify ContractCategory,t_order.ContractNo,t_order.ContractTitle,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks,DATEDIFF(t_order.ActualDelivery,t_order.CargoPeriod) D3_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod) D3_D4,DATEDIFF(t_order.ExpectedDeliveryPeriod,t_order.CargoPeriod) D4_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.DateOfSign) D3_D1 from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID "
				+ "left join  t_quotes on t_quotes.OrderID=t_order.ID "
				+ "left join t_payment_status on t_quotes.WhetherToPay=t_payment_status.ID "
				+ "left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID "
				+ "  left join t_quote_system on t_order.QuoteNumber=t_quote_system.ID where t_order.PageType=? ";// 去掉1(

		if (length1 == 0) {
			sql1 += " and t_order." + classify_MAP.get(classify1) + " = ?";
		}
		for (int i = 0; i < length1; i++) {
			sql1 += (i==0)?" and ":"";
			sql1 += "t_order." + classify_MAP.get(classify1) + " like ?";

			if (i < length1 - 1)
				sql1 += " or ";
		}

		// 后半句sql语句构建
		// String sql2=null;
		Object[] obj2 = null;

		switch (classify_MAP.get(classify2).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj2 = getIDByBlur(classify2, parameter2.toString());
			break;
		case "ContractCategory":
			obj2 = getIDByBlur(classify2, parameter2.toString());
			break;
		default:
			obj2 = new Object[1];
			obj2[0] = "%" + parameter2 + "%";
		}
		int length2 = obj2.length;
		if (length2 == 0) {
			sql1 += " and t_order." + classify_MAP.get(classify2) + " = ?";
		}
		// sql2 = "select t_order."+classify_MAP.get(classify2)+" from t_order
		// where ";
		for (int i = 0; i < length2; i++) {
			// sql2+="t_order."+classify_MAP.get(classify2)+" like ?";
			sql1 += " and t_order." + classify_MAP.get(classify2) + " like ?";
			if (i < length2 - 1)
				sql1 += " or ";
		}

		// String sql = sql1 +" ) and t_order."+classify_MAP.get(classify2)+"
		// in(" + sql2+")";
		sql = sql1;
		// 构建带有分页信息的参数数组
		int allCounts = obj1.length + obj2.length;

		int times = 0;
		if (length1 == 0 && length2 != 0) {
			sqlObj = new Object[allCounts + 3];
			sqlObj[0] = 0;
			for (int i = 0; i < length2; i++) {
				sqlObj[times++] = obj2[i];
			}
			sqlObj[allCounts + 1] = (page.getCurrentPage() - 1) * page.getRows();
			sqlObj[allCounts + 2] = page.getRows();
		} else if (length1 != 0 && length2 == 0) {
			sqlObj = new Object[allCounts + 3];
			for (int i = 0; i < length1; i++) {
				sqlObj[times++] = obj1[i];
			}
			sqlObj[1] = 0;
			sqlObj[allCounts + 1] = (page.getCurrentPage() - 1) * page.getRows();
			sqlObj[allCounts + 2] = page.getRows();
		} else if (length1 == 0 && length2 == 0) {
			sqlObj = new Object[allCounts + 4];
			sqlObj[0] = 0;
			sqlObj[1] = 0;
			sqlObj[allCounts + 2] = (page.getCurrentPage() - 1) * page.getRows();
			sqlObj[allCounts + 3] = page.getRows();
		} else {
			sqlObj = new Object[allCounts + 2];
			for (int i = 0; i < length1; i++) {
				sqlObj[times++] = obj1[i];
			}
			for (int i = 0; i < length2; i++) {
				sqlObj[times++] = obj2[i];
			}
			sqlObj[allCounts] = (page.getCurrentPage() - 1) * page.getRows();
			sqlObj[allCounts + 1] = page.getRows();
		}

		sql += condition
				+ "  ActualDelivery='0000-00-00' or ActualDelivery is null order by t_order.DateOfSign desc limit ?,?";

		return new OrderDao().getOrder(sql, sqlObj);

	}

	@Override
	public List<Map<String, Object>> exportOrder(String queryType, Object[] classify, Object[] parameter, String path,
			String condition) {


		List<Map<String, Object>> ls = null;
		if (queryType.equals("mixSelect")) {
			ls = getOrderByTwo(classify[0].toString(), parameter[0].toString(), classify[1].toString(),
					parameter[1].toString(),0);
		} else if (queryType.equals("singleSelect")) {
			if (classify[0].equals("PO(只能单一搜索)") || classify[0].equals("SO(只能单一搜索)")
					|| classify[0].equals("供应商(只能单一搜索)")) {
				ls = getOrderByPageInOneByPoSo(classify[0].toString(), parameter[0],0);
			} else if (classify[0].equals("合同货期") || classify[0].equals("实际货期") || classify[0].equals("预计货期")) {

				ls = getOrderExportByTime(classify[0].toString(), parameter[0], parameter[1], condition,0);
			} else {
				ls = getOrderByOne(classify[0].toString(), parameter[0], condition,0);
			}
		} else if (queryType.equals("common")) {
			ls = getOrderByNone(0);
		}

		return ls;
	}
	
	public List<Map<String, Object>> exportStockOrder(String queryType, Object[] classify, Object[] parameter, String path,
			String condition) {


		List<Map<String, Object>> ls = null;
		if (queryType.equals("mixSelect")) {
			ls = getOrderByTwo(classify[0].toString(), parameter[0].toString(), classify[1].toString(),
					parameter[1].toString(),1);
		} else if (queryType.equals("singleSelect")) {
			
			if (classify[0].equals("合同货期") || classify[0].equals("实际货期") || classify[0].equals("预计货期")) {

				ls = getOrderExportByTime(classify[0].toString(), parameter[0], parameter[1], condition,1);
			} else {
				ls = getOrderByOne(classify[0].toString(), parameter[0], condition,1);
			}
		} else if (queryType.equals("common")) {
			ls = getOrderByNone(1);
		}

		return ls;
	}

	@Override
	public Boolean exportOrderIncludePO(String queryType, Object[] classify, Object[] parameter, String path,
			String condition) {
		boolean flag = false;

		List<Map<String, Object>> ls = exportOrder(queryType, classify, parameter, path, condition);

		ls = getPOInfo(ls, queryType, classify, parameter, path, condition);

		String[] colName = new String[] { "客户名称", "联系人", "联系方式", "合同地区", "合同名称", "合同号", "合同类别", "销售代表", "合同签订日期",
				"合同货期", "实际货期", "预计货期", "合同状态", "装机时间", "装机地点", "备注", "合同报价USD", "合同报价RMB", "付款条件", "是否付款", "是否开具发票",
				"是否办理免税信用证", "收款日期1", "收款金额1", "收款日期2", "收款金额2", "收款日期3", "收款金额3", "预计收款日期", "开票日期", "PO号码", "SO号码",
				"PO金额USD", "PO金额RMB", "供应商", "工厂货期", "发货日期", "预计付款日期", "实际付款日期" };
		String[] dataName = new String[] { "Customer", "Contact", "ContactInfo", "Area", "ContractTitle", "ContractNo",
				"ContractCategory", "SalesRepresentative", "DateOfSign", "CargoPeriod", "ActualDelivery",
				"ExpectedDeliveryPeriod", "Status", "InstalledTime", "InstalledSite", "Remarks", "USDQuotes",
				"RMBQuotes", "PaymentTerms", "WhetherToPay", "WhetherToInvoice", "DutyFree", "ReceiptDate1",
				"ReceiptAmount1", "ReceiptDate2", "ReceiptAmount2", "ReceiptDate3", "ReceiptAmount3",
				"ExpectedReceiptDate", "BillingDate", "PONO", "SONO", "USD", "RMB", "Supplier", "FactoryShipment",
				"ActualDate", "EstimatedPaymentTime", "ActualPaymentTime" };
		try {
			new EXCELUtil().buildExcel(colName, dataName, ls, path);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public List<Map<String, Object>> getOrderByOne(String classify, Object parameter, String condition,int type) {
		if (condition.equals("AllNoSend")) {
			condition = " and t_order.Status<>14 and";
		}
		if (condition.equals("OtherNoSend")) {
			condition = " and t_order.Status<>17 and t_order.Status<>14 and";
		}
		if (condition.equals("All")) {
			condition = "";
		}
		String sql = null;
		Object[] obj = null;

		// 生成参数数组
		switch (classify_MAP.get(classify).toString()) {
		case "Area":
		case "Status":
		case "SalesRepresentative":
			obj = getIDByBlur(classify, parameter.toString());
			break;
		case "ContractCategory":
			obj = getIDByBlur(classify, parameter.toString());
			break;
		default:
			obj = new Object[1];
			obj[0] = "%" + parameter + "%";
		}

		// 构建sql语句
		int length = obj.length;
		sql = "select t_order.ID,t_order.ContractTitle,t_order.ActualPaymentTime,t_quotes.BillingDate,t_quotes.RMBQuotes,t_payment_terms.ID PaymentTermsID,t_order.ExpectedReceiptDate,t_quotes.USDQuotes,t_payment_terms.Condition PaymentTerms,t_payment_status.Status WhetherToPay,t_duty_free.Status DutyFree,t_quotes.WhetherToInvoice,"
				+ "t_order.ID,t_order.Customer,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,t_quotes.ReceiptDate1,t_quotes.ReceiptAmount1,t_quotes.ReceiptDate2,t_quotes.ReceiptAmount2,t_requirement_classify.Classify ContractCategory,t_quotes.ReceiptDate3,t_quotes.ReceiptAmount3,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID left join t_contract_status on t_order.Status=t_contract_status.ID  "
				+ " left join t_quotes on t_quotes.OrderID=t_order.ID left join t_payment_terms on t_quotes.PaymentTerms=t_payment_terms.ID left join t_payment_status "
				+ "on t_quotes.WhetherToPay=t_payment_status.ID left join t_duty_free on t_quotes.DutyFree=t_duty_free.ID left join t_requirement_classify on t_order.ContractCategory = t_requirement_classify.ID where PageType=? ";
		for (int i = 0; i < length; i++) {
			sql += " and " + classify_MAP.get(classify) + " like ?";
			if (i < length - 1)
				sql += " or ";
		}
		sql += condition + " t_order.ActualDelivery='0000-00-00' or t_order.ActualDelivery is null";
		sql += " order by t_order.DateOfSign desc";

		// 构建带有分页信息的参数数组
		Object[] param = new Object[obj.length+1];
		param[0] = type;
		for (int i = 0; i < length; i++) {
			param[i+1] = obj[i];
		}

		List<Map<String, Object>> ls = new OrderDao().getOrder(sql, param);
		return ls;
	}

	@Override
	public List<Map<String, Object>> getOrderByTwo(String classify1, Object parameter1, String classify2,
			Object parameter2, String condition,int type) {

		return null;
	}

	public List<Map<String, Object>> getPOInfo(List<Map<String, Object>> orderInfo, String queryType, Object[] classify,
			Object[] parameter, String path, String condition) {

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> ssmap = new HashMap<String, Object>();
		ssmap.put("11", "11");
		resultList.add(ssmap);
		int length = orderInfo.size();
		TransportService transportService = new TransportServiceImpl();
		for (int i = 1; i < length; i++) {
			Map<String, Object> midMap = orderInfo.get(i);
			int orderID = Integer.parseInt(midMap.get("ID").toString());

			// 根据合同ID统计出物流的信息,往list中增加物流信息的字段
			List<Map<String, Object>> poList = transportService.getPOAmountsByID(orderID);
			// System.out.println("物流size:"+poList.size());
			if (poList.size() > 1) {
				int poListLength = poList.size();
				for (int j = 1; j < poListLength; j++) {
					// System.out.println("poList:"+poList.get(j));
					midMap = exportOrder(queryType, classify, parameter, path, condition).get(i);// 导出订单不包括PONO信息
					// System.out.println(midMap);
					if (!poList.get(j).get("PONO").toString().equals("--")) {
						Map<String, Object> resultMap = midMap;
						resultMap.put("PONO", poList.get(j).get("PONO"));
						resultMap.put("ActualDate", poList.get(j).get("ActualDate"));
						resultMap.put("ActualPaymentTime", poList.get(j).get("ActualPaymentTime"));
						resultMap.put("EstimatedPaymentTime", poList.get(j).get("EstimatedPaymentTime"));
						resultMap.put("FactoryShipment", poList.get(j).get("FactoryShipment"));
						resultMap.put("Supplier", poList.get(j).get("Supplier"));
						resultMap.put("SONO", poList.get(j).get("SONO"));
						resultMap.put("USD", poList.get(j).get("USD"));
						resultMap.put("RMB", poList.get(j).get("RMB"));
						resultList.add(resultMap);
						// if(orderID == 2642){
						// System.out.println(111111111);
						// }
					} else if (poList.size() >= 2 && poList.get(j).get("PONO").toString().equals("--")) {
						Map<String, Object> resultMap = midMap;
						resultMap.put("PONO", "--");
						resultMap.put("ActualDate", "--");
						resultMap.put("ActualPaymentTime", "--");
						resultMap.put("EstimatedPaymentTime", "--");
						resultMap.put("FactoryShipment", "--");
						resultMap.put("Supplier", "--");
						resultMap.put("SONO", "--");
						resultMap.put("USD", "--");
						resultMap.put("RMB", "--");

						// if(orderID == 2642){
						// System.out.println(222222222);
						// }
						resultList.add(resultMap);
					}
				}
			} else if (poList.size() <= 1) {
				Map<String, Object> resultMap = midMap;
				resultMap.put("PONO", "--");
				resultMap.put("ActualDate", "--");
				resultMap.put("ActualPaymentTime", "--");
				resultMap.put("EstimatedPaymentTime", "--");
				resultMap.put("FactoryShipment", "--");
				resultMap.put("Supplier", "--");
				resultMap.put("SONO", "--");
				resultMap.put("USD", "--");
				resultMap.put("RMB", "--");
				// if(orderID == 2642){
				// System.out.println(333333333);
				// }

				resultList.add(resultMap);
			}

		}

		return resultList;
	}


	@Override
	public List<Map<String,Object>> MatchQuotation(HttpServletRequest request) {
		boolean result = true;
		int QuoteID = Integer.parseInt(request.getParameter("QuoteID")==null?"0":request.getParameter("QuoteID"));
		int OrderID = Integer.parseInt(request.getParameter("OrderID")==null?"0":request.getParameter("OrderID"));
		QuoteSystemService service =new QuoteSystemServiceImpl();
		List<Map<String,Object>> ls = service.getCommodityInfo(QuoteID);
		OrderInfoDao infoDao = new OrderInfoDao();
		LogisticsDao logDao = new LogisticsDao();
		if(ls.size()>1){
			for(int i=1;i<ls.size();i++){
				String model = ls.get(i).get("EquipmentModel").toString();
				String remarks = ls.get(i).get("Remarks").toString();
				int number = Integer.parseInt(ls.get(i).get("Number")==null?"0":ls.get(i).get("Number").toString());
				int CommodityID = Integer.parseInt(ls.get(i).get("CommodityID")==null?"0":ls.get(i).get("CommodityID").toString());
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderID(OrderID);
				orderInfo.setCommodityID(CommodityID);
				orderInfo.setEquipmentModel(CommodityID);
				orderInfo.setNumber(number);
				orderInfo.setLogisticsNumber(0);
				if(infoDao.isExist(OrderID, CommodityID)){
					result = false;
				}else{
					int OrderInfoID = 0;
					try {
						OrderInfoID = infoDao.insertOrderInfo(orderInfo);
						if(OrderInfoID==0){
							result = false;
							break;
						}
						Logistics logstics = new Logistics();
						logstics.setOrderID(OrderID);
						logstics.setOrderInfoID(OrderInfoID);
						if(!logDao.insertEmpty(logstics, new DBUtil())){
							result = false;
							break;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				
			}
		}
		if(result){
			return ls;
		}else{
			return null;
		}
		
	}

	@Override
	public boolean addPurchaseInfo(HttpServletRequest request) {
		int orderID = request.getParameter("OrderID") == null?0:Integer.parseInt(request.getParameter("OrderID"));
		String contractPath = request.getParameter("ContractPath") == null?"":request.getParameter("ContractPath");
		String product = request.getParameter("product") == null?"":request.getParameter("product");
		String money  = request.getParameter("Money") == null?"":request.getParameter("Money");
		String currency = request.getParameter("Currency") == null?"":request.getParameter("Currency");
		String use = request.getParameter("Use") == null?"":request.getParameter("Use");
		String supplier = request.getParameter("Supplier") == null?"":request.getParameter("Supplier");
		SupplierBankDao dao = new SupplierBankDao();
		List<Map<String, Object>> list = dao.querySupplier(supplier);
		
		String company = request.getParameter("Company") == null?"":request.getParameter("Company");
		String account = request.getParameter("Account") == null?"":request.getParameter("Account");
		String bank = request.getParameter("Bank") == null?"":request.getParameter("Bank");
		SupplierBank bankInfo = new SupplierBank();
		bankInfo.setSupplier(supplier);
		bankInfo.setCompany(company);
		bankInfo.setAccount(account);
		bankInfo.setBank(bank);
		if(list.size()>1){
			dao.updateSupplierBank(bankInfo);
		}else{
			dao.addSupplierBank(bankInfo);
		}
		
		
		PurchaseInfo pInfo = new PurchaseInfo();
		pInfo.setOrderID(orderID);
		pInfo.setProduct(product);
		pInfo.setMoney(money);
		pInfo.setCurrency(currency);
		pInfo.setUse(use);
		pInfo.setContractPath(contractPath);
		OrderDao dao2 = new OrderDao();
		List<Map<String, Object>> exist = dao2.queryPurchaseInfo(orderID);
		if(exist.size() >1){
			return dao2.updatePurchaseInfo(pInfo);
		}else{
			return dao2.addPurchaseInfo(pInfo);
		}
	}

	@Override
	public List<Map<String, Object>> getSupplierBank(String supplier) {
		SupplierBankDao dao = new SupplierBankDao();
		return dao.querySupplier(supplier);
	}

	@Override
	public List<Map<String, Object>> queryPurchaseInfo(int ID,String supplier) {
		OrderDao dao = new OrderDao();
		SupplierBankDao dao2 = new SupplierBankDao();
		List<Map<String,Object>> bankInfo = dao2.querySupplier(supplier);
		List<Map<String, Object>> purchaseInfo = dao.queryPurchaseInfo(ID);
		bankInfo.addAll(purchaseInfo);
		return bankInfo;
	}

	@Override
	public boolean setPurchaseMail(int ID) {
		OrderDao dao = new OrderDao();
		return dao.setPurchaseMail(ID);
	}

	@Override
	public String saveBillCharge(BillCharge charge) {
		
		OrderDao dao = new OrderDao();
		boolean flag = dao.saveBillCharge(charge);
		return flag == true?"保存成功":"保存失败";
	
	}

	@Override
	public List<Map<String, Object>> getBillCharge(int ID) {
		
		return new OrderDao().getBillCharge(ID);
	}
	

	
	public String sendInvoiceEmail(HttpServletRequest req){
		String subject = req.getParameter("Subject");
		String content = req.getParameter("Content");
		MethodUtil util = new MethodUtil();
		content = util.getEmailSign(content, "NA");
		String[] to = null;
		String[] copyto = null;
		Properties pro = new Properties();
		Properties pro2 = new Properties();
		List<String> list = new ArrayList<>();
		List<String> ls = new ArrayList<>();
		String user;
		String uname;
		String pwd;
	
		try {
			pro.load(SendMailUtil.class.getResourceAsStream("email.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		user = pro.getProperty("SEND_USER");
		uname = pro.getProperty("SEND_UNAME");
		pwd = pro.getProperty("SEND_PWD");
		
		try {
			pro2.load(SendMailUtil.class.getResourceAsStream("applicationInvoice.properties"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		int toCount = Integer.parseInt(pro2.getProperty("to"));
		for(int i=0 ; i<toCount ; i++){
			int temp = i+1;
			String key = "To"+temp;
			list.add(pro2.getProperty(key));
			
		}
		to = new String[toCount];
		for(int i=0 ; i<list.size();i++){
			to[i] = list.get(i);
		}
		int copytoCount = Integer.parseInt(pro2.getProperty("copyto"));
		copyto = new String[copytoCount];
		for(int i=0 ; i<copytoCount ; i++){
			int temp = i+1;
			String key = "CopyTo"+temp;
			ls.add(pro2.getProperty(key));
			
		}
		for(int i=0 ; i<ls.size();i++){
			copyto[i] = ls.get(i);
		}
		ExportApplicationInvoice invoice = new ExportApplicationInvoice();
	
		FileOutputStream foStream = null;
		
		String folderName = invoice.getInvoiceFolder(req);
		try {
			foStream = new FileOutputStream(folderName+".zip");
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}
		ZipUtils.toZip(folderName, foStream, true);
		return new JavaMailToolsUtil(user, uname, pwd).sendHtmlEmail(subject, content, new String[]{folderName+".zip"}, to, copyto);
	
	}

	@Override
	public int getShippedCounts(int type, String startTime, String endTime) {
		
		return new OrderDao().getShippedCounts(type, startTime, endTime);
	}

	@Override
	public List<Map<String, Object>> getTransportDetail(Page page, String condition, int type) {
		
		return new OrderDao().getTransportDetail(page, condition, type);
	}

	@Override
	public List<Map<String, Object>> getShippedDetail(Page page,int type, String startTime, String endTime) {
		
		return new OrderDao().getShippedDetail(page,type, startTime, endTime);
	}

	@Override
	public boolean exportOrderExcel(String path, int type) {
		OrderDao dao = new OrderDao();
		List<Map<String, Object>> list = dao.getOrderExcel(type);
		boolean result = false;
		try{
			EXCELUtil.buildOrderExcel(list, path);
			result = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/*
	public boolean splitCurrency(){
		boolean flag = false;
		OrderDao dao = new OrderDao();
		List<Map<String, Object>> map = dao.getAllPurchaseInfo();
		for(int i = 1;i < map.size();i ++){
			String money = map.get(i).get("Money").toString();
			if(!money.equals("")){
				int index = 0;
				for(int j = 0;j < money.length();j ++){
					if(money.charAt(j)>=48 && money.charAt(j)<=57){
						index = j;
						break;
					}
				}
				String currency = money.substring(0,index);
				money = money.substring(index);
				System.out.println("currency:"+currency);
				System.out.println("money:"+money);
				int orderID = Integer.parseInt(map.get(i).get("OrderID").toString());
				System.out.println("ID:"+orderID+"   "+dao.setCurrency(orderID, currency, money));
			}
		}
		flag = true;
		return flag;
		
	}
	
	public boolean splitProgress(){
		boolean flag = false;
		HardwareAdvancesDao dao = new HardwareAdvancesDao();
		List<Map<String, Object>> map = dao.getAllHardwareAdvances();
		for(int i = 1;i < map.size();i ++){
			String responsible = map.get(i).get("ResponsibleAndProcess").toString();
			if(!responsible.equals("")){
				String man = null;
				String progress = null;
				if(responsible.contains("：")){
					man = responsible.split("：",2)[0];
					progress = responsible.split("：",2)[1];
					
					
				}else{
					man = "";
					progress = responsible;
				}
				
				System.out.println("currency:"+man);
				System.out.println("money:"+progress);
				int ID = Integer.parseInt(map.get(i).get("ID").toString());
				System.out.println("ID:"+ID+"   "+dao.updateProgress(ID, progress, man));
				System.out.println("ID:"+ID+"   "+dao.insertProgress(ID, progress));
			}
		}
		flag = true;
		return flag;
		
	}
	*/
	@Test
	public void test(){
		String path = "E:\\合同统计2018.xlsx";
		OrderService service = new OrderServiceImpl();
		service.exportOrderExcel(path, 0);
	}




		
}

	


