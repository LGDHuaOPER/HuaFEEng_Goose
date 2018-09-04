package com.eoulu.entity;

public class PurchaseInfo {
	
	private int OrderID;
	private String contractPath;
	private String product;
	private String money;
	private String use;
	public int getOrderID() {
		return OrderID;
	}
	public void setOrderID(int orderID) {
		OrderID = orderID;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}

	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getContractPath() {
		return contractPath;
	}
	public void setContractPath(String contractPath) {
		this.contractPath = contractPath;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	
	

}
