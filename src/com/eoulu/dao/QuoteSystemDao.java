package com.eoulu.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.CascadePOMail;
import com.eoulu.entity.CommodityContracts;
import com.eoulu.entity.CommodityInfo;
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
import com.eoulu.util.DBUtil;

public class QuoteSystemDao {
	public static final Map<String, String> customerClassify_MAP;

	static {
		Map<String, String> map = new HashMap<>();
		map.put("客户名称", "CustomerName");
		map.put("联系人", "Contact");
		map.put("联系方式1", "ContactInfo1");
		map.put("联系方式2", "ContactInfo2");
		map.put("客户区域", "Area");
		map.put("电子邮箱", "Email");

		customerClassify_MAP = map;
	}

	/**
	 * 所有的客户资料
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getCustomerInfo() {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select ID,Contact CustomerName,ContactInfo1 CustomerTel,ContactInfo2 CustomerTelTwo,CustomerName CustomerCompany,FaxNumber CustomerFax,Email CustomerMail,ShorthandCoding CustomerCode from t_customer ";
		Object[] parameter = null;
		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 根据客户名称查询
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getCustomerInfoByCustomerName(String param) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select ID,Contact CustomerName,ContactInfo1 CustomerTel,ContactInfo2 CustomerTelTwo,CustomerName CustomerCompany,FaxNumber CustomerFax,Email CustomerMail,ShorthandCoding CustomerCode from t_customer where CustomerName like ?";
		Object[] parameter = new Object[] { param };
		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 根据联系人查询
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getCustomerInfoByContact(String param) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select ID,Contact CustomerName,ContactInfo1 CustomerTel,ContactInfo2 CustomerTelTwo,CustomerName CustomerCompany,FaxNumber CustomerFax,Email CustomerMail,ShorthandCoding CustomerCode from t_customer where Contact like ?";
		Object[] parameter = new Object[] { param };
		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 所有业务员
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getStaffInfo(String department) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "";
		Object[] parameter = null;
		if(department.equals("")){
			sql = "select ID,DepartmentCode,StaffCode,StaffName,Gender,Birthday,Job,EntryDate,LinkTel,DetailAddress,Quit,StaffMail from t_staff";
		}else{
			sql = "select ID,StaffName,StaffMail from t_staff where Department=?";
			parameter = new Object[]{department};
		}
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 根据业务员姓名
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getStaffInfoByStaffName(String param) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select ID,DepartmentCode,StaffCode,StaffName,Department,Gender,Birthday,Job,EntryDate,LinkTel,DetailAddress,Quit,StaffMail from t_staff where StaffName like ?";
		Object[] parameter = new Object[] { param };
		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 根据业务员部门
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getStaffInfoByDepartment(String param) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select ID,DepartmentCode,StaffCode,StaffName,Gender,Birthday,Job,EntryDate,LinkTel,DetailAddress,Quit,StaffMail from t_staff where Department like ?";
		Object[] parameter = new Object[] { param };
		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 所有商品
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getCommodityInfo() {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql="select t_commodity_info.Item,t_commodity_info.ItemDescription ,t_commodity_info.ID,"
				+ "t_commodity_info.CommodityName,t_commodity_info.Model,t_commodity_info.Unit,"
				+ "t_commodity_info.DeliveryPeriod,t_commodity_info.ProducingArea,t_commodity_info.CostPrice,"
				+ "t_commodity_info.DiscountCost,t_commodity_info.SellerPriceOne UnitPrice,"
				+ "t_commodity_info.SellerPriceTwo,t_commodity_info.SellerPriceThree,t_supplier.`Name` Supplier,"
				+ "t_commodity_info.ProductCategory from t_commodity_info "
				+ "LEFT JOIN t_supplier ON t_commodity_info.Supplier=t_supplier.ID order by t_commodity_info.ID "
				+ "desc";
		Object[] parameter = null;
		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 商品数量
	 * 
	 * @return
	 */
	public int getCommodityCounts() {
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_commodity_info ";

		Object[] parameter = new Object[] { "AllCounts" };
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);

		if (ls.size() > 1) {
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		}

