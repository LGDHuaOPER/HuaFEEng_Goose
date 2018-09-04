package com.eoulu.entity;

public class CommodityContracts {

	private int ID;
	private int QuoteID;
	private String CommodityModel;
	private String Remark;
	private String Unit;
	private String OperatingTime;
	private int Quantity;
	private double UnitPrice;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getQuoteID() {
		return QuoteID;
	}
	public void setQuoteID(int quoteID) {
		QuoteID = quoteID;
	}
	public String getCommodityModel() {
		return CommodityModel;
	}
	public void setCommodityModel(String commodityModel) {
		CommodityModel = commodityModel;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	public String getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		OperatingTime = operatingTime;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public double getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		UnitPrice = unitPrice;
	}
	
	
	
}
