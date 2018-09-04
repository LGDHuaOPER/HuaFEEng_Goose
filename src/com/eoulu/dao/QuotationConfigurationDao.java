package com.eoulu.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eoulu.entity.Logistics;
import com.eoulu.entity.QuotationConfiguration;
import com.eoulu.service.OrderService;
import com.eoulu.service.impl.OrderServiceImpl;
import com.eoulu.util.DBUtil;
import com.google.gson.Gson;
import com.mysql.jdbc.Connection;

/** @author  ���� E-mail: 
* @date ����ʱ�䣺2017��5��24�� ����10:35:57 
* @version 1.0 * @parameter  
* @since  
* @return  
*/

public class QuotationConfigurationDao {

	/**
	 * ����������Ϣ
	 * */
	public int insert(QuotationConfiguration quotationConfiguration){
		OrderInfoDao orderInfoDao = new OrderInfoDao();
		Logistics logistics = new Logistics();
		LogisticsDao logisticsDao = new LogisticsDao();
		//����t_order_info����
		String sql1 = "insert into t_order_info(OrderID,Number,LogisticsNumber,EquipmentModel,Status) values(?,?,?,?,?)";
		Object[] parameter1 = new Object[5];
		DBUtil db1 = new DBUtil();
		Connection conn = db1.getConnection();
		int result=0;
		try {
			conn.setAutoCommit(false);
			parameter1[0] = quotationConfiguration.getOrderID();
			parameter1[1] = quotationConfiguration.getNumber();
			parameter1[2] = quotationConfiguration.getLogisticsNumber();
			parameter1[3] = quotationConfiguration.getEquipmentModel();
			parameter1[4] = 5;
			int num=db1.executeUpdateNotClose(sql1, parameter1);
			//��ȡt_order_info���е�����//����t_logistics����
			int id = orderInfoDao.getID(quotationConfiguration.getOrderID(), quotationConfiguration.getEquipmentModel(), db1);
			logistics.setOrderID(quotationConfiguration.getOrderID());
			logistics.setOrderInfoID(id);
			logisticsDao.insertEmpty(logistics, db1);
			conn.commit();
			conn.setAutoCommit(true);
			
			
			//����t_quotes����
			//����t_quotes����
			String sql3 = "select RMBQuotes from  t_quotes where OrderID=?";
			Object[] parameter3 = new Object[]{quotationConfiguration.getOrderID()};
			DBUtil db3 = new DBUtil();
			List<Map<String,Object>> ls = db3.QueryToList(sql3, parameter3);
			if(ls.size()<2){
				parameter3[0] = quotationConfiguration.getOrderID();
				
				String sql = "insert into t_quotes(OrderID,RMBQuotes,USDQuotes,PaymentTerms,WhetherToPay,DutyFree,WhetherToInvoice) values(?,?,?,?,?,?,?)";
				Object[] parameter = new Object[7];
				DBUtil db = new DBUtil();
				
				parameter[0] = quotationConfiguration.getOrderID();
				parameter[1] = quotationConfiguration.getRMBQuotes();
				parameter[2] = quotationConfiguration.getUSDQuotes();
				parameter[3] = quotationConfiguration.getPaymentTerms();
				parameter[4] = 13;
				parameter[5] = 6;
				parameter[6] = 0;
				
				result = db.executeUpdate(sql, parameter);
			}else{
				//����t_quotes����
				String sql2 = "update t_quotes set RMBQuotes=?,USDQuotes=? where OrderID=?";
				Object[] parameter2 = new Object[3];
				DBUtil db2 = new DBUtil();
				
				
				parameter2[0] = quotationConfiguration.getRMBQuotes();
				parameter2[1] = quotationConfiguration.getUSDQuotes();
				parameter2[2] = quotationConfiguration.getOrderID();
				
				result = db2.executeUpdate(sql2, parameter2);
			}
			
		} catch (SQLException e) {
			try {
				conn.rollback();
				conn.setAutoCommit(true);
				result= 0;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return result;
		
		
		
	}
	
	
	
	
	
	/**
	 * �޸�t_quotes���м۸�
	 * */
	public int modifyQuotates(QuotationConfiguration quotationConfiguration){
		
		String sql = "update t_quotates set RMBQuotes= ?,USDQuotes= ? where OrderID=?";
		Object[] parameter = new Object[3];
		DBUtil db = new DBUtil();
		
		
		parameter[0] = quotationConfiguration.getRMBQuotes();
		parameter[1] = quotationConfiguration.getUSDQuotes();
		parameter[2] = quotationConfiguration.getOrderID();

		int result = db.executeUpdate(sql, parameter);
		
		
		return result;
		
	}
	/**
	 * ɾ��������ϢOrderService orderService = new OrderServiceImpl();
	 * */
	public int delete(QuotationConfiguration quotationConfiguration){
		
		OrderService orderService = new OrderServiceImpl();
		int result = 0;
		if(orderService.deleteOrderInfo(quotationConfiguration.getOrderInfoID())){
			//�޸�t_quotates�еļ۸�
						
			result= modifyQuotates(quotationConfiguration);
		}
		
		
		
		return result;
		
	}
	
	
	
	/**
	 * ��ѯ������Ϣ
	 * */
	public List<Map<String,Object>> query(int id){
		
		String sql = "select t_quotation_configuration.ID,t_quotation_configuration.QuotationID,t_equipment.ID EquipmentID,"
				+ "t_quotation_configuration.Counts,t_quotation_configuration.UnitPrice,t_quotation_configuration.PONumber,"
				+ "t_equipment.Model,t_equipment.Remarks,t_equipment.EquipmentUnit,t_equipment.DeliveryTime,t_equipment.SourceArea,"
				+ "t_supplier.Name SupplierName from t_quotation_configuration LEFT JOIN t_equipment on "
				+ "t_quotation_configuration.Equipment=t_equipment.ID LEFT JOIN t_supplier on t_equipment.Supplier=t_supplier.ID where QuotationID=?";
		
		Object[] parameter = new Object[]{id};
		DBUtil db = new DBUtil();
		
		
		
		List<Map<String,Object>> result = db.QueryToList(sql, parameter);
		
		
		return result;
		
	}
	
//	@Test
//	public void test(){
//		
//		for(int i=0; i<5; i++){
//		QuotationConfiguration quotationConfiguration = new QuotationConfiguration();
//		quotationConfiguration.setCounts(10);
//		quotationConfiguration.setQuotationID(70);
//		quotationConfiguration.setEquipment(10);
//		quotationConfiguration.setPONumber("POSEL12345");
//		quotationConfiguration.setUnitPrice(56);
//		quotationConfiguration.setID(3);
//		
//		
//		System.out.println(insert(quotationConfiguration));
//	}
		
	
}
