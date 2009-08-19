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
public class EmailSendlist extends ModelObject {

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
  private java.lang.Long id=0l; 

  @Persistent
  private java.lang.String email; 

  @Persistent
  private java.lang.String templateId; 

  @Persistent
  private java.lang.String emailContent; 

  @Persistent
  private java.lang.Long error=0l; 

  @Persistent
  private java.lang.Long pri=0l; 

  @Persistent
  private java.lang.Long lastSend=0l; 



	public EmailSendlist() {
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



  public java.lang.Long getId() {
    return id;
  }

  public void setId(java.lang.Long newId) {
    id = newId;
  }



  public java.lang.String getEmail() {
    return email;
  }

  public void setEmail(java.lang.String newEmail) {
    email = newEmail;
  }



  public java.lang.String getTemplateId() {
    return templateId;
  }

  public void setTemplateId(java.lang.String newTemplateId) {
    templateId = newTemplateId;
  }



  public java.lang.String getEmailContent() {
    return emailContent;
  }

  public void setEmailContent(java.lang.String newEmailContent) {
    emailContent = newEmailContent;
  }



  public java.lang.Long getError() {
    return error;
  }

  public void setError(java.lang.Long newError) {
    error = newError;
  }



  public java.lang.Long getPri() {
    return pri;
  }

  public void setPri(java.lang.Long newPri) {
    pri = newPri;
  }



  public java.lang.Long getLastSend() {
    return lastSend;
  }

  public void setLastSend(java.lang.Long newLastSend) {
    lastSend = newLastSend;
  }

}
