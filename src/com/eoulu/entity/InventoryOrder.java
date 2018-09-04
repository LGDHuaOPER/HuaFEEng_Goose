package com.eoulu.entity;

public class InventoryOrder {
	
	private int ID;
	private int CustomerID;
	private int OrderQuantity;
	private int InventoryID;
	private String Warehouse;
	private int RealNum;
	public int getRealNum() {
		return RealNum;
	}
	public void setRealNum(int realNum) {
		RealNum = realNum;
	}
	private String OperatingTime;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(int customerID) {
		CustomerID = customerID;
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


}
