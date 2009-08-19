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
public class AdPosition extends ModelObject {

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
  private java.lang.String positionId; 

  @Persistent
  private java.lang.String positionName; 

  @Persistent
  private java.lang.Long adWidth=0l; 

  @Persistent
  private java.lang.Long adHeight=0l; 

  @Persistent
  private java.lang.String positionDesc; 

  @Persistent
  private java.lang.String positionStyle; 



	public AdPosition() {
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



  public java.lang.String getPositionId() {
    return positionId;
  }

  public void setPositionId(java.lang.String newPositionId) {
    positionId = newPositionId;
  }



  public java.lang.String getPositionName() {
    return positionName;
  }

  public void setPositionName(java.lang.String newPositionName) {
    positionName = newPositionName;
  }



  public java.lang.Long getAdWidth() {
    return adWidth;
  }

  public void setAdWidth(java.lang.Long newAdWidth) {
    adWidth = newAdWidth;
  }



  public java.lang.Long getAdHeight() {
    return adHeight;
  }

  public void setAdHeight(java.lang.Long newAdHeight) {
    adHeight = newAdHeight;
  }



  public java.lang.String getPositionDesc() {
    return positionDesc;
  }

  public void setPositionDesc(java.lang.String newPositionDesc) {
    positionDesc = newPositionDesc;
  }



  public java.lang.String getPositionStyle() {
    return positionStyle;
  }

  public void setPositionStyle(java.lang.String newPositionStyle) {
    positionStyle = newPositionStyle;
  }

}
