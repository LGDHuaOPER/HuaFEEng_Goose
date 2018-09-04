package com.eoulu.entity;


/**
 * @author zhangkai
 * 
 * @date 2017/3/21
 * 
 * 日志信息表  log
 * */
public class Log {

	private int ID;
	private String LogTime;
	private String User;
	private String Content;
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getLogTime() {
		return LogTime;
	}
	public void setLogTime(String logTime) {
		LogTime = logTime;
	}
	public String getUser() {
		return User;
	}
	public void setUser(String user) {
		User = user;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	
	
	
}
