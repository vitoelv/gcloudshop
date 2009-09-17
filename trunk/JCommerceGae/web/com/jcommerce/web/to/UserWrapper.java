package com.jcommerce.web.to;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.User;
import com.jcommerce.web.util.WebFormatUtils;

public class UserWrapper extends BaseModelWrapper {
	User user;
	
	@Override
	protected Object getWrapped() {
		return getUser();
	}
	public UserWrapper(ModelObject user) {
		super();
		this.user = (User)user;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getFormatedCreditLine() {
		return WebFormatUtils.priceFormat(getUser().getCreditLine());
	}
	public String getUsername() {
		return getUser().getUserName();
	}

}
