package com.jcommerce.gwt.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.orm.jdo.support.OpenPersistenceManagerInViewFilter;

import com.jcommerce.core.dao.impl.PMF;

public class MyOpenPersistenceManagerInViewFilter extends
		OpenPersistenceManagerInViewFilter {
	private static final Logger log = Logger.getLogger(MyOpenPersistenceManagerInViewFilter.class.getName());
	
	public MyOpenPersistenceManagerInViewFilter() {
		// TODO Auto-generated constructor stub
	}
	public  javax.jdo.PersistenceManagerFactory lookupPersistenceManagerFactory() {
		return PMF.get();
	}
	
	public  javax.jdo.PersistenceManagerFactory lookupPersistenceManagerFactory(HttpServletRequest request) {
		return PMF.get();
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		log.info("=================== in MyOpenPersistenceManagerInViewFilter =================== ");
		super.doFilterInternal(request, response, filterChain);
		
	}
	
	
	
}
