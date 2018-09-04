package com.eoulu.entity;

public class CustomerInquiryRecord {
	
	private int ID;
	private int Quantity;
	private int InquiryID;
	private int ProductID;
	private String SequenceNO;
	private String IsMain;
	
	public int getProductID() {
		return ProductID;
	}
	public void setProductID(int productID) {
		ProductID = productID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public int getInquiryID() {
		return InquiryID;
	}
	public void setInquiryID(int inquiryID) {
		InquiryID = inquiryID;
	}
	public String getSequenceNO() {
		return SequenceNO;
	}
	public void setSequenceNO(String sequenceNO) {
		SequenceNO = sequenceNO;
	}
	public String getIsMain() {
		return IsMain;
	}
	public void setIsMain(String isMain) {
		IsMain = isMain;
	}

	
}
