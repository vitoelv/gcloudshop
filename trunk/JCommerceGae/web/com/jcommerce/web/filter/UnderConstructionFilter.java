package com.jcommerce.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.web.util.WebUtils;

public class UnderConstructionFilter implements Filter {

	public void debug(String s) {
		System.out.println("in [UnderConstructionFilter]: "+s);
	}
	
	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	Set<String> allowedActions = new HashSet<String>();
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String actionName = WebUtils.getActionName(httpRequest);
		debug("actionName="+actionName);
		if(allowedActions.contains(actionName)) {
			chain.doFilter(request, response);	
		}
		else {
			debug("redirecting to under construction...");
			httpResponse.sendRedirect("/underConstruction.jsp");
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String allowPara = filterConfig.getInitParameter("allow");
		String[] s = StringUtils.split(allowPara,",");
		allowedActions.addAll(Arrays.asList(s));
	}

}