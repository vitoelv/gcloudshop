package com.jcommerce.core.test.case1;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.jcommerce.core.model.ModelObject;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Person extends ModelObject{
    @Override
    public ModelObject getParent() {
    	return null;
    }
	public Person() {
		
	}
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
	
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String keyName;

    
    @Persistent
	private String name;
	
    @Persistent(mappedBy="person")
    private Set<Address> addresses = new HashSet<Address>();

    @Persistent
    private Set<String> aliasList = new HashSet<String>();
    
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

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<String> getAliasList() {
		return aliasList;
	}

	public void setAliasList(Set<String> aliasList) {
		this.aliasList = aliasList;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
    
    
    
}
