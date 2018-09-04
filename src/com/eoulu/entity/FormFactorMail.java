package com.eoulu.entity;



public class FormFactorMail {
	private int ID;
	private int factoryID;
	private String type;
	private String subject;
	private String receptor;
	private String copyList;
	private String mainBody;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getFactoryID() {
		return factoryID;
	}
	public void setFactoryID(int factoryID) {
		this.factoryID = factoryID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getReceptor() {
		return receptor;
	}
	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}
	public String getCopyList() {
		return copyList;
	}
	public void setCopyList(String copyList) {
		this.copyList = copyList;
	}
	public String getMainBody() {
		return mainBody;
	}
	public void setMainBody(String mainBody) {
		this.mainBody = mainBody;
	}
	
	
}
