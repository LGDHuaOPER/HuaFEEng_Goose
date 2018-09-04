package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.entity.DeliveryAdvice;
import com.eoulu.entity.QuoteDelivery;
import com.eoulu.util.DBUtil;

public class DeliveryAdviceDao {
	
	public List<Map<String,Object>> getAdvice(int quoteID){
		DBUtil db = new DBUtil();
		String sql = "select * from t_quote_delivery_advice where QuoteID=?";
		List<Map<String,Object>> ls = db.QueryToList(sql, new Object[]{quoteID});
		return ls;
	}

	public boolean insert(DeliveryAdvice advice){
		DBUtil db = new DBUtil();
		String sql = "insert into t_quote_delivery_advice (PackingFile,PONO,SONO,OriginService,Company,Contact,Email,LinkTel,InstallPlace,QuoteID) values (?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[]{advice.getPackingFile(),advice.getPONO(),advice.getSONO(),advice.getOriginService(),advice.getCompany(),
				advice.getContact(),advice.getEmail(),advice.getLinkTel(),advice.getInstallPlace(),advice.getQuoteID()};
		return db.executeUpdate(sql, param)>0?true:false;
	}
	public boolean update(DeliveryAdvice advice){
		DBUtil db = new DBUtil();
		String sql = "update t_quote_delivery_advice set PackingFile=?,PONO=?,SONO=?,OriginService=?,Company=?,Contact=?,Email=?,LinkTel=?,InstallPlace=? where ID=?";
		Object[] param = new Object[]{advice.getPackingFile(),advice.getPONO(),advice.getSONO(),advice.getOriginService(),advice.getCompany(),
				advice.getContact(),advice.getEmail(),advice.getLinkTel(),advice.getInstallPlace(),advice.getID()};
		return db.executeUpdate(sql, param)>0?true:false;
	}
	public List<Map<String,Object>> getAdviceList(int quoteID){
		DBUtil db = new DBUtil();
		String sql = "SELECT ID,Model,Description,Quantity,Remarks from t_quote_delivery_advice_item where  QuoteID=?";
		List<Map<String,Object>> ls = db.QueryToList(sql, new Object[]{quoteID});
		return ls;
	}
	public String[] getAdviceItem(int quoteID){
		DBUtil db = new DBUtil();
		String sql = "SELECT ID  from t_quote_delivery_advice_item where  QuoteID=?";
		List<Map<String,Object>> ls = db.QueryToList(sql, new Object[]{quoteID});
		String[] result = new String[ls.size()-1];
		for(int i=1;i<ls.size();i++){
			result[i-1]=ls.get(i).get("ID").toString();
		}
		return result;
	}
	public List<Map<String,Object>> getDelivery(int quoteID){
		DBUtil db = new DBUtil();
		String sql = "SELECT Model,Description,Quantity,Remarks from t_quote_delivery where  QuoteID=?";
		List<Map<String,Object>> ls = db.QueryToList(sql, new Object[]{quoteID});
		return ls;
	}
	
	public boolean insertDelivery(QuoteDelivery delivery) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into t_quote_delivery_advice_item (Model,Description,Quantity,Remarks,OperatingTime,QuoteID) values (?,?,?,?,?,?)";
		Object[] param = new Object[6];
		param[0] = delivery.getModel();
		param[1] = delivery.getDescription();
		param[2] = delivery.getQuantity();
		param[3] = delivery.getRemarks();
		param[4] = df.format(new Date());
		param[5] = delivery.getQuoteID();
		return  db.executeUpdate(sql, param)>0?true:false;
	}

	public boolean upadteDelivery(QuoteDelivery delivery) {
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_quote_delivery_advice_item set Model=?,Description=?,Quantity=?,Remarks=?,OperatingTime=? where ID=? ";
		Object[] param = new Object[6];
		param[0] = delivery.getModel();
		param[1] = delivery.getDescription();
		param[2] = delivery.getQuantity();
		param[3] = delivery.getRemarks();
		param[4] = df.format(new Date());
		param[5] = delivery.getID();
		return  db.executeUpdate(sql, param)>0?true:false;
	}
	public boolean delete(int id){
		DBUtil db = new DBUtil();
		String sql = "delete from t_quote_delivery_advice_item where ID=?";
		return db.executeUpdate(sql, new Object[]{id})>0?true:false;
	}
	
	public String getContractFile(int quoteID){
		DBUtil db = new DBUtil();
		String sql = "SELECT t_order.ContractPath,t_order.TechnologyPath FROM t_quote_request LEFT JOIN t_order ON t_quote_request.ContractNO=t_order.ContractNo WHERE t_quote_request.QuoteID=?";
		List<Map<String,Object>> ls = db.QueryToList(sql, new Object[]{quoteID});
		String contractFile = ls.size()>1?ls.get(1).get("ContractPath").toString():"";
		contractFile = contractFile.equals("--")?"":contractFile;
		String technologyFile = ls.size()>1?ls.get(1).get("TechnologyPath").toString():"";
		technologyFile = technologyFile.equals("--")?"":technologyFile;
		return contractFile.equals("")?technologyFile:contractFile+";"+technologyFile;
	}
	public boolean updateMailStatus(int quoteID){
		DBUtil db = new DBUtil();
		String sql = "update t_quote_system set DeliveryAdvice=? where ID=? ";
		return db.executeUpdate(sql, new Object[]{"yes",quoteID})>0?true:false;
	}
}
