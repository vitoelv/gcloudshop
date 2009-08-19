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
public class Vote extends ModelObject {

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
  private java.lang.String voteId; 

  @Persistent
  private java.lang.String voteName; 

  @Persistent
  private java.lang.Long startTime=0l; 

  @Persistent
  private java.lang.Long endTime=0l; 

  @Persistent
  private java.lang.Long canMulti=0l; 

  @Persistent
  private java.lang.Long voteCount=0l; 



	public Vote() {
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



  public java.lang.String getVoteId() {
    return voteId;
  }

  public void setVoteId(java.lang.String newVoteId) {
    voteId = newVoteId;
  }



  public java.lang.String getVoteName() {
    return voteName;
  }

  public void setVoteName(java.lang.String newVoteName) {
    voteName = newVoteName;
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



  public java.lang.Long getCanMulti() {
    return canMulti;
  }

  public void setCanMulti(java.lang.Long newCanMulti) {
    canMulti = newCanMulti;
  }



  public java.lang.Long getVoteCount() {
    return voteCount;
  }

  public void setVoteCount(java.lang.Long newVoteCount) {
    voteCount = newVoteCount;
  }

}
