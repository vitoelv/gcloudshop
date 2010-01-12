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
public class MemberPrice extends ModelObject {

    // relations
        
    
  // fields
  @Persistent
  private java.lang.String priceId; 

  @Persistent
  private java.lang.String goodsId; 

  @Persistent
  private java.lang.Long userRank=0l; 

  @Persistent
  private java.lang.Double userPrice=0.0; 



	public MemberPrice() {
	}



  public java.lang.String getPriceId() {
    return priceId;
  }

  public void setPriceId(java.lang.String newPriceId) {
    priceId = newPriceId;
  }



  public java.lang.String getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(java.lang.String newGoodsId) {
    goodsId = newGoodsId;
  }



  public java.lang.Long getUserRank() {
    return userRank;
  }

  public void setUserRank(java.lang.Long newUserRank) {
    userRank = newUserRank;
  }



  public java.lang.Double getUserPrice() {
    return userPrice;
  }

  public void setUserPrice(java.lang.Double newUserPrice) {
    userPrice = newUserPrice;
  }

}
