package com.eoulu.entity;

public class ServiceReport {
	
	private int ID;
	private String Number;
	private String project;
	private String CustomerTitle;
	private String CustomerName;
	private String LinkInfo;
	private String StaffName;
	private String ContractNo;
	private String ProductVersion;
	private String FileName;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getNumber() {
		return Number;
	}
	public void setNumber(String number) {
		Number = number;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getCustomerTitle() {
		return CustomerTitle;
	}
	public void setCustomerTitle(String customerTitle) {
		CustomerTitle = customerTitle;
	}
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	public String getLinkInfo() {
		return LinkInfo;
	}
	public void setLinkInfo(String linkInfo) {
		LinkInfo = linkInfo;
	}
	public String getStaffName() {
		return StaffName;
	}
	public void setStaffName(String staffName) {
		StaffName = staffName;
	}
	public String getContractNo() {
		return ContractNo;
	}
	public void setContractNo(String contractNo) {
		ContractNo = contractNo;
	}
	public String getProductVersion() {
		return ProductVersion;
	}
	public void setProductVersion(String productVersion) {
		ProductVersion = productVersion;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	

	

}
