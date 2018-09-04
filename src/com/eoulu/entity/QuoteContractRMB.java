package com.eoulu.entity;

public class QuoteContractRMB {

	private int ID;
	private String ContractNO;
	private String SignDate;
	private String CustomerCompany;
	private String CustomerTel;
	private String CustomerFax;
	private String CustomerContact;
	private String SecondContact;
	private double TotalPrice;
	private String Payment;
	private String LeadTime;
	private String DeliveryPoint;
	private String OperatingTime;
	private int QuoteID;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getContractNO() {
		return ContractNO;
	}
	public void setContractNO(String contractNO) {
		ContractNO = contractNO;
	}
	public String getSignDate() {
		return SignDate;
	}
	public void setSignDate(String signDate) {
		SignDate = signDate;
	}
	public String getCustomerCompany() {
		return CustomerCompany;
	}
	public void setCustomerCompany(String customerCompany) {
		CustomerCompany = customerCompany;
	}
	public String getCustomerTel() {
		return CustomerTel;
	}
	public void setCustomerTel(String customerTel) {
		CustomerTel = customerTel;
	}
	public String getCustomerFax() {
		return CustomerFax;
	}
	public void setCustomerFax(String customerFax) {
		CustomerFax = customerFax;
	}
	public String getCustomerContact() {
		return CustomerContact;
	}
	public void setCustomerContact(String customerContact) {
		CustomerContact = customerContact;
	}
	public String getSecondContact() {
		return SecondContact;
	}
	public void setSecondContact(String secondContact) {
		SecondContact = secondContact;
	}
	
	public double getTotalPrice() {
		return TotalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		TotalPrice = totalPrice;
	}
	public String getPayment() {
		return Payment;
	}
	public void setPayment(String payment) {
		Payment = payment;
	}
	public String getLeadTime() {
		return LeadTime;
	}
	public void setLeadTime(String leadTime) {
		LeadTime = leadTime;
	}
	public String getDeliveryPoint() {
		return DeliveryPoint;
	}
	public void setDeliveryPoint(String deliveryPoint) {
		DeliveryPoint = deliveryPoint;
	}
	public int getQuoteID() {
		return QuoteID;
	}
	public void setQuoteID(int quoteID) {
		QuoteID = quoteID;
	}
	public String getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		OperatingTime = operatingTime;
	}
	
}
