package com.eoulu.entity;

public class GoodsSize {

	private int ID;
	private String Dimension;
	private double GrossWeight;
	private double NetWeight;
	private int Quantity;
	private int PackingID;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getDimension() {
		return Dimension;
	}
	public void setDimension(String dimension) {
		Dimension = dimension;
	}
	public double getGrossWeight() {
		return GrossWeight;
	}
	public void setGrossWeight(double grossWeight) {
		GrossWeight = grossWeight;
	}
	public double getNetWeight() {
		return NetWeight;
	}
	public void setNetWeight(double netWeight) {
		NetWeight = netWeight;
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
