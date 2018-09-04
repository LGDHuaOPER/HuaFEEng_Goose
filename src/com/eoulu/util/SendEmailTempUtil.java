package com.eoulu.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class SendEmailTempUtil {

	
	String[] to = null;
	String[] copyto = null;
	
    private String SEND_USER = null;//昵称
    private String SEND_UNAME = null;
    private String SEND_PWD = null;
    public boolean doSendHtmlEmail(String subject, String content, String[] fileList) {
    	Properties pro = new Properties();
    	Properties pro2 = new Properties();
    	List<String> list = new ArrayList<>();
    	List<String> ls = new ArrayList<>();
    	try {
			pro.load(SendMailUtil.class.getResourceAsStream("email.properties"));
			SEND_USER = pro.getProperty("SEND_USER");
			SEND_UNAME = pro.getProperty("SEND_UNAME");
			SEND_PWD = pro.getProperty("SEND_PWD");
			
			try {
				pro2.load(SendMailUtil.class.getResourceAsStream("delivery.properties"));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			int toCount = Integer.parseInt(pro2.getProperty("to"));
			for(int i=0 ; i<toCount ; i++){
				int temp = i+1;
				String key = "To"+temp;
				list.add(pro2.getProperty(key));
				
			}
			to = new String[toCount];
			for(int i=0 ; i<list.size();i++){
				to[i] = list.get(i);
			}
			int copytoCount = Integer.parseInt(pro2.getProperty("copyto"));
			copyto = new String[copytoCount];
			for(int i=0 ; i<copytoCount ; i++){
				int temp = i+1;
				String key = "CopyTo"+temp;
				ls.add(pro2.getProperty(key));
				
			}
			for(int i=0 ; i<ls.size();i++){
				copyto[i] = ls.get(i);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    	
    	return new JavaMailToolsUtil(SEND_USER, SEND_UNAME, SEND_PWD).doSendHtmlEmail(subject, content, fileList, to, copyto);
    }
    
}
