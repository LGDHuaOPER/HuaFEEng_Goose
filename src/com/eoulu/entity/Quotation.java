/**
 * 
 */
package com.eoulu.entity;

/**
 * @author zhangkai
 *
 *对应的数据库表为t_quotation
 */
public class Quotation {

	private String QuotationID;
	private Integer OrderID;
	private String QuotationDate;
	private String ContractType;
	private double Version;
	private String Valid;
	private String LeadTime;
	private double ExchangeRate;
	private double ChargeDuty;
	private String ModifyTime;
	private Integer paymentTermID;
	//插入Order表信息
	private String Customer;
	private String Contact;
	private String ContactInfo;
	private Integer SalesRepresentative;
	private Integer ID;
	//上一次QuotationID1
	private String QuotationID1;
	
	public String getQuotationID() {
		return QuotationID;
	}
	public void setQuotationID(String quotationID) {
		QuotationID = quotationID;
	}
	public Integer getOrderID() {
		return OrderID;
	}
	public void setOrderID(Integer orderID) {
		OrderID = orderID;
	}
	public String getQuotationDate() {
		return QuotationDate;
	}
	public void setQuotationDate(String quotationDate) {
		QuotationDate = quotationDate;
	}
	public String getContractType() {
		return ContractType;
	}
	public void setContractType(String contractType) {
		ContractType = contractType;
	}
	public double getVersion() {
		return Version;
	}
	public void setVersion(double version) {
		Version = version;
	}
	public String getValid() {
		return Valid;
	}
	public void setValid(String valid) {
		Valid = valid;
	}
	public String getLeadTime() {
		return LeadTime;
	}
	public void setLeadTime(String leadTime) {
		LeadTime = leadTime;
	}
	public double getExchangeRate() {
		return ExchangeRate;
	}
	public void setExchangeRate(double exchangeRate) {
		ExchangeRate = exchangeRate;
	}
	public double getChargeDuty() {
		return ChargeDuty;
	}
	public void setChargeDuty(double chargeDuty) {
		ChargeDuty = chargeDuty;
	}
	public String getModifyTime() {
		return ModifyTime;
	}
	public void setModifyTime(String modifyTime) {
		ModifyTime = modifyTime;
	}
	public String getCustomer() {
		return Customer;
	}
	public void setCustomer(String customer) {
		Customer = customer;
	}
	public String getContact() {
		return Contact;
	}
	public void setContact(String contact) {
		Contact = contact;
	}
	public String getContactInfo() {
		return ContactInfo;
	}
	public void setContactInfo(String contactInfo) {
		ContactInfo = contactInfo;
	}
	public Integer getSalesRepresentative() {
		return SalesRepresentative;
	}
	public void setSalesRepresentative(Integer salesRepresentative) {
		SalesRepresentative = salesRepresentative;
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getQuotationID1() {
		return QuotationID1;
	}
	public void setQuotationID1(String quotationID1) {
		QuotationID1 = quotationID1;
	}
	public Integer getPaymentTermID() {
		return paymentTermID;
	}
	public void setPaymentTermID(Integer paymentTermID) {
		this.paymentTermID = paymentTermID;
	}
	
}
