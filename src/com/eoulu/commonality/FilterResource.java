/**
 * 
 */
package com.eoulu.commonality;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangkai
 *
 *过滤器中免过滤的内容
 */
public class FilterResource {

	
	private final static String[] resource={
		"/css/","/js/","/image/","/font-awesome-4.5.0/","/Login"
	};
	
	//如果url中存在免过滤的内容放行
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
