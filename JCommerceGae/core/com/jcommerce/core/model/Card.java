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
public class Card extends ModelObject {

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
  private java.lang.String cardId; 

  @Persistent
  private java.lang.String cardName; 

  @Persistent
  private java.lang.String cardImg; 

  @Persistent
  private java.lang.Double cardFee=0.0; 

  @Persistent
  private java.lang.Double freeMoney=0.0; 

  @Persistent
  private java.lang.String cardDesc; 



	public Card() {
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



  public java.lang.String getCardId() {
    return cardId;
  }

  public void setCardId(java.lang.String newCardId) {
    cardId = newCardId;
  }



  public java.lang.String getCardName() {
    return cardName;
  }

  public void setCardName(java.lang.String newCardName) {
    cardName = newCardName;
  }



  public java.lang.String getCardImg() {
    return cardImg;
  }

  public void setCardImg(java.lang.String newCardImg) {
    cardImg = newCardImg;
  }



  public java.lang.Double getCardFee() {
    return cardFee;
  }

  public void setCardFee(java.lang.Double newCardFee) {
    cardFee = newCardFee;
  }



  public java.lang.Double getFreeMoney() {
    return freeMoney;
  }

  public void setFreeMoney(java.lang.Double newFreeMoney) {
    freeMoney = newFreeMoney;
  }



  public java.lang.String getCardDesc() {
    return cardDesc;
  }

  public void setCardDesc(java.lang.String newCardDesc) {
    cardDesc = newCardDesc;
  }

}
