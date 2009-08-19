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
public class ArticleCat extends ModelObject {

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
  private java.lang.String catId; 

  @Persistent
  private java.lang.String catName; 

  @Persistent
  private java.lang.Long catType=0l; 

  @Persistent
  private java.lang.String keywords; 

  @Persistent
  private java.lang.String catDesc; 

  @Persistent
  private java.lang.Long sortOrder=0l; 

  @Persistent
  private java.lang.Long showInNav=0l; 

  @Persistent
  private java.lang.String parentId; 



	public ArticleCat() {
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



  public java.lang.String getCatId() {
    return catId;
  }

  public void setCatId(java.lang.String newCatId) {
    catId = newCatId;
  }



  public java.lang.String getCatName() {
    return catName;
  }

  public void setCatName(java.lang.String newCatName) {
    catName = newCatName;
  }



  public java.lang.Long getCatType() {
    return catType;
  }

  public void setCatType(java.lang.Long newCatType) {
    catType = newCatType;
  }



  public java.lang.String getKeywords() {
    return keywords;
  }

  public void setKeywords(java.lang.String newKeywords) {
    keywords = newKeywords;
  }



  public java.lang.String getCatDesc() {
    return catDesc;
  }

  public void setCatDesc(java.lang.String newCatDesc) {
    catDesc = newCatDesc;
  }



  public java.lang.Long getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(java.lang.Long newSortOrder) {
    sortOrder = newSortOrder;
  }



  public java.lang.Long getShowInNav() {
    return showInNav;
  }

  public void setShowInNav(java.lang.Long newShowInNav) {
    showInNav = newShowInNav;
  }



  public java.lang.String getParentId() {
    return parentId;
  }

  public void setParentId(java.lang.String newParentId) {
    parentId = newParentId;
  }

}
