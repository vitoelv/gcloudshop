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
public class Stat extends ModelObject {

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
  private java.lang.Long accessTime=0l; 

  @Persistent
  private java.lang.String ipAddress; 

  @Persistent
  private java.lang.Long visitTimes=0l; 

  @Persistent
  private java.lang.String browser; 

  @Persistent
  private java.lang.String system; 

  @Persistent
  private java.lang.String language; 

  @Persistent
  private java.lang.String area; 

  @Persistent
  private java.lang.String refererDomain; 

  @Persistent
  private java.lang.String refererPath; 

  @Persistent
  private java.lang.String accessUrl; 



	public Stat() {
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



  public java.lang.Long getAccessTime() {
    return accessTime;
  }

  public void setAccessTime(java.lang.Long newAccessTime) {
    accessTime = newAccessTime;
  }



  public java.lang.String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(java.lang.String newIpAddress) {
    ipAddress = newIpAddress;
  }



  public java.lang.Long getVisitTimes() {
    return visitTimes;
  }

  public void setVisitTimes(java.lang.Long newVisitTimes) {
    visitTimes = newVisitTimes;
  }



  public java.lang.String getBrowser() {
    return browser;
  }

  public void setBrowser(java.lang.String newBrowser) {
    browser = newBrowser;
  }



  public java.lang.String getSystem() {
    return system;
  }

  public void setSystem(java.lang.String newSystem) {
    system = newSystem;
  }



  public java.lang.String getLanguage() {
    return language;
  }

  public void setLanguage(java.lang.String newLanguage) {
    language = newLanguage;
  }



  public java.lang.String getArea() {
    return area;
  }

  public void setArea(java.lang.String newArea) {
    area = newArea;
  }



  public java.lang.String getRefererDomain() {
    return refererDomain;
  }

  public void setRefererDomain(java.lang.String newRefererDomain) {
    refererDomain = newRefererDomain;
  }



  public java.lang.String getRefererPath() {
    return refererPath;
  }

  public void setRefererPath(java.lang.String newRefererPath) {
    refererPath = newRefererPath;
  }



  public java.lang.String getAccessUrl() {
    return accessUrl;
  }

  public void setAccessUrl(java.lang.String newAccessUrl) {
    accessUrl = newAccessUrl;
  }

}
