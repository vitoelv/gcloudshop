package com.jcommerce.gwt.server;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.orm.jdo.support.OpenPersistenceManagerInViewFilter;

import com.jcommerce.core.dao.impl.PMF;

public class MyOpenPersistenceManagerInViewFilter extends
		OpenPersistenceManagerInViewFilter {

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
		System.out.println("=================== in MyOpenPersistenceManagerInViewFilter ===================");
		super.doFilterInternal(request, response, filterChain);
		
	}
	
	
	
}
