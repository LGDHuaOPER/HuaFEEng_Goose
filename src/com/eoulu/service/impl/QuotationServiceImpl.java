package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.dao.QuotationConfigurationDao;
import com.eoulu.dao.QuotationDao;
import com.eoulu.entity.Quotation;
import com.eoulu.entity.QuotationConfiguration;
import com.eoulu.service.QuotationService;
import com.eoulu.util.DBUtil;
import com.google.gson.Gson;

/** @author  作者 : zhangkai
 * @date 创建时间：2017年5月24日 下午3:11:16 
 * @version 1.0  
 * @since  
 * @return  
 */
public class QuotationServiceImpl implements QuotationService {

	/* (non-Javadoc)
	 * @see com.eoulu.service.QuotationService#getQuotationByPage(com.eoulu.commonality.Page)
	 */
	/**
	 * 按照分页查看报价信息
	 * */
	@Override
	public List<Map<String, Object>> getQuotationByPage(Page page) {

		/*String sql = "select t_quotation.ID,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1,t_sales_representative.ID SalesID,t_sales_representative.Name SalesName,t_sales_representative.Dept,t_quotation.ExchangeRate,t_quotation.ValidPeriod,"
				+ "t_quotation.Taxation,t_quotation.Freight,t_quotation.CargoPeriod,t_payment_terms.Condition CheckoutMethod,t_payment_terms.ID CheckoutMethodID,t_quotation.TransactionType,t_quotation.DeliveryMethod from t_quotation LEFT JOIN t_sales_representative on t_quotation.Clerk="
				+ "t_sales_representative.ID LEFT JOIN t_customer on t_quotation.Customer=t_customer.ID left join t_payment_terms on t_quotation.CheckoutMethod=t_payment_terms.ID order by t_quotation.InsertDate desc limit ?,?";*/
		String sql="SELECT t_quotation_list.ID,t_quotation_list.OrderID,t_quotation_list.QuotationID,t_quotation_list.QuotationDate,t_quotation_list.Version,t_quotation_list.ContractType, "
				+ "t_quotation_list.Valid,t_quotation_list.ExchangeRate,t_quotation_list.ChargeDuty,t_quotation_list.LeadTime, "
				+ "t_order.Customer,t_order.Contact,t_order.ContactInfo,t_sales_representative.`Name` Name,t_sales_representative.Contact SaleContact,t_sales_representative.Email,t_customer.Email ContactEmail,t_payment_terms.ID paymentID,t_payment_terms.Condition  "
				+ "from t_quotation_list LEFT JOIN t_order on t_quotation_list.OrderID=t_order.ID "
				+ "LEFT JOIN t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID "
				+ "left join t_customer on t_order.Contact=t_customer.Contact and t_order.Customer=t_customer.CustomerName "
				+ "left join t_payment_terms on t_payment_terms.ID=t_quotation_list.PaymentTermID "
				+ "order by t_quotation_list.ModifyTime desc limit ?,?";
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		DBUtil db = new DBUtil();

		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);

		//		int length = ls.size();
		//
		//		for(int i=1; i<length; i++){
		//
		//			int id = Integer.parseInt(ls.get(i).get("ID").toString());
		//			List<Map<String,Object>> quotConf = new QuotationConfigurationDao().query(id);
		//			ls.get(i).put("quotationConfigurations", new Gson().toJson(quotConf));
		//
		//		}

