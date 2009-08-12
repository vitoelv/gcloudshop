package com.jcommerce.core.test.case9;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Parent9 extends BaseModel{
	
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
    private Long keyId;
    
    
    @Persistent
    private String name;
    
    @Persistent(mappedBy="parent")
    private Set<Child9> children = new HashSet<Child9>();

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

	public Set<Child9> getChildren() {
		return children;
	}

	public void setChildren(Set<Child9> children) {
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}
