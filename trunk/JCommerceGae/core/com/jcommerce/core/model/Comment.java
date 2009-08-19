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
public class Comment extends ModelObject {

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
  private java.lang.String commentId; 

  @Persistent
  private java.lang.Long commentType=0l; 

  @Persistent
  private java.lang.Long idValue=0l; 

  @Persistent
  private java.lang.String email; 

  @Persistent
  private java.lang.String userName; 

  @Persistent
  private java.lang.String content; 

  @Persistent
  private java.lang.Long commentRank=0l; 

  @Persistent
  private java.lang.Long addTime=0l; 

  @Persistent
  private java.lang.String ipAddress; 

  @Persistent
  private java.lang.Long status=0l; 

  @Persistent
  private java.lang.String parentId; 

  @Persistent
  private java.lang.String userId; 



	public Comment() {
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



  public java.lang.String getCommentId() {
    return commentId;
  }

  public void setCommentId(java.lang.String newCommentId) {
    commentId = newCommentId;
  }



  public java.lang.Long getCommentType() {
    return commentType;
  }

  public void setCommentType(java.lang.Long newCommentType) {
    commentType = newCommentType;
  }



  public java.lang.Long getIdValue() {
    return idValue;
  }

  public void setIdValue(java.lang.Long newIdValue) {
    idValue = newIdValue;
  }



  public java.lang.String getEmail() {
    return email;
  }

  public void setEmail(java.lang.String newEmail) {
    email = newEmail;
  }



  public java.lang.String getUserName() {
    return userName;
  }

  public void setUserName(java.lang.String newUserName) {
    userName = newUserName;
  }



  public java.lang.String getContent() {
    return content;
  }

  public void setContent(java.lang.String newContent) {
    content = newContent;
  }



  public java.lang.Long getCommentRank() {
    return commentRank;
  }

  public void setCommentRank(java.lang.Long newCommentRank) {
    commentRank = newCommentRank;
  }



  public java.lang.Long getAddTime() {
    return addTime;
  }

  public void setAddTime(java.lang.Long newAddTime) {
    addTime = newAddTime;
  }



  public java.lang.String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(java.lang.String newIpAddress) {
    ipAddress = newIpAddress;
  }



  public java.lang.Long getStatus() {
    return status;
  }

  public void setStatus(java.lang.Long newStatus) {
    status = newStatus;
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

}
