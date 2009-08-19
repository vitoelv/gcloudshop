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
public class UserFeed extends ModelObject {

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
  private java.lang.String feedId; 

  @Persistent
  private java.lang.String userId; 

  @Persistent
  private java.lang.String valueId; 

  @Persistent
  private java.lang.String goodsId; 

  @Persistent
  private java.lang.Long feedType=0l; 

  @Persistent
  private java.lang.Boolean isFeed=false; 



	public UserFeed() {
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



  public java.lang.String getFeedId() {
    return feedId;
  }

  public void setFeedId(java.lang.String newFeedId) {
    feedId = newFeedId;
  }



  public java.lang.String getUserId() {
    return userId;
  }

  public void setUserId(java.lang.String newUserId) {
    userId = newUserId;
  }



  public java.lang.String getValueId() {
    return valueId;
  }

  public void setValueId(java.lang.String newValueId) {
    valueId = newValueId;
  }



  public java.lang.String getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(java.lang.String newGoodsId) {
    goodsId = newGoodsId;
  }



  public java.lang.Long getFeedType() {
    return feedType;
  }

  public void setFeedType(java.lang.Long newFeedType) {
    feedType = newFeedType;
  }



  public java.lang.Boolean getIsFeed() {
    return isFeed;
  }

  public void setIsFeed(java.lang.Boolean newIsFeed) {
    isFeed = newIsFeed;
  }

}
