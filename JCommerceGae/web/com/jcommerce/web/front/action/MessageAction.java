package com.jcommerce.web.front.action;

import javax.servlet.http.HttpServletRequest;

public class MessageAction extends BaseAction {
	
	
	@Override
	public String execute() throws Exception {
		try {
			debug("in execute");
			super.execute();
			HttpServletRequest request = getRequest();
			
			super.includeHelp(request);
			
			
			
			return SUCCESS;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
