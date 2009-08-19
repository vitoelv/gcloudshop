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
public class User extends ModelObject {

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
  private java.lang.String email; 

  @Persistent
  private java.lang.String userName; 

  @Persistent
  private java.lang.String password; 

  @Persistent
  private java.lang.String question; 

  @Persistent
  private java.lang.String answer; 

  @Persistent
  private java.lang.Long sex=0l; 

  @Persistent
  private java.util.Date birthday; 

  @Persistent
  private java.lang.Double userMoney=0.0; 

  @Persistent
  private java.lang.Double frozenMoney=0.0; 

  @Persistent
  private java.lang.Long payPoints=0l; 

  @Persistent
  private java.lang.Long rankPoints=0l; 

  @Persistent
  private java.lang.String addressId; 

  @Persistent
  private java.lang.Long regTime=0l; 

  @Persistent
  private java.lang.Long lastLogin=0l; 

  @Persistent
  private java.util.Date lastTime; 

  @Persistent
  private java.lang.String lastIp; 

  @Persistent
  private java.lang.Long visitCount=0l; 

  @Persistent
  private java.lang.Long userRank=0l; 

  @Persistent
  private java.lang.Boolean isSpecial=false; 

  @Persistent
  private java.lang.String salt; 

  @Persistent
  private java.lang.String parentId; 

  @Persistent
  private java.lang.Long flag=0l; 

  @Persistent
  private java.lang.String alias; 

  @Persistent
  private java.lang.String msn; 

  @Persistent
  private java.lang.String qq; 

  @Persistent
  private java.lang.String officePhone; 

  @Persistent
  private java.lang.String homePhone; 

  @Persistent
  private java.lang.String mobilePhone; 

  @Persistent
  private java.lang.Boolean isValidated=false; 

  @Persistent
  private java.lang.Double creditLine=0.0; 



	public User() {
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



  public java.lang.String getPassword() {
    return password;
  }

  public void setPassword(java.lang.String newPassword) {
    password = newPassword;
  }



  public java.lang.String getQuestion() {
    return question;
  }

  public void setQuestion(java.lang.String newQuestion) {
    question = newQuestion;
  }



  public java.lang.String getAnswer() {
    return answer;
  }

  public void setAnswer(java.lang.String newAnswer) {
    answer = newAnswer;
  }



  public java.lang.Long getSex() {
    return sex;
  }

  public void setSex(java.lang.Long newSex) {
    sex = newSex;
  }



  public java.util.Date getBirthday() {
    return birthday;
  }

  public void setBirthday(java.util.Date newBirthday) {
    birthday = newBirthday;
  }



  public java.lang.Double getUserMoney() {
    return userMoney;
  }

  public void setUserMoney(java.lang.Double newUserMoney) {
    userMoney = newUserMoney;
  }



  public java.lang.Double getFrozenMoney() {
    return frozenMoney;
  }

  public void setFrozenMoney(java.lang.Double newFrozenMoney) {
    frozenMoney = newFrozenMoney;
  }



  public java.lang.Long getPayPoints() {
    return payPoints;
  }

  public void setPayPoints(java.lang.Long newPayPoints) {
    payPoints = newPayPoints;
  }



  public java.lang.Long getRankPoints() {
    return rankPoints;
  }

  public void setRankPoints(java.lang.Long newRankPoints) {
    rankPoints = newRankPoints;
  }



  public java.lang.String getAddressId() {
    return addressId;
  }

  public void setAddressId(java.lang.String newAddressId) {
    addressId = newAddressId;
  }



  public java.lang.Long getRegTime() {
    return regTime;
  }

  public void setRegTime(java.lang.Long newRegTime) {
    regTime = newRegTime;
  }



  public java.lang.Long getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(java.lang.Long newLastLogin) {
    lastLogin = newLastLogin;
  }



  public java.util.Date getLastTime() {
    return lastTime;
  }

  public void setLastTime(java.util.Date newLastTime) {
    lastTime = newLastTime;
  }



  public java.lang.String getLastIp() {
    return lastIp;
  }

  public void setLastIp(java.lang.String newLastIp) {
    lastIp = newLastIp;
  }



  public java.lang.Long getVisitCount() {
    return visitCount;
  }

  public void setVisitCount(java.lang.Long newVisitCount) {
    visitCount = newVisitCount;
  }



  public java.lang.Long getUserRank() {
    return userRank;
  }

  public void setUserRank(java.lang.Long newUserRank) {
    userRank = newUserRank;
  }



  public java.lang.Boolean getIsSpecial() {
    return isSpecial;
  }

  public void setIsSpecial(java.lang.Boolean newIsSpecial) {
    isSpecial = newIsSpecial;
  }



  public java.lang.String getSalt() {
    return salt;
  }

  public void setSalt(java.lang.String newSalt) {
    salt = newSalt;
  }



  public java.lang.String getParentId() {
    return parentId;
  }

  public void setParentId(java.lang.String newParentId) {
    parentId = newParentId;
  }



  public java.lang.Long getFlag() {
    return flag;
  }

  public void setFlag(java.lang.Long newFlag) {
    flag = newFlag;
  }



  public java.lang.String getAlias() {
    return alias;
  }

  public void setAlias(java.lang.String newAlias) {
    alias = newAlias;
  }



  public java.lang.String getMsn() {
    return msn;
  }

  public void setMsn(java.lang.String newMsn) {
    msn = newMsn;
  }



  public java.lang.String getQq() {
    return qq;
  }

  public void setQq(java.lang.String newQq) {
    qq = newQq;
  }



  public java.lang.String getOfficePhone() {
    return officePhone;
  }

  public void setOfficePhone(java.lang.String newOfficePhone) {
    officePhone = newOfficePhone;
  }



  public java.lang.String getHomePhone() {
    return homePhone;
  }

  public void setHomePhone(java.lang.String newHomePhone) {
    homePhone = newHomePhone;
  }



  public java.lang.String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(java.lang.String newMobilePhone) {
    mobilePhone = newMobilePhone;
  }



  public java.lang.Boolean getIsValidated() {
    return isValidated;
  }

  public void setIsValidated(java.lang.Boolean newIsValidated) {
    isValidated = newIsValidated;
  }



  public java.lang.Double getCreditLine() {
    return creditLine;
  }

  public void setCreditLine(java.lang.Double newCreditLine) {
    creditLine = newCreditLine;
  }

}
