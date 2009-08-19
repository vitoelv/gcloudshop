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
public class FriendLink extends ModelObject {

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
  private java.lang.String linkId; 

  @Persistent
  private java.lang.String linkName; 

  @Persistent
  private java.lang.String linkUrl; 

  @Persistent
  private java.lang.String linkLogo; 

  @Persistent
  private java.lang.Long showOrder=0l; 



	public FriendLink() {
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



  public java.lang.String getLinkId() {
    return linkId;
  }

  public void setLinkId(java.lang.String newLinkId) {
    linkId = newLinkId;
  }



  public java.lang.String getLinkName() {
    return linkName;
  }

  public void setLinkName(java.lang.String newLinkName) {
    linkName = newLinkName;
  }



  public java.lang.String getLinkUrl() {
    return linkUrl;
  }

  public void setLinkUrl(java.lang.String newLinkUrl) {
    linkUrl = newLinkUrl;
  }



  public java.lang.String getLinkLogo() {
    return linkLogo;
  }

  public void setLinkLogo(java.lang.String newLinkLogo) {
    linkLogo = newLinkLogo;
  }



  public java.lang.Long getShowOrder() {
    return showOrder;
  }

  public void setShowOrder(java.lang.Long newShowOrder) {
    showOrder = newShowOrder;
  }

}
