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
public class Adsense extends ModelObject {

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
  private java.lang.Long fromAd=0l; 

  @Persistent
  private java.lang.String referer; 

  @Persistent
  private java.lang.Long clicks=0l; 



	public Adsense() {
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



  public java.lang.Long getFromAd() {
    return fromAd;
  }

  public void setFromAd(java.lang.Long newFromAd) {
    fromAd = newFromAd;
  }



  public java.lang.String getReferer() {
    return referer;
  }

  public void setReferer(java.lang.String newReferer) {
    referer = newReferer;
  }



  public java.lang.Long getClicks() {
    return clicks;
  }

  public void setClicks(java.lang.Long newClicks) {
    clicks = newClicks;
  }

}
