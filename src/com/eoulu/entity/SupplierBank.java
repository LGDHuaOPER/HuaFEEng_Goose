package com.eoulu.entity;

public class SupplierBank {
	private int ID;
	private String supplier;
	private String company;
	private String account;
	private String bank;
	private String TaxCode;
	private String SWIFTCode;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getTaxCode() {
		return TaxCode;
	}
	public void setTaxCode(String taxCode) {
		TaxCode = taxCode;
	}
	public String getSWIFTCode() {
		return SWIFTCode;
	}
	public void setSWIFTCode(String sWIFTCode) {
		SWIFTCode = sWIFTCode;
	}
	
	

}
