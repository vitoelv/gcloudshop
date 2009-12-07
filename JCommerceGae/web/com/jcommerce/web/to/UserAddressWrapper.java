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

	public void setAddressId(java.lang.String newAddressId) {
	    userAddress.setAddressId(newAddressId);
	}

	public java.lang.String getAddressName() {
	    return StringUtils.defaultIfEmpty(getUserAddress().getAddressName(), "");
	}

	public void setAddressName(java.lang.String newAddressName) {
		userAddress.setAddressName(newAddressName);
	}



	public java.lang.String getUserId() {
	    return StringUtils.defaultIfEmpty(getUserAddress().getUserId(), "");
	}

	public void setUserId(java.lang.String newUserId) {
		userAddress.setUserId(newUserId);
	}



	public java.lang.String getConsignee() {
	    return StringUtils.defaultIfEmpty(getUserAddress().getConsignee(), "");
	}

	public void setConsignee(java.lang.String newConsignee) {
		userAddress.setConsignee(newConsignee);
	}



	  public java.lang.String getEmail() {
	    return StringUtils.defaultIfEmpty(getUserAddress().getEmail(), "");
	  }

	  public void setEmail(java.lang.String newEmail) {
		  userAddress.setEmail(newEmail);
	  }







	  public java.lang.String getAddress() {
	    return StringUtils.defaultIfEmpty(getUserAddress().getAddress(), "");
	  }

	  public void setAddress(java.lang.String newAddress) {
		  userAddress.setAddress(newAddress);
	  }



	  public java.lang.String getZipcode() {
	    return StringUtils.defaultIfEmpty(getUserAddress().getZipcode(), "");
	  }

	  public void setZipcode(java.lang.String newZipcode) {
		  userAddress.setZipcode(newZipcode);
	  }



	  public java.lang.String getTel() {
	    return StringUtils.defaultIfEmpty(getUserAddress().getTel(), "");
	  }

	  public void setTel(java.lang.String newTel) {
		  userAddress.setTel(newTel);
	  }



	  public java.lang.String getMobile() {
	    return StringUtils.defaultIfEmpty(getUserAddress().getMobile(), "");
	  }

	  public void setMobile(java.lang.String newMobile) {
		  userAddress.setMobile(newMobile);
	  }



	  public java.lang.String getSignBuilding() {
	    return StringUtils.defaultIfEmpty(getUserAddress().getSignBuilding(), "");
	  }

	  public void setSignBuilding(java.lang.String newSignBuilding) {
		  userAddress.setSignBuilding(newSignBuilding);
	  }



	  public java.lang.String getBestTime() {
	    return StringUtils.defaultIfEmpty(getUserAddress().getBestTime(), "");
	  }

	  public void setBestTime(java.lang.String newBestTime) {
		  userAddress.setBestTime(newBestTime);
	  }

		public java.lang.String getCountry() {
			return StringUtils.defaultIfEmpty(getUserAddress().getCountry(), "");
		}


		public void setCountry(java.lang.String country) {
			userAddress.setCountry(country);
		}


		public java.lang.String getProvince() {
			return StringUtils.defaultIfEmpty(getUserAddress().getProvince(), "");
		}


		public void setProvince(java.lang.String province) {
			userAddress.setProvince(province);
		}


		public java.lang.String getCity() {
			return StringUtils.defaultIfEmpty(getUserAddress().getCity(), "");
		}


		public void setCity(java.lang.String city) {
			userAddress.setCity(city);
		}


		public java.lang.String getDistrict() {
			return StringUtils.defaultIfEmpty(getUserAddress().getDistrict(), "");
		}


		public void setDistrict(java.lang.String district) {
			userAddress.setDistrict(district);
		}

	

}
