package com.eoulu.entity;

public class QuoteDelivery {

	private int ID;
	private String Model;
	private String Description;
	private String Remarks;
	private int Quantity;
	private String OperatingTime;
	private int QuoteID;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getModel() {
		return Model;
	}
	public void setModel(String model) {
		Model = model;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
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
	
	
}
