package com.eoulu.entity;

public class SoftwareProject {
	int ID;
	String product;
	String instructions;
	String uiSpecification;  //UI规范
	String priority;    //优先级
	String submitter;   //r提交人
	String front;    //前端
	String back;   //后台
	String uiDesigner;    //UI设计
	String leader;  //主导人
	String submissionTime;   //提交时间
	String planningTime;  //规划时间
	String leadTime;   //交付时间
	int delayTime;  //延期时间
	String state;    //状态
	String remark;  //备注
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public String getUiSpecification() {
		return uiSpecification;
	}
	public void setUiSpecification(String uiSpecification) {
		this.uiSpecification = uiSpecification;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getSubmitter() {
		return submitter;
	}
	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}
	public String getFront() {
		return front;
	}
	public void setFront(String front) {
		this.front = front;
	}
	public String getBack() {
		return back;
	}
	public void setBack(String back) {
		this.back = back;
	}
	public String getUiDesigner() {
		return uiDesigner;
	}
	public void setUiDesigner(String uiDesigner) {
		this.uiDesigner = uiDesigner;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public String getSubmissionTime() {
		return submissionTime;
	}
	public void setSubmissionTime(String submissionTime) {
		this.submissionTime = submissionTime;
	}
	public String getPlanningTime() {
		return planningTime;
	}
	public void setPlanningTime(String planningTime) {
		this.planningTime = planningTime;
	}
	public String getLeadTime() {
		return leadTime;
	}
	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}
	public int getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

	

}
