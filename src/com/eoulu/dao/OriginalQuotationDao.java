package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Keysight;
import com.eoulu.entity.OriginalQuotation;
import com.eoulu.util.DBUtil;

public class OriginalQuotationDao {
	
private static Map<String, String> map;
	
	static{
		map = new HashMap<>();
		map.put("客户名称", "t_customer.CustomerName");
		map.put("联系人", "t_customer.Contact");
		map.put("机台型号", "Model");
		map.put("chuck类型","ChuckType");
		map.put("温度要求","TemperatureRequirement");
		map.put("显微镜","Microscope");
	
	}
	public List<Map<String, Object>> getDataByPage(Page page,String type,String column1,String content1,String column2,String content2){
		DBUtil dbUtil = new DBUtil();
		String sql = "select t_original_quotation.ID,CustomerID,t_customer.CustomerName,t_customer.CustomerDepartment,"
				+ "t_customer.Contact,Model,ChuckType,TemperatureRequirement,Microscope,VersionNumber,QuoteTotal,"
				+ "FilePath,Remarks from t_original_quotation "
				+ "left join t_customer on t_original_quotation.CustomerID=t_customer.ID ";
				
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		if(type.equals("singleSelect")){
			sql += "where "+map.get(column1)+" like ?";
			parameter = new Object[]{"%"+content1+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}else if(type.equals("mixSelect")){
			sql += "where "+map.get(column1)+" like ? and "+map.get(column2)+" like ?";
			parameter = new Object[]{"%"+content1+"%","%"+content2+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}
		sql += " order by t_original_quotation.ID desc limit ?,?";
		
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return list;
	}
	
	public int getCounts(String type,String column1,String content1,String column2,String content2){
		DBUtil dbUtil = new DBUtil();
		String sql = "select COUNT(t_original_quotation.ID) Count from t_original_quotation "
				+ "left join t_customer on t_original_quotation.CustomerID=t_customer.ID ";
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
	
	public boolean insert(OriginalQuotation originalQuotation){	
		DBUtil dbUtil = new DBUtil();
		String sql = "insert into t_original_quotation(CustomerID,Model,ChuckType,TemperatureRequirement,Microscope,VersionNumber,QuoteTotal,"
				+ "FilePath,Remarks) values(?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[9];
		param[0] = originalQuotation.getCustomerID();
		param[1] = originalQuotation.getModel();
		param[2] = originalQuotation.getChuckType();
		param[3] = originalQuotation.getTemperatureRequirement();
		param[4] = originalQuotation.getMicroscope();
		param[5] = originalQuotation.getVersionNumber();
		param[6] = originalQuotation.getQuoteTotal();
		param[7] = originalQuotation.getFilePath();
		param[8] = originalQuotation.getRemarks();
	
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
	}
	
	public boolean update(OriginalQuotation originalQuotation){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_original_quotation set CustomerID=?,Model=?,ChuckType=?,TemperatureRequirement=?,Microscope=?,VersionNumber=?,QuoteTotal=?,"
				+ "FilePath=?,Remarks=? where t_original_quotation.ID=?";
		Object[] param = new Object[10];
		param[0] = originalQuotation.getCustomerID();
		param[1] = originalQuotation.getModel();
		param[2] = originalQuotation.getChuckType();
		param[3] = originalQuotation.getTemperatureRequirement();
		param[4] = originalQuotation.getMicroscope();
		param[5] = originalQuotation.getVersionNumber();
		param[6] = originalQuotation.getQuoteTotal();
		param[7] = originalQuotation.getFilePath();
		param[8] = originalQuotation.getRemarks();
		param[9] = originalQuotation.getID();
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
		
	}
	
	
	
	

}
