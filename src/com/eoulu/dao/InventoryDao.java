package com.eoulu.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.entity.InventoryInfo;
import com.eoulu.entity.InventoryOrder;
import com.eoulu.util.DBUtil;
import com.mysql.jdbc.Connection;

public class InventoryDao {

	public List<Map<String,Object>> getAllData(String column,String condition,Object[] param){
		
		String sql = "SELECT t_inventory.ID,t_inventory.CommodityID,t_inventory.PNCode,t_inventory.Remarks,t_supplier.`Name` Supplier,t_commodity_info.Model,t_commodity_info.CommodityName Description,"
				+ "t_commodity_info.SellerPriceOne,t_inventory.InventoryQuantity*t_commodity_info.SellerPriceOne ListPrice,"
				+ "t_inventory.InventoryQuantity,t_inventory.Suzhou,t_inventory.Hefei,t_inventory.Xiamen,t_inventory.Chengdu,t_inventory.Xianggang,t_inventory.Shenzhen,t_inventory.Beijing,t_inventory.Shijiazhuang FROM t_inventory "
				+ "LEFT JOIN t_commodity_info ON t_inventory.CommodityID=t_commodity_info.ID LEFT JOIN t_supplier ON t_commodity_info.Supplier=t_supplier.ID "
				
				+condition+" order by " +column+ " desc LIMIT ?,?";
		if(column.equals("Supplier")){
			sql = "SELECT t_inventory.ID,t_inventory.CommodityID,t_inventory.PNCode,t_inventory.Remarks,t_supplier.`Name` Supplier,t_commodity_info.Model,t_commodity_info.CommodityName Description,"
					+ "t_commodity_info.SellerPriceOne,t_inventory.InventoryQuantity*t_commodity_info.SellerPriceOne ListPrice,"
					+ "t_inventory.InventoryQuantity FROM t_inventory "
					+ "LEFT JOIN t_commodity_info ON t_inventory.CommodityID=t_commodity_info.ID LEFT JOIN t_supplier ON t_commodity_info.Supplier=t_supplier.ID "
					
					+condition+" order by " +column+ "  LIMIT ?,?";
		}
		
		return new DBUtil().QueryToList(sql, param);
	}
	
