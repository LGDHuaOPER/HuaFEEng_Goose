package com.eoulu.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eoulu.entity.Quotation;
import com.eoulu.util.DBUtil;

/** @author  ���� : zhangkai
* @date ����ʱ�䣺2017��5��24�� ����10:39:40 
* @version 1.0 
* @since  
* @return  
*/
public class QuotationDao {

	/**
	 * ���뱨�۵���Ϣ
	 * */
	public int insert(Quotation quotation){
		Date date=new Date();
		String ContractNo=String.valueOf(date.getTime());
		//�Ȳ���Order����
		String sql1="insert into "
				+ "t_order (Customer,Contact,ContactInfo,SalesRepresentative,ContractNo,Area,Status,ContractCategory,flag) "
				+ "values (?,?,?,?,?,?,?,?,?)";
		Object[] parameter1 = new Object[9];
		DBUtil db1 = new DBUtil();
		parameter1[0] = quotation.getCustomer();
		parameter1[1] = quotation.getContact();
		parameter1[2] = quotation.getContactInfo();
		parameter1[3] = quotation.getSalesRepresentative();
		parameter1[4] = ContractNo;
		parameter1[5] = null;
		parameter1[6] = 9;
		parameter1[7] = 8;
		parameter1[8] = 0;
		db1.executeUpdate(sql1, parameter1);
		
		//��ȡ�������
		String sql2="select t_order.ID ID from t_order where ContractNo=? ";
		Object[] parameter2 = new Object[]{ContractNo};
		DBUtil db2 = new DBUtil();
		String OrderID=(String) db2.QueryToList(sql2, parameter2).get(1).get("ID");
		
		//������t_quotation_list��
		String sql = "insert into t_quotation_list (QuotationID,OrderID,QuotationDate,ContractType,Version,Valid,LeadTime,"
				+ "ExchangeRate,ChargeDuty,ModifyTime,PaymentTermID) values (?,?,?,?,?,?,?,?,?,?,?)";
		Object[] parameter = new Object[11];
		DBUtil db = new DBUtil();
		
		Calendar cale = Calendar.getInstance();
		
		parameter[0] = quotation.getQuotationID();
		parameter[1] = OrderID;
		parameter[2] = quotation.getQuotationDate();
		parameter[3] = quotation.getContractType();
		parameter[4] = quotation.getVersion();
		parameter[5] = quotation.getValid();
		parameter[6] = quotation.getLeadTime();
		parameter[7] = quotation.getExchangeRate();
		parameter[8] = quotation.getChargeDuty();
		parameter[9] = cale.getTime().toLocaleString();
		parameter[10] = quotation.getPaymentTermID();
		
		int result = db.executeUpdate(sql, parameter);
		
		return result;
		
	}
	
	
	
	/**
	 * �޸ı��۵���Ϣ
	 * */
	public int modify(Quotation quotation){
		//�޸�t_order����Ϣ
		String sql1="update t_order set Customer=?,Contact=?,ContactInfo=?,SalesRepresentative=? where ID= ? ";
		Object[] parameter1 = new Object[5];
		DBUtil db1 = new DBUtil();
		parameter1[0] = quotation.getCustomer();
		parameter1[1] = quotation.getContact();
		parameter1[2] = quotation.getContactInfo();
		parameter1[3] = quotation.getSalesRepresentative();
		parameter1[4] = quotation.getOrderID();
		db1.executeUpdate(sql1, parameter1);
		
		//�޸�t_quotation_list����Ϣ
		String sql = "update t_quotation_list set QuotationID= ?,QuotationDate= ?,ContractType= ?,Version= ?,Valid= ?,LeadTime=? ,"
				+ "ExchangeRate= ?,ChargeDuty= ?,ModifyTime= ?,PaymentTermID=? where ID= ?";
		
		Object[] parameter = new Object[11];
		DBUtil db = new DBUtil();
		
		//���۵����ۼƼ�1
		String QuotationID1=quotation.getQuotationID1();
	    String str = String.format("%03d", Integer.parseInt(QuotationID1.substring(12))+1);  
	    String QuotationID2="QU"+QuotationID1.substring(2, 6)+QuotationID1.substring(6, 12)+str;
		Calendar cale = Calendar.getInstance();
		parameter[0] = QuotationID2;
		parameter[1] = quotation.getQuotationDate();
		parameter[2] = quotation.getContractType();
		parameter[3] = quotation.getVersion();
		parameter[4] = quotation.getValid();
		parameter[5] = quotation.getLeadTime();
		parameter[6] = quotation.getExchangeRate();
		parameter[7] = quotation.getChargeDuty();
		parameter[8] = cale.getTime().toLocaleString();
		parameter[9] = quotation.getPaymentTermID();
		parameter[10] = quotation.getID();
		
		
		int result = db.executeUpdate(sql, parameter);
		
		return result;
		
	}
	
	
	
	
	/**
	 * ��ѯ�����еı��۵���Ϣ
	 * */
	public List<Map<String,Object>> query(){
		
		String sql = "select t_customer.ID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1,t_sales_representative.ID,t_sales_representative.Name,t_sales_representative.Dept,t_quotation.ExchangeRate,t_quotation.ValidPeriod,"
				+ "t_quotation.Taxation,t_quotation.Freight,t_quotation.CargoPeriod,t_quotation.CheckoutMethod,t_quotation.TransactionType,t_quotation.DeliveryMethod from t_quotation LEFT JOIN t_sales_representative on t_quotation.Clerk="
				+ "t_sales_representative.ID LEFT JOIN t_customer on t_quotation.Customer=t_customer.ID";
		
		Object[] parameter = new Object[0];
		DBUtil db = new DBUtil();
		
		
		
		List<Map<String,Object>> result = db.QueryToList(sql, parameter);
		
		return result;
		
	}
	
	
	
	
	/**
	 * ִ�в�ѯ����䣬���Զ��������sql  ���
	 * */
	public List<Map<String,Object>> executeQuery(DBUtil db,Object[] parameter,String sql){
		
		
		List<Map<String,Object>> result = db.QueryToList(sql, parameter);
		
		return result;
		
	}
	
	
	
	/**
	 * ִ��sql���,����ĵ�
	 * */
	public int executeUpdate(DBUtil db,Object[] parameter,String sql){
		
		
		int result = db.executeUpdate(sql, parameter);
		
		return result;
		
	}
	
	/**
	 * ��ѯ���м�¼������
	 * */
	public int getRecordsCounts(){
		//String sql = "select count(ID) ID from t_quotation";
		String sql = "select count(QuotationID) ID from t_quotation_list";
		Object[] parameter = new Object[0];
		DBUtil db = new DBUtil();
		
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		
		int result = 0;
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("ID").toString());
		
		
		return result;
	}
	
	@Test
	public void test(){
		
		int k = 0;
		/*for(int i=0;i<50;i++){
			k = i%10;
			Quotation quotation = new Quotation();
			quotation.setCustomer(k+"");
			quotation.setClerk(k+"");
			quotation.setExchangeRate(k);
			quotation.setValidPeriod(k+"");
			quotation.setTaxation(i+"");
			quotation.setQuoteVersion(k+"");
			quotation.setFreight(k+"");
			quotation.setCargoPeriod(k+"");
			quotation.setCheckoutMethod(k+"");
			quotation.setTransactionType(k+"");
			quotation.setDeliveryMethod(k+"");
			quotation.setID(k);
		System.out.println(insert(quotation));
		}*/
		
	}
	
}
