/**
 * 
 */
package com.eoulu.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.eoulu.dao.LogisticsDao;
import com.eoulu.dao.OrderInfoDao;
import com.eoulu.entity.Logistics;
import com.eoulu.entity.OrderInfo;
import com.eoulu.entity.SizeInfo;
import com.eoulu.service.LogisticService;
import com.eoulu.util.DBUtil;
import com.mysql.jdbc.Connection;

/**
 * @author zhangkai
 *
 */
public class LogisticServiceImpl implements LogisticService {

	public static  String getNowDate(){

		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH)+1;
		int day = cale.get(Calendar.DAY_OF_MONTH);
		String time = year+"-"+(month>9?month:"0"+month)+"-"+(day>9?day:"0"+day);

		return time;
	}

	/* (non-Javadoc)
	 * @see com.eoulu.service.LogisticService#getLogistic(int)
	 */
	public List<Map<String, Object>> getLogistic(int id) {
		String OperatingTime = getNowDate();
		DBUtil db = new DBUtil();
//		String sql = "select t_order_info.ID,t_order_info.OrderID,t_order_info.Review1,t_order_info.Review2,userA.RealName as ReviewName1,userB.RealName as ReviewName2,t_order_info.StockNumber,t_order_info.LogisticsNumber,t_equipment.ID EquipmentID,t_equipment.Model EquipmentModel,t_order_info.Number,t_order.ContractNo,"
//		+ "t_order_info.ExceptDate,t_order_info.Date,t_order_info.DeliveryNumber,t_order_status.Status,t_equipment.Remarks,t_supplier.Name,t_logistics.PONO,t_logistics.SONO,t_logistics.POAmount,t_logistics.RMBPOAmount,t_logistics.FactoryShipment,t_logistics.EstimatedPaymentTime,t_logistics.ActualPaymentTime "
//		+ "from t_order_info LEFT JOIN t_logistics on t_order_info.ID=t_logistics.OrderInfoID left join t_equipment on t_order_info.EquipmentModel=t_equipment.ID left join t_order_status on t_order_status.ID=t_order_info.Status LEFT JOIN t_user userA on t_order_info.ReviewName1=userA.ID "
//		+ "left join t_supplier on t_supplier.ID=t_logistics.Supplier left join t_order on t_order_info.OrderID=t_order.ID LEFT JOIN t_user userB on t_order_info.ReviewName2=userB.ID  left join t_inventory_info on t_inventory_info.OrderInfoID=t_order_info.ID "
//		+ "where t_order_info.OrderID=?";
//Object[] parameter = new Object[]{id};
		String sql = "select t_order_info.ID,t_order_info.OrderID,t_order_info.Review1,t_order_info.Review2,userA.RealName as ReviewName1,userB.RealName as ReviewName2,t_order_info.StockNumber,t_order_info.LogisticsNumber,t_commodity_info.ID EquipmentID,t_commodity_info.Model EquipmentModel,t_order_info.Number,t_order.ContractNo,t_order_info.ExceptDate,"
				+ "t_order_info.Date,t_order_info.DeliveryNumber,t_order_status.Status,t_commodity_info.CommodityName Remarks,t_supplier.Name,t_logistics.PONO,t_logistics.SONO,t_logistics.POAmount,t_logistics.RMBPOAmount,t_logistics.FactoryShipment,t_logistics.EstimatedPaymentTime,t_logistics.ActualPaymentTime,"
				+ "t_commodity_info.InitialQuantity-(case when a.outCounts is null then 0 else a.outCounts end)+(case when b.inCounts is null then 0 else b.inCounts end) InventoryQuantity "
				+ "from t_order_info LEFT JOIN t_logistics on t_order_info.ID=t_logistics.OrderInfoID "
				+ "left join t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID "
				+ "left join t_order_status on t_order_status.ID=t_order_info.Status "
				+ "left join t_supplier on t_supplier.ID=t_logistics.Supplier "
				+ "left join t_order on t_order_info.OrderID=t_order.ID "
				+ "LEFT JOIN t_user userA on t_order_info.ReviewName1=userA.ID "
				+ "LEFT JOIN t_user userB on t_order_info.ReviewName2=userB.ID "
				+ " left join t_inventory_info on t_inventory_info.OrderInfoID=t_order_info.ID "
				+ "LEFT JOIN(select t_inventory_info.EquipmentID ID, sum(t_inventory_info.Quantity) outCounts from t_commodity_info left join t_inventory_info "
				+ "on t_commodity_info.ID=t_inventory_info.EquipmentID WHERE t_inventory_info.Types= ? and t_inventory_info.OperatingTime<=? and t_inventory_info.OperatingTime>=?"
				+ " GROUP BY t_inventory_info.EquipmentID)a on t_commodity_info.ID=a.ID "
				+ "LEFT JOIN(select t_inventory_info.EquipmentID ID, sum(t_inventory_info.Quantity) inCounts from t_commodity_info left join "
				+ "t_inventory_info on t_commodity_info.ID=t_inventory_info.EquipmentID WHERE t_inventory_info.Types=?  and t_inventory_info.OperatingTime<=? and t_inventory_info.OperatingTime>=? "
				+ "GROUP BY t_inventory_info.EquipmentID)b on t_commodity_info.ID=b.ID where t_order_info.OrderID=?";
		//System.out.println(sql);
		Object[] parameter = new Object[]{2,OperatingTime,"2017-07-01",1,OperatingTime,"2017-07-01",id};
			
		return db.QueryToList(sql, parameter);
	}

	
	/**
	 * ��ͬ���ú�������Ϣ�����
	 * 订单信息添加
	 * 
	 * @return true  ��ӳɹ�  false  ���ʧ��
	 * */
	@Override
	public boolean orderInfoAdd(OrderInfo orderInfo, Logistics logistics) {
		boolean flag = false;
		DBUtil db = new DBUtil();
		Connection conn = db.getConnection();
		OrderInfoDao oid = new OrderInfoDao();
		LogisticsDao ld = new LogisticsDao();
		
		
		try{
			conn.setAutoCommit(false);
			
			oid.insert(orderInfo,db);
			int orderInfoID = oid.getID(orderInfo.getOrderID(),orderInfo.getEquipmentModel(),db);
			logistics.setOrderInfoID(orderInfoID);
			ld.insert(logistics,db);
			
			conn.commit();
			flag = true;
			conn.setAutoCommit(true);
		}catch(Exception e){
			try {
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			db.closed();
		}
		
		return flag;
	}



	/**
	 * ��ͬ���ú�������Ϣ���޸�
	 * 
	 * 物流信息修改
	 * @return true  �޸ĳɹ�  false  �޸�ʧ��
	 * */
	@Override
	public boolean orderInfoModify(OrderInfo orderInfo, Logistics logistics,String[] param1,String[] param2) {
		
		boolean flag = false;
		DBUtil db = new DBUtil();
		Connection conn = db.getConnection();
		OrderInfoDao oid = new OrderInfoDao();
		LogisticsDao ld = new LogisticsDao();
		
		
		try{
			conn.setAutoCommit(false);
			if(param1.length > 1 && param2.length > 1){
				oid.modify(orderInfo,db,param2); 
				ld.modify(logistics,db,param1);
			}else if(param2.length > 1){
				oid.modify(orderInfo,db,param2);
			}else if(param1.length > 1){
				ld.modify(logistics,db,param1);
			}
			
			conn.commit();
			flag = true;
			conn.setAutoCommit(true);
		}catch(Exception e){
			e.printStackTrace();
			try {
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			db.closed();
		}

		return flag;
		
	}

	@Override
	public boolean orderInfoModify2(OrderInfo orderInfo, Logistics logistics) {
		boolean flag = false;
		DBUtil db = new DBUtil();
		Connection conn = db.getConnection();
		OrderInfoDao oid = new OrderInfoDao();
		LogisticsDao ld = new LogisticsDao();
		
		
		try{
			conn.setAutoCommit(false);
			oid.modify2(orderInfo,db); 
			ld.modify2(logistics,db);
			conn.commit();
			flag = true;
			conn.setAutoCommit(true);
		}catch(Exception e){
			e.printStackTrace();
			try {
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			db.closed();
		}

		return flag;
	}

	@Override
	public boolean addSizeInfo(List<SizeInfo> size) {
		//System.out.println(size.size());
		boolean flag = false; 
		List<String> ls = new ArrayList<>();
		LogisticsDao dao = new LogisticsDao();
		for(int i=0 ; i<size.size() ; i++){
			boolean temp = dao.addSizeInfo(size.get(i));
			if(temp){
				ls.add("true");
			}else{
				ls.add("false");
			}
		}
		if(!ls.contains("false")){
			flag = true;
		}
		return flag; 
	}
	
	
	

}
