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
public class Archor {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String pkId;
    
    @Persistent
	private String name;
    
    @Persistent
//    @Persistent(mappedBy="archor1")
    private Set<Parent61> parent61s = new HashSet<Parent61>();
    
    @Persistent
//    @Persistent(mappedBy="archor2")
    private Set<Parent62> parent62s = new HashSet<Parent62>();



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Parent61> getParent61s() {
		return parent61s;
	}

	public void setParent61s(Set<Parent61> parent61s) {
		this.parent61s = parent61s;
	}

	public Set<Parent62> getParent62s() {
		return parent62s;
	}

	public void setParent62s(Set<Parent62> parent62s) {
		this.parent62s = parent62s;
	}

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}
    
}
