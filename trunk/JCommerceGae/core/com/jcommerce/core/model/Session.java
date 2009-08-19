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
public class Session extends ModelObject {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String pkId;
    
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String keyName;
    
    @Persistent
    private Long longId;
    
    // relations
        
    
  // fields
  @Persistent
  private java.lang.String sesskey; 

  @Persistent
  private java.lang.Long expiry=0l; 

  @Persistent
  private java.lang.Long userid=0l; 

  @Persistent
  private java.lang.Long adminid=0l; 

  @Persistent
  private java.lang.String ip; 

  @Persistent
  private java.lang.String userName; 

  @Persistent
  private java.lang.Long userRank=0l; 

  @Persistent
  private java.lang.Double discount=0.0; 

  @Persistent
  private java.lang.String email; 

  @Persistent
  private java.lang.String data; 



	public Session() {
	}


	@Override
	public Long getLongId() {
		return longId;
	}

	@Override
	public void setLongId(Long longId) {
		this.longId = longId;
	}

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}



  public java.lang.String getSesskey() {
    return sesskey;
  }

  public void setSesskey(java.lang.String newSesskey) {
    sesskey = newSesskey;
  }



  public java.lang.Long getExpiry() {
    return expiry;
  }

  public void setExpiry(java.lang.Long newExpiry) {
    expiry = newExpiry;
  }



  public java.lang.Long getUserid() {
    return userid;
  }

  public void setUserid(java.lang.Long newUserid) {
    userid = newUserid;
  }



  public java.lang.Long getAdminid() {
    return adminid;
  }

  public void setAdminid(java.lang.Long newAdminid) {
    adminid = newAdminid;
  }



  public java.lang.String getIp() {
    return ip;
  }

  public void setIp(java.lang.String newIp) {
    ip = newIp;
  }



  public java.lang.String getUserName() {
    return userName;
  }

  public void setUserName(java.lang.String newUserName) {
    userName = newUserName;
  }



  public java.lang.Long getUserRank() {
    return userRank;
  }

  public void setUserRank(java.lang.Long newUserRank) {
    userRank = newUserRank;
  }



  public java.lang.Double getDiscount() {
    return discount;
  }

  public void setDiscount(java.lang.Double newDiscount) {
    discount = newDiscount;
  }



  public java.lang.String getEmail() {
    return email;
  }

  public void setEmail(java.lang.String newEmail) {
    email = newEmail;
  }



  public java.lang.String getData() {
    return data;
  }

  public void setData(java.lang.String newData) {
    data = newData;
  }

}
