package com.eoulu.entity;

/**
 * @author zhangkai
 * 
 * @date 2017/3/21
 * 
 *       �ͻ���Ϣʵ���� customer
 * 
 */
public class Customer {

	private int ID;
	private String CustomerName;
	private String Contact;
	private String ContactInfo1;
	private String ContactInfo2;
	private String ContactAddress;
	private String Area;
	private String ZipCode;
	private String FaxNumber;
	private String Email;
	private String CustomerClassify;
	private String ShorthandCoding;
	private String EnglishName;
	private String City;
	private String Website;
	private String CustomerDepartment;
	private String DepartmentEnglish;
	private String ProductCategory;
	private String CustomerLevel;
	private int areaName;
	
	
	
	

	public int getAreaName() {
		return areaName;
	}

	public void setAreaName(int areaName) {
		this.areaName = areaName;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getCustomerLevel() {
		return CustomerLevel;
	}

	public void setCustomerLevel(String customerLeave) {
		CustomerLevel = customerLeave;
	}

	public String getProductCategory() {
		return ProductCategory;
	}

	public void setProductCategory(String productCategory) {
		ProductCategory = productCategory;
	}

	public String getEnglishName() {
		return EnglishName;
	}

	public void setEnglishName(String englishName) {
		EnglishName = englishName;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public String getContact() {
		return Contact;
	}

	public void setContact(String contact) {
		Contact = contact;
	}

	public String getContactInfo1() {
		return ContactInfo1;
	}

	public void setContactInfo1(String contactInfo1) {
		ContactInfo1 = contactInfo1;
	}

	public String getContactInfo2() {
		return ContactInfo2;
	}

	public void setContactInfo2(String contactInfo2) {
		ContactInfo2 = contactInfo2;
	}

	public String getContactAddress() {
		return ContactAddress;
	}

	public void setContactAddress(String contactAddress) {
		ContactAddress = contactAddress;
	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

	public String getZipCode() {
		return ZipCode;
	}

	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}

	public String getFaxNumber() {
		return FaxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		FaxNumber = faxNumber;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getCustomerClassify() {
		return CustomerClassify;
	}

	public void setCustomerClassify(String customerClassify) {
		CustomerClassify = customerClassify;
	}

	public String getShorthandCoding() {
		return ShorthandCoding;
	}

	public void setShorthandCoding(String shorthandCoding) {
		ShorthandCoding = shorthandCoding;
	}

	public String getWebsite() {
		return Website;
	}

	public void setWebsite(String website) {
		Website = website;
	}

	public String getCustomerDepartment() {
		return CustomerDepartment;
	}

	public void setCustomerDepartment(String customerDepartment) {
		CustomerDepartment = customerDepartment;
	}

	public String getDepartmentEnglish() {
		return DepartmentEnglish;
	}

	public void setDepartmentEnglish(String departmentEnglish) {
		DepartmentEnglish = departmentEnglish;
	}

}
