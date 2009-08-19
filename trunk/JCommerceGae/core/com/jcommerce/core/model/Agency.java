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
public class Agency extends ModelObject {

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
  private java.lang.String agencyId; 

  @Persistent
  private java.lang.String agencyName; 

  @Persistent
  private java.lang.String agencyDesc; 



	public Agency() {
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



  public java.lang.String getAgencyId() {
    return agencyId;
  }

  public void setAgencyId(java.lang.String newAgencyId) {
    agencyId = newAgencyId;
  }



  public java.lang.String getAgencyName() {
    return agencyName;
  }

  public void setAgencyName(java.lang.String newAgencyName) {
    agencyName = newAgencyName;
  }



  public java.lang.String getAgencyDesc() {
    return agencyDesc;
  }

  public void setAgencyDesc(java.lang.String newAgencyDesc) {
    agencyDesc = newAgencyDesc;
  }

}
