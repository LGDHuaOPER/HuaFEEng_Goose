package com.eoulu.entity;

import java.util.List;
import java.util.Map;

public class BillCharge {
	private int OrderID;
	private String InvoiceTitle;
	private String TaxPayerIdentityNO;
	private String RegisterAddress;
	private String Telephone;
	private String DepositBank;
	private String Account;
	private String InvoiceRecepter;
	private String LinkAddress;
	private String LinkTel;
	private String LinkZipCode;
	private int SumOfQuantity;
	private double SumOfTaxPrice ;
	private List<Map<String, Object>> saleGoods;
	
	public int getOrderID() {
		return OrderID;
	}
	public void setOrderID(int orderID) {
		OrderID = orderID;
	}
	public String getInvoiceTitle() {
		return InvoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		InvoiceTitle = invoiceTitle;
	}
	public String getTaxPayerIdentityNO() {
		return TaxPayerIdentityNO;
	}
	public void setTaxPayerIdentityNO(String taxPayerIdentityNO) {
		TaxPayerIdentityNO = taxPayerIdentityNO;
	}
	public String getRegisterAddress() {
		return RegisterAddress;
	}
	public void setRegisterAddress(String registerAddress) {
		RegisterAddress = registerAddress;
	}
	public String getTelephone() {
		return Telephone;
	}
	public void setTelephone(String telephone) {
		Telephone = telephone;
	}
	public String getDepositBank() {
		return DepositBank;
	}
	public void setDepositBank(String depositBank) {
		DepositBank = depositBank;
	}
	public String getAccount() {
		return Account;
	}
	public void setAccount(String account) {
		Account = account;
	}
	public String getInvoiceRecepter() {
		return InvoiceRecepter;
	}
	public void setInvoiceRecepter(String invoiceRecepter) {
		InvoiceRecepter = invoiceRecepter;
	}
	public String getLinkAddress() {
		return LinkAddress;
	}
	public void setLinkAddress(String linkAddress) {
		LinkAddress = linkAddress;
	}
	public String getLinkTel() {
		return LinkTel;
	}
	public void setLinkTel(String linkTel) {
		LinkTel = linkTel;
	}
	public String getLinkZipCode() {
		return LinkZipCode;
	}
	public void setLinkZipCode(String linkZipCode) {
		LinkZipCode = linkZipCode;
	}
	public int getSumOfQuantity() {
		return SumOfQuantity;
	}
	public void setSumOfQuantity(int sumOfQuantity) {
		SumOfQuantity = sumOfQuantity;
	}
	public double getSumOfTaxPrice() {
		return SumOfTaxPrice;
	}
	public void setSumOfTaxPrice(double sumOfTaxPrice) {
		SumOfTaxPrice = sumOfTaxPrice;
	}
	public List<Map<String, Object>> getSaleGoods() {
		return saleGoods;
	}
	public void setSaleGoods(List<Map<String, Object>> saleGoods) {
		this.saleGoods = saleGoods;
	}
	
	

}
