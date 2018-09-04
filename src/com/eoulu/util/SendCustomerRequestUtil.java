package com.eoulu.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class SendCustomerRequestUtil {

	String[] to = null;
	String[] copyto = null;

	/**
	 * 
	 * 
	 * @param to
	 *            收件人
	 * @param copyto
	 *            抄送
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @param fileList
	 *            附件列表
	 */
	public String doSendHtmlEmail(String USER,String UNAME,String PWD, String subject, String content, String[] fileList,String type) {
		Properties pro2 = new Properties();
		List<String> list = new ArrayList<>();
		List<String> ls = new ArrayList<>();
		if(type.equals("eoulu")){
			try {
				pro2.load(SendPreparationEmailUtil.class.getResourceAsStream("formfactor.properties"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(type.equals("formfactor")){
			try {
				pro2.load(SendPreparationEmailUtil.class.getResourceAsStream("eoulu.properties"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
		int toCount = Integer.parseInt(pro2.getProperty("to"));
		for (int i = 0; i < toCount; i++) {
			int temp = i + 1;
			String key = "To" + temp;
			list.add(pro2.getProperty(key));

		}
		to = new String[toCount];
		for (int i = 0; i < list.size(); i++) {
			to[i] = list.get(i);
		}
		int copytoCount = Integer.parseInt(pro2.getProperty("copyto"));
		copyto = new String[copytoCount];
		for (int i = 0; i < copytoCount; i++) {
			int temp = i + 1;
			String key = "CopyTo" + temp;
			ls.add(pro2.getProperty(key));

		}
		for (int i = 0; i < ls.size(); i++) {
			copyto[i] = ls.get(i);
		}
		
		if(type.equals("eoulu")){
			return new JavaMailToolsUtil(USER, UNAME, PWD).sendHtmlEmail(subject, content, fileList, to, copyto);
		}else{
			return new FormfactorMailUtil(USER, UNAME, PWD).sendHtmlEmail(subject, content, fileList, to, copyto);
		}
		
		
	}


}
