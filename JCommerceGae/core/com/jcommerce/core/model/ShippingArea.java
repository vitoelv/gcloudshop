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
public class ShippingArea extends ModelObject {

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
  private java.lang.String shippingAreaId; 

  @Persistent
  private java.lang.String shippingAreaName; 

  @Persistent
  private java.lang.String shippingId; 

  @Persistent
  private java.lang.String configure; 



	public ShippingArea() {
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

}
