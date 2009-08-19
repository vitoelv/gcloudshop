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
public class Parent52 {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String pkId;
    
    @Persistent
	private String name;
    
    @Persistent
    private Set<Child5> children2 = new HashSet<Child5>();



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Child5> getChildren2() {
		return children2;
	}

	public void setChildren2(Set<Child5> children2) {
		this.children2 = children2;
	}

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}


    
    
}
