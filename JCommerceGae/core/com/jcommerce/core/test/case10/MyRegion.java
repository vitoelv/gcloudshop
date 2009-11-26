package com.jcommerce.core.test.case10;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.jcommerce.core.model.ModelObject;

@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable="true") 
public class MyRegion extends ModelObject {


	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String pkId;
    
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String keyName;
    
    @Persistent
    private Long longId;

    @Persistent
    private String name;
    
    @Persistent
    private MyRegion myparent;
    
    @Persistent(mappedBy="myparent")
    private Set<MyRegion> children = new HashSet<MyRegion>();
    
	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public Long getLongId() {
		return longId;
	}

	public void setLongId(Long longId) {
		this.longId = longId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<MyRegion> getChildren() {
		return children;
	}

	public void setChildren(Set<MyRegion> children) {
		this.children = children;
	}

	public MyRegion getMyparent() {
		return myparent;
	}

	public void setMyparent(MyRegion myparent) {
		this.myparent = myparent;
	}


}
