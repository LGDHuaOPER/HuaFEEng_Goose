package com.eoulu.entity;

public class PackingGoods {

	private int ID;
	private String Model;
	private String Description;
	private String Qty;
	private String OperatingTime;
	private int PackingID;
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
	public String getQty() {
		return Qty;
	}
	public void setQty(String qty) {
		Qty = qty;
	}
	public String getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		OperatingTime = operatingTime;
	}
	public int getPackingID() {
		return PackingID;
	}
	public void setPackingID(int packingID) {
		PackingID = packingID;
	}
	
	
}
