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
public class UserBonus extends ModelObject {

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
  private java.lang.String bonusId; 

  @Persistent
  private java.lang.String bonusTypeId; 

  @Persistent
  private java.lang.Long bonusSn=0l; 

  @Persistent
  private java.lang.String userId; 

  @Persistent
  private java.lang.Long usedTime=0l; 

  @Persistent
  private java.lang.String orderId; 

  @Persistent
  private java.lang.Long emailed=0l; 



	public UserBonus() {
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



  public java.lang.String getBonusId() {
    return bonusId;
  }

  public void setBonusId(java.lang.String newBonusId) {
    bonusId = newBonusId;
  }



  public java.lang.String getBonusTypeId() {
    return bonusTypeId;
  }

  public void setBonusTypeId(java.lang.String newBonusTypeId) {
    bonusTypeId = newBonusTypeId;
  }



  public java.lang.Long getBonusSn() {
    return bonusSn;
  }

  public void setBonusSn(java.lang.Long newBonusSn) {
    bonusSn = newBonusSn;
  }



  public java.lang.String getUserId() {
    return userId;
  }

  public void setUserId(java.lang.String newUserId) {
    userId = newUserId;
  }



  public java.lang.Long getUsedTime() {
    return usedTime;
  }

  public void setUsedTime(java.lang.Long newUsedTime) {
    usedTime = newUsedTime;
  }



  public java.lang.String getOrderId() {
    return orderId;
  }

  public void setOrderId(java.lang.String newOrderId) {
    orderId = newOrderId;
  }



  public java.lang.Long getEmailed() {
    return emailed;
  }

  public void setEmailed(java.lang.Long newEmailed) {
    emailed = newEmailed;
  }

}
