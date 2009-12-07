package com.jcommerce.core.test.case12;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable(identityType = IdentityType.APPLICATION)

public class Parent extends BaseObject {
	
    @Persistent(mappedBy="parent")
    private Set<Child> children = new HashSet<Child>();

	public Set<Child> getChildren() {
		return children;
	}
	public void setChildren(Set<Child> children) {
		this.children = children;
	}
    
}
