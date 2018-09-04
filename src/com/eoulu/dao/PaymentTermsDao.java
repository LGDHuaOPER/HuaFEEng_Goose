/**
 * 
 */
package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eoulu.entity.PaymentStatus;
import com.eoulu.entity.PaymentTerms;
import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 */
public class PaymentTermsDao {


	/**
	 * ͨ��paymentTermsID��ȡpaymentTerms
	 * 
	 * @param paymentTerms  
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * */
	public List<Map<String, Object>> getPaymentTermsByID(PaymentTerms paymentTerms){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select * from t_payment_terms where ID=?";
		Object[] parameter = new Object[]{paymentTerms.getID()};


		ls = db.QueryToList(sql, parameter);
		return ls;
	}


	/**
	 * ͨ��Condition��ȡID
	 * 
	 * @param Condition  
	 * 
	 * @return int  0Ϊû������   ����0ΪID
	 * */
	public int getID(String Status){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select ID from t_payment_terms where t_payment_terms.`Condition`=?";
		Object[] parameter = new Object[]{Status};


		ls = db.QueryToList(sql, parameter);
		int id=0;
		if(ls.size()>1)
			id=Integer.parseInt(ls.get(1).get("ID").toString());
		return id;
	}
	
	
	
	
	/**
	 * ���븶������  ����ֵ1Ϊ�ɹ�������0Ϊʧ��
	 * */
	public int insert(String condition){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="insert into t_payment_terms (t_payment_terms.Condition) values (?)";
		Object[] parameter = new Object[]{condition};
		
		
		int result = db.executeUpdate(sql, parameter);
		return result;
	}

	
	

	/**
	 * ���ݸ���������condition����ѯ��Ӧ��ID�����������ֻ���ر�ͷ��ͨ���ж�list�ĳ��ȣ�����1�Ǵ��ڣ�����1˵��������
	 * */
	public List<Map<String, Object>> pamentStatusIsExit(String condition){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select t_payment_terms.ID,t_payment_terms.Condition from t_payment_terms where t_payment_terms.Condition=?";
		Object[] parameter = new Object[]{condition};
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	
	
	//��ȡ���еĸ�������
	public List<Map<String, Object>> getAllPayTerms(){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select t_payment_terms.ID,t_payment_terms.Condition  from t_payment_terms";
		Object[] parameter = new Object[0];


		ls = db.QueryToList(sql, parameter);
		return ls;
	}
}
