package com.jcommerce.core.model;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**  
 * generated with my extension of middleGen 
 * @author <a href="http://code.google.com/p/gcloudshop/">Leon</a>
 */
 
@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable="true") 
public class AreaRegion extends ModelObject {


    // relations
    @Persistent
    private ShippingArea shippingArea;
    @NotPersistent
    private String regionName;
    
  
    
  // fields
  @Persistent
  private java.lang.String shippingAreaId; 

  @Persistent
  private java.lang.String regionId; 



	public AreaRegion() {
	}




  public java.lang.String getShippingAreaId() {
    return shippingAreaId;
  }

  public void setShippingAreaId(java.lang.String newShippingAreaId) {
    shippingAreaId = newShippingAreaId;
  }



  public java.lang.String getRegionId() {
    return regionId;
  }

  public void setRegionId(java.lang.String newRegionId) {
    regionId = newRegionId;
  }


  public ShippingArea getShippingArea() {
	return shippingArea;
  }


  public void setShippingArea(ShippingArea shippingArea) {
	this.shippingArea = shippingArea;
  }


  public String getRegionName() {
	return regionName;
  }


  public void setRegionName(String regionName) {
	this.regionName = regionName;
  }

}
