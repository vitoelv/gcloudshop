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
		getDefaultManager().txdelete(ModelNames.SESSION, null);
	}
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
		Session session = (Session)getDefaultManager().get(ModelNames.SESSION, se.getSession().getId());
		if(session == null){
			// TODO  ip,data
			session = new Session();
			session.setSesskey(se.getSession().getId());
			session.setExpiry((new Date()).getTime());
			
			getDefaultManager().txadd(session);
			
		}else{
			if(((new Date()).getTime()-session.getExpiry()) <= maxLifeTime){
				session.setExpiry((new Date()).getTime());
				getDefaultManager().txupdate(session);
			}else{
				getDefaultManager().txdelete(ModelNames.SESSION, session.getPkId());
				session = new Session();
				session.setSesskey(se.getSession().getId());
				session.setExpiry((new Date()).getTime());
				getDefaultManager().txadd(session);
			}
		}

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		getDefaultManager().txdelete(ModelNames.SESSION, se.getSession().getId());

	}

	public IDefaultManager getDefaultManager() {
		return (IDefaultManager) SpringUtil.getBean("DefaultManager");
	}
}
