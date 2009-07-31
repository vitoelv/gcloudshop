package com.jcommerce.core.model;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.jcommerce.core.model.ModelObject;

@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable="true")
public class Region extends ModelObject {
	
	public static final String TYPE_COUNTRY = "0";
	public static final String TYPE_PROVINCE = "1";
	public static final String TYPE_CITY = "2";
	public static final String TYPE_DISTRICT = "3";
	
	public static final String DEFAULT_AGENCY_ID = "0";
	public static final String COUNTRY_PARENT_ID = "0";

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
	private String type;
    
    @Persistent
    private String parentId;
    
    @Persistent
	private String agencyId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	@Override
	public ModelObject getParent() {
		// TODO Auto-generated method stub
		return null;
	}

}
