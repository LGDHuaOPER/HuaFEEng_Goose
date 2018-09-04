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
	 * 通过paymentStatusID获取paymentStatus
	 * 
	 * @param paymentStatus  
	 * 
	 * @return List 第一行为列名   以后为数据行
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
	 * 通过Status获取ID
	 * 
	 * @param Status  
	 * 
	 * @return int  0为没有数据   大于0为ID
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
	 * 获取所有的付款状态
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
	 * 根据是否付款的Status来查询对应的ID，如果不存在只返回表头，通过判断list的长度，大于1是存在，等于1说明不存在
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
	 * 插入是否付款  返回值1为成功，返回0为失败
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