		return ls;
	}

	/* (non-Javadoc)
	 * @see com.eoulu.service.QuotationService#getQuotationCounts()
	 */
	/**
	 * 获取报价信息的数量
	 * */
	@Override
	public int getQuotationCounts() {
		// TODO Auto-generated method stub
		return new QuotationDao().getRecordsCounts();
	}

	/**
	 * 插入报价信息
	 * */
	@Override
	public boolean insertQuotation(Quotation quotation) {
		boolean flag = false;

		flag = new QuotationDao().insert(quotation)==1?true:false;

		return flag;
	}

	/**
	 * 修改报价信息
	 * */
	@Override
	public boolean modifyQuotation(Quotation quotation) {
		boolean flag = false;

		flag = new QuotationDao().modify(quotation)==1?true:false;

		return flag;
	}

	/* (non-Javadoc)
	 * @see com.eoulu.service.QuotationService#getQuotationConf(int)
	 */
	@Override
	public List<Map<String, Object>> getQuotationConf(int id) {
		List<Map<String,Object>> quotConf = new QuotationConfigurationDao().query(id);
		return quotConf;
	}

	/* (non-Javadoc)
	 * @see com.eoulu.service.QuotationService#insertQuotationConf(com.eoulu.entity.QuotationConfiguration)
	 */
	/**
	 * 插入配置信息
	 * */
	@Override
	public boolean insertQuotationConf(QuotationConfiguration configuration) {
		QuotationConfigurationDao quotationConfigurationDao = new QuotationConfigurationDao();

		boolean flag = quotationConfigurationDao.insert(configuration)>0;

		return flag;
	}
	@Override
	public boolean deleteQuotationConf(QuotationConfiguration configuration) {
		QuotationConfigurationDao quotationConfigurationDao = new QuotationConfigurationDao();
		
		boolean flag = quotationConfigurationDao.delete(configuration)>0;

		return flag;
	}
	/* (non-Javadoc)
	 * @see com.eoulu.service.QuotationService#modifyQuotationConf(com.eoulu.entity.QuotationConfiguration)
	 */
	/**
	 * 修改配置信息
	 * *//*
	
	public boolean modifyQuotationConf(QuotationConfiguration configuration) {
		boolean flag = false;

		String sql = "update t_quotation_configuration set Counts=?,UnitPrice=?,PONumber=? where  ID=?";

		Object[] parameter = new Object[4];
		DBUtil db = new DBUtil();

		parameter[0] = configuration.getCounts();
		parameter[1] = configuration.getUnitPrice();
		parameter[2] = configuration.getPONumber();
		parameter[3] = configuration.getID();


		int result = db.executeUpdate(sql, parameter);


		flag =  (result==1);


		return flag;
	}*/

	/**
	 * 修改配置信息
	 * 
	 * 修改t_orderinfo表中的数量
	 * 
	 * 修改t_equipment表中的单价
	 * 
	 * 修改t_quotes表中价格
	 * 
	 * */
	@Override
	public boolean modifyQuotationConf(QuotationConfiguration quotationConfiguration){
		boolean flag = false;
		String sql = "update t_order_info set Number= ? ,LogisticsNumber= ?  where OrderID=? and EquipmentModel=?";
		
		Object[] parameter = new Object[4];
		DBUtil db = new DBUtil();
		
		parameter[0] = quotationConfiguration.getNumber();
		parameter[1] = quotationConfiguration.getLogisticsNumber();
		parameter[2] = quotationConfiguration.getOrderID();
		parameter[3] = quotationConfiguration.getEquipmentModel();
		
		db.executeUpdate(sql, parameter);
		
		String sql1="update t_equipment set price=? where t_equipment.ID=? ";
		Object[] parameter1 = new Object[2];
		parameter1[0] = quotationConfiguration.getPrice();
		parameter1[1] = quotationConfiguration.getOrderInfoID();
		DBUtil db1 = new DBUtil();
		int result =db1.executeUpdate(sql1, parameter1);
		QuotationConfigurationDao QuotationConfigurationDao=new QuotationConfigurationDao();
		
		QuotationConfigurationDao.modifyQuotates(quotationConfiguration);
		flag =  (result==1);
		return flag;
		
	}

	@Override
	public List<Map<String, Object>> getQuotationByQuotationID(String QuotationID) {
		String sql="SELECT t_quotation_list.QuotationDate,t_quotation_list.Version,t_quotation_list.ContractType, "
				+ "t_quotation_list.Valid,t_quotation_list.ExchangeRate,t_quotation_list.ChargeDuty,t_quotation_list.LeadTime, "
				+ "t_order.Customer,t_order.Contact,t_order.ContactInfo,t_sales_representative.`Name` Name,t_sales_representative.Contact SaleContact,t_sales_representative.Email  "
				+ "from t_quotation_list LEFT JOIN t_order on t_quotation_list.OrderID=t_order.ID "
				+ "LEFT JOIN t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID where t_quotation_list.QuotationID=? ";
		Object[] parameter = new Object[]{QuotationID};
		DBUtil db = new DBUtil();

		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);

		//		int length = ls.size();
		//
		//		for(int i=1; i<length; i++){
		//
		//			int id = Integer.parseInt(ls.get(i).get("ID").toString());
		//			List<Map<String,Object>> quotConf = new QuotationConfigurationDao().query(id);
		//			ls.get(i).put("quotationConfigurations", new Gson().toJson(quotConf));
		//
		//		}

		return ls;
	}



}
