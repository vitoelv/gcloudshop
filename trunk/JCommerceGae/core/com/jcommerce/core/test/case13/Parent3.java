package com.jcommerce.core.test.case13;

import java.util.HashSet;
import java.util.Set;

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
	private Set<Child3> childset1 = new HashSet<Child3>();
	
	@Persistent
	private Set<Child3> childset2 = new HashSet<Child3>();

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

	public Set<Child3> getChildset1() {
		return childset1;
	}

	public void setChildset1(Set<Child3> childset1) {
		this.childset1 = childset1;
	}

	public Set<Child3> getChildset2() {
		return childset2;
	}

	public void setChildset2(Set<Child3> childset2) {
		this.childset2 = childset2;
	}




}
