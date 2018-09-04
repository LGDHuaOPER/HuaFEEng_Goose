package com.eoulu.entity;

public class InstallationReportLog {

	private int ID;
	private String FileName;
	private String FilePath;
	private String Area;
	private String Year;
	private String OperatingTime;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getFilePath() {
		return FilePath;
	}
	public void setFilePath(String filePath) {
		FilePath = filePath;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	public String getYear() {
		return Year;
	}
	public void setYear(String year) {
		Year = year;
	}
	public String getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		OperatingTime = operatingTime;
	}
	
}
