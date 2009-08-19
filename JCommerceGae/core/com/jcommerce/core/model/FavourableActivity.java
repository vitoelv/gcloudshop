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
public class FavourableActivity extends ModelObject {

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
  private java.lang.String actId; 

  @Persistent
  private java.lang.String actName; 

  @Persistent
  private java.lang.Long startTime=0l; 

  @Persistent
  private java.lang.Long endTime=0l; 

  @Persistent
  private java.lang.String userRank; 

  @Persistent
  private java.lang.Long actRange=0l; 

  @Persistent
  private java.lang.String actRangeExt; 

  @Persistent
  private java.lang.Double minAmount=0.0; 

  @Persistent
  private java.lang.Double maxAmount=0.0; 

  @Persistent
  private java.lang.Long actType=0l; 

  @Persistent
  private java.lang.Double actTypeExt=0.0; 

  @Persistent
  private java.lang.String gift; 

  @Persistent
  private java.lang.Long sortOrder=0l; 



	public FavourableActivity() {
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



  public java.lang.String getActId() {
    return actId;
  }

  public void setActId(java.lang.String newActId) {
    actId = newActId;
  }



  public java.lang.String getActName() {
    return actName;
  }

  public void setActName(java.lang.String newActName) {
    actName = newActName;
  }



  public java.lang.Long getStartTime() {
    return startTime;
  }

  public void setStartTime(java.lang.Long newStartTime) {
    startTime = newStartTime;
  }



  public java.lang.Long getEndTime() {
    return endTime;
  }

  public void setEndTime(java.lang.Long newEndTime) {
    endTime = newEndTime;
  }



  public java.lang.String getUserRank() {
    return userRank;
  }

  public void setUserRank(java.lang.String newUserRank) {
    userRank = newUserRank;
  }



  public java.lang.Long getActRange() {
    return actRange;
  }

  public void setActRange(java.lang.Long newActRange) {
    actRange = newActRange;
  }



  public java.lang.String getActRangeExt() {
    return actRangeExt;
  }

  public void setActRangeExt(java.lang.String newActRangeExt) {
    actRangeExt = newActRangeExt;
  }



  public java.lang.Double getMinAmount() {
    return minAmount;
  }

  public void setMinAmount(java.lang.Double newMinAmount) {
    minAmount = newMinAmount;
  }



  public java.lang.Double getMaxAmount() {
    return maxAmount;
  }

  public void setMaxAmount(java.lang.Double newMaxAmount) {
    maxAmount = newMaxAmount;
  }



  public java.lang.Long getActType() {
    return actType;
  }

  public void setActType(java.lang.Long newActType) {
    actType = newActType;
  }



  public java.lang.Double getActTypeExt() {
    return actTypeExt;
  }

  public void setActTypeExt(java.lang.Double newActTypeExt) {
    actTypeExt = newActTypeExt;
  }



  public java.lang.String getGift() {
    return gift;
  }

  public void setGift(java.lang.String newGift) {
    gift = newGift;
  }



  public java.lang.Long getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(java.lang.Long newSortOrder) {
    sortOrder = newSortOrder;
  }

}
