package com.jcommerce.core.test.case7;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Parent7 {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
    
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String keyName;
    
    @Persistent
	private String name;
   
    @Persistent
	private String child7Id;
    
    @Persistent
	private String child7KN;
    
//    @Persistent
    @Persistent
	private Child7 child7;

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

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public Child7 getChild7() {
		return child7;
	}

	public void setChild7(Child7 child7) {
		this.child7 = child7;
	}

	public String getChild7KN() {
		return child7KN;
	}

	public void setChild7KN(String child7KN) {
		this.child7KN = child7KN;
	}

	public String getChild7Id() {
		return child7Id;
	}

	public void setChild7Id(String child7Id) {
		this.child7Id = child7Id;
	}







    
    
}
