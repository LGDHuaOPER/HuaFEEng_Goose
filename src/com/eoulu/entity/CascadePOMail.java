package com.eoulu.entity;

public class CascadePOMail {
	
	private int ID;
	private String Recepter;
	private String CopyList;
	private String Content;
	private String Content1;
	private int POID;
	
	
	
	public String getContent1() {
		return Content1;
	}
	public void setContent1(String content1) {
		Content1 = content1;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getRecepter() {
		return Recepter;
	}
	public void setRecepter(String recepter) {
		Recepter = recepter;
	}
	public String getCopyList() {
		return CopyList;
	}
	public void setCopyList(String copyList) {
		CopyList = copyList;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public int getPOID() {
		return POID;
	}
	public void setPOID(int pOID) {
		POID = pOID;
	}

}
