package com.eoulu.entity;

public class SoftwareProduct {

	private int ID;
	private String PackageName;
	private String Model;
	private String PackageClassify;
	private int Count;
	private double HourlyWage;
	private double Cycle;
	private String Brand;
	private double PremiumIndex;
	private double MaintenanceIndex;
	private double TransportAllowance;
	private double MissionAllowance;
	private double Accommodation;
	private String Remarks;
	private String OperatingTime;
	
	public double getAccommodation() {
		return Accommodation;
	}
	public void setAccommodation(double accommodation) {
		Accommodation = accommodation;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getPackageName() {
		return PackageName;
	}
	public void setPackageName(String packageName) {
		PackageName = packageName;
	}
	public String getModel() {
		return Model;
	}
	public void setModel(String model) {
		Model = model;
	}
	public String getPackageClassify() {
		return PackageClassify;
	}
	public void setPackageClassify(String packageClassify) {
		PackageClassify = packageClassify;
	}
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	public double getHourlyWage() {
		return HourlyWage;
	}
	public void setHourlyWage(double hourlyWage) {
		HourlyWage = hourlyWage;
	}
	public double getCycle() {
		return Cycle;
	}
	public void setCycle(double cycle) {
		Cycle = cycle;
	}
	public String getBrand() {
		return Brand;
	}
	public void setBrand(String brand) {
		Brand = brand;
	}
	public double getPremiumIndex() {
		return PremiumIndex;
	}
	public void setPremiumIndex(double premiumIndex) {
		PremiumIndex = premiumIndex;
	}
	public double getMaintenanceIndex() {
		return MaintenanceIndex;
	}
	public void setMaintenanceIndex(double maintenanceIndex) {
		MaintenanceIndex = maintenanceIndex;
	}
	public double getTransportAllowance() {
		return TransportAllowance;
	}
	public void setTransportAllowance(double transportAllowance) {
		TransportAllowance = transportAllowance;
	}
	public double getMissionAllowance() {
		return MissionAllowance;
	}
	public void setMissionAllowance(double missionAllowance) {
		MissionAllowance = missionAllowance;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public String getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		OperatingTime = operatingTime;
	}
	
	
}
