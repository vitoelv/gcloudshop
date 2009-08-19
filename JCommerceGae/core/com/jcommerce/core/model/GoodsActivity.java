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
public class GoodsActivity extends ModelObject {

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
  private java.lang.String actId; 

  @Persistent
  private java.lang.String actName; 

  @Persistent
  private java.lang.String actDesc; 

  @Persistent
  private java.lang.Long actType=0l; 

  @Persistent
  private java.lang.String goodsId; 

  @Persistent
  private java.lang.String goodsName; 

  @Persistent
  private java.lang.Long startTime=0l; 

  @Persistent
  private java.lang.Long endTime=0l; 

  @Persistent
  private java.lang.Boolean isFinished=false; 

  @Persistent
  private java.lang.String extInfo; 



	public GoodsActivity() {
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



  public java.lang.String getActId() {
    return actId;
  }

  public void setActId(java.lang.String newActId) {
    actId = newActId;
  }



  public java.lang.String getActName() {
    return actName;
  }

  public void setActName(java.lang.String newActName) {
    actName = newActName;
  }



  public java.lang.String getActDesc() {
    return actDesc;
  }

  public void setActDesc(java.lang.String newActDesc) {
    actDesc = newActDesc;
  }



  public java.lang.Long getActType() {
    return actType;
  }

  public void setActType(java.lang.Long newActType) {
    actType = newActType;
  }



  public java.lang.String getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(java.lang.String newGoodsId) {
    goodsId = newGoodsId;
  }



  public java.lang.String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(java.lang.String newGoodsName) {
    goodsName = newGoodsName;
  }



  public java.lang.Long getStartTime() {
    return startTime;
  }

  public void setStartTime(java.lang.Long newStartTime) {
    startTime = newStartTime;
  }



  public java.lang.Long getEndTime() {
    return endTime;
  }

  public void setEndTime(java.lang.Long newEndTime) {
    endTime = newEndTime;
  }



  public java.lang.Boolean getIsFinished() {
    return isFinished;
  }

  public void setIsFinished(java.lang.Boolean newIsFinished) {
    isFinished = newIsFinished;
  }



  public java.lang.String getExtInfo() {
    return extInfo;
  }

  public void setExtInfo(java.lang.String newExtInfo) {
    extInfo = newExtInfo;
  }

}
