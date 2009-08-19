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
public class Cart extends ModelObject {

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
  private java.lang.String recId; 

  @Persistent
  private java.lang.String userId; 

  @Persistent
  private java.lang.String sessionId; 

  @Persistent
  private java.lang.String goodsId; 

  @Persistent
  private java.lang.String goodsSn; 

  @Persistent
  private java.lang.String goodsName; 

  @Persistent
  private java.lang.Double marketPrice=0.0; 

  @Persistent
  private java.lang.Double goodsPrice=0.0; 

  @Persistent
  private java.lang.Long goodsNumber=0l; 

  @Persistent
  private java.lang.String goodsAttr; 

  @Persistent
  private java.lang.Boolean isReal=false; 

  @Persistent
  private java.lang.String extensionCode; 

  @Persistent
  private java.lang.String parentId; 

  @Persistent
  private java.lang.Long recType=0l; 

  @Persistent
  private java.lang.Boolean isGift=false; 

  @Persistent
  private java.lang.Long canHandsel=0l; 

  @Persistent
  private java.lang.String goodsAttrId; 



	public Cart() {
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



  public java.lang.String getSessionId() {
    return sessionId;
  }

  public void setSessionId(java.lang.String newSessionId) {
    sessionId = newSessionId;
  }



  public java.lang.String getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(java.lang.String newGoodsId) {
    goodsId = newGoodsId;
  }



  public java.lang.String getGoodsSn() {
    return goodsSn;
  }

  public void setGoodsSn(java.lang.String newGoodsSn) {
    goodsSn = newGoodsSn;
  }



  public java.lang.String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(java.lang.String newGoodsName) {
    goodsName = newGoodsName;
  }



  public java.lang.Double getMarketPrice() {
    return marketPrice;
  }

  public void setMarketPrice(java.lang.Double newMarketPrice) {
    marketPrice = newMarketPrice;
  }



  public java.lang.Double getGoodsPrice() {
    return goodsPrice;
  }

  public void setGoodsPrice(java.lang.Double newGoodsPrice) {
    goodsPrice = newGoodsPrice;
  }



  public java.lang.Long getGoodsNumber() {
    return goodsNumber;
  }

  public void setGoodsNumber(java.lang.Long newGoodsNumber) {
    goodsNumber = newGoodsNumber;
  }



  public java.lang.String getGoodsAttr() {
    return goodsAttr;
  }

  public void setGoodsAttr(java.lang.String newGoodsAttr) {
    goodsAttr = newGoodsAttr;
  }



  public java.lang.Boolean getIsReal() {
    return isReal;
  }

  public void setIsReal(java.lang.Boolean newIsReal) {
    isReal = newIsReal;
  }



  public java.lang.String getExtensionCode() {
    return extensionCode;
  }

  public void setExtensionCode(java.lang.String newExtensionCode) {
    extensionCode = newExtensionCode;
  }



  public java.lang.String getParentId() {
    return parentId;
  }

  public void setParentId(java.lang.String newParentId) {
    parentId = newParentId;
  }



  public java.lang.Long getRecType() {
    return recType;
  }

  public void setRecType(java.lang.Long newRecType) {
    recType = newRecType;
  }



  public java.lang.Boolean getIsGift() {
    return isGift;
  }

  public void setIsGift(java.lang.Boolean newIsGift) {
    isGift = newIsGift;
  }



  public java.lang.Long getCanHandsel() {
    return canHandsel;
  }

  public void setCanHandsel(java.lang.Long newCanHandsel) {
    canHandsel = newCanHandsel;
  }



  public java.lang.String getGoodsAttrId() {
    return goodsAttrId;
  }

  public void setGoodsAttrId(java.lang.String newGoodsAttrId) {
    goodsAttrId = newGoodsAttrId;
  }

}
