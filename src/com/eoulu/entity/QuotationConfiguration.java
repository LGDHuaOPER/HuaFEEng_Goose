/**
 * 
 */
package com.eoulu.entity;

/**
 * @author zhangkai
 *
 *��Ӧ�ı�Ϊt_quotation_configuration
 *报价配置
 */
public class QuotationConfiguration {

	private Integer OrderID;
	private Integer Number;
	private Integer LogisticsNumber;
	private Integer EquipmentModel;
	private Double RMBQuotes;
	private Double USDQuotes;
	private Integer PaymentTerms;
	
	private String OrderInfoID;
	private Double Price;
	public Integer getOrderID() {
		return OrderID;
	}
	public void setOrderID(Integer orderID) {
		OrderID = orderID;
	}
	public Integer getNumber() {
		return Number;
	}
	public void setNumber(Integer number) {
		Number = number;
	}
	public Integer getLogisticsNumber() {
		return LogisticsNumber;
	}
	public void setLogisticsNumber(Integer logisticsNumber) {
		LogisticsNumber = logisticsNumber;
	}
	public Integer getEquipmentModel() {
		return EquipmentModel;
	}
	public void setEquipmentModel(Integer equipmentModel) {
		EquipmentModel = equipmentModel;
	}
	public Double getRMBQuotes() {
		return RMBQuotes;
	}
	public void setRMBQuotes(Double rMBQuotes) {
		RMBQuotes = rMBQuotes;
	}
	public Double getUSDQuotes() {
		return USDQuotes;
	}
	public void setUSDQuotes(Double uSDQuotes) {
		USDQuotes = uSDQuotes;
	}
	public Integer getPaymentTerms() {
		return PaymentTerms;
	}
	public void setPaymentTerms(Integer paymentTerms) {
		PaymentTerms = paymentTerms;
	}
	public String getOrderInfoID() {
		return OrderInfoID;
	}
	public void setOrderInfoID(String orderInfoID) {
		OrderInfoID = orderInfoID;
	}
	public Double getPrice() {
		return Price;
	}
	public void setPrice(Double price) {
		Price = price;
	}
	
	
}
