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
public class Topic extends ModelObject {

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
  private java.lang.String topicId; 

  @Persistent
  private java.lang.String title; 

  @Persistent
  private java.lang.String intro; 

  @Persistent
  private java.lang.Long startTime=0l; 

  @Persistent
  private java.lang.Long endTime=0l; 

  @Persistent
  private java.lang.String data; 

  @Persistent
  private java.lang.String template; 

  @Persistent
  private java.lang.String css; 



	public Topic() {
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



  public java.lang.String getTopicId() {
    return topicId;
  }

  public void setTopicId(java.lang.String newTopicId) {
    topicId = newTopicId;
  }



  public java.lang.String getTitle() {
    return title;
  }

  public void setTitle(java.lang.String newTitle) {
    title = newTitle;
  }



  public java.lang.String getIntro() {
    return intro;
  }

  public void setIntro(java.lang.String newIntro) {
    intro = newIntro;
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



  public java.lang.String getData() {
    return data;
  }

  public void setData(java.lang.String newData) {
    data = newData;
  }



  public java.lang.String getTemplate() {
    return template;
  }

  public void setTemplate(java.lang.String newTemplate) {
    template = newTemplate;
  }



  public java.lang.String getCss() {
    return css;
  }

  public void setCss(java.lang.String newCss) {
    css = newCss;
  }

}
