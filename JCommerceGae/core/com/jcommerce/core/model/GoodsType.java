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
public class GoodsType extends ModelObject {


    
    // relations
    @Persistent(mappedBy="goodsType")
	private Set<Attribute> attributes = new HashSet<Attribute>();
    
    @NotPersistent
	private long attrcount;
    
	public Set<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}


    
    
  // fields
  @Persistent
  private java.lang.String catId; 

  @Persistent
  private java.lang.String catName; 

  @Persistent
  private java.lang.Boolean enabled=false; 

  @Persistent
  private java.lang.String attrGroup; 



	public GoodsType() {
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



  public java.lang.Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(java.lang.Boolean newEnabled) {
    enabled = newEnabled;
  }



  public java.lang.String getAttrGroup() {
    return attrGroup;
  }

  public void setAttrGroup(java.lang.String newAttrGroup) {
    attrGroup = newAttrGroup;
  }

	public long getAttrcount() {
		return attrcount;
	}

	public void setAttrcount(long attrcount) {
		this.attrcount = attrcount;
	}

}
