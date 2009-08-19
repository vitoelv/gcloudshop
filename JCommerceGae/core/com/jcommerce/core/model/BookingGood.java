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
public class BookingGood extends ModelObject {

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
  private java.lang.String recId; 

  @Persistent
  private java.lang.String userId; 

  @Persistent
  private java.lang.String email; 

  @Persistent
  private java.lang.String linkMan; 

  @Persistent
  private java.lang.String tel; 

  @Persistent
  private java.lang.String goodsId; 

  @Persistent
  private java.lang.String goodsDesc; 

  @Persistent
  private java.lang.Long goodsNumber=0l; 

  @Persistent
  private java.lang.Long bookingTime=0l; 

  @Persistent
  private java.lang.Boolean isDispose=false; 

  @Persistent
  private java.lang.String disposeUser; 

  @Persistent
  private java.lang.Long disposeTime=0l; 

  @Persistent
  private java.lang.String disposeNote; 



	public BookingGood() {
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



  public java.lang.String getRecId() {
    return recId;
  }

  public void setRecId(java.lang.String newRecId) {
    recId = newRecId;
  }



  public java.lang.String getUserId() {
    return userId;
  }

  public void setUserId(java.lang.String newUserId) {
    userId = newUserId;
  }



  public java.lang.String getEmail() {
    return email;
  }

  public void setEmail(java.lang.String newEmail) {
    email = newEmail;
  }



  public java.lang.String getLinkMan() {
    return linkMan;
  }

  public void setLinkMan(java.lang.String newLinkMan) {
    linkMan = newLinkMan;
  }



  public java.lang.String getTel() {
    return tel;
  }

  public void setTel(java.lang.String newTel) {
    tel = newTel;
  }



  public java.lang.String getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(java.lang.String newGoodsId) {
    goodsId = newGoodsId;
  }



  public java.lang.String getGoodsDesc() {
    return goodsDesc;
  }

  public void setGoodsDesc(java.lang.String newGoodsDesc) {
    goodsDesc = newGoodsDesc;
  }



  public java.lang.Long getGoodsNumber() {
    return goodsNumber;
  }

  public void setGoodsNumber(java.lang.Long newGoodsNumber) {
    goodsNumber = newGoodsNumber;
  }



  public java.lang.Long getBookingTime() {
    return bookingTime;
  }

  public void setBookingTime(java.lang.Long newBookingTime) {
    bookingTime = newBookingTime;
  }



  public java.lang.Boolean getIsDispose() {
    return isDispose;
  }

  public void setIsDispose(java.lang.Boolean newIsDispose) {
    isDispose = newIsDispose;
  }



  public java.lang.String getDisposeUser() {
    return disposeUser;
  }

  public void setDisposeUser(java.lang.String newDisposeUser) {
    disposeUser = newDisposeUser;
  }



  public java.lang.Long getDisposeTime() {
    return disposeTime;
  }

  public void setDisposeTime(java.lang.Long newDisposeTime) {
    disposeTime = newDisposeTime;
  }



  public java.lang.String getDisposeNote() {
    return disposeNote;
  }

  public void setDisposeNote(java.lang.String newDisposeNote) {
    disposeNote = newDisposeNote;
  }

}
