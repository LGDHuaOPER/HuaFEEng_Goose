package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.PaymentRequest;
import com.eoulu.util.DBUtil;

public class PaymentRequestDao {
	
public boolean insert(PaymentRequest request){
		
		
		DBUtil dbUtil = new DBUtil();
		
		String sql = "insert into t_payment_request(Applicant,Department,ExpenseCategory,Amount,ExpenseItem,"
				+ "ExpenseDetails,ApplicationDate,PayDate,Attachment,Payee,Account,DepositBank,"
				+ "PaymentRemark,StoreName,OrderNO,Link,LinkRemark,SendState,PayState) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[19];
		param[0] = request.getApplicant();
		param[1] = request.getDepartment();
		param[2] = request.getExpenseCategory();
		param[3] = request.getAmount();
		param[4] = request.getExpenseItem();
		param[5] = request.getExpenseDetails();
		param[6] = request.getApplicationDate();
		param[7] = request.getPayDate();
		param[8] = request.getAttachment();
		param[9] = request.getPayee();
		param[10] = request.getAccount();
		param[11] = request.getDepositBank();
		param[12] = request.getPaymentRemark();
		param[13] = request.getStoreName();
		param[14] = request.getOrderNO();
		param[15] = request.getLink();
		param[16] = request.getLinkRemark();
		param[17] = "未发送";
		param[18] = "未付款";
		

		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
	}
	
	public boolean update(PaymentRequest request){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_payment_request set Applicant=?,Department=?,ExpenseCategory=?,"
				+ "Amount=?,ExpenseItem=?,ExpenseDetails=?,ApplicationDate=?,PayDate=?,Attachment=?,"
				+ "Payee=?,Account=?,DepositBank=?,PaymentRemark=?,StoreName=?,OrderNO=?,Link=?,LinkRemark=? where ID=?";
		Object[] param = new Object[18];
		param[0] = request.getApplicant();
		param[1] = request.getDepartment();
		param[2] = request.getExpenseCategory();
		param[3] = request.getAmount();
		param[4] = request.getExpenseItem();
		param[5] = request.getExpenseDetails();
		param[6] = request.getApplicationDate();
		param[7] = request.getPayDate();
		param[8] = request.getAttachment();
		param[9] = request.getPayee();
		param[10] = request.getAccount();
		param[11] = request.getDepositBank();
		param[12] = request.getPaymentRemark();
		param[13] = request.getStoreName();
		param[14] = request.getOrderNO();
		param[15] = request.getLink();
		param[16] = request.getLinkRemark();
		param[17] = request.getID();

	
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
		
	}
	public List<Map<String, Object>> getDataByPage(Page page){
		DBUtil dbUtil = new DBUtil();
		String sql = "select t_payment_request.ID,t_payment_request.Applicant,t_payment_request.Department,"
				+ "t_payment_request.ExpenseCategory,t_payment_request.Amount,t_payment_request.ExpenseItem,"
				+ "t_payment_request.ExpenseDetails,t_payment_request.ApplicationDate,t_payment_request.PayDate,"
				+ "t_payment_request.Attachment,t_payment_request.Payee,t_payment_request.Account,t_payment_request.DepositBank,"
				+ "t_payment_request.PaymentRemark,t_payment_request.StoreName,t_payment_request.OrderNO,t_payment_request.Link,"
				+ "t_payment_request.LinkRemark,t_payment_request.SendState,t_payment_request.PayState from t_payment_request "
				+ "order by t_payment_request.ID desc limit ?,?" ;
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return list;
	}
	
	public int getCounts(){
		DBUtil dbUtil = new DBUtil();
		String sql = "select COUNT(t_payment_request.ID) Count from t_payment_request ";
		
		List<Map<String, Object>> list = dbUtil.QueryToList(sql,null);
		return Integer.parseInt(list.get(1).get("Count").toString());
	}
	
	public boolean updateSendState(int ID){
		String sql = "update t_payment_request set SendState = '已发送' where ID = ?";
		DBUtil util = new DBUtil();
		
		int result = util.executeUpdate(sql, new Object[]{ID});
		
		return result>0?true:false;
	}
	
	public boolean updatePayState(int ID){
		String sql = "update t_payment_request set PayState = '已付款' where ID = ?";
		DBUtil util = new DBUtil();
		
		int result = util.executeUpdate(sql, new Object[]{ID});
		
		return result>0?true:false;
	}
	
	public List<Map<String, Object>> getMailInfo(int ID){
		
		String sql = "select Attachment,ExpenseDetails,Payee,Account,DepositBank,PaymentRemark,StoreName,OrderNO,Link,LinkRemark from "
				+ "t_payment_request where ID=?";
		
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{ID});
	}
	
	public List<Map<String, Object>> getAttachement(int ID){
		
		String sql = "select Attachment from t_payment_request where ID=?";
		
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{ID});
		
	}
	
	public boolean updateAttachemnt(int ID,String fileStr){
		String sql = "update t_payment_request set Attachment = ? where ID = ?";
		DBUtil dbUtil = new DBUtil();
		int result = dbUtil.executeUpdate(sql, new Object[]{fileStr,ID});
		return result > 0?true:false;
	}
	

}
