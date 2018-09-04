/**
 * 
 */
package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eoulu.entity.OrderStatus;
import com.eoulu.entity.PaymentStatus;
import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 */
public class PaymentStatusDao {

	
	/**
	 * ͨ��paymentStatusID��ȡpaymentStatus
	 * 
	 * @param paymentStatus  
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * */
	public List<Map<String, Object>> getPaymentStatusByID(PaymentStatus paymentStatus){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select * from t_payment_status where ID=?";
		Object[] parameter = new Object[]{paymentStatus.getID()};
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	
	/**
	 * ͨ��Status��ȡID
	 * 
	 * @param Status  
	 * 
	 * @return int  0Ϊû������   ����0ΪID
	 * */
	public int getID(String Status){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select ID from t_payment_status where Status=?";
		Object[] parameter = new Object[]{Status};
		
		
		ls = db.QueryToList(sql, parameter);
		int id=0;
		if(ls.size()>1)
			id=Integer.parseInt(ls.get(1).get("ID").toString());
		return id;
	}
	
	
	/**
	 * ��ȡ���еĸ���״̬
	 * */
	public List<Map<String, Object>> getAllPaymentStatus(){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select t_payment_status.ID,t_payment_status.Status from t_payment_status";
		Object[] parameter = new Object[0];
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	
	
	/**
	 * �����Ƿ񸶿��Status����ѯ��Ӧ��ID�����������ֻ���ر�ͷ��ͨ���ж�list�ĳ��ȣ�����1�Ǵ��ڣ�����1˵��������
	 * */
	public List<Map<String, Object>> pamentStatusIsExit(String status){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select t_payment_status.ID,t_payment_status.Status from t_payment_status where t_payment_status.Status=?";
		Object[] parameter = new Object[]{status};
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	
	/**
	 * �����Ƿ񸶿�  ����ֵ1Ϊ�ɹ�������0Ϊʧ��
	 * */
	public int insert(String status){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="insert into t_payment_status (t_payment_status.Status) values (?)";
		Object[] parameter = new Object[]{status};
		
		
		int result = db.executeUpdate(sql, parameter);
		return result;
	}
	

}
