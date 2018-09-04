package com.eoulu.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class SendPreparationEmailUtil {

	public boolean doSendHtmlEmail(String area, String subject, String content, String[] fileList) {
		Properties pro = new Properties();
	
		try {
			pro.load(SendPreparationEmailUtil.class.getResourceAsStream("North.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String SEND_USER = pro.getProperty("SEND_USER");
		String SEND_UNAME = pro.getProperty("SEND_UNAME");
		String SEND_PWD = pro.getProperty("SEND_PWD");
		
		Properties pro2 = new Properties();
		List<String> list = new ArrayList<>();
		List<String> ls = new ArrayList<>();
		try {
			pro2.load(SendPreparationEmailUtil.class.getResourceAsStream("preparation.properties"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		int toCount = Integer.parseInt(pro2.getProperty("to"));
		for (int i = 0; i < toCount; i++) {
			int temp = i + 1;
			String key = "To" + temp;
			list.add(pro2.getProperty(key));

		}
		String[] to = new String[toCount];
		for (int i = 0; i < list.size(); i++) {
			to[i] = list.get(i);
		}
		int copytoCount = Integer.parseInt(pro2.getProperty("copyto"));
		String[] copyto = new String[copytoCount];
		for (int i = 0; i < copytoCount; i++) {
			int temp = i + 1;
			String key = "CopyTo" + temp;
			ls.add(pro2.getProperty(key));

		}
		for (int i = 0; i < ls.size(); i++) {
			copyto[i] = ls.get(i);
		}
		
		JavaMailToolsUtil util = new JavaMailToolsUtil(SEND_USER, SEND_UNAME, SEND_PWD);
		return util.doSendHtmlEmail(subject, content, fileList, to, copyto);
	}
	
}
