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
public class SnatchLog extends ModelObject {

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
  private java.lang.String snatchId; 

  @Persistent
  private java.lang.String userId; 

  @Persistent
  private java.lang.Double bidPrice=0.0; 

  @Persistent
  private java.lang.Long bidTime=0l; 



	public SnatchLog() {
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



  public java.lang.String getSnatchId() {
    return snatchId;
  }

  public void setSnatchId(java.lang.String newSnatchId) {
    snatchId = newSnatchId;
  }



  public java.lang.String getUserId() {
    return userId;
  }

  public void setUserId(java.lang.String newUserId) {
    userId = newUserId;
  }



  public java.lang.Double getBidPrice() {
    return bidPrice;
  }

  public void setBidPrice(java.lang.Double newBidPrice) {
    bidPrice = newBidPrice;
  }



  public java.lang.Long getBidTime() {
    return bidTime;
  }

  public void setBidTime(java.lang.Long newBidTime) {
    bidTime = newBidTime;
  }

}
