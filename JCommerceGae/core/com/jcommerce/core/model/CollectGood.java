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
public class CollectGood extends ModelObject {


    
    // relations
        
    
  // fields
  @Persistent
  private java.lang.String recId; 

  @Persistent
  private java.lang.String userId; 

  @Persistent
  private java.lang.String goodsId; 

  @Persistent
  private java.lang.Long addTime=0l; 

  @Persistent
  private java.lang.Boolean isAttention=false; 



	public CollectGood() {
	}





  public java.lang.String getRecId() {
    return recId;
  }

  public void setRecId(java.lang.String newRecId) {
    recId = newRecId;
  }



  public java.lang.String getUserId() {
    return userId;
  }

  public void setUserId(java.lang.String newUserId) {
    userId = newUserId;
  }



  public java.lang.String getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(java.lang.String newGoodsId) {
    goodsId = newGoodsId;
  }



  public java.lang.Long getAddTime() {
    return addTime;
  }

  public void setAddTime(java.lang.Long newAddTime) {
    addTime = newAddTime;
  }



  public java.lang.Boolean getIsAttention() {
    return isAttention;
  }

  public void setIsAttention(java.lang.Boolean newIsAttention) {
    isAttention = newIsAttention;
  }

}
