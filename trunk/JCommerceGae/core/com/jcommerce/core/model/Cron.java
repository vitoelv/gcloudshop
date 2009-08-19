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
public class Cron extends ModelObject {

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
  private java.lang.String cronId; 

  @Persistent
  private java.lang.String cronCode; 

  @Persistent
  private java.lang.String cronName; 

  @Persistent
  private java.lang.String cronDesc; 

  @Persistent
  private java.lang.Long cronOrder=0l; 

  @Persistent
  private java.lang.String cronConfig; 

  @Persistent
  private java.lang.Long thistime=0l; 

  @Persistent
  private java.lang.Long nextime=0l; 

  @Persistent
  private java.lang.Long day=0l; 

  @Persistent
  private java.lang.String week; 

  @Persistent
  private java.lang.String hour; 

  @Persistent
  private java.lang.String minute; 

  @Persistent
  private java.lang.Long enable=0l; 

  @Persistent
  private java.lang.Long runOnce=0l; 

  @Persistent
  private java.lang.String allowIp; 

  @Persistent
  private java.lang.String alowFiles; 



	public Cron() {
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



  public java.lang.String getCronId() {
    return cronId;
  }

  public void setCronId(java.lang.String newCronId) {
    cronId = newCronId;
  }



  public java.lang.String getCronCode() {
    return cronCode;
  }

  public void setCronCode(java.lang.String newCronCode) {
    cronCode = newCronCode;
  }



  public java.lang.String getCronName() {
    return cronName;
  }

  public void setCronName(java.lang.String newCronName) {
    cronName = newCronName;
  }



  public java.lang.String getCronDesc() {
    return cronDesc;
  }

  public void setCronDesc(java.lang.String newCronDesc) {
    cronDesc = newCronDesc;
  }



  public java.lang.Long getCronOrder() {
    return cronOrder;
  }

  public void setCronOrder(java.lang.Long newCronOrder) {
    cronOrder = newCronOrder;
  }



  public java.lang.String getCronConfig() {
    return cronConfig;
  }

  public void setCronConfig(java.lang.String newCronConfig) {
    cronConfig = newCronConfig;
  }



  public java.lang.Long getThistime() {
    return thistime;
  }

  public void setThistime(java.lang.Long newThistime) {
    thistime = newThistime;
  }



  public java.lang.Long getNextime() {
    return nextime;
  }

  public void setNextime(java.lang.Long newNextime) {
    nextime = newNextime;
  }



  public java.lang.Long getDay() {
    return day;
  }

  public void setDay(java.lang.Long newDay) {
    day = newDay;
  }



  public java.lang.String getWeek() {
    return week;
  }

  public void setWeek(java.lang.String newWeek) {
    week = newWeek;
  }



  public java.lang.String getHour() {
    return hour;
  }

  public void setHour(java.lang.String newHour) {
    hour = newHour;
  }



  public java.lang.String getMinute() {
    return minute;
  }

  public void setMinute(java.lang.String newMinute) {
    minute = newMinute;
  }



  public java.lang.Long getEnable() {
    return enable;
  }

  public void setEnable(java.lang.Long newEnable) {
    enable = newEnable;
  }



  public java.lang.Long getRunOnce() {
    return runOnce;
  }

  public void setRunOnce(java.lang.Long newRunOnce) {
    runOnce = newRunOnce;
  }



  public java.lang.String getAllowIp() {
    return allowIp;
  }

  public void setAllowIp(java.lang.String newAllowIp) {
    allowIp = newAllowIp;
  }



  public java.lang.String getAlowFiles() {
    return alowFiles;
  }

  public void setAlowFiles(java.lang.String newAlowFiles) {
    alowFiles = newAlowFiles;
  }

}
