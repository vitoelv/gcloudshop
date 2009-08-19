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
public class Plugin extends ModelObject {

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
  private java.lang.String code; 

  @Persistent
  private java.lang.String version; 

  @Persistent
  private java.lang.String library; 

  @Persistent
  private java.lang.Long assign=0l; 

  @Persistent
  private java.lang.Long installDate=0l; 



	public Plugin() {
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



  public java.lang.String getCode() {
    return code;
  }

  public void setCode(java.lang.String newCode) {
    code = newCode;
  }



  public java.lang.String getVersion() {
    return version;
  }

  public void setVersion(java.lang.String newVersion) {
    version = newVersion;
  }



  public java.lang.String getLibrary() {
    return library;
  }

  public void setLibrary(java.lang.String newLibrary) {
    library = newLibrary;
  }



  public java.lang.Long getAssign() {
    return assign;
  }

  public void setAssign(java.lang.Long newAssign) {
    assign = newAssign;
  }



  public java.lang.Long getInstallDate() {
    return installDate;
  }

  public void setInstallDate(java.lang.Long newInstallDate) {
    installDate = newInstallDate;
  }

}
