package com.eoulu.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.AuthorityResource;
import com.eoulu.entity.Authority;
import com.google.gson.Gson;

/**
 * Servlet Filter implementation class Filter2_Authority
 */
@WebFilter("/*")
public class Filter2_Authority implements Filter {

    /**
     * Default constructor. 
     */
    public Filter2_Authority() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		//�û���������������ӣ�controller�ӿ���
		String authority = reqToSplit(request);
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		//�ж��û������Ƿ���Ҫ����Ȩ���ж�
		if(AuthorityResource.isController(authority)){
			
			//�ж��û��Ƿ���и�Ȩ��
			if(AuthorityResource.isExist(req, authority)){
				chain.doFilter(request, response);
			}else{
				response.getWriter().write(new Gson().toJson("{message:没有对应权限}"));
			}
			
		}else{
			chain.doFilter(request, response);
		}
		
		
		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	
	/**
	 * ��������url����ȡController��Ľӿ�
	 * */
	public String reqToSplit(ServletRequest request){
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		String result = req.getRequestURI().split("/")[2];
		
		return result;
		
	}

}
