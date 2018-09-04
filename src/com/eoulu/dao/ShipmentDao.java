package com.eoulu.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Origin;
import com.eoulu.entity.Shipment;
import com.eoulu.entity.ShipmentSize;
import com.eoulu.util.DBUtil;

public class ShipmentDao {

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getShipment(Page page){
    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();


		String sql= "select t_shipment.AirWaybillNO,t_shipment.CarrierName,t_shipment.ContractNO,t_shipment.Date,t_shipment.DCNO,t_shipment.DepartAirport,"
				+ "t_shipment.DepartureDate,t_shipment.DestAirport,t_shipment.FlightNO,t_shipment.GoodsDescription,t_shipment.ID,t_shipment.InfoADD,"
				+ "t_shipment.InfoAPP,t_shipment.InfoTel,t_shipment.Qty,t_shipment.Total,t_shipment.ShipmentValue Value ,t_order.ContractTitle,t_order.Customer "
				+ " from t_shipment left join t_order on t_shipment.ContractNO=t_order.ContractNo order by t_shipment.OperatingTime desc limit ?,?";
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
		String sql = "select count(ID) ? from t_shipment order by OperatingTime desc";
		
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
	public boolean insert(Shipment shipment){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[17];
		String sql = "insert into t_shipment (AirWaybillNO,CarrierName,ContractNO,Date,DCNO,DepartAirport,"
				+ "DepartureDate,DestAirport,FlightNO,GoodsDescription,InfoADD,InfoAPP,InfoTel,Qty,Total,"
				+ "ShipmentValue,OperatingTime) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		parameter[0] = shipment.getAirWaybillNO();
		parameter[1] = shipment.getCarrierName();
		parameter[2] = shipment.getContractNO();
		parameter[3] = shipment.getDate();
		parameter[4] = shipment.getDCNO();
		parameter[5] = shipment.getDepartAirport();
		parameter[6] = shipment.getDepartureDate();
		parameter[7] = shipment.getDestAirport();
		parameter[8] = shipment.getFlightNO();
		parameter[9] = shipment.getGoodsDescription();
		parameter[10] = shipment.getInfoADD();
		parameter[11] = shipment.getInfoAPP();
		parameter[12] = shipment.getInfoTel();
		parameter[13] = shipment.getQty();
		parameter[14] = shipment.getTotal();
		parameter[15] = shipment.getValue();
		parameter[16] = df.format(new Date());
		
		int i = 0;
		i = db.executeUpdate(sql, parameter);
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	public List<Map<String,Object>> getShipmentSize(int ShipmentID){
	    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();


		String sql= "select t_shipment_size.DIM,t_shipment_size.GrossWeight "
				+ " from t_shipment_size left join t_shipment on t_shipment.ID=t_shipment_size.ShipmentID where t_shipment.ID=?";
		Object[] parameter = new Object[]{ShipmentID};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	public boolean insertSize(ShipmentSize size){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[4];
		String sql = "insert into t_shipment_size (DIM,GrossWeight,ShipmentID,OperatingTime) values (?,?,?,?)";
		parameter[0] = size.getDIM();
		parameter[1] = size.getGrossWeight();
		parameter[2] = size.getShipmentID();
		parameter[3] = df.format(new Date());
		
		
		int i = 0;
		i = db.executeUpdate(sql, parameter);
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
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
	public boolean updateShipment(Shipment shipment){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[18];
		String sql = "update t_shipment set AirWaybillNO=?,CarrierName=?,ContractNO=?,Date=?,DCNO=?,DepartAirport=?,"
				+ "DepartureDate=?,DestAirport=?,FlightNO=?,GoodsDescription=?,InfoADD=?,InfoAPP=?,InfoTel=?,Qty=?,Total=?,"
				+ "ShipmentValue=?,OperatingTime=? where ID=?";

		parameter[0] = shipment.getAirWaybillNO();
		parameter[1] = shipment.getCarrierName();
		parameter[2] = shipment.getContractNO();
		parameter[3] = shipment.getDate();
		parameter[4] = shipment.getDCNO();
		parameter[5] = shipment.getDepartAirport();
		parameter[6] = shipment.getDepartureDate();
		parameter[7] = shipment.getDestAirport();
		parameter[8] = shipment.getFlightNO();
		parameter[9] = shipment.getGoodsDescription();
		parameter[10] = shipment.getInfoADD();
		parameter[11] = shipment.getInfoAPP();
		parameter[12] = shipment.getInfoTel();
		parameter[13] = shipment.getQty();
		parameter[14] = shipment.getTotal();
		parameter[15] = shipment.getValue();
		parameter[16] = df.format(new Date());
		parameter[17] = shipment.getID();
		int i = 0;
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
		i = db.executeUpdate(sql, parameter);
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	public boolean updateSize(ShipmentSize size){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[5];
		String sql = "update t_shipment_size set DIM=?,GrossWeight=?,ShipmentID=?,OperatingTime=? where ID=?";
		parameter[0] = size.getDIM();
		parameter[1] = size.getGrossWeight();
		parameter[2] = size.getShipmentID();
		parameter[3] = df.format(new Date());
		parameter[4] = size.getID();
		
		int i = 0;
		i = db.executeUpdate(sql, parameter);
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
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
		String sql = "delete from t_shipment where ID=?";
		Object[] parameter = new Object[]{id};
		int i = 0;
		i = db.executeUpdate(sql, parameter);
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
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
	public List<Map<String, Object>> getShipment(String sql,Object[] parameter){
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		return ls;
		
	}
	/**
	 * 获取shipmentId
	 * @param contractNo
	 * @return
	 */
	public int getShipmentID(String contractNo){
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = null;
		
		String sql="select ID from t_shipment where ContractNo=?";
		Object[] parameter = new Object[]{contractNo};
		
		
		ls = db.QueryToList(sql, parameter,true);
		
		int packingId = 0;
		if(ls.size()>1){
			packingId = Integer.parseInt(ls.get(1).get("ID").toString());
		}
		return packingId;
	}
	
}
