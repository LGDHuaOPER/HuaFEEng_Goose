/**
 * 
 */
package com.eoulu.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.eoulu.entity.Quotes;
import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 */
public class QuotesDao {

	

	/**
	 * ͨ��OrderID��ȡquotes
	 * 
	 * @param quotes  
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * */
	public List<Map<String, Object>> getQuotesByID(Quotes quotes){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select * from t_quotes where OrderID=?";
		Object[] parameter = new Object[]{quotes.getOrderID()};


//		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	
	/**
	 * ͨ��OrderID��ȡquotes
	 * 
	 * @param quotes  
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * */
	public List<Map<String, Object>> getQuotesByID(String id){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select t_quotes.ReceiptDate1,t_quotes.ReceiptAmount1,t_quotes.ReceiptDate2,t_quotes.ReceiptAmount2,t_quotes.ReceiptDate3,t_quotes.ReceiptAmount3,"
				+ "t_quotes.BillingDate,t_quotes.RMBQuotes,t_payment_terms.ID PaymentTermsID,t_quotes.USDQuotes,t_payment_terms.Condition PaymentTerms,t_payment_status.Status WhetherToPay,t_duty_free.Status DutyFree,t_quotes.WhetherToInvoice,t_quotes.WhetherToPayRemarks,t_quotes.DutyFreeRemarks,t_quotes.WhetherToInvoiceRemarks,t_quotes.PayDate,t_quotes.TrackingNo from t_quotes left join t_payment_terms on t_quotes.PaymentTerms=t_payment_terms.ID left join t_payment_status on t_quotes.WhetherToPay=t_payment_status.ID left join "
				+ "t_duty_free on t_quotes.DutyFree=t_duty_free.ID  where OrderID=?";
		Object[] parameter = new Object[]{id};


		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * ��Ӻ�ͬ����
	 * 
	 * 
	 * 
	 * @param quotes ��ͬ����
	 * 
	 * @return boolean �ɹ�����true  ���򷵻�false
	 * @throws SQLException 
	 * */
	public boolean insert(Quotes quotes,DBUtil db) throws SQLException{

		boolean flag = false;
		String sql = "insert into t_quotes (OrderID,RMBQuotes,USDQuotes,PaymentTerms"
				+ ",WhetherToPay,WhetherToInvoice,DutyFree,WhetherToPayRemarks,WhetherToInvoiceRemarks,DutyFreeRemarks,"
				+ "ReceiptDate1,ReceiptAmount1,ReceiptDate2,ReceiptAmount2,ReceiptDate3,ReceiptAmount3,BillingDate,PayDate,TrackingNo) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		

		//��quotes�е���Ϣ��ֵ��Object������
		Object[] parameter = new Object[19];
		System.out.println("quotes:"+quotes.getOrderID());
		parameter[0] = quotes.getOrderID();
		parameter[1] = quotes.getRMBQuotes();
		parameter[2] = quotes.getUSDQuotes();
		parameter[3] = quotes.getPaymentTerms();
		parameter[4] = quotes.getWhetherToPay();
		parameter[5] = quotes.getWhetherToInvoice();
		parameter[6] = quotes.getDutyFree();
		parameter[7] = quotes.getWhetherToPayRemarks();
		parameter[8] = quotes.getWhetherToInvoiceRemarks();
		parameter[9] = quotes.getDutyFreeRemarks();
		
		parameter[10] = quotes.getReceiptDate1().equals("")?"0000-00-00":quotes.getReceiptDate1();
		parameter[11] = quotes.getReceiptAmount1();
		parameter[12] = quotes.getReceiptDate2().equals("")?"0000-00-00":quotes.getReceiptDate2();
		parameter[13] = quotes.getReceiptAmount2();
		parameter[14] = quotes.getReceiptDate3().equals("")?"0000-00-00":quotes.getReceiptDate3();
		parameter[15] = quotes.getReceiptAmount3();
		parameter[16] = quotes.getBillingDate().equals("")?"0000-00-00":quotes.getBillingDate();
		parameter[17] = quotes.getPayDate().equals("")?"0000-00-00":quotes.getPayDate();
		parameter[18] = quotes.getTrackingNo();
		
		int i = db.executeUpdateNotClose(sql, parameter);
		
		if(i>=1){
			flag = true;
		}

		return flag;
	}
	
	
	
	
	/**
	 * �޸ĺ�ͬ����
	 * 
	 * 
	 * 
	 * @param quotes ��ͬ����
	 * 
	 * @return boolean �ɹ�����true  ���򷵻�false
	 * */
	public boolean modify(Quotes quotes){

		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "update t_quotes set RMBQuotes=?,USDQuotes=?,PaymentTerms=?,WhetherToPay=?,WhetherToInvoice=?,DutyFree=? where OrderID=?";
		

		//��quotes�е���Ϣ��ֵ��Object������
		Object[] parameter = new Object[7];
		parameter[0] = quotes.getRMBQuotes();
		parameter[1] = quotes.getUSDQuotes();
		parameter[2] = quotes.getPaymentTerms();
		parameter[3] = quotes.getWhetherToPay();
		parameter[4] = quotes.getWhetherToInvoice();
		parameter[5] = quotes.getDutyFree();
		parameter[6] = quotes.getOrderID();
		

		int i = db.executeUpdate(sql, parameter);

		if(i>=1){
			flag = true;
		}

		return flag;
	}
	
	
	/**
	 * �޸ĺ�ͬ����
	 * 
	 * 
	 * 
	 * @param quotes ��ͬ����
	 * 
	 * @return boolean �ɹ�����true  ���򷵻�false
	 * @throws SQLException 
	 * */
	public boolean modify(Quotes quotes,DBUtil db) throws SQLException{

		boolean flag = false;
		String sql = "update t_quotes set RMBQuotes=?,USDQuotes=?,PaymentTerms=?,WhetherToPay=?,WhetherToInvoice=?,DutyFree=?,WhetherToPayRemarks=?,WhetherToInvoiceRemarks=?,DutyFreeRemarks=?,"
				+ "ReceiptDate1=?,ReceiptAmount1=?,ReceiptDate2=?,ReceiptAmount2=?,ReceiptDate3=?,ReceiptAmount3=?,BillingDate=?,PayDate=?,TrackingNo=? where OrderID=?";
		

		//��quotes�е���Ϣ��ֵ��Object������
		Object[] parameter = new Object[19];
		parameter[0] = quotes.getRMBQuotes();
		parameter[1] = quotes.getUSDQuotes();
		parameter[2] = quotes.getPaymentTerms();
		parameter[3] = quotes.getWhetherToPay();
		parameter[4] = quotes.getWhetherToInvoice();
		parameter[5] = quotes.getDutyFree();
		parameter[6] = quotes.getWhetherToPayRemarks();
		parameter[7] = quotes.getWhetherToInvoiceRemarks();
		parameter[8] = quotes.getDutyFreeRemarks();
		
		
		parameter[9] = quotes.getReceiptDate1().equals("")?"0000-00-00":quotes.getReceiptDate1();
		parameter[10] = quotes.getReceiptAmount1();
		parameter[11] = quotes.getReceiptDate2().equals("")?"0000-00-00":quotes.getReceiptDate2();
		parameter[12] = quotes.getReceiptAmount2();
		parameter[13] = quotes.getReceiptDate3().equals("")?"0000-00-00":quotes.getReceiptDate3();
		parameter[14] = quotes.getReceiptAmount3();
		parameter[15] = quotes.getBillingDate().equals("")?"0000-00-00":quotes.getBillingDate();
		parameter[16] = quotes.getPayDate().equals("")?"0000-00-00":quotes.getPayDate();
		parameter[17] = quotes.getTrackingNo();
		
		
		parameter[18] = quotes.getOrderID();
		

		int i = db.executeUpdateNotClose(sql, parameter);

		if(i>=1){
			flag = true;
		}

		return flag;
	}
	
	
	/**
	 * ͨ��orderIDɾ����Ӧorderid�ĺ�ͬ��ϸ��Ϣ
	 * @throws SQLException 
	 * */
	public boolean deleteQuotes(String id,DBUtil db) throws SQLException{
		boolean flag = false;
		
		String sql = "delete from t_quotes where OrderID=?";
		Object[] parameter = new Object[]{id};
		
		if(db.executeUpdateNotClose(sql, parameter)>0){
			flag = true;
		}
		
		return flag;
	}
	
}
