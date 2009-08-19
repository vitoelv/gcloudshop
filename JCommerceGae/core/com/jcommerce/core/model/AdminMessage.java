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
public class AdminMessage extends ModelObject {

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
  private java.lang.String messageId; 

  @Persistent
  private java.lang.String senderId; 

  @Persistent
  private java.lang.String receiverId; 

  @Persistent
  private java.lang.Long sentTime=0l; 

  @Persistent
  private java.lang.Long readTime=0l; 

  @Persistent
  private java.lang.Long readed=0l; 

  @Persistent
  private java.lang.Long deleted=0l; 

  @Persistent
  private java.lang.String title; 

  @Persistent
  private java.lang.String message; 



	public AdminMessage() {
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



  public java.lang.String getMessageId() {
    return messageId;
  }

  public void setMessageId(java.lang.String newMessageId) {
    messageId = newMessageId;
  }



  public java.lang.String getSenderId() {
    return senderId;
  }

  public void setSenderId(java.lang.String newSenderId) {
    senderId = newSenderId;
  }



  public java.lang.String getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(java.lang.String newReceiverId) {
    receiverId = newReceiverId;
  }



  public java.lang.Long getSentTime() {
    return sentTime;
  }

  public void setSentTime(java.lang.Long newSentTime) {
    sentTime = newSentTime;
  }



  public java.lang.Long getReadTime() {
    return readTime;
  }

  public void setReadTime(java.lang.Long newReadTime) {
    readTime = newReadTime;
  }



  public java.lang.Long getReaded() {
    return readed;
  }

  public void setReaded(java.lang.Long newReaded) {
    readed = newReaded;
  }



  public java.lang.Long getDeleted() {
    return deleted;
  }

  public void setDeleted(java.lang.Long newDeleted) {
    deleted = newDeleted;
  }



  public java.lang.String getTitle() {
    return title;
  }

  public void setTitle(java.lang.String newTitle) {
    title = newTitle;
  }



  public java.lang.String getMessage() {
    return message;
  }

  public void setMessage(java.lang.String newMessage) {
    message = newMessage;
  }

}
