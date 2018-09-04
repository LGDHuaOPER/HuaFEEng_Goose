package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Keysight;
import com.eoulu.util.DBUtil;

public class KeysightDao {
	private static Map<String, String> map;
	
	static{
		map = new HashMap<>();
		map.put("Opportunity Create Date", "t_keysight.OpportunityCreateDate");
		map.put("CustomerName", "t_customer.CustomerName");
		map.put("Deal Status", "t_keysight.DealStatus");
		map.put("Keysight Model Number-Option.","t_commodity_info.Model");
		map.put("Forecasted Order Date","t_keysight.OrderDate");
		map.put("Customer Attn","t_customer.Contact");
		map.put("City","t_customer.City");
	
	}
	
	
	
	public boolean insert(Keysight keysight){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		DBUtil dbUtil = new DBUtil();
		String sql = "insert into t_keysight(PartnerID,OpportunityCreateDate,DealID,CustomerID,"
				+ "StreetAddress,PostalCode,CountryCode,DealStatus,WinProbability,KeysightReseller,ShipToLocation,"
				+ "KeysightName,Line,CommodityID,DealValue,Qty,OrderDate,SalesOrder,BookingDate,CurrencyCode,Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[21];
		param[0] = keysight.getPartnerID();
		param[1] = simpleDateFormat.format(new Date());;
		param[2] = keysight.getDealID();
		param[3] = keysight.getCustomerID();
		param[4] = keysight.getStreetAddress();
		param[5] = keysight.getPostalCode();
		param[6] = keysight.getCountryCode();
		param[7] = keysight.getDealStatus();
		param[8] = keysight.getWinProbability();
		param[9] = keysight.getKeysightReseller();
		param[10] = keysight.getShipToLocation();
		param[11] = keysight.getKeysightName();
		param[12] = keysight.getLine();
		param[13] = keysight.getCommodityID();
		param[14] = keysight.getDealValue();
		param[15] = keysight.getQty();
		param[16] = keysight.getOrderDate();
		param[17] = keysight.getSalesOrder();
		param[18] = keysight.getBookingDate();
		param[19] = keysight.getCurrencyCode();
		param[20] = keysight.getStatus();
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
	}
	
	public boolean update(Keysight keysight){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_keysight set PartnerID = ?,DealID=?,CustomerID=?,"
				+ "StreetAddress=?,PostalCode=?,CountryCode=?,DealStatus=?,WinProbability=?,KeysightReseller=?,ShipToLocation=?,"
				+ "KeysightName=?,Line=?,CommodityID=?,DealValue=?,Qty=?,OrderDate=?,SalesOrder=?,BookingDate=?,CurrencyCode=?,Status=? "
				+ "where t_keysight.ID=?";
		Object[] param = new Object[21];
		param[0] = keysight.getPartnerID();
		param[1] = keysight.getDealID();
		param[2] = keysight.getCustomerID();
		param[3] = keysight.getStreetAddress();
		param[4] = keysight.getPostalCode();
		param[5] = keysight.getCountryCode();
		param[6] = keysight.getDealStatus();
		param[7] = keysight.getWinProbability();
		param[8] = keysight.getKeysightReseller();
		param[9] = keysight.getShipToLocation();
		param[10] = keysight.getKeysightName();
		param[11] = keysight.getLine();
		param[12] = keysight.getCommodityID();
		param[13] = keysight.getDealValue();
		param[14] = keysight.getQty();
		param[15] = keysight.getOrderDate();
		param[16] = keysight.getSalesOrder();
		param[17] = keysight.getBookingDate();
		param[18] = keysight.getCurrencyCode();
		param[19] = keysight.getStatus();
		param[20] = keysight.getID();
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
		
	}
	public List<Map<String, Object>> getDataByPage(Page page,String type,String column1,String content1,String column2,String content2){
		DBUtil dbUtil = new DBUtil();
		String sql = "select t_keysight.ID,PartnerID,OpportunityCreateDate,DealID,DealValue,CustomerID,t_customer.CustomerName,"
				+ "t_customer.City,t_customer.Area,t_customer.Contact,t_customer.ContactInfo1,t_customer.Email,"
				+ "StreetAddress,PostalCode,CountryCode,DealStatus,WinProbability,KeysightReseller,ShipToLocation,"
				+ "KeysightName,Line,CommodityID,t_commodity_info.Model,t_commodity_info.SellerPriceOne,"
				+ "Qty,OrderDate,SalesOrder,BookingDate,CurrencyCode,Status from t_keysight "
				+ "left join t_customer on t_keysight.CustomerID=t_customer.ID "
				+ "left join t_commodity_info on t_keysight.CommodityID=t_commodity_info.ID " ;
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		if(type.equals("singleSelect")){
			sql += "where "+map.get(column1)+" like ?";
			parameter = new Object[]{"%"+content1+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}else if(type.equals("mixSelect")){
			sql += "where "+map.get(column1)+" like ? and "+map.get(column2)+" like ?";
			parameter = new Object[]{"%"+content1+"%","%"+content2+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}
		sql += " order by t_keysight.ID desc limit ?,?";
		
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return list;
	}
	
	public int getCounts(String type,String column1,String content1,String column2,String content2){
		DBUtil dbUtil = new DBUtil();
		String sql = "select COUNT(t_keysight.ID) Count from t_keysight "
				+ "left join t_customer on t_keysight.CustomerID=t_customer.ID "
				+ "left join t_commodity_info on t_keysight.CommodityID=t_commodity_info.ID ";
		Object[] parameter = null;
		if(type.equals("singleSelect")){
			sql += "where "+map.get(column1)+" like ?";
			parameter = new Object[]{"%"+content1+"%"};
		}else if(type.equals("mixSelect")){
			sql += "where "+map.get(column1)+" like ? and "+map.get(column1)+" like ?";
			parameter = new Object[]{"%"+content1+"%","%"+content2+"%"};
		}
	
		
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return Integer.parseInt(list.get(1).get("Count").toString());
	}
	
	public List<Map<String, Object>> getExcelData(){
		DBUtil dbUtil = new DBUtil();
		String sql = "select t_keysight.ID,PartnerID,OpportunityCreateDate,DealID,DealValue,CustomerID,t_customer.CustomerName,"
				+ "t_customer.City,t_customer.Area,t_customer.Contact,t_customer.ContactInfo1,t_customer.Email,"
				+ "StreetAddress,PostalCode,CountryCode,DealStatus,WinProbability,KeysightReseller,ShipToLocation,"
				+ "KeysightName,Line,CommodityID,t_commodity_info.Model,t_commodity_info.SellerPriceOne,"
				+ "Qty,OrderDate,SalesOrder,BookingDate,CurrencyCode,Status from t_keysight "
				+ "left join t_customer on t_keysight.CustomerID=t_customer.ID "
				+ "left join t_commodity_info on t_keysight.CommodityID=t_commodity_info.ID " ;
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, null);
		return list;
		
	}
	


}
