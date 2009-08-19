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
public class Brand extends ModelObject {

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
        // TODO leon combine logo and logoFileId to a DSFile instance, 
    // in order to make DSFile a child of Brand, and thus can be put in a DS transaction
    @Persistent
    private String logoFileId;
    
//    @Persistent
//    private DSFile logo;

public String getLogoFileId() {
	return logoFileId;
}


public void setLogoFileId(String logoFileId) {
	this.logoFileId = logoFileId;
}
    
    
  // fields
  @Persistent
  private java.lang.String brandId; 

  @Persistent
  private java.lang.String brandName; 

  @Persistent
  private java.lang.String brandLogo; 

  @Persistent
  private java.lang.String brandDesc; 

  @Persistent
  private java.lang.String siteUrl; 

  @Persistent
  private java.lang.Long sortOrder=0l; 

  @Persistent
  private java.lang.Boolean isShow=false; 



	public Brand() {
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



  public java.lang.String getBrandId() {
    return brandId;
  }

  public void setBrandId(java.lang.String newBrandId) {
    brandId = newBrandId;
  }



  public java.lang.String getBrandName() {
    return brandName;
  }

  public void setBrandName(java.lang.String newBrandName) {
    brandName = newBrandName;
  }



  public java.lang.String getBrandLogo() {
    return brandLogo;
  }

  public void setBrandLogo(java.lang.String newBrandLogo) {
    brandLogo = newBrandLogo;
  }



  public java.lang.String getBrandDesc() {
    return brandDesc;
  }

  public void setBrandDesc(java.lang.String newBrandDesc) {
    brandDesc = newBrandDesc;
  }



  public java.lang.String getSiteUrl() {
    return siteUrl;
  }

  public void setSiteUrl(java.lang.String newSiteUrl) {
    siteUrl = newSiteUrl;
  }



  public java.lang.Long getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(java.lang.Long newSortOrder) {
    sortOrder = newSortOrder;
  }



  public java.lang.Boolean getIsShow() {
    return isShow;
  }

  public void setIsShow(java.lang.Boolean newIsShow) {
    isShow = newIsShow;
  }

}
