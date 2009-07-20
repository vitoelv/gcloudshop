package com.jcommerce.core.test.case6;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Child6 {
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
    
    @Persistent
	private String name;
    
    @Persistent
    private String parent61Id;
    
    @Persistent
    private String parent62Id;

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

	public String getParent61Id() {
		return parent61Id;
	}

	public void setParent61Id(String parent61Id) {
		this.parent61Id = parent61Id;
	}

	public String getParent62Id() {
		return parent62Id;
	}

	public void setParent62Id(String parent62Id) {
		this.parent62Id = parent62Id;
	}





    
    
}
