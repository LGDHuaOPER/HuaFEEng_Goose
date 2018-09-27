package com.eoulu.entity;

public class MachineDetails {

	private int ID;

	private String Model;
	private String SN;
	private String ContractNO;
	private String InstalledTime;
	private String OperatingTime;
	private int CustomerID;
	private int Status;
	private String Responsible;
	private String CurrentProgress;
	public int getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(int customerID) {
		CustomerID = customerID;
	}
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
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public String getContractNO() {
		return ContractNO;
	}
	public void setContractNO(String contractNO) {
		ContractNO = contractNO;
	}
	public String getInstalledTime() {
		return InstalledTime;
	}
	public void setInstalledTime(String installedTime) {
		InstalledTime = installedTime;
	}
	public String getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		OperatingTime = operatingTime;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public String getResponsible() {
		return Responsible;
	}
	public void setResponsible(String responsible) {
		Responsible = responsible;
	}
	public String getCurrentProgress() {
		return CurrentProgress;
	}
	public void setCurrentProgress(String currentProgress) {
		CurrentProgress = currentProgress;
	}
	

	
	
}
