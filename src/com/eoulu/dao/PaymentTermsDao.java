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
	 * 通过paymentTermsID获取paymentTerms
	 * 
	 * @param paymentTerms  
	 * 
	 * @return List 第一行为列名   以后为数据行
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
	 * 通过Condition获取ID
	 * 
	 * @param Condition  
	 * 
	 * @return int  0为没有数据   大于0为ID
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
	 * 插入付款条件  返回值1为成功，返回0为失败
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
	 * 根据付款条件的condition来查询对应的ID，如果不存在只返回表头，通过判断list的长度，大于1是存在，等于1说明不存在
	 * */
	public List<Map<String, Object>> pamentStatusIsExit(String condition){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select t_payment_terms.ID,t_payment_terms.Condition from t_payment_terms where t_payment_terms.Condition=?";
		Object[] parameter = new Object[]{condition};
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	
	
	//获取所有的付款条件
	public List<Map<String, Object>> getAllPayTerms(){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select t_payment_terms.ID,t_payment_terms.Condition  from t_payment_terms";
		Object[] parameter = new Object[0];


		ls = db.QueryToList(sql, parameter);
		return ls;
	}
}
