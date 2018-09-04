package com.eoulu.util;

import java.io.IOException;
import java.util.Properties;


public class SendPOEmailUtil {

	private String SEND_USER = null;
	private String SEND_UNAME = null;
	private String SEND_PWD = null;

	public boolean doSendHtmlEmail(String[] to, String[] copyto, String subject, String content, String[] fileList) {
		Properties pro = new Properties();
		try {
			pro.load(SendPOEmailUtil.class.getResourceAsStream("email.properties"));
			SEND_USER = pro.getProperty("SEND_USER");
			SEND_UNAME = pro.getProperty("SEND_UNAME");
			SEND_PWD = pro.getProperty("SEND_PWD");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return new JavaMailToolsUtil(SEND_USER, SEND_UNAME, SEND_PWD).doSendHtmlEmail(subject, content, fileList, to, copyto);
	}

}