		return counts;
	}

	/**
	 * 分页(显示报价时间)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getCommodityInfo(Page page) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select t_commodity_info.Item,t_commodity_info.ItemDescription ,t_commodity_info.ID,t_commodity_info.CommodityName,t_commodity_info.Model,"
				+ "t_commodity_info.Unit,t_commodity_info.DeliveryPeriod,t_commodity_info.ProducingArea,"
				+ "t_commodity_info.CostPrice,t_commodity_info.DiscountCost,"
				+ "t_commodity_info.SellerPriceOne UnitPrice,t_commodity_info.SellerPriceTwo,"
				+ "t_commodity_info.SellerPriceThree,t_supplier.`Name` Supplier,t_commodity_info.ProductCategory ,"
				+ "t_commodity_info.QuoteTime from t_commodity_info LEFT JOIN t_supplier ON t_commodity_info.Supplier=t_supplier.ID"
				+ " order by t_commodity_info.ID desc limit ?,?";
		Object[] parameter = new Object[] { (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 添加
	 * 
	 * @param info
	 * @return
	 */
	public boolean insertCommodityInfo(CommodityInfo info) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into t_commodity_info  (CommodityName,Model,Unit,DeliveryPeriod,ProducingArea,"
				+ "CostPrice,DiscountCost,SellerPriceOne,SellerPriceTwo,SellerPriceThree,Supplier,"
				+ "OperatingTime,ProductCategory,QuoteTime) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		Object[] param = new Object[14];
		param[0] = info.getCommodityName();
		param[1] = info.getModel();
		param[2] = info.getUnit();
		param[3] = info.getDeliveryPeriod();
		param[4] = info.getProducingArea();
		param[5] = info.getCostPrice();
		param[6] = info.getDiscountCost();
		param[7] = info.getSellerPriceOne();
		param[8] = info.getSellerPriceTwo();
		param[9] = info.getSellerPriceThree();
		param[10] = info.getSupplier();
		param[12] = info.getProductCategory();
		param[11] = df.format(new Date());
		param[13] = info.getQuoteTime();
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean updateCommodityInfo(CommodityInfo info) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_commodity_info  set CommodityName=?,Model=?,Unit=?,DeliveryPeriod=?,ProducingArea=?,"
				+ "CostPrice=?,DiscountCost=?,SellerPriceOne=?,SellerPriceTwo=?,SellerPriceThree=?,Supplier=?,"
				+ "OperatingTime=?,ProductCategory=?,QuoteTime=?,Item=?,ItemDescription=? where ID=? ";
		Object[] param = new Object[17];
		param[0] = info.getCommodityName();
		param[1] = info.getModel();
		param[2] = info.getUnit();
		param[3] = info.getDeliveryPeriod();
		param[4] = info.getProducingArea();
		param[5] = info.getCostPrice();
		param[6] = info.getDiscountCost();
		param[7] = info.getSellerPriceOne();
		param[8] = info.getSellerPriceTwo();
		param[9] = info.getSellerPriceThree();
		param[10] = info.getSupplier();
		param[11] = df.format(new Date());
		param[12] = info.getProductCategory();
		param[13] = info.getQuoteTime();
		param[14] = info.getItem();
		param[15] = info.getItemDescription();
		param[16] = info.getID();
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据型号
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getCommodityInfoByModel(String model) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		model = "%" + model + "%";
//		String sql = "select ID,CommodityName,Model,Unit,DeliveryPeriod,ProducingArea,CostPrice,DiscountCost,SellerPriceOne UnitPrice,SellerPriceTwo,SellerPriceThree,Supplier from t_commodity_info where Model like ?";
		
		String sql = "select t_commodity_info.ID,t_commodity_info.CommodityName,t_commodity_info.Model,"
				+ "t_commodity_info.Unit,t_commodity_info.DeliveryPeriod,t_commodity_info.ProducingArea,t_commodity_info.QuoteTime,"
				+ "t_commodity_info.CostPrice,t_commodity_info.DiscountCost,"
				+ "t_commodity_info.SellerPriceOne UnitPrice,t_commodity_info.SellerPriceTwo,"
				+ "t_commodity_info.SellerPriceThree,t_supplier.`Name` Supplier"
				+ " from t_commodity_info LEFT JOIN t_supplier ON t_commodity_info.Supplier=t_supplier.ID"
				+ " where t_commodity_info.Model like ?";
		Object[] parameter = new Object[] { model };
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	public List<Map<String, Object>> getCommodityInfoByTitle(String name){
		DBUtil db = new DBUtil();
		name = "%" + name + "%";
//		
		
		String sql = "select t_commodity_info.ID,t_commodity_info.CommodityName,t_commodity_info.Model,"
				+ "t_commodity_info.Unit,t_commodity_info.DeliveryPeriod,t_commodity_info.ProducingArea,t_commodity_info.QuoteTime,"
				+ "t_commodity_info.CostPrice,t_commodity_info.DiscountCost,"
				+ "t_commodity_info.SellerPriceOne UnitPrice,t_commodity_info.SellerPriceTwo,"
				+ "t_commodity_info.SellerPriceThree,t_supplier.`Name` Supplier"
				+ " from t_commodity_info LEFT JOIN t_supplier ON t_commodity_info.Supplier=t_supplier.ID"
				+ " where t_commodity_info.CommodityName like ?";
		Object[] parameter = new Object[] { name };
		return db.QueryToList(sql, parameter);
	}
	
	
	public List<Map<String, Object>> getCommodityModel(String model) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		model = "%" + model + "%";
//		String sql = "select ID,CommodityName,Model,Unit,DeliveryPeriod,ProducingArea,CostPrice,DiscountCost,SellerPriceOne UnitPrice,SellerPriceTwo,SellerPriceThree,Supplier from t_commodity_info where Model like ?";
		
		String sql = "select ID,Model,SellerPriceOne,CommodityName from t_commodity_info"
		
				+ " where t_commodity_info.Model like ?";
		Object[] parameter = new Object[] { model };
		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 根据ID
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getCommodityInfoByID(int id) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select t_commodity_info.CommodityName Remark,t_commodity_info.Model,"
				+ "t_commodity_info.Unit,t_commodity_info.SellerPriceOne UnitPrice ,"
				+ "t_commodity_temp.Qty from t_commodity_info  LEFT JOIN t_commodity_temp on "
				+ "t_commodity_info.ID=t_commodity_temp.Commodity where t_commodity_info.ID=?";
		Object[] parameter = new Object[] { id };
		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	public List<Map<String, Object>> getCommodityInfoByID(int id, int quoteID) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select t_commodity_info.CommodityName Remark,t_commodity_info.Model,"
				+ "t_commodity_info.Unit,t_commodity_info.SellerPriceOne UnitPrice ,"
				+ "t_commodity_temp.Qty from t_commodity_info  LEFT JOIN t_commodity_temp on "
				+ "t_commodity_info.ID=t_commodity_temp.Commodity where t_commodity_info.ID=? AND t_commodity_temp.QuoteID=?";
		Object[] parameter = new Object[] { id, quoteID };
		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getQuoteSystem(Page page, String param1, String content1, String param2,
			String content2) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
	
		String sql = "select t_quote_system.CascadeCompleteStatus,t_quote_system.DeliveryAdvice,t_quote_system_model.FinalTotal USDTotal,FORMAT((t_quote_system_model.SubTotal*t_quote_system.ExchangeRate*t_quote_system.TaxCategories*1.07*1.03+t_quote_system_model.GiftsTotal+t_quote_system.ShipmentCost*t_quote_system.ExchangeRate),2) RMBTotal,t_quote_system.ID,t_quote_system.CustomerCode,"
				+ "t_quote_system.CustomerCompany,t_quote_system.CustomerName,t_quote_system.CustomerTel,"
				+ "t_quote_system.CustomerMail,t_quote_system.CustomerFax,t_quote_system.LeadTime,"
				+ "t_quote_system.Payment,t_quote_system.StaffName,t_quote_system.Department,"
				+ "t_quote_system.ShipmentCost,t_quote_system.DeliveryWay,	t_quote_system.ExchangeRate,"
				+ "t_quote_system.Valid,t_quote_system.Currency,t_quote_system.TaxCategories,"
				+ "t_quote_system.Versions,t_quote_system.Datesent,t_quote_system.Number,"
				+ "t_quote_system.StaffTel,t_quote_system.StaffMail,t_quote_system.MailStatus,"
				+ "t_quote_system.CascadeStatus,t_quote_system.OtherStatus,t_quote_system.OtherRMBStatus ,"
				+ "t_commodity_info.Model from t_quote_system LEFT JOIN (SELECT MIN(t_commodity_temp.ID),"
				+ "t_commodity_temp.QuoteID,t_commodity_temp.Commodity FROM "
				+ "t_commodity_temp GROUP BY"
				+ " t_commodity_temp.QuoteID)a ON a.QuoteID = t_quote_system.ID LEFT JOIN t_commodity_info "
				+ "ON a.Commodity=t_commodity_info.ID LEFT JOIN t_quote_system_model ON "
				+ "t_quote_system_model.QuoteID=t_quote_system.ID WHERE t_quote_system_model.Type='CompleteUSD' ";
		Object[] parameter = null;
		if (param1 == null && param2 == null) {
			sql += " order by Datesent desc, CountNO desc limit ?,?";
			parameter = new Object[] { (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		}
		if (param1 != null && param2 == null) {
			sql += " and ";
			String temp1 = "t_quote_system.";
			if (param1.equals("Model")) {
				temp1 = "t_commodity_info.";
			}
	
			if (param1.equals("Datesent")) {
				param1 = param1 + " =?";
			
			} else {
				param1 = param1 + " like ?";
			}
			sql += param1 + "  order by Datesent desc, CountNO desc limit ?,?";
			parameter = new Object[] { content1, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		}
		if (param1 != null && param2 != null) {
			sql += " and ";
			String temp1 = "t_quote_system.";
			if (param1.equals("Model")) {
				temp1 = "t_commodity_info.";
			}
			String temp2 = "t_quote_system.";
			if (param2.equals("Model")) {
				temp2 = "t_commodity_info.";
			}
			if (param1.equals("Datesent")) {
				param1 = param1 + " =?";
				
			} else {
				param1 = param1 + " like ?";
			}
			if (param2.equals("Datesent")) {
				param2 = param2 + " =?";
			} else {
				param2 = param2 + " like ?";
			}
			sql += temp1 + param1 + " and " + temp2 + param2 + " order by Datesent desc, CountNO desc limit ?,?";
			parameter = new Object[] { content1, content2, (page.getCurrentPage() - 1) * page.getRows(),
					page.getRows() };
		}
		System.out.println(sql);
		long time1 = System.currentTimeMillis();
		ls = db.QueryToList(sql, parameter);
		long time2 = System.currentTimeMillis();
		System.out.println("花费时间为:"+(time2-time1));
		return ls;
	}

	/**
	 * 总数量
	 * 
	 * @return
	 */
	public int getAllCounts(String param1, String content1, String param2, String content2) {
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID)  from t_quote_system ";
		Object[] parameter = null;
		String table = "t_quote_system.";
		if (param1 != null && param2 == null) {
			sql = "select count(t_quote_system.ID) from t_quote_system LEFT JOIN "
					+ "(SELECT MIN(t_commodity_temp.ID),t_commodity_temp.QuoteID,t_commodity_temp.Commodity "
					+ "FROM  t_commodity_temp  GROUP BY t_commodity_temp.QuoteID)a "
					+ "ON a.QuoteID = t_quote_system.ID LEFT JOIN t_commodity_info ON a.Commodity=t_commodity_info.ID where ";

			if (param1.equals("Model")) {
				table = "t_commodity_info.";
			}
			sql += table;
			if (param1.equals("Datesent")) {
				sql += param1 + " =? ";
			} else {
				sql += param1 + " like ? ";
			}

			parameter = new Object[] { content1 };
		}
		if (param2 != null) {
			sql = "select count(t_quote_system.ID) from t_quote_system LEFT JOIN "
					+ "(SELECT MIN(t_commodity_temp.ID),t_commodity_temp.QuoteID,t_commodity_temp.Commodity "
					+ "FROM t_quote_system LEFT JOIN t_commodity_temp ON "
					+ "t_quote_system.ID=t_commodity_temp.QuoteID GROUP BY t_commodity_temp.QuoteID)a "
					+ "ON a.QuoteID = t_quote_system.ID LEFT JOIN t_commodity_info ON "
					+ "a.Commodity=t_commodity_info.ID where ";
			if (param1.equals("Model")) {
				table = "t_commodity_info.";
			}
			String table2 = "t_quote_system.";
			if (param2.equals("Model")) {
				table2 = "t_commodity_info.";
			}
			if (param1.equals("Model")) {
				table = "t_commodity_info.";
			}
			if (param1.equals("Datesent")) {
				param1 = param1 + " =?";
			} else {
				param1 = param1 + " like ?";
			}
			if (param2.equals("Datesent")) {
				param2 = param2 + " =?";
			} else {
				param2 = param2 + " like ?";
			}
			sql += table + param1 + " and " + table2 + param2;
			parameter = new Object[] { content1, content2 };
		}
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);

		if (ls.size() > 1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());

		return counts;
	}

	/**
	 * 添加
	 * 
	 * @param invoice
	 * @param db
	 * @return
	 */
	public boolean insert(QuoteSystem quote) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[30];
		String sql = "insert into t_quote_system (CustomerCode,CustomerCompany,CustomerName,CustomerTel,"
				+ "CustomerFax,LeadTime,Payment,StaffName,Department,ShipmentCost,DeliveryWay,"
				+ "ExchangeRate,Valid,Currency,TaxCategories,Versions,Datesent,Number,StaffTel,StaffMail,CustomerMail,OperatingTime,CountNO,MailStatus,"
				+ "CascadeStatus,OtherStatus,OtherRMBStatus,ModifyTime,CascadeCompleteStatus,DeliveryAdvice) "
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		parameter[0] = quote.getCustomerCode();
		parameter[1] = quote.getCustomerCompany();
		parameter[2] = quote.getCustomerName();
		parameter[3] = quote.getCustomerTel();
		parameter[4] = quote.getCustomerFax();
		parameter[5] = quote.getLeadTime();
		parameter[6] = quote.getPayment();
		parameter[7] = quote.getStaffName();
		parameter[8] = quote.getDepartment();
		parameter[9] = quote.getShipmentCost();
		parameter[10] = quote.getDeliveryWay();
		parameter[11] = quote.getExchangeRate();
		parameter[12] = quote.getValid();
		parameter[13] = quote.getCurrency();
		parameter[14] = quote.getTaxCategories();
		parameter[15] = quote.getVersions();
		parameter[16] = quote.getDatesent();
		parameter[17] = quote.getNumber();
		parameter[18] = quote.getStaffTel();
		parameter[19] = quote.getStaffMail();
		parameter[20] = quote.getCustomerMail();
		parameter[21] = df.format(new Date());
		parameter[22] = quote.getCountNO();
		parameter[23] = "no";
		parameter[24] = "no";
		parameter[25] = "no";
		parameter[26] = "no";
		parameter[27] = df.format(new Date());
		parameter[28] = "no";
		parameter[29] = "no";
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean updateMailStatus(QuoteSystem quote) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[2];
		String sql = "update t_quote_system set MailStatus=? where ID=?";

		parameter[0] = quote.getMailStatus();
		parameter[1] = quote.getID();
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean updateCascadeStatus(QuoteSystem quote) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[2];
		String sql = "update t_quote_system set CascadeStatus=? where ID=?";

		parameter[0] = quote.getCascadeStatus();
		parameter[1] = quote.getID();
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean updateCascadeCompleteStatus(QuoteSystem quote) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[2];
		String sql = "update t_quote_system set CascadeCompleteStatus=? where ID=?";

		parameter[0] = quote.getCascadeCompleteStatus();
		parameter[1] = quote.getID();
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean updateOtherStatus(QuoteSystem quote) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[2];
		String sql = "update t_quote_system set OtherStatus=? where ID=?";

		parameter[0] = quote.getOtherStatus();
		parameter[1] = quote.getID();
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean updateOtherRMBStatus(QuoteSystem quote) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[2];
		String sql = "update t_quote_system set OtherRMBStatus=? where ID=?";

		parameter[0] = quote.getOtherRMBStatus();
		parameter[1] = quote.getID();
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 修改
	 * 
	 * @param invoice
	 * @param db
	 * @return
	 */
	public boolean update(QuoteSystem quote) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[23];
		String sql = "update t_quote_system set CustomerCode=?,CustomerCompany=?,CustomerName=?,CustomerTel=?,"
				+ "CustomerFax=?,LeadTime=?,Payment=?,StaffName=?,Department=?,ShipmentCost=?,DeliveryWay=?,"
				+ "ExchangeRate=?,Valid=?,Currency=?,TaxCategories=?,Versions=?,Datesent=?,Number=? "
				+ ",StaffTel=?,StaffMail=?,CustomerMail=?,ModifyTime=? where ID=?";

		parameter[0] = quote.getCustomerCode();
		parameter[1] = quote.getCustomerCompany();
		parameter[2] = quote.getCustomerName();
		parameter[3] = quote.getCustomerTel();
		parameter[4] = quote.getCustomerFax();
		parameter[5] = quote.getLeadTime();
		parameter[6] = quote.getPayment();
		parameter[7] = quote.getStaffName();
		parameter[8] = quote.getDepartment();
		parameter[9] = quote.getShipmentCost();
		parameter[10] = quote.getDeliveryWay();
		parameter[11] = quote.getExchangeRate();
		parameter[12] = quote.getValid();
		parameter[13] = quote.getCurrency();
		parameter[14] = quote.getTaxCategories();
		parameter[15] = quote.getVersions();
		parameter[16] = quote.getDatesent();
		parameter[17] = quote.getNumber();
		parameter[18] = quote.getStaffTel();
		parameter[19] = quote.getStaffMail();
		parameter[20] = quote.getCustomerMail();
		parameter[21] = df.format(new Date());
		parameter[22] = quote.getID();
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 今天录入的第几条报价单
	 * 
	 * @return
	 */
	public int getTodayCount() {
		int count = 0;
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String start = df.format(new Date()) + " 00-00-00";
		String end = df.format(new Date()) + " 23-59-59";
		Object[] param = new Object[] { start, end };
		String sql = "select count(ID) from t_quote_system where OperatingTime>=? and OperatingTime<=?";
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		if (ls.size() >= 1) {
			count = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString()) + 1;
		}
		return count;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public boolean delete(int id) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] param = new Object[] { id };
		String sql = "delete from t_quote_system where ID=?";
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 搜索
	 * 
	 * @param sql
	 * @param parameter
	 * @return
	 */
	public List<Map<String, Object>> getQueryList(String sql, Object[] parameter) {
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		return ls;

	}

	/**
	 * 临时添加
	 * 
	 * @param invoice
	 * @param db
	 * @return
	 */
	public boolean insertCommodity(CommodityTemp quote) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[8];
		String sql = "insert into t_commodity_temp (Commodity,Description,PaymentDate,OrderNO,QuoteID,Qty,OperatingTime,Remarks) "
				+ "values (?,?,?,?,?,?,?,?)";
		parameter[0] = quote.getCommodity();
		parameter[1] = quote.getDescription();
		parameter[2] = quote.getPaymentDate();
		parameter[3] = quote.getOrderNO();
		parameter[4] = quote.getQuoteID();
		parameter[5] = quote.getQty();
		parameter[6] = df.format(new Date());
		parameter[7] = "框架内物料";
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 临时修改
	 * 
	 * @param invoice
	 * @param db
	 * @return
	 */
	public boolean updateCommodity(CommodityTemp quote) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[7];
		String sql = "update t_commodity_temp set Commodity=?,Description=?,PaymentDate=?,OrderNO=?,OperatingTime=?,Qty=? where ID=?";

		parameter[0] = quote.getCommodity();
		parameter[1] = quote.getDescription();
		parameter[2] = quote.getPaymentDate();
		parameter[3] = quote.getOrderNO();
		parameter[4] = df.format(new Date());
		parameter[5] = quote.getQty();
		parameter[6] = quote.getID();
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据报价单号查询ID
	 * 
	 * @param Number
	 * @return
	 */
	public int getQuoteSystemID(String number) {
		int id = 0;
		DBUtil db = new DBUtil();
		Object[] param = new Object[] { number };
		String sql = "select ID from t_quote_system where CountNO=?";
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		if (ls.size() > 1) {
			id = Integer.parseInt(ls.get(1).get("ID").toString());
		}
		return id;
	}

	/**
	 * 查詢模板中的商品資料
	 * 
	 * @param id
	 * @return
	 */

	public List<Map<String, Object>> getQuoteTemp(int id) {

		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();

		String sql = "select t_commodity_info.CommodityName Description,t_commodity_info.Model,t_commodity_info.SellerPriceOne UnitPrice,"
				+ "t_commodity_temp.ID CommodityID, t_commodity_temp.Commodity, t_commodity_temp.Qty,t_commodity_temp.UnitPriceUSD,t_commodity_temp.ExtendedUSD,"
				+ "t_commodity_temp.UnitPriceRMB,t_commodity_temp.ExtendedRMB,t_commodity_info.CostPrice,t_commodity_info.DeliveryPeriod,t_commodity_info.DiscountCost,"
				+ "t_commodity_info.ProducingArea,t_commodity_info.Unit,t_commodity_info.Supplier "
				+ " from t_commodity_temp  left join t_commodity_info on "
				+ " t_commodity_temp.Commodity=t_commodity_info.ID where t_commodity_temp.QuoteID=?";
		Object[] parameter = new Object[] { id };
		ls = db.QueryToList(sql, parameter);

		return ls;
	}

	

	/**
	 * 添加合同模板基本信息
	 * 
	 * @param rmb
	 * @return
	 */
	public boolean insertRMBContract(QuoteContractRMB rmb) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into t_quote_contracts_rmb (ContractNO,SignDate,CustomerCompany,CustomerTel,"
				+ "CustomerFax,CustomerContact,SecondContact,TotalPrice,Payment,LeadTime,DeliveryPoint,"
				+ "QuoteID,OperatingTime) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[13];
		param[0] = rmb.getContractNO();
		param[1] = rmb.getSignDate();
		param[2] = rmb.getCustomerCompany();
		param[3] = rmb.getCustomerTel();
		param[4] = rmb.getCustomerFax();
		param[5] = rmb.getCustomerContact();
		param[6] = rmb.getSecondContact();
		param[7] = rmb.getTotalPrice();
		param[8] = rmb.getPayment();
		param[9] = rmb.getLeadTime();
		param[10] = rmb.getDeliveryPoint();
		param[11] = rmb.getQuoteID();
		param[12] = df.format(new Date());
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 修改合同模板基本信息
	 * 
	 * @param rmb
	 * @return
	 */
	public boolean updateRMBContract(QuoteContractRMB rmb) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_quote_contracts_rmb set ContractNO=?,SignDate=?,CustomerCompany=?,CustomerTel=?,"
				+ "CustomerFax=?,CustomerContact=?,SecondContact=?,TotalPrice=?,Payment=?,LeadTime=?,DeliveryPoint=?,"
				+ "OperatingTime=? where ID=?";
		Object[] param = new Object[13];
		param[0] = rmb.getContractNO();
		param[1] = rmb.getSignDate();
		param[2] = rmb.getCustomerCompany();
		param[3] = rmb.getCustomerTel();
		param[4] = rmb.getCustomerFax();
		param[5] = rmb.getCustomerContact();
		param[6] = rmb.getSecondContact();
		param[7] = rmb.getTotalPrice();
		param[8] = rmb.getPayment();
		param[9] = rmb.getLeadTime();
		param[10] = rmb.getDeliveryPoint();
		param[11] = df.format(new Date());
		param[12] = rmb.getID();
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据报价ID查看合同基本信息
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getRMBContract(int id) {
		DBUtil db = new DBUtil();
		String sql = "select t_quote_contracts_rmb.ID,t_quote_contracts_rmb.ContractNO,t_quote_contracts_rmb.SignDate,t_quote_contracts_rmb.CustomerCompany,"
				+ "t_quote_contracts_rmb.CustomerTel,t_quote_contracts_rmb.CustomerFax,"
				+ "t_quote_contracts_rmb.CustomerContact,t_quote_contracts_rmb.SecondContact,"
				+ "t_quote_contracts_rmb.TotalPrice,t_quote_contracts_rmb.Payment,t_quote_contracts_rmb.LeadTime,t_quote_contracts_rmb.DeliveryPoint "
				+ "from t_quote_contracts_rmb left join t_quote_system on t_quote_system.ID=t_quote_contracts_rmb.QuoteID "
				+ "where t_quote_contracts_rmb.QuoteID=?";
		Object[] param = new Object[] { id };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);

		return ls;
	}

	/**
	 * 添加美金合同模板基本信息
	 * 
	 * @param rmb
	 * @return
	 */
	public boolean insertUSDContract(QuoteContractUSD usd) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into t_quote_contracts_usd  (ContractNO,Date,CustomerCompany,CustomerTel,"
				+ "CustomerFax,CustomerContact,TotalPrice,Payment,DeliveryPoint,Shipment,Destination,"
				+ "ShipmentTime,ShippingMark,AirPort,Manufacturer,CustomerAdd,Version,QuoteID,OperatingTime,PayTime)"
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[20];
		param[0] = usd.getContractNO();
		param[1] = usd.getDate();
		param[2] = usd.getCustomerCompany();
		param[3] = usd.getCustomerTel();
		param[4] = usd.getCustomerFax();
		param[5] = usd.getCustomerContact();
		param[6] = usd.getTotalPrice();
		param[7] = usd.getPayment();
		param[8] = usd.getDeliveryPoint();
		param[9] = usd.getShipment();
		param[10] = usd.getDestination();
		param[11] = usd.getShipmentTime();
		param[12] = usd.getShippingMark();
		param[13] = usd.getAirPort();
		param[14] = usd.getManufacturer();
		param[15] = usd.getCustomerAdd();
		param[16] = usd.getVersion();
		param[17] = usd.getQuoteID();
		param[18] = usd.getPayTime();
		param[19] = df.format(new Date());

		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 修改美金合同模板基本信息
	 * 
	 * @param rmb
	 * @return
	 */
	public boolean updateUSDContract(QuoteContractUSD usd) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_quote_contracts_usd  set ContractNO=?,Date=?,CustomerCompany=?,CustomerTel=?,"
				+ "CustomerFax=?,CustomerContact=?,TotalPrice=?,Payment=?,DeliveryPoint=?,Shipment=?,Destination=?,"
				+ "ShipmentTime=?,ShippingMark=?,AirPort=?,Manufacturer=?,CustomerAdd=?,Version=?,OperatingTime=?,PayTime=?"
				+ " where ID=?";
		Object[] param = new Object[20];
		param[0] = usd.getContractNO();
		param[1] = usd.getDate();
		param[2] = usd.getCustomerCompany();
		param[3] = usd.getCustomerTel();
		param[4] = usd.getCustomerFax();
		param[5] = usd.getCustomerContact();
		param[6] = usd.getTotalPrice();
		param[7] = usd.getPayment();
		param[8] = usd.getDeliveryPoint();
		param[9] = usd.getShipment();
		param[10] = usd.getDestination();
		param[11] = usd.getShipmentTime();
		param[12] = usd.getShippingMark();
		param[13] = usd.getAirPort();
		param[14] = usd.getManufacturer();
		param[15] = usd.getCustomerAdd();
		param[16] = usd.getVersion();
		param[17] = df.format(new Date());
		param[18] = usd.getPayTime();
		param[19] = usd.getID();
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据报价ID查看美金合同基本信息
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getUSDContract(int id) {
		DBUtil db = new DBUtil();
		String sql = "select t_quote_contracts_usd.ID,t_quote_contracts_usd.PayTime,t_quote_contracts_usd.ContractNO,t_quote_contracts_usd.Date,t_quote_contracts_usd.CustomerCompany,"
				+ "t_quote_contracts_usd.CustomerTel,t_quote_contracts_usd.CustomerFax,t_quote_contracts_usd.CustomerContact,"
				+ "t_quote_contracts_usd.TotalPrice,t_quote_contracts_usd.Payment,t_quote_contracts_usd.DeliveryPoint,t_quote_contracts_usd.Shipment,"
				+ "t_quote_contracts_usd.Destination,t_quote_contracts_usd.ShipmentTime,t_quote_contracts_usd.ShippingMark,"
				+ "t_quote_contracts_usd.AirPort,t_quote_contracts_usd.Manufacturer,t_quote_contracts_usd.CustomerAdd,t_quote_contracts_usd.Version "
				+ "from t_quote_contracts_usd left join t_quote_system on t_quote_system.ID=t_quote_contracts_usd.QuoteID "
				+ "where t_quote_contracts_usd.QuoteID=?";
		Object[] param = new Object[] { id };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);

		return ls;
	}

	/**
	 * 添加合同模板的商品信息
	 * 
	 * @param comm
	 * @return
	 */
	public boolean insertContractCommodity(CommodityContracts comm) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into t_commodity_contracts (QuoteID,CommodityModel,Remark,Unit,Quantity,UnitPrice,OperatingTime) values (?,?,?,?,?,?,?)";
		Object[] param = new Object[7];
		param[0] = comm.getQuoteID();
		param[1] = comm.getCommodityModel();
		param[2] = comm.getRemark();
		param[3] = comm.getUnit();
		param[4] = comm.getQuantity();
		param[5] = comm.getUnitPrice();
		param[6] = df.format(new Date());
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 修改合同模板的商品信息
	 * 
	 * @param comm
	 * @return
	 */
	public boolean updateContractCommodity(CommodityContracts comm) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_commodity_contracts set CommodityModel=?,Remark=?,Unit=?,Quantity=?,UnitPrice=?,OperatingTime=? where ID=?";
		Object[] param = new Object[7];
		param[0] = comm.getCommodityModel();
		param[1] = comm.getRemark();
		param[2] = comm.getUnit();
		param[3] = comm.getQuantity();
		param[4] = comm.getUnitPrice();
		param[5] = df.format(new Date());
		param[6] = comm.getID();
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean updateContractCommodity2(CommodityContracts comm) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_commodity_contracts set CommodityModel=?,Remark=?,Unit=?,Quantity=?,UnitPrice=?,OperatingTime=? where QuoteID=? and CommodityModel=?";
		Object[] param = new Object[8];
		param[0] = comm.getCommodityModel();
		param[1] = comm.getRemark();
		param[2] = comm.getUnit();
		param[3] = comm.getQuantity();
		param[4] = comm.getUnitPrice();
		param[5] = df.format(new Date());
		param[6] = comm.getQuoteID();
		param[7] = comm.getCommodityModel();
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据报价单ID查看当前模板的商品信息
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getContractCommodity(int id) {
		DBUtil db = new DBUtil();
		String sql = "SELECT ID,QuoteID,CommodityModel,Remark,Unit,Quantity,UnitPrice,OperatingTime from t_commodity_contracts where QuoteID=? order by ID";
		Object[] param = new Object[] { id };

		List<Map<String, Object>> ls = db.QueryToList(sql, param);

		return ls;
	}

	/**
	 * 根据ID删除当前模板的商品信息
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteContractCommodity(int id) {
		DBUtil db = new DBUtil();
		String sql = "delete from t_commodity_contracts where ID=?";
		Object[] param = new Object[] { id };
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}
	
	public boolean deleteContractCommodity(int quoteID,String model) {
		DBUtil db = new DBUtil();
		String sql = "delete from t_commodity_contracts where QuoteID=? and CommodityModel=?";
		Object[] param = new Object[] {quoteID,model };
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 添加合同模板的商品信息
	 * 
	 * @param comm
	 * @return
	 */
	public boolean insertUSDContractCommodity(CommodityUSDContracts comm) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into t_commodity_contracts_usd (QuoteID,CommodityModel,Remark,Unit,Quantity,UnitPrice,OperatingTime) values (?,?,?,?,?,?,?)";
		Object[] param = new Object[7];
		param[0] = comm.getQuoteID();
		param[1] = comm.getCommodityModel();
		param[2] = comm.getRemark();
		param[3] = comm.getUnit();
		param[4] = comm.getQuantity();
		param[5] = comm.getUnitPrice();
		param[6] = df.format(new Date());
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 修改合同模板的商品信息
	 * 
	 * @param comm
	 * @return
	 */
	public boolean updateUSDContractCommodity(CommodityUSDContracts comm) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_commodity_contracts_usd set CommodityModel=?,Remark=?,Unit=?,Quantity=?,UnitPrice=?,OperatingTime=? where ID=?";
		Object[] param = new Object[7];
		param[0] = comm.getCommodityModel();
		param[1] = comm.getRemark();
		param[2] = comm.getUnit();
		param[3] = comm.getQuantity();
		param[4] = comm.getUnitPrice();
		param[5] = df.format(new Date());
		param[6] = comm.getID();
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean updateUSDContractCommodity2(CommodityUSDContracts comm) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_commodity_contracts_usd set CommodityModel=?,Remark=?,Unit=?,Quantity=?,UnitPrice=?,OperatingTime=? where QuoteID=? and CommodityModel=?";
		Object[] param = new Object[8];
		param[0] = comm.getCommodityModel();
		param[1] = comm.getRemark();
		param[2] = comm.getUnit();
		param[3] = comm.getQuantity();
		param[4] = comm.getUnitPrice();
		param[5] = df.format(new Date());
		param[6] = comm.getQuoteID();
		param[7] = comm.getCommodityModel();
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据报价单ID查看当前模板的商品信息
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getUSDContractCommodity(int id) {
		DBUtil db = new DBUtil();
//		String sql = "SELECT t_commodity_contracts_usd.ID,t_commodity_contracts_usd.CommodityModel,t_commodity_contracts_usd.Remark,"
//				+ "t_commodity_contracts_usd.Unit,t_commodity_contracts_usd.Quantity,"
//				+ "t_commodity_contracts_usd.UnitPrice,t_commodity_info.CommodityName from t_commodity_contracts_usd "
//				+ " LEFT JOIN t_commodity_info ON t_commodity_info.Model=t_commodity_contracts_usd.CommodityModel where QuoteID=? order by ID";
		String sql = "SELECT * FROM t_commodity_contracts_usd WHERE QuoteID=? order by ID";
		Object[] param = new Object[] { id };

		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	/**
	 * 根据ID删除当前模板的商品信息
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteUSDContractCommodity(int id) {
		DBUtil db = new DBUtil();
		String sql = "delete from t_commodity_contracts_usd where ID=?";
		Object[] param = new Object[] { id };
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}
	
	public boolean deleteUSDContractCommodity(int quoteID,String model) {
		DBUtil db = new DBUtil();
		String sql = "delete from t_commodity_contracts_usd where QuoteID=? and CommodityModel=?";
		Object[] param = new Object[] { quoteID,model };
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean insertDelivery(QuoteDelivery delivery) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean flag = false;
		String sql = "insert into t_quote_delivery (Model,Description,Quantity,Remarks,OperatingTime,QuoteID) "
				+ "values (?,?,?,?,?,?)";
		Object[] param = new Object[6];
		param[0] = delivery.getModel();
		param[1] = delivery.getDescription();
		param[2] = delivery.getQuantity();
		param[3] = delivery.getRemarks();
		param[4] = df.format(new Date());
		param[5] = delivery.getQuoteID();
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean upadteDelivery(QuoteDelivery delivery) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean flag = false;
		String sql = "update t_quote_delivery set Model=?,Description=?,Quantity=?,Remarks=?,OperatingTime=?,QuoteID=? where ID=? ";
		Object[] param = new Object[7];
		param[0] = delivery.getModel();
		param[1] = delivery.getDescription();
		param[2] = delivery.getQuantity();
		param[3] = delivery.getRemarks();
		param[4] = df.format(new Date());
		param[5] = delivery.getQuoteID();
		param[6] = delivery.getID();
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean upadteDelivery2(QuoteDelivery delivery) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean flag = false;
		String sql = "update t_quote_delivery set Model=?,Description=?,Quantity=?,Remarks=?,OperatingTime=?,QuoteID=? where QuoteID=? and Model=?";
		Object[] param = new Object[8];
		param[0] = delivery.getModel();
		param[1] = delivery.getDescription();
		param[2] = delivery.getQuantity();
		param[3] = delivery.getRemarks();
		param[4] = df.format(new Date());
		param[5] = delivery.getQuoteID();
		param[6] = delivery.getQuoteID();
		param[7] = delivery.getModel();
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据报价单ID查看当前模板的商品信息
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getDelivery(int id) {
		DBUtil db = new DBUtil();
		String sql = "SELECT ID,QuoteID,Model,Description,Quantity,Remarks,OperatingTime from t_quote_delivery where QuoteID=?";
		Object[] param = new Object[] { id };

		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	/**
	 * 备货清单中没有生成PO的
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getDeliveryNotInPO(int id) {
		DBUtil db = new DBUtil();
		String sql = "SELECT ID,QuoteID,Model,Description,Quantity,Remarks,OperatingTime "
				+ "from t_quote_delivery where QuoteID=? and Model Not IN "
				+ "(select Model from t_commodity_info where t_commodity_info.ID IN "
				+ "(select PartID ID from (select PartID,QuoteID from t_quote_cascade_temp UNION SELECT"
				+ " PartID,QuoteID from t_quote_other_temp)a where a.QuoteID=?) )";
		Object[] param = new Object[] { id, id };

		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	/**
	 * 备货清单中生成PO的
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getDeliveryInPO(int id) {
		DBUtil db = new DBUtil();
		String sql = "SELECT ID,QuoteID,Model,Description,Quantity,Remarks,OperatingTime "
				+ "from t_quote_delivery where QuoteID=? and Model IN "
				+ "(select Model from t_commodity_info where t_commodity_info.ID IN "
				+ "(select PartID ID from (select PartID,QuoteID from t_quote_cascade_temp UNION SELECT"
				+ " PartID,QuoteID from t_quote_other_temp)a where a.QuoteID=?) )";
		Object[] param = new Object[] { id, id };

		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	/**
	 * 根据ID删除当前模板的商品信息
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteDelivery(int id) {
		DBUtil db = new DBUtil();
		String sql = "delete from t_quote_delivery where ID=?";
		Object[] param = new Object[] { id };
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}
	
	public boolean deleteDelivery(int quoteID,String model) {
		DBUtil db = new DBUtil();
		String sql = "delete from t_quote_delivery where QuoteID=? and Model=?";
		Object[] param = new Object[] { quoteID,model };
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean insertQuoteRequest(QuoteRequest req) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean flag = false;
		String sql = "insert into t_quote_request (Fumigation,Size,Weight,ProductImg,NamePlateImg,OriginInfo,ProductName,"
				+ "PackingQty,ShippingMark,Departure,Destination,Receiving,SplitShipment,Airelift,Truck,"
				+ "FastMail,TailCar,Unloading,QuoteID,OperatingTime,ContractNO,PO,SO,Name) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		Object[] param = new Object[24];
		param[0] = req.getFumigation();
		param[1] = req.getSize();
		param[2] = req.getWeight();
		param[3] = req.getProductImg();
		param[4] = req.getNamePlateImg();
		param[5] = req.getOriginInfo();
		param[6] = req.getProductName();
		param[7] = req.getPackingQty();
		param[8] = req.getShippingMark();
		param[9] = req.getDeparture();
		param[10] = req.getDestination();
		param[11] = req.getReceiving();
		param[12] = req.getSplitShipment();
		param[13] = req.getAirelift();
		param[14] = req.getTruck();
		param[15] = req.getFastMail();
		param[16] = req.getTailCar();
		param[17] = req.getUnloading();
		param[18] = req.getQuoteID();
		param[19] = df.format(new Date());
		param[20] = req.getContractNO();
		param[21] = req.getPO();
		param[22] = req.getSO();
		param[23] = req.getName();
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean upadteQuoteRequest(QuoteRequest req) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean flag = false;
		String sql = "update t_quote_request set Fumigation=?,Size=?,Weight=?,ProductImg=?,NamePlateImg=?,OriginInfo=?,ProductName=?,"
				+ "PackingQty=?,ShippingMark=?,Departure=?,Destination=?,Receiving=?,SplitShipment=?,Airelift=?,Truck=?,"
				+ "FastMail=?,TailCar=?,Unloading=?,QuoteID=?,OperatingTime=?,ContractNO=?,PO=?,SO=?,Name=?  where ID=?";
		Object[] param = new Object[25];
		param[0] = req.getFumigation();
		param[1] = req.getSize();
		param[2] = req.getWeight();
		param[3] = req.getProductImg();
		param[4] = req.getNamePlateImg();
		param[5] = req.getOriginInfo();
		param[6] = req.getProductName();
		param[7] = req.getPackingQty();
		param[8] = req.getShippingMark();
		param[9] = req.getDeparture();
		param[10] = req.getDestination();
		param[11] = req.getReceiving();
		param[12] = req.getSplitShipment();
		param[13] = req.getAirelift();
		param[14] = req.getTruck();
		param[15] = req.getFastMail();
		param[16] = req.getTailCar();
		param[17] = req.getUnloading();
		param[18] = req.getQuoteID();
		param[19] = df.format(new Date());
		param[20] = req.getContractNO();
		param[21] = req.getPO();
		param[22] = req.getSO();
		param[23] = req.getName();
		param[24] = req.getID();
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据报价单ID查看
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getQuoteRequest(int id) {
		DBUtil db = new DBUtil();
		String sql = "select  t_quote_request.ID,t_quote_request.Fumigation,t_quote_request.Size,"
				+ "t_quote_request.Weight,t_quote_request.ProductImg,t_quote_request.NamePlateImg,"
				+ "t_quote_request.OriginInfo,t_quote_request.ProductName,t_quote_request.PackingQty,"
				+ "t_quote_request.ShippingMark,t_quote_request.Departure,t_quote_request.Destination,"
				+ "t_quote_request.Receiving,t_quote_request.SplitShipment,t_quote_request.Airelift,"
				+ "t_quote_request.Truck,t_quote_request.FastMail,t_quote_request.TailCar,"
				+ "t_quote_request.Unloading,t_quote_request.QuoteID,t_quote_request.ContractNO,"
				+ "t_quote_request.PO,t_quote_request.SO,t_order.Customer Name,"
				+ "t_quote_request.OperatingTime from t_quote_request LEFT JOIN t_order ON "
				+ " t_order.ContractNo=t_quote_request.ContractNO  where QuoteID=?";
		Object[] param = new Object[] { id };

		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	/**
	 * 包装要求 根据报价单ID查看
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getQuoteRequestByPacking(int id) {
		DBUtil db = new DBUtil();
		String sql = "select Fumigation,Size,Weight,ProductImg,NamePlateImg,OriginInfo,ProductName,"
				+ "PackingQty,ShippingMark from t_quote_request where QuoteID=?";
		Object[] param = new Object[] { id };

		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public List<Map<String, Object>> getQuoteRequestPO(int id) {
		DBUtil db = new DBUtil();
		String sql = "select ContractNO,PO,SO from t_quote_request where QuoteID=?";
		Object[] param = new Object[] { id };

		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public List<Map<String, Object>> getQuoteRequestName(int id) {
		DBUtil db = new DBUtil();
//		String sql = "select t_order.Customer Name from t_quote_request LEFT JOIN t_order ON  t_order.ContractNo=t_quote_request.ContractNO  where QuoteID=?";
		String sql = "select t_order.ContractTitle Name from t_quote_request LEFT JOIN t_order ON  t_order.ContractNo=t_quote_request.ContractNO  where QuoteID=?";
		Object[] param = new Object[] { id };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public List<Map<String, Object>> getQuoteRequestContractNO(int id) {
		DBUtil db = new DBUtil();
		String sql = "select ContractNO from t_quote_request where QuoteID=?";
		Object[] param = new Object[] { id };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}
	
	public List<Map<String, Object>> getExpectedDeliveryPeriod(String ContractNo) {
		DBUtil db = new DBUtil();
		String sql = "select ExpectedDeliveryPeriod from t_order where ContractNO=?";
		Object[] param = new Object[] { ContractNo };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	/**
	 * 运输要求 根据报价单ID查看
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getQuoteRequestByTransport(int id) {
		DBUtil db = new DBUtil();
		String sql = "select Departure,Destination,Receiving,SplitShipment,Airelift,Truck,"
				+ "FastMail,TailCar,Unloading from t_quote_request where QuoteID=?";
		Object[] param = new Object[] { id };

		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	/**
	 * 根据ID删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteQuoteRequest(int id) {
		DBUtil db = new DBUtil();
		String sql = "delete from t_quote_request where ID=?";
		Object[] param = new Object[] { id };
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean insertCascadeTemp(QuoteCascadeTemp temp) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "insert into t_quote_cascade_temp (QuoteID,PartID,Type) values (?,?,?) ";
		Object[] param = new Object[3];
		param[0] = temp.getQuoteID();
		param[1] = temp.getPartID();
		param[2] = temp.getType();

		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public static List<Map<String, Object>> getCascadeTemp(int id) {
		DBUtil db = new DBUtil();
		String sql = "select t_quote_cascade_temp.ID,t_commodity_info.Model,"
				+ "t_commodity_info.CostPrice UnitPrice,t_quote_delivery.Quantity,"
				+ "(t_commodity_info.CostPrice*t_quote_delivery.Quantity) ExtendedPrice,"
				+ "t_quote_delivery.Description from t_quote_cascade_temp left join t_commodity_info "
				+ "on t_quote_cascade_temp.PartID=t_commodity_info.ID left join t_quote_delivery "
				+ "on t_quote_delivery.Model=t_commodity_info.Model where t_quote_delivery.QuoteID=? "
				+ "and t_quote_cascade_temp.QuoteID=? and Type='Parts'";
		Object[] param = new Object[] { id, id };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public static List<Map<String, Object>> getCascadeCompleteTemp(int id) {
		DBUtil db = new DBUtil();
		String sql = "select t_quote_cascade_temp.ID,t_commodity_info.Model,"
				+ "t_commodity_info.CostPrice UnitPrice,t_quote_delivery.Quantity,"
				+ "(t_commodity_info.CostPrice*t_quote_delivery.Quantity) ExtendedPrice,"
				+ "t_quote_delivery.Description from t_quote_cascade_temp left join t_commodity_info "
				+ "on t_quote_cascade_temp.PartID=t_commodity_info.ID left join t_quote_delivery "
				+ "on t_quote_delivery.Model=t_commodity_info.Model where t_quote_delivery.QuoteID=? "
				+ "and t_quote_cascade_temp.QuoteID=? and Type='Complete'";
		Object[] param = new Object[] { id, id };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public boolean deleteCascadeTemp(int id) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "delete from t_quote_cascade_temp where ID=? ";
		Object[] param = new Object[] { id };
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}
	
	public boolean deleteCascadeTemp(int quoteID,int commodityID) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "delete from t_quote_cascade_temp where QuoteID=? and PartID=? ";
		Object[] param = new Object[] { quoteID,commodityID };
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean insertOtherTemp(QuoteOtherTemp temp) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "insert into t_quote_other_temp (QuoteID,PartID) values (?,?) ";
		Object[] param = new Object[2];
		param[0] = temp.getQuoteID();
		param[1] = temp.getPartID();
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public static List<Map<String, Object>> getOtherTemp(int id) {
		DBUtil db = new DBUtil();
		String sql = "select t_quote_other_temp.ID,t_commodity_info.Model ,"
				+ "t_commodity_info.CostPrice UnitPrice,t_quote_delivery.Quantity,"
				+ "(t_commodity_info.CostPrice*t_quote_delivery.Quantity) ExtendedPrice,"
				+ "t_quote_delivery.Description from t_quote_other_temp left join t_commodity_info "
				+ "on t_quote_other_temp.PartID=t_commodity_info.ID left join t_quote_delivery "
				+ "on t_quote_delivery.Model=t_commodity_info.Model where t_quote_delivery.QuoteID=? and t_quote_other_temp.QuoteID=?";
		Object[] param = new Object[] { id, id };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public boolean deleteOtherTemp(int id) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "delete from t_quote_other_temp where ID=? ";
		Object[] param = new Object[] { id };
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}
	
	public boolean deleteOtherTemp(int quoteID,int commodityID) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "delete from t_quote_other_temp where QuoteID=? and PartID=?";
		Object[] param = new Object[] { quoteID,commodityID };
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public int getCascadePOID(String number) {//Cascade PO   数据库的操作
		int id = 0;
		DBUtil db = new DBUtil();
		Object[] param = new Object[] { number };
		String sql = "select ID from t_quote_cascade_po where Number=?";
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		if (ls.size() > 1) {
			id = Integer.parseInt(ls.get(1).get("ID").toString());
		}
		return id;
	}

	public boolean insertCascadePOEndUser(QuoteCascadePO po){
		String sql = "insert into t_quote_cascade_po (EndCompany,EndAddr,ContactPerson,EndTel,Type,QuoteID) values (?,?,?,?,?,?)";
		return new DBUtil().executeUpdate(sql, new Object[]{po.getEndCompany(),po.getEndAddr(),po.getContactPerson(),po.getEndTel(),po.getType(),po.getQuoteID()})>0?true:false;
	}
	public boolean updateCascadePOEndUser(QuoteCascadePO po){
		String sql = "update t_quote_cascade_po set EndCompany=?,EndAddr=?,ContactPerson=?,EndTel=? where ID=?";
		return new DBUtil().executeUpdate(sql, new Object[]{po.getEndCompany(),po.getEndAddr(),po.getContactPerson(),po.getEndTel(),po.getID()})>0?true:false;
	}
	public boolean insertCascadePO(QuoteCascadePO po) {
		String sql = "insert into t_quote_cascade_po (Number,Version,ForwarderOne,ForwarderTwo,ForwarderThree,ForwarderFour,"
				+ "ShipCompany,ShipAddr,ShipTel,ShipAttn,EndCompany,EndAddr,ContactPerson,DeliveryTerm,"
				+ "ShippingMark,ContractNO,ShipmentPort,SubTotal,Discounted,FinalTotal,QuoteID,EndTel,RefNO,Type) values "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[24];
		param[0] = po.getNumber();
		param[1] = po.getVersion();
		param[2] = po.getForwarderOne();
		param[3] = po.getForwarderTwo();
		param[4] = po.getForwarderThree();
		param[5] = po.getForwarderFour();
		param[6] = po.getShipCompany();
		param[7] = po.getShipAddr();
		param[8] = po.getShipTel();
		param[9] = po.getShipAttn();
		param[10] = po.getEndCompany();
		param[11] = po.getEndAddr();
		param[12] = po.getContactPerson();
		param[13] = po.getDeliveryTerm();
		param[14] = po.getShippingMark();
		param[15] = po.getContractNO();
		param[16] = po.getShipmentPort();
		param[17] = po.getSubTotal();
		param[18] = po.getDiscounted();
		param[19] = po.getFinalTotal();
		param[20] = po.getQuoteID();
		param[21] = po.getEndTel();
		param[22] = po.getRefNO();
		param[23] = po.getType();

		return new DBUtil().executeUpdate(sql, param)>0?true:false;
	}

	public boolean updateCascadePO(QuoteCascadePO po) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "update t_quote_cascade_po set Number=?,Version=?,ForwarderOne=?,ForwarderTwo=?,ForwarderThree=?,ForwarderFour=?,"
				+ "ShipCompany=?,ShipAddr=?,ShipTel=?,ShipAttn=?,EndCompany=?,EndAddr=?,ContactPerson=?,DeliveryTerm=?,"
				+ "ShippingMark=?,ContractNO=?,ShipmentPort=?,SubTotal=?,Discounted=?,FinalTotal=?,QuoteID=?,EndTel=?,RefNO=? "
				+ " where ID=?";
		Object[] param = new Object[24];
		param[0] = po.getNumber();
		param[1] = po.getVersion();
		param[2] = po.getForwarderOne();
		param[3] = po.getForwarderTwo();
		param[4] = po.getForwarderThree();
		param[5] = po.getForwarderFour();
		param[6] = po.getShipCompany();
		param[7] = po.getShipAddr();
		param[8] = po.getShipTel();
		param[9] = po.getShipAttn();
		param[10] = po.getEndCompany();
		param[11] = po.getEndAddr();
		param[12] = po.getContactPerson();
		param[13] = po.getDeliveryTerm();
		param[14] = po.getShippingMark();
		param[15] = po.getContractNO();
		param[16] = po.getShipmentPort();
		param[17] = po.getSubTotal();
		param[18] = po.getDiscounted();
		param[19] = po.getFinalTotal();
		param[20] = po.getQuoteID();
		param[21] = po.getEndTel();
		param[22] = po.getRefNO();
		param[23] = po.getID();
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public List<Map<String, Object>> getQuoteCascadePO(int id) {
		DBUtil db = new DBUtil();
		String sql = "select ID,Number,Version,ForwarderOne,ForwarderTwo,ForwarderThree,ForwarderFour,RefNO,"
				+ "ShipCompany,ShipAddr,ShipTel,ShipAttn,EndCompany,EndAddr,ContactPerson,DeliveryTerm,"
				+ "ShippingMark,ContractNO,ShipmentPort,SubTotal,concat(Discounted*100,'%') Discounted,FinalTotal,EndTel,QuoteID "
				+ "from t_quote_cascade_po where QuoteID=? and Type='Parts'";
		Object[] param = new Object[] { id };

		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public int getPOID(int quoteID,String type,String temp){
		String sql = "SELECT ID "+temp+" FROM t_quote_cascade_po WHERE Type=? AND QuoteID=?";
		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, new Object[]{type,quoteID});
		return ls.size()>1?Integer.parseInt(ls.get(1).get(temp).toString()):0;
	}
	
	public List<Map<String, Object>> getQuoteCompleteCascadePO(int id) {
		DBUtil db = new DBUtil();
		String sql = "select ID,Number,Version,ForwarderOne,ForwarderTwo,ForwarderThree,ForwarderFour,RefNO,"
				+ "ShipCompany,ShipAddr,ShipTel,ShipAttn,EndCompany,EndAddr,ContactPerson,DeliveryTerm,"
				+ "ShippingMark,ContractNO,ShipmentPort,SubTotal,concat(Discounted*100,'%') Discounted,FinalTotal,EndTel,QuoteID "
				+ "from t_quote_cascade_po where QuoteID=? and Type='Complete'";
		Object[] param = new Object[] { id };

		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public boolean insert(CascadePOMail mail){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into t_quote_cascade_po_mail (POID,Recepter,CopyList,Content,OperatingTime,Content1) values (?,?,?,?,?,?)";
		Object[] param = new Object[]{mail.getPOID(),mail.getRecepter(),mail.getCopyList(),mail.getContent(),df.format(new Date()),mail.getContent1()};

		return new DBUtil().executeUpdate(sql, param)>0?true:false;
	}
	
	public boolean update(CascadePOMail mail){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_quote_cascade_po_mail set Recepter=?,CopyList=?,Content=?,OperatingTime=?,Content1=? where ID=?";
		Object[] param = new Object[]{mail.getRecepter(),mail.getCopyList(),mail.getContent(),df.format(new Date()),mail.getContent1(),mail.getID()};

		return new DBUtil().executeUpdate(sql, param)>0?true:false;
	}
	
	public List<Map<String,Object>> getCascadeMail(int poID){
		String sql = "SELECT ID,Recepter,CopyList,Content1 FROM t_quote_cascade_po_mail WHERE POID=?";
		return new DBUtil().QueryToList(sql, new Object[]{poID});
	}
	
	public int getOtherPOID(String number) {//其他供应商 PO    数据库的操作
		int id = 0;
		DBUtil db = new DBUtil();
		Object[] param = new Object[] { number };
		String sql = "select ID from t_quote_other_po where Number=?";
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		if (ls.size() > 1) {
			id = Integer.parseInt(ls.get(1).get("ID").toString());
		}
		return id;
	}

	public boolean insertOtherPO(QuoteOtherPO po) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "insert into t_quote_other_po (Number,Version,ForwarderOne,ForwarderTwo,ForwarderThree,ForwarderFour,"
				+ "ShipCompany,ShipAddr,ShipTel,ShipAttn,VendorOne,VendorTwo,VendorThree,DeliveryTerm,"
				+ "ShippingMark,ContractNO,ShipmentPort,SubTotal,Discounted,FinalTotal,QuoteID,CreditTerm,VendorFour) values "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[23];
		param[0] = po.getNumber();
		param[1] = po.getVersion();
		param[2] = po.getForwarderOne();
		param[3] = po.getForwarderTwo();
		param[4] = po.getForwarderThree();
		param[5] = po.getForwarderFour();
		param[6] = po.getShipCompany();
		param[7] = po.getShipAddr();
		param[8] = po.getShipTel();
		param[9] = po.getShipAttn();
		param[10] = po.getVendorOne();
		param[11] = po.getVendorTwo();
		param[12] = po.getVendorThree();
		param[13] = po.getDeliveryTerm();
		param[14] = po.getShippingMark();
		param[15] = po.getContractNO();
		param[16] = po.getShipmentPort();
		param[17] = po.getSubTotal();
		param[18] = po.getDiscounted();
		param[19] = po.getFinalTotal();
		param[20] = po.getQuoteID();
		param[21] = po.getCreditTerm();
		param[22] = po.getVendorFour();
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean updateOtherPO(QuoteOtherPO po) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "update t_quote_other_po set Number=?,Version=?,ForwarderOne=?,ForwarderTwo=?,ForwarderThree=?,ForwarderFour=?,"
				+ "ShipCompany=?,ShipAddr=?,ShipTel=?,ShipAttn=?,VendorOne=?,VendorTwo=?,VendorThree=?,DeliveryTerm=?,"
				+ "ShippingMark=?,ContractNO=?,ShipmentPort=?,SubTotal=?,Discounted=?,FinalTotal=?,QuoteID=? "
				+ ",CreditTerm=?,VendorFour=? where ID=?";
		Object[] param = new Object[24];
		param[0] = po.getNumber();
		param[1] = po.getVersion();
		param[2] = po.getForwarderOne();
		param[3] = po.getForwarderTwo();
		param[4] = po.getForwarderThree();
		param[5] = po.getForwarderFour();
		param[6] = po.getShipCompany();
		param[7] = po.getShipAddr();
		param[8] = po.getShipTel();
		param[9] = po.getShipAttn();
		param[10] = po.getVendorOne();
		param[11] = po.getVendorTwo();
		param[12] = po.getVendorThree();
		param[13] = po.getDeliveryTerm();
		param[14] = po.getShippingMark();
		param[15] = po.getContractNO();
		param[16] = po.getShipmentPort();
		param[17] = po.getSubTotal();
		param[18] = po.getDiscounted();
		param[19] = po.getFinalTotal();
		param[20] = po.getQuoteID();
		param[21] = po.getCreditTerm();
		param[22] = po.getVendorFour();
		param[23] = po.getID();
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public List<Map<String, Object>> getQuoteOtherPO(int id) {
		DBUtil db = new DBUtil();
		String sql = "select ID,Number,Version,ForwarderOne,ForwarderTwo,ForwarderThree,ForwarderFour,"
				+ "ShipCompany,ShipAddr,ShipTel,ShipAttn,VendorOne,VendorTwo,VendorThree,VendorFour,DeliveryTerm,"
				+ "ShippingMark,ContractNO,ShipmentPort,SubTotal,concat(Discounted*100,'%') Discounted,FinalTotal,"
				+ "CreditTerm,QuoteID from t_quote_other_po where t_quote_other_po.QuoteID=?";
		Object[] param = new Object[] { id };

		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public boolean updateCascadePOContact(QuoteCascadePO other) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "update t_quote_cascade_po set Contact=?,Email=? ";
		Object[] param = new Object[2];
		param[0] = other.getContact();
		param[1] = other.getEmail();

		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public List<Map<String, Object>> getCurrentUserEmail(String name) {
		DBUtil db = new DBUtil();
		String sql = "select Email from t_user where UserName=?";
		Object[] param = new Object[] { name };

		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public List<Map<String, Object>> getUserEmail() {
		DBUtil db = new DBUtil();
		String sql = "select Contact,Email from t_quote_cascade_po ";
		Object[] param = null;

		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public boolean deleteCommodityTemp(int id) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "delete from t_commodity_temp where ID=?";
		Object[] param = new Object[] { id };
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public int getOtherSupplierPOID(String number) {
		int id = 0;
		DBUtil db = new DBUtil();
		Object[] param = new Object[] { number };
		String sql = "select ID from t_quote_other_supplier_po where Number=?";
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		if (ls.size() > 1) {
			id = Integer.parseInt(ls.get(1).get("ID").toString());
		}
		return id;
	}

	public boolean insertOtherSupplierPO(QuoteOtherSupplierPO po) {
		DBUtil db = new DBUtil();// ForwarderOne,ForwarderTwo,ForwarderThree,ForwarderFour,ShipCompany,ShipAddr,
		boolean flag = false;
		String sql = "insert into t_quote_other_supplier_po (Number,Version,"
				+ "ShipTel,ShipAttn,VendorOne,VendorTwo,VendorThree,DeliveryTerm,"
				+ "ShippingMark,ContractNO,ShipmentPort,SubTotal,Discounted,FinalTotal,QuoteID,CreditTerm,VendorFour,VendorFive,BillContact,BillEmail) values "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[20];
		param[0] = po.getNumber();
		param[1] = po.getVersion();
		// param[2] = po.getForwarderOne();
		// param[3] = po.getForwarderTwo();
		// param[4] = po.getForwarderThree();
		// param[5] = po.getForwarderFour();
		// param[6] = po.getShipCompany();
		// param[7] = po.getShipAddr();
		param[2] = po.getShipTel();
		param[3] = po.getShipAttn();
		param[4] = po.getVendorOne();
		param[5] = po.getVendorTwo();
		param[6] = po.getVendorThree();
		param[7] = po.getDeliveryTerm();
		param[8] = po.getShippingMark();
		param[9] = po.getContractNO();
		param[10] = po.getShipmentPort();
		param[11] = po.getSubTotal();
		param[12] = po.getDiscounted();
		param[13] = po.getFinalTotal();
		param[14] = po.getQuoteID();
		param[15] = po.getCreditTerm();
		param[16] = po.getVendorFour();
		param[17] = po.getVendorFive();
		param[18] = po.getBillContact();
		param[19] = po.getBillEmail();

		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean updateQuoteOtherSupplierPO(QuoteOtherSupplierPO po) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "update t_quote_other_supplier_po set Number=?,Version=?,"
				+ "ShipTel=?,ShipAttn=?,VendorOne=?,VendorTwo=?,VendorThree=?,DeliveryTerm=?,"
				+ "ShippingMark=?,ContractNO=?,ShipmentPort=?,SubTotal=?,Discounted=?,FinalTotal=?,QuoteID=? "
				+ ",CreditTerm=?,VendorFour=?,VendorFive=?,BillContact=?,BillEmail=? where ID=?";
		Object[] param = new Object[21];
		param[0] = po.getNumber();
		param[1] = po.getVersion();
		// param[2] = po.getForwarderOne();
		// param[3] = po.getForwarderTwo();
		// param[4] = po.getForwarderThree();
		// param[5] = po.getForwarderFour();
		// param[6] = po.getShipCompany();
		// param[7] = po.getShipAddr();
		param[2] = po.getShipTel();
		param[3] = po.getShipAttn();
		param[4] = po.getVendorOne();
		param[5] = po.getVendorTwo();
		param[6] = po.getVendorThree();
		param[7] = po.getDeliveryTerm();
		param[8] = po.getShippingMark();
		param[9] = po.getContractNO();
		param[10] = po.getShipmentPort();
		param[11] = po.getSubTotal();
		param[12] = po.getDiscounted();
		param[13] = po.getFinalTotal();
		param[14] = po.getQuoteID();
		param[15] = po.getCreditTerm();
		param[16] = po.getVendorFour();
		param[17] = po.getVendorFive();
		param[18] = po.getBillContact();
		param[19] = po.getBillEmail();
		param[20] = po.getID();
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public List<Map<String, Object>> getQuoteOtherSupplierPO(int id) {
		DBUtil db = new DBUtil();
		String sql = "select ID,Number,Version,VendorFour,VendorFive,BillContact,BillEmail,"
				+ "ShipTel,ShipAttn,VendorOne,VendorTwo,VendorThree,DeliveryTerm,"
				+ "ShippingMark,ContractNO,ShipmentPort,SubTotal,concat(Discounted*100,'%') Discounted,FinalTotal,"
				+ "CreditTerm,QuoteID from t_quote_other_supplier_po where t_quote_other_supplier_po.QuoteID=?";
		Object[] param = new Object[] { id };

		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public boolean insertOtherSupplierTemp(QuoteOtherSupplierTemp temp) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "insert into t_quote_other_supplier_temp (QuoteID,PartID) values (?,?) ";
		Object[] param = new Object[2];
		param[0] = temp.getQuoteID();
		param[1] = temp.getPartID();

		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public static List<Map<String, Object>> getOtherSupplierTemp(int id) {
		DBUtil db = new DBUtil();
		String sql = "select t_quote_other_supplier_temp.ID,t_commodity_info.Model ,"
				+ "FORMAT((t_commodity_info.CostPrice*t_quote_system.ExchangeRate*t_quote_system.TaxCategories*1.07*1.03),2) UnitPrice,"
				+ "t_quote_delivery.Quantity,"
				+ "FORMAT((t_commodity_info.CostPrice*t_quote_delivery.Quantity*t_quote_system.ExchangeRate*t_quote_system.TaxCategories*1.07*1.03),2) ExtendedPrice,"
				+ "t_quote_delivery.Description from t_quote_other_supplier_temp left join t_commodity_info "
				+ "on t_quote_other_supplier_temp.PartID=t_commodity_info.ID left join t_quote_delivery "
				+ "on t_quote_delivery.Model=t_commodity_info.Model LEFT JOIN t_quote_system "
				+ "on t_quote_system.ID=t_quote_other_supplier_temp.QuoteID "
				+ "where t_quote_delivery.QuoteID=? and t_quote_other_supplier_temp.QuoteID=?";
		Object[] param = new Object[] { id, id };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public boolean deleteOtherSupplierTemp(int id) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "delete from t_quote_other_supplier_temp where ID=? ";
		Object[] param = new Object[] { id };
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}
	
	public boolean deleteOtherSupplierTemp(int quoteID,int commodityID) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "delete from t_quote_other_supplier_temp where QuoteID=? and PartID=? ";
		Object[] param = new Object[] { quoteID,commodityID };
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public List<Map<String, Object>> getCascadePOByQuoteID(int quoteID) {
		DBUtil db = new DBUtil();
		String sql = "select ID,Number from t_quote_cascade_po where QuoteID=?";
		Object[] param = new Object[] { quoteID };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public List<Map<String, Object>> getOtherPOByQuoteID(int quoteID) {
		DBUtil db = new DBUtil();
		String sql = "select ID,Number from t_quote_other_po where QuoteID=?";
		Object[] param = new Object[] { quoteID };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public List<Map<String, Object>> getOtherRMBPOByQuoteID(int quoteID) {
		DBUtil db = new DBUtil();
		String sql = "select ID,Number from t_quote_other_supplier_po where QuoteID=?";
		Object[] param = new Object[] { quoteID };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public boolean insertModel(QuoteSystemModel model,List<Map<String, Object>> list) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into t_quote_system_model (QuoteID,SubTotal,Gifts,FinalTotal,Type,GiftsTotal,OperatingTime) values (?,?,?,?,?,?,?)";
		Connection connection = db.getConnection();
		Object[] param = new Object[7];
		param[0] = model.getQuoteID();
		param[1] = model.getSubTotal();
		param[2] = model.getGifts();
		param[3] = model.getFinalTotal();
		param[4] = model.getType();
		param[5] = model.getGiftsTotal();
		param[6] = df.format(new Date());
		try {
			connection.setAutoCommit(false);
			db.executeUpdateNotClose(sql, param);
			if(list!=null){
				if(list.get(0).get("UnitPriceColumn")!=null){
					String unitPriceColumn = list.get(0).get("UnitPriceColumn").toString();
					String extendedColumn = list.get(0).get("ExtendedColumn").toString();
					String sql2 = "update t_commodity_temp set "+unitPriceColumn+" = ?,"+extendedColumn+"=?,Qty=? where ID=?";
					for(int i = 0;i<list.size();i ++){
						db.executeUpdateNotClose(sql2, new Object[]{list.get(i).get("NewUnitPrice"),
								list.get(i).get("NewExtended"),list.get(i).get("Qty"),list.get(i).get("ID")});
					}
				}else{
					String sql2 = "update t_commodity_temp set Qty=? where ID=?";
					for(int i = 0;i<list.size();i ++){
						db.executeUpdateNotClose(sql2, new Object[]{list.get(i).get("Qty"),list.get(i).get("ID")});
					}
				}
			}
			connection.commit();
			return true;
			 
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}finally {
			db.closed();
		}

	}

	public boolean updateModel(QuoteSystemModel model,List<Map<String, Object>> list) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_quote_system_model set SubTotal=?,Gifts=?,FinalTotal=?,Type=?,GiftsTotal=?,OperatingTime=? where ID=?";
		Object[] param = new Object[7];
		param[0] = model.getSubTotal();
		param[1] = model.getGifts();
		param[2] = model.getFinalTotal();
		param[3] = model.getType();
		param[4] = model.getGiftsTotal();
		param[5] = df.format(new Date());
		param[6] = model.getID();
		Connection connection = db.getConnection();
		try {
			connection.setAutoCommit(false);
			db.executeUpdateNotClose(sql, param);
			
			if(list.get(0).get("UnitPriceColumn")!=null){
				String unitPriceColumn = list.get(0).get("UnitPriceColumn").toString();
				String extendedColumn = list.get(0).get("ExtendedColumn").toString();
				String sql2 = "update t_commodity_temp set "+unitPriceColumn+" = ?,"+extendedColumn+"=?,Qty=? where ID=?";
				for(int i = 0;i<list.size();i ++){
					db.executeUpdateNotClose(sql2, new Object[]{list.get(i).get("NewUnitPrice"),
							list.get(i).get("NewExtended"),list.get(i).get("Qty"),list.get(i).get("ID")});
				}
			}else{
				String sql2 = "update t_commodity_temp set Qty=? where ID=?";
				for(int i = 0;i<list.size();i ++){
					db.executeUpdateNotClose(sql2, new Object[]{list.get(i).get("Qty"),list.get(i).get("ID")});
				}
			}
			connection.commit();
			return true;
			 
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}finally {
			db.closed();
		}
	}

	public List<Map<String, Object>> getModelInfo(int quoteID, String type) {
		DBUtil db = new DBUtil();
		String sql = "select ID,SubTotal,Gifts,FinalTotal,GiftsTotal from t_quote_system_model where QuoteID=? and Type=?";
		Object[] param = new Object[] { quoteID, type };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public String getQuoteNumber(int id) {
		String sql = "select Number from t_quote_system where ID=?";
		Object[] param = new Object[] { id };
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		String number = "";
		if (ls.size() > 1) {
			number = ls.get(1).get("Number").toString();
		}
		return number;
	}

	public String getCommodityName(String model) {
		DBUtil db = new DBUtil();
		String sql = "select CommodityName from t_commodity_info where Model=?";
		Object[] param = new Object[] { model };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		String name = "";
		if (ls.size() > 1) {
			name = ls.get(1).get("CommodityName").toString();
		}
		return name;
	}

	public String getStaffCode(String staff) {
		DBUtil db = new DBUtil();
		String sql = "select StaffCode from t_staff WHERE StaffName=?";
		Object[] param = new Object[] { staff };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		String name = "";
		if (ls.size() > 1) {
			name = ls.get(1).get("StaffCode").toString();
		}
		return name;
	}

	public List<Map<String, Object>> getTotalPrice(int quoteID) {
		DBUtil db = new DBUtil();
		String sql = "select t_commodity_temp.QuoteID,FORMAT(SUM(t_commodity_temp.Qty*t_commodity_info.SellerPriceOne),0) FinalTotal"
				+ " from t_commodity_temp  left join t_commodity_info on "
				+ "t_commodity_temp.Commodity=t_commodity_info.ID WHERE "
				+ "t_commodity_temp.QuoteID=? GROUP BY t_commodity_temp.QuoteID";
		Object[] param = new Object[] { quoteID };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public List<Map<String, Object>> getGiftsTotal(int quoteID, String type) {
		DBUtil db = new DBUtil();
		String sql = "SELECT Gifts,GiftsTotal FROM t_quote_system_model WHERE QuoteID=? AND Type=?";
		Object[] param = new Object[] { quoteID, type };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public boolean updateModel2(QuoteSystemModel model) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_quote_system_model set SubTotal=?,FinalTotal=?,OperatingTime=? where QuoteID=? AND Type=?";
		Object[] param = new Object[5];
		param[0] = model.getSubTotal();
		param[1] = model.getFinalTotal();
		param[2] = df.format(new Date());
		param[3] = model.getQuoteID();
		param[4] = model.getType();
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if (count >= 1) {
			flag = true;
		}

		return flag;
	}

	public List<Map<String, Object>> GetModelByCategory(String productCategory) {
		DBUtil db = new DBUtil();
		String sql = "select Model from t_commodity_info where ProductCategory=? order by Model";
		Object[] param = new Object[] { productCategory };
		return db.QueryToList(sql, param);
	}

	public double getUnitPriceAvgByModel() {
		DBUtil db = new DBUtil();
		String sql = "select avg(a.CostPrice*1.5) avg from "
				+ "(select max(CostPrice) CostPrice from t_commodity_info GROUP BY Model) a";
		Object[] parameter = null;
		return Double.parseDouble(db.QueryToList(sql, parameter).get(1).get("avg").toString());
	}

	public double getUnitPriceValueByModel(String model) {
		DBUtil db = new DBUtil();
		String sql = "select max(CostPrice*1.5) max from t_commodity_info where Model =?";
		Object[] parameter = new Object[] { model };
		return Double.parseDouble(db.QueryToList(sql, parameter).get(1).get("max").toString());
	}

	public double getUnitPriceStdevByModel() {
		DBUtil db = new DBUtil();
		String sql = "select std(a.CostPrice*1.5) std from "
				+ "(select max(CostPrice) CostPrice from t_commodity_info GROUP BY Model) a";
		Object[] parameter = null;
		return Double.parseDouble(db.QueryToList(sql, parameter).get(1).get("std").toString());
	}

	public double getValueAvgByModel() {
		DBUtil db = new DBUtil();
		String sql = "select avg((select COALESCE(SUM(Number),0) from t_order_info join t_order on t_order_info.OrderID = t_order.ID where t_order.PageType = 0 and EquipmentModel in "
				+ "(select ID from t_commodity_info where t_commodity_info.Model = a.Model)  )*a.CostPrice*1.5) avg "
				+ "from (select t_commodity_info.Model Model,max(t_commodity_info.CostPrice) CostPrice from t_commodity_info  GROUP BY t_commodity_info.Model) a ";
		Object[] parameter = null;
		return Double.parseDouble(db.QueryToList(sql, parameter).get(1).get("avg").toString());
	}

	public double getValueValueByModel(String model,String startTime,String endTime) {
		DBUtil db = new DBUtil();
		String sql = "select (select COALESCE(SUM(Number),0) from t_order_info join t_order on t_order_info.OrderID = t_order.ID where t_order.PageType = 0 and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? and EquipmentModel in "
				+ "(select ID from t_commodity_info where t_commodity_info.Model =?)) *max(COALESCE(CostPrice,0)*1.5) max"
				+ " from t_commodity_info where  Model=?";
		Object[] parameter = new Object[] {startTime,endTime, model, model };
		return Double.parseDouble(db.QueryToList(sql, parameter).get(1).get("max").toString());
	}

	public double getValueStdevByModel() {
		DBUtil db = new DBUtil();
//		String sql = "select std((select COALESCE(SUM(Number),0) from t_order_info join t_order on t_order_info.OrderID = t_order.ID where t_order.PageType = 0 and EquipmentModel in "
//				+ "(select ID from t_equipment where t_equipment.Model = a.Model)  )*a.CostPrice*1.5) std "
//				+ "from (select t_commodity_info.Model Model,max(t_commodity_info.CostPrice) CostPrice from t_commodity_info  GROUP BY t_commodity_info.Model) a ";
		
		String sql = "select std((select COALESCE(SUM(Number),0) from t_order_info join t_order on "
				+ "t_order_info.OrderID = t_order.ID where t_order.PageType = 0 and EquipmentModel in "
				+ "(select ID from t_commodity_info )  )*t_commodity_info.CostPrice*1.5) std from t_commodity_info  ";
		Object[] parameter = null;
		return Double.parseDouble(db.QueryToList(sql, parameter).get(1).get("std").toString());
	}

	public double getClientAvgByModel() {
		DBUtil db = new DBUtil();
//		String sql = "select avg((select count(DISTINCT(t_order.Customer)) from t_order where t_order.PageType = 0 and t_order.ID in  "
//				+ " (select t_order_info.OrderID from t_order_info where t_order_info.ID in  "
//				+ "(select t_equipment.ID from t_equipment where t_equipment.Model = a.Model)))*1) avg from "
//				+ " (select t_commodity_info.Model from t_commodity_info GROUP BY t_commodity_info.Model) a ";
		String sql = "select avg(a.count) avg from (select count(DISTINCT(t_order.Customer)) count from "
				+ "t_order where t_order.PageType = 0 and t_order.ID in (select t_order_info.OrderID from "
				+ "t_order_info where t_order_info.EquipmentModel<>''))a";
		Object[] parameter = null;
		return Double.parseDouble(db.QueryToList(sql, parameter).get(1).get("avg").toString());
	}

	public double getClientStdevByModel() {
		DBUtil db = new DBUtil();
//		String sql = "select std((select count(DISTINCT(t_order.Customer)) from t_order where t_order.PageType = 0 and t_order.ID in  "
//				+ " (select t_order_info.OrderID from t_order_info where t_order_info.ID in  "
//				+ "(select t_equipment.ID from t_equipment where t_equipment.Model = a.Model)))*1) std from  "
//				+ "(select t_commodity_info.Model Model from t_commodity_info GROUP BY t_commodity_info.Model) a";
		String sql = "select std(a.count*1) std from (select count(DISTINCT(t_order.Customer)) count from"
				+ " t_order where t_order.PageType = 0 and t_order.ID in (select t_order_info.OrderID from "
				+ "t_order_info where t_order_info.EquipmentModel<>'' ))a ";
		Object[] parameter = null;
		return Double.parseDouble(db.QueryToList(sql, parameter).get(1).get("std").toString());
	}

	public double getClientValueByModel(String model,String startTime,String endTime) {

		DBUtil db = new DBUtil();
		String sql = "select count(DISTINCT(t_order.Customer)) max from t_order " + 
				" left join t_order_info on t_order_info.OrderID = t_order.ID " + 
				"left join t_commodity_info on t_commodity_info.ID =t_order_info.EquipmentModel " + 
				" where t_order.PageType = 0 and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? "
				+ "and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? and t_commodity_info.Model =?";
		Object[] parameter = new Object[] {startTime,endTime,model };
		return Double.parseDouble(db.QueryToList(sql, parameter).get(1).get("max").toString());
	}

	public List<String> getAllModel() {
		List<String> resultList = new ArrayList<String>();
		DBUtil db = new DBUtil();
		String sql = "select DISTINCT(Model) Model from t_commodity_info where ProductCategory = '探针台' "
				+ "OR ProductCategory = '射频探针' OR ProductCategory = '直流探针' OR ProductCategory = '线缆' "
				+ "OR ProductCategory = '定位器' OR ProductCategory = '校准片' OR ProductCategory = '其他'";
		List<Map<String, Object>> ls = db.QueryToList(sql, null);
		for (int i = 1; i < ls.size(); i++) {
			resultList.add(ls.get(i).get("Model").toString());
		}
		return resultList;
	}

	public void deleteAll() {
		DBUtil db = new DBUtil();
		String sql = "truncate t_radar";
		Object[] param = new Object[0];
		db.executeUpdate(sql, param);
	}

	public void insetIntoRadar(String Name, double priceScore, double NumberScore, double valueScore,
			double clientScore, double salesScore) {
		DBUtil db = new DBUtil();
		String sql = "insert into t_radar (Name,UnitPrice,Quantity,Worth,Client,MonthlySales) values (?,?,?,?,?,?)";
		Object[] param = new Object[] { Name, priceScore, NumberScore, valueScore, clientScore, salesScore };
		db.executeUpdate(sql, param);
	}

	public Map<String, Double> getSalesValuesByModel() {
		String sql = "select t_commodity_info.Model Model,std(a.sum) std from t_commodity_info "
				+ "left join  (select COALESCE(SUM(t_order_info.Number),0) sum,t_commodity_info.Model Model from t_order_info "
				+ "left join t_order on t_order.id=t_order_info.OrderID "
				+ "left join t_commodity_info on t_commodity_info.ID = t_order_info.EquipmentModel where t_order.PageType = 0 group by year(t_order.DateOfSign),month(t_order.DateOfSign) ) a "
				+ "on a.Model = t_commodity_info.Model " + "GROUP BY t_commodity_info.Model ";
		DBUtil db = new DBUtil();
		Object[] param = null;
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		Map<String, Double> result = new HashMap<String, Double>();
		for (int i = 1; i < ls.size(); i++) {
			result.put(ls.get(i).get("Model").toString(),
					ls.get(i).get("std") == null || "--".equals(ls.get(i).get("std").toString()) ? 0
							: Double.parseDouble(ls.get(i).get("std").toString()));
		}
		return result;

	}

	public List<String> getAllProductCategory() {
		List<String> resultList = new ArrayList<String>();
		DBUtil db = new DBUtil();
		String sql = "select DISTINCT(ProductCategory) ProductCategory from t_commodity_info where 1=?";
		Object[] param = new Object[] { 1 };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		for (int i = 1; i < ls.size(); i++) {
			resultList.add(ls.get(i).get("ProductCategory").toString());
		}
		return resultList;
	}

	public double getUnitPriceAvgByProductCategor() {
		DBUtil db = new DBUtil();
		String sql = "select avg(a.CostPriceAvg*1.5) avg from "
				+ "(select avg(CostPrice) CostPriceAvg from t_commodity_info GROUP BY ProductCategory) a";
		Object[] parameter = null;
		String a = db.QueryToList(sql, parameter).get(1).get("avg").toString();
		return Double.parseDouble(a.equals("--") ? "0" : a);
	}

	public double getUnitPriceStdevByProductCategor() {
		DBUtil db = new DBUtil();
		String sql = "select std(a.CostPriceAvg*1.5) std from "
				+ "(select avg(CostPrice) CostPriceAvg from t_commodity_info GROUP BY ProductCategory) a";
		Object[] parameter = null;
		String a = db.QueryToList(sql, parameter).get(1).get("std").toString();
		return Double.parseDouble(a.equals("--") ? "0" : a);
	}

	public double getUnitPriceValueByProductCategory(String ProductCategory) {
		DBUtil db = new DBUtil();
		String sql = "select avg(CostPrice*1.5) CostPriceAvg from t_commodity_info where ProductCategory=?";
		Object[] parameter = new Object[] { ProductCategory };
		String a = db.QueryToList(sql, parameter).get(1).get("std").toString();
		return Double.parseDouble(a.equals("--") ? "0" : a);
	}

	public double getNumberAvgByProductCategor() {
		DBUtil db = new DBUtil();
		String sql = "select avg(a.con) avg from (select count(Model) con from t_commodity_info GROUP BY ProductCategory) a";
		Object[] parameter = null;
		String a = db.QueryToList(sql, parameter).get(1).get("avg").toString();
		return Double.parseDouble(a.equals("--") ? "0" : a);
	}

	public double getNumberStdevPByProductCategor() {
		DBUtil db = new DBUtil();
		String sql = "select std(a.con) std from (select count(Model) con from t_commodity_info GROUP BY ProductCategory) a";
		Object[] parameter = null;
		String a = db.QueryToList(sql, parameter).get(1).get("std").toString();
		return Double.parseDouble(a.equals("--") ? "0" : a);
	}

	public Double getPriceValueByProductCategory(String productCategory) {
		DBUtil db = new DBUtil();
		String sql = "";
		Object[] parameter = null;
		if (productCategory.equals("--")) {
			sql = "select avg(t_commodity_info.CostPrice*1.5) result "
					+ "from t_commodity_info where ProductCategory is null";
		} else {
			sql = "select avg(t_commodity_info.CostPrice*1.5) result "
					+ "from t_commodity_info where ProductCategory=?";
			parameter = new Object[] { productCategory };
		}
		String a = db.QueryToList(sql, parameter).get(1).get("result").toString();
		return Double.parseDouble(a.equals("--") ? "0" : a);
	}

	public Double getNumberValueByProductCategory(String productCategory,String startTime,String endTime) {
		DBUtil db = new DBUtil();
		String sql = "";
		Object[] parameter = null;
		if (productCategory.equals("--")) {
//			sql = "select sum(t_order_info.Number) sum from " + 
//					"t_order_info join t_order on t_order_info.OrderID = t_order.ID where t_order.PageType = 0 and t_order_info.EquipmentModel in " + 
//					"(select t_equipment.ID from t_equipment where t_equipment.Model in " + 
//					"(select t_commodity_info.Model from t_commodity_info where t_commodity_info.ProductCategory is null))";
			sql = "select sum(t_order_info.Number) sum from t_order_info join t_order on t_order_info.OrderID = t_order.ID "
					+ "where t_order.PageType = 0 and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? and "
					+ "DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? and t_order_info.EquipmentModel in (select  t_commodity_info.ID from "
					+ "t_commodity_info where t_commodity_info.ProductCategory is null)";
			parameter = new Object[]{startTime,endTime};
			
		} else {
			sql = "select sum(t_order_info.Number) sum from " + 
					"t_order_info join t_order on t_order_info.OrderID = t_order.ID where t_order.PageType = 0 and "
					+ "DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? and t_order_info.EquipmentModel in " + 
					"(select t_commodity_info.ID from t_commodity_info where t_commodity_info.ProductCategory=?)";
			parameter = new Object[] {startTime,endTime,productCategory };
		}
		Object b = db.QueryToList(sql, parameter).get(1).get("sum");
		String a = b == null ? "0" : b.toString();
		return Double.parseDouble(a.equals("--") ? "0" : a);
	}

//	public static void main(String[] args) {
//		System.out.println(new QuoteSystemDao().getNumberValueByProductCategory("--"));
//	}

	public Double getValueValueByProductCategory(String productCategory,String startTime,String endTime) {
		DBUtil db = new DBUtil();
		String sql = "";
		Object[] parameter = null;
		if (productCategory.equals("--")) {
			sql = "select avg(t_commodity_info.CostPrice*1.5)*(select sum(t_order_info.Number) sum from "
					+ "t_order_info join t_order on t_order_info.OrderID = t_order.ID where t_order.PageType = 0 and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? and t_order_info.EquipmentModel in "
//					+ "(select t_equipment.ID from t_equipment where t_equipment.Model in "
					+ "(select t_commodity_info.ID from t_commodity_info where t_commodity_info.ProductCategory is null)) result "
					+ "from t_commodity_info where ProductCategory is null";
			parameter = new Object[]{startTime,endTime};
		} else {
			sql = "select avg(t_commodity_info.CostPrice*1.5)*(select sum(t_order_info.Number) sum from "
					+ "t_order_info join t_order on t_order_info.OrderID = t_order.ID where t_order.PageType = 0 and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? and t_order_info.EquipmentModel in "
//					+ "(select t_equipment.ID from t_equipment where t_equipment.Model in "
					+ "(select t_commodity_info.ID from t_commodity_info where t_commodity_info.ProductCategory=?)) result "
					+ "from t_commodity_info where ProductCategory=?";
			parameter = new Object[] {startTime,endTime, productCategory, productCategory };
		}
		String a = db.QueryToList(sql, parameter).get(1).get("result").toString();
		return Double.parseDouble(a.equals("--") ? "0" : a);
	}

	public Double getClientValueByProductCategory(String productCategory,String startTime,String endTime) {
		DBUtil db = new DBUtil();
		String sql = "";
		Object[] parameter = null;
		if (productCategory.equals("--")) {
			sql = "select count(DISTINCT(t_order.Customer)) result from t_order left join t_order_info on t_order.ID=t_order_info.OrderID "
					+ "left join t_commodity_info on t_commodity_info.ID=t_order_info.EquipmentModel where t_order.PageType = 0 "
					+ "and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? and  t_commodity_info.ProductCategory is null";
			parameter = new Object[] {startTime,endTime};
		} else {
			sql = "select count(DISTINCT(t_order.Customer)) result from t_order left join t_order_info on t_order.ID=t_order_info.OrderID "
					+ "left join t_commodity_info on t_commodity_info.ID=t_order_info.EquipmentModel "
					+ "where t_order.PageType = 0 and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? and "
					+ "DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? and  t_commodity_info.ProductCategory =?";
			parameter = new Object[] {startTime,endTime, productCategory };
		}
		String a = db.QueryToList(sql, parameter).get(1).get("result").toString();
		return Double.parseDouble(a.equals("--") ? "0" : a);
	}

	public Double getSalesValueByProductCategory(String productCategory,String startTime,String endTime) {
		String sql = "";
		Object[] parameter = null;
		if (productCategory.equals("--")) {
			sql = "select COUNT(*) result from (select COALESCE(SUM(t_order_info.Number),0) sum from t_order_info "
					+ "left join t_order on t_order.id=t_order_info.OrderID left join "
					+ "t_commodity_info on t_commodity_info.ID = t_order_info.EquipmentModel "
					+ "where t_order.PageType = 0 and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? "
					+ "and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? "
					+ "and t_commodity_info.ProductCategory=null group by year(t_order.DateOfSign),month(t_order.DateOfSign)) a";
			parameter = new Object[]{startTime,endTime};
		} else {
			sql = "select COUNT(*) result from (select COALESCE(SUM(t_order_info.Number),0) sum from t_order_info "
					+ "left join t_order on t_order.id=t_order_info.OrderID left join "
					+ "t_commodity_info on t_commodity_info.ID = t_order_info.EquipmentModel "
					+ "where t_order.PageType = 0 and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? "
					+ "and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? and t_commodity_info.ProductCategory=? group by year(t_order.DateOfSign),month(t_order.DateOfSign)) a";
			parameter = new Object[] {startTime,endTime,productCategory};
		}
		DBUtil db = new DBUtil();
		String a = db.QueryToList(sql, parameter).get(1).get("result").toString();
		return Double.parseDouble(a.equals("--") ? "0" : a);
	}

	public String[] getRaderByName(String name,double[] maxAndMin) {
		String[] result = new String[5];
		DBUtil db = new DBUtil();
		String sql = "select UnitPrice,Quantity,Worth,Client,MonthlySales from t_radar where Name=?";
		Object[] parameter = new Object[] { name};
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		if(maxAndMin[0]-maxAndMin[1] == 0){
			result[0] = "0";
		}else{
			result[0] = String.valueOf(1-(Double.parseDouble(ls.get(1).get("UnitPrice").toString())-maxAndMin[1])/(maxAndMin[0]-maxAndMin[1]));
		}
		if(maxAndMin[2]-maxAndMin[3] == 0){
			result[1] = "0";
		}else{
			result[1] = String.valueOf((Double.parseDouble(ls.get(1).get("Quantity").toString())-maxAndMin[3])/(maxAndMin[2]-maxAndMin[3]));
		}
		if(maxAndMin[4]-maxAndMin[5] == 0){
			result[2] = "0";
		}else{
			result[2] = String.valueOf((Double.parseDouble(ls.get(1).get("Worth").toString())-maxAndMin[5])/(maxAndMin[4]-maxAndMin[5]));
		}
		if(maxAndMin[6]-maxAndMin[7] == 0){
			result[3] = "0";
		}else{
			result[3] = String.valueOf((Double.parseDouble(ls.get(1).get("Client").toString())-maxAndMin[7])/(maxAndMin[6]-maxAndMin[7]));
		}
		if(maxAndMin[8]-maxAndMin[9] == 0){
			result[4] = "0";
		}else{
			result[4] = String.valueOf((Double.parseDouble(ls.get(1).get("MonthlySales").toString())-maxAndMin[9])/(maxAndMin[8]-maxAndMin[9]));
		}
		/*for(int i = 0;i < result.length;i ++){
			System.out.println(result[i]);
		}*/
		return result;

	}

	public String[] getDataByName(String name){
		String[] result = new String[5];
		DBUtil db = new DBUtil();
		String sql = "select UnitPrice,Quantity,Worth,Client,MonthlySales from t_radar where Name=?";
		Object[] parameter = new Object[] { name};
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		result[0] = String.valueOf((Double.parseDouble(ls.get(1).get("UnitPrice").toString())));
		result[1] = String.valueOf((Double.parseDouble(ls.get(1).get("Quantity").toString())));
		result[2] = String.valueOf((Double.parseDouble(ls.get(1).get("Worth").toString())));
		result[3] = String.valueOf((Double.parseDouble(ls.get(1).get("Client").toString())));
		result[4] = String.valueOf((Double.parseDouble(ls.get(1).get("MonthlySales").toString())));
		return result;
		
	}
