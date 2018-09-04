package com.eoulu.entity;

public class TransportDirective {

	private int ID;
	private String ContractNO;
	private String PackingDate;
	private String Status;
	private String transporter;
	private int Total1;
	private int Total2;
	private int Total3;

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getContractNO() {
		return ContractNO;
	}

	public void setContractNO(String contractNO) {
		ContractNO = contractNO;
	}

	public String getPackingDate() {
		return PackingDate;
	}

	public void setPackingDate(String packingDate) {
		PackingDate = packingDate;
	}

	public int getTotal1() {
		return Total1;
	}

	public void setTotal1(int total1) {
		Total1 = total1;
	}

	public int getTotal2() {
		return Total2;
	}

	public void setTotal2(int total2) {
		Total2 = total2;
	}

	public int getTotal3() {
		return Total3;
	}

	public void setTotal3(int total3) {
		Total3 = total3;
	}

}
