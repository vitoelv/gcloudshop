package com.jcommerce.web.filter;

import com.jcommerce.web.util.WebUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class UnderConstructionFilter implements Filter {
	
	private static final Logger log = Logger.getLogger(UnderConstructionFilter.class.getName());

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
		
		Long startTime = System.currentTimeMillis();
		
		String actionName = WebUtils.getActionName(httpRequest);
		log.info("actionName="+actionName);
		if(allowedActions.contains(actionName)) {
			chain.doFilter(request, response);	
		}
		else {
			log.warning("redirecting to under construction...");
//			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.sendRedirect("/underConstruction.html");
//			request.getRequestDispatcher("/underConstruction.jsp").forward(request, response);
		}
		
		Long endTime = System.currentTimeMillis();
		String warn = "==============req="+(endTime-startTime)+", s="+startTime+", e="+endTime+"<br>\r\n";
		
		String perfStats = (String) request.getAttribute("perfStats");
        perfStats += warn;
        request.setAttribute("perfStats", perfStats);

        log.warning(perfStats);
        
//      httpRequest.getSession().setAttribute("perfStats", perfStats);        
//        httpResponse.sendRedirect("/perfStats.jsp");
		
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String allowPara = filterConfig.getInitParameter("allow");
		String[] s = StringUtils.split(allowPara,",");
		allowedActions.addAll(Arrays.asList(s));
	}

}
