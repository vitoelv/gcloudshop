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
public class Ad extends ModelObject {

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
  private java.lang.String adId; 

  @Persistent
  private java.lang.String positionId; 

  @Persistent
  private java.lang.Long mediaType=0l; 

  @Persistent
  private java.lang.String adName; 

  @Persistent
  private java.lang.String adLink; 

  @Persistent
  private java.lang.String adCode; 

  @Persistent
  private java.lang.Long startTime=0l; 

  @Persistent
  private java.lang.Long endTime=0l; 

  @Persistent
  private java.lang.String linkMan; 

  @Persistent
  private java.lang.String linkEmail; 

  @Persistent
  private java.lang.String linkPhone; 

  @Persistent
  private java.lang.Long clickCount=0l; 

  @Persistent
  private java.lang.Boolean enabled=false; 



	public Ad() {
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



  public java.lang.String getAdId() {
    return adId;
  }

  public void setAdId(java.lang.String newAdId) {
    adId = newAdId;
  }



  public java.lang.String getPositionId() {
    return positionId;
  }

  public void setPositionId(java.lang.String newPositionId) {
    positionId = newPositionId;
  }



  public java.lang.Long getMediaType() {
    return mediaType;
  }

  public void setMediaType(java.lang.Long newMediaType) {
    mediaType = newMediaType;
  }



  public java.lang.String getAdName() {
    return adName;
  }

  public void setAdName(java.lang.String newAdName) {
    adName = newAdName;
  }



  public java.lang.String getAdLink() {
    return adLink;
  }

  public void setAdLink(java.lang.String newAdLink) {
    adLink = newAdLink;
  }



  public java.lang.String getAdCode() {
    return adCode;
  }

  public void setAdCode(java.lang.String newAdCode) {
    adCode = newAdCode;
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



  public java.lang.String getLinkMan() {
    return linkMan;
  }

  public void setLinkMan(java.lang.String newLinkMan) {
    linkMan = newLinkMan;
  }



  public java.lang.String getLinkEmail() {
    return linkEmail;
  }

  public void setLinkEmail(java.lang.String newLinkEmail) {
    linkEmail = newLinkEmail;
  }



  public java.lang.String getLinkPhone() {
    return linkPhone;
  }

  public void setLinkPhone(java.lang.String newLinkPhone) {
    linkPhone = newLinkPhone;
  }



  public java.lang.Long getClickCount() {
    return clickCount;
  }

  public void setClickCount(java.lang.Long newClickCount) {
    clickCount = newClickCount;
  }



  public java.lang.Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(java.lang.Boolean newEnabled) {
    enabled = newEnabled;
  }

}
