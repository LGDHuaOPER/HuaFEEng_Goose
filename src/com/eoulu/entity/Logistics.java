/**
 * 
 */
package com.eoulu.entity;

/**
 * @author zhangkai
 * 
 *@date 2017/3/21
 *
 *������Ϣ��ʵ����  logistics
 *物流
 */
public class Logistics {

	private int OrderID;
	private int OrderInfoID;
	private int Supplier;
	private String PONO;
	private String SONO;
	private float POAmount;
	private float RMBPOAmount;
	private String FactoryShipment;
	private String EstimatedPaymentTime;
	private String ActualPaymentTime;
	
	
	public int getOrderID() {
		return OrderID;
	}
	public void setOrderID(int orderID) {
		OrderID = orderID;
	}
	public int getOrderInfoID() {
		return OrderInfoID;
	}
	public void setOrderInfoID(int orderInfoID) {
		OrderInfoID = orderInfoID;
	}
	public int getSupplier() {
		return Supplier;
	}
	public void setSupplier(int supplier) {
		Supplier = supplier;
	}
	public String getPONO() {
		return PONO;
	}
	public void setPONO(String pONO) {
		PONO = pONO;
	}
	public String getSONO() {
		return SONO;
	}
	public void setSONO(String sONO) {
		SONO = sONO;
	}
	public float getPOAmount() {
		return POAmount;
	}
	public void setPOAmount(float pOAmount) {
		POAmount = pOAmount;
	}
	public float getRMBPOAmount() {
		return RMBPOAmount;
	}
	public void setRMBPOAmount(float rMBPOAmount) {
		RMBPOAmount = rMBPOAmount;
	}
	public String getFactoryShipment() {
		return FactoryShipment;
	}
	public void setFactoryShipment(String factoryShipment) {
		FactoryShipment = factoryShipment;
	}
	public String getEstimatedPaymentTime() {
		return EstimatedPaymentTime;
	}
	public void setEstimatedPaymentTime(String estimatedPaymentTime) {
		EstimatedPaymentTime = estimatedPaymentTime;
	}
	public String getActualPaymentTime() {
		return ActualPaymentTime;
	}
	public void setActualPaymentTime(String actualPaymentTime) {
		ActualPaymentTime = actualPaymentTime;
	}
	
	
	
	
	
	
	
	
	
}
