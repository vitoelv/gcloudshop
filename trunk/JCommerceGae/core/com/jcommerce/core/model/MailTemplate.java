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
public class MailTemplate extends ModelObject {

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
  private java.lang.String templateId; 

  @Persistent
  private java.lang.String templateCode; 

  @Persistent
  private java.lang.Boolean isHtml=false; 

  @Persistent
  private java.lang.String templateSubject; 

  @Persistent
  private java.lang.String templateContent; 

  @Persistent
  private java.lang.Long lastModify=0l; 

  @Persistent
  private java.lang.Long lastSend=0l; 

  @Persistent
  private java.lang.String type; 



	public MailTemplate() {
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



  public java.lang.String getTemplateId() {
    return templateId;
  }

  public void setTemplateId(java.lang.String newTemplateId) {
    templateId = newTemplateId;
  }



  public java.lang.String getTemplateCode() {
    return templateCode;
  }

  public void setTemplateCode(java.lang.String newTemplateCode) {
    templateCode = newTemplateCode;
  }



  public java.lang.Boolean getIsHtml() {
    return isHtml;
  }

  public void setIsHtml(java.lang.Boolean newIsHtml) {
    isHtml = newIsHtml;
  }



  public java.lang.String getTemplateSubject() {
    return templateSubject;
  }

  public void setTemplateSubject(java.lang.String newTemplateSubject) {
    templateSubject = newTemplateSubject;
  }



  public java.lang.String getTemplateContent() {
    return templateContent;
  }

  public void setTemplateContent(java.lang.String newTemplateContent) {
    templateContent = newTemplateContent;
  }



  public java.lang.Long getLastModify() {
    return lastModify;
  }

  public void setLastModify(java.lang.Long newLastModify) {
    lastModify = newLastModify;
  }



  public java.lang.Long getLastSend() {
    return lastSend;
  }

  public void setLastSend(java.lang.Long newLastSend) {
    lastSend = newLastSend;
  }



  public java.lang.String getType() {
    return type;
  }

  public void setType(java.lang.String newType) {
    type = newType;
  }

}
