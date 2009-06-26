package com.jcommerce.gwt.server;

import javax.servlet.http.HttpServletRequest;

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
}
