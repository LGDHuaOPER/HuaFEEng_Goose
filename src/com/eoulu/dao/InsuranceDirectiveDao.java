package com.eoulu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.DeliveryAddress;
import com.eoulu.entity.InsuranceDirective;
import com.eoulu.entity.TransportGoodsInfo;
import com.eoulu.util.DBUtil;

public class InsuranceDirectiveDao {

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getInsuranceDirective(Page page){
    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();


		String sql= "select t_transport_insurance_directive.ShippingMarkNO,t_transport_insurance_directive.ShippingMarkADD,t_transport_insurance_directive.ID,t_transport_insurance_directive.SONO,t_transport_insurance_directive.PONO,t_transport_insurance_directive.ContractNO,"
				+ "t_transport_insurance_directive.TakingDate,t_transport_insurance_directive.DCNO,t_transport_insurance_directive.ConsigneeADD,t_transport_insurance_directive.ConsigneeATTN,"
				+ "t_transport_insurance_directive.ConsigneeName,t_transport_insurance_directive.ConsigneeTel,"
				+ "t_transport_insurance_directive.Shipment,t_transport_insurance_directive.Destination,t_transport_insurance_directive.InvoiceUSD,t_transport_insurance_directive.InvoiceUSD*1.1 InsuranceUSD,"
				+ "t_transport_insurance_directive.FinalDestination FinalADD,t_transport_insurance_directive.InsuredLiability Insured,"
				+ "t_transport_insurance_directive.Currency,t_transport_insurance_directive.Address,t_transport_insurance_directive.OperatingTime,t_order.ContractTitle,t_order.Customer,"
				+ "t_transport_insurance_directive.InWarehouse,t_transport_insurance_directive.WaybillNum,t_transport_insurance_directive.IsSend "
				+ " from t_transport_insurance_directive "
				+ "left join t_order on t_order.ContractNo=t_transport_insurance_directive.ContractNO "
				+ "order by t_transport_insurance_directive.TakingDate desc limit ?,?";
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
		String sql = "select count(ID) ? from t_transport_insurance_directive order by OperatingTime desc";
		
		Object[] parameter = new Object[]{"AllCounts"};
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		
		if(ls.size()>1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		
		return counts;
	}

	public int getIDByContractNO(String ContractNO){
		
		int id = 0;
		DBUtil db = new DBUtil();
		String sql = "select ID from t_transport_insurance_directive where ContractNO=?";
		
		Object[] parameter = new Object[]{ContractNO};
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
	
		if(ls.size()>1){
			id = Integer.parseInt(ls.get(1).get("ID").toString());//get(ls.get(0).get("ID")).
			
		}
		return id;
	}
	
	/**
	 * 添加
	 * @param invoice
	 * @param db
	 * @return
	 */
	public int insert(InsuranceDirective directive){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[22];
		String sql = "insert into t_transport_insurance_directive (SONO,PONO,ContractNO,TakingDate,DCNO,ConsigneeADD,ConsigneeATTN,ConsigneeName,ConsigneeTel,"
				+ "Shipment,Destination,InvoiceUSD,FinalDestination,InsuredLiability,"
				+ "Currency,Address,OperatingTime,ShippingMarkNO,ShippingMarkADD,InWarehouse,WaybillNum,IsSend) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";;
		parameter[0] = directive.getSONO();
		parameter[1] = directive.getPONO();
		parameter[2] = directive.getContractNO();
		parameter[3] = directive.getTakingDate();
		parameter[4] = directive.getDCNO();
		parameter[5] = directive.getConsigneeADD();
		parameter[6] = directive.getConsigneeATTN();
		parameter[7] = directive.getConsigneeName();
		parameter[8] = directive.getConsigneeTel();
		parameter[9] = directive.getShipment();
		parameter[10] = directive.getDestination();
		parameter[11] = directive.getInvoiceUSD();
		parameter[12] = directive.getFinalDestination();
		parameter[13] = directive.getInsuredLiability();
		parameter[14] = directive.getCurrency();
		parameter[15] = directive.getAddress();
		parameter[16] = df.format(new Date());
		parameter[17] = directive.getShippingMarkNO();
		parameter[18] = directive.getShippingMarkADD();
		parameter[19] = directive.getInWarehouse();
		parameter[20] = directive.getWaybillNum();
		parameter[21] = "未发送";
		int i = 0;
		try {
			i = db.executeUpdateNotClose(sql, parameter);
		
		if(i>=1){
			String sql2 = "select last_insert_id()";
			ResultSet rs = db.Query(sql2);
			if(rs.next()) {
				i = rs.getInt(1);
			}
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	public static void main(String[] args) throws SQLException {
		DBUtil db = new DBUtil();
		DBUtil db2 = new DBUtil();
		String sql = "insert into t_user (UserName) values (?)";
		Object[] parameter = new Object[] {"hello"};
		Object[] parameter2 = new Object[] {"hello2"};
		db.executeUpdateOpen(sql, parameter);
		db2.executeUpdateOpen(sql, parameter2);
		for(int i=0;i<10000;i++) {
			for(int j=0;j<10000;j++) {
				
			}
		}
		String sql2 = "select last_insert_id()";
		ResultSet rs = db.Query(sql2);
		rs.next();
		ResultSet rs2 = db2.Query(sql2);
		rs2.next();
		System.out.println("1:"+rs.getInt(1));
		System.out.println("2:"+rs2.getInt(1));
		
		
	}
	
	/**
	 * 修改
	 * @param packing
	 * @return
	 */
	public boolean update(InsuranceDirective directive){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[22];
		String sql = "update t_transport_insurance_directive set SONO=?,PONO=?,ContractNO=?,TakingDate=?,DCNO=?,ConsigneeADD=?,ConsigneeATTN=?,ConsigneeName=?,ConsigneeTel=?, "
				+ "Shipment=?,Destination=?,InvoiceUSD=?,FinalDestination=?,InsuredLiability=?,"
				+ "Currency=?,Address=?,OperatingTime=?,ShippingMarkNO=?,ShippingMarkADD=?,InWarehouse=?,WaybillNum=? where ID=?";
		parameter[0] = directive.getSONO();
		parameter[1] = directive.getPONO();
		parameter[2] = directive.getContractNO();
		parameter[3] = directive.getTakingDate();
		parameter[4] = directive.getDCNO();
		parameter[5] = directive.getConsigneeADD();
		parameter[6] = directive.getConsigneeATTN();
		parameter[7] = directive.getConsigneeName();
		parameter[8] = directive.getConsigneeTel();
		parameter[9] = directive.getShipment();
		parameter[10] = directive.getDestination();
		parameter[11] = directive.getInvoiceUSD();
		parameter[12] = directive.getFinalDestination();
		parameter[13] = directive.getInsuredLiability();
		parameter[14] = directive.getCurrency();
		parameter[15] = directive.getAddress();
		parameter[16] = df.format(new Date());
		parameter[17] = directive.getShippingMarkNO();
		parameter[18] = directive.getShippingMarkADD();
		parameter[19] = directive.getInWarehouse();
		parameter[20] = directive.getWaybillNum();
		parameter[21] = directive.getID();
		//System.out.println("ID为："+directive.getID());
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
		String sql = "delete from t_transport_insurance_directive where ID=?";
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
	 * 
	 * @param DirectiveID
	 * @return
	 */
	public List<Map<String,Object>> getDeliveryAddress(int DirectiveID){
	    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql= "select t_transport_delivery_address.ID AddressID,t_transport_delivery_address.`Name`,"
				+ "t_transport_delivery_address.Applicant ATT,t_transport_delivery_address.Address,"
				+ "t_transport_delivery_address.Tel,t_transport_delivery_address.DirectiveID,t_transport_delivery_address.OperatingTime "
				+ "from t_transport_delivery_address "
				//+ "left join t_transport_insurance_directive on t_transport_delivery_address.DirectiveID=t_transport_insurance_directive.ID"
				+ " where t_transport_delivery_address.DirectiveID=?";
		Object[] parameter = new Object[]{DirectiveID};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	public boolean insert(DeliveryAddress address){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[6];
		String sql = "insert into t_transport_delivery_address (Address,`Name`,Applicant,Tel,DirectiveID,OperatingTime)"
				+ "values (?,?,?,?,?,?) ";
		parameter[0] = address.getAddress();
		parameter[1] = address.getName();
		parameter[2] = address.getApplicant();
		parameter[3] = address.getTel();
		parameter[4] = address.getDirectiveID();
		parameter[5] = df.format(new Date());
	
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
	public boolean updateDeliveryAddress(DeliveryAddress address){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[6];
		String sql =  "update t_transport_delivery_address set Address=?,`Name`=?,Applicant=?,Tel=?,"
				+ "OperatingTime=? where ID=?";
		parameter[0] = address.getAddress();
		parameter[1] = address.getName();
		parameter[2] = address.getApplicant();
		parameter[3] = address.getTel();
		//parameter[4] = address.getDirectiveID();
		parameter[4] = df.format(new Date());
		parameter[5] = address.getID();
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
	public boolean deleteDeliveryAddress(int id){
		boolean flag = false;
		DBUtil db = new DBUtil();
		String sql = "delete from t_transport_delivery_address where ID=?";
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
	 * 
	 * @param DirectiveID
	 * @return
	 */
	public List<Map<String,Object>> getTransportGoodsInfo(int DirectiveID){
	    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();


		String sql= "select ID InfoID,Model,Description,Qty,Size,DirectiveID,OperatingTime "
				+ "from t_transport_goods_info"
				+ " where DirectiveID=?";
		Object[] parameter = new Object[]{DirectiveID};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	public boolean insertGoodsInfo(TransportGoodsInfo info){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[6];
		String sql = "insert into t_transport_goods_info (Model,Description,Qty,Size,DirectiveID,OperatingTime)"
				+ "values (?,?,?,?,?,?) ";
		parameter[0] = info.getModel();
		parameter[1] = info.getDescription();
		parameter[2] = info.getQty();
		parameter[3] = info.getSize();
		parameter[4] = info.getDirectiveID();
		parameter[5] = df.format(new Date());
	
		int i = 0;
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
			i = db.executeUpdate(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
		if(i>=1){
			flag = true;
		}
		return flag;
	} 
	public boolean updateGoodsInfo(TransportGoodsInfo info){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(df.format(new Date()));
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[6];
		String sql =  "update t_transport_goods_info set Model=?,Description=?,Qty=?,Size=?,"
				+ "OperatingTime=? where ID=?";
		parameter[0] = info.getModel();
		parameter[1] = info.getDescription();
		parameter[2] = info.getQty();
		parameter[3] = info.getSize();
		
		parameter[4] = df.format(new Date());
		parameter[5] = info.getID();
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
	public boolean deleteGoodsInfo(int id){
		System.out.println("id:"+id);
		boolean flag = false;
		DBUtil db = new DBUtil();
		String sql = "delete from t_transport_goods_info where ID=?";
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
	 * 
	 * @param DirectiveID
	 * @return
	 */
	public List<Map<String,Object>> getGoodsInfoAndAddress(int DirectiveID){
	    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();


		String sql= "select t_transport_delivery_address.Address,t_transport_delivery_address.Applicant,"
				+ "t_transport_delivery_address.ID AddressID,t_transport_delivery_address.`Name`,"
				+ "t_transport_delivery_address.Tel,t_transport_goods_info.Description,"
				+ "t_transport_goods_info.ID InfoID,t_transport_goods_info.Model,t_transport_goods_info.Qty,"
				+ "t_transport_goods_info.Size from t_transport_delivery_address left join "
				+ "t_transport_goods_info on t_transport_goods_info.DirectiveID=t_transport_delivery_address.DirectiveID "
				+ " where t_transport_goods_info.DirectiveID=?";
		Object[] parameter = new Object[]{DirectiveID};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	/**
	 * 搜索
	 * @param sql
	 * @param parameter 
	 * @return
	 */
	public List<Map<String, Object>> getInsuranceDirective(String sql,Object[] parameter){
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
	
		return ls;
		
	}
	
	public List<Map<String,Object>> getAllEmail(){
		DBUtil db = new DBUtil();
		String sql = "select Email FROM t_user ";
		Object[] param = null;
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;
		
	}
	public void updateStatus(int ID) {
		DBUtil db = new DBUtil();
		String sql = "update t_transport_insurance_directive set IsSend=? where ID=?";
		Object[] param = new Object[] {"已发送",ID};
		db.executeUpdate(sql, param);
		
	}
	
}
