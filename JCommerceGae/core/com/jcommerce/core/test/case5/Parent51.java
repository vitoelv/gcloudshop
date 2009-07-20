package com.jcommerce.core.test.case5;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Parent51 {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
    
    @Persistent
	private String name;
    
    @Persistent
    private Set<Child5> children = new HashSet<Child5>();

    @Persistent
    private Child51 child51;
    
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

	public Set<Child5> getChildren() {
		return children;
	}

	public void setChildren(Set<Child5> children) {
		this.children = children;
	}

	public Child51 getChild51() {
		return child51;
	}

	public void setChild51(Child51 child51) {
		this.child51 = child51;
	}
    
}
