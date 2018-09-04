package com.eoulu.entity;

public class OriginGoods {

	private int ID;
	private int OriginID;
	private String Name;
	private String Model;
	private String Quality;
	private String OperatingTime;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getModel() {
		return Model;
	}
	public void setModel(String model) {
		Model = model;
	}
	public String getQuality() {
		return Quality;
	}
	public void setQuality(String quality) {
		Quality = quality;
	}
	public String getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		OperatingTime = operatingTime;
	}
	public int getOriginID() {
		return OriginID;
	}
	public void setOriginID(int originID) {
		OriginID = originID;
	}
	
	
}
