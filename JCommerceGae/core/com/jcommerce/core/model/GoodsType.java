/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GoodsType extends ModelObject {
//	public static final String ID = "id";
//	public static final String NAME = "name";
//	public static final String ENABLED = "enabled";
//	public static final String ATTRIBUTEGROUP = "attributeGroup";
//	public static final String ATTRCOUNT = "attrcount";

    @Override
    public ModelObject getParent() {
    	return null;
    }
	
	public GoodsType() {
		
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
    
    @Persistent
	private boolean enabled;
	
    @Persistent(mappedBy="goodsType")
	private Set<Attribute> attributes = new HashSet<Attribute>();
    
    @NotPersistent
	private long attrcount;
	
    @Persistent
	private String attributeGroup;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAttributeGroup() {
        return attributeGroup;
    }

    public void setAttributeGroup(String attributeGroup) {
        this.attributeGroup = attributeGroup;
    }

    public String[] getAttributeGroups() {
        if (attributeGroup == null) {
            return new String[0];
        }
        
        return attributeGroup.split(",");
    }

    public void setAttributeGroups(String[] groups) {
        StringBuffer sb = new StringBuffer();
        if (groups != null) {
            for (int i = 0; i < groups.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(groups[i]);
            }
        }
        setAttributeGroup(sb.toString());
    }

	public Set<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}

	public long getAttrcount() {
		return attrcount;
	}

	public void setAttrcount(long attrcount) {
		this.attrcount = attrcount;
	}
	
	public void setId(String id) {
		this.id = id;
	}
    public String getId() {
    	return id;
    }
    
	public void setKeyName(String kn) {
		this.keyName = kn;
	}
    public String getKeyName() {
    	return keyName;
    }
	
}
