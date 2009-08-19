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
public class Payment extends ModelObject {

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
  private java.lang.String payId; 

  @Persistent
  private java.lang.String payCode; 

  @Persistent
  private java.lang.String payName; 

  @Persistent
  private java.lang.String payFee; 

  @Persistent
  private java.lang.String payDesc; 

  @Persistent
  private java.lang.Long payOrder=0l; 

  @Persistent
  private java.lang.String payConfig; 

  @Persistent
  private java.lang.Boolean enabled=false; 

  @Persistent
  private java.lang.Boolean isCod=false; 

  @Persistent
  private java.lang.Boolean isOnline=false; 



	public Payment() {
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



  public java.lang.String getPayId() {
    return payId;
  }

  public void setPayId(java.lang.String newPayId) {
    payId = newPayId;
  }



  public java.lang.String getPayCode() {
    return payCode;
  }

  public void setPayCode(java.lang.String newPayCode) {
    payCode = newPayCode;
  }



  public java.lang.String getPayName() {
    return payName;
  }

  public void setPayName(java.lang.String newPayName) {
    payName = newPayName;
  }



  public java.lang.String getPayFee() {
    return payFee;
  }

  public void setPayFee(java.lang.String newPayFee) {
    payFee = newPayFee;
  }



  public java.lang.String getPayDesc() {
    return payDesc;
  }

  public void setPayDesc(java.lang.String newPayDesc) {
    payDesc = newPayDesc;
  }



  public java.lang.Long getPayOrder() {
    return payOrder;
  }

  public void setPayOrder(java.lang.Long newPayOrder) {
    payOrder = newPayOrder;
  }



  public java.lang.String getPayConfig() {
    return payConfig;
  }

  public void setPayConfig(java.lang.String newPayConfig) {
    payConfig = newPayConfig;
  }



  public java.lang.Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(java.lang.Boolean newEnabled) {
    enabled = newEnabled;
  }



  public java.lang.Boolean getIsCod() {
    return isCod;
  }

  public void setIsCod(java.lang.Boolean newIsCod) {
    isCod = newIsCod;
  }



  public java.lang.Boolean getIsOnline() {
    return isOnline;
  }

  public void setIsOnline(java.lang.Boolean newIsOnline) {
    isOnline = newIsOnline;
  }

}
