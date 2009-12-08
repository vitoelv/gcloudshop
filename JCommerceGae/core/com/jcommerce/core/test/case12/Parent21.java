package com.jcommerce.core.test.case12;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Parent21 extends BaseObject {
	
    @Persistent
	private String name;
	
	@Persistent
	private Child2 the1;

	public Child2 getThe1() {
		return the1;
	}

	public void setThe1(Child2 the1) {
		this.the1 = the1;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
