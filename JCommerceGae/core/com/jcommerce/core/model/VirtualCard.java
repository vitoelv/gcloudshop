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
public class VirtualCard extends ModelObject {

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
  private java.lang.String cardId; 

  @Persistent
  private java.lang.String goodsId; 

  @Persistent
  private java.lang.String cardSn; 

  @Persistent
  private java.lang.String cardPassword; 

  @Persistent
  private java.lang.Long addDate=0l; 

  @Persistent
  private java.lang.Long endDate=0l; 

  @Persistent
  private java.lang.Boolean isSaled=false; 

  @Persistent
  private java.lang.String orderSn; 

  @Persistent
  private java.lang.Long crc32=0l; 



	public VirtualCard() {
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



  public java.lang.String getCardId() {
    return cardId;
  }

  public void setCardId(java.lang.String newCardId) {
    cardId = newCardId;
  }



  public java.lang.String getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(java.lang.String newGoodsId) {
    goodsId = newGoodsId;
  }



  public java.lang.String getCardSn() {
    return cardSn;
  }

  public void setCardSn(java.lang.String newCardSn) {
    cardSn = newCardSn;
  }



  public java.lang.String getCardPassword() {
    return cardPassword;
  }

  public void setCardPassword(java.lang.String newCardPassword) {
    cardPassword = newCardPassword;
  }



  public java.lang.Long getAddDate() {
    return addDate;
  }

  public void setAddDate(java.lang.Long newAddDate) {
    addDate = newAddDate;
  }



  public java.lang.Long getEndDate() {
    return endDate;
  }

  public void setEndDate(java.lang.Long newEndDate) {
    endDate = newEndDate;
  }



  public java.lang.Boolean getIsSaled() {
    return isSaled;
  }

  public void setIsSaled(java.lang.Boolean newIsSaled) {
    isSaled = newIsSaled;
  }



  public java.lang.String getOrderSn() {
    return orderSn;
  }

  public void setOrderSn(java.lang.String newOrderSn) {
    orderSn = newOrderSn;
  }



  public java.lang.Long getCrc32() {
    return crc32;
  }

  public void setCrc32(java.lang.Long newCrc32) {
    crc32 = newCrc32;
  }

}
