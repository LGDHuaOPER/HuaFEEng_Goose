package com.eoulu.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class Filter0_Encode
 */
@WebFilter("/*")
public class Filter0_Encode implements Filter {

    /**
     * Default constructor. 
     */
    public Filter0_Encode() {
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
	 * 
	 * 编码过滤器
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		String url = req.getRequestURI();

		
		String encode = "UTF-8";
		// 设置request编码
		req.setCharacterEncoding(encode);
		// 设置相应信息
		rep.setContentType("text/html;charset=" + encode);
		rep.setCharacterEncoding(encode);
		
		
//		Enumeration<String> enu =  req.getParameterNames();
//		
//		while(enu.hasMoreElements()){  
//			String paraName=(String)enu.nextElement();  
//			System.out.println(paraName+": "+request.getParameter(paraName));  
//			}
		
		//System.out.println("come in");
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
