package com.eoulu.entity;

public class CustomerInquiry {
	
	private int ID;
	private int CustomerID;
	private String PreSalesTable;
	private String QuoteTime;
	private String ServiceTime;
	private double TotalPrice;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(int customerID) {
		CustomerID = customerID;
	}
	public String getPreSalesTable() {
		return PreSalesTable;
	}
	public void setPreSalesTable(String preSalesTable) {
		PreSalesTable = preSalesTable;
	}
	public String getQuoteTime() {
		return QuoteTime;
	}
	public void setQuoteTime(String quoteTime) {
		QuoteTime = quoteTime;
	}
	public String getServiceTime() {
		return ServiceTime;
	}
	public void setServiceTime(String serviceTime) {
		ServiceTime = serviceTime;
	}
	public double getTotalPrice() {
		return TotalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		TotalPrice = totalPrice;
	}

}
