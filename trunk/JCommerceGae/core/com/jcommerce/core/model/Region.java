package com.jcommerce.core.model;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.jcommerce.core.annotation.IsPK;

/**  
 * generated with my extension of middleGen 
 * @author <a href="http://code.google.com/p/gcloudshop/">Leon</a>
 */
 
@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable="true") 
public class Region extends ModelObject {

    
    // relations
  
    
    
    
  // fields
  @Persistent
  private java.lang.String regionId; 

  @Persistent
  @IsPK(myclazz="com.jcommerce.core.model.Region")
  private java.lang.String parentId; 

  @Persistent
  private java.lang.String regionName; 

  @Persistent
  private java.lang.Long regionType=0l; 

  @Persistent
  private java.lang.String agencyId; 



	public Region() {
	}





  public java.lang.String getRegionId() {
    return regionId;
  }

  public void setRegionId(java.lang.String newRegionId) {
    regionId = newRegionId;
  }



  public java.lang.String getParentId() {
    return parentId;
  }

  public void setParentId(java.lang.String newParentId) {
    parentId = newParentId;
  }



  public java.lang.String getRegionName() {
    return regionName;
  }

  public void setRegionName(java.lang.String newRegionName) {
    regionName = newRegionName;
  }



  public java.lang.Long getRegionType() {
    return regionType;
  }

  public void setRegionType(java.lang.Long newRegionType) {
    regionType = newRegionType;
  }



  public java.lang.String getAgencyId() {
    return agencyId;
  }

  public void setAgencyId(java.lang.String newAgencyId) {
    agencyId = newAgencyId;
  }

}
