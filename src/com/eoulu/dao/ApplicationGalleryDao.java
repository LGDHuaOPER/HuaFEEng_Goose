package com.eoulu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.ProductDrawings;
import com.eoulu.entity.Scheme;
import com.eoulu.util.DBUtil;

public class ApplicationGalleryDao {
	
private static Map<String, String> map;
	
	static{
		map = new HashMap<>();
		map.put("应用类型", "ApplicationType");
		map.put("客户名称", "CustomerName");
		map.put("方案名称", "SchemeName");
		map.put("机台型号","MachineModel");
		map.put("制定时间","MakeTime");
		map.put("产品名称","ProductName");
		map.put("产品类型", "ProductType");
		map.put("适用机台", "Machine");
		map.put("版本号", "VersionNO");
		map.put("设计时间", "DesignTime");
		map.put("使用情况", "Usage");
		
	
	
	}
	
	public boolean addScheme(Scheme scheme){
		DBUtil dbUtil = new DBUtil();
		
		String sql = "insert into t_scheme(ApplicationType,CustomerName,SchemeName,MachineModel,"
				+ "MakeTime,CADdrawings,PDFdrawings,Remark) values(?,?,?,?,?,?,?,?)";
		
		Object[] param = new Object[8];
		param[0] = scheme.getApplicationType();
		param[1] = scheme.getCustomerName();
		param[2] = scheme.getSchemeName();
		param[3] = scheme.getMachineModel();
		param[4] = scheme.getMakeTime();
		param[5] = scheme.getCADdrawings();
		param[6] = scheme.getPDFdrawings();
		param[7] = scheme.getRemark();
		
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
	}
	
	public boolean updateScheme(Scheme scheme){
		
		DBUtil dbUtil = new DBUtil();
		
		String sql = "update t_scheme set ApplicationType=?,CustomerName=?,SchemeName=?,MachineModel=?,"
				+ "MakeTime=?,CADdrawings=?,PDFdrawings=?,Remark=? where ID=?";
		
		Object[] param = new Object[9];
		param[0] = scheme.getApplicationType();
		param[1] = scheme.getCustomerName();
		param[2] = scheme.getSchemeName();
		param[3] = scheme.getMachineModel();
		param[4] = scheme.getMakeTime();
		param[5] = scheme.getCADdrawings();
		param[6] = scheme.getPDFdrawings();
		param[7] = scheme.getRemark();
		param[8] = scheme.getID();
		
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
		
	}
	
	public List<Map<String, Object>> getSchemeByPage(Page page,String type,String column1,String content1,String column2,String content2){
		
		DBUtil dbUtil = new DBUtil();
		String sql = "select ID,ApplicationType,CustomerName,SchemeName,MachineModel,"
				+ "MakeTime,CADdrawings,PDFdrawings,Remark from t_scheme " ;
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		if(type.equals("singleSelect")){
			sql += "where "+map.get(column1)+" like ?";
			parameter = new Object[]{"%"+content1+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}else if(type.equals("mixSelect")){
			sql += "where "+map.get(column1)+" like ? and "+map.get(column2)+" like ?";
			parameter = new Object[]{"%"+content1+"%","%"+content2+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}
		sql += " order by t_scheme.ID desc limit ?,?";
		
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return list;
	}
	
	public int getSchemeCount(String type,String column1,String content1,String column2,String content2){
		DBUtil dbUtil = new DBUtil();
		String sql = "select COUNT(ID) Count from t_scheme " ;
		Object[] parameter = null;
		if(type.equals("singleSelect")){
			sql += "where "+map.get(column1)+" like ?";
			parameter = new Object[]{"%"+content1+"%"};
		}else if(type.equals("mixSelect")){
			sql += "where "+map.get(column1)+" like ? and "+map.get(column2)+" like ?";
			parameter = new Object[]{"%"+content1+"%","%"+content2+"%"};
		}
		return Integer.parseInt(dbUtil.QueryToList(sql, parameter).get(1).get("Count").toString());
	}
	
	public boolean addProduct(ProductDrawings drawings){
		DBUtil dbUtil = new DBUtil();
		
		String sql = "insert into t_product_drawings(Category,ProductName,ProductType,Machine,"
				+ "VersionNO,DesignTime,UseCondition,CADdrawings,PDFdrawings,Remark) values(?,?,?,?,?,?,?,?,?,?)";
		
		Object[] param = new Object[10];
		param[0] = drawings.getCategory();
		param[1] = drawings.getProductName();
		param[2] = drawings.getProductType();
		param[3] = drawings.getMachine();
		param[4] = drawings.getVersionNO();
		param[5] = drawings.getDesignTime();
		param[6] = drawings.getUsage();
		param[7] = drawings.getCADdrawings();
		param[8] = drawings.getPDFdrawings();
		param[9] = drawings.getRemark();
		
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
	}
	
	public boolean updateProduct(ProductDrawings drawings){
		
		DBUtil dbUtil = new DBUtil();
		
		String sql = "update t_product_drawings set ProductName = ?,ProductType=?,Machine=?,"
				+ "VersionNO=?,DesignTime=?,UseCondition=?,CADdrawings=?,PDFdrawings=?,Remark=? where ID=?";
		
		Object[] param = new Object[10];
		param[0] = drawings.getProductName();
		param[1] = drawings.getProductType();
		param[2] = drawings.getMachine();
		param[3] = drawings.getVersionNO();
		param[4] = drawings.getDesignTime();
		param[5] = drawings.getUsage();
		param[6] = drawings.getCADdrawings();
		param[7] = drawings.getPDFdrawings();
		param[8] = drawings.getRemark();
		param[9] = drawings.getID();
		
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
		
	}
	
	public List<Map<String, Object>> getDrawingsByPage(Page page,String category,String type,String column1,String content1,String column2,String content2){
		
		DBUtil dbUtil = new DBUtil();
		String sql = "select ID,ProductName,ProductType,Machine,VersionNO,DesignTime,UseCondition,CADdrawings,"
				+ "PDFdrawings,Remark from t_product_drawings where Category = ?" ;
		Object[] parameter = new Object[]{category,(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		if(type.equals("singleSelect")){
			sql += " and "+map.get(column1)+" like ?";
			parameter = new Object[]{category,"%"+content1+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}else if(type.equals("mixSelect")){
			sql += " and "+map.get(column1)+" like ? and "+map.get(column2)+" like ?";
			parameter = new Object[]{category,"%"+content1+"%","%"+content2+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}
		sql += " order by t_product_drawings.ID desc limit ?,?";
		
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return list;
	}
	
	public int getDrawingsCount(String category,String type,String column1,String content1,String column2,String content2){
		DBUtil dbUtil = new DBUtil();
		String sql = "select COUNT(ID) Count from t_product_drawings where Category = ? " ;
		Object[] parameter = new Object[]{category};
		if(type.equals("singleSelect")){
			sql += " and "+map.get(column1)+" like ?";
			parameter = new Object[]{category,"%"+content1+"%"};
		}else if(type.equals("mixSelect")){
			sql += " and "+map.get(column1)+" like ? and "+map.get(column2)+" like ?";
			parameter = new Object[]{category,"%"+content1+"%","%"+content2+"%"};
		}
		return Integer.parseInt(dbUtil.QueryToList(sql, parameter).get(1).get("Count").toString());
	}
	
	
	

}
