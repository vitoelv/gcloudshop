package com.jcommerce.web.util;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.jcommerce.core.model.Session;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;

public class SessionListener implements HttpSessionListener {
	
	private int maxLifeTime  = 1800; // SESSION 过期时间

	private void init(){
//		getDefaultManager().txdelete(ModelNames.SESSION, null);
	}
	
	public void sessionCreated(HttpSessionEvent se) {
		// removed due to performance impact.
	    // consider to use memcache, see gae doc about write contention
	    
//		Session session = new Session();
//		session.setSesskey(se.getSession().getId());
//		session.setExpiry((new Date()).getTime());
//		getDefaultManager().txadd(session);
	}

	public void sessionDestroyed(HttpSessionEvent se) {
//		getDefaultManager().txdelete(ModelNames.SESSION, se.getSession().getId());

	}

	public IDefaultManager getDefaultManager() {
		return (IDefaultManager) SpringUtil.getBean("DefaultManager");
	}
}
