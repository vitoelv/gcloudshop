package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**  
 * generated with my extension of middleGen 
 * @author <a href="http://code.google.com/p/gcloudshop/">Leon</a>
 */
 
@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable="true") 
public class UserAddress extends ModelObject {


    // relations
        
    
  // fields
  @Persistent
  private java.lang.String addressId; 

  @Persistent
  private java.lang.String addressName; 

  @Persistent
  private java.lang.String userId; 

  @Persistent
  private java.lang.String consignee; 

  @Persistent
  private java.lang.String email; 

  @Persistent
  private java.lang.String country; 

  @Persistent
  private java.lang.String province; 

  @Persistent
  private java.lang.String city; 

  @Persistent
  private java.lang.String district; 

  @Persistent
  private java.lang.String address; 

  @Persistent
  private java.lang.String zipcode; 

  @Persistent
  private java.lang.String tel; 

  @Persistent
  private java.lang.String mobile; 

  @Persistent
  private java.lang.String signBuilding; 

  @Persistent
  private java.lang.String bestTime; 



	public UserAddress() {
	}






  public java.lang.String getAddressId() {
    return addressId;
  }

  public void setAddressId(java.lang.String newAddressId) {
    addressId = newAddressId;
  }



  public java.lang.String getAddressName() {
    return addressName;
  }

  public void setAddressName(java.lang.String newAddressName) {
    addressName = newAddressName;
  }



  public java.lang.String getUserId() {
    return userId;
  }

  public void setUserId(java.lang.String newUserId) {
    userId = newUserId;
  }



  public java.lang.String getConsignee() {
    return consignee;
  }

  public void setConsignee(java.lang.String newConsignee) {
    consignee = newConsignee;
  }



  public java.lang.String getEmail() {
    return email;
  }

  public void setEmail(java.lang.String newEmail) {
    email = newEmail;
  }







  public java.lang.String getAddress() {
    return address;
  }

  public void setAddress(java.lang.String newAddress) {
    address = newAddress;
  }



  public java.lang.String getZipcode() {
    return zipcode;
  }

  public void setZipcode(java.lang.String newZipcode) {
    zipcode = newZipcode;
  }



  public java.lang.String getTel() {
    return tel;
  }

  public void setTel(java.lang.String newTel) {
    tel = newTel;
  }



  public java.lang.String getMobile() {
    return mobile;
  }

  public void setMobile(java.lang.String newMobile) {
    mobile = newMobile;
  }



  public java.lang.String getSignBuilding() {
    return signBuilding;
  }

  public void setSignBuilding(java.lang.String newSignBuilding) {
    signBuilding = newSignBuilding;
  }



  public java.lang.String getBestTime() {
    return bestTime;
  }

  public void setBestTime(java.lang.String newBestTime) {
    bestTime = newBestTime;
  }


public java.lang.String getCountry() {
	return country;
}


public void setCountry(java.lang.String country) {
	this.country = country;
}


public java.lang.String getProvince() {
	return province;
}


public void setProvince(java.lang.String province) {
	this.province = province;
}


public java.lang.String getCity() {
	return city;
}


public void setCity(java.lang.String city) {
	this.city = city;
}


public java.lang.String getDistrict() {
	return district;
}


public void setDistrict(java.lang.String district) {
	this.district = district;
}

}
