package com.eoulu.entity;

public class PackingItem {

	private int ID;
	private int PackingID;
	private String Goods;
	private String Desciption;
	private int Quantity;
	private String PONO;
	
	public String getPONO() {
		return PONO;
	}
	public void setPONO(String pONO) {
		PONO = pONO;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getGoods() {
		return Goods;
	}
	public void setGoods(String goods) {
		Goods = goods;
	}
	public String getDesciption() {
		return Desciption;
	}
	public void setDesciption(String desciption) {
		Desciption = desciption;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public int getPackingID() {
		return PackingID;
	}
	public void setPackingID(int packingID) {
		PackingID = packingID;
	}
	
	
}
