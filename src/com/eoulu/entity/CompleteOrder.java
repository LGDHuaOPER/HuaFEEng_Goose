/**
 * 
 */
package com.eoulu.entity;

/**
 * @author zhangkai
 *
 *
 *完整的订单信息
 */
public class CompleteOrder {

	
	private Quotes quotes;
	private Order order;
	private OrderInfo orderInfo;
	
	public Quotes getQuotes() {
		return quotes;
	}
	public void setQuotes(Quotes quotes) {
		this.quotes = quotes;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}
	

	
	
	
}
