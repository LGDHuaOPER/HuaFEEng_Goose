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
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.FilterResource;

/**
 * Servlet Filter implementation class Filter0_StaticSource
 */
@WebFilter("/*")
public class Filter1_Login implements Filter {

    /**
     * Default constructor. 
     */
    public Filter1_Login() {
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
	 * 用户登录和免过滤的文件
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		
		if(req.getSession().getAttribute("user")!=null){
			chain.doFilter(request, response);
		}else{
			if(FilterResource.isExist(req)){
				chain.doFilter(request, response);
			}else{
				req.getRequestDispatcher("WEB-INF\\login.jsp").forward(req, rep);
			}
		}
		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
