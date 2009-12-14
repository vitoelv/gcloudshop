package com.jcommerce.core.model;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.jcommerce.gwt.client.util.URLConstants;

@PersistenceCapable(identityType = IdentityType.APPLICATION) 
@Inheritance(customStrategy = "complete-table")
public abstract class ModelObject implements Serializable, URLConstants {
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String pkId;
    
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String keyName;
    
    @Persistent
    private Long longId;

    
//    public ModelObject getParent(){
//    	return null;
//    }






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


   public String getTreeParentId(){
	   return null;
   }
   public void setTreeParentId(String treeParentId){
   }
   




	public Long getLongId() {
		return longId;
	}

	public void setLongId(Long longId) {
		this.longId = longId;
	}
    

}
