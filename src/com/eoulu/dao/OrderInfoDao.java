/**
 * 
 */
package com.eoulu.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Logistics;
import com.eoulu.entity.OrderInfo;
import com.eoulu.entity.Quotes;
import com.eoulu.util.DBUtil;
import com.mysql.fabric.xmlrpc.base.Array;

/**
 * @author zhangkai
 *
 */
public class OrderInfoDao {

	/**
	 * ͨ��order��ȡorderInfo
	 * 
	 * @param orderInfo
	 * 
	 * @return List ��һ��Ϊ���� �Ժ�Ϊ������
	 */
	public List<Map<String, Object>> getOrderInfoByID(int orderID) {
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql = "select t_order_info.ID,t_order_info.ExceptDate,t_order_info.StockNumber,t_order_info.LogisticsNumber,t_commodity_info.CommodityName Remarks,t_order_info.OrderID,t_commodity_info.Model EquipmentModel,"
				+ "t_commodity_info.SellerPriceOne*ExchangeRate*TaxCategories*1.07*1.03 UnitPrice,"
				+ "t_order_info.Number,t_order_info.Date,t_order_info.DeliveryNumber,"
				+ "t_order_status.Status from t_order_info left join t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID left join t_order_status on "
				+ "t_order_info.Status=t_order_status.ID left join t_order on t_order_info.OrderID=t_order.ID left join t_quote_system on t_order.QuoteNumber=t_quote_system.ID where OrderID=?";
		
//		String sql = "select t_order_info.ID,t_order_info.ExceptDate,t_order_info.StockNumber,"
//				+ "t_order_info.LogisticsNumber,t_commodity_info.CommodityName Remarks,t_order_info.OrderID,"
//				+ "t_commodity_info.Model EquipmentModel,t_order_info.Number,t_order_info.Date,"
//				+ "t_order_info.DeliveryNumber,t_order_status.Status from t_order_info left join "
//				+ "t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID left join t_order_status on "
//				+ "t_order_info.Status=t_order_status.ID where OrderID=? AND t_order_info.EquipmentModel<>'' "
//				+ "UNION "
//				+ "select t_order_info.ID,t_order_info.ExceptDate,t_order_info.StockNumber,"
//				+ "t_order_info.LogisticsNumber,t_commodity_info.CommodityName Remarks,"
//				+ "t_order_info.OrderID,t_commodity_info.Model EquipmentModel,t_order_info.Number,"
//				+ "t_order_info.Date,t_order_info.DeliveryNumber,t_order_status.Status from "
//				+ "t_order_info left join t_commodity_info on t_order_info.CommodityID=t_commodity_info.ID "
//				+ "left join t_order_status on t_order_info.Status=t_order_status.ID where OrderID=? AND t_order_info.CommodityID<>''";
		Object[] parameter = new Object[] { orderID };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	public List<Map<String, Object>> getOrderInfoForExcel(int orderID) {
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql = "select t_order_info.ID,t_order_info.ExceptDate,t_order_info.StockNumber,t_order_info.LogisticsNumber,t_commodity_info.CommodityName Remarks,t_order_info.OrderID,t_commodity_info.Model EquipmentModel,"
				+ "t_order_info.Number,t_order_info.Date,t_order_info.DeliveryNumber,"
				+ "t_order_status.Status from t_order_info left join t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID left join t_order_status on "
				+ "t_order_info.Status=t_order_status.ID  where OrderID=?";
		
//		String sql = "select t_order_info.ID,t_order_info.ExceptDate,t_order_info.StockNumber,"
//				+ "t_order_info.LogisticsNumber,t_commodity_info.CommodityName Remarks,t_order_info.OrderID,"
//				+ "t_commodity_info.Model EquipmentModel,t_order_info.Number,t_order_info.Date,"
//				+ "t_order_info.DeliveryNumber,t_order_status.Status from t_order_info left join "
//				+ "t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID left join t_order_status on "
//				+ "t_order_info.Status=t_order_status.ID where OrderID=? AND t_order_info.EquipmentModel<>'' "
//				+ "UNION "
//				+ "select t_order_info.ID,t_order_info.ExceptDate,t_order_info.StockNumber,"
//				+ "t_order_info.LogisticsNumber,t_commodity_info.CommodityName Remarks,"
//				+ "t_order_info.OrderID,t_commodity_info.Model EquipmentModel,t_order_info.Number,"
//				+ "t_order_info.Date,t_order_info.DeliveryNumber,t_order_status.Status from "
//				+ "t_order_info left join t_commodity_info on t_order_info.CommodityID=t_commodity_info.ID "
//				+ "left join t_order_status on t_order_info.Status=t_order_status.ID where OrderID=? AND t_order_info.CommodityID<>''";
		Object[] parameter = new Object[] { orderID };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * ͨ�������µĶ�����ϸ
	 * 
	 * @param orderInfo
	 * 
	 * @return int ���ظ��µ�����
	 */
	public int insert(OrderInfo orderInfo) {
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql = "insert into t_order_info (OrderID,EquipmentModel,Number,LogisticsNumber,Date,DeliveryNumber,Status,Remarks) values (?,?,?,?,?,?,?,?)";
		Object[] parameter = new Object[8];

		parameter[0] = orderInfo.getOrderID();
		parameter[1] = orderInfo.getEquipmentModel();
		parameter[2] = orderInfo.getNumber();
		parameter[3] = orderInfo.getLogisticsNumber();
		parameter[4] = orderInfo.getDate();
		parameter[5] = orderInfo.getDeliveryNumber();
		parameter[6] = orderInfo.getStatus();
		parameter[7] = orderInfo.getRemarks();

		int flag = db.executeUpdate(sql, parameter);

		return flag;
	}

	/**
	 * ������Ա����orderInfo
	 * 
	 * @param orderInfo
	 * 
	 * @return int ���ظ��µ�����
	 * @throws SQLException
	 */
	public int insertOrderInfo(OrderInfo orderInfo, DBUtil db) throws SQLException {
		List<Map<String, Object>> ls = null;

		String sql = "insert into t_order_info (OrderID,EquipmentModel,Number,LogisticsNumber) values (?,?,?,?)";
		Object[] parameter = new Object[4];

		parameter[0] = orderInfo.getOrderID();
		parameter[1] = orderInfo.getEquipmentModel();
		parameter[2] = orderInfo.getNumber();
		parameter[3] = orderInfo.getLogisticsNumber();

		int flag = db.executeUpdateNotClose(sql, parameter);

		return flag;
	}

	public int insertOrderInfo(OrderInfo orderInfo) throws SQLException {
		DBUtil db = new DBUtil();
		String sql = "insert into t_order_info (OrderID,CommodityID,Number,LogisticsNumber,EquipmentModel) values (?,?,?,?,?)";
		Object[] parameter = new Object[5];
		parameter[0] = orderInfo.getOrderID();
		parameter[1] = orderInfo.getCommodityID();
		parameter[2] = orderInfo.getNumber();
		parameter[3] = orderInfo.getLogisticsNumber();
		parameter[4] = orderInfo.getEquipmentModel();

		int flag = 0;
		try {
			Object id =  db.insertGetId(sql, parameter);
			flag = Integer.parseInt(id.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * ͨ�������µĶ�����ϸ
	 * 
	 * @param orderInfo
	 * 
	 * @return int ���ظ��µ�����
	 * @throws SQLException
	 */
	public int insert(OrderInfo orderInfo, DBUtil db) throws SQLException {
		List<Map<String, Object>> ls = null;

		String sql = "insert into t_order_info (OrderID,EquipmentModel,Number,Date,DeliveryNumber,Status,Remarks,ExceptDate) values (?,?,?,?,?,?,?,?)";
		Object[] parameter = new Object[8];

		parameter[0] = orderInfo.getOrderID();
		parameter[1] = orderInfo.getEquipmentModel();
		parameter[2] = orderInfo.getNumber();
		parameter[3] = orderInfo.getDate();
		parameter[4] = orderInfo.getDeliveryNumber();
		parameter[5] = orderInfo.getStatus();
		parameter[6] = orderInfo.getRemarks();
		parameter[7] = orderInfo.getExceptDate();

		int flag = db.executeUpdateNotClose(sql, parameter);

		return flag;
	}

	/**
	 * 订单信息的单条修改
	 * 
	 * @param orderInfo
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	public boolean modify2(OrderInfo orderInfo, DBUtil db) throws SQLException {

		boolean flag = false;
		String sql = "update t_order_info set Date=?,DeliveryNumber=?,Status=?,LogisticsNumber=?,ExceptDate=? where ID=?";// ,StockNumber=?
		Object[] parameter = new Object[6];
		parameter[0] = orderInfo.getDate().equals("") ? "0000-00-00" : orderInfo.getDate();
		parameter[1] = orderInfo.getDeliveryNumber();
		parameter[2] = orderInfo.getStatus();
		parameter[3] = orderInfo.getLogisticsNumber();
		parameter[4] = orderInfo.getExceptDate().equals("") ? "0000-00-00" : orderInfo.getExceptDate();
		// parameter[5] = orderInfo.getStockNumber();
		parameter[5] = orderInfo.getID();
		int i = db.executeUpdateNotClose(sql, parameter);

		if (i > 0)
			flag = true;

		return flag;
	}

	/**
	 * �޸Ķ�����ϸ
	 * 
	 * 
	 * 
	 * @param orderInfo
	 *            ������ϸ
	 * 
	 * @return boolean �ɹ�����true ���򷵻�false
	 * @throws SQLException
	 */
	public boolean modify(OrderInfo orderInfo, DBUtil db, String[] param) throws SQLException {
		System.out.println("suzhu" + Arrays.toString(param));
		boolean flag = false;

		String sql = "update t_order_info set ";
		for (int i = 0; i < param.length - 1; i++) {

			if (i == param.length - 2) {
				sql += param[i] + "=?";
			} else {
				sql += param[i] + "=?,";
			}

		}

		sql += " where ID=?";
		System.out.println(sql);
		// ��orderInfo�е���Ϣ��ֵ��Object������
		Object[] parameter = new Object[param.length];
		System.out.println("length:" + parameter.length);
		for (int i = 0; i < param.length; i++) {

			if (param[i].equals("Date")) {
				parameter[i] = orderInfo.getDate().equals("") ? "0000-00-00" : orderInfo.getDate();
			}
			if (param[i].equals("DeliveryNumber")) {
				parameter[i] = orderInfo.getDeliveryNumber();
			}
			if (param[i].equals("Status")) {
				parameter[i] = orderInfo.getStatus();
			}
			if (param[i].equals("LogisticsNumber")) {
				parameter[i] = orderInfo.getLogisticsNumber();
			}
			if (param[i].equals("ExceptDate")) {
				parameter[i] = orderInfo.getExceptDate().equals("") ? "0000-00-00" : orderInfo.getExceptDate();
			}
		}
		parameter[param.length - 1] = orderInfo.getID();

		int i = db.executeUpdateNotClose(sql, parameter);

		if (i > 0)
			flag = true;

		return flag;
	}

	/**
	 * ��ȡorderInfo ��id
	 * 
	 * 
	 * @return int db������������ĺ�ͬ���õ�id
	 * 
	 */
	public int getID(int orderID, int equipment, DBUtil db) {
		List<Map<String, Object>> ls = null;

		String sql = "select ID from t_order_info where OrderID=? and EquipmentModel=?";
		Object[] parameter = new Object[2];

		parameter[0] = orderID;
		parameter[1] = equipment;

		int result = 0;
		ls = db.QueryToList(sql, parameter, false);
		if (ls.size() > 1)
			result = Integer.parseInt(ls.get(1).get("ID").toString());
		return result;
	}

	/**
	 * ɾ��order_info
	 * 
	 * @throws SQLException
	 */
	public boolean deletOrderInfo(String id, DBUtil db) throws SQLException {
		boolean flag = false;

		String sql = "delete from t_order_info where ID=?";
		Object[] parameter = new Object[] { id };

		if (db.executeUpdateNotClose(sql, parameter) > 0) {
			flag = true;
		}

		return flag;

	}

	/**
	 * �޸ĺ�ͬ����
	 */
	public boolean modifyOrderInfo(String id, String counts) {
		boolean flag = false;

		DBUtil db = new DBUtil();
		String sql = "update t_order_info set Number=? where ID=?";
		Object[] parameter = new Object[] { counts, id };

		if (db.executeUpdate(sql, parameter) > 0) {
			flag = true;
		}

		return flag;
	}

	/**
	 * ͨ��orderIDɾ�����ж�Ӧorderid��������Ϣ
	 * 
	 * @throws SQLException
	 */
	public boolean deleteOrderInfo(String id, DBUtil db) throws SQLException {
		boolean flag = false;

		String sql = "delete from t_order_info where OrderID=?";
		Object[] parameter = new Object[] { id };

		if (db.executeUpdateNotClose(sql, parameter) > 0) {
			flag = true;
		}

		return flag;
	}

	public List<Map<String, Object>> getModelAndNumber() {

		List<Map<String, Object>> ls;
		String sql = "SELECT SUM(t_order_info.Number) Number,t_commodity_info.Model Model,t_requirement_classify.Classify Classify,t_commodity_info.CommodityName Remarks FROM t_order_info LEFT JOIN t_commodity_info "
				+ "on t_order_info.EquipmentModel=t_commodity_info.ID "
				+ "LEFT JOIN t_order ON t_order.ID=t_order_info.OrderID "
				+ "LEFT JOIN t_requirement_classify on t_order.ContractCategory=t_requirement_classify.ID"
				+ " where t_requirement_classify.Classify=? and t_order.DateOfSign>='2017-07-01' and t_order.DateOfSign<='2017-09-30' GROUP BY EquipmentModel";
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[1];
		parameter[0] = "机台";
		ls = db.QueryToList(sql, parameter);
		// System.out.println(sql);
		return ls;
	}

	public List<Map<String, Object>> getAllModelAndNumber() {

		List<Map<String, Object>> ls;

		String sql = "SELECT SUM(t_order_info.Number) Number,t_commodity_info.Model Model,t_requirement_classify.Classify Classify,t_commodity_info.CommodityName Remarks FROM t_order_info LEFT JOIN t_commodity_info "
				+ "on t_order_info.EquipmentModel=t_commodity_info.ID "
				+ "LEFT JOIN t_order ON t_order.ID=t_order_info.OrderID "
				+ "LEFT JOIN t_requirement_classify on t_order.ContractCategory=t_requirement_classify.ID"
				+ " where t_order.DateOfSign>='2017-01-01' and t_order.DateOfSign<='2017-12-31' and t_commodity_info.Model like 'DPP210%' GROUP BY EquipmentModel ";//
		/*
		 * String sql =
		 * "SELECT SUM(t_order_info.Number) Number FROM t_order_info LEFT JOIN t_equipment "
		 * + "on t_order_info.EquipmentModel=t_equipment.ID " +
		 * "LEFT JOIN t_order ON t_order.ID=t_order_info.OrderID " +
		 * "LEFT JOIN t_requirement_classify on t_order.ContractCategory=t_requirement_classify.ID"
		 * +
		 * "where t_order.DateOfSign>='2017-01-01' and t_order.DateOfSign<='2017-12-31' and t_equipment.Model like 'DPP210%'"
		 * ;
		 */
		DBUtil db = new DBUtil();
		Object[] parameter = null;
		ls = db.QueryToList(sql, parameter);
		System.out.println("123:" + sql);
		return ls;
	}
	
	public boolean isExist(int orderID,int commodity){
		String sql = "select ID from t_order_info where OrderID=? and CommodityID=?";
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[]{orderID, commodity};
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		return ls.size()>1?true:false;
	}
	public boolean deleteCommodity(int id){
		String sql = "delete from t_order_info where OrderID=? and CommodityID<>''";
		Object[] param = new Object[]{id};
		DBUtil db = new DBUtil();
		int count = db.executeUpdate(sql, param);
		return count>0?true:false;
	}
}
