/**
 * 
 */
package com.eoulu.entity;

/**
 * @author zhangkai
 *
 *@date 2017/3/21
 *
 *������Ϣʵ�����   quotes
 */
public class Quotes {

	private int OrderID;
	private double RMBQuotes=0;
	private double USDQuotes=0;
	private int PaymentTerms=0;
	private int WhetherToPay=0;
	private String WhetherToPayRemarks;
	private int WhetherToInvoice=0;
	private String WhetherToInvoiceRemarks;
	private int DutyFree=0;
	private String DutyFreeRemarks;
	//��������1
	private String ReceiptDate1;
	//������1
	private float ReceiptAmount1;
	//��������2
	private String ReceiptDate2;
	//������2
	private float ReceiptAmount2;
	//��������3
	private String ReceiptDate3;
	//������3
	private float ReceiptAmount3;
	//��Ʊ����
	private String BillingDate;
	
	private String PayDate;
	
	private String TrackingNo;



	public String getPayDate() {
		return PayDate;
	}
	public void setPayDate(String payDate) {
		PayDate = payDate;
	}
	public String getTrackingNo() {
		return TrackingNo;
	}
	public void setTrackingNo(String trackingNo) {
		TrackingNo = trackingNo;
	}
	public String getBillingDate() {
		return BillingDate;
	}
	public void setBillingDate(String billingDate) {
		BillingDate = billingDate;
	}
	public String getReceiptDate1() {
		return ReceiptDate1;
	}
	public void setReceiptDate1(String receiptDate1) {
		ReceiptDate1 = receiptDate1;
	}
	public float getReceiptAmount1() {
		return ReceiptAmount1;
	}
	public void setReceiptAmount1(float receiptAmount1) {
		ReceiptAmount1 = receiptAmount1;
	}
	public String getReceiptDate2() {
		return ReceiptDate2;
	}
	public void setReceiptDate2(String receiptDate2) {
		ReceiptDate2 = receiptDate2;
	}
	public float getReceiptAmount2() {
		return ReceiptAmount2;
	}
	public void setReceiptAmount2(float receiptAmount2) {
		ReceiptAmount2 = receiptAmount2;
	}
	public String getReceiptDate3() {
		return ReceiptDate3;
	}
	public void setReceiptDate3(String receiptDate3) {
		ReceiptDate3 = receiptDate3;
	}
	public float getReceiptAmount3() {
		return ReceiptAmount3;
	}
	public void setReceiptAmount3(float receiptAmount3) {
		ReceiptAmount3 = receiptAmount3;
	}
	public int getOrderID() {
		return OrderID;
	}
	public void setOrderID(int orderID) {
		OrderID = orderID;
	}
	public double getRMBQuotes() {
		return RMBQuotes;
	}
	public void setRMBQuotes(double rMBQuotes) {
		RMBQuotes = rMBQuotes;
	}
	public double getUSDQuotes() {
		return USDQuotes;
	}
	public void setUSDQuotes(double uSDQuotes) {
		USDQuotes = uSDQuotes;
	}
	public int getPaymentTerms() {
		return PaymentTerms;
	}
	public void setPaymentTerms(int paymentTerms) {
		PaymentTerms = paymentTerms;
	}
	public int getWhetherToPay() {
		return WhetherToPay;
	}
	public void setWhetherToPay(int whetherToPay) {
		WhetherToPay = whetherToPay;
	}
	public String getWhetherToPayRemarks() {
		return WhetherToPayRemarks;
	}
	public void setWhetherToPayRemarks(String whetherToPayRemarks) {
		WhetherToPayRemarks = whetherToPayRemarks;
	}
	public int getWhetherToInvoice() {
		return WhetherToInvoice;
	}
	public void setWhetherToInvoice(int whetherToInvoice) {
		WhetherToInvoice = whetherToInvoice;
	}
	public String getWhetherToInvoiceRemarks() {
		return WhetherToInvoiceRemarks;
	}
	public void setWhetherToInvoiceRemarks(String whetherToInvoiceRemarks) {
		WhetherToInvoiceRemarks = whetherToInvoiceRemarks;
	}
	public int getDutyFree() {
		return DutyFree;
	}
	public void setDutyFree(int dutyFree) {
		DutyFree = dutyFree;
	}
	public String getDutyFreeRemarks() {
		return DutyFreeRemarks;
	}
	public void setDutyFreeRemarks(String dutyFreeRemarks) {
		DutyFreeRemarks = dutyFreeRemarks;
	}
}
