package com.eoulu.dao;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.entity.CommodityInfo;
import com.eoulu.entity.CommodityMail;
import com.eoulu.entity.SupplierInfo;
import com.eoulu.util.DBUtil;

public class SupplierInfoDao {

	//供应商管理
	public int insert(SupplierInfo info){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		String sql = "insert into t_supplier_info (Commodity,PurchaseModel,PurchaseLink,PurchaseContact,PurchaseEmail,PaymentCondition,ValidTime,OperatingTime,AddTime) values (?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[9];
		param[0] = info.getCommodity();
		param[1] = info.getModel();
		param[2] = info.getPurchaseLink();
		param[3] = info.getPurchaseContact();
		param[4] = info.getPurchaseEmail();
		param[5] = info.getPaymentCondition();
//		param[6] = info.getPurchaseRecord();
//		param[7] = info.getPurchaseRecordPath();
//		param[6] = info.getQuoteTime();
		param[6] = info.getValidTime();
		param[7] = df.format(new Date());
		param[8] = df.format(new Date());
		int id = 0;
		try {
			Object result = db.insertGetId(sql, param);
			id =  Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		int count =db.executeUpdate(sql, param);
//		boolean flag = false;
//		if(count>1){
//			flag = true;
//		}
		return id;
	}
	
	public boolean insertFile(SupplierInfo info){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		String sql = "insert into t_supplier_info (Commodity,PurchaseRecord,PurchaseRecordPath,OperatingTime,AddTime) values (?,?,?,?,?)";
		Object[] param = new Object[5];
		param[0] = info.getCommodity();
		param[1] = info.getPurchaseRecord();
		param[2] = info.getPurchaseRecordPath();
		param[3] = df.format(new Date());
		param[4] = df.format(new Date());
		
		int count =db.executeUpdate(sql, param);
		boolean flag = false;
		if(count>=1){
			flag = true;
		}
		return flag;
	}
	public boolean update(SupplierInfo info){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		String sql = "update t_supplier_info set PurchaseModel=?,PurchaseLink=?,PurchaseContact=?,PurchaseEmail=?,PaymentCondition=?,ValidTime=?,OperatingTime=? where Commodity=?";
		Object[] param = new Object[8];
		param[0] = info.getModel();
		param[1] = info.getPurchaseLink();
		param[2] = info.getPurchaseContact();
		param[3] = info.getPurchaseEmail();
		param[4] = info.getPaymentCondition();
//		param[5] = info.getPurchaseRecord();
//		param[6] = info.getPurchaseRecordPath();
//		param[8] = info.getQuoteTime();
		param[5] = info.getValidTime();
		param[6] = df.format(new Date());
		param[7] = info.getCommodity();
		System.out.println(info.getCommodity());
		int count =db.executeUpdate(sql, param);
		boolean flag = false;
		if(count>=1){
			flag = true;
		}
		return flag;
	}
	public boolean updateFile(SupplierInfo info){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		String sql = "update t_supplier_info set PurchaseRecord=?,PurchaseRecordPath=?,OperatingTime=? where Commodity=?";
		Object[] param = new Object[4];
		param[0] = info.getPurchaseRecord();
		param[1] = info.getPurchaseRecordPath();
		param[2] = df.format(new Date());
		param[3] = info.getCommodity();
		
		int count =db.executeUpdate(sql, param);
		boolean flag = false;
		if(count>=1){
			flag = true;
		}
		return flag;
	}
	//获取文件路径
	public String getPath(int id){
		DBUtil db = new DBUtil();
		String sql = "select PurchaseRecordPath from t_supplier_info where Commodity=?";
		Object[] param = new Object[]{id};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		String path = "";
		if(ls.size()>1){
			path = ls.get(1).get("PurchaseRecordPath").toString();
		}
		return path;
	}
	//获取文件名称
		public String getFileName(int id){
			DBUtil db = new DBUtil();
			String sql = "select PurchaseRecord from t_supplier_info where Commodity=?";
			Object[] param = new Object[]{id};
			List<Map<String,Object>> ls = db.QueryToList(sql, param);
			String path = "";
			if(ls.size()>1){
				path = ls.get(1).get("PurchaseRecord").toString();
			}
			return path;
		}
	//邮件
	public boolean insertMail(CommodityMail mail){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		String sql = "insert into t_commodity_mail (Commodity,Consignee,CCList,`Subject`,Content,OperatingTime) values (?,?,?,?,?,?)";
		Object[] param = new Object[6];
		param[0] = mail.getCommodity();
		param[1] = mail.getConsignee();
		param[2] = mail.getCCList();
		param[3] = mail.getSubject();
		param[4] = mail.getContent();
		param[5] = df.format(new Date());
		int count = db.executeUpdate(sql, param);
		boolean flag = false;
		if(count>=1){
			flag = true;
		}
			
		return flag;
	}
	
	public boolean updateMail(CommodityMail mail){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		String sql = "update t_commodity_mail "
				+ "set Commodity=?,Consignee=?,CCList=?,`Subject`=?,Content=?,OperatingTime=? where Commodity=?";
		Object[] param = new Object[7];
		param[0] = mail.getCommodity();
		param[1] = mail.getConsignee();
		param[2] = mail.getCCList();
		param[3] = mail.getSubject();
		param[4] = mail.getContent();
		param[5] = df.format(new Date());
		param[6] = mail.getCommodity();
		int count = db.executeUpdate(sql, param);
		boolean flag = false;
		if(count>=1){
			flag = true;
		}
			
		return flag;
	}
	//修改中获取邮件模板
	public List<Map<String,Object>> getModel(int id){
		DBUtil db = new DBUtil();
		String sql = "select ID MailID,Consignee,CCList,`Subject`,Content from t_commodity_mail where Commodity=?";
		Object[] param = new Object[]{id};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;
	}
	public List<Map<String,Object>> getSupplier(int id){
		DBUtil db = new DBUtil();
		String sql = "select PurchaseModel,PurchaseLink,PurchaseContact,PurchaseEmail,PaymentCondition,ValidTime from t_supplier_info where Commodity=?";
		Object[] param = new Object[]{id};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;
	}
	
	
	public int insertCommodityInfo(CommodityInfo info) {
		int id = 0;
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into t_commodity_info  (CommodityName,Model,Unit,DeliveryPeriod,ProducingArea,"
				+ "CostPrice,DiscountCost,SellerPriceOne,SellerPriceTwo,SellerPriceThree,Supplier,"
				+ "OperatingTime,ProductCategory,QuoteTime,Item,ItemDescription) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		Object[] param = new Object[16];
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
		param[14] = info.getItem();
		param[15] = info.getItemDescription();
		try {
			Object result = db.insertGetId(sql, param);
			id =  Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	public boolean isExistModel(String model){
		boolean flag = false;
		DBUtil db = new DBUtil();
		String sql = "select * from t_commodity_info where Model=?";
		Object[] param = new Object[]{model};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		if(ls.size()>1){
			flag = true;
		}
		return flag;
	}
}
