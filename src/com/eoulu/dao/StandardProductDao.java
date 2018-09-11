package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.StandardProduct;
import com.eoulu.util.DBUtil;

public class StandardProductDao {
	
	public static Map<String, String> Classify_Map;
	static{
		Classify_Map = new HashMap<>();
		Classify_Map.put("产品型号", "Model");
		Classify_Map.put("产品名称", "Title");
		Classify_Map.put("产品类型", "Type");
		Classify_Map.put("适用机台", "Machine");
		
	}
	
	public List<Map<String, Object>> getAllData(Page page,String type,String column1,String content1,String column2,String content2){
		String sql = "select ID,Model,Title,Type,Machine,InstallInstructions,TestInstructions,CheckingReport,"
				+ "DocumentIntegrity,Review,ProductInstructions,UpdateTime from t_standard_product ";
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		if(type.equals("singleSelect")){
			sql += "where "+Classify_Map.get(column1)+" like ?";
			parameter = new Object[]{"%"+content1+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}else if(type.equals("mixSelect")){
			sql += "where "+Classify_Map.get(column1)+" like ? and "+Classify_Map.get(column2)+" like ?";
			parameter = new Object[]{"%"+content1+"%","%"+content2+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}
		sql += " order by OperateTime desc limit ?,?";
		return new DBUtil().QueryToList(sql, parameter);
				
	}
	
	public int getCounts(String type,String column1,String content1,String column2,String content2){
		String sql = "select count(ID) Count from t_standard_product ";
		Object[] parameter = null;
		if(type.equals("singleSelect")){
			sql += "where "+Classify_Map.get(column1)+" like ?";
			parameter = new Object[]{"%"+content1+"%"};
		}else if(type.equals("mixSelect")){
			sql += "where "+Classify_Map.get(column1)+" like ? and "+Classify_Map.get(column2)+" like ?";
			parameter = new Object[]{"%"+content1+"%","%"+content2+"%"};
		}
		List<Map<String, Object>> list = new DBUtil().QueryToList(sql, parameter);
		return Integer.parseInt(list.get(1).get("Count").toString());
	}
	
	public boolean insert(StandardProduct product){
		String sql = "insert into t_standard_product(Model,Title,Type,Machine,InstallInstructions,TestInstructions,CheckingReport,"
				+ "DocumentIntegrity,Review,OperateTime,ProductInstructions,UpdateTime) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Object[] param = new Object[12];
		param[0] = product.getModel();
		param[1] = product.getTitle();
		param[2] = product.getType();
		param[3] = product.getMachine();
		param[4] = product.getInstallInstructions();
		param[5] = product.getTestInstructions();
		param[6] = product.getCheckingReport();
		param[7] = product.getDocumentIntegrity();
		param[8] = "未通过";
		param[9] = format.format(new Date());
		param[10] = product.getProductInstructions();
		param[11] = product.getUpdateTime();
		
		int result = new DBUtil().executeUpdate(sql, param);
		return result > 0?true:false;
		
	}
	
	public boolean update(StandardProduct product){
		String sql = "update t_standard_product set Model=?,Title=?,Type=?,Machine=?,InstallInstructions=?,TestInstructions=?,CheckingReport=?,"
				+ "DocumentIntegrity=?,OperateTime=?,ProductInstructions=?,UpdateTime=?  where ID=?";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Object[] param = new Object[12];
		param[0] = product.getModel();
		param[1] = product.getTitle();
		param[2] = product.getType();
		param[3] = product.getMachine();
		param[4] = product.getInstallInstructions();
		param[5] = product.getTestInstructions();
		param[6] = product.getCheckingReport();
		param[7] = product.getDocumentIntegrity();
		param[8] = format.format(new Date());
		param[9] = product.getProductInstructions();
		param[10] = product.getUpdateTime();
		param[11] = product.getID();
		int result = new DBUtil().executeUpdate(sql, param);
		return result > 0?true:false;
		
	}
	
	public boolean setDocumentIntegrity(String documentIntegrity,int ID){
		String sql = "update t_standard_product set DocumentIntegrity = ? where ID=?";
		int result = new DBUtil().executeUpdate(sql, new Object[]{documentIntegrity,ID});
		return result > 0?true:false;
	}
	
	public boolean setReview(int ID){
		String sql = "update t_standard_product set Review = '已通过' where ID = ?";
		int result = new DBUtil().executeUpdate(sql, new Object[]{ID});
		return result > 0?true:false;
	}

}
