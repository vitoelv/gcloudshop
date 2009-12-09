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
public class UserRank extends ModelObject {


    
    // relations
        
    
  // fields
  @Persistent
  private java.lang.String rankId; 

  @Persistent
  private java.lang.String rankName; 

  @Persistent
  private java.lang.Long minPoints=0l; 

  @Persistent
  private java.lang.Long maxPoints=0l; 

  @Persistent
  private java.lang.Long discount=0l; 

  @Persistent
  private java.lang.Long showPrice=0l; 

  @Persistent
  private java.lang.Long specialRank=0l; 



	public UserRank() {
	}






  public java.lang.String getRankId() {
    return rankId;
  }

  public void setRankId(java.lang.String newRankId) {
    rankId = newRankId;
  }



  public java.lang.String getRankName() {
    return rankName;
  }

  public void setRankName(java.lang.String newRankName) {
    rankName = newRankName;
  }



  public java.lang.Long getMinPoints() {
    return minPoints;
  }

  public void setMinPoints(java.lang.Long newMinPoints) {
    minPoints = newMinPoints;
  }



  public java.lang.Long getMaxPoints() {
    return maxPoints;
  }

  public void setMaxPoints(java.lang.Long newMaxPoints) {
    maxPoints = newMaxPoints;
  }



  public java.lang.Long getDiscount() {
    return discount;
  }

  public void setDiscount(java.lang.Long newDiscount) {
    discount = newDiscount;
  }



  public java.lang.Long getShowPrice() {
    return showPrice;
  }

  public void setShowPrice(java.lang.Long newShowPrice) {
    showPrice = newShowPrice;
  }



  public java.lang.Long getSpecialRank() {
    return specialRank;
  }

  public void setSpecialRank(java.lang.Long newSpecialRank) {
    specialRank = newSpecialRank;
  }

}
