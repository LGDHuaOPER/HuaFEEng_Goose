package com.eoulu.util;

public class CommodityMailUtil {

	private String SEND_USER = null;// 昵称
	private String SEND_UNAME = null;
	private String SEND_PWD = null;

	/*
	 * 初始化方法
	 */
	public CommodityMailUtil(String user, String password) {
		SEND_USER = user;
		SEND_UNAME = user;
		SEND_PWD = password;
	}

	public boolean doSendHtmlEmail(String subject, String content, String[] fileList, String[] to, String[] copyto) {
	
	return new JavaMailToolsUtil(SEND_USER, SEND_UNAME, SEND_PWD).doSendHtmlEmail(subject, content, fileList, to, copyto);
	}


}
