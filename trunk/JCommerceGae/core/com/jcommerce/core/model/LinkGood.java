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
public class LinkGood extends ModelObject {

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
  private java.lang.String goodsId; 

  @Persistent
  private java.lang.String linkGoodsId; 

  @Persistent
  private java.lang.Boolean isDouble=false; 

  @Persistent
  private java.lang.String adminId; 



	public LinkGood() {
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



  public java.lang.String getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(java.lang.String newGoodsId) {
    goodsId = newGoodsId;
  }



  public java.lang.String getLinkGoodsId() {
    return linkGoodsId;
  }

  public void setLinkGoodsId(java.lang.String newLinkGoodsId) {
    linkGoodsId = newLinkGoodsId;
  }



  public java.lang.Boolean getIsDouble() {
    return isDouble;
  }

  public void setIsDouble(java.lang.Boolean newIsDouble) {
    isDouble = newIsDouble;
  }



  public java.lang.String getAdminId() {
    return adminId;
  }

  public void setAdminId(java.lang.String newAdminId) {
    adminId = newAdminId;
  }

}
