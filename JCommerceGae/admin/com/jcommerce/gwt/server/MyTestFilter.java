package com.jcommerce.gwt.server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyTestFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
			return;
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String url = httpRequest.getRequestURL().toString();
//		System.out.println("in MyTestFilter =================== requesting url="+url);
		filterChain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
