/**
 * 
 */
package com.eoulu.entity;

/**
 * @author zhangkai
 *
 *@date 2017/3/21
 *订单信息
 *������ϸ��ʵ����   orderInfo
 */
public class OrderInfo {

	private int ID;
	private int OrderID;
	private int EquipmentModel;
	private String ExceptDate;
	private int Number;
	private int StockNumber;
	private int LogisticsNumber;
	private String Date;
	private String DeliveryNumber;
	public int Status;
	private String Remarks;
	private String EquipmentModel2;
	private int CommodityID;
	
	
	public int getCommodityID() {
		return CommodityID;
	}
	public void setCommodityID(int commodityID) {
		CommodityID = commodityID;
	}
	public String getEquipmentModel2() {
		return EquipmentModel2;
	}
	public void setEquipmentModel2(String equipmentModel2) {
		EquipmentModel2 = equipmentModel2;
	}
	public OrderInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderInfo(int equipmentModel, int number) {
		super();
		EquipmentModel = equipmentModel;
		Number = number;
	}
	
	public OrderInfo(String equipmentModel2,int number ) {
		super();
		Number = number;
		EquipmentModel2 = equipmentModel2;
	}
	public int getStockNumber() {
		return StockNumber;
	}
	public void setStockNumber(int stockNumber) {
		StockNumber = stockNumber;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getOrderID() {
		return OrderID;
	}
	public void setOrderID(int orderID) {
		OrderID = orderID;
	}
	public int getEquipmentModel() {
		return EquipmentModel;
	}
	public void setEquipmentModel(int equipmentModel) {
		EquipmentModel = equipmentModel;
	}
	public String getExceptDate() {
		return ExceptDate;
	}
	public void setExceptDate(String exceptDate) {
		ExceptDate = exceptDate;
	}
	public int getNumber() {
		return Number;
	}
	public void setNumber(int number) {
		Number = number;
	}
	public int getLogisticsNumber() {
		return LogisticsNumber;
	}
	public void setLogisticsNumber(int logisticsNumber) {
		LogisticsNumber = logisticsNumber;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getDeliveryNumber() {
		return DeliveryNumber;
	}
	public void setDeliveryNumber(String deliveryNumber) {
		DeliveryNumber = deliveryNumber;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	
	
	
	
	
	
}
