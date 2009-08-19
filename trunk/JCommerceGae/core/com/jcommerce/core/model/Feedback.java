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
public class Feedback extends ModelObject {

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
  private java.lang.String msgId; 

  @Persistent
  private java.lang.String parentId; 

  @Persistent
  private java.lang.String userId; 

  @Persistent
  private java.lang.String userName; 

  @Persistent
  private java.lang.String userEmail; 

  @Persistent
  private java.lang.String msgTitle; 

  @Persistent
  private java.lang.Long msgType=0l; 

  @Persistent
  private java.lang.String msgContent; 

  @Persistent
  private java.lang.Long msgTime=0l; 

  @Persistent
  private java.lang.String messageImg; 

  @Persistent
  private java.lang.String orderId; 

  @Persistent
  private java.lang.Long msgArea=0l; 



	public Feedback() {
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



  public java.lang.String getMsgId() {
    return msgId;
  }

  public void setMsgId(java.lang.String newMsgId) {
    msgId = newMsgId;
  }



  public java.lang.String getParentId() {
    return parentId;
  }

  public void setParentId(java.lang.String newParentId) {
    parentId = newParentId;
  }



  public java.lang.String getUserId() {
    return userId;
  }

  public void setUserId(java.lang.String newUserId) {
    userId = newUserId;
  }



  public java.lang.String getUserName() {
    return userName;
  }

  public void setUserName(java.lang.String newUserName) {
    userName = newUserName;
  }



  public java.lang.String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(java.lang.String newUserEmail) {
    userEmail = newUserEmail;
  }



  public java.lang.String getMsgTitle() {
    return msgTitle;
  }

  public void setMsgTitle(java.lang.String newMsgTitle) {
    msgTitle = newMsgTitle;
  }



  public java.lang.Long getMsgType() {
    return msgType;
  }

  public void setMsgType(java.lang.Long newMsgType) {
    msgType = newMsgType;
  }



  public java.lang.String getMsgContent() {
    return msgContent;
  }

  public void setMsgContent(java.lang.String newMsgContent) {
    msgContent = newMsgContent;
  }



  public java.lang.Long getMsgTime() {
    return msgTime;
  }

  public void setMsgTime(java.lang.Long newMsgTime) {
    msgTime = newMsgTime;
  }



  public java.lang.String getMessageImg() {
    return messageImg;
  }

  public void setMessageImg(java.lang.String newMessageImg) {
    messageImg = newMessageImg;
  }



  public java.lang.String getOrderId() {
    return orderId;
  }

  public void setOrderId(java.lang.String newOrderId) {
    orderId = newOrderId;
  }



  public java.lang.Long getMsgArea() {
    return msgArea;
  }

  public void setMsgArea(java.lang.Long newMsgArea) {
    msgArea = newMsgArea;
  }

}
