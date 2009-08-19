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
public class Nav extends ModelObject {

	public static final int CATEGORYTYPE_GOODSCATEGORY=1;//货物分类导航
	public static final int CATEGORYTYPE_ARTICLECATEGORY=2;//文章分类导航
	
	public static final int TYPE_TOP=1;//上部
	public static final int TYPE_MIDDLE=2;//中部
	public static final int TYPE_BOTTOM=3;//下部
	
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
  private java.lang.String ctype; 

  @Persistent
  private java.lang.Long cid=0l; 

  @Persistent
  private java.lang.String name; 

  @Persistent
  private java.lang.Long ifshow=0l; 

  @Persistent
  private java.lang.Long vieworder=0l; 

  @Persistent
  private java.lang.Long opennew=0l; 

  @Persistent
  private java.lang.String url; 

  @Persistent
  private java.lang.String type; 



	public Nav() {
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



  public java.lang.String getCtype() {
    return ctype;
  }

  public void setCtype(java.lang.String newCtype) {
    ctype = newCtype;
  }



  public java.lang.Long getCid() {
    return cid;
  }

  public void setCid(java.lang.Long newCid) {
    cid = newCid;
  }



  public java.lang.String getName() {
    return name;
  }

  public void setName(java.lang.String newName) {
    name = newName;
  }



  public java.lang.Long getIfshow() {
    return ifshow;
  }

  public void setIfshow(java.lang.Long newIfshow) {
    ifshow = newIfshow;
  }



  public java.lang.Long getVieworder() {
    return vieworder;
  }

  public void setVieworder(java.lang.Long newVieworder) {
    vieworder = newVieworder;
  }



  public java.lang.Long getOpennew() {
    return opennew;
  }

  public void setOpennew(java.lang.Long newOpennew) {
    opennew = newOpennew;
  }



  public java.lang.String getUrl() {
    return url;
  }

  public void setUrl(java.lang.String newUrl) {
    url = newUrl;
  }



  public java.lang.String getType() {
    return type;
  }

  public void setType(java.lang.String newType) {
    type = newType;
  }

}
