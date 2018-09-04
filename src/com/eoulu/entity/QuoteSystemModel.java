package com.eoulu.entity;

public class QuoteSystemModel {

	private int ID;
	private int QuoteID;
	private double SubTotal;
	private double FinalTotal;
	private double GiftsTotal;
	private String Gifts;
	private String Type;
	private String OperatingTime;
	private String ModelJson;
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
	public double getSubTotal() {
		return SubTotal;
	}
	public void setSubTotal(double subTotal) {
		SubTotal = subTotal;
	}
	public double getFinalTotal() {
		return FinalTotal;
	}
	public void setFinalTotal(double finalTotal) {
		FinalTotal = finalTotal;
	}
	public double getGiftsTotal() {
		return GiftsTotal;
	}
	public void setGiftsTotal(double giftsTotal) {
		GiftsTotal = giftsTotal;
	}
	public String getGifts() {
		return Gifts;
	}
	public void setGifts(String gifts) {
		Gifts = gifts;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		OperatingTime = operatingTime;
	}
	public String getModelJson() {
		return ModelJson;
	}
	public void setModelJson(String modelJson) {
		ModelJson = modelJson;
	}
	
	
	
}
