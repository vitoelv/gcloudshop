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
public class AutoManage extends ModelObject {

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
  private java.lang.String itemId; 

  @Persistent
  private java.lang.String type; 

  @Persistent
  private java.lang.Long starttime=0l; 

  @Persistent
  private java.lang.Long endtime=0l; 



	public AutoManage() {
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



  public java.lang.String getItemId() {
    return itemId;
  }

  public void setItemId(java.lang.String newItemId) {
    itemId = newItemId;
  }



  public java.lang.String getType() {
    return type;
  }

  public void setType(java.lang.String newType) {
    type = newType;
  }



  public java.lang.Long getStarttime() {
    return starttime;
  }

  public void setStarttime(java.lang.Long newStarttime) {
    starttime = newStarttime;
  }



  public java.lang.Long getEndtime() {
    return endtime;
  }

  public void setEndtime(java.lang.Long newEndtime) {
    endtime = newEndtime;
  }

}
