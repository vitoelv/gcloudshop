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
public class AdminAction extends ModelObject {
    // relations
        
    
  // fields
  @Persistent
  private java.lang.String actionId; 

  @Persistent
  private java.lang.String parentId; 

  @Persistent
  private java.lang.String actionCode; 



	public AdminAction() {
	}


  public java.lang.String getActionId() {
    return actionId;
  }

  public void setActionId(java.lang.String newActionId) {
    actionId = newActionId;
  }



  public java.lang.String getParentId() {
    return parentId;
  }

  public void setParentId(java.lang.String newParentId) {
    parentId = newParentId;
  }



  public java.lang.String getActionCode() {
    return actionCode;
  }

  public void setActionCode(java.lang.String newActionCode) {
    actionCode = newActionCode;
  }

}
