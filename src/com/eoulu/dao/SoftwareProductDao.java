package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.SoftwareProduct;
import com.eoulu.util.DBUtil;

public class SoftwareProductDao {
	
	public List<Map<String,Object>> getAllData(Page page){
		String sql = "select  FORMAT((Count*HourlyWage*Cycle*PremiumIndex*MaintenanceIndex+TransportAllowance+AccommodationAllowance+MissionAllowance),2) Price,"
				+ "ID,PackageName,Model,Count,HourlyWage,PackageClassify,Cycle,Brand,PremiumIndex,MaintenanceIndex,"
				+ "TransportAllowance,AccommodationAllowance,MissionAllowance,Remarks from t_software_product order by"
				+ " ID desc limit ?,?";
		Object[] param = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, param);
		return ls;
	}
	
	public int  getAllCounts(){
		String sql = "select Count(ID) from t_software_product ";
		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, null);
		int count = ls.size()>1?Integer.parseInt(ls.get(1).get("Count(ID)").toString()):0;
		return count;
	}
	
	public boolean insert(SoftwareProduct product){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "INSERT INTO t_software_product (PackageName,Model,Count,HourlyWage,PackageClassify,Cycle,Brand,PremiumIndex,MaintenanceIndex,TransportAllowance,MissionAllowance,AccommodationAllowance,Remarks,OperatingTime) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param =  new Object[14];
		param[0] = product.getPackageName();
		param[1] = product.getModel();
		param[2] = product.getCount();
		param[3] = product.getHourlyWage();
		param[4] = product.getPackageClassify();
		param[5] = product.getCycle();
		param[6] = product.getBrand();
		param[7] = product.getPremiumIndex();
		param[8] = product.getMaintenanceIndex();
		param[9] = product.getTransportAllowance();
		param[10] = product.getMissionAllowance();
		param[11] = product.getAccommodation();
		param[12] = product.getRemarks();
		param[13] = df.format(new Date());
		DBUtil db = new DBUtil();
		return db.executeUpdate(sql, param)>0?true:false;
	}
	
	public boolean update(SoftwareProduct product){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_software_product set PackageName=?,Model=?,Count=?,HourlyWage=?,PackageClassify=?,Cycle=?,Brand=?,"
				+ "PremiumIndex=?,MaintenanceIndex=?,TransportAllowance=?,MissionAllowance=?,AccommodationAllowance=?,Remarks=?,OperatingTime=? where ID=?";
		Object[] param =  new Object[15];
		param[0] = product.getPackageName();
		param[1] = product.getModel();
		param[2] = product.getCount();
		param[3] = product.getHourlyWage();
		param[4] = product.getPackageClassify();
		param[5] = product.getCycle();
		param[6] = product.getBrand();
		param[7] = product.getPremiumIndex();
		param[8] = product.getMaintenanceIndex();
		param[9] = product.getTransportAllowance();
		param[10] = product.getMissionAllowance();
		param[11] = product.getAccommodation();
		param[12] = product.getRemarks();
		param[13] = df.format(new Date());
		param[14] = product.getID();
		DBUtil db = new DBUtil();
		return db.executeUpdate(sql, param)>0?true:false;
	}
	
	public List<Map<String,Object>> getQueryResult(String content,Page page){
		String sql = "select (Count*HourlyWage*Cycle*PremiumIndex*MaintenanceIndex+TransportAllowance+AccommodationAllowance+MissionAllowance) Price,"
				+ "ID,PackageName,Model,Count,HourlyWage,PackageClassify,Cycle,Brand,PremiumIndex,MaintenanceIndex,"
				+ "TransportAllowance,AccommodationAllowance,MissionAllowance,Remarks from t_software_product WHERE PackageName LIKE ? OR Model LIKE ? OR Brand LIKE ? OR PackageClassify LIKE ? order by ID desc limit ?,?";
		Object[] param = new Object[]{"%"+content+"%","%"+content+"%","%"+content+"%","%"+content+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		
		return new DBUtil().QueryToList(sql, param);
	}
	
	public int getQueryCounts(String content){
		String sql = "select Count(ID) from t_software_product WHERE PackageName LIKE ? OR Model LIKE ? OR Brand LIKE ? OR PackageClassify LIKE ? ";
		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, new Object[]{"%"+content+"%","%"+content+"%","%"+content+"%","%"+content+"%"});
		int count = ls.size()>1?Integer.parseInt(ls.get(1).get("Count(ID)").toString()):0;
		return count;
	}
	
	public List<Map<String,Object>> getAllProductModel(String content,String column){
		String sql = "select ID,Model,PackageName,Remarks,FORMAT((Count*HourlyWage*Cycle*PremiumIndex*MaintenanceIndex+TransportAllowance+AccommodationAllowance+MissionAllowance),2) Price FROM t_software_product where ";
		if(column.equals("")){
			sql += "Model like ?";
		}else{
			sql += column + " like ?";
		}
		return new DBUtil().QueryToList(sql, new Object[]{"%"+content+"%"});
	}

}