public static void main(String[] args) {
	
}
	/*private double[] getRaderMaxAndMinByCategory(String category) {
		double[] result = new double[10];
		DBUtil db = new DBUtil();
		String sql = "select max(UnitPrice) xUnitPrice,min(UnitPrice) iUnitPrice,"
				+ "max(Quantity) xQuantity,min(Quantity) iQuantity,"
				+ "max(Worth) xWorth,min(Worth) iWorth,"
				+ "max(Client) xClient,min(Client) iClient,"
				+ "max(MonthlySales) xMonthlySales,min(MonthlySales) iMonthlySales"
				+ " from t_radar where Category=?";
		Object[] parameter = new Object[] {category };
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		System.out.println(ls);
		System.out.println(ls.get(1).get("xUnitPrice").toString());
		System.out.println(ls.get(1).get("iUnitPrice").toString());
		result[0]=Double.parseDouble(ls.get(1).get("xUnitPrice").toString());
		result[1]=Double.parseDouble(ls.get(1).get("iUnitPrice").toString());
		result[2]=Double.parseDouble(ls.get(1).get("xQuantity").toString());
		result[3]=Double.parseDouble(ls.get(1).get("iQuantity").toString());
		result[4]=Double.parseDouble(ls.get(1).get("xWorth").toString());
		result[5]=Double.parseDouble(ls.get(1).get("iWorth").toString());
		result[6]=Double.parseDouble(ls.get(1).get("xClient").toString());
		result[7]=Double.parseDouble(ls.get(1).get("iClient").toString());
		result[8]=Double.parseDouble(ls.get(1).get("xMonthlySales").toString());
		result[9]=Double.parseDouble(ls.get(1).get("iMonthlySales").toString());
		for(int i=0;i<result.length;i++) {
			System.out.println(result[i]);
		}
		return result;
	}*/
	public double[] getRaderMaxAndMinByModelList(String[] ModelList) {
		double[] result = new double[10];
		DBUtil db = new DBUtil();
		String sql1 = "(";
		for(int i = 0;i < ModelList.length -1;i ++){
			sql1 +="?,";
		}
		sql1 += "?)";
		String sql = "select max(UnitPrice) xUnitPrice,min(UnitPrice) iUnitPrice,"
				+ "max(Quantity) xQuantity,min(Quantity) iQuantity,"
				+ "max(Worth) xWorth,min(Worth) iWorth,"
				+ "max(Client) xClient,min(Client) iClient,"
				+ "max(MonthlySales) xMonthlySales,min(MonthlySales) iMonthlySales"
				+ " from t_radar where Name in" + sql1;
		Object[] parameter = new Object[ModelList.length];
		for(int i = 0;i < ModelList.length;i ++){
			parameter[i] = ModelList[i];
		}

		
		List<Map<String,Object>> ls = db.QueryToList(sql,parameter);
		result[0]=Double.parseDouble(ls.get(1).get("xUnitPrice").toString())*1.1;
		result[1]=Double.parseDouble(ls.get(1).get("iUnitPrice").toString())*0.9;
		result[2]=Double.parseDouble(ls.get(1).get("xQuantity").toString())*1.1;
		result[3]=Double.parseDouble(ls.get(1).get("iQuantity").toString())*0.9;
		result[4]=Double.parseDouble(ls.get(1).get("xWorth").toString())*1.1;
		result[5]=Double.parseDouble(ls.get(1).get("iWorth").toString())*0.9;
		result[6]=Double.parseDouble(ls.get(1).get("xClient").toString())*1.1;
		result[7]=Double.parseDouble(ls.get(1).get("iClient").toString())*0.9;
		result[8]=Double.parseDouble(ls.get(1).get("xMonthlySales").toString())*1.1;
		result[9]=Double.parseDouble(ls.get(1).get("iMonthlySales").toString())*0.9;
		return result;
	}

	public double getUnitPriceValueByModel(List<String> list) {
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[list.size()];
		String sql1 = "";
		for (int i = 0; i < list.size(); i++) {
			parameter[i] = list.get(i);
			if (i == 0) {
				sql1 += "?";
			} else {
				sql1 += ",?";
			}
		}
		String sql = "select max(CostPrice*1.5) CostPrice from t_commodity_info  where Model in(" + sql1 + ")";
		return Double.parseDouble(db.QueryToList(sql, parameter).get(1).get("CostPrice").toString());
	}

	public double getValueValueByModel(List<String> list,String startTime,String endTime) {
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[list.size() * 2 + 2];
		String sql1 = "";
		parameter[0] = startTime;
		parameter[1] = endTime;
		for (int i = 0; i < list.size(); i++) {
			parameter[i+2] = list.get(i);
			parameter[i+2 + list.size()] = list.get(i);
			if (i == 0) {
				sql1 += "?";
			} else {
				sql1 += ",?";
			}
		}
		String sql = "select (select COALESCE(SUM(Number),0) from t_order_info join t_order on t_order_info.OrderID = t_order.ID "
				+ "where t_order.PageType = 0 and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? and EquipmentModel in "
				+ "(select ID from t_commodity_info where t_commodity_info.Model in (" + sql1 + "))) *max(COALESCE(CostPrice,0)*1.5) max"
				+ " from t_commodity_info where  Model in (" + sql1 + ")";
		return Double.parseDouble(db.QueryToList(sql, parameter).get(1).get("max").toString());
	}

	public double getClientValueByModel(List<String> list,String startTime,String endTime) {
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[list.size()+2];
		parameter[0] = startTime;
		parameter[1] = endTime;
		String sql1 = "";
		for (int i = 0; i < list.size(); i++) {
			parameter[i+2] = list.get(i);
			if (i == 0) {
				sql1 += "?";
			} else {
				sql1 += ",?";
			}
			
		}
		String sql = "select count(DISTINCT(t_order.Customer)) max from t_order " + 
				" left join t_order_info on t_order_info.OrderID = t_order.ID " + 
				"left join t_commodity_info on t_commodity_info.ID=t_order_info.EquipmentModel " + 
				" where t_order.PageType = 0 and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? and "
				+ "DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? and t_commodity_info.Model in ("+sql1+")";
		return Double.parseDouble(db.QueryToList(sql, parameter).get(1).get("max").toString());
	}

	public double getSalesValueByModel(List<String> list,String startTime,String endTime) {
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[list.size()+2];
		String sql1 = "";
		for (int i = 0; i < list.size(); i++) {
			parameter[i] = list.get(i);
			if (i == 0) {
				sql1 += "?";
			} else {
				sql1 += ",?";
			}
		}
		parameter[list.size()] = startTime;
		parameter[list.size()+1] = endTime;
		String sql =  "select COUNT(*) std from (select COALESCE(SUM(t_order_info.Number),0) sum from "
				+ "t_order_info left join t_order on t_order.id=t_order_info.OrderID left "
				+ "join t_commodity_info on t_commodity_info.ID = t_order_info.EquipmentModel "
				+ "where t_order.PageType = 0 and t_commodity_info.Model IN("+sql1+") "
				+ "and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? "
				+ "group by year(t_order.DateOfSign),month(t_order.DateOfSign)) a";

		Object result = db.QueryToList(sql, parameter).get(1).get("std");
		String result2 = result == null || result.toString().equals("--") ? "0" : result.toString();
		return Double.parseDouble(result2);
	}

	public List<Map<String, Object>> getHuaEgoComplete(int id) {
		String sql = "SELECT t_commodity_info.Item,t_commodity_info.ItemDescription,t_commodity_temp.Qty FROM t_commodity_temp LEFT JOIN t_commodity_info ON t_commodity_temp.Commodity=t_commodity_info.ID WHERE t_commodity_temp.QuoteID=?";
		DBUtil db = new DBUtil();
		Object[] param = new Object[] { id };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public List<Map<String, Object>> getHuaEgoParts(int id) {
		String sql = "SELECT t_commodity_temp.ID,t_commodity_info.Item,t_commodity_info.ItemDescription,t_commodity_temp.Qty,"
				+ "t_commodity_info.Model,t_commodity_info.CommodityName,t_commodity_temp.UnitUSD,"
				+ "t_commodity_temp.DiscountedUSD,t_commodity_temp.TotalPrice,"
				+ "t_commodity_temp.DiscountedPercent,t_commodity_temp.Remarks FROM t_commodity_temp "
				+ "LEFT JOIN t_commodity_info ON t_commodity_temp.Commodity=t_commodity_info.ID WHERE "
				+ "t_commodity_temp.QuoteID=?";
		DBUtil db = new DBUtil();
		Object[] param = new Object[] { id };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public boolean operateHuaEgoParts(CommodityTemp temp) {
		String sql = "update t_commodity_temp set UnitUSD=?,DiscountedUSD=?,TotalPrice=?,DiscountedPercent=?,Remarks=? where ID=?";
		DBUtil db = new DBUtil();
		Object[] param = new Object[6];
		param[0] = temp.getUnitUSD();
		param[1] = temp.getDiscountedUSD();
		param[2] = temp.getTotalPrice();
		param[3] = temp.getDiscountedPercent();
		param[4] = temp.getRemarks();
		param[5] = temp.getID();

		int count = db.executeUpdate(sql, param);
		boolean flag = false;
		if (count > 0) {
			flag = true;
		}
		return flag;
	}

	public Map<String, Double> getUnitPriceValueByModel() {
		String sql = "select Model,max(CostPrice*1.5) std from t_commodity_info group by Model";
		DBUtil db = new DBUtil();
		Object[] param = null;
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		Map<String, Double> result = new HashMap<String, Double>();
		for (int i = 1; i < ls.size(); i++) {
			result.put(ls.get(i).get("Model").toString(),
					ls.get(i).get("std") == null || "--".equals(ls.get(i).get("std").toString()) ? 0
							: Double.parseDouble(ls.get(i).get("std").toString()));
		}
		return result;
	}

	public List<Map<String,Object>> getCommodityOtherInfo(int iD) {
		DBUtil db = new DBUtil();
		String sql ="select t_supplier_info.PurchaseRecordPath,t_supplier_info.PaymentCondition,"
				+ "t_supplier_info.PurchaseContact ,t_supplier_info.PurchaseEmail,t_supplier_info.ID SupplierID,"
				+ "t_supplier_info.PurchaseLink,t_supplier_info.PurchaseModel,t_supplier_info.ValidTime,"
				+ "t_commodity_mail.CCList,t_commodity_mail.Consignee,t_commodity_mail.Content,"
				+ "t_commodity_mail.ID MailID,t_commodity_mail.`Subject` from  t_supplier_info "
				+ " LEFT JOIN t_commodity_mail ON t_supplier_info.Commodity=t_commodity_mail.Commodity "
				+ "where t_supplier_info.Commodity=?";
		Object[] param = new Object[] {iD};
		
		return db.QueryToList(sql, param);
	}

	public String getSalesValueString(String model,String startTime,String endTime) {
		String sql ="select COALESCE(SUM(t_order_info.Number),0) sum from t_order_info " + 
				"left join t_order on t_order.id=t_order_info.OrderID " + 
				"left join t_commodity_info on t_commodity_info.ID = t_order_info.EquipmentModel" + 
				" where t_order.PageType = 0 and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? "
				+ "and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? and t_commodity_info.Model=?" + 
				" group by year(t_order.DateOfSign),month(t_order.DateOfSign) ";
		DBUtil db = new DBUtil();
		Object[] param = new Object[] {startTime,endTime,model};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		StringBuilder sb = new StringBuilder();
		for(int i=1;i<ls.size();i++) {
			if(i==ls.size()-1) {
				sb.append(ls.get(i).get("sum").toString());
			}else{
				sb.append(ls.get(i).get("sum").toString()+",");
			}
		}
		return sb.toString();
	}

	public String getSalesValueString(List<String> list,String startTime,String endTime) {
		Object[] parameter = new Object[list.size()+2];
		String sql1 = "";
		parameter[0] = startTime;
		parameter[1] = endTime;
		for (int i = 0; i < list.size(); i++) {
			parameter[i+2] = list.get(i);
			if (i == 0) {
				sql1 += "?";
			} else {
				sql1 += ",?";
			}
		}
		String sql ="select COALESCE(SUM(t_order_info.Number),0) sum from t_order_info " + 
				"left join t_order on t_order.id=t_order_info.OrderID " + 
				"left join t_commodity_info on t_commodity_info.ID = t_order_info.EquipmentModel" + 
				" where t_order.PageType = 0 and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? "
				+ "and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? and t_commodity_info.Model in ("+sql1+")" + 
				" group by year(t_order.DateOfSign),month(t_order.DateOfSign) ";
		DBUtil db = new DBUtil();
		
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		StringBuilder sb = new StringBuilder();
		for(int i=1;i<ls.size();i++) {
			if(i==ls.size()-1) {
				sb.append(ls.get(i).get("sum").toString());
			}else{
				sb.append(ls.get(i).get("sum").toString()+",");
			}
		}
		return sb.toString();
	}

	public String getSalesValueStringByProductCategory(String productCategory,String startTime,String endTime) {
		String sql ="select year(t_order.DateOfSign),month(t_order.DateOfSign),COALESCE(SUM(t_order_info.Number),0) sum from t_order_info " + 
				"left join t_order on t_order.id=t_order_info.OrderID " + 
//				"left join t_equipment on t_equipment.ID = t_order_info.EquipmentModel " + 
				"left join t_commodity_info on t_commodity_info.ID=t_order_info.EquipmentModel " + 
				"where t_order.PageType = 0 and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? and "
				+ "DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? and t_commodity_info.ProductCategory=? " + 
				" group by year(t_order.DateOfSign),month(t_order.DateOfSign) ";
		DBUtil db = new DBUtil();
		Object[] param = new Object[] {startTime,endTime,productCategory};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		StringBuilder sb = new StringBuilder();
		for(int i=1;i<ls.size();i++) {
			if(i==ls.size()-1) {
				sb.append(ls.get(i).get("sum").toString());
			}else{
				sb.append(ls.get(i).get("sum").toString()+",");
			}
		}
		return sb.toString();
	}

	public void insertRaderData(String Name,double priceValueP, double numberValueP, double salesValueP, double clientValueP,
			double valueValueP, String salesValueString,String category) {
		DBUtil db = new DBUtil();
		String sql = "insert into t_radar (Name,UnitPrice,Quantity,MonthlySales,Client,Worth,SalesValueString,Category) values (?,?,?,?,?,?,?,?)";
		Object[] param = new Object[] { Name, priceValueP, numberValueP, salesValueP,clientValueP, valueValueP, salesValueString,category };
		db.executeUpdate(sql, param);
		
	}
	
	public String getCategoryByModel(String model){
		DBUtil db = new DBUtil();
		String sql = "select ProductCategory from t_commodity_info where Model = ?";
		Object[] param = {model};
		return db.QueryToList(sql, param).get(1).get("ProductCategory").toString();
		
	}

	public double getSaleValuesByModel(String model,String startTime,String endTime) {
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[] {startTime,endTime,model};
		String sql = "select COUNT(*) std from (select COALESCE(SUM(t_order_info.Number),0) sum from t_order_info "
				+ "left join t_order on t_order.id=t_order_info.OrderID left join t_commodity_info "
				+ "on t_commodity_info.ID = t_order_info.EquipmentModel where t_order.PageType = 0 "
				+ "and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? "
				+ "and t_commodity_info.Model= ? "
				+ "group by year(t_order.DateOfSign),month(t_order.DateOfSign)) a";
		Object result = db.QueryToList(sql, parameter).get(1).get("std");
		String result2 = result == null || result.toString().equals("--") ? "0" : result.toString();
		return Double.parseDouble(result2);
	}

	public double getNumberValueByModel(String model,String startTime,String endTime) {
		DBUtil db = new DBUtil();
		Object[] parameter = null;
		String sql = "select sum(t_order_info.Number) sum from " + 
					"t_order_info join t_order on t_order_info.OrderID = t_order.ID where t_order.PageType = 0 and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? and t_order_info.EquipmentModel in " + 
					"(select t_commodity_info.ID from t_commodity_info where t_commodity_info.Model =?) " ;
		parameter = new Object[] {startTime,endTime, model };
		Object b = db.QueryToList(sql, parameter).get(1).get("sum");
		String a = b == null ? "0" : b.toString();
		return Double.parseDouble(a.equals("--") ? "0" : a);
	}

	public double getNumberValueByModel(List<String> list,String startTime,String endTime) {
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[list.size()+2];
		parameter[0] = startTime;
		parameter[1] = endTime;
		String sql1 = "";
		for (int i = 0; i < list.size(); i++) {
			parameter[i+2] = list.get(i);
			if (i == 0) {
				sql1 += "?";
			} else {
				sql1 += ",?";
			}
		}
		String sql = "select sum(t_order_info.Number) sum from " + 
				"t_order_info join t_order on t_order_info.OrderID = t_order.ID where t_order.PageType = 0 "
				+ "and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= ? and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= ? and t_order_info.EquipmentModel in " + 
				"(select t_commodity_info.ID from t_commodity_info where t_commodity_info.Model in ("+sql1+"))";
		Object b = db.QueryToList(sql, parameter).get(1).get("sum");
		String a = b == null ? "0" : b.toString();
		return Double.parseDouble(a.equals("--") ? "0" : a);
	}

	public int getCountByOne(String type1, String contect1) {
		String Type = customerClassify_MAP.get(type1);
		String sql = "select count(*) count from t_customer where "+Type+" like ?";
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[] {"%"+contect1+"%"};
		return Integer.parseInt(db.QueryToList(sql, parameter).get(1).get("count").toString());
	}

	public List<Map<String, Object>> getcustomersByOne(String type1, String contect1, int currentPage) {
		String Type = customerClassify_MAP.get(type1);
		String sql = "select *  from t_customer where "+Type+" like ? limit ?,10";
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[] {"%"+contect1+"%",(currentPage-1)*10};
		return db.QueryToList(sql, parameter);
	}

	public int getCountByTwo(String type1, String contect1, String type2, String contect2) {
		String Type1 = customerClassify_MAP.get(type1);
		String Type2 = customerClassify_MAP.get(type1);
		String sql = "select count(*) count from t_customer where "+Type1+" like ? and "+Type2+" like ?";
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[] {"%"+contect1+"%","%"+contect2+"%"};
		return Integer.parseInt(db.QueryToList(sql, parameter).get(1).get("count").toString());
	}

	public List<Map<String, Object>> getcustomersByTwo(String type1, String contect1, String type2, String contect2,
			int currentPage) {
		String Type1 = customerClassify_MAP.get(type1);
		String Type2 = customerClassify_MAP.get(type2);
		String sql = "select *  from t_customer where "+Type1+" like ? and "+Type2+" like ? limit ?,10";
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[] {"%"+contect1+"%","%"+contect2+"%",(currentPage-1)*10};
		return db.QueryToList(sql, parameter);
	}
	
	public List<Map<String,Object>> getAllQuoteNumber(String content){
		String sql = "select ID,Number from t_quote_system where Number like ?";
		DBUtil db = new DBUtil();
		Object[] param = new Object[]{"%"+content+"%"};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public List<Map<String,Object>> getCommodityInfo(int id){
		String sql = "SELECT t_commodity_info.ID CommodityID,t_commodity_info.Model EquipmentModel,"
				+ "t_commodity_info.CommodityName Remarks,t_commodity_temp.Qty Number FROM "
				+ "t_quote_system LEFT JOIN t_commodity_temp ON "
				+ "t_quote_system.ID=t_commodity_temp.QuoteID LEFT JOIN t_commodity_info ON "
				+ "t_commodity_info.ID=t_commodity_temp.Commodity WHERE t_quote_system.ID=?";
		DBUtil db = new DBUtil();
		Object[] param = new Object[]{id};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;
		
	}
	public List<Map<String, Object>> getEquipmentByName(String equipment){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select * from t_commodity_info where Model like ?"; 
		Object[] parameter = new Object[]{"%"+equipment+"%"};


		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	public List<Map<String, Object>> getNumberValueByRadar(String model){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select Quantity from t_radar where Name = ?"; 
		Object[] parameter = new Object[]{model};
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	public List<Map<String, Object>> queryQuoteNumber(String number){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql = "select ID from t_quote_system where Number=?";
		Object[] parameter = new Object[]{number};
		ls = db.QueryToList(sql, parameter);
		return ls;
		
	}
	
	public List<Map<String, Object>> GetModelByPage(String productCategory,String model,String column, Page page) {

		DBUtil db = new DBUtil();
		Object[] param = null;
		if(column.equals("")){
			column = "Sum";
		}
		String sql = "select Model,UnitPrice,ProductCategory,COALESCE(Year_2016,0) Year_2016,COALESCE(Year_2017,0) Year_2017,COALESCE(Year_2018,0) Year_2018,"
				+ "COALESCE(Year_2016,0)+COALESCE(Year_2017,0)+COALESCE(Year_2018,0) Sum  FROM"
				+ " (select t_sale_volume.Model,CONVERT(UnitPrice,DECIMAL(10,2)) UnitPrice,ProductCategory,Year_2016,Year_2017,m.Sum Year_2018 from t_sale_volume LEFT JOIN"
				+ " (select  SUM(COALESCE(t_order_info.Number,0)) Sum,Model from t_commodity_info LEFT JOIN t_order_info on t_commodity_info.ID = t_order_info.EquipmentModel"
				+ " LEFT JOIN t_order ON t_order_info.OrderID = t_order.ID  WHERE DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= '2018-01-01' and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= '2018-12-31'"
				+ " AND t_order.PageType = '0'  GROUP BY t_commodity_info.Model)m on t_sale_volume.Model = m.Model UNION"
				+ " select n.Model,CONVERT(n.UnitPrice,DECIMAL(10,2)) UnitPrice,n.ProductCategory,t_sale_volume.year_2016,t_sale_volume.Year_2017,n.Sum Year_2018 from t_sale_volume RIGHT JOIN"
				+ " (select  Model,CostPrice*1.5 UnitPrice,ProductCategory,SUM(COALESCE(t_order_info.Number,0)) Sum from t_commodity_info LEFT JOIN t_order_info on t_commodity_info.ID = t_order_info.EquipmentModel"
				+ " LEFT JOIN t_order ON t_order_info.OrderID = t_order.ID  WHERE DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= '2018-01-01' and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= '2018-12-31'"
				+ " AND t_order.PageType = '0'  GROUP BY t_commodity_info.Model)n on t_sale_volume.Model = n.Model)z ";
		if(!productCategory.equals("")){
			if(productCategory.equals("探针台")){
				sql = "select Classify Model,MAX(UnitPrice) UnitPrice,ProductCategory,SUM(COALESCE(Year_2016,0)) Year_2016,SUM(COALESCE(Year_2017,0)) Year_2017,SUM(COALESCE(Year_2018,0)) Year_2018,SUM(COALESCE(Year_2016,0)+COALESCE(Year_2017,0)+COALESCE(Year_2018,0)) Sum  FROM"
					+ " (select t_sale_volume.Model,CONVERT(UnitPrice,DECIMAL(10,2)) UnitPrice,ProductCategory,COALESCE(Classify,t_sale_volume.Model) Classify,Year_2016,Year_2017,m.Sum Year_2018 from t_sale_volume LEFT JOIN" 
					+ " (select  SUM(COALESCE(t_order_info.Number,0)) Sum,Model from t_commodity_info LEFT JOIN t_order_info on t_commodity_info.ID = t_order_info.EquipmentModel"
					+ " LEFT JOIN t_order ON t_order_info.OrderID = t_order.ID  WHERE DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= '2018-01-01' and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= '2018-12-31'" 
					+ " AND t_order.PageType = '0'  GROUP BY t_commodity_info.Model)m on t_sale_volume.Model = m.Model UNION"
					+ " select n.Model,CONVERT(n.UnitPrice,DECIMAL(10,2)) UnitPrice,n.ProductCategory,COALESCE(n.Classify,n.Model) Classify,t_sale_volume.year_2016,t_sale_volume.Year_2017,n.Sum Year_2018 from t_sale_volume RIGHT JOIN" 
					+ " (select  Model,CostPrice*1.5 UnitPrice,ProductCategory,Classify,SUM(COALESCE(t_order_info.Number,0)) Sum from t_commodity_info LEFT JOIN t_order_info on t_commodity_info.ID = t_order_info.EquipmentModel"
					+ " LEFT JOIN t_order ON t_order_info.OrderID = t_order.ID  WHERE DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= '2018-01-01' and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= '2018-12-31'" 
					+ " AND t_order.PageType = '0'  GROUP BY t_commodity_info.Model)n on t_sale_volume.Model = n.Model)z WHERE ProductCategory = '探针台'"
					+ " GROUP BY Classify ";
				param = new Object[]{(page.getCurrentPage() - 1) * page.getRows(), page.getRows()};
			}else{
				sql += "WHERE ProductCategory = ? ";
				param = new Object[]{productCategory,(page.getCurrentPage() - 1) * page.getRows(), page.getRows()};
			}
		}else if(!model.equals("")){
			sql += "WHERE Model like ? ";
			param = new Object[]{"%"+model+"%",(page.getCurrentPage() - 1) * page.getRows(), page.getRows()};
		}else{
			param = new Object[]{(page.getCurrentPage() - 1) * page.getRows(), page.getRows()};
		}
		sql += "ORDER BY " +column+" DESC limit ?,?";
		return db.QueryToList(sql, param);
	
	}
	
	public int getModelCounts(String productCategory,String model){
		

		Object[] param = null;
		DBUtil db = new DBUtil();
		String sql = "select COUNT(Model) Count FROM"
				+ " (select t_sale_volume.Model,CONVERT(UnitPrice,DECIMAL(10,2)) UnitPrice,ProductCategory,Year_2016,Year_2017,m.Sum Year_2018 from t_sale_volume LEFT JOIN"
				+ " (select  SUM(COALESCE(t_order_info.Number,0)) Sum,Model from t_commodity_info LEFT JOIN t_order_info on t_commodity_info.ID = t_order_info.EquipmentModel"
				+ " LEFT JOIN t_order ON t_order_info.OrderID = t_order.ID  WHERE DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= '2018-01-01' and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= '2018-12-31'"
				+ " AND t_order.PageType = '0'  GROUP BY t_commodity_info.Model)m on t_sale_volume.Model = m.Model UNION"
				+ " select n.Model,CONVERT(n.UnitPrice,DECIMAL(10,2)) UnitPrice,n.ProductCategory,t_sale_volume.year_2016,t_sale_volume.Year_2017,n.Sum Year_2018 from t_sale_volume RIGHT JOIN"
				+ " (select  Model,CostPrice*1.5 UnitPrice,ProductCategory,SUM(COALESCE(t_order_info.Number,0)) Sum from t_commodity_info LEFT JOIN t_order_info on t_commodity_info.ID = t_order_info.EquipmentModel"
				+ " LEFT JOIN t_order ON t_order_info.OrderID = t_order.ID  WHERE DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= '2018-01-01' and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= '2018-12-31'"
				+ " AND t_order.PageType = '0'  GROUP BY t_commodity_info.Model)n on t_sale_volume.Model = n.Model)z ";
		if(!productCategory.equals("")){
			if(productCategory.equals("探针台")){
				sql = "SELECT COUNT(Model) Count FROM (select Model FROM"
					+ " (select t_sale_volume.Model,CONVERT(UnitPrice,DECIMAL(10,2)) UnitPrice,ProductCategory,COALESCE(Classify,t_sale_volume.Model) Classify,Year_2016,Year_2017,m.Sum Year_2018 from t_sale_volume LEFT JOIN" 
					+ " (select  SUM(COALESCE(t_order_info.Number,0)) Sum,Model from t_commodity_info LEFT JOIN t_order_info on t_commodity_info.ID = t_order_info.EquipmentModel"
					+ " LEFT JOIN t_order ON t_order_info.OrderID = t_order.ID  WHERE DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= '2018-01-01' and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= '2018-12-31'" 
					+ " AND t_order.PageType = '0'  GROUP BY t_commodity_info.Model)m on t_sale_volume.Model = m.Model UNION"
					+ " select n.Model,CONVERT(n.UnitPrice,DECIMAL(10,2)) UnitPrice,n.ProductCategory,COALESCE(n.Classify,n.Model) Classify,t_sale_volume.year_2016,t_sale_volume.Year_2017,n.Sum Year_2018 from t_sale_volume RIGHT JOIN" 
					+ " (select  Model,CostPrice*1.5 UnitPrice,ProductCategory,Classify,SUM(COALESCE(t_order_info.Number,0)) Sum from t_commodity_info LEFT JOIN t_order_info on t_commodity_info.ID = t_order_info.EquipmentModel"
					+ " LEFT JOIN t_order ON t_order_info.OrderID = t_order.ID  WHERE DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= '2018-01-01' and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= '2018-12-31'" 
					+ " AND t_order.PageType = '0'  GROUP BY t_commodity_info.Model)n on t_sale_volume.Model = n.Model)z WHERE ProductCategory = '探针台'"
					+ " GROUP BY Classify)x ";
				
			}else{
				sql += "WHERE ProductCategory = ? ";
				param = new Object[]{productCategory};
			}
		}else if(!model.equals("")){
			sql += "WHERE Model like ? ";
			param = new Object[]{"%"+model+"%"};
		}
		
		List<Map<String, Object>> list = db.QueryToList(sql, param);
		return Integer.parseInt(list.get(1).get("Count").toString());
		
	}
	

	
}
