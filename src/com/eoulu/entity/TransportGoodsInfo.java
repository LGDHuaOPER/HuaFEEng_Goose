package com.eoulu.entity;

public class TransportGoodsInfo {

	private int ID;
	private String Model;
	private String Description;
	private String Size;
	private int Qty;
	private String OperatingTime;
	private int DirectiveID;
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
	public String getSize() {
		return Size;
	}
	public void setSize(String size) {
		Size = size;
	}
	public int getQty() {
		return Qty;
	}
	public void setQty(int qty) {
		Qty = qty;
	}
	public String getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		OperatingTime = operatingTime;
	}
	public int getDirectiveID() {
		return DirectiveID;
	}
	public void setDirectiveID(int directiveID) {
		DirectiveID = directiveID;
	}
	
	
}
