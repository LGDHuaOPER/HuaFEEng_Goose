/**
 * 
 */
package com.eoulu.commonality;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangkai
 *
 *������������˵�����
 */
public class FilterResource {

	
	private final static String[] resource={
		"/css/","/js/","/image/","/font-awesome-4.5.0/","/Login"
	};
	
	//���url�д�������˵����ݷ���
	public static boolean isExist(HttpServletRequest request){
		boolean flag = false;
		String url = request.getRequestURI();
		for(String rs : resource){
			if(url.contains(rs)){
				flag = true;
			}
		}
		
		
		return flag;
	}
}
