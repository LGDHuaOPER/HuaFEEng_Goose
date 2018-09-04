package com.eoulu.entity;

public class CommodityMail {

	private int ID;
	private int Commodity;
	private String Consignee;
	private String CCList;
	private String Subject;
	private String Content;
	private String OperatingTime;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getCommodity() {
		return Commodity;
	}
	public void setCommodity(int commodity) {
		Commodity = commodity;
	}
	public String getConsignee() {
		return Consignee;
	}
	public void setConsignee(String consignee) {
		Consignee = consignee;
	}
	public String getCCList() {
		return CCList;
	}
	public void setCCList(String cCList) {
		CCList = cCList;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		OperatingTime = operatingTime;
	}
	
	
}
