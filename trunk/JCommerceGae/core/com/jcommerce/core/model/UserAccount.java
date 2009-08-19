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
public class UserAccount extends ModelObject {

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
  private java.lang.Long id=0l; 

  @Persistent
  private java.lang.String userId; 

  @Persistent
  private java.lang.String adminUser; 

  @Persistent
  private java.lang.Double amount=0.0; 

  @Persistent
  private java.lang.Long addTime=0l; 

  @Persistent
  private java.lang.Long paidTime=0l; 

  @Persistent
  private java.lang.String adminNote; 

  @Persistent
  private java.lang.String userNote; 

  @Persistent
  private java.lang.Long processType=0l; 

  @Persistent
  private java.lang.String payment; 

  @Persistent
  private java.lang.Boolean isPaid=false; 



	public UserAccount() {
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



  public java.lang.Long getId() {
    return id;
  }

  public void setId(java.lang.Long newId) {
    id = newId;
  }



  public java.lang.String getUserId() {
    return userId;
  }

  public void setUserId(java.lang.String newUserId) {
    userId = newUserId;
  }



  public java.lang.String getAdminUser() {
    return adminUser;
  }

  public void setAdminUser(java.lang.String newAdminUser) {
    adminUser = newAdminUser;
  }



  public java.lang.Double getAmount() {
    return amount;
  }

  public void setAmount(java.lang.Double newAmount) {
    amount = newAmount;
  }



  public java.lang.Long getAddTime() {
    return addTime;
  }

  public void setAddTime(java.lang.Long newAddTime) {
    addTime = newAddTime;
  }



  public java.lang.Long getPaidTime() {
    return paidTime;
  }

  public void setPaidTime(java.lang.Long newPaidTime) {
    paidTime = newPaidTime;
  }



  public java.lang.String getAdminNote() {
    return adminNote;
  }

  public void setAdminNote(java.lang.String newAdminNote) {
    adminNote = newAdminNote;
  }



  public java.lang.String getUserNote() {
    return userNote;
  }

  public void setUserNote(java.lang.String newUserNote) {
    userNote = newUserNote;
  }



  public java.lang.Long getProcessType() {
    return processType;
  }

  public void setProcessType(java.lang.Long newProcessType) {
    processType = newProcessType;
  }



  public java.lang.String getPayment() {
    return payment;
  }

  public void setPayment(java.lang.String newPayment) {
    payment = newPayment;
  }



  public java.lang.Boolean getIsPaid() {
    return isPaid;
  }

  public void setIsPaid(java.lang.Boolean newIsPaid) {
    isPaid = newIsPaid;
  }

}
