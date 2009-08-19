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
public class AdminUser extends ModelObject {

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
  private java.lang.String userId; 

  @Persistent
  private java.lang.String userName; 

  @Persistent
  private java.lang.String email; 

  @Persistent
  private java.lang.String password; 

  @Persistent
  private java.lang.Long addTime=0l; 

  @Persistent
  private java.lang.Long lastLogin=0l; 

  @Persistent
  private java.lang.String lastIp; 

  @Persistent
  private java.lang.String actionList; 

  @Persistent
  private java.lang.String navList; 

  @Persistent
  private java.lang.String langType; 

  @Persistent
  private java.lang.String agencyId; 

  @Persistent
  private java.lang.String todolist; 



	public AdminUser() {
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



  public java.lang.String getEmail() {
    return email;
  }

  public void setEmail(java.lang.String newEmail) {
    email = newEmail;
  }



  public java.lang.String getPassword() {
    return password;
  }

  public void setPassword(java.lang.String newPassword) {
    password = newPassword;
  }



  public java.lang.Long getAddTime() {
    return addTime;
  }

  public void setAddTime(java.lang.Long newAddTime) {
    addTime = newAddTime;
  }



  public java.lang.Long getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(java.lang.Long newLastLogin) {
    lastLogin = newLastLogin;
  }



  public java.lang.String getLastIp() {
    return lastIp;
  }

  public void setLastIp(java.lang.String newLastIp) {
    lastIp = newLastIp;
  }



  public java.lang.String getActionList() {
    return actionList;
  }

  public void setActionList(java.lang.String newActionList) {
    actionList = newActionList;
  }



  public java.lang.String getNavList() {
    return navList;
  }

  public void setNavList(java.lang.String newNavList) {
    navList = newNavList;
  }



  public java.lang.String getLangType() {
    return langType;
  }

  public void setLangType(java.lang.String newLangType) {
    langType = newLangType;
  }



  public java.lang.String getAgencyId() {
    return agencyId;
  }

  public void setAgencyId(java.lang.String newAgencyId) {
    agencyId = newAgencyId;
  }



  public java.lang.String getTodolist() {
    return todolist;
  }

  public void setTodolist(java.lang.String newTodolist) {
    todolist = newTodolist;
  }

}
