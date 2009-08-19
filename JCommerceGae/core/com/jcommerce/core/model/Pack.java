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
public class Pack extends ModelObject {

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
  private java.lang.String packId; 

  @Persistent
  private java.lang.String packName; 

  @Persistent
  private java.lang.String packImg; 

  @Persistent
  private java.lang.Long packFee=0l; 

  @Persistent
  private java.lang.Long freeMoney=0l; 

  @Persistent
  private java.lang.String packDesc; 



	public Pack() {
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



  public java.lang.String getPackId() {
    return packId;
  }

  public void setPackId(java.lang.String newPackId) {
    packId = newPackId;
  }



  public java.lang.String getPackName() {
    return packName;
  }

  public void setPackName(java.lang.String newPackName) {
    packName = newPackName;
  }



  public java.lang.String getPackImg() {
    return packImg;
  }

  public void setPackImg(java.lang.String newPackImg) {
    packImg = newPackImg;
  }



  public java.lang.Long getPackFee() {
    return packFee;
  }

  public void setPackFee(java.lang.Long newPackFee) {
    packFee = newPackFee;
  }



  public java.lang.Long getFreeMoney() {
    return freeMoney;
  }

  public void setFreeMoney(java.lang.Long newFreeMoney) {
    freeMoney = newFreeMoney;
  }



  public java.lang.String getPackDesc() {
    return packDesc;
  }

  public void setPackDesc(java.lang.String newPackDesc) {
    packDesc = newPackDesc;
  }

}
