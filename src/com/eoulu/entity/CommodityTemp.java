package com.eoulu.entity;

public class CommodityTemp {

	private int ID;
	private int Commodity;
	private String OperatingTime;
	private int QuoteID;
	private String Description;
	private String PaymentDate;
	private String OrderNO;
	private int Qty;
	private double UnitUSD;
	private double DiscountedUSD;
	private double TotalPrice;
	private double DiscountedPercent;
	private String Remarks;
	
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public double getUnitUSD() {
		return UnitUSD;
	}
	public void setUnitUSD(double unitUSD) {
		UnitUSD = unitUSD;
	}
	public double getDiscountedUSD() {
		return DiscountedUSD;
	}
	public void setDiscountedUSD(double discountedUSD) {
		DiscountedUSD = discountedUSD;
	}
	public double getTotalPrice() {
		return TotalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		TotalPrice = totalPrice;
	}
	public double getDiscountedPercent() {
		return DiscountedPercent;
	}
	public void setDiscountedPercent(double discountedPercent) {
		DiscountedPercent = discountedPercent;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getCommodity() {
		return Commodity;
	}
	public void setCommodity(int commodity) {
		Commodity = commodity;
	}
	public String getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		OperatingTime = operatingTime;
	}
	public int getQuoteID() {
		return QuoteID;
	}
	public void setQuoteID(int quoteID) {
		QuoteID = quoteID;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getPaymentDate() {
		return PaymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		PaymentDate = paymentDate;
	}
	public String getOrderNO() {
		return OrderNO;
	}
	public void setOrderNO(String orderNO) {
		OrderNO = orderNO;
	}
	public int getQty() {
		return Qty;
	}
	public void setQty(int qty) {
		Qty = qty;
	}
	
	
	
}
