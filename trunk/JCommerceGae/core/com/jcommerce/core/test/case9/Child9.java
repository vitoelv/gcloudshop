package com.jcommerce.core.test.case9;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Child9  extends BaseModel {
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;
 
    
    @Persistent
    private String name;
    
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
    private Long keyId;
    
    @Persistent
    private Parent9 parent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getKeyId() {
		return keyId;
	}

	public void setKeyId(Long keyId) {
		this.keyId = keyId;
	}

	public Parent9 getParent() {
		return parent;
	}

	public void setParent(Parent9 parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}
