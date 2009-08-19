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
public class Template extends ModelObject {

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
  private java.lang.String filename; 

  @Persistent
  private java.lang.String region; 

  @Persistent
  private java.lang.String library; 

  @Persistent
  private java.lang.Long sortOrder=0l; 

  @Persistent
  private java.lang.Long id=0l; 

  @Persistent
  private java.lang.Long number=0l; 

  @Persistent
  private java.lang.Long type=0l; 

  @Persistent
  private java.lang.String theme; 

  @Persistent
  private java.lang.String remarks; 



	public Template() {
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



  public java.lang.String getFilename() {
    return filename;
  }

  public void setFilename(java.lang.String newFilename) {
    filename = newFilename;
  }



  public java.lang.String getRegion() {
    return region;
  }

  public void setRegion(java.lang.String newRegion) {
    region = newRegion;
  }



  public java.lang.String getLibrary() {
    return library;
  }

  public void setLibrary(java.lang.String newLibrary) {
    library = newLibrary;
  }



  public java.lang.Long getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(java.lang.Long newSortOrder) {
    sortOrder = newSortOrder;
  }



  public java.lang.Long getId() {
    return id;
  }

  public void setId(java.lang.Long newId) {
    id = newId;
  }



  public java.lang.Long getNumber() {
    return number;
  }

  public void setNumber(java.lang.Long newNumber) {
    number = newNumber;
  }



  public java.lang.Long getType() {
    return type;
  }

  public void setType(java.lang.Long newType) {
    type = newType;
  }



  public java.lang.String getTheme() {
    return theme;
  }

  public void setTheme(java.lang.String newTheme) {
    theme = newTheme;
  }



  public java.lang.String getRemarks() {
    return remarks;
  }

  public void setRemarks(java.lang.String newRemarks) {
    remarks = newRemarks;
  }

}
