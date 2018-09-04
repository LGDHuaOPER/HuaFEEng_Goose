/**
 * 
 */
package com.eoulu.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.eoulu.entity.Log;
import com.eoulu.entity.Logistics;
import com.eoulu.entity.OrderInfo;
import com.eoulu.entity.SizeInfo;
import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 */
public class LogisticsDao {

	
	/**
	 * ͨ��logisticsID��ȡlogistics
	 * 
	 * @param logistics  
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * */
	public List<Map<String, Object>> getLogisticsByID(Logistics logistics){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select * from t_logistics where OrderID=?";
		Object[] parameter = new Object[]{logistics.getOrderID()};
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	
	/**
	 * ͨ��orderID��ȡorderID
	 * 
	 * @param logistics  
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * */
	public List<Map<String, Object>> getLogisticsByID(int id){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select t_logistics.OrderID,t_supplier.ID,t_supplier.Name Supplier,t_logistics.PONO,t_logistics.SONO,t_logistics.POAmount,t_logistics.RMBPOAmount,t_logistics.POAmount,t_logistics.FactoryShipment from t_logistics"
				+ " left join t_supplier on t_supplier.ID=t_logistics.Supplier where OrderID=?";
		Object[] parameter = new Object[]{id};
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	
	/**
	 * ���붩����Ϣ
	 * @throws SQLException 
	 * 
	 * */
	public int insert(Logistics logistics,DBUtil db) throws SQLException{
		int flag = 0;
		
		String sql = "insert into t_logistics (OrderInfoID,OrderID,Supplier,PONO,SONO,POAmount,FactoryShipment) values (?,?,?,?,?,?,?)";
		Object[] parameter = new Object[7];
		parameter[0] = logistics.getOrderInfoID();
		parameter[1] = logistics.getOrderID();
		parameter[2] = logistics.getSupplier();
		parameter[3] = logistics.getPONO();
		parameter[4] = logistics.getSONO();
		parameter[5] = logistics.getPOAmount();
		parameter[6] = logistics.getFactoryShipment();
		
		flag =  db.executeUpdateNotClose(sql, parameter);
	    
		
		return flag;
	}
	
	/**
	 * 物流信息的单条修改
	 * @param logistics
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	public boolean modify2(Logistics logistics,DBUtil db) throws SQLException{

		boolean flag = false;
		String sql = "update t_logistics set Supplier=?,PONO=?,SONO=?,POAmount=?,RMBPOAmount=?,FactoryShipment=?,EstimatedPaymentTime=?,ActualPaymentTime=? where OrderInfoID=?";
	
		Object[] parameter = new Object[9];
		parameter[0] = logistics.getSupplier();
		parameter[1] = logistics.getPONO();
		parameter[2] = logistics.getSONO();
		parameter[3] = logistics.getPOAmount();
		parameter[4] = logistics.getRMBPOAmount();
		parameter[5] = logistics.getFactoryShipment().equals("")?"0000-00-00":logistics.getFactoryShipment();
		parameter[6] = logistics.getEstimatedPaymentTime().equals("")?"0000-00-00":logistics.getEstimatedPaymentTime();
		parameter[7] = logistics.getActualPaymentTime().equals("")?"0000-00-00":logistics.getActualPaymentTime();
		parameter[8] = logistics.getOrderInfoID();
		
		int i = db.executeUpdateNotClose(sql, parameter);

        if(i>0)
        	flag = true;

		return flag;
	}
	
	
	/**
	 * �޸�������Ϣ
	 * 
	 * 
	 * 
	 * @param orderInfo ������ϸ
	 * 
	 * @return boolean �ɹ�����true  ���򷵻�false
	 * @throws SQLException 
	 * */
	public boolean modify(Logistics logistics,DBUtil db,String[] param) throws SQLException{
		System.out.println(param.length+":"+Arrays.toString(param));
		boolean flag = false;
		//String sql = "update t_logistics set Supplier=?,PONO=?,SONO=?,POAmount=?,RMBPOAmount=?,FactoryShipment=?,EstimatedPaymentTime=?,ActualPaymentTime=? where OrderInfoID=?";
		String sql = "update t_logistics set ";
		for(int i=0 ; i<param.length ; i++){
			
			if(i == param.length-1){
				sql +=" where OrderInfoID=?";
			}else if(i == param.length-2){
				sql += param[i]+"=?";
			}else{
				sql += param[i]+"=?,";
			}
			
		}

		System.out.println(sql);
		//��orderInfo�е���Ϣ��ֵ��Object������
		Object[] parameter = new Object[param.length];
		for(int i=0 ; i<param.length ; i++){
			if(param[i].equals("Supplier")){
				parameter[i] = logistics.getSupplier();
			}
			if(param[i].equals("PONO")){
				parameter[i] = logistics.getPONO();
			}
			if(param[i].equals("SONO")){
				parameter[i] = logistics.getSONO();
			}
			if(param[i].equals("POAmount")){
				parameter[i] = logistics.getPOAmount();
			}
			if(param[i].equals("RMBPOAmount")){
				parameter[i] = logistics.getRMBPOAmount();
				
			}
			if(param[i].equals("FactoryShipment")){
				parameter[i] = logistics.getFactoryShipment().equals("")?"0000-00-00":logistics.getFactoryShipment();
			}
			if(param[i].equals("EstimatedPaymentTime")){
				parameter[i] = logistics.getEstimatedPaymentTime().equals("")?"0000-00-00":logistics.getEstimatedPaymentTime();
			}
			if(param[i].equals("ActualPaymentTime")){
				parameter[i] = logistics.getActualPaymentTime().equals("")?"0000-00-00":logistics.getActualPaymentTime();
			}
		}
//		parameter[0] = logistics.getSupplier();
//		parameter[1] = logistics.getPONO();
//		parameter[2] = logistics.getSONO();
//		parameter[3] = logistics.getPOAmount();
//		parameter[4] = logistics.getRMBPOAmount();
//		parameter[5] = logistics.getFactoryShipment().equals("")?"0000-00-00":logistics.getFactoryShipment();
//		parameter[6] = logistics.getEstimatedPaymentTime().equals("")?"0000-00-00":logistics.getEstimatedPaymentTime();
//		parameter[7] = logistics.getActualPaymentTime().equals("")?"0000-00-00":logistics.getActualPaymentTime();
		parameter[param.length-1] = logistics.getOrderInfoID();
		
		int i = db.executeUpdateNotClose(sql, parameter);

        if(i>0)
        	flag = true;

		return flag;
	}
	
	/**
	 * ����յ�logictics��Ϣ
	 * @throws SQLException 
	 * 
	 * */
	public boolean insertEmpty(Logistics logistics,DBUtil db) throws SQLException{
		boolean flag = false;
		
		String sql = "insert into t_logistics (OrderInfoID,OrderID) values (?,?)";
		Object[] parameter = new Object[]{logistics.getOrderInfoID(),logistics.getOrderID()};
		
		if(db.executeUpdateNotClose(sql, parameter)>0){
			flag = true;
		}
		
		
		return flag;
	}
	
	/**
	 * ɾ����Ӧ����ϸ��Ϣ
	 * @throws SQLException 
	 * */
	public boolean deletLogistics(String id,DBUtil db) throws SQLException{
		boolean flag =false;
		String sql = "delete from t_logistics where OrderInfoID=?";
		Object[] parameter = new Object[]{id};
		
		if(db.executeUpdateNotClose(sql, parameter)>0){
			flag = true;
		}
		
		
		return flag;
	}
	
	
	
	/**
	 * ͨ��orderIDɾ�����ж�Ӧorderid��������Ϣ
	 * @throws SQLException 
	 * */
	public boolean deleteLogistics(String id,DBUtil db) throws SQLException{
		boolean flag = false;
		
		String sql = "delete from t_logistics where OrderID=?";
		Object[] parameter = new Object[]{id};
		
		if(db.executeUpdateNotClose(sql, parameter)>0){
			flag = true;
		}
		
		return flag;
	}
	
	public boolean addSizeInfo(SizeInfo size){
		DBUtil db = new DBUtil();
		String sql = "insert into t_size_info (Dimension,GrossWeight,NetWeight,Quantity,ContractNO,PONO) values (?,?,?,?,?,?)";
		Object[] param = new Object[6];
		param[0] = size.getDimension();
		param[1] = size.getGrossWeight();
		param[2] = size.getNetWeight();
		param[3] = size.getQuantity();
		param[4] = size.getContractNO();
		param[5] = size.getPONO();

		boolean flag = false;
		try {
			if(db.executeUpdateNotClose(sql, param)>0){
				flag = true;
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return flag;
	}
	
}
