package com.eoulu.util;

import javax.servlet.http.HttpServletRequest;


public class InsuranceUtil {
	
	
		private String SEND_USER = null;
		private String SEND_UNAME = null;
		private String SEND_PWD = null;
		public InsuranceUtil(HttpServletRequest request,String password){
			SEND_USER = (String) request.getSession().getAttribute("email");
			SEND_UNAME = (String) request.getSession().getAttribute("email");
			SEND_PWD = password;
		}
		
		public boolean doSendHtmlEmail(String[] to, String[] copyto, String subject, String content, String[] fileList) {
			return new JavaMailToolsUtil(SEND_USER, SEND_UNAME, SEND_PWD).doSendHtmlEmail(subject, content, fileList, to, copyto);
		}

		
}
