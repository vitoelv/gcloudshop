package com.jcommerce.gwt.server;

import org.springframework.web.context.WebApplicationContext;

import com.jcommerce.core.service.CustomizedManager;
import com.jcommerce.core.service.IDefaultManager;



public class BaseGWTHttpAction {
	private WebApplicationContext ctx;

	public WebApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(WebApplicationContext ctx) {
		this.ctx = ctx;
	}
	public IDefaultManager getDefaultManager() {

		return (IDefaultManager)ctx.getBean("DefaultManager");
	}
	
	public CustomizedManager getCustomizedManager() {
		return (CustomizedManager)ctx.getBean("CustomizedManager");
	}
	
	
}
