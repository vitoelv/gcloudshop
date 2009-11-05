package com.jcommerce.web.to;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.UserAddress;

public class UserAddressWrapper extends BaseModelWrapper {

	UserAddress userAddress;
	
	@Override
	protected Object getWrapped() {
		return getUserAddress();
	}

	public UserAddressWrapper(ModelObject userAddress) {
		super();
		this.userAddress = (UserAddress)userAddress;
	}
	public UserAddress getUserAddress() {
		return userAddress;
	}
	
	public String getAddressId() {
		return StringUtils.defaultIfEmpty(getUserAddress().getPkId(), "");
	}

}
