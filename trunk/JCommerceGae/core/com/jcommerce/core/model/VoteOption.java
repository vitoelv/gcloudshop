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
public class VoteOption extends ModelObject {

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
  private java.lang.String optionId; 

  @Persistent
  private java.lang.String voteId; 

  @Persistent
  private java.lang.String optionName; 

  @Persistent
  private java.lang.Long optionCount=0l; 



	public VoteOption() {
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



  public java.lang.String getOptionId() {
    return optionId;
  }

  public void setOptionId(java.lang.String newOptionId) {
    optionId = newOptionId;
  }



  public java.lang.String getVoteId() {
    return voteId;
  }

  public void setVoteId(java.lang.String newVoteId) {
    voteId = newVoteId;
  }



  public java.lang.String getOptionName() {
    return optionName;
  }

  public void setOptionName(java.lang.String newOptionName) {
    optionName = newOptionName;
  }



  public java.lang.Long getOptionCount() {
    return optionCount;
  }

  public void setOptionCount(java.lang.Long newOptionCount) {
    optionCount = newOptionCount;
  }

}
