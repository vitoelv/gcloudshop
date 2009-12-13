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
public class OrderGoods extends ModelObject implements Cloneable{


    
    // relations
        
    
  // fields
  @Persistent
  private java.lang.String recId; 

  @Persistent
  private java.lang.String orderId; 

  @Persistent
  private java.lang.String goodsId; 

  @Persistent
  private java.lang.String goodsName; 

  @Persistent
  private java.lang.String goodsSn; 

  @Persistent
  private java.lang.Long goodsNumber=0l; 

  @Persistent
  private java.lang.Double marketPrice=0.0; 

  @Persistent
  private java.lang.Double goodsPrice=0.0; 

  @Persistent
  private java.lang.String goodsAttr; 

  @Persistent
  private java.lang.Long sendNumber=0l; 

  @Persistent
  private java.lang.Boolean isReal=false; 

  @Persistent
  private java.lang.String extensionCode; 

  @Persistent
  private java.lang.String parentId; 

  @Persistent
  private java.lang.Boolean isGift=false; 



	public OrderGoods() {
	}






  public java.lang.String getRecId() {
    return recId;
  }

  public void setRecId(java.lang.String newRecId) {
    recId = newRecId;
  }



  public java.lang.String getOrderId() {
    return orderId;
  }

  public void setOrderId(java.lang.String newOrderId) {
    orderId = newOrderId;
  }



  public java.lang.String getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(java.lang.String newGoodsId) {
    goodsId = newGoodsId;
  }



  public java.lang.String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(java.lang.String newGoodsName) {
    goodsName = newGoodsName;
  }



  public java.lang.String getGoodsSn() {
    return goodsSn;
  }

  public void setGoodsSn(java.lang.String newGoodsSn) {
    goodsSn = newGoodsSn;
  }



  public java.lang.Long getGoodsNumber() {
    return goodsNumber;
  }

  public void setGoodsNumber(java.lang.Long newGoodsNumber) {
    goodsNumber = newGoodsNumber;
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



  public java.lang.String getGoodsAttr() {
    return goodsAttr;
  }

  public void setGoodsAttr(java.lang.String newGoodsAttr) {
    goodsAttr = newGoodsAttr;
  }



  public java.lang.Long getSendNumber() {
    return sendNumber;
  }

  public void setSendNumber(java.lang.Long newSendNumber) {
    sendNumber = newSendNumber;
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



  public java.lang.Boolean getIsGift() {
    return isGift;
  }

  public void setIsGift(java.lang.Boolean newIsGift) {
    isGift = newIsGift;
  }

  public Object clone() {   
		OrderGoods o = null;   
		try {   
			o = (OrderGoods) super.clone();   
		} catch (CloneNotSupportedException e) {   
			e.printStackTrace();   
		}   
		return o;   
	  } 
}
