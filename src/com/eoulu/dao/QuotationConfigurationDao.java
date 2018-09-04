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

/** @author  作者 E-mail: 
* @date 创建时间：2017年5月24日 上午10:35:57 
* @version 1.0 * @parameter  
* @since  
* @return  
*/

public class QuotationConfigurationDao {

	/**
	 * 插入配置信息
	 * */
	public int insert(QuotationConfiguration quotationConfiguration){
		OrderInfoDao orderInfoDao = new OrderInfoDao();
		Logistics logistics = new Logistics();
		LogisticsDao logisticsDao = new LogisticsDao();
		//插入t_order_info表中
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
			//获取t_order_info表中的主键//插入t_logistics表中
			int id = orderInfoDao.getID(quotationConfiguration.getOrderID(), quotationConfiguration.getEquipmentModel(), db1);
			logistics.setOrderID(quotationConfiguration.getOrderID());
			logistics.setOrderInfoID(id);
			logisticsDao.insertEmpty(logistics, db1);
			conn.commit();
			conn.setAutoCommit(true);
			
			
			//插入t_quotes表中
			//插入t_quotes表中
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
				//插入t_quotes表中
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
	 * 修改t_quotes表中价格
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
	 * 删除配置信息OrderService orderService = new OrderServiceImpl();
	 * */
	public int delete(QuotationConfiguration quotationConfiguration){
		
		OrderService orderService = new OrderServiceImpl();
		int result = 0;
		if(orderService.deleteOrderInfo(quotationConfiguration.getOrderInfoID())){
			//修改t_quotates中的价格
						
			result= modifyQuotates(quotationConfiguration);
		}
		
		
		
		return result;
		
	}
	
	
	
	/**
	 * 查询配置信息
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
