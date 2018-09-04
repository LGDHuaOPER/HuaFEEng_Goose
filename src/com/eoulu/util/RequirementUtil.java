package com.eoulu.util;

import java.io.IOException;
import java.util.Properties;

public class RequirementUtil {

	String[] to = null;
	String[] copyto = null;
    private String SEND_USER = null;//昵称
    private String SEND_UNAME = null;
    private String SEND_PWD = null;

    
    public boolean doSendHtmlEmail(String subject, String content, String[] fileList,String umail) {
    	Properties pro = new Properties();
    	Properties pro2 = new Properties();
    	try {
			pro.load(SendMailUtil.class.getResourceAsStream("email.properties"));
			SEND_USER = pro.getProperty("SEND_USER");
			SEND_UNAME = pro.getProperty("SEND_UNAME");
			SEND_PWD = pro.getProperty("SEND_PWD");
			pro2.load(SendMailUtil.class.getResourceAsStream("requirement.properties"));
			to = new String[Integer.parseInt(pro2.getProperty("to"))];
			for(int i=0;i<to.length;i++){
				to[i] = pro2.getProperty("To"+(i+1));
			}
			copyto = new String[Integer.parseInt(pro2.getProperty("copyto"))+1];
			for(int i=0;i<copyto.length-1;i++){
				copyto[i] = pro2.getProperty("CopyTo"+(i+1));
			}
			copyto[copyto.length-1] = umail;
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    	
    	content = new MethodUtil().getEmailSign(content, "NA");
		return new JavaMailToolsUtil(SEND_USER, SEND_UNAME, SEND_PWD).doSendHtmlEmail(subject, content, fileList, to, copyto);
    }
    
	
}
