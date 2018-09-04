package com.eoulu.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Origin;
import com.eoulu.entity.OriginGoods;
import com.eoulu.util.DBUtil;

public class OriginDao {

	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getOrigin(Page page){
    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql= "select t_origin_certificate.Date,t_origin_certificate.ContractNO,t_origin_certificate.Goods,t_origin_certificate.ID,t_origin_certificate.LCNO,t_origin_certificate.ManufactoryAddress,"
				+ "t_origin_certificate.ManufactoryName,t_origin_certificate.OriginCountry,t_origin_certificate.PurchaseOrderNO,"
				+ "t_origin_certificate.Tel,t_order.ContractTitle,t_order.Customer,t_origin_certificate.ModelNumber,t_origin_certificate.Quality  "
				+ "from t_origin_certificate left join t_order on t_order.ContractNo=t_origin_certificate.ContractNO "
				+ "order by t_origin_certificate.OperatingTime desc limit ?,?";

		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	/**
	 * 总数量
	 * @return
	 */
	public int getAllCounts(){
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_origin_certificate order by OperatingTime desc";
		
		Object[] parameter = new Object[]{"AllCounts"};
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		
		if(ls.size()>1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		
		return counts;
	}

	
	/**
	 * 添加
	 * @param invoice
	 * @param db
	 * @return
	 */
	public boolean insert(Origin origin){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[12];
		String sql = "insert into t_origin_certificate (Date,ContractNO,Goods,LCNO,ManufactoryAddress,"
				+ "ManufactoryName,OriginCountry,PurchaseOrderNO,Tel,OperatingTime,ModelNumber,Quality) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?)";
		parameter[0] = origin.getDate();
		parameter[1] = origin.getContractNO();
		parameter[2] = origin.getGoods();
		parameter[3] = origin.getLCNO();
		parameter[4] = origin.getManufactoryAddress();
		parameter[5] = origin.getManufactoryName();
		
		parameter[6] = origin.getOriginCountry();
		parameter[7] = origin.getPurchaseOrderNO();
		
		parameter[8] = origin.getTel();
		parameter[9] = df.format(new Date());
		parameter[10] = origin.getModelNumber();
		parameter[11] = origin.getQuality();
		
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	
	
	/**
	 * 修改
	 * @param packing
	 * @return
	 */
	public boolean updateOrigin(Origin origin){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[13];
		String sql = "update t_origin_certificate set Date=?,ContractNO=?,Goods=?,LCNO=?,ManufactoryAddress=?,"
				+ "ManufactoryName=?,OriginCountry=?,PurchaseOrderNO=?,Tel=?,OperatingTime=?,ModelNumber=?,Quality=?"
				+ " where ID=?";

		parameter[0] = origin.getDate();
		parameter[1] = origin.getContractNO();
		parameter[2] = origin.getGoods();
		parameter[3] = origin.getLCNO();
		parameter[4] = origin.getManufactoryAddress();
		parameter[5] = origin.getManufactoryName();
		
		parameter[6] = origin.getOriginCountry();
		parameter[7] = origin.getPurchaseOrderNO();
		
		parameter[8] = origin.getTel();
		parameter[9] = df.format(new Date());
		parameter[10] = origin.getModelNumber();
		parameter[11] = origin.getQuality();
		parameter[12] = origin.getID();
		
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean delete(int id){
		boolean flag = false;
		DBUtil db = new DBUtil();
		String sql = "delete from t_origin_certificate where ID=?";
		Object[] parameter = new Object[]{id};
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 搜索
	 * @param sql
	 * @param parameter 
	 * @return
	 */
	public List<Map<String, Object>> getOrigin(String sql,Object[] parameter){
		
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
	
		return ls;
		
	}
	
	public List<Map<String,Object>> getGoodsByOriginID(int originID){
		List<Map<String,Object>> ls = new ArrayList<Map<String,Object>>();
		DBUtil db = new DBUtil();
		String sql = "select ID,`Name`,Model,Quality from t_origin_goods where OriginID=? order by ID";
		Object[] param = new Object[]{originID};
		ls = db.QueryToList(sql, param);
		return ls;
	}
	public boolean insertGoods(OriginGoods goods){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "insert into t_origin_goods (Name,Model,Quality,OperatingTime,OriginID) values (?,?,?,?,?)";
		Object[] param = new Object[5];
		param[0] = goods.getName();
		param[1] = goods.getModel();
		param[2] = goods.getQuality();
		param[3] = df.format(new Date());
		param[4] = goods.getOriginID();
		
		int temp = db.executeUpdate(sql, param);
		if(temp >= 1){
			flag = true;
		}
		return flag;
	}
	
	public boolean updateGoods(OriginGoods goods){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "update t_origin_goods set Name=?,Model=?,Quality=?,OperatingTime=? where ID=?";
		Object[] param = new Object[5];
		param[0] = goods.getName();
		param[1] = goods.getModel();
		param[2] = goods.getQuality();
		param[3] = df.format(new Date());
		param[4] = goods.getID();
		
		int temp = db.executeUpdate(sql, param);
		if(temp >= 1){
			flag = true;
		}
		return flag;
	}
	public boolean deleteGoods(int id){
		boolean flag = false;
		DBUtil db = new DBUtil();
		String sql = "delete from t_origin_goods where ID=?";
		Object[] parameter = new Object[]{id};
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	
	public int getOriginID(String contractNo){
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = null;
		
		String sql="select ID from t_origin_certificate where ContractNo=?";
		Object[] parameter = new Object[]{contractNo};
		
		
		ls = db.QueryToList(sql, parameter,true);
		
		int id = 0;
		if(ls.size()>1){
			id = Integer.parseInt(ls.get(1).get("ID").toString());
		}
		return id;
	}
}
