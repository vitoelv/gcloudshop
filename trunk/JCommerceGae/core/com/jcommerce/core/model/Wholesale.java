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
public class Wholesale extends ModelObject {

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
  private java.lang.String actId; 

  @Persistent
  private java.lang.String goodsId; 

  @Persistent
  private java.lang.String goodsName; 

  @Persistent
  private java.lang.String rankIds; 

  @Persistent
  private java.lang.String prices; 

  @Persistent
  private java.lang.Boolean enabled=false; 



	public Wholesale() {
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



  public java.lang.String getActId() {
    return actId;
  }

  public void setActId(java.lang.String newActId) {
    actId = newActId;
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



  public java.lang.String getRankIds() {
    return rankIds;
  }

  public void setRankIds(java.lang.String newRankIds) {
    rankIds = newRankIds;
  }



  public java.lang.String getPrices() {
    return prices;
  }

  public void setPrices(java.lang.String newPrices) {
    prices = newPrices;
  }



  public java.lang.Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(java.lang.Boolean newEnabled) {
    enabled = newEnabled;
  }

}
