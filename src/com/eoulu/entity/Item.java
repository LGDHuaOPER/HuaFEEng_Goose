package com.eoulu.entity;

public class Item {

	private int ID;
	private int InvoiceID;
	private String Goods;
	private String Unit;
	private int Qty;
	private Double UnitUSDPrice;
	private Double TotalUSDAmount;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getInvoiceID() {
		return InvoiceID;
	}
	public void setInvoiceID(int invoiceID) {
		InvoiceID = invoiceID;
	}
	public String getGoods() {
		return Goods;
	}
	public void setGoods(String goods) {
		Goods = goods;
	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	
	public Double getUnitUSDPrice() {
		return UnitUSDPrice;
	}
	public void setUnitUSDPrice(Double unitUSDPrice) {
		UnitUSDPrice = unitUSDPrice;
	}
	public Double getTotalUSDAmount() {
		return TotalUSDAmount;
	}
	public void setTotalUSDAmount(Double totalUSDAmount) {
		TotalUSDAmount = totalUSDAmount;
	}
	public int getQty() {
		return Qty;
	}
	public void setQty(int qty) {
		Qty = qty;
	}
	
	
	
	
}
