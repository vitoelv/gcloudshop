package com.jcommerce.core.test.case12;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Parent3 {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String pkId;
    
    @Persistent
	private String name;
	@Persistent
	private Child3 the1;
	
	@Persistent
	private Child3 the2;

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

	public Child3 getThe1() {
		return the1;
	}

	public void setThe1(Child3 the1) {
		this.the1 = the1;
	}

	public Child3 getThe2() {
		return the2;
	}

	public void setThe2(Child3 the2) {
		this.the2 = the2;
	}


}
