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
public class BonusType extends ModelObject {

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
  private java.lang.String typeId; 

  @Persistent
  private java.lang.String typeName; 

  @Persistent
  private java.lang.Double typeMoney=0.0; 

  @Persistent
  private java.lang.Long sendType=0l; 

  @Persistent
  private java.lang.Double minAmount=0.0; 

  @Persistent
  private java.lang.Double maxAmount=0.0; 

  @Persistent
  private java.lang.Long sendStartDate=0l; 

  @Persistent
  private java.lang.Long sendEndDate=0l; 

  @Persistent
  private java.lang.Long useStartDate=0l; 

  @Persistent
  private java.lang.Long useEndDate=0l; 

  @Persistent
  private java.lang.Double minGoodsAmount=0.0; 



	public BonusType() {
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



  public java.lang.String getTypeId() {
    return typeId;
  }

  public void setTypeId(java.lang.String newTypeId) {
    typeId = newTypeId;
  }



  public java.lang.String getTypeName() {
    return typeName;
  }

  public void setTypeName(java.lang.String newTypeName) {
    typeName = newTypeName;
  }



  public java.lang.Double getTypeMoney() {
    return typeMoney;
  }

  public void setTypeMoney(java.lang.Double newTypeMoney) {
    typeMoney = newTypeMoney;
  }



  public java.lang.Long getSendType() {
    return sendType;
  }

  public void setSendType(java.lang.Long newSendType) {
    sendType = newSendType;
  }



  public java.lang.Double getMinAmount() {
    return minAmount;
  }

  public void setMinAmount(java.lang.Double newMinAmount) {
    minAmount = newMinAmount;
  }



  public java.lang.Double getMaxAmount() {
    return maxAmount;
  }

  public void setMaxAmount(java.lang.Double newMaxAmount) {
    maxAmount = newMaxAmount;
  }



  public java.lang.Long getSendStartDate() {
    return sendStartDate;
  }

  public void setSendStartDate(java.lang.Long newSendStartDate) {
    sendStartDate = newSendStartDate;
  }



  public java.lang.Long getSendEndDate() {
    return sendEndDate;
  }

  public void setSendEndDate(java.lang.Long newSendEndDate) {
    sendEndDate = newSendEndDate;
  }



  public java.lang.Long getUseStartDate() {
    return useStartDate;
  }

  public void setUseStartDate(java.lang.Long newUseStartDate) {
    useStartDate = newUseStartDate;
  }



  public java.lang.Long getUseEndDate() {
    return useEndDate;
  }

  public void setUseEndDate(java.lang.Long newUseEndDate) {
    useEndDate = newUseEndDate;
  }



  public java.lang.Double getMinGoodsAmount() {
    return minGoodsAmount;
  }

  public void setMinGoodsAmount(java.lang.Double newMinGoodsAmount) {
    minGoodsAmount = newMinGoodsAmount;
  }

}
