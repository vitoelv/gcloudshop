package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;
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
public class GoodsAttr extends ModelObject {


    
    // relations
    	@Persistent
	private Goods goods;


  
public Goods getGoods() {
	return goods;
}


public void setGoods(Goods goods) {
	this.goods = goods;
}    
    
  // fields
  @Persistent
  private java.lang.String goodsAttrId; 

  @Persistent
  private java.lang.String goodsId; 

  @Persistent
  @IsPK(myclazz="com.jcommerce.core.model.Attribute")
  private java.lang.String attrId; 

  @Persistent
  private java.lang.String attrValue; 

  @Persistent
  private java.lang.String attrPrice; 



	public GoodsAttr() {
	}





  public java.lang.String getGoodsAttrId() {
    return goodsAttrId;
  }

  public void setGoodsAttrId(java.lang.String newGoodsAttrId) {
    goodsAttrId = newGoodsAttrId;
  }



  public java.lang.String getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(java.lang.String newGoodsId) {
    goodsId = newGoodsId;
  }



  public java.lang.String getAttrId() {
    return attrId;
  }

  public void setAttrId(java.lang.String newAttrId) {
    attrId = newAttrId;
  }



  public java.lang.String getAttrValue() {
    return attrValue;
  }

  public void setAttrValue(java.lang.String newAttrValue) {
    attrValue = newAttrValue;
  }



  public java.lang.String getAttrPrice() {
    return attrPrice;
  }

  public void setAttrPrice(java.lang.String newAttrPrice) {
    attrPrice = newAttrPrice;
  }

}
