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
public class AffiliateLog extends ModelObject {

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
  private java.lang.String orderId; 

  @Persistent
  private java.lang.Long time=0l; 

  @Persistent
  private java.lang.String userId; 

  @Persistent
  private java.lang.String userName; 

  @Persistent
  private java.lang.Double money=0.0; 

  @Persistent
  private java.lang.Long point=0l; 

  @Persistent
  private java.lang.Long separateType=0l; 



	public AffiliateLog() {
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



  public java.lang.String getOrderId() {
    return orderId;
  }

  public void setOrderId(java.lang.String newOrderId) {
    orderId = newOrderId;
  }



  public java.lang.Long getTime() {
    return time;
  }

  public void setTime(java.lang.Long newTime) {
    time = newTime;
  }



  public java.lang.String getUserId() {
    return userId;
  }

  public void setUserId(java.lang.String newUserId) {
    userId = newUserId;
  }



  public java.lang.String getUserName() {
    return userName;
  }

  public void setUserName(java.lang.String newUserName) {
    userName = newUserName;
  }



  public java.lang.Double getMoney() {
    return money;
  }

  public void setMoney(java.lang.Double newMoney) {
    money = newMoney;
  }



  public java.lang.Long getPoint() {
    return point;
  }

  public void setPoint(java.lang.Long newPoint) {
    point = newPoint;
  }



  public java.lang.Long getSeparateType() {
    return separateType;
  }

  public void setSeparateType(java.lang.Long newSeparateType) {
    separateType = newSeparateType;
  }

}
