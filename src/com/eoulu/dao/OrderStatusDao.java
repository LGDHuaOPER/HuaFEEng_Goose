/**
 * 
 */
package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.entity.OrderInfo;
import com.eoulu.entity.OrderStatus;
import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 */
public class OrderStatusDao {

	/**
	 * 通过orderStatusID获取orderStatus
	 * 
	 * @param orderStatus  
	 * 
	 * @return List 第一行为列名   以后为数据行
	 * */
	public List<Map<String, Object>> getOrderStatusByID(OrderStatus orderStatus){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select * from t_order_status where ID=?";
		Object[] parameter = new Object[]{orderStatus.getID()};
		
		
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
		String sql="select ID from t_order_status where Status=?";
		Object[] parameter = new Object[]{Status};
		
		
		ls = db.QueryToList(sql, parameter);
		int id=0;
		if(ls.size()>1)
			id=Integer.parseInt(ls.get(1).get("ID").toString());
		return id;
	}
	
	
	/**
	 * 
	 * 获取所有的订单状态
	 * */
	public List<Map<String,Object>> getAllOrderStatus(){
		DBUtil db = new DBUtil();
		String sql = "select t_order_status.ID,t_order_status.Status  from t_order_status";
		
		
		return db.QueryToList(sql, new Object[0]);
	}
	
}
