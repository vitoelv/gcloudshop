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
public class Category extends ModelObject {

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
  private java.lang.String keywords; 

  @Persistent
  private java.lang.String catDesc; 

  @Persistent
  @IsPK(clazz="com.jcommerce.core.model.Category")
  private java.lang.String parentId; 

  @Persistent
  private java.lang.Long sortOrder=0l; 

  @Persistent
  private java.lang.String templateFile; 

  @Persistent
  private java.lang.String measureUnit; 

  @Persistent
  private java.lang.Long showInNav=0l; 

  @Persistent
  private java.lang.String style; 

  @Persistent
  private java.lang.Boolean isShow=false; 

  @Persistent
  private java.lang.Long grade=0l; 

  @Persistent
  private java.lang.Long filterAttr=0l; 



	public Category() {
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



  public java.lang.String getParentId() {
    return parentId;
  }

  public void setParentId(java.lang.String newParentId) {
    parentId = newParentId;
  }



  public java.lang.Long getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(java.lang.Long newSortOrder) {
    sortOrder = newSortOrder;
  }



  public java.lang.String getTemplateFile() {
    return templateFile;
  }

  public void setTemplateFile(java.lang.String newTemplateFile) {
    templateFile = newTemplateFile;
  }



  public java.lang.String getMeasureUnit() {
    return measureUnit;
  }

  public void setMeasureUnit(java.lang.String newMeasureUnit) {
    measureUnit = newMeasureUnit;
  }







  public java.lang.String getStyle() {
    return style;
  }

  public void setStyle(java.lang.String newStyle) {
    style = newStyle;
  }



  public java.lang.Boolean getIsShow() {
    return isShow;
  }

  public void setIsShow(java.lang.Boolean newIsShow) {
    isShow = newIsShow;
  }



  public java.lang.Long getGrade() {
    return grade;
  }

  public void setGrade(java.lang.Long newGrade) {
    grade = newGrade;
  }



  public java.lang.Long getFilterAttr() {
    return filterAttr;
  }

  public void setFilterAttr(java.lang.Long newFilterAttr) {
    filterAttr = newFilterAttr;
  }


	public java.lang.Long getShowInNav() {
		return showInNav;
	}

	public void setShowInNav(java.lang.Long showInNav) {
		this.showInNav = showInNav;
	}



}
