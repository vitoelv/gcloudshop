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
public class PayLog extends ModelObject {

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
  private java.lang.String logId; 

  @Persistent
  private java.lang.String orderId; 

  @Persistent
  private java.lang.Double orderAmount=0.0; 

  @Persistent
  private java.lang.Long orderType=0l; 

  @Persistent
  private java.lang.Boolean isPaid=false; 



	public PayLog() {
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



  public java.lang.String getLogId() {
    return logId;
  }

  public void setLogId(java.lang.String newLogId) {
    logId = newLogId;
  }



  public java.lang.String getOrderId() {
    return orderId;
  }

  public void setOrderId(java.lang.String newOrderId) {
    orderId = newOrderId;
  }



  public java.lang.Double getOrderAmount() {
    return orderAmount;
  }

  public void setOrderAmount(java.lang.Double newOrderAmount) {
    orderAmount = newOrderAmount;
  }



  public java.lang.Long getOrderType() {
    return orderType;
  }

  public void setOrderType(java.lang.Long newOrderType) {
    orderType = newOrderType;
  }



  public java.lang.Boolean getIsPaid() {
    return isPaid;
  }

  public void setIsPaid(java.lang.Boolean newIsPaid) {
    isPaid = newIsPaid;
  }

}
