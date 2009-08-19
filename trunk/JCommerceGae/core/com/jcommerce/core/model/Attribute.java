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
public class Attribute extends ModelObject {

	
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
        @Persistent
    private GoodsType goodsType;
    
    @Override
    public ModelObject getParent(){
    	return goodsType;
    }

public GoodsType getGoodsType() {
	return goodsType;
}


public void setGoodsType(GoodsType goodsType) {
	this.goodsType = goodsType;
}    
    
  // fields
  @Persistent
  private java.lang.String attrId; 

  @Persistent
  private java.lang.String catId; 

  @Persistent
  private java.lang.String attrName; 

  @Persistent
  private java.lang.Long attrInputType=0l; 

  @Persistent
  private java.lang.Long attrType=0l; 

  @Persistent
  private java.lang.String attrValues; 

  @Persistent
  private java.lang.Long attrIndex=0l; 

  @Persistent
  private java.lang.Long sortOrder=0l; 

  @Persistent
  private java.lang.Boolean isLinked=false; 

  @Persistent
  private java.lang.Long attrGroup=0l; 



	public Attribute() {
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



  public java.lang.String getAttrId() {
    return attrId;
  }

  public void setAttrId(java.lang.String newAttrId) {
    attrId = newAttrId;
  }



  public java.lang.String getCatId() {
    return catId;
  }

  public void setCatId(java.lang.String newCatId) {
    catId = newCatId;
  }



  public java.lang.String getAttrName() {
    return attrName;
  }

  public void setAttrName(java.lang.String newAttrName) {
    attrName = newAttrName;
  }



  public java.lang.Long getAttrInputType() {
    return attrInputType;
  }

  public void setAttrInputType(java.lang.Long newAttrInputType) {
    attrInputType = newAttrInputType;
  }



  public java.lang.Long getAttrType() {
    return attrType;
  }

  public void setAttrType(java.lang.Long newAttrType) {
    attrType = newAttrType;
  }



  public java.lang.String getAttrValues() {
    return attrValues;
  }

  public void setAttrValues(java.lang.String newAttrValues) {
    attrValues = newAttrValues;
  }



  public java.lang.Long getAttrIndex() {
    return attrIndex;
  }

  public void setAttrIndex(java.lang.Long newAttrIndex) {
    attrIndex = newAttrIndex;
  }



  public java.lang.Long getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(java.lang.Long newSortOrder) {
    sortOrder = newSortOrder;
  }



  public java.lang.Boolean getIsLinked() {
    return isLinked;
  }

  public void setIsLinked(java.lang.Boolean newIsLinked) {
    isLinked = newIsLinked;
  }



  public java.lang.Long getAttrGroup() {
    return attrGroup;
  }

  public void setAttrGroup(java.lang.Long newAttrGroup) {
    attrGroup = newAttrGroup;
  }

}
