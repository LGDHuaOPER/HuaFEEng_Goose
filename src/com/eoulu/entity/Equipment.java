/**
 * 
 */
package com.eoulu.entity;

/**
 * @author zhangkai
 *
 *
 *@date 2017/3/21
 *
 *�豸��Ϣʵ����  equipment
 */
public class Equipment {

	private int ID;
	private String Model;
	private String Supplier;
	private String Remarks;
	private String EquipmentUnit;
	private String DeliveryTime;
	private String SourceArea;
	private String ItemCode;
	private String CommodityCategory;
	private int Suppiler;
	private int InitialQuantity;
	private String ProductCategory;
	
	
	
	public String getProductCategory() {
		return ProductCategory;
	}
	public void setProductCategory(String productCategory) {
		ProductCategory = productCategory;
	}
	public int getInitialQuantity() {
		return InitialQuantity;
	}
	public void setInitialQuantity(int initialQuantity) {
		InitialQuantity = initialQuantity;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getModel() {
		return Model;
	}
	public void setModel(String model) {
		Model = model;
	}
	public String getSupplier() {
		return Supplier;
	}
	public void setSupplier(String supplier) {
		Supplier = supplier;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public String getEquipmentUnit() {
		return EquipmentUnit;
	}
	public void setEquipmentUnit(String equipmentUnit) {
		EquipmentUnit = equipmentUnit;
	}
	public String getDeliveryTime() {
		return DeliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		DeliveryTime = deliveryTime;
	}
	public String getSourceArea() {
		return SourceArea;
	}
	public void setSourceArea(String sourceArea) {
		SourceArea = sourceArea;
	}
	public String getItemCode() {
		return ItemCode;
	}
	public void setItemCode(String itemCode) {
		ItemCode = itemCode;
	}
	public String getCommodityCategory() {
		return CommodityCategory;
	}
	public void setCommodityCategory(String commodityCategory) {
		CommodityCategory = commodityCategory;
	}
	public int getSuppiler() {
		return Suppiler;
	}
	public void setSuppiler(int suppiler) {
		Suppiler = suppiler;
	}
	
	
	
}
