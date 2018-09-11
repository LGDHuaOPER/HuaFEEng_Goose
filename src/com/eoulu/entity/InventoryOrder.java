package com.eoulu.entity;

public class InventoryOrder {
	
	private int ID;
	private String Customer;
	private int OrderQuantity;
	private int InventoryID;
	private String Warehouse;
	private int RealNum;
	private String OrderTime;
	private String ContractNO;
	private String EstimatedShippingTime;
	private String OperatingTime;
	public int getRealNum() {
		return RealNum;
	}
	public void setRealNum(int realNum) {
		RealNum = realNum;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getCustomer() {
		return Customer;
	}
	public void setCustomer(String customer) {
		Customer = customer;
	}
	public int getOrderQuantity() {
		return OrderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		OrderQuantity = orderQuantity;
	}
	public int getInventoryID() {
		return InventoryID;
	}
	public void setInventoryID(int inventoryID) {
		InventoryID = inventoryID;
	}
	public String getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		OperatingTime = operatingTime;
	}
	public String getWarehouse() {
		return Warehouse;
	}
	public void setWarehouse(String warehouse) {
		Warehouse = warehouse;
	}
	public String getOrderTime() {
		return OrderTime;
	}
	public void setOrderTime(String orderTime) {
		OrderTime = orderTime;
	}
	public String getContractNO() {
		return ContractNO;
	}
	public void setContractNO(String contractNO) {
		ContractNO = contractNO;
	}
	public String getEstimatedShippingTime() {
		return EstimatedShippingTime;
	}
	public void setEstimatedShippingTime(String estimatedShippingTime) {
		EstimatedShippingTime = estimatedShippingTime;
	}
	


}
