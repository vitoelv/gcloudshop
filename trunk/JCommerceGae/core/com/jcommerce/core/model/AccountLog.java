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
public class AccountLog extends ModelObject {

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
  private java.lang.String logId; 

  @Persistent
  private java.lang.String userId; 

  @Persistent
  private java.lang.Double userMoney=0.0; 

  @Persistent
  private java.lang.Double frozenMoney=0.0; 

  @Persistent
  private java.lang.Long rankPoints=0l; 

  @Persistent
  private java.lang.Long payPoints=0l; 

  @Persistent
  private java.lang.Long changeTime=0l; 

  @Persistent
  private java.lang.String changeDesc; 

  @Persistent
  private java.lang.Long changeType=0l; 



	public AccountLog() {
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



  public java.lang.String getLogId() {
    return logId;
  }

  public void setLogId(java.lang.String newLogId) {
    logId = newLogId;
  }



  public java.lang.String getUserId() {
    return userId;
  }

  public void setUserId(java.lang.String newUserId) {
    userId = newUserId;
  }



  public java.lang.Double getUserMoney() {
    return userMoney;
  }

  public void setUserMoney(java.lang.Double newUserMoney) {
    userMoney = newUserMoney;
  }



  public java.lang.Double getFrozenMoney() {
    return frozenMoney;
  }

  public void setFrozenMoney(java.lang.Double newFrozenMoney) {
    frozenMoney = newFrozenMoney;
  }



  public java.lang.Long getRankPoints() {
    return rankPoints;
  }

  public void setRankPoints(java.lang.Long newRankPoints) {
    rankPoints = newRankPoints;
  }



  public java.lang.Long getPayPoints() {
    return payPoints;
  }

  public void setPayPoints(java.lang.Long newPayPoints) {
    payPoints = newPayPoints;
  }



  public java.lang.Long getChangeTime() {
    return changeTime;
  }

  public void setChangeTime(java.lang.Long newChangeTime) {
    changeTime = newChangeTime;
  }



  public java.lang.String getChangeDesc() {
    return changeDesc;
  }

  public void setChangeDesc(java.lang.String newChangeDesc) {
    changeDesc = newChangeDesc;
  }



  public java.lang.Long getChangeType() {
    return changeType;
  }

  public void setChangeType(java.lang.Long newChangeType) {
    changeType = newChangeType;
  }

}
