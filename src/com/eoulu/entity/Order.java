package com.eoulu.entity;

/**
 * @author zhangkai
 * 
 * @date 2017/3/21
 * 
 *       ������Ϣ�� order 订单
 */
public class Order {

	private int ID;
	private String Customer;
	private int Area;
	private String ContractNo;
	private String ContractTitle;
	private int SalesRepresentative;
	private String Contact;
	private String ContactInfo;
	private String DateOfSign;
	private String CargoPeriod;
	private String ActualDelivery;
	private String ExpectedDeliveryPeriod;
	private int Status;
	private String InstalledTime;
	private String InstalledSite;
	private String Remarks;
	private int ContractCategory;
	// Ԥ���տ�����
	private String ExpectedReceiptDate;
	private String ActualPaymentTime;
	private String isSend;
	private int PageType;
	private String ContractPath;
	private String TechnologyPath;
	private int QuoteNumber;

	public String getContractPath() {
		return ContractPath;
	}

	public void setContractPath(String contractPath) {
		ContractPath = contractPath;
	}

	public String getTechnologyPath() {
		return TechnologyPath;
	}

	public void setTechnologyPath(String technologyPath) {
		TechnologyPath = technologyPath;
	}

	public int getQuoteNumber() {
		return QuoteNumber;
	}

	public void setQuoteNumber(int quoteNumber) {
		QuoteNumber = quoteNumber;
	}

	public int getPageType() {
		return PageType;
	}

	public void setPageType(int pageType) {
		PageType = pageType;
	}

	public String getIsSend() {
		return isSend;
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}
	
	

	public String getActualPaymentTime() {
		return ActualPaymentTime;
	}

	public void setActualPaymentTime(String actualPaymentTime) {
		ActualPaymentTime = actualPaymentTime;
	}

	public Order() {

	}

	public Order(int iD, String customer, int area, String contractNo, String contractTitle, int salesRepresentative,
			String contact, String contactInfo, String dateOfSign, String cargoPeriod, String actualDelivery,
			String expectedDeliveryPeriod, int status, String installedTime, String installedSite, String remarks) {
		super();
		ID = iD;
		Customer = customer;
		Area = area;
		ContractNo = contractNo;
		ContractTitle = contractTitle;
		SalesRepresentative = salesRepresentative;
		Contact = contact;
		ContactInfo = contactInfo;
		DateOfSign = dateOfSign;
		CargoPeriod = cargoPeriod;
		ActualDelivery = actualDelivery;
		ExpectedDeliveryPeriod = expectedDeliveryPeriod;
		Status = status;
		InstalledTime = installedTime;
		InstalledSite = installedSite;
		Remarks = remarks;
	}

	public String getExpectedReceiptDate() {
		return ExpectedReceiptDate;
	}

	public void setExpectedReceiptDate(String expectedReceiptDate) {
		ExpectedReceiptDate = expectedReceiptDate;
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

	public int getArea() {
		return Area;
	}

	public void setArea(int area) {
		Area = area;
	}

	public String getContractNo() {
		return ContractNo;
	}

	public void setContractNo(String contractNo) {
		ContractNo = contractNo;
	}

	public String getContractTitle() {
		return ContractTitle;
	}

	public void setContractTitle(String contractTitle) {
		ContractTitle = contractTitle;
	}

	public int getSalesRepresentative() {
		return SalesRepresentative;
	}

	public void setSalesRepresentative(int salesRepresentative) {
		SalesRepresentative = salesRepresentative;
	}

	public String getContact() {
		return Contact;
	}

	public void setContact(String contact) {
		Contact = contact;
	}

	public String getContactInfo() {
		return ContactInfo;
	}

	public void setContactInfo(String contactInfo) {
		ContactInfo = contactInfo;
	}

	public String getDateOfSign() {
		return DateOfSign;
	}

	public void setDateOfSign(String dateOfSign) {
		DateOfSign = dateOfSign;
	}

	public String getCargoPeriod() {
		return CargoPeriod;
	}

	public void setCargoPeriod(String cargoPeriod) {
		CargoPeriod = cargoPeriod;
	}

	public String getActualDelivery() {
		return ActualDelivery;
	}

	public void setActualDelivery(String actualDelivery) {
		ActualDelivery = actualDelivery;
	}

	public String getExpectedDeliveryPeriod() {
		return ExpectedDeliveryPeriod;
	}

	public void setExpectedDeliveryPeriod(String expectedDeliveryPeriod) {
		ExpectedDeliveryPeriod = expectedDeliveryPeriod;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getInstalledTime() {
		return InstalledTime;
	}

	public void setInstalledTime(String installedTime) {
		InstalledTime = installedTime;
	}

	public String getInstalledSite() {
		return InstalledSite;
	}

	public void setInstalledSite(String installedSite) {
		InstalledSite = installedSite;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public int getContractCategory() {
		return ContractCategory;
	}

	public void setContractCategory(int contractCategory) {
		ContractCategory = contractCategory;
	}

}
