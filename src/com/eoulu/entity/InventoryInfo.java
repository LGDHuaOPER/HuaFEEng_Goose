package com.eoulu.entity;
/** @author  ���� : zhangkai
* @date ����ʱ�䣺2017��6��12�� ����10:20:39 
* @version 1.0  
* @since  
* @return  
* 
* 库存信息
* t_inventory_info��
*/
public class InventoryInfo {

	private int ID;
	private int EquipmentID;
	private int Types;
	private int Quantity;

	//��ͬ��/�ɹ���ͬ�ţ�PO��
	private String ContractNo;
	private String OperationCode;
	private String OperatingTime;
	//��������Ϣ����Դ 1��������ҳ��   2�û��ڿ��ҳ����ӵ�
	private int SourceSign;
	private int OrderInfoID;
	private String Remarks;
	private String OperateDate;
	private int CommodityID;
	private int InventoryID;
	private int InventoryQuantity;
	private int POUSD;
	private int PORMB;
	private int ContractUSD;
	private int ContractRMB;
	private String Warehouse; 
	private String PNCode;
	
	public int getPOUSD() {
		return POUSD;
	}
	public void setPOUSD(int pOUSD) {
		POUSD = pOUSD;
	}
	public int getPORMB() {
		return PORMB;
	}
	public void setPORMB(int pORMB) {
		PORMB = pORMB;
	}
	public int getContractUSD() {
		return ContractUSD;
	}
	public void setContractUSD(int contractUSD) {
		ContractUSD = contractUSD;
	}
	public int getContractRMB() {
		return ContractRMB;
	}
	public void setContractRMB(int contractRMB) {
		ContractRMB = contractRMB;
	}
	public int getInventoryQuantity() {
		return InventoryQuantity;
	}
	public void setInventoryQuantity(int inventoryQuantity) {
		InventoryQuantity = inventoryQuantity;
	}
	public int getCommodityID() {
		return CommodityID;
	}
	public void setCommodityID(int commodityID) {
		CommodityID = commodityID;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public String getOperateDate() {
		return OperateDate;
	}
	public void setOperateDate(String operateDate) {
		OperateDate = operateDate;
	}
	public int getOrderInfoID() {
		return OrderInfoID;
	}
	public void setOrderInfoID(int orderInfoID) {
		OrderInfoID = orderInfoID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	public int getInventoryID() {
		return InventoryID;
	}
	public void setInventoryID(int inventoryID) {
		InventoryID = inventoryID;
	}
	public int getEquipmentID() {
		return EquipmentID;
	}
	public void setEquipmentID(int equipmentID) {
		EquipmentID = equipmentID;
	}
	public int getTypes() {
		return Types;
	}
	public void setTypes(int types) {
		Types = types;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public String getContractNo() {
		return ContractNo;
	}
	public void setContractNo(String contractNo) {
		ContractNo = contractNo;
	}
	public String getOperationCode() {
		return OperationCode;
	}
	public void setOperationCode(String operationCode) {
		OperationCode = operationCode;
	}
	public String getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		OperatingTime = operatingTime;
	}
	public int getSourceSign() {
		return SourceSign;
	}
	public void setSourceSign(int sourceSign) {
		SourceSign = sourceSign;
	}
	public String getWarehouse() {
		return Warehouse;
	}
	public void setWarehouse(String warehouse) {
		Warehouse = warehouse;
	}
	public String getPNCode() {
		return PNCode;
	}
	public void setPNCode(String pNCode) {
		PNCode = pNCode;
	}
	
	
	
	
	
}
