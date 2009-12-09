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
public class ShopConfig extends ModelObject {


    // relations
        
    
  // fields
  @Persistent
  private java.lang.Long id=0l; 

  @Persistent
  private java.lang.String parentId; 

  @Persistent
  private java.lang.String code; 

  @Persistent
  private java.lang.String type; 

  @Persistent
  private java.lang.String storeRange; 

  @Persistent
  private java.lang.String storeDir; 

  @Persistent
  private java.lang.String value; 

  @Persistent
  private java.lang.Long sortOrder=0l; 



	public ShopConfig() {
	}



  public java.lang.Long getId() {
    return id;
  }

  public void setId(java.lang.Long newId) {
    id = newId;
  }



  public java.lang.String getParentId() {
    return parentId;
  }

  public void setParentId(java.lang.String newParentId) {
    parentId = newParentId;
  }



  public java.lang.String getCode() {
    return code;
  }

  public void setCode(java.lang.String newCode) {
    code = newCode;
  }



  public java.lang.String getType() {
    return type;
  }

  public void setType(java.lang.String newType) {
    type = newType;
  }



  public java.lang.String getStoreRange() {
    return storeRange;
  }

  public void setStoreRange(java.lang.String newStoreRange) {
    storeRange = newStoreRange;
  }



  public java.lang.String getStoreDir() {
    return storeDir;
  }

  public void setStoreDir(java.lang.String newStoreDir) {
    storeDir = newStoreDir;
  }



  public java.lang.String getValue() {
    return value;
  }

  public void setValue(java.lang.String newValue) {
    value = newValue;
  }



  public java.lang.Long getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(java.lang.Long newSortOrder) {
    sortOrder = newSortOrder;
  }

}
