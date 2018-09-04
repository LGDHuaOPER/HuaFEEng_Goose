package com.eoulu.entity;

public class DeliveryAddress {

	private int ID;
	private String Name;
	private String Address;
	private String Applicant;
	private String Tel;
	private String OperatingTime;
	private int DirectiveID;
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
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getApplicant() {
		return Applicant;
	}
	public void setApplicant(String applicant) {
		Applicant = applicant;
	}
	public String getTel() {
		return Tel;
	}
	public void setTel(String tel) {
		Tel = tel;
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
