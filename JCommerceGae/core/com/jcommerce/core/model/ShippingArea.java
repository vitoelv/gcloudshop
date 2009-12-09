package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;

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
public class ShippingArea extends ModelObject {


    
    // relations
    @Persistent(mappedBy="shippingArea")
    private Set<AreaRegion> areaRegions = new HashSet<AreaRegion>();
	@Persistent
	private Shipping shipping;
    @NotPersistent
	private String regionNames;
    
    
  // fields
  @Persistent
  private java.lang.String shippingAreaId; 

  @Persistent
  private java.lang.String shippingAreaName; 

  @Persistent
  private java.lang.String shippingId; 

  @Persistent
  private java.lang.String configure; 



	public ShippingArea() {
	}






  public java.lang.String getShippingAreaId() {
    return shippingAreaId;
  }

  public void setShippingAreaId(java.lang.String newShippingAreaId) {
    shippingAreaId = newShippingAreaId;
  }



  public java.lang.String getShippingAreaName() {
    return shippingAreaName;
  }

  public void setShippingAreaName(java.lang.String newShippingAreaName) {
    shippingAreaName = newShippingAreaName;
  }



  public java.lang.String getShippingId() {
    return shippingId;
  }

  public void setShippingId(java.lang.String newShippingId) {
    shippingId = newShippingId;
  }



  public java.lang.String getConfigure() {
    return configure;
  }

  public void setConfigure(java.lang.String newConfigure) {
    configure = newConfigure;
  }


  public Set<AreaRegion> getAreaRegions() {
	return areaRegions;
  }


  public void setAreaRegions(Set<AreaRegion> areaRegions) {
	this.areaRegions = areaRegions;
  }


  public String getRegionNames() {
	return regionNames;
  }


  public void setRegionNames(String regionNames) {
	this.regionNames = regionNames;
  }


  public Shipping getShipping() {
	return shipping;
  }


  public void setShipping(Shipping shipping) {
	this.shipping = shipping;
  }

}
