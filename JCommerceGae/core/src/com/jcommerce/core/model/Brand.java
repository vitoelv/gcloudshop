/**
* Author: Bob Chen
*/

package com.jcommerce.core.model;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable="true")
public class Brand extends ModelObject {


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
    private String logo;

    // TODO leon combine logo and logoFileId to a DSFile instance, 
    // in order to make DSFile a child of Brand, and thus can be put in a DS transaction
    @Persistent
    private String logoFileId;
    
//    @Persistent
//    private DSFile logo;
//    
//    @Persistent
//    private String logoFileName;
//    
//    @Persistent
//    private String logoFileId;
    
    @Persistent
    private String description;
    
    @Persistent
    private String siteUrl;
    
    @Persistent
    private boolean show;
    
    @Persistent
    private int sortOrder;
    

    

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
//    public DSFile getLogo() {
//        return logo;
//    }
//    
//    public void setLogo(DSFile logo) {
//        this.logo = logo;
//    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getSiteUrl() {
        return siteUrl;
    }
    
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }
    
    public boolean isShow() {
        return show;
    }
    
    public void setShow(boolean show) {
        this.show = show;
    }
    
    public int getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
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

	public String getLogoFileId() {
		return logoFileId;
	}

	public void setLogoFileId(String logoFileId) {
		this.logoFileId = logoFileId;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

//	public String getLogoFileName() {
//		return logoFileName;
//	}
//
//	public void setLogoFileName(String logoFileName) {
//		this.logoFileName = logoFileName;
//	}
}
