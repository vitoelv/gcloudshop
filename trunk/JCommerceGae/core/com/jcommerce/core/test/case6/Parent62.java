package com.jcommerce.core.test.case6;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Parent62 {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String pkId;
    
    @Persistent
	private String name;
    
//    @Persistent
//	private Archor archor2;

    @Persistent
	private Set<Child6> child62=new HashSet<Child6>();
    
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



	public Set<Child6> getChild62() {
		return child62;
	}

	public void setChild62(Set<Child6> child62) {
		this.child62 = child62;
	}


    
}
