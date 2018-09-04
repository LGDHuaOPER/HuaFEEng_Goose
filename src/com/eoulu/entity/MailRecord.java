package com.eoulu.entity;

public class MailRecord {

	private int ID;
	private String ToEmail;
	private String CopyTo;
	private String NickName;
	private String Type;
	private String OperatingTime;
	private int QuoteID;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getToEmail() {
		return ToEmail;
	}
	public void setToEmail(String toEmail) {
		ToEmail = toEmail;
	}
	public String getCopyTo() {
		return CopyTo;
	}
	public void setCopyTo(String copyTo) {
		CopyTo = copyTo;
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		OperatingTime = operatingTime;
	}
	public int getQuoteID() {
		return QuoteID;
	}
	public void setQuoteID(int quoteID) {
		QuoteID = quoteID;
	}
	
	
	
}
