package com.jcommerce.core.test.case11;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.jcommerce.core.model.ModelObject;

@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable="true") 
public class Person extends ModelObject{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String pkId;
    
    @Persistent
	private String name;

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getKeyName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKeyName(String kn) {
		// TODO Auto-generated method stub
		
	}
}
