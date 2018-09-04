package com.eoulu.entity;

public class Reimburse {
	
	private int ID;
	private String Name;
	private String Department;
	private double TotalAmount;
	private String FilingDate;
	private String Pass;
	private String BillScreenshot;
	private String ElectronicInvoice;
	private String TravelPaper;
	private String Others;
	
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
	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}
	
	public double getTotalAmount() {
		return TotalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		TotalAmount = totalAmount;
	}
	public String getFilingDate() {
		return FilingDate;
	}
	public void setFilingDate(String filingDate) {
		FilingDate = filingDate;
	}
	public String getPass() {
		return Pass;
	}
	public void setPass(String pass) {
		Pass = pass;
	}
	public String getBillScreenshot() {
		return BillScreenshot;
	}
	public void setBillScreenshot(String billScreenshot) {
		BillScreenshot = billScreenshot;
	}
	public String getElectronicInvoice() {
		return ElectronicInvoice;
	}
	public void setElectronicInvoice(String electronicInvoice) {
		ElectronicInvoice = electronicInvoice;
	}
	public String getTravelPaper() {
		return TravelPaper;
	}
	public void setTravelPaper(String travelPaper) {
		TravelPaper = travelPaper;
	}
	public String getOthers() {
		return Others;
	}
	public void setOthers(String others) {
		Others = others;
	}
	
}
