package com.jcommerce.core.test.case4;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Child4 {
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
    
    @Persistent
	private String name;
    
    @Persistent
    private Parent41 parent41;
    
    @Persistent
    private Parent42 parent42;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Parent41 getParent41() {
		return parent41;
	}

	public void setParent41(Parent41 parent41) {
		this.parent41 = parent41;
	}

	public Parent42 getParent42() {
		return parent42;
	}

	public void setParent42(Parent42 parent42) {
		this.parent42 = parent42;
	}


    
    
}
