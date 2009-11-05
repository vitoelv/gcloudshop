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
public class Shipping extends ModelObject {

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
    @Persistent(mappedBy="shipping")
    private Set<ShippingArea> shippingAreas = new HashSet<ShippingArea>();        
    
  // fields
  @Persistent
  private java.lang.String shippingId; 

  @Persistent
  private java.lang.String shippingCode; 

  @Persistent
  private java.lang.String shippingName; 

  @Persistent
  private java.lang.String shippingDesc; 

  @Persistent
  private java.lang.String insure; 

  @Persistent
  private java.lang.Boolean supportCod=false; 

  @Persistent
  private java.lang.Boolean enabled=false; 

  @Persistent
  private java.lang.String shippingPrint; 



	public Shipping() {
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



  public java.lang.String getShippingId() {
    return shippingId;
  }

  public void setShippingId(java.lang.String newShippingId) {
    shippingId = newShippingId;
  }



  public java.lang.String getShippingCode() {
    return shippingCode;
  }

  public void setShippingCode(java.lang.String newShippingCode) {
    shippingCode = newShippingCode;
  }



  public java.lang.String getShippingName() {
    return shippingName;
  }

  public void setShippingName(java.lang.String newShippingName) {
    shippingName = newShippingName;
  }



  public java.lang.String getShippingDesc() {
    return shippingDesc;
  }

  public void setShippingDesc(java.lang.String newShippingDesc) {
    shippingDesc = newShippingDesc;
  }



  public java.lang.String getInsure() {
    return insure;
  }

  public void setInsure(java.lang.String newInsure) {
    insure = newInsure;
  }







  public java.lang.Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(java.lang.Boolean newEnabled) {
    enabled = newEnabled;
  }



  public java.lang.String getShippingPrint() {
    return shippingPrint;
  }

  public void setShippingPrint(java.lang.String newShippingPrint) {
    shippingPrint = newShippingPrint;
  }


  public java.lang.Boolean getSupportCod() {
	return supportCod;
  }


  public void setSupportCod(java.lang.Boolean supportCod) {
	this.supportCod = supportCod;
  }


  public Set<ShippingArea> getShippingAreas() {
	return shippingAreas;
  }


  public void setShippingAreas(Set<ShippingArea> shippingAreas) {
	this.shippingAreas = shippingAreas;
  }

}