	public int getAllCounts(String condition,Object[] param){
		String sql = "SELECT COUNT(t_inventory.ID) FROM t_inventory "+condition;
		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, param);
		return ls.size()>1?Integer.parseInt(ls.get(1).get("COUNT(t_inventory.ID)").toString()):0;
	}
	
	public List<Map<String,Object>> QueryModel(String content){
		
		String sql = "SELECT t_commodity_info.ID CommodityID,t_commodity_info.Supplier SupplierID,t_commodity_info.Model,t_commodity_info.CommodityName Description,"
				+ "t_supplier.`Name` Supplier FROM t_commodity_info LEFT JOIN t_supplier ON t_commodity_info.Supplier=t_supplier.ID WHERE Model LIKE ?";
		return new DBUtil().QueryToList(sql, new Object[]{"%"+content+"%"});
	}
	
	public String insert(InventoryInfo info){
		if(queryCommodity(info.getCommodityID()).size()>1){
			return "型号已存在";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into t_inventory (CommodityID,InventoryQuantity,Remarks,OperatingTime,Suzhou,Hefei,Xiamen,Chengdu,Xianggang,PNCode) values (?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[]{info.getCommodityID(),info.getInventoryQuantity(),info.getRemarks(),df.format(new Date()),0,0,0,0,0,info.getPNCode()};
		return new DBUtil().executeUpdate(sql, param)>0?"添加成功！":"添加失败！";
	}
	
	public String update(InventoryInfo info){
	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_inventory set Remarks = ?,OperatingTime=?,PNCode=? where ID=?";
		Object[] param = new Object[]{info.getRemarks(),df.format(new Date()),info.getPNCode(),info.getID()};
		return new DBUtil().executeUpdate(sql, param)>0?"修改成功！":"修改失败！";
	}
	
	public List<Map<String, Object>> queryCommodity(int commodityID){
		String sql = "select ID from t_inventory where CommodityID=?";
		return new DBUtil().QueryToList(sql, new Object[]{commodityID});
	}
	
	public boolean insertStoreInfo(InventoryInfo info){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql1 = "INSERT INTO t_inventory_store_info (InventoryID,Types,Quantity,ContractNo,OperationCode,OperateDate,POUSD,PORMB,OperatingTime,Warehouse) VALUES (?,?,?,?,?,?,?,?,?,?)";
		Object[] param1 = new Object[]{info.getInventoryID(),info.getTypes(),info.getQuantity(),info.getContractNo(),info.getOperationCode(),info.getOperateDate(),info.getPOUSD(),info.getPORMB(),df.format(new Date()),info.getWarehouse()};
		String sql2 = "update t_inventory set InventoryQuantity = (InventoryQuantity + ?) ,"+info.getWarehouse()+" = ("+info.getWarehouse() +" + ?) where ID = ?";

		Object[] param2 = new Object[]{info.getQuantity(),info.getQuantity(),info.getInventoryID()};
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			dbUtil.executeUpdateNotClose(sql1, param1);
			dbUtil.executeUpdateNotClose(sql2, param2);
			
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
		
				e1.printStackTrace();
			}
			return false;
		
		}finally {
			dbUtil.closed();
		}
	}
	public List<Map<String, Object>> getOldQuantity(int ID){
		String sql = "select Quantity from t_inventory_store_info where ID=?";
		return new DBUtil().QueryToList(sql, new Object[]{ID});
	}
	
	public List<Map<String, Object>> getOldOrder(int ID){
		String sql = "select OrderQuantity FROM t_inventory_customer_order where ID=?";
		return new DBUtil().QueryToList(sql, new Object[]{ID});
	}

	
	public boolean updateStoreInfo(InventoryInfo info,int realNum){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql1 = "update t_inventory_store_info set Quantity=?,ContractNo=?,OperationCode=?,OperateDate=?,OperatingTime=?,POUSD=?,PORMB=? where ID=?";
		Object[] param1 = new Object[]{info.getQuantity(),info.getContractNo(),info.getOperationCode(),info.getOperateDate(),df.format(new Date()),info.getPOUSD(),info.getPORMB(),info.getID()};
		String sql2 = "update t_inventory set InventoryQuantity = InventoryQuantity + ?,"+info.getWarehouse()+" = "+info.getWarehouse() +" + ? where ID = ?";
	
		Object[] param2 = new Object[]{realNum,realNum,info.getInventoryID()};
		
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			dbUtil.executeUpdateNotClose(sql1, param1);
			dbUtil.executeUpdateNotClose(sql2, param2);
			
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
		
				e1.printStackTrace();
			}
			return false;
		
		}finally {
			dbUtil.closed();
		}

	}
	
	public List<Map<String,Object>> getStoreInfo(int inventoryID,int type){
		String sql = "SELECT ID StoreID,Quantity,ContractNo,Warehouse,OperationCode,OperateDate,POUSD,PORMB FROM t_inventory_store_info WHERE InventoryID=? AND Types=? ";

		return new DBUtil().QueryToList(sql, new Object[]{inventoryID,type});
	}
	
	public boolean insertOutOfStock(InventoryInfo info){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql1 = "INSERT INTO t_inventory_store_info (InventoryID,Types,Quantity,ContractNo,OperationCode,OperateDate,ContractUSD,ContractRMB,OperatingTime,Warehouse) VALUES (?,?,?,?,?,?,?,?,?,?)";
		Object[] param1 = new Object[]{info.getInventoryID(),info.getTypes(),info.getQuantity(),info.getContractNo(),info.getOperationCode(),info.getOperateDate(),info.getContractUSD(),info.getContractRMB(),df.format(new Date()),info.getWarehouse()};
		String sql2 = "update t_inventory set InventoryQuantity = InventoryQuantity - ? , "+info.getWarehouse()+" = "+info.getWarehouse() +" - ? where ID = ?";
		Object[] param2 = new Object[]{info.getQuantity(),info.getQuantity(),info.getInventoryID()};
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			dbUtil.executeUpdateNotClose(sql1, param1);
			dbUtil.executeUpdateNotClose(sql2, param2);
			
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
		
				e1.printStackTrace();
			}
			return false;
		
		}finally {
			dbUtil.closed();
		}
	}
	
	public boolean updateOutOfStock(InventoryInfo info,int realNum){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql1 = "update t_inventory_store_info set Quantity=?,ContractNo=?,OperationCode=?,OperateDate=?,OperatingTime=?,ContractUSD=?,ContractRMB=? where ID=?";
		Object[] param1 = new Object[]{info.getQuantity(),info.getContractNo(),info.getOperationCode(),info.getOperateDate(),df.format(new Date()),info.getContractUSD(),info.getContractRMB(),info.getID()};
		String sql2 = "update t_inventory set InventoryQuantity = InventoryQuantity - ?,"+info.getWarehouse()+" = "+info.getWarehouse() +" - ? where ID = ?";
		Object[] param2 = new Object[]{realNum,realNum,info.getInventoryID()};
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			dbUtil.executeUpdateNotClose(sql1, param1);
			dbUtil.executeUpdateNotClose(sql2, param2);
			
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
		
				e1.printStackTrace();
			}
			return false;
		
		}finally {
			dbUtil.closed();
		}
	}
	
	public List<Map<String,Object>> getOutOfStock(int inventoryID,int type){
		String sql = "SELECT ID OutID,Quantity,ContractNo,OperationCode,Warehouse,OperateDate,ContractUSD,ContractRMB FROM t_inventory_store_info WHERE InventoryID=? AND Types=? ";
		return new DBUtil().QueryToList(sql, new Object[]{inventoryID,type});
	}
	
	public boolean deleteInventory(InventoryInfo info){
		String sql1 = "delete from t_inventory_store_info where ID=?";
		
		String sql2 = "";
		switch (info.getTypes()) {
		case 1:
			sql2 = "update t_inventory set InventoryQuantity = InventoryQuantity + ?,"+info.getWarehouse()+" = "+info.getWarehouse() +" + ? where ID = ?";
			break;

		case 2:
			sql2 = "update t_inventory set InventoryQuantity = InventoryQuantity - ?,"+info.getWarehouse()+" = "+info.getWarehouse() +" - ? where ID = ?";
			break;
		}
	
		
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			dbUtil.executeUpdateNotClose(sql1, new Object[]{info.getID()});
			dbUtil.executeUpdateNotClose(sql2, new Object[]{info.getQuantity(),info.getQuantity(),info.getInventoryID()});
			
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
		
				e1.printStackTrace();
			}
			return false;
		
		}finally {
			dbUtil.closed();
		}

	}
	
	public boolean insertCustomerOrder(InventoryOrder order,Map map){
		String warehouse = order.getWarehouse();
		String sql1 = "INSERT INTO t_inventory_customer_order (Customer,OrderQuantity,InventoryID,OperatingTime,Warehouse,RemarksInfo,OrderTime,ContractNO,EstimatedShippingTime) VALUES (?,?,?,?,?,?,?,?,?)";
		String sql2 = "update t_inventory set InventoryQuantity = InventoryQuantity - ? ,"+warehouse+" = "+warehouse +" - ? where ID = ?";
	
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		try {
			conn.setAutoCommit(false);	
			String remarksInfo = map.get(warehouse)+":"+order.getCustomer()+"预定"+order.getOrderQuantity()+"个；";
			Object[] param = new Object[]{order.getCustomer(),order.getOrderQuantity(),order.getInventoryID(),
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),warehouse,remarksInfo,
						order.getOrderTime(),order.getContractNO(),order.getEstimatedShippingTime()};
			dbUtil.executeUpdateNotClose(sql1, param);
			dbUtil.executeUpdateNotClose(sql2, new Object[]{order.getOrderQuantity(),order.getOrderQuantity(),order.getInventoryID()});
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();

			} catch (SQLException e1) {
		
				e1.printStackTrace();
			}
			return false;
		
		}finally {
			dbUtil.closed();
		}

	}
	public boolean updateCustomerOrder(InventoryOrder order,Map map){
		String warehouse = order.getWarehouse();
		String sql1 = "update t_inventory_customer_order set Customer=?,OrderQuantity=?,OperatingTime=?,Warehouse=?,"
				+ "RemarksInfo=?,OrderTime=?,ContractNO=?,EstimatedShippingTime=? where ID=?";
		String sql2 = "update t_inventory set InventoryQuantity = InventoryQuantity - ? ,"+warehouse+" = "+warehouse +" - ? where ID = ?";
	
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		try {
			conn.setAutoCommit(false);	
			String remarksInfo = map.get(warehouse)+":"+order.getCustomer()+"预定"+order.getOrderQuantity()+"个；";
			Object[] param = new Object[]{order.getCustomer(),order.getOrderQuantity(),
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),warehouse,remarksInfo,
						order.getOrderTime(),order.getContractNO(),order.getEstimatedShippingTime(),order.getID()};
			dbUtil.executeUpdateNotClose(sql1, param);
			dbUtil.executeUpdateNotClose(sql2, new Object[]{order.getRealNum(),order.getRealNum(),order.getInventoryID()});
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
		
				e1.printStackTrace();
			}
			return false;
		
		}finally {
			dbUtil.closed();
		}

	}
	
	public boolean deleteCustomerOrder(InventoryOrder order){
		String sql1 = "delete from t_inventory_customer_order where ID=?";	
		String sql2 = "update t_inventory set InventoryQuantity = InventoryQuantity + ?,"+order.getWarehouse()+" = "+order.getWarehouse() +" + ? where ID = ?";

		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			dbUtil.executeUpdateNotClose(sql1, new Object[]{order.getID()});
			dbUtil.executeUpdateNotClose(sql2, new Object[]{order.getOrderQuantity(),order.getOrderQuantity(),order.getInventoryID()});
			
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();

			} catch (SQLException e1) {
		
				e1.printStackTrace();
			}
			return false;
		
		}finally {
			dbUtil.closed();
		}

	}
	
	public boolean updateRemarks(int inventoryID){
		String sql = "update t_inventory set Remarks = ? where ID = ?";
		int result = new DBUtil().executeUpdate(sql, new Object[]{getOrderRemarks(inventoryID),inventoryID});
		return result>0?true:false;
		
	}
	
	public String getCustomer(int customerID){
		String sql = "SELECT CustomerName from t_customer where ID = ?";
		return new DBUtil().QueryToList(sql, new Object[]{customerID}).get(1).get("CustomerName").toString();
		
	}
	
	public String getOrderRemarks(int inventoryID){
		String sql = "select RemarksInfo from t_inventory_customer_order where InventoryID = ?";
		List<Map<String, Object>> list = new DBUtil().QueryToList(sql, new Object[]{inventoryID});
		StringBuilder sBuilder = new StringBuilder();
		for(int i = 1;i < list.size();i ++){
			sBuilder.append(list.get(i).get("RemarksInfo").toString());
			sBuilder.append("\n");
		}
		return sBuilder.toString();
		
	}

	
	
	
	public List<Map<String,Object>> getCustomerOrder(int inventoryID){
		String sql = "SELECT t_inventory_customer_order.ID CustomerOrder,t_inventory_customer_order.OrderQuantity,t_inventory_customer_order.Warehouse,t_inventory_customer_order.Customer, "
				+ "t_inventory_customer_order.OrderTime,t_inventory_customer_order.ContractNO,t_inventory_customer_order.EstimatedShippingTime FROM t_inventory_customer_order WHERE InventoryID=? ";
		return new DBUtil().QueryToList(sql, new Object[]{inventoryID});
	}
	public List<Map<String,Object>> getAllData(){
		String sql = "SELECT t_inventory.ID InventoryID,t_inventory.CommodityID,t_commodity_info.Model,t_commodity_info.CommodityName Description,"
				+ "t_commodity_info.SellerPriceOne,t_inventory.InventoryQuantity*t_commodity_info.SellerPriceOne ListPrice,t_inventory.PNCode,"
				+ "t_inventory.InventoryQuantity,t_inventory.Suzhou,t_inventory.Hefei,t_inventory.Xiamen,t_inventory.Chengdu,t_inventory.Xianggang,"
				+ "t_inventory.Shenzhen,t_inventory.Beijing,t_inventory.Shijiazhuang,t_inventory.Remarks,t_inventory_customer_order.OrderTime,"
				+ "t_inventory_customer_order.Customer,t_inventory_customer_order.ContractNO,t_inventory_customer_order.OrderQuantity,t_inventory_customer_order.Warehouse,"
				+ "t_inventory_customer_order.EstimatedShippingTime FROM t_inventory"
				+ " LEFT JOIN t_commodity_info ON t_inventory.CommodityID=t_commodity_info.ID LEFT JOIN t_inventory_customer_order on"
				+ " t_inventory.ID=t_inventory_customer_order.InventoryID ORDER BY InventoryQuantity DESC,t_inventory.ID DESC";
				
		
		return new DBUtil().QueryToList(sql, null);
	}
	
	public boolean addPNCode(int CommodityID,String PNCode){
		String sql = "insert into t_commodity_code (CommodityID,PNCode) values(?,?)";
		int result = new DBUtil().executeUpdate(sql, new Object[]{CommodityID,PNCode});
		return result>0?true:false;	
	}
	public boolean updatePNCode(int CommodityID,String PNCode){
		String sql = "update t_commodity_code set PNCode = ?,CommodityID = ? where CommodityID=? or PNCode=?";
		int result = new DBUtil().executeUpdate(sql, new Object[]{PNCode,CommodityID,CommodityID,PNCode});
		return result>0?true:false;
	}
	
	public List<Map<String, Object>> queryPNCode(int CommodityID,String PNCode){
		String sql = "select ID from t_commodity_code where CommodityID = ? or PNCode = ?";
		return new DBUtil().QueryToList(sql, new Object[]{CommodityID,PNCode});	
	}
	
	public List<Map<String, Object>> getInventory(String PNCode){
		String sql = "SELECT t_inventory.ID,t_inventory.CommodityID,t_inventory.Remarks,t_inventory.PNCode,t_supplier.`Name` Supplier,t_commodity_info.Model,t_commodity_info.CommodityName Description,"
				+ "t_commodity_info.SellerPriceOne,t_inventory.InventoryQuantity*t_commodity_info.SellerPriceOne ListPrice,"
				+ "t_inventory.InventoryQuantity,t_inventory.Suzhou,t_inventory.Hefei,t_inventory.Xiamen,t_inventory.Chengdu,t_inventory.Xianggang,t_inventory.Shenzhen,t_inventory.Beijing,"
				+ "t_inventory.Shijiazhuang FROM t_inventory LEFT JOIN t_commodity_info ON t_inventory.CommodityID=t_commodity_info.ID LEFT JOIN t_supplier ON t_commodity_info.Supplier=t_supplier.ID "
				+ "where t_inventory.PNCode=? ";
		return new DBUtil().QueryToList(sql, new Object[]{PNCode});
	}
	
	public List<Map<String, Object>> getInfoByWarehouse(String warehouse,int commodityID){
		String sql = "select t_inventory.ID,t_inventory.Remarks,t_inventory.PNCode,t_supplier.`Name` Supplier,t_commodity_info.Model,t_commodity_info.CommodityName Description,"
				+ "t_commodity_info.SellerPriceOne,(t_inventory." +warehouse+"*t_commodity_info.SellerPriceOne) ListPrice,"
				+ "t_inventory."+warehouse+" InventoryQuantity FROM t_inventory "
				+ "LEFT JOIN t_commodity_info ON t_inventory.CommodityID=t_commodity_info.ID LEFT JOIN t_supplier ON t_commodity_info.Supplier=t_supplier.ID "
				+ "where t_inventory.CommodityID = ? ";
		return new DBUtil().QueryToList(sql, new Object[]{commodityID});
	}
	
	public String getQuantity(String warehouse,int commodityID){
		String sql = "select t_inventory.InventoryQuantity FROM t_inventory "
				+" where t_inventory.CommodityID = ? and t_inventory.WarehouseAddress = ?";
		List<Map<String, Object>> list = new DBUtil().QueryToList(sql, new Object[]{commodityID,warehouse});
		if(list.size()>1){
			return list.get(1).get("InventoryQuantity").toString();
		}else{
			return "";
		}
	}
	
	public boolean updateOperationTime(String time){
		
		String sql = "update t_inventory_time set OperationTime=? where ID = 1";
		DBUtil db = new DBUtil();
		int result = db.executeUpdate(sql, new Object[]{time});
		return result>0?true:false;
	}
	
	public boolean updateLatestInventory(String time){
		
		String sql = "update t_inventory_time set LatestInventory=? where ID = 1";
		DBUtil db = new DBUtil();
		int result = db.executeUpdate(sql, new Object[]{time});
		return result>0?true:false;
	}
	
	public List<Map<String, Object>> getTimeInfo(){
		String sql = "select OperationTime,LatestInventory from t_inventory_time";
		DBUtil db = new DBUtil();
		return db.QueryToList(sql, null);
	}
}
