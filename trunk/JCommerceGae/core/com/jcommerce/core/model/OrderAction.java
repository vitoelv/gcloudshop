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
public class OrderAction extends ModelObject {

    // relations
        
    
  // fields
  @Persistent
  private java.lang.String actionId; 

  @Persistent
  private java.lang.String orderId; 

  @Persistent
  private java.lang.String actionUser; 

  @Persistent
  private java.lang.Long orderStatus=0l; 

  @Persistent
  private java.lang.Long shippingStatus=0l; 

  @Persistent
  private java.lang.Long payStatus=0l; 

  @Persistent
  private java.lang.String actionNote; 

  @Persistent
  private java.lang.Long logTime=0l; 



	public OrderAction() {
	}


	
  public java.lang.String getActionId() {
    return actionId;
  }

  public void setActionId(java.lang.String newActionId) {
    actionId = newActionId;
  }



  public java.lang.String getOrderId() {
    return orderId;
  }

  public void setOrderId(java.lang.String newOrderId) {
    orderId = newOrderId;
  }



  public java.lang.String getActionUser() {
    return actionUser;
  }

  public void setActionUser(java.lang.String newActionUser) {
    actionUser = newActionUser;
  }



  public java.lang.Long getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(java.lang.Long newOrderStatus) {
    orderStatus = newOrderStatus;
  }



  public java.lang.Long getShippingStatus() {
    return shippingStatus;
  }

  public void setShippingStatus(java.lang.Long newShippingStatus) {
    shippingStatus = newShippingStatus;
  }



  public java.lang.Long getPayStatus() {
    return payStatus;
  }

  public void setPayStatus(java.lang.Long newPayStatus) {
    payStatus = newPayStatus;
  }



  public java.lang.String getActionNote() {
    return actionNote;
  }

  public void setActionNote(java.lang.String newActionNote) {
    actionNote = newActionNote;
  }



  public java.lang.Long getLogTime() {
    return logTime;
  }

  public void setLogTime(java.lang.Long newLogTime) {
    logTime = newLogTime;
  }

}
