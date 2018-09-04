package com.eoulu.entity;

public class SoftwareImplementation {

	private int ID;
	private int InquiryID;
	private int CustomerID;
	private String Machine;
	private String SoftwareVersion;
	private String Type;
	private String InstallTime;
	private String ContinueTime;
	private String Engineer;
	private String MachineCode;
	private String SN;
	private String RegistrationCode;
	private String Email;
	private String InstallationReport;
	private String ImplementHandbook;
	private String TechnologyProtocol;
	private String OperatingTime;
	private String Remarks;
	private String preSalesTable;
	private String ScriptBackup;
	
	
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getInquiryID() {
		return InquiryID;
	}
	public void setInquiryID(int inquiryID) {
		InquiryID = inquiryID;
	}
	public String getMachine() {
		return Machine;
	}
	public void setMachine(String machine) {
		Machine = machine;
	}
	public String getSoftwareVersion() {
		return SoftwareVersion;
	}
	public void setSoftwareVersion(String softwareVersion) {
		SoftwareVersion = softwareVersion;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getInstallTime() {
		return InstallTime;
	}
	public void setInstallTime(String installTime) {
		InstallTime = installTime;
	}
	public String getContinueTime() {
		return ContinueTime;
	}
	public void setContinueTime(String continueTime) {
		ContinueTime = continueTime;
	}
	public String getEngineer() {
		return Engineer;
	}
	public void setEngineer(String engineer) {
		Engineer = engineer;
	}

	public String getMachineCode() {
		return MachineCode;
	}
	public void setMachineCode(String machineCode) {
		MachineCode = machineCode;
	}
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public String getRegistrationCode() {
		return RegistrationCode;
	}
	public void setRegistrationCode(String registrationCode) {
		RegistrationCode = registrationCode;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}

	public String getInstallationReport() {
		return InstallationReport;
	}
	public void setInstallationReport(String installationReport) {
		InstallationReport = installationReport;
	}
	public String getImplementHandbook() {
		return ImplementHandbook;
	}
	public void setImplementHandbook(String implementHandbook) {
		ImplementHandbook = implementHandbook;
	}
	public String getTechnologyProtocol() {
		return TechnologyProtocol;
	}
	public void setTechnologyProtocol(String technologyProtocol) {
		TechnologyProtocol = technologyProtocol;
	}
	public String getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		OperatingTime = operatingTime;
	}
	public int getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(int customerID) {
		CustomerID = customerID;
	}
	public String getPreSalesTable() {
		return preSalesTable;
	}
	public void setPreSalesTable(String preSalesTable) {
		this.preSalesTable = preSalesTable;
	}
	public String getScriptBackup() {
		return ScriptBackup;
	}
	public void setScriptBackup(String scriptBackup) {
		ScriptBackup = scriptBackup;
	}
	
	
	
}
